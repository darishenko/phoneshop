package com.es.core.service;


import com.es.core.exception.OutOfStockException;
import com.es.core.model.cart.Cart;

public interface CartService {

    Cart getCart();

    void addPhoneById(Long phoneId, Long quantity);

    void addPhoneByModel(String model, Long quantity);

    boolean tryToUpdate(Long phoneId, Long quantity);

    void remove(Long phoneId);

    void updateCartBeforeOrder() throws OutOfStockException;

    void clearCart();
}
