package com.shortlyst.test.vendingmachine;

import com.shortlyst.test.vendingmachine.controller.ShelveBoxController;
import com.shortlyst.test.vendingmachine.service.CoinCalculatorService;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by zer0, the Maverick Hunter
 * on 22/07/19.
 * Class: CommandTest.java
 */
public class CommandTest {

    private App app;
    private static final Collection<Integer> ACCEPTED_DENOMINATION_COIN = Arrays.asList(10, 50, 100, 500);
    private static final Collection<Integer> COIN_10_LIMIT = Arrays.asList(
            10, 10, 10, 10, 10, 10, 10, 10,
            100, 100, 100, 100, 100, 100, 100, 100, 100, 100
    );
    private static final Collection<Integer> COIN_100_LIMIT = Arrays.asList(
            10, 10, 10, 10, 10, 10, 10, 10, 10,
            100, 100, 100
    );

    private static final String denominationNotValid = "Denomination is not acceptable, valid denomination are : " + String.join(" ", ACCEPTED_DENOMINATION_COIN.toString());
    private static final String coinLimitReached = "Denomination is not acceptable, coin stock limit reached";
    private static final String outOfStock = "Cannot select item, out of stock";
    private static final String insufficientCoint = "Coin insufficient, try insert more";

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {
        app = null;
    }

    @Test
    public void coinAccepted() {
        app = new App();
        Assert.assertThat(app.processCommand("1 10"), CoreMatchers.not(denominationNotValid));
    }

    @Test
    public void coinNotAccepted() {
        app = new App();
        Assert.assertThat(app.processCommand("1 30"), CoreMatchers.containsString(denominationNotValid));
    }

    @Test
    public void coin10ReachLimitThenExcept10coinIsNotPossible() {

        app = new App(
                new ShelveBoxController().init(),
                new CoinCalculatorService(COIN_10_LIMIT)
        );

        Assert.assertThat(app.processCommand("1 50"), CoreMatchers.containsString(coinLimitReached));

        app = new App(
                new ShelveBoxController().init(),
                new CoinCalculatorService(COIN_10_LIMIT)
        );

        Assert.assertThat(app.processCommand("1 10"), CoreMatchers.not(coinLimitReached));
    }

    @Test
    public void coin100ReachLimitThenIf500CoinSelectedIsNotPossible() {

        app = new App(
                new ShelveBoxController().init(),
                new CoinCalculatorService(COIN_100_LIMIT)
        );


        Assert.assertThat(app.processCommand("1 500"), CoreMatchers.containsString(coinLimitReached));
    }

    @Test
    public void insufficientCoinOnAvailableGoodsSelection() {
        app = new App(
                new ShelveBoxController().init(),
                new CoinCalculatorService(COIN_100_LIMIT)
        );

        app.processCommand("1 100");

        Assert.assertThat(app.processCommand("2 1"), CoreMatchers.containsString(insufficientCoint));
    }

    @Test
    public void selectOutOfStockGoods() {
        app = new App(
                new ShelveBoxController().init(),
                new CoinCalculatorService(COIN_100_LIMIT)
        );

        app.processCommand("1 100");
        app.processCommand("1 100");

        Assert.assertThat(app.processCommand("2 2"), CoreMatchers.containsString(outOfStock));
    }

}
