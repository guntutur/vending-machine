package com.shortlyst.test.vendingmachine.domain;

/**
 * Created by zer0, the Maverick Hunter
 * on 21/07/19.
 * Class: ShelveBox.java
 */
public class ShelveBox {

    private Goods goods;
    private Integer quantity;

    public ShelveBox(Goods goods, Integer quantity) {
        this.goods = goods;
        this.quantity = quantity;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getStatus(Integer insertedCoin) {
        int status = 0;
        if (quantity == 0) status = 1;
        if (insertedCoin > goods.getPrice()) status = 2;
        return status;
    }
}
