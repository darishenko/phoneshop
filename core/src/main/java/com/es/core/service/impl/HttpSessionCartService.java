package com.es.core.service.impl;

import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.exception.OutOfStockException;
import com.es.core.service.CartService;
import com.es.core.service.PhoneService;
import com.es.core.service.StockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class HttpSessionCartService implements CartService {
    @Resource
    private PhoneService phoneService;
    @Resource
    private StockService stockService;
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

    @Override
    public void updateCartBeforeOrder() throws OutOfStockException{
        List<CartItem> updatedCartItems = cart.getItems().stream()
                .filter(cartItem ->
                        stockService.getAvailableOrderCount(cartItem.getPhone().getId()) >= cartItem.getQuantity())
                .collect(Collectors.toList());
        boolean isUpdated = cart.getItems().size() != updatedCartItems.size();
        cart.setItems(updatedCartItems);
        if (isUpdated) {
            recalculateCart();
            throw new OutOfStockException();
        }
    }

    @Override
    public void clearCart() {
        cart.getItems().clear();
        cart.setTotalQuantity(0L);
        cart.setTotalCost(BigDecimal.ZERO);
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

}
