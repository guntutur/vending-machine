package com.shortlyst.test.vendingmachine.controller;

import com.shortlyst.test.vendingmachine.domain.Goods;
import com.shortlyst.test.vendingmachine.domain.ShelveBox;
import com.shortlyst.test.vendingmachine.service.ShelveBoxService;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zer0, the Maverick Hunter
 * on 21/07/19.
 * Class: ShelveBoxController.java
 *
 * implement rule no 4 of vending.md
 * 4. State of whether or not the items can be purchased is as following
 *         - Item is out of stock and Item price > Inserted amount -> Cannot purchase [Sold out]
 *         - Item is out of stock and Item price ≦ Inserted amount -> Cannot purchase [Sold out]
 *         - Item is in stock and Item price > Inserted amount -> Cannot purchase [Display nothing]
 *         - Item is out of stock and Item price ≧ Inserted amount -> Can purchase [Can purchase]
 */
public class ShelveBoxController implements IInput {

    private ShelveBoxService shelveBoxService = new ShelveBoxService();
    private int selectedShelf;
    private int totalHoldAmount = 0;

    public ShelveBoxController init() {
        shelveBoxService.addToShelf("Canned coffee", 120, 3);
        shelveBoxService.addToShelf("Water PET bottle", 100, 0);
        shelveBoxService.addToShelf("Sport drinks", 150, 5);
        return this;
    }

    /**
     * Returns (without removing) the top product from the given shelf (or null, if shelf is empty)
     *
     * @return the top product or null, if shelf is empty
     **/
    public Goods getGoodsFromIndex(int index) throws IndexOutOfBoundsException {
        return shelveBoxService.getGoodsFromIndex(index);
    }

    /**
     * Returns the ShelveBox Object at given index
     *
     * @return the ShelveBox
     **/
    public ShelveBox getShelveBoxFromIndex(int index) throws IndexOutOfBoundsException {
        return shelveBoxService.getShelveBoxFromIndex(index);
    }

    public LinkedList<ShelveBox> getAvailableGoods() {
        return shelveBoxService.getAvailableGoods();
    }

    public boolean selectGoodsAttempt(List<ShelveBox> currentlyHoldShelve, int index, int currentCoin) {
        boolean proceed = true;
        int totalHoldPrice = 0;
        for (ShelveBox box : currentlyHoldShelve) {
            totalHoldPrice += box.getGoods().getPrice();
        }
        totalHoldPrice += getGoodsFromIndex(index).getPrice();
        if (totalHoldPrice > currentCoin) {
            proceed = false;
        }

        totalHoldAmount = totalHoldPrice;

        return proceed;
    }

    @Override
    public void cancel() {

    }

    // todo subtract quantity
    // sent selected to outlet
    @Override
    public void selectShelf(int index) {

    }

    @Override
    public void insertCoin(Double coin) {

    }

    public int getTotalHoldAmount() {
        return totalHoldAmount;
    }
}
