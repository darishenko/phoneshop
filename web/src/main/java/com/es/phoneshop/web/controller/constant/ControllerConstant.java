package com.es.phoneshop.web.controller.constant;

public interface ControllerConstant {
    interface JspPage {
        String PRODUCT_LIST = "productList";
        String PRODUCT_DETAILS = "productDetails";
        String CART = "cart";
        String ORDER = "order";
        String ORDER_OVERVIEW = "orderOverview";
    }

    interface ModelsAttribute {
        String PHONES = "phones";
        String PHONE = "phone";
        String CART = "cart";
        String CART_DTO = "cartDto";
        String ORDER_DETAIL_DTO = "orderDetails";
        String ORDER = "order";
    }

    interface RequestParameter {
        String SORT_ORDER = "order";
        String SORT_FIELD = "sort";
        String PAGE_NUMBER = "page";
        String QUERY = "search";
    }

    interface Pagination {
        int PAGE_PHONES_COUNT = 10;
        int DEFAULT_PAGE_NUMBER = 1;
        String DEFAULT_PAGE_NUMBER_STRING = "1";
    }

    interface SuccessMessage {
        String ADD_TO_CART = "Phone added to cart successfully!";
        String UPDATE = "Updated successfully!";
        String ACCEPTED_ORDER = "Your order has been accepted!";
    }

    interface ErrorMessage {
        String ADD_TO_CART_ERROR = "Adding to cart error.";
        String WRONG_FORMAT = "Wrong format!";
        String ORDER_ERROR = "Some items were out of stock! They were deleted from cart.\nTry to make your order again!";
    }

}
