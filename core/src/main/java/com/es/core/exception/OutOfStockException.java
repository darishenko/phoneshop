package com.es.core.exception;

import lombok.Data;

@Data
public class OutOfStockException extends RuntimeException {
    private static final String NOT_ENOUGH_STOCK_ITEMS = "Not enough items in stock!";
    private final long phoneId;
    private final long requestedQuantity;
    private final long stockQuantity;
    private final long cartQuantity;
    private final long availableUserQuantity;

    public OutOfStockException(long phoneId, long requestedQuantity, long stockQuantity, long cartQuantity) {
        super(NOT_ENOUGH_STOCK_ITEMS);
        this.phoneId = phoneId;
        this.requestedQuantity = requestedQuantity;
        this.stockQuantity = stockQuantity;
        this.cartQuantity = cartQuantity;
        this.availableUserQuantity = stockQuantity - cartQuantity;
    }

    public OutOfStockException(long phoneId, long requestedQuantity, long stockQuantity) {
        super(NOT_ENOUGH_STOCK_ITEMS);
        this.phoneId = phoneId;
        this.requestedQuantity = requestedQuantity;
        this.stockQuantity = stockQuantity;
        this.cartQuantity = 0;
        this.availableUserQuantity = stockQuantity;
    }

}
