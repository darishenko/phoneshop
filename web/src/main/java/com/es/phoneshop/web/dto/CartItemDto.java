package com.es.phoneshop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    @NotNull
    private Long phoneId;
    @NotNull(message = "Quantity must be a number.")
    @Min(value = 1, message = "Quantity must be greater than or equal to 1.")
    private Long quantity;
    private String errorMessage;
    private String successMessage;

    public CartItemDto(Long phoneId, Long quantity) {
        this.phoneId = phoneId;
        this.quantity = quantity;
    }
}
