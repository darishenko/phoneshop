<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag language="java" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="cart" required="true" type="com.es.core.model.cart.Cart" %>

<div class="card my-2" style="width: 6rem;">
    <div class="card-body px-1 py-1 mx-auto">
        <a href="<c:url value="/cart" />" class="fw-bold text-decoration-none">My Cart</a>
        <p class="card-text my-0">
            <span id="cartTotalQuantity">${cart.totalQuantity}</span>
            <span>item<c:if test="${cart.totalQuantity > 1}">s</c:if></span>
        </p>
        <p class="card-text my-0" id="cartTotalCost">${cart.totalCost} $</p>
    </div>
</div>