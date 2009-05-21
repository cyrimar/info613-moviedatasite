package edu.drexel.info613.moviedatasite.domain;

/*
 * This class is a domain object for the movie data site and represents a movie director.
 * @author Patrick Freestone, Batuhan Yukselen
 */
public class Director {

    private String name;

    /**
     * Empty Constructor. Defaults name to empty String.
     */
    public Director() {
        this.name = "";
    }

    /**
     * Constructor. Sets director name to parameter value.
     * 
     * @param name  value to set for the name of the director
     */
    public Director(String name) {
        this.name = name;
    }

    /**
     * Mutator for name attribute.
     * 
     * @param name  value to set for the name of the director
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accessor for the name attribute
     * 
     * @return  name of the director
     */
    public String getName() {
        return this.name;
    }
}
