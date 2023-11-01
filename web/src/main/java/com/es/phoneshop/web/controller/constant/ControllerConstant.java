package com.es.phoneshop.web.controller.constant;

public interface ControllerConstant {
    interface JspPage {
        String PRODUCT_LIST = "productList";
        String PRODUCT_DETAILS = "productDetails";
        String CART = "cart";
        String ORDER = "order";
        String ORDER_OVERVIEW = "orderOverview";
        String ORDERS_FOR_ADMIN = "adminOrders";
        String ORDER_FOR_ADMIN = "adminOrderOverview";
        String QUICK_CART_ADDING = "quickCartAdding";
    }

    interface ModelsAttribute {
        String PHONES = "phones";
        String PHONE = "phone";
        String CART = "cart";
        String CART_DTO = "cartDto";
        String ORDER_DETAIL_DTO = "orderDetails";
        String ORDER = "order";
        String ORDERS = "orders";
        String QUICK_CART_ADDING_DTO = "quickCartAddingDto";
        String ADDED_PHONES = "addedPhones";
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
        String ALL_ADDED_TO_CART = "All phones added to cart successfully!";
        String ADD_TO_CART = "Phone added to cart successfully!";
        String UPDATE = "Updated successfully!";
    }

    interface ErrorMessage {
        String ADD_TO_CART_ERROR = "Adding to cart error.";
        String WRONG_FORMAT = "Wrong format!";
        String EMPTY_CART = "Cart is empty!";
        String ORDER_ERROR = "Some items were out of stock! They were deleted from cart.\nTry to make your order again!";
    }

}
