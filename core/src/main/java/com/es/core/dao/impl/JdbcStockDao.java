package com.es.core.dao.impl;

import com.es.core.model.stock.Stock;
import com.es.core.dao.StockDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class JdbcStockDao implements StockDao {
    private static final String QUERY_WHERE_PHONE_ID = " where phoneId = ?";
    private static final String QUERY_AVAILABLE_COUNT_FOR_ORDER = "select STOCK - RESERVED as diff from STOCKS " + QUERY_WHERE_PHONE_ID;
    private static final String QUERY_REDUCE_PHONE_STOCK = "update STOCKS set stock = (stock - ?) " + QUERY_WHERE_PHONE_ID;
    private static final String QUERY_SELECT_PHONE_STOCK = "select * from STOCKS " + QUERY_WHERE_PHONE_ID;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Stock getPhoneStock(Long phoneId) {
        return jdbcTemplate.queryForObject(QUERY_SELECT_PHONE_STOCK, new BeanPropertyRowMapper<>(Stock.class), phoneId);
    }

    @Override
    public Integer getAvailableCountForOrder(Long phoneId) {
        return jdbcTemplate.queryForObject(QUERY_AVAILABLE_COUNT_FOR_ORDER, Integer.class, phoneId);
    }

    @Override
    public void reducePhonesStock(Map<Long, Long> reducedItems) {
        List<Object[]> items = new ArrayList<>();
        reducedItems.forEach((key, value) -> items.add(new Object[]{value, key}));
        jdbcTemplate.batchUpdate(QUERY_REDUCE_PHONE_STOCK, items);
    }
}
