const SHOW_TIME = 10000;
const HIDE_TIME = 1000;

const HTML_ID_QUANTITY = "quantity";

const CART_QUANTITY_FIELD = "totalQuantity";
const CART_COST_FIELD = "totalCost";
const ADD_T0_CART_MESSAGE_FIELD = "message";

const MESSAGE_CART_COUNT = "\nPhone count in cart is ";
const MESSAGE_AVAILABLE_COUNT = "\nAvailable count is "

const HTML_CLASS_SUCCESS_MESSAGE = "text-success";
const HTML_CLASS_ERROR_MESSAGE = "text-danger";

function add_to_cart(phoneId, url) {
    $('.addToCartResultMessage').hide();

    const quantity = document.getElementById(HTML_ID_QUANTITY + phoneId).value;
    const data = {phoneId: phoneId, quantity: quantity}
    $.ajax({
        url: url + "/ajaxCart",
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        data: JSON.stringify(data),
        success: function (response) {
            const messageElement = $(document.getElementById(phoneId + "_addToCartResultMessage"));
            showMessageWithAnimation(messageElement, response[ADD_T0_CART_MESSAGE_FIELD], HTML_CLASS_SUCCESS_MESSAGE);
            document.getElementById("cartTotalQuantity").textContent = response[CART_QUANTITY_FIELD];
            document.getElementById("cartTotalCost").textContent = response[CART_COST_FIELD] + " $";
        }, error: function (error) {
            const messageElement = $(document.getElementById(phoneId + "_addToCartResultMessage"));
            let errorResponse = error.responseJSON;
            let message;
            if (errorResponse !== undefined) {
                message = `${errorResponse.message} ${MESSAGE_CART_COUNT} ${error.responseJSON.cartQuantity}.
                 ${MESSAGE_AVAILABLE_COUNT} ${error.responseJSON.availableUserQuantity}.`;
            } else {
                message = error.responseText;
            }
            showMessageWithAnimation(messageElement, message, HTML_CLASS_ERROR_MESSAGE);
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