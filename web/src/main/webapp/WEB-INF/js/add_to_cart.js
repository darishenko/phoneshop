const SHOW_TIME = 10000;
const HIDE_TIME = 1000;

const JSON_CART_QUANTITY_FIELD = "totalQuantity";
const JSON_CART_COST_FIELD = "totalCost";
const JSON_ADD_T0_CART_MESSAGE_FIELD = "message";

const HTML_CLASS_SUCCESS_MESSAGE = "text-success";
const HTML_CLASS_ERROR_MESSAGE = "text-danger";

function add_to_cart(phoneId, url) {
    $('.addToCartResultMessage').hide();

    $.ajax({
        url: url + "/ajaxCart",
        method: "POST",
        data: $("#" + phoneId).serialize(),
        success: function (response) {
            let responseMap = JSON.parse(response);
            const messageElement = $(document.getElementById(phoneId + "_addToCartResultMessage"));
            showMessageWithAnimation(messageElement, responseMap[JSON_ADD_T0_CART_MESSAGE_FIELD],
                HTML_CLASS_SUCCESS_MESSAGE);
            document.getElementById("cartTotalQuantity").textContent = responseMap[JSON_CART_QUANTITY_FIELD];
            document.getElementById("cartTotalCost").textContent = responseMap[JSON_CART_COST_FIELD] + " $";
        },
        error: function (error) {
            const messageElement = $(document.getElementById(phoneId + "_addToCartResultMessage"));
            showMessageWithAnimation(messageElement, error.responseText, HTML_CLASS_ERROR_MESSAGE);
        }
    });
}

function showMessageWithAnimation(messageElement, message, addElementClass) {
    messageElement.addClass(addElementClass);
    messageElement.text(message);
    messageElement.show();

    setTimeout(function () {
        messageElement.removeClass(addElementClass);
        messageElement.hide(HIDE_TIME);
    }, SHOW_TIME);
}