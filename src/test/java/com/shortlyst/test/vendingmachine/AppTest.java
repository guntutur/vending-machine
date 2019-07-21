package com.shortlyst.test.vendingmachine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppTest {

    private App app;

    @Before
    public void setUp() throws Exception {

        app = new App();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void main() {

        app.processCommand("1 500");
        app.processCommand("1 500");
        app.processCommand("1 500");
//        assertEquals(Long.valueOf(app.insertedCoin.stream().mapToInt(a -> a).sum()), Long.valueOf(1500));

    }
}
