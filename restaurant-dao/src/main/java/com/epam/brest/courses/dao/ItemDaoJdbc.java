package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.brest.courses.constants.ItemConstants.*;

@Repository
public class ItemDaoJdbc implements ItemDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemDaoJdbc.class);

    @Value("${item.findAll}")
    private String findAllItemSql;

    @Value("${item.findById}")
    private String findItemById;

    @Value("${item.create}")
    private String createItemSql;

    @Value("${item.update}")
    private String updateItemSql;

    @Value("${item.delete}")
    private String deleteItemSql;

    @Value("${item.deleteAllItems}")
    private String deleteAllItemsSql;


    private static final String CHECK_COUNT_NAME = "select count(item_id) from item where lower(item_name) = lower(:itemName)";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public ItemDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
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
        if (!isNameUnique(item)) {
            throw new IllegalArgumentException("Item with the same name already exists in DB.");
        }
        MapSqlParameterSource  params = new MapSqlParameterSource();
        params.addValue(ITEM_NAME, item.getItemName());
        params.addValue(ITEM_PRICE, item.getItemPrice());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(createItemSql, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    private boolean isNameUnique(Item item) {

        return namedParameterJdbcTemplate.queryForObject(CHECK_COUNT_NAME,
                new MapSqlParameterSource(ITEM_NAME, item.getItemName()),
                Integer.class) == 0;
    }

    @Override
    public int update(Item item) {
        Map<String, Object> params = new HashMap<>();
        params.put(ITEM_ID, item.getItemId());
        params.put(ITEM_NAME, item.getItemName());
        params.put(ITEM_PRICE, item.getItemPrice());
        return namedParameterJdbcTemplate.update(updateItemSql, params);

    }

    @Override
    public int delete(Integer itemId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue(ITEM_ID, itemId);
        return namedParameterJdbcTemplate.update(deleteItemSql, param);
    }

    @Override
    public void deleteAllItems() {
        LOGGER.info("delete all items DAO");
        jdbcTemplate.execute(deleteAllItemsSql);
    }
}
