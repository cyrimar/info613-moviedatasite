package edu.drexel.info613.moviedatasite.db;

import edu.drexel.info613.moviedatasite.domain.*;
import edu.drexel.info613.moviedatasite.db.DBResult;
import java.util.*;
import java.sql.*;
import oracle.jdbc.driver.*;

/*
 * This class will be a singleton and provide access to the underlying Oracle DB. It will handle
 * data truncation (called from TruncateDBServlet), insertion (called from UploadDataServlet), and
 * reporting (called from ReportingServlet). 
 * @author Patrick Freestone, Batuhan Yukselen
 */
public class MovieDB {
    private static MovieDB           instance        = null;
    private final String      ORACLE_USERNAME = "by32";
    private final String      ORACLE_PASSWORD = "yuk12yuk";
    private Connection        conn            = null;
    private CallableStatement cs              = null;
    private ResultSet         rs              = null;
    private String            url             = null;
    private DBResult          dbResult        = new DBResult();

    private final String      INSERT_MOVIE    = "Movie Insertion";
    private final String      INSERT_DIRECTOR = "Director Insertion";
    private final String      INSERT_GENRE    = "Genre Insertion";
    private final String      INSERT_ACTOR    = " Actor Insertion";

    /**
     * Provide a global point of access to the object and ensure that 
     * only one instance of a class is created 
     * @return instance
     */
    public static MovieDB getInstance() {
        if (instance == null) {
            instance = new MovieDB();
        }

        return instance;
    }
    
    protected MovieDB() {
        // Exists only to defeat instantiation.
     }


    /**
     * Fetch domain object info into DB tables
     * 
     * @return  DBResult
     */
    public DBResult insertMovies(List<Movie> movies) {
        //keep track of sps and report if one of them fail
        String traceProc = null;

        //keep track of movie domain obj and report if one of sps fail
        String traceMovie = null;

        establishConnection();

        try {
            //Distribute each movie object Info into DB relations 
            for (int i = 0; i < movies.size(); i++) {
                //a single xml data fields are stored into movie object
                Movie movie = movies.get(i);

                traceMovie = movie.getTitle();
                
                /*******Insert Movie Title and Movie Year into Movie Tbl**********/

                traceProc = INSERT_MOVIE;

                cs = conn.prepareCall("{call insertMovie(?,?)}");

                // Set the value for the IN parameters
                cs.setString(1, movie.getTitle());
                cs.setInt(2,Integer.parseInt(movie.getYear()));

                //Execute and populate movie tbl
                cs.execute();

                /*******Insert Director Name into Director tbl***********/

                traceProc = INSERT_DIRECTOR;

                // iterate through director list and insert directorName into director table
                for (int j = 0; j < movie.getDirectors().size(); j++) {
                    cs = conn.prepareCall("{call insertDirector(?)}");

                    // Set the value for the IN parameter
                    cs.setString(1, movie.getDirectors().get(j).getName());

                    //Execute and populate director tbl
                    cs.execute();

                }

                /*******Insert Actor FirstName and LastName into Actor tbl***********/

                traceProc = INSERT_ACTOR;

                // iterate through actor list and insert firstName and lastName into actor table
                for (int k = 0; k < movie.getActors().size(); k++) {
                    cs = conn.prepareCall("{call insertActor(?,?)}");
                    // Set the value for the IN parameter
                    cs.setString(1, movie.getActors().get(k).getFirstName());
                    cs.setString(2, movie.getActors().get(k).getLastName());

                    //Execute and populate actor tbl
                    cs.execute();
                }

                /**************Insert GenreName into Genre tbl*********************/

                traceProc = INSERT_GENRE;

                for (int m = 0; m < movie.getGenres().size(); m++) {
                    cs = conn.prepareCall("{call insertGenre(?)}");

                    // Set the value for the IN parameters
                    cs.setString(1, movie.getGenres().get(m).getName());

                    //Execute and populate genre tbl
                    cs.execute();
                }

            } //end for

            //close statement and connection
            cs.close();
            conn.close();

            dbResult.setSuccess(true);

        } //end try

        catch (SQLException ex) {
            dbResult.setSuccess(false);
            dbResult.setError(traceProc + " Failed For " + traceMovie);
        }

        return dbResult;
    }

