package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.phoneshop.web.controller.constant.ControllerConstant.JspPage;
import com.es.phoneshop.web.controller.constant.ControllerConstant.ModelAttribute;
import com.es.phoneshop.web.controller.constant.ControllerConstant.RequestParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.es.core.model.phone.PhoneDao;

import java.util.Optional;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    private static final int PAGE_PHONES_COUNT = 10;
    private static final int DEFAULT_PAGE_NUMBER = 0;
    @Resource
    private PhoneDao phoneDao;

    @GetMapping
    public String showProductList(@RequestParam(value = RequestParameter.PAGE_NUMBER, required = false) String page,
                                  @RequestParam(value = RequestParameter.SORT_FIELD, required = false) String sort,
                                  @RequestParam(value = RequestParameter.SORT_ORDER, required = false) String order,
                                  @RequestParam(value = RequestParameter.QUERY, required = false) String search,
                                  Model model) {
        int pageNumber = Optional.ofNullable(page).map(number -> {
            try {
                return Integer.parseUnsignedInt(page) - 1;
            } catch (NumberFormatException exception) {
                return DEFAULT_PAGE_NUMBER;
            }
        }).orElse(DEFAULT_PAGE_NUMBER);
        Pageable pageable = PageRequest.of(pageNumber, PAGE_PHONES_COUNT);
        model.addAttribute(ModelAttribute.PHONES , phoneDao.findAllInStock(sort, order, search, pageable));
        return JspPage.PRODUCT_LIST;
    }
}
