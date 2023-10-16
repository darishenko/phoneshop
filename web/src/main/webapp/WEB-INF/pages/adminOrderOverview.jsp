<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="order" type="com.es.core.model.order.Order"--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<tags:master pageTitle="Order owerview" _cart="${null}"/>
<div class="container text-center">
    <div class="row">
        <p class="col fs-4 text-start">Order number: ${order.id}</p>
        <p class="col fs-4 text-end">Order status: ${order.status}</p>
    </div>
</div>
<a href="${pageContext.request.contextPath}/admin/orders" class="col btn btn-primary my-3 fw-bold w-100">Back</a>
<tags:orderItemsList itemsList="${order.orderItems}" subtotal="${order.subtotal}" delivery="${order.deliveryPrice}"
                     total="${order.totalPrice}"/>
<tags:orderDetails order="${order}"/>
<div class="container text-center">
    <div class="row">
        <form:form method="PUT" cssClass="col text-start">
            <input type="hidden" name="orderStatus" value="DELIVERED">
            <button type="submit" class="btn btn btn-success my-3 fw-bold">Delivered</button>
        </form:form>
        <form:form method="PUT" cssClass="col text-end">
            <input type="hidden" name="orderStatus" value="REJECTED">
            <button type="submit" class="btn btn-danger my-3 fw-bold">Rejected</button>
        </form:form>
        <div class="col"></div>
        <div class="col"></div>
    </div>
</div>
