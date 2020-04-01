package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Position;

import java.util.List;
import java.util.Optional;

public interface PositionDao {

    List<Position> findAllPosition();
    Optional <Position> findPositionById(Integer positionId);
    List<Position> findPositionByOrderId(Integer positionOrderId);
    Integer create(Position position);
    int update(Position position);
    int delete(Integer positionId);
}
