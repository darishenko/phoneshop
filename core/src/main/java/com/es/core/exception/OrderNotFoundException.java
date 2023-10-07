package com.es.core.exception;

import com.es.core.model.order.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderNotFoundException extends RuntimeException {
    public static final String ORDER_WITH_ID_NOT_FOUND = "Order with ID %s not found.";
    private UUID secureId;

    public OrderNotFoundException(UUID secureId) {
        super(String.format(ORDER_WITH_ID_NOT_FOUND, secureId));
        this.secureId = secureId;
    }
}
