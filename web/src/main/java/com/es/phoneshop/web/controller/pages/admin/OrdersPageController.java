package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.OrderStatus;
import com.es.core.service.OrderService;
import com.es.phoneshop.web.controller.constant.ControllerConstant.ModelsAttribute;
import com.es.phoneshop.web.controller.constant.ControllerConstant.JspPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {
    @Resource
    private OrderService orderService;

    @GetMapping
    public String getOrders(Model model) {
        model.addAttribute(ModelsAttribute.ORDERS, orderService.getOrders());
        return JspPage.ORDERS_FOR_ADMIN;
    }

    @GetMapping("/{orderId}")
    public String getOrder(@PathVariable Long orderId, Model model) {
        model.addAttribute(ModelsAttribute.ORDER, orderService.getOrder(orderId));
        return JspPage.ORDER_FOR_ADMIN;
    }

    @PutMapping("/{orderId}")
    public String changeOrderStatus(@PathVariable Long orderId, OrderStatus orderStatus) {
        orderService.setOrderStatus(orderId, orderStatus);
        return "redirect:/admin/orders/" + orderId;
    }
}
