package edu.drexel.info613.moviedatasite.xml;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for edu.drexel.info613.moviedatasite.xml");
        //$JUnit-BEGIN$
        suite.addTestSuite(MovieDataXMLParserTest.class);
        //$JUnit-END$
        return suite;
    }

}
