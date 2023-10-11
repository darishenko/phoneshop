package com.es.core.service.impl;

import com.es.core.dao.OrderDao;
import com.es.core.dao.StockDao;
import com.es.core.exception.OrderNotFoundException;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.exception.OutOfStockException;
import com.es.core.model.order.OrderDetails;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.service.CartService;
import com.es.core.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private CartService cartService;
    @Resource
    private StockDao stockDao;
    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

    @Override
    public Order getOrder(Long id) throws OrderNotFoundException {
        return orderDao.getById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        order.setOrderItems(getOrderItemsFromCart(cart, order));
        order.setSubtotal(cart.getTotalCost());
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(order.getSubtotal().add(order.getDeliveryPrice()));
        return order;
    }

    @Override
    @Transactional
    public void placeOrder(Order order, OrderDetails orderDetails) throws OutOfStockException {
        cartService.updateCartBeforeOrder();
        setOrderDetails(order, orderDetails);
        orderDao.save(order);
        stockDao.reducePhonesStock(getReducedItems(order));
        cartService.clearCart();
    }

    @Override
    public Order getOrderBySecureId(UUID secureId) throws OrderNotFoundException {
        return orderDao.getBySecureId(secureId).orElseThrow(() -> new OrderNotFoundException(secureId));
    }

    @Override
    public List<Order> getOrders() {
        return orderDao.getOrders();
    }

    @Override
    public void setOrderStatus(Long id, OrderStatus status) {
        orderDao.setStatus(id, status);
    }

    private List<OrderItem> getOrderItemsFromCart(Cart cart, Order order) {
        return cart.getItems().stream()
                .map(cartItem -> new OrderItem(cartItem.getPhone(), cartItem.getQuantity(), order))
                .collect(Collectors.toList());
    }

    private void setOrderDetails(Order order, OrderDetails orderDetails) {
        order.setSecureId(UUID.randomUUID());
        order.setFirstName(orderDetails.getFirstName());
        order.setLastName(orderDetails.getLastName());
        order.setContactPhoneNo(orderDetails.getContactPhoneNo());
        order.setDeliveryAddress(orderDetails.getDeliveryAddress());
        order.setAdditionalInfo(orderDetails.getAdditionalInfo());
        order.setStatus(OrderStatus.NEW);
    }

    private Map<Long, Long> getReducedItems(Order order) {
        return order.getOrderItems().stream()
                .collect(Collectors.toMap(item -> item.getPhone().getId(), OrderItem::getQuantity));
    }
}
