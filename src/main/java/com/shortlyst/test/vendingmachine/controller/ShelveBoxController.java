package com.shortlyst.test.vendingmachine.controller;

import com.shortlyst.test.vendingmachine.service.ShelveBoxService;

/**
 * Created by zer0, the Maverick Hunter
 * on 21/07/19.
 * Class: ShelveBoxController.java
 */
public class ShelveBoxController implements IInput {

    private final ShelveBoxService shelveBox;
    private int selectedShelf;

    public ShelveBoxController(ShelveBoxService shelveBox, int selectedShelf) {
        this.shelveBox = shelveBox;
        this.selectedShelf = selectedShelf;
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
