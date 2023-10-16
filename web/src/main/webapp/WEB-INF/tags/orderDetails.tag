<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ attribute name="order" required="true" type="com.es.core.model.order.Order" %>

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