<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ attribute name="itemsList" required="true" type="java.util.List" %>
<%@ attribute name="subtotal" required="true" type="java.math.BigDecimal" %>
<%@ attribute name="delivery" required="true" type="java.math.BigDecimal" %>
<%@ attribute name="total" required="true" type="java.math.BigDecimal" %>

<table class="table table-bordered">
    <thead class="table-dark">
    <tr>
        <td>Brand</td>
        <td>Model</td>
        <td>Color</td>
        <td>Display size</td>
        <td>Quantity</td>
        <td>Price</td>
    </tr>
    </thead>
    <tbody class="table-group-divider">
    <c:forEach var="item" items="${itemsList}" varStatus="ind">
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
        <td>${item.quantity}</td>
        <td>${item.phone.price}$</td>
    </tr>
    </c:forEach>

    <tr>
        <td colspan="4"></td>
        <th scope="row">Subtotal</th>
        <td>${subtotal}$</td>
    </tr>
    <tr>
        <td colspan="4"></td>
        <th scope="row">Delivery</th>
        <td>${delivery}$</td>
    </tr>
    <tr>
        <td colspan="4"></td>
        <th scope="row">TOTAL</th>
        <td>${total}$</td>
    </tr>
    </tbody>
</table>


