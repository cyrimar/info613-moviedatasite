package edu.drexel.info613.moviedatasite.xml;

import java.io.File;
import java.io.FileFilter;

/**
 * This class is used to filter a set of files to only include xml files.
 * 
 * @author Patrick Freestone, Batuhan Yukselen
 *
 */
public class XMLFileFilter implements FileFilter {

    /**
     * This method implements the accept method of <code>FileFilter</code> and only returns true for
     * files whose name ends in ".xml"
     */
    public boolean accept(File fileToCheck) {
        if (fileToCheck.getName().matches(".*[.]xml")) {
            return true;
        }
        return false;
    }

}
