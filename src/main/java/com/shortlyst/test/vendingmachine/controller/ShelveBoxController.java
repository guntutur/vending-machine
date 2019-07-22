package com.shortlyst.test.vendingmachine.controller;

import com.shortlyst.test.vendingmachine.domain.Goods;
import com.shortlyst.test.vendingmachine.domain.ShelveBox;
import com.shortlyst.test.vendingmachine.service.ShelveBoxService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zer0, the Maverick Hunter
 * on 21/07/19.
 * Class: ShelveBoxController.java
 */
public class ShelveBoxController implements IInput {

    private ShelveBoxService shelveBoxService = new ShelveBoxService();
    private int totalCurrentHoldAmount = 0;
    private List<Integer> insertedCoin = new ArrayList<>();
    private List<Goods> selectedGoods = new ArrayList<>();

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

    public int getTotalHoldAmount() {
        int totalHoldPrice = 0;
        for (Goods goods : selectedGoods) {
            totalHoldPrice += goods.getPrice();
        }
        return sumInsertedCoin() - totalHoldPrice;
    }

    public int getTotalCurrentHoldAmount() {
        return sumInsertedCoin() - getTotalHoldAmount();
    }

    private Integer sumInsertedCoin() {
        return insertedCoin.stream().mapToInt(a -> a).sum();
    }

    public boolean canProceed() {
        return sumInsertedCoin() >= getTotalCurrentHoldAmount();
    }

    public void removeFromContainer() {
        selectedGoods.remove(selectedGoods.size()-1);
    }

    public List<Goods> getSelectedGoods() {
        return selectedGoods;
    }

    public void setSelectedGoods(List<Goods> selectedGoods) {
        this.selectedGoods = selectedGoods;
    }

    public List<Integer> getInsertedCoin() {
        return insertedCoin;
    }

    public void setInsertedCoin(List<Integer> insertedCoin) {
        this.insertedCoin = insertedCoin;
    }

    public void releaseIfProceed(int index) {
        shelveBoxService.releaseGoodsFromIndex(index);
    }

    @Override
    public void reset() {
        totalCurrentHoldAmount = 0;
        insertedCoin = new ArrayList<>();
        selectedGoods = new ArrayList<>();
    }

    @Override
    public void selectShelf(int index) {
//        selectedGoods.add(shelveBoxService.releaseGoodsFromIndex(index));
        selectedGoods.add(getGoodsFromIndex(index));
        selectedGoods.forEach(goods -> totalCurrentHoldAmount += goods.getPrice());
    }

    @Override
    public void insertCoin(int coin) {
        insertedCoin.add(coin);
    }

    public void test() {
        System.out.println("anuan");
    }
}
