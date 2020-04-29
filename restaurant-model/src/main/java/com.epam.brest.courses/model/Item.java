package com.epam.brest.courses.model;

import java.math.BigDecimal;

/**
 * Database item model.
 */
public class Item {

    /**
     * Item id.
     */
    private Integer itemId;

    /**
     * Item name.
     */
    private String itemName;

    /**
     * Item price.
     */
    private BigDecimal itemPrice;

    /**
     * Default constructor.
     */
    public Item() {
    }

    /**
     * Constructor accepts item name.
     * @param itemName
     */
    public Item(String itemName) {
    }

    /**
     * Get item id.
     * @return itemId.
     */
    public Integer getItemId() {
        return itemId;
    }

    /**
     * Set item id.
     * @param itemId
     * @return itemId.
     */
    public Item setItemId(Integer itemId) {
        this.itemId = itemId;
        return this;
    }

    /**
     * Get item name
     * @return itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Set item name.
     * @param itemName
     * @return itemName.
     */
    public Item setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    /**
     * Get item price.
     * @return itemPrice.
     */
    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    /**
     * Set item price.
     * @param itemPrice
     * @return itemPrice.
     */
    public Item setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
        return this;
    }

    /**
     * Represents item field values as a string.
     * @return a string item field values.
     */
    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                '}';
    }
}


