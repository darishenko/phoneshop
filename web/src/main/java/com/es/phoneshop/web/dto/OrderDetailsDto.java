package com.es.phoneshop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Field must contain only letters.")
    private String firstName;
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Field must contain only letters.")
    private String lastName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Field must contain only letters and numbers.")
    private String deliveryAddress;
    @Pattern(regexp = "^\\+\\d{12}$",
            message = "Invalid format.\n Phone number must start from '+' and consist of 12 digits.")
    private String contactPhoneNo;
    private String additionalInfo;
    private String resultMessage;
    private BigDecimal deliveryPrice;
    private Map<String, String> errors;
}
