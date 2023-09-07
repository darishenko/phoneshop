package com.es.core.order;

import com.es.core.model.phone.Phone;

public class OutOfStockException extends RuntimeException {
    private final Phone phone;
    private final long requestedQuantity;
    private final long availableQuantity;

    public OutOfStockException(Phone product, long requestedQuantity, long availableQuantity) {
        this.phone = product;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }

    public Phone getPhone() {
        return phone;
    }

    public long getRequestedQuantity() {
        return requestedQuantity;
    }

    public long getAvailableQuantity() {
        return availableQuantity;
    }
}
