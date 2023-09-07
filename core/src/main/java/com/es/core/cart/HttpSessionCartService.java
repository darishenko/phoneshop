package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.stock.StockDao;
import com.es.core.order.OutOfStockException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private StockDao stockDao;
    @Resource
    private Cart cart;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        Optional<Phone> phone = phoneDao.get(phoneId);
        if (phone.isPresent()) {
            Phone currPhone = phone.get();
            CartItem cartItem = cart.getItems().stream()
                    .filter(item -> phoneId.equals(item.getPhone().getId()))
                    .findAny()
                    .orElse(new CartItem(currPhone, 0L));
            long availablePhoneQuantity = getAvailablePhoneQuantity(phoneId);
            if (quantity <= availablePhoneQuantity - cartItem.getQuantity()) {
                addPhoneToCart(cartItem, quantity);
                recalculateCart(currPhone, quantity);
            } else {
                throw new OutOfStockException(currPhone, quantity, availablePhoneQuantity);
            }
        }
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }

    private Long getAvailablePhoneQuantity(Long phoneId) {
        return stockDao.getPhoneStock(phoneId).longValue();
    }

    private void addPhoneToCart(CartItem cartItem, Long quantity) {
        if (cartItem.getQuantity() == 0) {
            cart.getItems().add(cartItem);
        }
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
    }

    private void recalculateCart(Phone phone, Long quantity) {
        cart.setTotalQuantity(cart.getTotalQuantity() + quantity);
        cart.setTotalCost(phone.getPrice().multiply(BigDecimal.valueOf(quantity)).add(cart.getTotalCost()));
    }
}
