package com.es.phoneshop.web.controller.pages;

import com.es.core.service.CartService;
import com.es.core.exception.OutOfStockException;
import com.es.phoneshop.web.controller.constant.ControllerConstant.SuccessMessage;
import com.es.phoneshop.web.controller.constant.ControllerConstant.ErrorMessage;
import com.es.phoneshop.web.controller.constant.ControllerConstant.JspPage;
import com.es.phoneshop.web.controller.constant.ControllerConstant.ModelsAttribute;
import com.es.phoneshop.web.dto.CartDto;
import com.es.phoneshop.web.dto.CartItemDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;

    @GetMapping
    public String getCart(@ModelAttribute CartDto cartDto, Model model) {
        setCartModelAttributes(model, cartDto);
        return JspPage.CART;
    }

    @PutMapping
    public String updateCart(@Valid @ModelAttribute CartDto cartDto, BindingResult bindingResult, Model model) {
        if (Objects.nonNull(cartDto.getCartItems())) {
            tryToUpdateAllCartItemsSeparately(cartDto, bindingResult);
        }
        setCartModelAttributes(model, cartDto);
        return JspPage.CART;
    }

    @DeleteMapping("/{phoneId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long phoneId) {
        cartService.remove(phoneId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private void setCartModelAttributes(Model model, CartDto cartDto) {
        model.addAttribute(ModelsAttribute.CART, cartService.getCart());
        model.addAttribute(ModelsAttribute.CART_DTO, fillCartDto(cartDto));
    }

    private CartDto fillCartDto(CartDto cartDto) {
        if (Objects.isNull(cartDto.getCartItems())) {
            List<CartItemDto> items = cartService.getCart().getItems().stream()
                    .map(item -> new CartItemDto(item.getPhone().getId(), item.getQuantity()))
                    .collect(Collectors.toList());
            cartDto.setCartItems(items);
        }
        return cartDto;
    }
    private void tryToUpdateAllCartItemsSeparately(CartDto cartDto, BindingResult bindingResult) {
        for (int i = 0; i < cartDto.getCartItems().size(); i++) {
            List<FieldError> errors = bindingResult.getFieldErrors("cartItems[" + i + "].quantity");
            tryToUpdateCartItem(cartDto.getCartItems().get(i), errors);
        }
    }

    private void tryToUpdateCartItem (CartItemDto cartItem, List<FieldError> errors) {
        if (!errors.isEmpty()) {
            cartItem.setErrorMessage(getQuantityErrorMessage(errors));
        } else {
            try {
                boolean isUpdated = cartService.tryToUpdate(cartItem.getPhoneId(), cartItem.getQuantity());
                if (isUpdated) {
                    cartItem.setSuccessMessage(SuccessMessage.UPDATE);
                }
            } catch (OutOfStockException exception) {
                cartItem.setErrorMessage(exception.getMessage());
            }
        }
    }

    private String getQuantityErrorMessage(List<FieldError> errors) {
        return errors.stream()
                .filter(err -> err.getDefaultMessage().contains(NumberFormatException.class.getSimpleName()))
                .findAny()
                .map(er -> ErrorMessage.WRONG_FORMAT)
                .orElse(null);
    }

}
