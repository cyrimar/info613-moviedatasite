package edu.drexel.info613.moviedatasite.servlet;

import edu.drexel.info613.moviedatasite.db.MovieDB;
import edu.drexel.info613.moviedatasite.domain.Director;
import edu.drexel.info613.moviedatasite.domain.Actor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: ReportingServlet
 * This class is called from movieSearch jsp and 
 * Responsible for taking the information provided by the user,
 * Changing it to a domain object, passing that domain object to 
 * the MovieDB to obtain the results, and generate html (Movie objects), 
 * to display output. doGet() and doPost() forward to processRequest().
 * @author Patrick Freestone, Batuhan Yukselen
 *
 */
public class ReportingServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

    static final long    serialVersionUID        = 1L;
    static final int     MAX_TEXT_SIZE           = 50;
    static final int     MIN_TEXT_SIZE           = 1;

    //use regular expression  to validate text inputs
    final String         regex                   = ".{" + MIN_TEXT_SIZE + "," + MAX_TEXT_SIZE + "}[a-zA-Z]";

    //instantiate database object
    MovieDB              database                = MovieDB.getInstance();
    LinkedList<String[]> outputList              = null;
    final String         HTML_STREAM_BEGIN       = "<html><head><title>Search Results</title></head>" + "<body><table bgcolor=\"#D8D8E8\" align=\"center\"><tr><th><h1>INFO 613 Movie Data Website<h1></th></tr></table><hr/><p><a href=\"Index.html\">Home Page</a></p><center><table border=3><tr><th> Movie Title </th><th> Movie Genre </th></tr>";
    final String         HTML_STREAM_END         = "</center></body></html>";
    final String         HTML_USER_ENTRY_INVALID = "One of your inputs is invalid. You must enter" + " at least " + MIN_TEXT_SIZE + " but no more than " + MAX_TEXT_SIZE + " characters "
                                                         + "and include only alphabetical characters";
    final String         HTML_NO_SEARCH_FOUND    = "No Search Results Found...";

    PrintWriter          out                     = null;

    /*
    * Empty Constructor
    */
    public ReportingServlet() {
        super();
    }

    /* 
     * Forwards requests and response to processRequest where business logic resides
     * @param HttpServletRequest request, HttpServletResponse response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //populate output list with data
            processRequest(request, response);
        } catch (IOException ex) {
            out.print("IO exception occured");
        }
    }

    /* 
     *  Forwards requests and response to processRequest where business logic resides
     * @param HttpServletRequest request, HttpServletResponse response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            //populate output list with data
            processRequest(request, response);
        } catch (IOException ex) {
            out.print("IO exception occurred");
        }

    }

    /*
     * Processes requests for both HTTP GET and POST methods.
     * Validates text inputs, determines chosen search criteria, 
     * populates outputList with DB results, generates HTML code and ultimately displays: 
     * a.table populated with movie genre and title info in case of valid user entries
     * b.warning message in case of no search result is found
     * c.error message and validation rules in case of invalid user entries 
     *  
     * @param HttpServletRequest request, HttpServletResponse response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //reset boolean to its original value for every page post
        boolean isEntryValid = false;

        //store user inputs into local variables 
        String firstName = request.getParameter("firstName").trim();
        String lastName = request.getParameter("lastName").trim();

        //validate user inputs
        if (isValid(firstName) && isValid(lastName)) {
            //set to true so as to avoid any error reporting
            isEntryValid = true;

            //if search criteria is director, use retrieveMoviesByDirector function from MovieDB
            if (request.getParameter("radios").equals("director")) {
                Director director = new Director();
                //Concatenate first name and last name and pass full_name
                //to director's name property
                String fullName = firstName + " " + lastName;
                director.setName(fullName);
                outputList = database.getMoviesByDirector(director);
                director = null;
            }

            //if search criteria is director, use retrieveMoviesByActor function from MovieDB
            else if (request.getParameter("radios").equals("actor")) {
                Actor actor = new Actor();
                outputList = new LinkedList<String[]>();
                actor.setFirstName(firstName);
                actor.setLastName(lastName);
                outputList = database.getMoviesByActor(actor);
                actor = null;
            }

        }
        //if there is data record returned, display results on table
        if (isEntryValid) {

            //use print writer to generate HTML code
            out = response.getWriter();

            //set the response page header info
            response.setContentType("text/html; charset=ISO-8859-1");

            //if there is a data record, generate table
            if (outputList.size() > 0) {
                //print header HTML tags
                out.print(HTML_STREAM_BEGIN);

                //populate data rows to HTML table
                for (String[] movieInfo : outputList) {
                    String dataRow = "<tr><td>" + movieInfo[0] + "</td><td>" + movieInfo[1] + "</td></tr>";
                    out.print(dataRow);
                }

                //print closing HTML tags
                out.print(HTML_STREAM_END);
            }
            //if there is no data record returned, notify user
            else {
                out.print(HTML_NO_SEARCH_FOUND);
            }
        } else {
            //print error message
            out.print(HTML_USER_ENTRY_INVALID);
        }
    }

    /*
    * Validates user inputs based on regular expression
    * regex= ".{x,y}[A-Za-z]} 
    *  x is minimum input size
    *  y is maximum input size
    *  [A-Za-z] only alphabetical chars
    * @param input
    * @param string match result
    */
    public boolean isValid(String input) {
        return input.matches(regex);
    }
}