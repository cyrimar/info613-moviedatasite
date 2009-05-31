package edu.drexel.info613.moviedatasite.servlet;

import edu.drexel.info613.moviedatasite.db.DBResult;
import edu.drexel.info613.moviedatasite.db.MovieDB;
import edu.drexel.info613.moviedatasite.domain.Movie;
import edu.drexel.info613.moviedatasite.xml.MovieDataXMLParser;
import edu.drexel.info613.moviedatasite.xml.XMLUploadResult;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/** This servlet handles uploading xml files for the Movie Data Website.
 * 
 * @author Patrick Freestone, Batuhan Yukselen
 */
public class DataUploadServlet extends HttpServlet {

    private static final String FILE_SEPARATOR    = System.getProperty("file.separator");
    private static final String UPLOADED_FILE_DIR = System.getProperty("java.io.tmpdir");
    private static final int    ZIP_BUFFER        = 2048;
    private static final long   serialVersionUID  = 1;

    private static final String HTML_STREAM_BEGIN = "<html><head><title>Upload Results</title></head><body>";
    private static final String HTML_STREAM_END   = "</body></html>";

    /**
     * The processRequest method is the main driver for the servlet. The method coordinates writing the 
     * uploaded file to disk, extracting it from a zip if necessary, retrieving domain objects from the xml
     * file, writing the domain objects to the database, and reporting on the results back to the user.
     * 
     * @param request           HTTP request sent to the servlet
     * @param response          HTTP response to be sent back to the requester
     * @throws ServletException 
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            File uploadDir = makeFileUploadDirectory();
            File uploadedFileOnDisk = writeUploadedFileToDisk(request, uploadDir);
            List<File> xmlFileList = getXMLFileListFromUploadedFile(uploadedFileOnDisk);
            if (xmlFileList.size() > 0) {
                MovieDataXMLParser xmlParser = new MovieDataXMLParser();
                XMLUploadResult uploadResult = xmlParser.readMovies(xmlFileList);
                List<Movie> moviesToInsert = uploadResult.getMovies();
                if (moviesToInsert.size() > 0) {
                    DBResult movieInsertionResult = MovieDB.getInstance().insertMovies(moviesToInsert);
                    writeUploadResultsPage(response, uploadResult, movieInsertionResult);
                } else {
                    writeUploadErrorPage(response, "No XML files could be converted to Domain Objects.");
                }
            } else {
                writeUploadErrorPage(response, "No XML files found in upload.");
            }
        } else {
            writeUploadErrorPage(response, "Servlet Upload was not Multipart - a.k.a. no file found in upload.");
        }

    }

    /**
     * Method for handling form using the "Get" method. All requests are forwarded to the processRequest
     * method.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Method for handling form using the "Post" method. All requests are forwarded to the processRequest
     * method.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * This method is responsible for making a directory to which to write the uploaded files. The directory
     * is located in the java.io.tmpdir location and is named using the current time in milliseconds. This
     * could cause issues if two uploads occur at exactly the same time, as there could be contention for, a
     * specific directory name. However, since this is not a high-volume system, the implemented solution 
     * should suffice.
     * 
     * @return  directory where file should be uploaded.
     */
    private File makeFileUploadDirectory() {
        File uploadsDir = new File(UPLOADED_FILE_DIR);
        if (!uploadsDir.exists()) {
            uploadsDir.mkdir();
        }
        String uploadSpecificDirName = UPLOADED_FILE_DIR + FILE_SEPARATOR + System.currentTimeMillis();
        File uploadSpecificDir = new File(uploadSpecificDirName);
        uploadSpecificDir.mkdir();
        return uploadSpecificDir;
    }

