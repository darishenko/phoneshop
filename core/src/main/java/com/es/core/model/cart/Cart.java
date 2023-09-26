package com.es.core.model.cart;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {
    List<CartItem> items;
    private Long totalQuantity;
    private BigDecimal totalCost;

    public Cart() {
        this.items = new ArrayList<>();
        this.totalQuantity = 0L;
        this.totalCost = BigDecimal.ZERO;
    }

}
