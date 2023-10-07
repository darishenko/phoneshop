package com.es.core.dao.impl;

import com.es.core.dao.ColorDao;
import com.es.core.dao.OrderDao;
import com.es.core.dao.StockDao;
import com.es.core.dao.impl.mapper.OrderMapper;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
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
    public static final String QUERY_INSERT_ORDER_ITEM = "insert into ORDER_ITEMS (phoneId, quantity, orderId) values (?, ?, ?)";
    public static final String QUERY_SELECT_ORDER_BY_SECURE_ID = "select * from ORDERS as ord inner join ORDER_ITEMS as item on ord.id = item.orderId inner join PHONES as ph on item.phoneId = ph.id where ord.secureId = ?";
    private static final String QUERY_INSERT_ORDER = "insert into ORDERS (secureId, subtotal, deliveryPrice, totalPrice, firstName, lastName, deliveryAddress, contactPhoneNo, additionalInfo, status) values (?, ? ,?, ?, ?, ?, ?, ?, ?, ?)";
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private ColorDao colorDao;
    @Resource
    private StockDao stockDao;

    @Override
    public Optional<Order> getBySecureId(UUID secureId) {
        List<Order> orders = jdbcTemplate.query(QUERY_SELECT_ORDER_BY_SECURE_ID, new OrderMapper(colorDao, stockDao),
                secureId.toString());
        if (!orders.isEmpty()) {
            orders.get(0).setOrderItems(getAllOrderItems(orders));
            return Optional.of(orders.get(0));
        }
        return Optional.empty();
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
