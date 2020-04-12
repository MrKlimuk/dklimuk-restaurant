package com.epam.brest.courses.model;

import java.math.BigDecimal;

public class Item {

    private Integer itemId;
    private String itemName;
    private BigDecimal itemPrice;

    public Item() {
    }
    public Item(String itemName) {
    }

    public Integer getItemId() {
        return itemId;
    }

    public Item setItemId(Integer itemId) {
        this.itemId = itemId;
        return this;
    }

    public String getItemName() {
        return itemName;
    }

    public Item setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public Item setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
        return this;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                '}';
    }
}


