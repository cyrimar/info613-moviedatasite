package edu.drexel.info613.moviedatasite.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import edu.drexel.info613.moviedatasite.db.DBResult;
import edu.drexel.info613.moviedatasite.db.MovieDB;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TruncateDBServlet extends HttpServlet {

    private static final long serialVersionUID = 1;
    
    private static final String HTML_STREAM_BEGIN = "<html><head><title>Truncate DB Results</title></head><body><p><a href=\"Index.html\">Home Page</a></p>";
    private static final String HTML_STREAM_END   = "</body></html>";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBResult truncateResult = MovieDB.getInstance().truncateDB();
        writeTruncateStatusPage(response, truncateResult);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }
    
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
