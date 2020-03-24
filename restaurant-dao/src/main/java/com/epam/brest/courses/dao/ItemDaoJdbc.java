package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Optional;

public class ItemDaoJdbc implements ItemDao {

    @Value("${item.findAll}")
    private String findAllItemSql;

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
        return Optional.empty();
    }

    @Override
    public Integer create(Item item) {
        return null;
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
