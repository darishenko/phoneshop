package com.es.phoneshop.web.controller.pages;

import com.es.core.service.CartService;
import com.es.core.service.PhoneService;
import com.es.phoneshop.web.controller.constant.ControllerConstant.JspPage;
import com.es.phoneshop.web.controller.constant.ControllerConstant.ModelsAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {
    @Resource
    private PhoneService phoneService;
    @Resource
    private CartService cartService;

    @GetMapping("/{phoneId}")
    public String showPhoneDetails(@PathVariable Long phoneId, Model model) {
        model.addAttribute(ModelsAttribute.PHONE, phoneService.get(phoneId));
        model.addAttribute(ModelsAttribute.CART, cartService.getCart());
        return JspPage.PRODUCT_DETAILS;
    }

}
