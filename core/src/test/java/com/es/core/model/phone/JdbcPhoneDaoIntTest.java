package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context/test-applicationContext-core.xml")
public class JdbcPhoneDaoIntTest {
    private static final String TABLE_PHONES = "PHONES";
    private static final String TABLE_STOCKS = "STOCKS";
    private static final String QUERY_POSITIVE_STOCK = "stock > 0";
    private static final Long EXISTING_PHONE_ID = 1000L;
    private static final Long EXISTING_PHONE_ID_WITH_ZERO_STOCK = 1002L;
    private static final Long NON_EXISTING_PHONE_ID = 0L;
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Test
    public void get_existingPhoneId_getProduct() {
        Optional<Phone> phone = phoneDao.get(EXISTING_PHONE_ID);

        assertTrue(phone.isPresent());
        assertEquals(EXISTING_PHONE_ID, phone.get().getId());
    }

    @Test
    public void get_nonExistingPhoneId_getPhone() {
        Optional<Phone> phone = phoneDao.get(NON_EXISTING_PHONE_ID);

        assertFalse(phone.isPresent());
    }

    @Test
    public void save_newPhone_savePhone() {
        int expectedPhonesCount = getPhonesTableRowCount();

        phoneDao.save(createNewPhone("new brand", "new model"));

        int phonesCount = getPhonesTableRowCount();
        assertEquals(expectedPhonesCount + 1, phonesCount);
    }
    @Test
    public void findAllInStock_fullyPhonesTable_noPhonesWithZeroStock() {
        int phonesCount = getPhonesTableRowCount();
        int phonesCountWithPositiveStock = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, TABLE_STOCKS,
                QUERY_POSITIVE_STOCK);

        List<Long> phones = phoneDao.findAllInStock(null, null, null, phonesCount, 0).stream()
                .map(Phone::getId)
                .collect(Collectors.toList());

        assertEquals(phonesCountWithPositiveStock, phones.size());
        assertFalse(phones.contains(EXISTING_PHONE_ID_WITH_ZERO_STOCK));
    }

    @Test
    public void findAll_fullyPhonesTable_notEmptyResult() {
        int expectedPhonesCount = getPhonesTableRowCount();

        int phonesCount = phoneDao.findAll(0, expectedPhonesCount).size();

        assertEquals(expectedPhonesCount, phonesCount);
    }

    @Test
    public void findAll_emptyPhonesTable_emptyResult() {
        int expectedPhonesCount = getPhonesTableRowCount();
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TABLE_PHONES);

        int phonesCount = phoneDao.findAll(0, expectedPhonesCount).size();

        assertEquals(0, phonesCount);
    }

    private int getPhonesTableRowCount() {
        return JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_PHONES);
    }

    private Phone createNewPhone(String brand, String model) {
        Phone phone = new Phone();
        phone.setBrand(brand);
        phone.setModel(model);
        return phone;
    }

}
