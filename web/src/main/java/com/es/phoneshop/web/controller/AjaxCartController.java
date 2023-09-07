package com.es.phoneshop.web.controller;

import com.es.core.cart.CartService;
import com.es.core.order.OutOfStockException;
import com.es.phoneshop.web.dto.AddToCartDto;
import com.es.phoneshop.web.validator.QuantityValidator;
import jakarta.validation.Valid;
import org.json.JSONObject;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Arrays;

import static com.es.phoneshop.web.controller.constant.ControllerConstant.*;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    private static final String JSON_CART_QUANTITY_FIELD = "totalQuantity";
    private static final String JSON_CART_COST_FIELD = "totalCost";
    private static final String JSON_ADD_T0_CART_MESSAGE_FIELD = "message";

    @Resource
    private CartService cartService;
    @Resource
    private QuantityValidator quantityValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(quantityValidator);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addPhone(@Valid AddToCartDto addToCartDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getQuantityErrorMessage(bindingResult));
        }
        try {
            cartService.addPhone(addToCartDto.getPhoneId(), addToCartDto.getQuantity());
            return new ResponseEntity<>(createJsonAddToCartResponse().toString(), HttpStatus.OK);
        } catch (OutOfStockException exception) {
            return ResponseEntity.badRequest().body(String.format("%s\n%s %d.",
                    ErrorMessage.NOT_ENOUGH_IN_STOCK, InfoMessage.AVAILABLE_PHONE_COUNT,
                    exception.getAvailableQuantity()));
        }
    }

    private JSONObject createJsonAddToCartResponse() {
        return new JSONObject()
                .put(JSON_CART_QUANTITY_FIELD, cartService.getCart().getTotalQuantity())
                .put(JSON_CART_COST_FIELD, cartService.getCart().getTotalCost())
                .put(JSON_ADD_T0_CART_MESSAGE_FIELD, SuccessMessage.ADD_TO_CART);
    }

    private String getQuantityErrorMessage(BindingResult bindingResult) {
        String searchErrorField = quantityValidator.getValidatedFieldQuantity();
        return bindingResult.getAllErrors().stream()
                .filter(error -> Arrays.asList(error.getCodes()).contains(searchErrorField))
                .findAny().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse(ErrorMessage.ADD_TO_CART_ERROR);
    }

}
