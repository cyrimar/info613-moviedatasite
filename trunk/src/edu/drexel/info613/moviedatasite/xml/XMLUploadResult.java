package edu.drexel.info613.moviedatasite.xml;

import edu.drexel.info613.moviedatasite.domain.Movie;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The purpose of this class is to serve as a vessel for communicating XML upload status back to a caller
 * of the <code>MovieDataXMLParser</code>. The class provides a list of <code>Movie</code> objects, a list
 * of files successfully uploaded, and a list of failed files with their associated error messages. In
 * theory, the list of <code>Movie</code> objects should always be the same size as the list of successful
 * files.
 * 
 * @author Patrick Freestone, Batuhan Yukselen
 *
 */
public class XMLUploadResult {
    
    private ArrayList<File> successfulFiles;
    private HashMap<File,String> failedFiles;
    private ArrayList<Movie> movies;
    
    /**
     * Constructor. Initializes member variables.
     */
    public XMLUploadResult() {
        successfulFiles = new ArrayList<File>();
        failedFiles = new HashMap<File,String>();
        movies = new ArrayList<Movie>();
    }
    
    /**
     * Adds a file to the list of successfully uploaded files.
     * 
     * @param fileToAdd file to add to list
     */
    public void addSuccessfulFile(File fileToAdd) {
        this.successfulFiles.add(fileToAdd);
    }
    
    /**
     * Returns the list of successfully uploaded files.
     * 
     * @return  list of successfully uploaded files.
     */
    public List<File> getSuccessfulFiles() {
        return this.successfulFiles;
    }
    
    /**
     * Adds a failed file to the list of failed files and associates an error message with that file's
     * failure.
     * 
     * @param fileToAdd file to added to failed files list
     * @param error     error message for the file
     */
    public void addFailedFile(File fileToAdd, String error) {
        this.failedFiles.put(fileToAdd, error);
    }
    
    /**
     * This method returns a <code>Map</code> of failed files and their error messages. The failed file
     * is the key and the error message is the value. This is intended to be iterated over to provide 
     * information to the end user.
     * 
     * @return  map containing failed files and associated error messages
     */
    public Map<File,String> getFailedFiles() {
        return this.failedFiles;
    }
    
    /**
     * This method adds a movie to the list of domain objects returned as part of the result.
     * 
     * @param movieToAdd    movie to add to the list.
     */
    public void addMovie(Movie movieToAdd) {
        this.movies.add(movieToAdd);
    }
    
    /**
     * This method returns a list of domain objects for the XML files.
     * 
     * @return  list of <code>Movie</code> domain objects.
     */
    public List<Movie> getMovies() {
        return this.movies;
    }

}
