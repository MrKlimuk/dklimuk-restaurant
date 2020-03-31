package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Position;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Optional;

public class PositionDaoJdbc implements PositionDao{

    @Value("${position.findAll}")
    private String findAllSql;


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PositionDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public List<Position> findAllPosition() {
        List<Position> positions = namedParameterJdbcTemplate
                .query(findAllSql, BeanPropertyRowMapper.newInstance(Position.class));
        return positions;
    }

    @Override
    public Optional<Position> findPositionById(Integer positionId) {
        return Optional.empty();
    }

    @Override
    public List<Position> findByOrderId(Integer positionOrderId) {
        return null;
    }

    @Override
    public Integer create(Position position) {
        return null;
    }

    @Override
    public int update(Position position) {
        return 0;
    }

    @Override
    public int delete(Integer positionId) {
        return 0;
    }
}
