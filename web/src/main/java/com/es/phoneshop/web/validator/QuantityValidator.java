package com.es.phoneshop.web.validator;

import com.es.phoneshop.web.dto.AddToCartDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class QuantityValidator implements Validator {
    private static final String MESSAGE_WRONG_FORMAT = "Wrong format.";
    private static final String MESSAGE_POSITIVE_QUANTITY = "Quantity must be greater than or equal to 1.";
    private final String validatedFieldQuantity = "quantity";

    public String getValidatedFieldQuantity() {
        return validatedFieldQuantity;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return AddToCartDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        try {
            long quantity = Long.parseLong(((AddToCartDto) o).getQuantity().toString());
            if (quantity < 1L) {
                errors.reject(validatedFieldQuantity, MESSAGE_POSITIVE_QUANTITY);
            }
        } catch (NumberFormatException | NullPointerException exception) {
            errors.reject(validatedFieldQuantity, MESSAGE_WRONG_FORMAT);
        }
    }
}