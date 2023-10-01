<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--@elvariable id="cartDto" type="com.es.phoneshop.web.dto.CartDto"--%>
<jsp:useBean id="cart" scope="session" class="com.es.core.model.cart.Cart"/>


<tags:master pageTitle="Phones" cart="${cart}"></tags:master>
<a href="${pageContext.request.contextPath}/productList" class="btn btn-primary my-3 w-100 fw-bold">Back to phone
    list</a>

<form:form modelAttribute="cartDto" method="PUT">
    <table class="table table-bordered">
        <thead class="table-dark">
        <tr>
            <td>Brand</td>
            <td>Model</td>
            <td>Color</td>
            <td>Display size</td>
            <td>Price</td>
            <td>Quantity</td>
            <td>Action</td>
        </tr>
        </thead>
        <c:forEach var="item" items="${cart.items}" varStatus="ind">
            <tr>
                <td>${item.phone.brand}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/productDetails/${item.phone.id}">
                            ${item.phone.model}
                    </a>
                </td>
                <td>
                    <c:forEach var="color" items="${item.phone.colors}">
                        <p>${color.code}</p>
                    </c:forEach>
                </td>
                <td>${item.phone.displaySizeInches}''</td>
                <td>${item.phone.price}$</td>
                <td>
                    <form:hidden path="cartItems[${ind.index}].phoneId"/>
                    <form:input path="cartItems[${ind.index}].quantity"/>
                    <div>
                        <c:if test="${empty cartDto.cartItems[ind.index].errorMessage}">
                            <form:errors path="cartItems[${ind.index}].quantity" cssClass="text-danger"/>
                        </c:if>
                        <span class="text-danger">${cartDto.cartItems[ind.index].errorMessage}</span>
                        <span class="text-success">${cartDto.cartItems[ind.index].successMessage}</span>
                    </div>
                </td>
                <td>
                    <button type="button"
                            onclick="delete_phone_from_cart(${item.phone.id}, '${pageContext.request.contextPath}')"
                            class="btn btn-outline-danger">Delete
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>

    <input type="submit" value="Update" class="btn btn-primary btn-lg"/>
</form:form>
