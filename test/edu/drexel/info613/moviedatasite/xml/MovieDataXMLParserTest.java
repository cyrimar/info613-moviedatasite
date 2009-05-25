package edu.drexel.info613.moviedatasite.xml;

import edu.drexel.info613.moviedatasite.domain.Actor;
import edu.drexel.info613.moviedatasite.domain.Director;
import edu.drexel.info613.moviedatasite.domain.Genre;
import edu.drexel.info613.moviedatasite.domain.Movie;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

/**
 * This class is used to test the MovieDataXMLParser
 * 
 * @author Patrick Freestone, Batuhan Yukselen
 *
 */
public class MovieDataXMLParserTest extends TestCase {

    public MovieDataXMLParserTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests a valid xml document. In other words, a document that follows the DTD supplied.
     */
    public void testReadMoviesOneValid() {
        File fileToCheck = new File("xml/0013442.xml");
        MovieDataXMLParser xmlParser = new MovieDataXMLParser();
        List<File> filesToUpload = new ArrayList<File>();
        filesToUpload.add(fileToCheck);
        XMLUploadResult uploadResult = xmlParser.readMovies(filesToUpload);
        Movie movieFromFile = uploadResult.getMovies().get(0);
        String title = movieFromFile.getTitle();
        String year = movieFromFile.getYear();
        List<Director> directors = movieFromFile.getDirectors();
        List<Genre> genres = movieFromFile.getGenres();
        List<Actor> actors = movieFromFile.getActors();
        assertEquals("Nosferatu, eine Symphonie des Grauens",title);
        assertEquals("1922",year);
        assertEquals(1,directors.size());
        assertEquals(2,genres.size());
        assertEquals(15,actors.size());
    }
    
    /**
     * Tests an invalid xml document. In other words, a document that does not follow the DTD supplied.
     */
    public void testReadMoviesOneInvalid() {
        File fileToCheck = new File("xml/0015648.xml");
        MovieDataXMLParser xmlParser = new MovieDataXMLParser();
        List<File> filesToUpload = new ArrayList<File>();
        filesToUpload.add(fileToCheck);
        XMLUploadResult uploadResult = xmlParser.readMovies(filesToUpload);
        Map<File,String> failedFiles = uploadResult.getFailedFiles();
        assertEquals(0,uploadResult.getMovies().size());
        assertEquals(0,uploadResult.getSuccessfulFiles().size());
        assertEquals(1,uploadResult.getFailedFiles().size());
        assertTrue(failedFiles.containsKey(fileToCheck));
        assertTrue(failedFiles.get(fileToCheck).length() > 0);
    }
    
    /**
     * Tests uploading multiple xml documents simultaneously.
     */
    public void testReadMoviesAll() {
         File directoryWithFiles = new File("xml");
         XMLFileFilter xmlFileFilter = new XMLFileFilter();
         File[] filesToCheckArray = directoryWithFiles.listFiles(xmlFileFilter);
         List<File> filesToCheck = new ArrayList<File>();
         for (int i = 0; i < filesToCheckArray.length; i++) {
             filesToCheck.add(filesToCheckArray[i]);
         }
         MovieDataXMLParser xmlParser = new MovieDataXMLParser();
         XMLUploadResult uploadResult = xmlParser.readMovies(filesToCheck);
         assertEquals(453,uploadResult.getMovies().size());
         assertEquals(453,uploadResult.getSuccessfulFiles().size());
         assertEquals(37,uploadResult.getFailedFiles().size());
     }

}
