package com.es.core.service;

import com.es.core.model.stock.Stock;

public interface StockService {
    Stock getPhoneStock(Long phoneId);

    Integer getAvailableOrderCount(Long phoneId);
}
