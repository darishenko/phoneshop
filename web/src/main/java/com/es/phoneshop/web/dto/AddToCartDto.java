package com.es.phoneshop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartDto {
    @NotNull
    private Long phoneId;
    @NotNull
    @Min(value = 1, message = "Quantity must be greater than or equal to 1.")
    private Long quantity;
}
