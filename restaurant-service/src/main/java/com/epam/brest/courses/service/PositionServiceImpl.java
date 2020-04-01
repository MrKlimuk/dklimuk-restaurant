package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.PositionDao;
import com.epam.brest.courses.model.Position;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class PositionServiceImpl implements PositionService{


    private final PositionDao positionDao;

    public PositionServiceImpl(PositionDao positionDao) {
        this.positionDao = positionDao;
    }

    @Override
    public List<Position> findAllPosition() {
        return positionDao.findAllPosition();
    }

    @Override
    public Optional<Position> findPositionById(Integer positionId) {
        return positionDao.findPositionById(positionId);
    }

    @Override
    public List<Position> findPositionByOrderId(Integer positionOrderId) {
        return positionDao.findPositionByOrderId(positionOrderId);
    }

    @Override
    public Integer create(Position position) {
        return positionDao.create(position);
    }

    @Override
    public int update(Position position) {
        return positionDao.update(position);
    }

    @Override
    public int delete(Integer positionId) {
        return positionDao.delete(positionId);
    }
}
