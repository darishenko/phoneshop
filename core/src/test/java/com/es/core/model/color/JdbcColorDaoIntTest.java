package com.es.core.model.color;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.annotation.Resource;
import java.util.Set;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context/test-applicationContext-core.xml")
public class JdbcColorDaoIntTest {
    private static final String TABLE_PHONE_TO_COLOR = "phone2color";
    private static final String ROW_PHONE_IN_TABLE_PHONE_TO_COLOR = "phoneId";
    private static final Long PHONE_ID_WITH_COLORS = 1000L;
    private static final Long PHONE_ID_WITHOUT_COLORS = 1002L;
    @Resource
    private ColorDao colorDao;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Test
    public void getPhoneColors_phoneWithColors_notEmptyColorSet() {
        int expectedPhoneColorsCount = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, TABLE_PHONE_TO_COLOR,
                String.format("%s = %d", ROW_PHONE_IN_TABLE_PHONE_TO_COLOR, PHONE_ID_WITH_COLORS));

        Set<Color> phoneColors = colorDao.getPhoneColors(PHONE_ID_WITH_COLORS);

        assertFalse(phoneColors.isEmpty());
        assertEquals(expectedPhoneColorsCount, phoneColors.size());
    }

    @Test
    public void getPhoneColors_phoneWithoutColors_emptyColorSet() {
        Set<Color> phoneColors = colorDao.getPhoneColors(PHONE_ID_WITHOUT_COLORS);

        assertTrue(phoneColors.isEmpty());
    }

}
