package com.es.core.model.phone;


import com.es.core.model.color.ColorDao;
import com.es.core.model.stock.StockDao;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
@AllArgsConstructor
public class PhoneMapper implements RowMapper<Phone> {
    private final ColorDao colorDao;
    private final StockDao stockDao;

    @Override
    public Phone mapRow(ResultSet resultSet, int i) throws SQLException {
        Phone phone = new BeanPropertyRowMapper<>(Phone.class).mapRow(resultSet, 0);
        phone.setColors(colorDao.getPhoneColors(phone.getId()));
        phone.setStock(stockDao.getPhoneStock(phone.getId()));
        return phone;
    }
}
