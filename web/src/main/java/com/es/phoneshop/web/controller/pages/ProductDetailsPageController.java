package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.controller.constant.ControllerConstant.JspPage;
import com.es.phoneshop.web.controller.constant.ControllerConstant.ModelAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private CartService cartService;

    @GetMapping("/{phoneId}")
    public String showPhoneDetails(@PathVariable Long phoneId, Model model) {
        //#TODO: phone not found
        Phone phone = phoneDao.get(phoneId).orElse(null);
        model.addAttribute(ModelAttribute.PHONE, phone);
        model.addAttribute(ModelAttribute.CART, cartService.getCart());
        return JspPage.PRODUCT_DETAILS;
    }

}
