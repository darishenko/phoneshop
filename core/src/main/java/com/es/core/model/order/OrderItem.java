package com.es.core.model.order;

import com.es.core.model.phone.Phone;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItem {
    private Long id;
    private Phone phone;
    private Long quantity;
    private Order order;

    public OrderItem(Phone phone, Long quantity, Order order) {
        this.phone = phone;
        this.quantity = quantity;
        this.order = order;
    }
}
