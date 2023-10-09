package com.es.core.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private UUID secureId;
    private List<OrderItem> orderItems = Collections.emptyList();
    /**
     * A sum of order item prices;
     */
    private BigDecimal subtotal;
    private BigDecimal deliveryPrice;
    /**
     * <code>subtotal</code> + <code>deliveryPrice</code>
     */
    private BigDecimal totalPrice;
    private OrderStatus status;

    private String firstName;
    private String lastName;
    private String deliveryAddress;
    private String contactPhoneNo;
    private String additionalInfo;

    public Order(List<OrderItem> orderItems, BigDecimal subtotal) {
        this.orderItems = orderItems;
        this.subtotal = subtotal;
        this.totalPrice = subtotal.add(this.deliveryPrice);
    }
}
