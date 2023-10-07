package com.es.core.dao;

import com.es.core.model.stock.Stock;

import java.util.Map;

public interface StockDao {
    Stock getPhoneStock(Long phoneId);

    Integer getAvailableOrderCount(Long phoneId);

    void reducePhonesStock(Map<Long, Long> reducedItems);
}