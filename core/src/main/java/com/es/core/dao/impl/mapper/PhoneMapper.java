package com.es.core.dao.impl.mapper;


import com.es.core.dao.ColorDao;
import com.es.core.model.phone.Phone;
import com.es.core.dao.StockDao;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class PhoneMapper implements RowMapper<Phone> {
    @Resource
    private ColorDao colorDao;
    @Resource
    private StockDao stockDao;

    @Override
    public Phone mapRow(ResultSet resultSet, int i) throws SQLException {
        Phone phone = new BeanPropertyRowMapper<>(Phone.class).mapRow(resultSet, 0);
        phone.setColors(colorDao.getPhoneColors(phone.getId()));
        phone.setStock(stockDao.getPhoneStock(phone.getId()));
        return phone;
    }
}
