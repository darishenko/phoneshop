package com.es.phoneshop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AddToCartResponseDto {
    private Long totalQuantity;
    private BigDecimal totalCost;
    private String message;
}
