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

    public void setPositionOrderId(Integer positionOrderId) {
        this.positionOrderId = positionOrderId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public BigDecimal getPositionPrice() {
        return positionPrice;
    }

    public void setPositionPrice(BigDecimal positionPrice) {
        this.positionPrice = positionPrice;
    }

    public Integer getPositionCount() {
        return positionCount;
    }

    public void setPositionCount(Integer positionCount) {
        this.positionCount = positionCount;
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
