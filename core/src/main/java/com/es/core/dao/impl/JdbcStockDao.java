package com.es.core.dao.impl;

import com.es.core.model.stock.Stock;
import com.es.core.dao.StockDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JdbcStockDao implements StockDao {
    private static final String QUERY_SELECT_PHONE_STOCK = "select * from STOCKS where phoneId = ?";
    @Resource
    private JdbcTemplate jdbcTemplate;


    @Override
    public Stock getPhoneStock(Long phoneId) {
        return jdbcTemplate.queryForObject(QUERY_SELECT_PHONE_STOCK, new BeanPropertyRowMapper<>(Stock.class), phoneId);
    }
}
