package edu.drexel.info613.moviedatasite.xml;

import edu.drexel.info613.moviedatasite.domain.Actor;
import edu.drexel.info613.moviedatasite.domain.Director;
import edu.drexel.info613.moviedatasite.domain.Genre;
import edu.drexel.info613.moviedatasite.domain.Movie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/*
 * This class is responsible for converting XML files into domain objects.
 * @author Patrick Freestone, Batuhan Yukselen
 */
public class MovieDataXMLParser {

    private boolean documentValidation = true;
    XMLUploadResult uploadResult       = new XMLUploadResult();

    /**
     * Empty Constructor.
     */
    public MovieDataXMLParser() {

    }

    /**
     * This method is the entry point for the class. The method accepts a list of <code>java.io.File</code>
     * objects and returns them in an <code>XMLUploadResult</code>.
     * @param filesToRead   list of xml files to convert to domain objects
     * @return              XMLUploadResult containing domain objects, a list of successful files, and a list of failed file with error messages.
     */
    public XMLUploadResult readMovies(List<File> filesToRead) {
        for (File file : filesToRead) {
            try {
                uploadResult.addMovie(readMovie(file));
                uploadResult.addSuccessfulFile(file);
            } catch (SAXException e) {
                String errorMessage = "SAXException parsing " + file.getName() + ":" + e.getMessage();
                uploadResult.addFailedFile(file, errorMessage);
            } catch (ParserConfigurationException e) {
                String errorMessage = "ParserConfigurationException parsing " + file.getName() + ":" + e.getMessage();
                uploadResult.addFailedFile(file, errorMessage);
            } catch (IOException e) {
                String errorMessage = "IOException parsing " + file.getName() + ":" + e.getMessage();
                uploadResult.addFailedFile(file, errorMessage);
            } catch (TransformerException e) {
                String errorMessage = "TransformerException parsing " + file.getName() + ":" + e.getMessage();
                uploadResult.addFailedFile(file, errorMessage);
            }
        }
        return uploadResult;
    }

    /**
     * This method is for processing a single file and is called for each files in the readMovies method. A
     * <code>Movie</code> object is returned from the <code>File</code> object passed in.
     * 
     * @param fileToRead    file from which to extract domain object
     * @return              <code>Movie</code> object from file
     */
    private Movie readMovie(File fileToRead) throws SAXException, ParserConfigurationException, IOException, TransformerException {
        Document doc = getXMLDocumentFromFile(fileToRead);
        Movie movieFromFile = getMovieFromXMLDocument(doc);
        return movieFromFile;
    }

