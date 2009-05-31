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
    
    Movie movie1=null, movie2=null, movie3 = null;
    Actor actor1 = null, actor2 = null;
    Actor actor3 = null, actor4 = null;
    Actor actor5 = null, actor6 = null;
    Director director1 = null, director2 = null;
    Director director3 = null, director4 = null;
    Director director5 = null, director6 = null;
    Genre genre1 = null, genre2 = null;
    Genre genre3 = null, genre4 = null;
    Genre genre5 = null, genre6 = null;
        
    LinkedList<Actor> actors1 = null, actors2 = null, actors3 = null;
    LinkedList<Director> directors1 = null, directors2 = null, directors3 = null;
    LinkedList<Genre> genres1 = null, genres2 = null, genres3 = null;
    LinkedList<Movie> movies;

    public MovieDBTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
        
        //create dummy objects and populate them with data
        movie1 = new Movie();
        movie2 = new Movie();
        movie3 = new Movie();
        
        actor1 = new Actor();
        actor2 = new Actor();
        actor3 = new Actor();
        actor4 = new Actor();
        actor5 = new Actor();
        actor6 = new Actor();

        director1 = new Director();
        director2 = new Director();
        director3 = new Director();
        director4 = new Director();
        director5 = new Director();
        director6 = new Director();
        
        genre1 = new Genre();
        genre2 = new Genre();
        genre3 = new Genre();
        genre4 = new Genre();
        genre5 = new Genre();
        genre6 = new Genre();
        
        movies = new LinkedList<Movie>();
        actors1 = new LinkedList<Actor>();
        actors2 = new LinkedList<Actor>();
        actors3 = new LinkedList<Actor>();
        directors1 = new LinkedList<Director>();
        directors2 = new LinkedList<Director>();
        directors3 = new LinkedList<Director>();
        genres1 = new LinkedList<Genre>();       
        genres2 = new LinkedList<Genre>();
        genres3 = new LinkedList<Genre>();
        
        movie1.setTitle("Ocean's Eleven");
        movie1.setYear("2001");
        actor1.setFirstName("Julia");
        actor1.setLastName("Roberts");
        actor2.setFirstName("Jennifer");
        actor2.setLastName("Aniston");
        director1.setName("Steven Spielberg");
        director2.setName("Robert Altman");
        genre1.setName("horror");
        genre2.setName("romance");
        
        movie2.setTitle("Batu");
        movie2.setYear("2005");
        actor3.setFirstName("Ju");
        actor3.setLastName("Ro");
        actor4.setFirstName("Jen");
        actor4.setLastName("An");
        director3.setName("Stevenede");
        director4.setName("Robert Aewe");
        genre3.setName("horror");
        genre4.setName("more");
        
        movie3.setTitle("FightClub");
        movie3.setYear("2009");
        actor5.setFirstName("Batuhan");
        actor5.setLastName("Yukselen");
        actor6.setFirstName("Patrick");
        actor6.setLastName("Freestone");
        director5.setName("Christopher Yang1");
        director6.setName("Christopher Yang2");
        genre5.setName("horror");
        genre6.setName("comedy");
        
        //add domain object into descendant-collection
        actors1.add(actor1);
        actors1.add(actor2);
        directors1.add(director1);
        directors1.add(director2);
        genres1.add(genre1);
        genres1.add(genre2);
        
        actors2.add(actor1);
        actors2.add(actor2);
        directors2.add(director1);
        directors2.add(director2);
        genres2.add(genre1);
        genres2.add(genre2);
        
        actors3.add(actor1);
        actors3.add(actor2);
        directors3.add(director1);
        directors3.add(director2);
        genres3.add(genre1);
        genres3.add(genre2);
        
        //add descendant-collection into child-collection
        movie1.setActors(actors1);
        movie1.setDirectors(directors1);
        movie1.setGenres(genres1);

        movie2.setActors(actors2);
        movie2.setDirectors(directors2);
        movie2.setGenres(genres2);
        
        movie3.setActors(actors2);
        movie3.setDirectors(directors2);
        movie3.setGenres(genres2);
                
        //add child-collection into parent-collection
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);
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
                assertEquals(movie1.getTitle(), result.get(i)[0]);
                assertEquals(movie1.getGenres().get(i).getName(), result.get(i)[1]);
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
                assertEquals(movie1.getTitle(), result.get(i)[0]);
                //linked list adds element to the tail, so the first element of array
                //matches up the last element of linked list 
                assertEquals(movie1.getGenres().get(i).getName(), result.get(i)[1]);
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