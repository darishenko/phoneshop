<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="cart" scope="session" class="com.es.core.model.cart.Cart"/>


<tags:master pageTitle="Order owerview" _cart="${cart}"/>
<a href="${pageContext.request.contextPath}/productList" class="btn btn-primary my-3 w-100 fw-bold">Back to phone
    list</a>
<h2>Thank you for your order!</h2>
<tags:orderItemsList itemsList="${order.orderItems}" subtotal="${order.subtotal}" delivery="${order.deliveryPrice}"
                     total="${order.totalPrice}"/>
<tags:orderDetails order="${order}"/>

