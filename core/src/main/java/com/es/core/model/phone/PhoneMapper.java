package com.es.core.model.phone;


import com.es.core.model.color.ColorDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhoneMapper implements RowMapper<Phone> {
    private final ColorDao colorDao;

    public PhoneMapper(ColorDao colorDao) {
        this.colorDao = colorDao;
    }

    @Override
    public Phone mapRow(ResultSet resultSet, int i) throws SQLException {
        Phone phone = new BeanPropertyRowMapper<>(Phone.class).mapRow(resultSet, 0);
        phone.setColors(colorDao.getPhoneColors(phone.getId()));
        return phone;
    }
}
