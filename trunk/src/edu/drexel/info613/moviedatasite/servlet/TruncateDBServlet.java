package edu.drexel.info613.moviedatasite.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import edu.drexel.info613.moviedatasite.db.DBResult;
import edu.drexel.info613.moviedatasite.db.MovieDB;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * This servlet is responsible for handling requests to truncate the database - a.k.a. delete the
 * data from the database tables.
 * @author Patrick Freestone, Batuhan Yukselen
 */
public class TruncateDBServlet extends HttpServlet {

    private static final long   serialVersionUID  = 1;

    private static final String HTML_STREAM_BEGIN = "<html><head><title>Truncate DB Results</title></head><body><table bgcolor=\"#D8D8E8\" align=\"center\"><tr><th><h1>INFO 613 Movie Data Website<h1></th></tr></table><hr/><p><a href=\"Index.html\">Home Page</a></p>";
    private static final String HTML_STREAM_END   = "</body></html>";

    /**
     * This is the main method where the behavior is stored. This method basically calls as stored 
     * procedure in the database that cleans out the tables and then passes the result along to the
     * <code>writeTruncateStatusPage</code> method which prints out a results HTML page.
     * 
     * @param request   HTTP request sent to the servlet
     * @param response  HTTP response to be sent back to the requester
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBResult truncateResult = MovieDB.getInstance().truncateDB();
        writeTruncateStatusPage(response, truncateResult);
    }

    /**
     * Method for handling form using the "Get" method. All requests are forwarded to the processRequest
     * method.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    /**
     * Method for handling form using the "Post" method. All requests are forwarded to the processRequest
     * method.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    /**
     * Method responsible for writing the DB Truncation Result page.
     * 
     * @param response          HTTP response to which to write web page
     * @param truncateResult    result of DB truncation to include in response
     */
    private void writeTruncateStatusPage(HttpServletResponse response, DBResult truncateResult) {
        try {
            PrintWriter out = response.getWriter();
            out.print(HTML_STREAM_BEGIN);
            if (truncateResult.isSuccess()) {
                out.print("<p>Truncation Successful</p>");
            } else {
                out.print("<p>Truncation Failed with error: </p>");
                out.print("<p>" + truncateResult.getError() + "</p>");
            }
            out.print(HTML_STREAM_END);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