    /**
     * This method is responsible for writing the uploaded file to disk in the directory created by the
     * <code>makeFileUploadedDirectory</code> method. 
     * 
     * @param request               incoming request in which file is contained
     * @param directoryToWriteTo    location to which to write the uploaded file
     * @return                      new file written to disk
     */
    private File writeUploadedFileToDisk(HttpServletRequest request, File directoryToWriteTo) {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List items = null;
        File uploadedFile = null;
        try {
            items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem)iter.next();
                if (!item.isFormField()) {
                    String fileName = item.getName();
                    uploadedFile = new File(directoryToWriteTo.getCanonicalPath() + FILE_SEPARATOR + fileName);
                    item.write(uploadedFile);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadedFile;
    }

    /**
     * Zip extraction modified from http://java.sun.com/developer/technicalArticles/Programming/compression/
     * This method is responsible for generating the list of <code>File</code> objects from the uploaded file.
     * If this uploaded file the method will extract the file and add all contained xml files to the returned
     * list.
     * 
     * @param uploadedFile  file uploaded by user
     * @return              <code>List</code> of <code>File</code> objects from the uploaded file that are
     *                      xml files
     */
    private List<File> getXMLFileListFromUploadedFile(File uploadedFile) {
        List<File> listOfXMLFiles = new ArrayList<File>();
        if (uploadedFile.getName().matches(".*.xml")) {
            listOfXMLFiles.add(uploadedFile);
        } else if (uploadedFile.getName().matches(".*.zip")) {
            System.out.println("Extracting File: " + uploadedFile.getName());
            try {
                String extractionDir = constructZipExtractionDirectory(uploadedFile);
                BufferedOutputStream dest = null;
                FileInputStream fis = new FileInputStream(uploadedFile);
                ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    int count;
                    byte data[] = new byte[ZIP_BUFFER];
                    String fileName = extractionDir + FILE_SEPARATOR + entry.getName();
                    System.out.println("Extracting: " + fileName);
                    if (entry.isDirectory()) {
                        System.out.println("This entry is a directory");
                        File newDir = new File(fileName);
                        newDir.mkdir();
                    } else {
                        FileOutputStream fos = new FileOutputStream(fileName);
                        dest = new BufferedOutputStream(fos, ZIP_BUFFER);
                        while ((count = zis.read(data, 0, ZIP_BUFFER)) != -1) {
                            dest.write(data, 0, count);
                        }
                        dest.flush();
                        dest.close();
                        File extractedFile = new File(fileName);
                        if (extractedFile.getName().matches(".*.xml")) {
                            listOfXMLFiles.add(extractedFile);
                        }
                    }
                }
                zis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listOfXMLFiles;
    }

    /**
     * Method to construction the directory to which to extract an uploaded zip file.
     * 
     * @param zipFile   the file being unzipped
     * @return          the directory to which to extract the file
     */
    private String constructZipExtractionDirectory(File zipFile) {
        String extractionDirName = zipFile.getParent() + FILE_SEPARATOR + zipFile.getName().substring(0, zipFile.getName().length() - 4);
        File extractionDir = new File(extractionDirName);
        if (extractionDir.exists()) {
            extractionDir.delete();
        }
        extractionDir.mkdir();
        return extractionDirName;
    }

    /**
     * This method is responsible for writing the result page. The page will report on the files successfully
     * uploaded, the files that failed to be uploaded, and the result of writing to the DB.
     * 
     * @param response  response to which to write HTML output
     * @param xmlResult result of converting XML files to domain objects
     * @param dbResult  result of writing domain objects to DB
     */
    private void writeUploadResultsPage(HttpServletResponse response, XMLUploadResult xmlResult, DBResult dbResult) {
        try {
            PrintWriter out = response.getWriter();
            out.print(HTML_STREAM_BEGIN);

            out.print("<h2>Successfully Uploaded Files</h2>");
            out.print("<p>Count: " + xmlResult.getSuccessfulFiles().size() + "</p>");
            out.print("<p>Files: ");
            for (File file : xmlResult.getSuccessfulFiles()) {
                out.print(file.getName() + ", ");
            }
            out.print("</p><br/>");

            out.print("<h2>File Upload Failures</h2>");
            out.print("<table><tr><th>File Name</th><th>Error</th></tr>");
            Iterator<Map.Entry<File, String>> it = xmlResult.getFailedFiles().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<File, String> failedFile = (Map.Entry<File, String>)it.next();
                out.print("<tr><td>" + failedFile.getKey() + "</td><td>" + failedFile.getValue() + "</td></tr>");
            }
            out.print("</table><br/>");

            out.print("<h2>DB Write Result</h2>");
            if (dbResult.isSuccess()) {
                out.print("<p>Success!</p>");
            } else {
                out.print("Failed with error: " + dbResult.getError());
            }
            out.print(HTML_STREAM_END);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is responsible for writing an error page. The method accepts an error message as a 
     * <code>String</code>.
     * 
     * @param response      response to which to write page
     * @param errorMessage  error message to report on in page
     */
    private void writeUploadErrorPage(HttpServletResponse response, String errorMessage) {
        try {
            PrintWriter out = response.getWriter();
            out.print(HTML_STREAM_BEGIN);
            out.print("<p>Uploaded Failed with Error: </p>");
            out.print("<p>" + errorMessage + "</p>");
            out.print(HTML_STREAM_END);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
