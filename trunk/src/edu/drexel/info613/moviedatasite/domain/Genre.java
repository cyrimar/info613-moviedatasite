package edu.drexel.info613.moviedatasite.domain;

/*
 * This class is a domain object for the movie data site and represents a movie genre.
 * @author Patrick Freestone, Batuhan Yukselen
 */
public class Genre {

    private String name;

    /**
     * Empty Constructor. Defaults name to empty String.
     */
    public Genre() {
        this.name = "";
    }

    /**
     * Constructor. Sets genre name to parameter value.
     * 
     * @param name  value to set for the name of the genre
     */
    public Genre(String name) {
        this.name = name;
    }

    /**
     * Mutator for name attribute.
     * 
     * @param name  value to set for the name of the genre
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accessor for the name attribute
     * 
     * @return  name of the genre
     */
    public String getName() {
        return this.name;
    }

}
