package com.es.core.dao.impl;

import com.es.core.dao.OrderDao;
import com.es.core.dao.impl.mapper.OrderMapper;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JdbcOrderDao implements OrderDao {
    private static final String QUERY_SELECT_ORDERS = "select * from ORDERS ";
    private static final String QUERY_WHERE_ID = " where ORDERS.id = ? ";
    private static final String QUERY_WHERE_SECURE_ID = " where ORDERS.secureId = ? ";
    private static final String QUERY_UPDATE_ORDER_STATUS = "update ORDERS set status = ? where id = ?";
    private static final String QUERY_JOIN_ORDER_ITEMS_JOIN_PHONES = " inner join ORDER_ITEMS as item on ORDERS.id = item.orderId inner join PHONES as ph on item.phoneId = ph.id ";
    private static final String QUERY_SELECT_BY_ID = QUERY_SELECT_ORDERS + QUERY_JOIN_ORDER_ITEMS_JOIN_PHONES + QUERY_WHERE_ID;
    private static final String QUERY_INSERT_ORDER_ITEM = "insert into ORDER_ITEMS (phoneId, quantity, orderId) values (?, ?, ?)";
    private static final String QUERY_SELECT_ORDER_BY_SECURE_ID = QUERY_SELECT_ORDERS + QUERY_JOIN_ORDER_ITEMS_JOIN_PHONES + QUERY_WHERE_SECURE_ID;
    private static final String QUERY_INSERT_ORDER = "insert into ORDERS (secureId, subtotal, deliveryPrice, totalPrice, firstName, lastName, deliveryAddress, contactPhoneNo, additionalInfo, status) values (?, ? ,?, ?, ?, ?, ?, ?, ?, ?)";
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private OrderMapper orderMapper;

    @Override
    public Optional<Order> getById(Long id) {
        List<Order> orders = jdbcTemplate.query(QUERY_SELECT_BY_ID, orderMapper, id);
        return getOptionalOfOrderFromList(orders);
    }

    @Override
    public Optional<Order> getBySecureId(UUID secureId) {
        List<Order> orders = jdbcTemplate.query(QUERY_SELECT_ORDER_BY_SECURE_ID, orderMapper, secureId.toString());
        return getOptionalOfOrderFromList(orders);
    }

    @Override
    public void save(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT_ORDER,
                    Statement.RETURN_GENERATED_KEYS);
            return fillPreparedStatement(preparedStatement, order);
        }, keyHolder);
        order.setId((Long) keyHolder.getKey());
        saveOrderItems(order);
    }

    @Override
    public List<Order> getOrders() {
        return jdbcTemplate.query(QUERY_SELECT_ORDERS, new BeanPropertyRowMapper<>(Order.class));
    }

    @Override
    public void setStatus(Long id, OrderStatus status) {
        jdbcTemplate.update(QUERY_UPDATE_ORDER_STATUS, status.name(), id);
    }

    private Optional<Order> getOptionalOfOrderFromList(List<Order> orders) {
        if (!orders.isEmpty()) {
            orders.get(0).setOrderItems(getAllOrderItems(orders));
            return Optional.of(orders.get(0));
        }
        return Optional.empty();
    }

    private List<OrderItem> getAllOrderItems(List<Order> orders) {
        return orders.stream()
                .map(item -> item.getOrderItems().get(0))
                .collect(Collectors.toList());
    }

    private void saveOrderItems(Order order) {
        List<Object[]> orderItems = new ArrayList<>();
        order.getOrderItems().forEach(item ->
                orderItems.add(new Object[]{item.getPhone().getId(), item.getQuantity(), order.getId()}));
        jdbcTemplate.batchUpdate(QUERY_INSERT_ORDER_ITEM, orderItems);
    }

    private PreparedStatement fillPreparedStatement(PreparedStatement preparedStatement, Order order)
            throws SQLException {
        preparedStatement.setString(1, order.getSecureId().toString());
        preparedStatement.setObject(2, order.getSubtotal());
        preparedStatement.setObject(3, order.getDeliveryPrice());
        preparedStatement.setObject(4, order.getTotalPrice());
        preparedStatement.setString(5, order.getFirstName());
        preparedStatement.setString(6, order.getLastName());
        preparedStatement.setString(7, order.getDeliveryAddress());
        preparedStatement.setString(8, order.getContactPhoneNo());
        preparedStatement.setString(9, order.getAdditionalInfo());
        preparedStatement.setString(10, order.getStatus().name());
        return preparedStatement;
    }
}