    /**
     * Retrieve titles and genres of all movies that were acted by a particular person
     * 
     * @param actor containing first name and last name attributes
     * @return resultList populated with result array objects
     */
    public LinkedList<String[]> getMoviesByActor(Actor actor) {

        //retrieveMovies functions returns this linked list
        LinkedList<String[]> resultList = new LinkedList<String[]>();

        establishConnection();

        try {
            cs = conn.prepareCall("{ ? = call retrieveMoviesByActor(?,?)}");

            // Register the type of the return value
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // Set the value for the IN parameters
            cs.setString(2, actor.getFirstName());
            cs.setString(3, actor.getLastName());

            //Execute and retrieve the result
            cs.execute();

            rs = (ResultSet)cs.getObject(1);
            
            //move the cursor to the first row
            rs.next();
            
            //print movie title and genre name
           /* while (rs.next()) 
            {
                System.out.println(rs.getString(1) + "\t" +
                               rs.getString(2) + "\t" );
            }*/
             
             if (!rs.getString(1).isEmpty()) {
               
                //result[0] = movieTitle
                //result[1] = movieGenre
                String result[] = new String[2];
                
                //read the values from the first row
                result[0] = rs.getString(1);
                result[1] = rs.getString(2);
                resultList.add(result);

                //iterate through other rows if exists
                while (rs.next() == true) {
                    result[0] = rs.getString(1);
                    result[1] = rs.getString(2);
                    resultList.add(result);
                }

                //clean-up array
                result = null;
            }
             

            //close resultset, statement, and connection
            rs.close();
            cs.close();
            conn.close();
            dbResult.setSuccess(true);

        }//end try

        catch (SQLException ex) {
            dbResult.setSuccess(false);
            dbResult.setError("Failed to retrieve movies by actor");
        }

        return resultList;
    }

    /**
     * Retrieve titles and genres of all movies that were directed by a particular person
     * 
     * @param director - domain object containing name attribute
     * @return resultList - linked list populated with result array
     */
    public LinkedList<String[]> getMoviesByDirector(Director director) {

        //retrieveMovies functions returns this linked list
        LinkedList<String[]> resultList = new LinkedList<String[]>();

        establishConnection();

        try {
            cs = conn.prepareCall("{? = call retrieveMoviesByDirector(?)}");

            // Register the type of the return value
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // Set the value for the IN parameter
            cs.setString(2, director.getName());

            //execute and retrieve the result
            cs.execute();
            
            //get RefCursor containing genre and movie title from DB
            rs = (ResultSet)cs.getObject(1);

            //move the cursor to the first row
            rs.next();
            
            //print movie title and genre name
            //for testing
            /*while (rs.next()) 
            {
                System.out.println(rs.getString(1) + "\t" +
                               rs.getString(2) + "\t" );
            }*/
            
            if (!rs.getString(1).isEmpty()) {

                //result[0] = movieTitle
                //result[1] = movieGenre
                String[] result = new String[2];
                
                //read the values from the first row
                result[0] = rs.getString(1);
                result[1] = rs.getString(2);
                resultList.add(result);

                //iterate through other rows if exists
                while (rs.next() == true) {
                    result[0] = rs.getString(1);
                    result[1] = rs.getString(2);
                    resultList.add(result);
                }

                //clean-up array
                result = null;
            } //end if

            //close resultset, statement, and connection
            rs.close();
            cs.close();
            conn.close();

            dbResult.setSuccess(true);
        } //end try

        catch (SQLException ex) {
            dbResult.setSuccess(false);
            dbResult.setError("Failed to retrieve movies by director");
        }

        return resultList;
    }

    /**
     * Executes a stored procedure and remove all existing data from DB
     * 
     * @return success/fail message object
     */
    public DBResult truncateDB() {
        establishConnection();

        try {
            cs = conn.prepareCall("{call truncateDB}");

            //execute and truncate data in DB
            cs.execute();

            //close statement and connection
            cs.close();
            conn.close();

            dbResult.setSuccess(true);

        } catch (SQLException ex) {
            dbResult.setSuccess(false);
            dbResult.setError("Failed to truncate DB");
        }

        return dbResult;
    }

    /**
     * Loads the JDBC driver and establishes a connection
     */
    private void establishConnection() {
        //construct the url;
        url = "jdbc:oracle:thin:@oracle.cis.drexel.edu:1521:orcl";

        //load the driver and connect to DB
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, ORACLE_USERNAME, ORACLE_PASSWORD);
            dbResult.setSuccess(true);

        } catch (ClassNotFoundException ex) {
            dbResult.setSuccess(false);
            dbResult.setError("Failed to find driver class: oracle.jdbc.driver.OracleDriver");
        } catch (SQLException ex) {
            dbResult.setSuccess(false);
            dbResult.setError("Failed to establish a connection to " + url);
        }
    }
}