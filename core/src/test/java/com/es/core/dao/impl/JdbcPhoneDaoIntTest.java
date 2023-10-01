package com.es.core.dao.impl;

import com.es.core.dao.PhoneDao;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.sortEnam.SortField;
import com.es.core.model.phone.sortEnam.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private static final String QUERY_SEARCH = "model ilike 'ARCHOS' or brand ilike 'ARCHOS'";
    private static final String AND = " and ";
    private static final String QUERY_PHONES_WITH_POSITIVE_STOCK = " id in (select phoneId from STOCKS where stock > 0)";
    private static final String EXISTING_PHONE_BRAND = "ARCHOS";
    private static final Long EXISTING_PHONE_ID = 1000L;
    private static final Long EXISTING_PHONE_ID_WITH_ZERO_STOCK = 1003L;
    private static final Long NON_EXISTING_PHONE_ID = 0L;
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Test
    public void get_existingPhoneId_getPhone() {
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
    public void findAllForSale_fullyPhonesTable_noPhonesWithZeroStock() {
        int phonesCount = getPhonesTableRowCount();
        int phonesCountWithPositiveStock = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, TABLE_STOCKS,
                QUERY_POSITIVE_STOCK);

        List<Long> phones = phoneDao.findAllForSale(null, null, null, getPageRequest(phonesCount))
                .stream()
                .map(Phone::getId)
                .collect(Collectors.toList());

        assertEquals(phonesCountWithPositiveStock, phones.size());
        assertFalse(phones.contains(EXISTING_PHONE_ID_WITH_ZERO_STOCK));
    }

    @Test
    public void findAllForSale_existingPhoneBrand_foundPhones() {
        int phonesCount = getPhonesTableRowCount();
        int suitablePhonesCount = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, TABLE_PHONES,
                QUERY_SEARCH + AND + QUERY_PHONES_WITH_POSITIVE_STOCK);

        long foundPhonesCount = phoneDao.findAllForSale(null, null,
                EXISTING_PHONE_BRAND, getPageRequest(1)).getTotalElements();

        assertEquals(suitablePhonesCount, foundPhonesCount);
    }

    @Test
    public void findAllForSale_nonExistingPhoneBrand_foundPhones() {
        int phonesCount = getPhonesTableRowCount();

        long foundPhonesCount = phoneDao.findAllForSale(null, null,
                TABLE_STOCKS, getPageRequest(phonesCount)).getTotalElements();

        assertEquals(0, foundPhonesCount);
    }

    @Test
    public void findAllForSale_sortFieldSortOrder_sortedPhones() {
        int phonesCount = getPhonesTableRowCount();

        List<Phone> foundPhones = phoneDao.findAllForSale(SortField.price, SortOrder.desc,
                null, getPageRequest(phonesCount)).toList();

        for (int i = 1; i < foundPhones.size(); i++) {
            assertTrue(
                    foundPhones.get(i - 1).getPrice().longValue() >= foundPhones.get(i).getPrice().longValue()
            );
        }
    }

    @Test
    public void findAllForSale_sortFieldSortOrderExistingPhoneBrand_sortedFoundPhones() {
        int phonesCount = getPhonesTableRowCount();
        int suitablePhonesCount = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, TABLE_PHONES,
                QUERY_SEARCH + AND + QUERY_PHONES_WITH_POSITIVE_STOCK);

        List<Phone> foundPhones = phoneDao.findAllForSale(SortField.price, SortOrder.asc,
                EXISTING_PHONE_BRAND, getPageRequest(phonesCount)).toList();

        assertEquals(suitablePhonesCount, foundPhones.size());
        for (int i = 1; i < foundPhones.size(); i++) {
            assertTrue(
                    foundPhones.get(i - 1).getPrice().longValue() <= foundPhones.get(i).getPrice().longValue()
            );
        }
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

    private Pageable getPageRequest(int phonesCount) {
        return PageRequest.of(0, phonesCount);
    }

}
