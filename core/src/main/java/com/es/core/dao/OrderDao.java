package com.es.core.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderDao {
    Optional<Order> getById(Long id);

    Optional<Order> getBySecureId(UUID secureId);

    void save(Order order);

    List<Order> getOrders();

    void setStatus(Long id, OrderStatus status);
}
