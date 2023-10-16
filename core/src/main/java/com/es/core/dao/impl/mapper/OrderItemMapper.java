package com.es.core.dao.impl.mapper;

import com.es.core.model.order.OrderItem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderItemMapper implements RowMapper<OrderItem> {
    @Resource
    private PhoneMapper phoneMapper;

    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItem orderItem = new BeanPropertyRowMapper<>(OrderItem.class).mapRow(resultSet, i);
        orderItem.setPhone(phoneMapper.mapRow(resultSet, i));
        return orderItem;
    }
}
