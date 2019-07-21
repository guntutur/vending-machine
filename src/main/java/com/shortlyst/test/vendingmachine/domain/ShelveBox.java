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
        if (quantity == 0) return 1;
        if (insertedCoin > goods.getPrice()) return 2;
        return 0;
    }

    public String getStatusText(Integer insertedCoin) {
        if (quantity == 0) return "Out of Stock";
        if (insertedCoin > goods.getPrice()) return "Available for Purchase";
        return "";
    }
}
