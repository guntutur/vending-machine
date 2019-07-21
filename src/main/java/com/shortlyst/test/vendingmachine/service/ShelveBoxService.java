package com.shortlyst.test.vendingmachine.service;

import com.shortlyst.test.vendingmachine.domain.Goods;
import com.shortlyst.test.vendingmachine.domain.ShelveBox;

import java.util.*;

/**
 * Created by zer0, the Maverick Hunter
 * on 21/07/19.
 * Class: ShelveBox.java
 *
 * Acts as a Container of all Goods
 */
public class ShelveBoxService {

    private final LinkedList<ShelveBox> shelves = new LinkedList<>();

    public ShelveBoxService addToShelf(String name, int price, int quantity) {
        ShelveBox newShelf = new ShelveBox(new Goods(name, price), quantity);
        shelves.add(newShelf);
        return this;
    }

    /**
     * @return - checks if the shelves are completely empty
     */
    public Boolean isEmpty() {
        return shelves.isEmpty();
    }

    public Boolean isEmptyAt(int index) throws IndexOutOfBoundsException {
        return shelves.get(index) != null;
    }

    /**
     * Permanently removes a product from the given shelf by subtract the quantity and returns it (or null, if shelf is empty)
     *
     * @return the top product or null, if shelf is empty
     */
    public Goods releaseGoodsFromIndex(int index) throws IndexOutOfBoundsException {
        Integer goodsQuantity = shelves.get(index).getQuantity();
        if (goodsQuantity == 0) {
            return null;
        }

        shelves.get(index).setQuantity(goodsQuantity - 1);
        return shelves.get(index).getGoods();
    }

    public Integer getPriceAtIndex(int index) throws IndexOutOfBoundsException {
        Goods isProduct = getGoodsFromIndex(index);
        if (isProduct != null) {
            return isProduct.getPrice();
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Returns (without removing) the top product from the given shelf (or null, if shelf is empty)
     *
     * @return the top product or null, if shelf is empty
     **/
    public Goods getGoodsFromIndex(int index) throws IndexOutOfBoundsException {
        return shelves.get(index).getGoods();
    }

    /**
     * Returns the ShelveBox Object at given index
     *
     * @return the ShelveBox
     **/
    public ShelveBox getShelveBoxFromIndex(int index) throws IndexOutOfBoundsException {
        return shelves.get(index);
    }

    public LinkedList<ShelveBox> getAvailableGoods() {
        return shelves;
    }

}
