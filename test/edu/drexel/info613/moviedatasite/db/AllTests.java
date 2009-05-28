package edu.drexel.info613.moviedatasite.db;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for edu.drexel.info613.moviedatasite.db");
        //$JUnit-BEGIN$
        suite.addTestSuite(DBResultTest.class);
        suite.addTestSuite(MovieDBTest.class);
        //$JUnit-END$
        return suite;
    }

}
