package edu.drexel.info613.moviedatasite.db;

import junit.framework.TestCase;
import edu.drexel.info613.moviedatasite.db.*;

/**
 * This class is used to test the DBResult
 * 
 * @author Patrick Freestone, Batuhan Yukselen
 *
 */
public class DBResultTest extends TestCase {

    public DBResultTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests if error message is set correctly
     */
    public void testErrorReporting() {
        DBResult dbResult = new DBResult();
        dbResult.setError("Failed to execute");
        dbResult.setSuccess(false);
        assertEquals("Failed to execute", dbResult.getError());
        dbResult = null;
    }

    /*
     * Tests if error message is cleared up properly when success is set to true
     */
    public void testSuccessStatus() {
        DBResult dbResult = new DBResult();
        dbResult.setSuccess(true);

        assertEquals(true, dbResult.isSuccess());

        if (dbResult.isSuccess() == true) {
            assertEquals("", dbResult.getError());
        }

        dbResult = null;
    }
}