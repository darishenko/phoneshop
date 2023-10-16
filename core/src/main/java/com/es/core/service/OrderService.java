package com.es.core.service;

import com.es.core.exception.OrderNotFoundException;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.exception.OutOfStockException;
import com.es.core.model.order.OrderDetails;
import com.es.core.model.order.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order getOrder(Long id) throws OrderNotFoundException;

    Order createOrder(Cart cart);

    void placeOrder(Order order, OrderDetails orderDetails) throws OutOfStockException;

    Order getOrderBySecureId(UUID secureId);

    List<Order> getOrders();

    void setOrderStatus(Long id, OrderStatus status);
}
