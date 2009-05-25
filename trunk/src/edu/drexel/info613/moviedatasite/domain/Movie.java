package edu.drexel.info613.moviedatasite.domain;

import java.util.ArrayList;
import java.util.List;

/*
 * This class is a domain object for the movie data site and represents a movie.
 * @author Patrick Freestone, Batuhan Yukselen
 */
public class Movie {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private String              title;
    private String              year;
    private List<Director>      directors      = new ArrayList<Director>();
    private List<Genre>         genres         = new ArrayList<Genre>();
    private List<Actor>         actors         = new ArrayList<Actor>();

    /**
     * Empty Constructor. Initializes title and year to empty strings.
     */
    public Movie() {
        this.title = "";
        this.year = "";
    }

    /**
     * Constructor. Sets the title and year to the passed parameters. The directors, genres, and actors lists are 
     * initialized to empty.
     * 
     * @param title the movie's title
     * @param year  the movie's year
     */
    public Movie(String title, String year) {
        this.title = title;
        this.year = year;
    }

    /**
     * Constructor. Sets the title, year, directors, genres, and actors to the values passed in.
     * 
     * @param title     the movie's title
     * @param year      the movie's year
     * @param directors a <code>List</code> of the movie's directors
     * @param genres    a <code>List</code> of the movie's genres
     * @param actors    a <code>List</code> of the movie's actors
     */
    public Movie(String title, String year, List<Director> directors, List<Genre> genres, List<Actor> actors) {
        this.title = title;
        this.year = year;
        this.directors.addAll(directors);
        this.genres.addAll(genres);
        this.actors.addAll(actors);
    }

    /**
     * Accessor method for title attribute
     * 
     * @return  the movie's title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Mutator method for title attribute
     * 
     * @param title     the movie's title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Accessor method for year attribute
     * 
     * @return  the movie's year
     */
    public String getYear() {
        return this.year;
    }

    /**
     * Mutator method for year attribute
     * 
     * @param year      the movie's year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Accessor method for directors attribute
     * 
     * @return  the movie's directors
     */
    public List<Director> getDirectors() {
        return this.directors;
    }

    /**
     * Mutator method for directors attribute
     * 
     * @param directors     the movie's directors
     */
    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    /**
     * Accessor method for genres attribute
     * 
     * @return  the movie's genres
     */
    public List<Genre> getGenres() {
        return this.genres;
    }

    /**
     * Mutator method for genres attribute
     * 
     * @param genres    the movie's genres
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /**
     * Accessor method for actors attribute
     * 
     * @return  the movie's actors
     */
    public List<Actor> getActors() {
        return this.actors;
    }

    /**
     * Mutator method for actors attribute
     * 
     * @param actors    the movie's actors
     */
    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    /**
     * Convenience method for representing the Movie object as a string
     */
    public String toString() {
        String ret = "***************" + LINE_SEPARATOR;
        ret = ret + "Title: " + this.title + LINE_SEPARATOR;
        ret = ret + "Year: " + this.year + LINE_SEPARATOR;
        ret = ret + "Directors: " + LINE_SEPARATOR;
        for (Director director : this.directors) {
            ret = ret + "\t" + director.getName() + LINE_SEPARATOR;
        }
        ret = ret + "Genres: " + LINE_SEPARATOR;
        for (Genre genre : this.genres) {
            ret = ret + "\t" + genre.getName() + LINE_SEPARATOR;
        }
        ret = ret + "Actors: " + LINE_SEPARATOR;
        for (Actor actor : this.actors) {
            ret = ret + "\t" + actor.getFirstName() + " " + actor.getLastName() + LINE_SEPARATOR;
        }
        return ret;
    }
}
