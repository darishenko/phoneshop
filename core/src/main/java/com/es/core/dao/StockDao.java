package com.es.core.dao;

import com.es.core.model.stock.Stock;

public interface StockDao {
    Stock getPhoneStock(Long phoneId);
}