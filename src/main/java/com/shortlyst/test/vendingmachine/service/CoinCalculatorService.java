package com.shortlyst.test.vendingmachine.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by zer0, the Maverick Hunter
 * on 21/07/19.
 * Class: CoinCalculatorService.java
 *
 * This Service Aims to calculate if a given list of coins can be processed to purchase goods and get the change
 * On success it divides up the coins into change and remainingCoins
 *
 * implement rule no 3 of vending.md
 * 3. Conditions to determine change possibility
 *         Change is impossible if the coins contained in the Vending Machine’s vault is in the below conditions:
 *         - 10 JPY coin: Less than 9 coins
 *         - 100 JPY coin: Less than 4 coins
 *         In case if vending machine running out of 10 JPY coin, then 50 JPY, 100 JPY, 500 JPY coins cannot be used.
 *         In case if vending machine running out of 100 JPY coin, then 500 JPY coins cannot be used.
 */
public class CoinCalculatorService {

    private final List<Integer> coins;
    private final ArrayList<Integer> change = new ArrayList<>();
    private int amount;
    private List<Integer> remainingCoins = new ArrayList<>();
    private boolean isChangeAvailable = false;
    private final int coin10limit = 9;
    private final int coin100limit = 4;
    private List<Integer> insertedCoin = new ArrayList<>();

    public CoinCalculatorService(Collection<Integer> coinsAvailable, int totalAmount) {
        amount = totalAmount;
        coins = new ArrayList<>(coinsAvailable);
        Collections.sort(coins);
        Collections.reverse(coins);
        calculate();
    }

    public CoinCalculatorService(Collection<Integer> coinsAvailable) {
        coins = new ArrayList<>(coinsAvailable);
        Collections.sort(coins);
        Collections.reverse(coins);
    }

    boolean isChangeAvailable() {
        return isChangeAvailable;
    }

    public ArrayList<Integer> getChange() {
        return change;
    }

    public List<Integer> getRemainingCoins() {
        return remainingCoins;
    }

    private Integer getRemainingCoins(int defaultDenomination) {
        return Collections.frequency(coins, defaultDenomination);
    }

    public String check10limit() {
        return getRemainingCoins(10) < coin10limit ? "No Change" : "Change";
    }

    public String check100limit() {
        return getRemainingCoins(100) < coin100limit ? "No Change" : "Change";
    }

    private void calculate() {
        remainingCoins = new ArrayList<>();
        int target = amount;

        // if limit reached for 10 coin, stop immediately
        if (getRemainingCoins(10) < coin10limit) {
            isChangeAvailable = false;
            return;
        }

        // if limit reached for 500 coin, stop immediately
        if (getRemainingCoins(100) < coin100limit) {
            isChangeAvailable = false;
            return;
        }

        // calculate change and remaining coin
        for (Integer coin : coins) {
            if (coin <= target) {
                change.add(coin);
                target -= coin;
            } else {
                remainingCoins.add(coin);
            }
        }

        // determine if change is available
        isChangeAvailable = (target == 0);
        if (!isChangeAvailable) {
            remainingCoins = coins;
        }
    }

    public List<Integer> getInsertedCoin() {
        return insertedCoin;
    }

    public void setInsertedCoin(List<Integer> insertedCoin) {
        this.insertedCoin = insertedCoin;
    }
}
