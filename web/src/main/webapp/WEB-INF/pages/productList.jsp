<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<tags:master pageTitle="Phones"></tags:master>
<h1>Phones</h1>
<p>
    Found
    <c:out value="${phones.size()}"/> phones.
<p></p>
<form>
    <label>
        <input name="search" value="${param.search}">
    </label>
    <button>Search</button>
</form>
<table border="1px">
    <thead>
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
            <tags:sortLink sort="displaySize" order="asc"></tags:sortLink>
            <tags:sortLink sort="displaySize" order="desc"></tags:sortLink>
        </td>
        <td>Price
            <tags:sortLink sort="price" order="asc"></tags:sortLink>
            <tags:sortLink sort="price" order="desc"></tags:sortLink>
        </td>
        <td>Quantity</td>
        <td>Action</td>
    </tr>
    </thead>
    <c:forEach var="phone" items="${phones}">
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
                    <input value="1">
                </label>
            </td>
            <td>
                <form method="post">
                    <input type="submit" value="Add to">
                    <input type="hidden" name="productId" value="${phone.id}">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>