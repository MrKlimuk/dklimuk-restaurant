package com.epam.brest.courses.model;

import java.math.BigDecimal;

public class Position {

    private Integer positionId;
    private Integer positionOrderId;
    private String positionName;
    private BigDecimal positionPrice;
    private Integer positionCount;

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
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
