<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="cart" scope="session" class="com.es.core.model.cart.Cart"/>


<tags:master pageTitle="Phones" _cart="${cart}"/>
<p class="text-center">Found
    <c:out value="${phones.totalElements}"/> phones.
<p></p>
<form class="text-center">
    <label>
        <input name="search" value="${param.search}">
    </label>
    <button class="btn btn-dark">Search</button>
</form>
<tags:pager pageCount="${phones.totalPages}" currentPage="${phones.number + 1}"/>
<table class="table table-bordered">
    <thead class="table-dark">
    <tr>
        <td>Image</td>
        <td>Brand
            <tags:sortLink sort="brand" order="asc"/>
            <tags:sortLink sort="brand" order="desc"/>
        </td>
        <td>Model
            <tags:sortLink sort="model" order="asc"/>
            <tags:sortLink sort="model" order="desc"/>
        </td>
        <td>Color</td>
        <td>Display size
            <tags:sortLink sort="displaySizeInches" order="asc"/>
            <tags:sortLink sort="displaySizeInches" order="desc"/>
        </td>
        <td>Price
            <tags:sortLink sort="price" order="asc"/>
            <tags:sortLink sort="price" order="desc"/>
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
            <td>
                <a href="<c:url value="/productDetails/${phone.id}" />">
                        ${phone.model}
                </a>
            </td>
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
                <button onclick="add_phone_to_cart(${phone.id}, '${pageContext.request.contextPath}')"
                        class="btn btn-outline-success">Add to
                </button>
            </td>
        </tr>
    </c:forEach>
</table>
<tags:pager pageCount="${phones.totalPages}" currentPage="${phones.number + 1}"/>