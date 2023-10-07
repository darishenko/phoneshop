package com.es.core.service.impl;

import com.es.core.dao.StockDao;
import com.es.core.dao.impl.JdbcStockDao;
import com.es.core.model.stock.Stock;
import com.es.core.service.StockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StockServiceImpl implements StockService {
    @Resource
    private StockDao stockDao;

    @Override
    public Stock getPhoneStock(Long phoneId) {
        return stockDao.getPhoneStock(phoneId);
    }

    @Override
    public Integer getAvailableOrderCount(Long phoneId) {
        return stockDao.getAvailableOrderCount(phoneId);
    }
}
