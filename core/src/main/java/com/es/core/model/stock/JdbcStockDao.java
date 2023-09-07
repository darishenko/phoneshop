package com.es.core.model.stock;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JdbcStockDao implements StockDao {
    private static final String QUERY_SELECT_PHONE_STOCK = "select stock from STOCKS where phoneId = ?";
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Integer getPhoneStock(Long phoneId) {
        return jdbcTemplate.queryForObject(QUERY_SELECT_PHONE_STOCK, Integer.class, phoneId);
    }
}
