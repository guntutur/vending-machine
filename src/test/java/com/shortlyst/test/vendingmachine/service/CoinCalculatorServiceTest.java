package com.shortlyst.test.vendingmachine.service;

import org.junit.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class CoinCalculatorServiceTest {

    private static final Collection<Integer> DEFAULT_ACCEPTED_DENOMINATION_COIN = Arrays.asList(10, 50, 100, 500);

    private static final Collection<Integer> AVAILABLE_CHANGE_COIN_STOCK = Arrays.asList(
            10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
            100, 100, 100, 100, 100
    );

    private static final Collection<Integer> UNAVAILABLE_CHANGE_COIN_STOCK = Arrays.asList(
            10, 10, 10, 10, 10, 10, 10, 10,
            100, 100, 100
    );

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void coinIsAcceptable() {
        Integer coinIs = 50;
        Assert.assertTrue(DEFAULT_ACCEPTED_DENOMINATION_COIN.contains(coinIs));
    }

    @Test
    public void coinIsUnacceptable() {
        Integer coinIs = 30;
        Assert.assertFalse(DEFAULT_ACCEPTED_DENOMINATION_COIN.contains(coinIs));
    }

    @Test
    public void changeAvailable() {
        Stream.of(10, 50, 100, 500).forEach(d -> {
            CoinCalculatorService counter = new CoinCalculatorService(AVAILABLE_CHANGE_COIN_STOCK, d);
            Assert.assertTrue(counter.isChangeAvailable());
        });
    }

    @Test
    public void changeNotAvailable() {
        DEFAULT_ACCEPTED_DENOMINATION_COIN.forEach(d -> {
            CoinCalculatorService counter = new CoinCalculatorService(UNAVAILABLE_CHANGE_COIN_STOCK, d);
            Assert.assertFalse(counter.isChangeAvailable());
        });
    }
}
