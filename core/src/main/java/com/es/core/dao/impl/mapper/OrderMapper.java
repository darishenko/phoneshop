package com.es.core.dao.impl.mapper;


import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

@Component
@AllArgsConstructor
public class OrderMapper implements RowMapper<Order> {
    private static final String COLUMN_ORDER_ID = "ORDERS.ID";
    private static final String COLUMN_ORDER_ITEM_ID = "ORDER_ITEMS.ID";
    private static final String COLUMN_ORDER_ITEM_QUANTITY = "ORDER_ITEMS.QUANTITY";
    @Resource
    private PhoneMapper phoneMapper;

    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order = mapOrder(resultSet, i);
        Phone phone = phoneMapper.mapRow(resultSet, i);
        OrderItem orderItem = mapOrderItem(resultSet);
        orderItem.setPhone(phone);
        orderItem.setOrder(order);
        order.setOrderItems(Collections.singletonList(orderItem));
        return order;
    }

    private Order mapOrder(ResultSet resultSet, int i) throws SQLException {
        Order order = new BeanPropertyRowMapper<>(Order.class).mapRow(resultSet, i);
        order.setId(resultSet.getLong(COLUMN_ORDER_ID));
        return order;
    }

    private OrderItem mapOrderItem(ResultSet resultSet) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(resultSet.getLong(COLUMN_ORDER_ITEM_ID));
        orderItem.setQuantity(resultSet.getLong(COLUMN_ORDER_ITEM_QUANTITY));
        return orderItem;
    }
}
