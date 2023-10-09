package com.es.phoneshop.web.controller.pages;

import com.es.core.service.CartService;
import com.es.core.service.OrderService;
import com.es.phoneshop.web.controller.constant.ControllerConstant.JspPage;
import com.es.phoneshop.web.controller.constant.ControllerConstant.ModelsAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.UUID;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {
    @Resource
    private CartService cartService;
    @Resource
    private OrderService orderService;

    @GetMapping("/{orderSecureId}")
    public String getOrderOverview(@PathVariable UUID orderSecureId, Model model) {
        model.addAttribute(ModelsAttribute.CART, cartService.getCart());
        model.addAttribute(ModelsAttribute.ORDER, orderService.getOrderBySecureId(orderSecureId));
        return JspPage.ORDER_OVERVIEW;
    }
}
