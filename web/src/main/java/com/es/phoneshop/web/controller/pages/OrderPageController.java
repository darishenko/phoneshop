package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDetails;
import com.es.core.service.CartService;
import com.es.core.service.OrderService;
import com.es.core.exception.OutOfStockException;
import com.es.phoneshop.web.controller.constant.ControllerConstant.ErrorMessage;
import com.es.phoneshop.web.controller.constant.ControllerConstant.ModelsAttribute;
import com.es.phoneshop.web.controller.constant.ControllerConstant.JspPage;
import com.es.phoneshop.web.dto.OrderDetailsDto;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    @Resource
    private OrderService orderService;
    @Resource
    private CartService cartService;

    @GetMapping
    public String getOrder(@ModelAttribute OrderDetailsDto orderDetails, Model model) throws OutOfStockException {
        setModelAttributes(orderDetails, model);
        return JspPage.ORDER;
    }

    @PostMapping
    public String placeOrder(@Valid @ModelAttribute OrderDetailsDto orderDetails, BindingResult bindingResult,
                             Model model) throws OutOfStockException {
        setModelAttributes(orderDetails, model);
        if (cartService.getCart().getItems().isEmpty()) {
            orderDetails.setResultMessage(ErrorMessage.EMPTY_CART);
            return JspPage.ORDER;
        }
        if (!bindingResult.hasErrors()) {
            return tryPlaceOrderAndRedirect(orderDetails);
        }
        orderDetails.setErrors(getErrorsMessages(bindingResult));
        return JspPage.ORDER;
    }

    private String tryPlaceOrderAndRedirect(OrderDetailsDto orderDetails) {
        Order order = orderService.createOrder(cartService.getCart());
        try {
            orderService.placeOrder(order, getOrderDetailFromOrderDetailsDto(orderDetails));
        } catch (OutOfStockException ex) {
            orderDetails.setResultMessage(ErrorMessage.ORDER_ERROR);
            return JspPage.ORDER;
        }
        return "redirect:/orderOverview/" + order.getSecureId();
    }

    private OrderDetails getOrderDetailFromOrderDetailsDto(OrderDetailsDto orderDetailsDto) {
        return new OrderDetails(orderDetailsDto.getFirstName(), orderDetailsDto.getLastName(),
                orderDetailsDto.getDeliveryAddress(), orderDetailsDto.getContactPhoneNo(),
                orderDetailsDto.getAdditionalInfo());
    }

    private void setModelAttributes(OrderDetailsDto orderDetails, Model model) {
        Order order = orderService.createOrder(cartService.getCart());
        orderDetails.setDeliveryPrice(order.getDeliveryPrice());
        model.addAttribute(ModelsAttribute.ORDER_DETAIL_DTO, orderDetails);
        model.addAttribute(ModelsAttribute.CART, cartService.getCart());
    }

    private Map<String, String> getErrorsMessages(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }
}
