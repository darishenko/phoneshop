<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="cart" scope="session" class="com.es.core.model.cart.Cart"/>
<style>
    .fixed-width-table th,
    .fixed-width-table td {
        width: 50%;
    }
</style>


<tags:master pageTitle="Phone details" _cart="${cart}"/>
<a href="${pageContext.request.contextPath}/productList" class="btn btn-primary my-3 w-100 fw-bold">Back to phone
    list</a>

<div class="container text-center my-3">
    <div class="row">
        <div class="col">
            <h1>${phone.model}</h1>
            <p>${phone.description}</p>
            <div>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
            </div>

            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Price: ${phone.price}$</h5>
                    <label>
                        <input value="1" name="quantity" id="quantity${phone.id}">
                    </label>
                    <p id="${phone.id}_addToCartResultMessage" class="addToCartResultMessage"></p>
                    <div class="mt-2">
                        <button class="btn btn-primary"
                                onclick="add_phone_to_cart(${phone.id}, '${pageContext.request.contextPath}')">Add to
                            cart
                        </button>
                    </div>
                </div>
            </div>

        </div>

        <div class="col">
            <p class="text-start fw-bold text-decoration-underline">Display</p>
            <table class="table table-bordered fixed-width-table">
                <tbody>
                <tr>
                    <th scope="row">Size</th>
                    <td>${phone.displaySizeInches}''</td>
                </tr>
                <tr>
                    <th scope="row">Resolution</th>
                    <td>${phone.displayResolution}</td>
                </tr>
                <tr>
                    <th scope="row">Technology</th>
                    <td>${phone.displayTechnology}</td>
                </tr>
                <tr>
                    <th scope="row">Pixel density</th>
                    <td>${phone.pixelDensity}</td>
                </tr>
                </tbody>
            </table>

            <p class="text-start fw-bold text-decoration-underline">Dimensions & weight</p>
            <table class="table table-bordered fixed-width-table">
                <tbody>
                <tr>
                    <th scope="row">Length</th>
                    <td>${phone.lengthMm}mm</td>
                </tr>
                <tr>
                    <th scope="row">Width</th>
                    <td>${phone.widthMm}mm</td>
                </tr>
                <tr>
                    <th scope="row">Color</th>
                    <td>
                        <c:forEach var="color" items="${phone.colors}">
                            <p>${color.code}</p>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <th scope="row">Weight</th>
                    <td>${phone.weightGr}</td>
                </tr>
                </tbody>
            </table>

            <p class="text-start fw-bold text-decoration-underline">Camera</p>
            <table class="table table-bordered fixed-width-table">
                <tbody>
                <tr>
                    <th scope="row">Front</th>
                    <td>${phone.frontCameraMegapixels} megapixels</td>
                </tr>
                <tr>
                    <th scope="row">Back</th>
                    <td>${phone.backCameraMegapixels} megapixels</td>
                </tr>
                </tbody>
            </table>

            <p class="text-start fw-bold text-decoration-underline">Battery</p>
            <table class="table table-bordered fixed-width-table">
                <tbody>
                <tr>
                    <th scope="row">Talk time</th>
                    <td>${phone.talkTimeHours} hours</td>
                </tr>
                <tr>
                    <th scope="row">Stand by time</th>
                    <td>${phone.standByTimeHours} hours</td>
                </tr>
                <tr>
                    <th scope="row">Battery capacity</th>
                    <td>${phone.batteryCapacityMah}mAh</td>
                </tr>
                </tbody>
            </table>

            <p class="text-start fw-bold text-decoration-underline">Other</p>
            <table class="table table-bordered fixed-width-table">
                <tbody>
                <tr>
                    <th scope="row">Device type</th>
                    <td>${phone.deviceType}</td>
                </tr>
                <tr>
                    <th scope="row">Bluetooth</th>
                    <td>${phone.bluetooth}</td>
                </tr>
                </tbody>
            </table>

        </div>
    </div>
</div>