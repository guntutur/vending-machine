package com.shortlyst.test.vendingmachine;

import com.shortlyst.test.vendingmachine.controller.ShelveBoxController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppTest {

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
    public void main() {
        app.processCommand("1 500");
//        shelveBoxController.test();
//        shelveBoxController.insertCoin(500);
//        shelveBoxController.insertCoin(500);
//        shelveBoxController.insertCoin(500);
//        assertEquals(shelveBoxController.getTotalHoldAmount(), 1500);
    }
}
