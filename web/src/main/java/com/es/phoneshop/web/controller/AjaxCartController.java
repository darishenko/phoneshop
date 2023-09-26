package com.es.phoneshop.web.controller;

import com.es.core.service.CartService;
import com.es.core.exception.OutOfStockException;
import com.es.phoneshop.web.dto.AddToCartResponseDto;
import com.es.phoneshop.web.dto.CartItemDto;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

import static com.es.phoneshop.web.controller.constant.ControllerConstant.ErrorMessage;
import static com.es.phoneshop.web.controller.constant.ControllerConstant.SuccessMessage;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    public static final String BINDING_RESULT_QUANTITY_FIELD = "quantity";
    @Resource
    private CartService cartService;

    @PostMapping
    public ResponseEntity<?> addPhone(@Valid @RequestBody CartItemDto cartItemDto,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = getQuantityErrorMessage(bindingResult);
            return ResponseEntity.badRequest().body(errorMessage);
        }
        try {
            cartService.addPhone(cartItemDto.getPhoneId(), cartItemDto.getQuantity());
            return ResponseEntity.ok(createAddToCartResponse());
        } catch (OutOfStockException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> handleInvalidFormatException() {
        return ResponseEntity.badRequest().body(ErrorMessage.WRONG_FORMAT);
    }

    private AddToCartResponseDto createAddToCartResponse() {
        return new AddToCartResponseDto(cartService.getCart().getTotalQuantity(), cartService.getCart().getTotalCost(),
                SuccessMessage.ADD_TO_CART);
    }

    private String getQuantityErrorMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .filter(error -> Arrays.stream(error.getCodes())
                                .anyMatch(code -> code.contains(BINDING_RESULT_QUANTITY_FIELD)))
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findAny()
                .orElse(ErrorMessage.ADD_TO_CART_ERROR);
    }

}
