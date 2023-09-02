package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.es.core.model.phone.PhoneDao;

import java.util.Optional;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    private static final int PAGE_PHONES_COUNT = 10;
    @Resource
    private PhoneDao phoneDao;

    @GetMapping
    public String showProductList(@RequestParam(value = "page", required = false) String page,
                                  @RequestParam(value = "sort", required = false) String sort,
                                  @RequestParam(value = "order", required = false) String order,
                                  @RequestParam(value = "search", required = false) String search,
                                  Model model) {
        //#TODO: Pagination
        int offset = (Optional.ofNullable(page).map(Integer::valueOf).orElse(1) - 1) * PAGE_PHONES_COUNT;
        model.addAttribute("phones", phoneDao.findAllInStock(offset, PAGE_PHONES_COUNT, sort, order, search));
        return "productList";
    }
}
