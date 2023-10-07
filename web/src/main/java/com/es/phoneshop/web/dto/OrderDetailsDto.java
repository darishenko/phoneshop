package com.es.phoneshop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {
    @NotNull(message = "The value is required.")
    @Pattern(regexp = "^[a-z, A-Z]+$", message = "Field should consist only of letters.")
    private String firstName;
    @NotNull(message = "The value is required.")
    @Pattern(regexp = "^[a-z, A-Z]+$", message = "Field should consist only of letters.")
    private String lastName;
    @NotNull(message = "The value is required.")
    @Pattern(regexp = "^[a-z, A-Z]+$", message = "Field should consist only of letters.")
    private String deliveryAddress;
    @NotNull(message = "The value is required.")
    @Pattern(regexp = "^\\+\\d{12}$",
            message = "Invalid format.\n Phone number should start from '+' and consist of 12 digits.")
    private String contactPhoneNo;
    private String additionalInfo;
    private String resultMessage;
    private BigDecimal deliveryPrice;
}
