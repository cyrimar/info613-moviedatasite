package edu.drexel.info613.moviedatasite.db;

import edu.drexel.info613.moviedatasite.db.*;
import edu.drexel.info613.moviedatasite.domain.*;
import junit.framework.TestCase;
import java.util.*;

/**
 * This class is used to test the MovieDB
 * 
 * @author Patrick Freestone, Batuhan Yukselen
 *
 */
public class MovieDBTest extends TestCase {
    
    Movie movie=null;
    Actor actor1 = null, actor2 = null;
    Director director1 = null, director2 = null;
    Genre genre1 = null, genre2 = null;
        
    LinkedList<Actor> actors = null;
    LinkedList<Director> directors = null;
    LinkedList<Genre> genres = null;
    LinkedList<Movie> movies = null;

    public MovieDBTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
        
        //create dummy objects and populate them with data
        movie = new Movie();
        actor1 = new Actor();
        actor2 = new Actor();
        director1 = new Director();
        director2 = new Director();

        genre1 = new Genre();
        genre2 = new Genre();
        
        movies = new LinkedList<Movie>();
        actors = new LinkedList<Actor>();
        directors = new LinkedList<Director>();
        genres = new LinkedList<Genre>();
        
        movie.setTitle("Ocean's Eleven");
        movie.setYear("2001");
        actor1.setFirstName("Julia");
        actor1.setLastName("Roberts");
        actor2.setFirstName("Jennifer");
        actor2.setLastName("Aniston");
        director1.setName("Steven Spielberg");
        director2.setName("Robert Altman");
        genre1.setName("horror");
        genre2.setName("romance");
        
        //add domain object into descendant-collection
        actors.add(actor1);
        actors.add(actor2);
        directors.add(director1);
        directors.add(director2);
        genres.add(genre1);
        genres.add(genre2);
        
        //add descendant-collection into child-collection
        movie.setActors(actors);
        movie.setDirectors(directors);
        movie.setGenres(genres);
        
        //add child-collection into parent-collection
        movies.add(movie);
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Checks to see whether the references are identical
     */
    public void testSingleton()
    {
        MovieDB ins1 = MovieDB.getInstance();
        MovieDB ins2 = MovieDB.getInstance(); 
        assertEquals(true, ins1 == ins2);
    }
    
    /**
     * Tests truncateDB function and ensures that there's no error reported.
     */
    public void testTruncateDB() 
    { 
        MovieDB instance = MovieDB.getInstance();
        DBResult d =instance.truncateDB();
        assertTrue(d.isSuccess());
        assertEquals("",d.getError());
        instance = null;
        
    }
    
    /**
     * Tests getMoviesByActor function and ensures that all genre and titles 
     * associated with a particular actor are returned properly.
     */
    public void testGetMoviesByActor() 
    { 
        MovieDB instance = MovieDB.getInstance();
        instance.truncateDB();
        instance.insertMovies(movies);
        
        //test with direct object containing data
        LinkedList<String[]> result = instance.getMoviesByActor(actor1);
         
        if (result.size()>0)
        {            
            for (int i=0; i< result.size();i++)
            {
                assertEquals(movie.getTitle(), result.get(i)[0]);
                assertEquals(movie.getGenres().get(i).getName(), result.get(i)[1]);
            }
        }
           
        //test with actor object not containing data
        Actor actor3 = new Actor();
        result = instance.getMoviesByActor(actor3);
        assertEquals(0,result.size());
        
        instance = null;
        
    }
    
    /**
     * Tests getMoviesByDirector function and ensures that all genre and titles 
     * associated with a particular director are returned properly.
     */
    public void testGetMoviesByDirector() 
    { 
        MovieDB instance = MovieDB.getInstance();
        instance.truncateDB();
        instance.insertMovies(movies);
        
        //test with direct object containing data
        LinkedList<String[]> result = instance.getMoviesByDirector(director1);
         
        if (result.size()>0)
        {
            for (int i=0; i< result.size();i++)
            {
                assertEquals(movie.getTitle(), result.get(i)[0]);
                //linked list adds element to the tail, so the first element of array
                //matches up the last element of linked list 
                assertEquals(movie.getGenres().get(i).getName(), result.get(i)[1]);
            }
        }
        
        
        //test with director object not containing data
        Director director3 = new Director();
        result = instance.getMoviesByDirector(director3);
        assertEquals(0,result.size());
        
        instance = null;
        
    }  
    
    
    /**
     * Tests insertMovies function and ensures that all data is fetched into DB properly.
     */
    public void testInsertMovies() 
    { 
        MovieDB instance = MovieDB.getInstance();
        instance.truncateDB();
        DBResult d = instance.insertMovies(movies);
        assertTrue(d.isSuccess());
        assertEquals("",d.getError());
        System.out.println(d.getError());
        instance = null;
    }
}