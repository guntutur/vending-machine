package com.shortlyst.test.vendingmachine.controller;

/**
 * Created by zer0, the Maverick Hunter
 * on 21/07/19.
 * Class: IInput.java
 */
public interface IInput {

    void reset();

    void selectShelf(int index);

    void insertCoin(int coin);

}
