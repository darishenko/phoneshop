package com.es.core.service;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.exception.OutOfStockException;
import com.es.core.model.order.OrderDetails;

public interface OrderService {
    Order createOrder(Cart cart);

    void placeOrder(Order order, OrderDetails orderDetails) throws OutOfStockException;

    Order getOrderBySecureId(String secureId);
}
