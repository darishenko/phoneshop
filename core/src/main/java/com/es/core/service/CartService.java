package com.es.core.service;


import com.es.core.model.cart.Cart;

public interface CartService {

    Cart getCart();

    void addPhone(Long phoneId, Long quantity);

    boolean tryToUpdate(Long phoneId, Long quantity);

    void remove(Long phoneId);
}
