package com.epam.brest.courses.model;

import java.math.BigDecimal;

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

    public Integer getPositionId() {
        return positionId;
    }

    public Position setPositionId(Integer positionId) {
        this.positionId = positionId;
        return this;
    }

    public Integer getPositionOrderId() {
        return positionOrderId;
    }

    public Position setPositionOrderId(Integer positionOrderId) {
        this.positionOrderId = positionOrderId;
        return this;
    }

    public String getPositionName() {
        return positionName;
    }

    public Position setPositionName(String positionName) {
        this.positionName = positionName;
        return this;
    }

    public BigDecimal getPositionPrice() {
        return positionPrice;
    }

    public Position setPositionPrice(BigDecimal positionPrice) {
        this.positionPrice = positionPrice;
        return this;
    }

    public Integer getPositionCount() {
        return positionCount;
    }

    public Position setPositionCount(Integer positionCount) {
        this.positionCount = positionCount;
        return this;
    }

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
