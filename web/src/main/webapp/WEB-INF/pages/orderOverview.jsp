<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--@elvariable id="orderDetails" type="com.es.phoneshop.web.dto.OrderDetailsDto"--%>
<jsp:useBean id="cart" scope="session" class="com.es.core.model.cart.Cart"/>


<tags:master pageTitle="Order owerview" cart="${cart}"/>
<a href="${pageContext.request.contextPath}/productList" class="btn btn-primary my-3 w-100 fw-bold">Back to phone
    list</a>
<h2>Thank you for your order!</h2>
<tags:orderItemsList itemsList="${order.orderItems}" subtotal="${order.subtotal}" delivery="${order.deliveryPrice}"
                     total="${order.totalPrice}"/>

<table class="table table-bordered fixed-width-table w-50 table-striped-columns">
    <tbody>
    <tr>
        <th scope="row">
            <p><strong>First Name</strong></p>
        </th>
        <td>
            <p>${order.firstName}</p>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <p><strong>Last Name</strong></p>
        </th>
        <td>
            <p>${order.lastName}</p>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <p><strong>Address</strong></p>
        </th>
        <td>
            <p>${order.deliveryAddress}</p>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <p><strong>Phone</strong></p>
        </th>
        <td>
            <p>${order.contactPhoneNo}</p>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <span>${order.additionalInfo}</span>
        </td>
    </tr>
    </tbody>
</table>

