package com.es.core.dao;

import com.es.core.model.order.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderDao {
    Optional<Order> getBySecureId(UUID secureId);

    void save(Order order);
}
