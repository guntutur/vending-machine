package com.shortlyst.test.vendingmachine.controller;

import com.shortlyst.test.vendingmachine.domain.Goods;
import com.shortlyst.test.vendingmachine.domain.ShelveBox;
import com.shortlyst.test.vendingmachine.service.ShelveBoxService;

import java.util.LinkedList;

/**
 * Created by zer0, the Maverick Hunter
 * on 21/07/19.
 * Class: ShelveBoxController.java
 */
public class ShelveBoxController implements IInput {

    private ShelveBoxService shelveBoxService = new ShelveBoxService();
    private int selectedShelf;

//    public ShelveBoxController(ShelveBoxService shelveBox, int selectedShelf) {
//        this.shelveBoxService = shelveBox;
//        this.selectedShelf = selectedShelf;
//    }

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

    @Override
    public void cancel() {

    }

    @Override
    public void selectShelf(int index) {

    }

    @Override
    public void insertCoin(Double coin) {

    }
}
