package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.controller.constant.ControllerConstant.JspPage;
import com.es.phoneshop.web.controller.constant.ControllerConstant.ModelAttribute;
import com.es.phoneshop.web.controller.constant.ControllerConstant.Pagination;
import com.es.phoneshop.web.controller.constant.ControllerConstant.RequestParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private CartService cartService;

    @GetMapping
    public String showProductList(@RequestParam(value = RequestParameter.PAGE_NUMBER, required = false) String page,
                                  @RequestParam(value = RequestParameter.SORT_FIELD, required = false) String sort,
                                  @RequestParam(value = RequestParameter.SORT_ORDER, required = false) String order,
                                  @RequestParam(value = RequestParameter.QUERY, required = false) String search,
                                  Model model) {
        int pageNumber = Optional.ofNullable(page)
                .map(number -> getPageNumber(page))
                .orElse(Pagination.DEFAULT_PAGE_NUMBER);
        Pageable pageable = PageRequest.of(pageNumber, Pagination.PAGE_PHONES_COUNT);
        model.addAttribute(ModelAttribute.PHONES, phoneDao.findAllForSale(sort, order, search, pageable));
        model.addAttribute(ModelAttribute.CART, cartService.getCart());
        return JspPage.PRODUCT_LIST;
    }

    private int getPageNumber(String page) {
        try {
            int result = Integer.parseInt(page) - 1;
            if (result < 0) {
                throw new NumberFormatException();
            }
            return result;
        } catch (NumberFormatException exception) {
            return Pagination.DEFAULT_PAGE_NUMBER;
        }
    }

}
