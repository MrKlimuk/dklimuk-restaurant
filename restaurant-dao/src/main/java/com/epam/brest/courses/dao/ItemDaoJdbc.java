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
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.brest.courses.constants.ItemConstants.*;

@Repository
public class ItemDaoJdbc implements ItemDao {

//    @Value("${item.findAll}")
    private String findAllItemSql = "SELECT o.item_id, o.item_name, o.item_price FROM item AS o ORDER BY o.item_id";

//    @Value("${item.findById}")
    private String findItemById = "SELECT item_id, item_name, item_price FROM item WHERE item_id = :itemId";

//    @Value("${item.create}")
    private String createItemSql = "INSERT INTO item (item_name, item_price) VALUES (:itemName, :itemPrice)";

//    @Value("${item.update}")
    private String updateItemSql = "UPDATE item SET item_name = :itemName, item_price = :itemPrice WHERE item_id = :itemId";

//    @Value("${item.delete}")
    private String deleteItemSql = "DELETE FROM item WHERE item_id = :itemId";

    private static final String CHECK_COUNT_NAME = "select count(item_id) from item where lower(item_name) = lower(:itemName)";

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
}
