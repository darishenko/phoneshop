package com.es.core.dao;

import com.es.core.model.order.OrderItem;

import java.util.List;

public interface OrderItemDao {
    List<OrderItem> getOrderItems(final Long id);
}
