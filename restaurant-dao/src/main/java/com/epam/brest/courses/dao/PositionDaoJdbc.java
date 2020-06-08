package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Position;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.brest.courses.constants.PositionConstants.*;

@Repository
public class PositionDaoJdbc implements PositionDao{

//    @Value("${position.findAll}")
    private String findAllSql = "SELECT p.position_id, p.position_order_id, p.position_name, p.position_price, p.position_count FROM position AS p ORDER BY p.position_order_id";

//    @Value("${position.findById}")
    private String findPositionByIdSql = "SELECT position_id, position_order_id, position_name, position_price, position_count FROM position WHERE position_id = :positionId";

//    @Value("${position.create}")
    private String createPositionSql = "INSERT INTO position ( position_order_id, position_name, position_price, position_count) VALUES (:positionOrderId, :positionName, :positionPrice, :positionCount)";

//    @Value("${position.update}")
    private String updatePositionSql = "UPDATE position SET position_order_id = :positionOrderId, position_name = :positionName, position_price = :positionPrice, position_count = :positionCount WHERE position_id = :positionId";


//    @Value("${position.delete}")
    private String deletePositionSql = "DELETE FROM position WHERE position_id = :positionId";

//    @Value("${position.findByOrderId}")
    private String findFindPositionByOrderIdSql = "SELECT position_id, position_order_id, position_name, position_price, position_count FROM position WHERE position_order_id = :positionOrderId";


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
        SqlParameterSource namedParameters = new MapSqlParameterSource(POSITION_ID, positionId);
        List<Position> result = namedParameterJdbcTemplate.query(findPositionByIdSql, namedParameters,
                                    BeanPropertyRowMapper.newInstance(Position.class));
        return Optional.ofNullable(DataAccessUtils.uniqueResult(result));
    }

    @Override
    public List<Position> findPositionByOrderId(Integer positionOrderId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource(POSITION_ORDER_ID, positionOrderId);
        List<Position> positions = namedParameterJdbcTemplate.query(findFindPositionByOrderIdSql,
                parameterSource, BeanPropertyRowMapper.newInstance(Position.class));
        return positions;
    }

    @Override
    public Integer create(Position position) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(POSITION_ORDER_ID, position.getPositionOrderId());
        params.addValue(POSITION_NAME, position.getPositionName());
        params.addValue(POSITION_PRICE, position.getPositionPrice());
        params.addValue(POSITION_COUNT, position.getPositionCount());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(createPositionSql, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int update(Position position) {
        Map<String, Object> params = new HashMap<>();
        params.put(POSITION_ID, position.getPositionId());
        params.put(POSITION_ORDER_ID, position.getPositionOrderId());
        params.put(POSITION_NAME, position.getPositionName());
        params.put(POSITION_PRICE, position.getPositionPrice());
        params.put(POSITION_COUNT, position.getPositionCount());

        return namedParameterJdbcTemplate.update(updatePositionSql, params);
    }

    @Override
    public int delete(Integer positionId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(POSITION_ID, positionId);
        return namedParameterJdbcTemplate.update(deletePositionSql, mapSqlParameterSource);
    }
}
