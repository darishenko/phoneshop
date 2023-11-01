<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--@elvariable id="quickCartAddingDto" type="com.es.phoneshop.web.dto.QuickCartAddingDto"--%>
<jsp:useBean id="cart" scope="session" class="com.es.core.model.cart.Cart"/>


<tags:master pageTitle="Quick cart adding" _cart="${cart}"/>
<a href="<c:url value="/productList" />" class="btn btn-primary my-3 w-100 fw-bold">Back to phone
    list</a>

<p class="text-success">${quickCartAddingDto.successMessage}</p>
<p class="text-danger">${quickCartAddingDto.errorMessage}</p>

<c:if test="${not empty addedPhones}">
    <p>ADDED PHONES</p>
    <table class="table table-bordered my-3">
        <thead class="table-dark">
        <tr>
            <td>Number</td>
            <td>Model</td>
            <td>Quantity</td>
        </tr>
        </thead>
        <c:forEach varStatus="ind" var="item" items="${addedPhones}">
            <tr>
                <td>${ind.index + 1}</td>
                <td>${item.key}</td>
                <td>${item.value}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>


<form:form modelAttribute="quickCartAddingDto" method="POST">
    <table class="table table-bordered my-3">
        <thead class="table-dark">
        <tr>
            <td>Number</td>
            <td>Model</td>
            <td>Quantity</td>
        </tr>
        </thead>
        <c:forEach begin="0" end="7" varStatus="ind">
            <tr>
                <td>${ind.index + 1}</td>
                <td>
                    <form:input path="items[${ind.index}].model"/>
                    <div>
                        <form:errors path="items[${ind.index}].model" cssClass="text-danger"/>
                    </div>
                </td>
                <td>
                    <form:input path="items[${ind.index}].quantity"/>
                    <div>
                        <form:errors path="items[${ind.index}].quantity" cssClass="text-danger"/>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>

    <input type="submit" value="Add to cart" class="btn btn-primary btn-lg"/>
</form:form>
