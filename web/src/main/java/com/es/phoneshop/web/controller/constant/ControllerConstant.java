package com.es.phoneshop.web.controller.constant;

public interface ControllerConstant {
    interface JspPage {
        String PRODUCT_LIST = "productList";
    }

    interface ModelAttribute {
        String PHONES = "phones";
        String CART = "cart";
    }

    interface RequestParameter {
        String SORT_ORDER = "order";
        String SORT_FIELD = "sort";
        String PAGE_NUMBER = "page";
        String QUERY = "search";
    }

    interface Pagination {
        int PAGE_PHONES_COUNT = 10;
        int DEFAULT_PAGE_NUMBER = 0;
    }

    interface SuccessMessage {
        String ADD_TO_CART = "Phone added to cart successfully!";
    }

    interface ErrorMessage {
        String NOT_ENOUGH_IN_STOCK = "Not enough items in stock!";
        String ADD_TO_CART_ERROR = "Adding to cart error.";
    }

    interface InfoMessage {
        String AVAILABLE_PHONE_COUNT = "Available count is ";
    }

}