    /**
     * The purpose of this method is to obtain a <code>Document</code> object from a <code>File</code> object.
     * The <code>Document</code> object can then be navigated using the DOM model to extract needed data.
     * 
     * @param fileToParse   <code>File</code> to convert to <code>Document</code>
     * @return              <code>Document</code> from file
     */
    private Document getXMLDocumentFromFile(File fileToParse) throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(documentValidation);
        DocumentBuilder builder = factory.newDocumentBuilder();
        MovieDataXMLErrorHandler errorHandler = new MovieDataXMLErrorHandler();
        builder.setErrorHandler(errorHandler);
        Document doc = builder.parse(fileToParse);
        return doc;
    }

    /**
     * This method organizes the creation of the <code>Movie</code> domain object from the <code>Document</code>.
     * The method distributes the work for each of the attributes to other methods.
     * 
     * @param doc   document from which to extract domain object
     * @return      domain <code>Movie</code> object
     */
    private Movie getMovieFromXMLDocument(Document doc) throws TransformerException {
        String title = getTitleFromXMLDocument(doc);
        String year = getYearFromXMLDocument(doc);
        List<Director> directors = getDirectorsFromXMLDocument(doc);
        List<Genre> genres = getGenresFromXMLDocument(doc);
        List<Actor> actors = getActorsFromXMLDocument(doc);
        Movie movieFromFile = new Movie(title, year, directors, genres, actors);
        return movieFromFile;
    }

    /**
     * The purpose of this method it to retrieve the movie title from the <code>Document</code>.
     * 
     * @param doc   document from which to extract title
     * @return      movie title
     */
    private String getTitleFromXMLDocument(Document doc) throws TransformerException {
        String title = "";
        Node node = XPathAPI.selectSingleNode(doc, "//W4F_DOC/Movie/Title");
        Element titleElement = (Element)node;
        title = titleElement.getTextContent();
        return title;
    }

    /**
     * The purpose of this method it to retrieve the movie year from the <code>Document</code>
     * 
     * @param doc   document from which to extract year
     * @return      movie's year
     */
    private String getYearFromXMLDocument(Document doc) throws TransformerException {
        String year = "";
        Node node = XPathAPI.selectSingleNode(doc, "//W4F_DOC/Movie/Year");
        Element titleElement = (Element)node;
        year = titleElement.getTextContent();
        return year;
    }

    /**
     * The purpose of this method it to retrieve the movie directors from the <code>Document</code>
     * 
     * @param doc   document from which to extract directors
     * @return      movie's director
     */
    private List<Director> getDirectorsFromXMLDocument(Document doc) throws TransformerException {
        List<Director> directors = new ArrayList<Director>();
        NodeList nodeList = XPathAPI.selectNodeList(doc, "//W4F_DOC/Movie/Directed_By/Director");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element directorElement = (Element)nodeList.item(i);
            String directorName = directorElement.getTextContent();
            Director director = new Director(directorName);
            directors.add(director);
        }
        return directors;
    }

    /**
     * The purpose of this method it to retrieve the movie's genres from the <code>Document</code>
     * 
     * @param doc   document from which to extract genres
     * @return      movie's genres
     */
    private List<Genre> getGenresFromXMLDocument(Document doc) throws TransformerException {
        List<Genre> genres = new ArrayList<Genre>();
        NodeList nodeList = XPathAPI.selectNodeList(doc, "//W4F_DOC/Movie/Genres/Genre");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element genreElement = (Element)nodeList.item(i);
            String genreName = genreElement.getTextContent();
            Genre genre = new Genre(genreName);
            genres.add(genre);
        }
        return genres;
    }

    /**
     * The purpose of this method it to retrieve the movie's cast from the <code>Document</code>
     * 
     * @param doc   document from which to extract the movie's cast
     * @return      movie's actors
     */
    private List<Actor> getActorsFromXMLDocument(Document doc) throws TransformerException {
        List<Actor> actors = new ArrayList<Actor>();
        NodeList nodeList = XPathAPI.selectNodeList(doc, "//W4F_DOC/Movie/Cast/Actor");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element actorElement = (Element)nodeList.item(i);
            Element actorFirstNameElement = (Element)XPathAPI.selectSingleNode(actorElement, "FirstName");
            Element actorLastNameElement = (Element)XPathAPI.selectSingleNode(actorElement, "LastName");
            String firstName = actorFirstNameElement.getTextContent();
            String lastName = actorLastNameElement.getTextContent();
            Actor actor = new Actor(firstName, lastName);
            actors.add(actor);
        }
        return actors;
    }
}

/**
 * Class modified from http://www.ibm.com/developerworks/library/x-tipeh.html. The purpose of this class
 * is to act as an error handler for errors encountered by the DocumentBuilder object when parsing an
 * xml file. The class will construct an error message and forward the error via a <code>SAXEception</code>
 * 
 * @author Patrick Freestone, Batuhan Yukselen
 *
 */
class MovieDataXMLErrorHandler implements ErrorHandler {

    private final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * This method handles warnings from the <code>DocumentBuilder</code>.
     */
    public void warning(SAXParseException exception) throws SAXException {
        String message = "**Parsing Warning**" + LINE_SEPARATOR + "  Line:    " + exception.getLineNumber() + LINE_SEPARATOR + "  URI:     " + exception.getSystemId() + LINE_SEPARATOR + "  Message: "
                + exception.getMessage();
        throw new SAXException(message);
    }

    /**
     * This method handles errors from the <code>DocumentBuilder</code>.
     */
    public void error(SAXParseException exception) throws SAXException {
        String message = "**Parsing Error**" + LINE_SEPARATOR + "  Line:    " + exception.getLineNumber() + LINE_SEPARATOR + "  URI:     " + exception.getSystemId() + LINE_SEPARATOR + "  Message: "
                + exception.getMessage();
        throw new SAXException(message);
    }

    /**
     * This method handles fatal errors from the <code>DocumentBuilder</code>.
     */
    public void fatalError(SAXParseException exception) throws SAXException {
        String message = "**Parsing Fatal Error**" + LINE_SEPARATOR + "  Line:    " + exception.getLineNumber() + LINE_SEPARATOR + "  URI:     " + exception.getSystemId() + LINE_SEPARATOR + "  Message: "
                + exception.getMessage();
        throw new SAXException(message);
    }
}
