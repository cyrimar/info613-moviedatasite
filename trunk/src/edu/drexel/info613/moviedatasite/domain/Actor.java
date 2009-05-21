package edu.drexel.info613.moviedatasite.domain;

/*
 * This class is a domain object for the movie data site and represents an actor.
 * @author Patrick Freestone, Batuhan Yukselen
 */
public class Actor {

    private String firstName;
    private String lastName;

    /**
     * Empty Constructor. Defaults firstName and lastName to empty String.
     */
    public Actor() {
        this.firstName = "";
        this.lastName = "";
    }

    /**
     * Constructor. Sets firstName and lastName to parameter values.
     * 
     * @param firstName actor's first name
     * @param lastName  actor's last name
     */
    public Actor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Accessor method for firstName attribute
     * 
     * @return  actor's first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Mutator method for firstName attribute
     * 
     * @param firstName actor's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Accessor method for lastName attribute
     * 
     * @return  actor's last name
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Mutator method for lastName attribute
     * 
     * @param lastName actor's first name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
