package com.es.core.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderNotFoundException extends RuntimeException {
    private static final String ORDER_WITH_SECURE_ID_NOT_FOUND = "Order with ID %s not found.";
    private Long id;
    private UUID secureId;

    public OrderNotFoundException(Long id) {
        super(String.format(ORDER_WITH_SECURE_ID_NOT_FOUND, id));
        this.id = id;
    }

    public OrderNotFoundException(UUID secureId) {
        super(String.format(ORDER_WITH_SECURE_ID_NOT_FOUND, secureId));
        this.secureId = secureId;
    }
}
