package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.ItemConstants.*;

public class ItemDaoJdbc implements ItemDao {

    @Value("${item.findAll}")
    private String findAllItemSql;

    @Value("${item.findById}")
    private String findItemById;

    @Value("${item.create}")
    private String createItemSql;


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ItemDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public List<Item> findAll() {
        List<Item> items = namedParameterJdbcTemplate
                .query(findAllItemSql, BeanPropertyRowMapper.newInstance(Item.class));
        return items;
    }

    @Override
    public Optional<Item> findById(Integer itemId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource(ITEM_ID, itemId);
        List<Item> result = namedParameterJdbcTemplate.query(findItemById, namedParameters,
                BeanPropertyRowMapper.newInstance(Item.class));
        return Optional.ofNullable(DataAccessUtils.uniqueResult(result));
    }

    @Override
    public Integer create(Item item) {
        MapSqlParameterSource  params = new MapSqlParameterSource();
        params.addValue(ITEM_NAME, item.getItemName());
        params.addValue(ITEM_PRICE, item.getItemPrice());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(createItemSql, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int update(Item item) {
        return 0;
    }

    @Override
    public int delete(Integer itemId) {
        return 0;
    }
}
