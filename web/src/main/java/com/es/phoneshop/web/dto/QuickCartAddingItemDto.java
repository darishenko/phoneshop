package com.es.phoneshop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuickCartAddingItemDto {
    @Pattern(regexp = "^(?!\\s*$).+", message = "Field shouldn't be empty!")
    private String model;
    @NotNull(message = "Quantity must be a number.")
    @Min(value = 1, message = "Quantity must be greater than or equal to 1.")
    private Long quantity;
}
