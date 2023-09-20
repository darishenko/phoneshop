<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="cart" scope="session" class="com.es.core.cart.Cart"/>


<tags:master pageTitle="Phones"></tags:master>
<p>Found <c:out value="${phones.totalElements}"/> phones.
<p></p>
<div class="card mb-3" style="width: 12rem;">
    <div class="card-body">
        <h5 class="card-title">My Cart</h5>
        <p class="card-text">
            <span id="cartTotalQuantity">${cart.totalQuantity}</span>
            <span>item<c:if test="${cart.totalQuantity > 1}">s</c:if></span>
        </p>
        <p class="card-text" id="cartTotalCost">${cart.totalCost} $</p>
        <a href="#" class="btn btn-primary">Go to Cart</a>
    </div>
</div>
<form>
    <label>
        <input name="search" value="${param.search}">
    </label>
    <button class="btn btn-dark">Search</button>
</form>
<tags:pager pageCount="${phones.totalPages}" currentPage="${phones.number + 1}"></tags:pager>
<table class="table table-bordered">
    <thead class="table-dark">
    <tr>
        <td>Image</td>
        <td>Brand
            <tags:sortLink sort="brand" order="asc"></tags:sortLink>
            <tags:sortLink sort="brand" order="desc"></tags:sortLink>
        </td>
        <td>Model
            <tags:sortLink sort="model" order="asc"></tags:sortLink>
            <tags:sortLink sort="model" order="desc"></tags:sortLink>
        </td>
        <td>Color</td>
        <td>Display size
            <tags:sortLink sort="displaySizeInches" order="asc"></tags:sortLink>
            <tags:sortLink sort="displaySizeInches" order="desc"></tags:sortLink>
        </td>
        <td>Price
            <tags:sortLink sort="price" order="asc"></tags:sortLink>
            <tags:sortLink sort="price" order="desc"></tags:sortLink>
        </td>
        <td>Quantity</td>
        <td>Action</td>
    </tr>
    </thead>
    <c:forEach var="phone" items="${phones.toList()}">
        <tr>
            <td>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
            </td>
            <td>${phone.brand}</td>
            <td>${phone.model}</td>
            <td>
                <c:forEach var="color" items="${phone.colors}">
                    <p>${color.code}</p>
                </c:forEach>
            </td>
            <td>${phone.displaySizeInches}''</td>
            <td>${phone.price}$</td>
            <td>
                <label>
                    <input value="1" name="quantity" id="quantity${phone.id}">
                </label>
                <input type="hidden" value="${phone.id}" name="phoneId">
                <p id="${phone.id}_addToCartResultMessage" class="addToCartResultMessage"></p>
            </td>
            <td>
                <button onclick="add_to_cart(${phone.id}, '${pageContext.request.contextPath}')">Add to</button>
            </td>
        </tr>
    </c:forEach>
</table>
<tags:pager pageCount="${phones.totalPages}" currentPage="${phones.number + 1}"></tags:pager>