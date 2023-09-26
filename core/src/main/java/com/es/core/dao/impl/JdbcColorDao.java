package com.es.core.dao.impl;

import com.es.core.dao.ColorDao;
import com.es.core.model.color.Color;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Component
public class JdbcColorDao implements ColorDao {
    private static final String QUERY_SELECT_PHONE_COLORS = "select * from colors c inner join phone2color p2c on c.ID = p2c.COLORID where p2c.PHONEID = ?";
    @Resource
    private JdbcTemplate jdbcTemplate;

    public Set<Color> getPhoneColors(final Long phoneId) {
        return new HashSet<>(jdbcTemplate.query(QUERY_SELECT_PHONE_COLORS, new BeanPropertyRowMapper<>(Color.class),
                phoneId));
    }
}
