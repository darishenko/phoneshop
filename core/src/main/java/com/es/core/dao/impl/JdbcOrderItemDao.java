package com.es.core.dao.impl;

import com.es.core.dao.OrderItemDao;
import com.es.core.dao.impl.mapper.OrderItemMapper;
import com.es.core.model.order.OrderItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class JdbcOrderItemDao implements OrderItemDao {
    private static final String QUERY_SELECT_ORDER_ITEMS_JOIN_PHONES = "select * from ORDER_ITEMS as item inner join PHONES as ph on item.phoneId = ph.id where item.orderId = ?";
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItem> getOrderItems(final Long id) {
        return jdbcTemplate.query(QUERY_SELECT_ORDER_ITEMS_JOIN_PHONES, orderItemMapper, id);
    }
}
