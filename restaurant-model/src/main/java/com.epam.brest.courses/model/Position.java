package com.epam.brest.courses.model;

import java.math.BigDecimal;

/**
 * Database position model.
 */
public class Position {

    /**
     * Position id.
     */
    private Integer positionId;

    /**
     * Position order id.
     */
    private Integer positionOrderId;

    /**
     * Position name.
     */
    private String positionName;

    /**
     * Position price.
     */
    private BigDecimal positionPrice;

    /**
     * Position count.
     */
    private Integer positionCount;

    /**
     * Get position Id.
     * @return positionId.
     */
    public Integer getPositionId() {
        return positionId;
    }

    /**
     * Set position id.
     * @param positionId
     * @return positionId.
     */
    public Position setPositionId(Integer positionId) {
        this.positionId = positionId;
        return this;
    }

    /**
     * Get position order id.
     * @return positionOrderId.
     */
    public Integer getPositionOrderId() {
        return positionOrderId;
    }

    /**
     * Set position order id.
     * @param positionOrderId
     * @return positionOrderId.
     */
    public Position setPositionOrderId(Integer positionOrderId) {
        this.positionOrderId = positionOrderId;
        return this;
    }

    /**
     * Get position name.
     * @return position name.
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * Set position name.
     * @param positionName
     * @return position name.
     */
    public Position setPositionName(String positionName) {
        this.positionName = positionName;
        return this;
    }

    /**
     * Get position price.
     * @return positionPrice.
     */
    public BigDecimal getPositionPrice() {
        return positionPrice;
    }

    /**
     * Set position price.
     * @param positionPrice
     * @return positionPrice.
     */
    public Position setPositionPrice(BigDecimal positionPrice) {
        this.positionPrice = positionPrice;
        return this;
    }

    /**
     * Get position count.
     * @return position count.
     */
    public Integer getPositionCount() {
        return positionCount;
    }

    /**
     * Set position count.
     * @param positionCount
     * @return positionCount.
     */
    public Position setPositionCount(Integer positionCount) {
        this.positionCount = positionCount;
        return this;
    }

    /**
     * Represents position field values as a string.
     * @return a string position field values.
     */
    @Override
    public String toString() {
        return "Position{" +
                "positionId=" + positionId +
                ", positionOrderId=" + positionOrderId +
                ", positionName='" + positionName + '\'' +
                ", positionPrice=" + positionPrice +
                ", positionCount=" + positionCount +
                '}';
    }
}
