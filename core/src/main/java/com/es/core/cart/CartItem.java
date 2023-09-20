package com.es.core.cart;

import com.es.core.model.phone.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItem {
    private Phone phone;
    private Long quantity;
}
