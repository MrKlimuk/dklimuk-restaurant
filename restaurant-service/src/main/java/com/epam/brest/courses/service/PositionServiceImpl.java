package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.PositionDao;
import com.epam.brest.courses.model.Position;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * A class that defines a set of operations
 * with an position model.
 */
@Service
@Transactional
public class PositionServiceImpl implements PositionService{

    /**
     * A position data access object.
     */
    private final PositionDao positionDao;

    /**
     * Constructor accepts dao layer object.
     *
     * @param positionDao
     */
    public PositionServiceImpl(PositionDao positionDao) {
        this.positionDao = positionDao;
    }

    /**
     * Find all positions.
     *
     * @return positions list.
     */
    @Override
    public List<Position> findAllPosition() {
        return positionDao.findAllPosition();
    }

    /**
     * Find position by id.
     *
     * @param positionId position id.
     * @return position.
     */
    @Override
    public Optional<Position> findPositionById(Integer positionId) {
        return positionDao.findPositionById(positionId);
    }

    /**
     * Find positions by order id.
     *
     * @param positionOrderId position order id.
     * @return positions list.
     */
    @Override
    public List<Position> findPositionByOrderId(Integer positionOrderId) {
        return positionDao.findPositionByOrderId(positionOrderId);
    }

    /**
     * Create position.
     *
     * @param position position.
     * @return created position id.
     */
    @Override
    public Integer create(Position position) {
        return positionDao.create(position);
    }

    /**
     * Update position.
     *
     * @param position position.
     * @return number of updated records in the database.
     */
    @Override
    public int update(Position position) {
        return positionDao.update(position);
    }

    /**
     * Delete position by Id.
     *
     * @param positionId position Id.
     * @return the number of rows affected.
     */
    @Override
    public int delete(Integer positionId) {
        return positionDao.delete(positionId);
    }
}
