package com.shortlyst.test.vendingmachine.domain;

/**
 * Created by zer0, the Maverick Hunter
 * on 21/07/19.
 * Class: Goods.java
 *
 * Goods represents a purchasable good with a larger than zero price and a name
 */
public class Goods {

    private int price = 1;
    private String name = "";

    Goods(String name) {
        setName(name);
    }

    /**
     * @param name - product name, currently serves as an identifier as well
     * @param price - larger than zero price of the product
     */
    public Goods(String name, int price) {
        setName(name);
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    void setPrice(int price) {
        assert (0 < price);
        this.price = price;
    }


}
