<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--@elvariable id="orderDetails" type="com.es.phoneshop.web.dto.OrderDetailsDto"--%>
<jsp:useBean id="cart" scope="session" class="com.es.core.model.cart.Cart"/>


<tags:master pageTitle="Order" _cart="${cart}"/>
<a href="<c:url value="/productList" />" class="btn btn-primary my-3 w-100 fw-bold">Back to phone
    list</a>

<form:form modelAttribute="orderDetails" method="POST">
    <c:set var="total" value="${cart.totalCost.add(orderDetails.deliveryPrice)}"/>
    <tags:orderItemsList itemsList="${cart.items}" subtotal="${cart.totalCost}" delivery="${orderDetails.deliveryPrice}"
                         total="${total}"/>

    <h4 class="text-danger my-3 text-bold">${orderDetails.resultMessage}</h4>
    <table class="table table-bordered fixed-width-table w-50">
        <tbody>
        <tr>
            <th scope="row">
                <label for="firstName">First Name*</label>
            </th>
            <td>
                <form:input path="firstName"/>
                <p class="text-danger">${orderDetails.errors.get('firstName')}</p>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <label for="lastName">Last Name*</label>
            </th>
            <td>
                <form:input path="lastName"/>
                <p class="text-danger">${orderDetails.errors.get('lastName')}</p>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <label for="deliveryAddress">Address*</label>
            </th>
            <td>
                <form:input path="deliveryAddress"/>
                <p class="text-danger">${orderDetails.errors.get('deliveryAddress')}</p>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <label for="contactPhoneNo">Phone*</label>
            </th>
            <td>
                <form:input path="contactPhoneNo"/>
                <p class="text-danger">${orderDetails.errors.get('contactPhoneNo')}</p>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <label for="additionalInfo">Additional information</label>
            </th>
            <td>
                <form:textarea path="additionalInfo"/>
            </td>
        </tr>
        </tbody>
    </table>

    <input type="submit" value="Order" class="btn btn-primary btn-lg my-3"/>
</form:form>