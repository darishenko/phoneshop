package com.es.core.service.impl;

import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.exception.OutOfStockException;
import com.es.core.service.CartService;
import com.es.core.service.PhoneService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class HttpSessionCartService implements CartService {
    @Resource
    private PhoneService phoneService;
    @Resource
    private Cart cart;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        Phone phone = phoneService.get(phoneId);
        CartItem cartItem = getCartItemByPhoneId(phoneId);
        if (Objects.isNull(cartItem)) {
            cartItem = new CartItem(phone, 0L);
        }
        long availableQuantity = getAvailablePhoneQuantity(phone);
        if (quantity <= availableQuantity - cartItem.getQuantity()) {
            addPhoneToCart(cartItem, cartItem.getQuantity() + quantity);
            recalculateCart();
        } else {
            throw new OutOfStockException(phone.getId(), quantity, availableQuantity, cartItem.getQuantity());
        }
    }

    @Override
    public void update(Map<Long, Long> items) {
        cart.setItems(getAvailableCartItems(items));
        recalculateCart();
    }

    @Override
    public boolean tryToUpdate(Long phoneId, Long quantity) {
        CartItem cartItem = getCartItemByPhoneId(phoneId);
        if (Objects.nonNull(cartItem) && !quantity.equals(cartItem.getQuantity())) {
            long availableQuantity = getAvailablePhoneQuantity(cartItem.getPhone());
            if (quantity <= availableQuantity) {
                addPhoneToCart(cartItem, quantity);
                recalculateCart();
                return true;
            } else {
                throw new OutOfStockException(cartItem.getPhone().getId(), quantity, availableQuantity,
                        cartItem.getQuantity());
            }
        }
        return false;
    }

    @Override
    public void remove(Long phoneId) {
        cart.getItems().stream()
                .filter(item -> phoneId.equals(item.getPhone().getId()))
                .findAny()
                .ifPresent(cart.getItems()::remove);
        recalculateCart();
    }

    private Integer getAvailablePhoneQuantity(Phone phone) {
        return phone.getStock().getStock() - phone.getStock().getReserved();
    }

    private void addPhoneToCart(CartItem cartItem, Long quantity) {
        if (cartItem.getQuantity() == 0) {
            cart.getItems().add(cartItem);
        }
        cartItem.setQuantity(quantity);
    }

    private void recalculateCart() {
        long totalQuantity = cart.getItems().stream()
                .mapToLong(CartItem::getQuantity)
                .sum();
        BigDecimal totalCost = cart.getItems().stream()
                .map(cartItem -> cartItem.getPhone().getPrice().multiply(
                        BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalQuantity(totalQuantity);
        cart.setTotalCost(totalCost);
    }

    private CartItem getCartItemByPhoneId(Long phoneId) {
        return cart.getItems().stream()
                .filter(item -> phoneId.equals(item.getPhone().getId()))
                .findAny()
                .orElse(null);
    }

    private List<CartItem> getAvailableCartItems(Map<Long, Long> items) {
        return items.entrySet().stream()
                .map(item -> getCartItem(item.getKey(), item.getValue()))
                .collect(Collectors.toList());
    }

    private CartItem getCartItem(Long phoneId, Long quantity) {
        Phone phone = phoneService.get(phoneId);
        long availableQuantity = getAvailablePhoneQuantity(phone);
        if (quantity <= availableQuantity) {
            return new CartItem(phone, quantity);
        } else {
            throw new OutOfStockException(phone.getId(), quantity, availableQuantity);
        }
    }

}
