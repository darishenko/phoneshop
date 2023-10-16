<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%
    String datePattern = "dd.MM.yyyy HH:mm";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
%>


<tags:master pageTitle="Orders" _cart="${null}"/>
<h2>Orders</h2>

<table class="table table-bordered">
    <thead class="table-dark">
    <tr>
        <td>Order number</td>
        <td>Customer</td>
        <td>Phone</td>
        <td>Address</td>
        <td>Date</td>
        <td>Total price</td>
        <td>Status</td>
    </tr>
    </thead>
    <tbody class="table-group-divider">
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/admin/orders/${order.id}">${order.id}</a>
            </td>
            <td>${order.firstName} ${order.lastName}</td>
            <td>${order.contactPhoneNo}</td>
            <td>${order.deliveryAddress}</td>

            <td>
                <c:set value="<%=formatter%>" var="format"/>
                    ${order.date.format(format)}
            </td>
            <td>${order.totalPrice}$</td>
            <td>${order.status}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>


