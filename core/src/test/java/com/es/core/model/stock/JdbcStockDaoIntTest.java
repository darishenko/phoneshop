package com.es.core.model.stock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context/test-applicationContext-core.xml")
public class JdbcStockDaoIntTest {
    private static final Long PHONE_ID_WITH_POSITIVE_STOCK = 1000L;
    private static final Integer PHONE_ID_WITH_POSITIVE_STOCK_COUNT = 11;
    private static final Long PHONE_ID_WITH_ZERO_STOCK = 1002L;
    @Resource
    private StockDao stockDao;

    @Test
    public void getPhoneStock_positivePhoneStock_getStock() {
        int stockCount = stockDao.getPhoneStock(PHONE_ID_WITH_POSITIVE_STOCK);

        assertEquals(PHONE_ID_WITH_POSITIVE_STOCK_COUNT.intValue(), stockCount);
    }

    @Test
    public void getPhoneStock_zeroPhoneStock_getStock() {
        int stockCount = stockDao.getPhoneStock(PHONE_ID_WITH_ZERO_STOCK);

        assertEquals(0, stockCount);
    }
}
