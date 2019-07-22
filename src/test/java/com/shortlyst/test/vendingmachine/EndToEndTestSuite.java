package com.shortlyst.test.vendingmachine;

import com.shortlyst.test.vendingmachine.controller.ShelveBoxController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by zer0, the Maverick Hunter
 * on 22/07/19.
 * Class: EndToEndTestSuite.java
 */
public class EndToEndTestSuite {

    private App app;
    private static ShelveBoxController shelveBoxController;

    @Before
    public void setUp() throws Exception {
        app = new App();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void EndToEndTest() {
        String status10coin = "----------------------------------\n" +
                "[Input Amount]\n" +
                "\t10 JPY\n" +
                "[Change]\n" +
                "\t100 JPY Change\n" +
                "\t10 JPY Change\n" +
                "[Return Gate]\n" +
                "\tEmpty\n" +
                "[Items for sale]\n" +
                "\t1. Canned coffee 120 JPY \n" +
                "\u001B[31m\t2. Water PET bottle 100 JPY Out of Stock\u001B[0m\n" +
                "\t3. Sport drinks 150 JPY \n" +
                "[Outlet]\n" +
                "\tEmpty\n" +
                "----------------------------------";
        assertEquals(app.processCommand("1 10"), status10coin);
    }

}
