<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ attribute name="pageTitle" required="true" %>
<%@ attribute name="_cart" required="false" type="com.es.core.model.cart.Cart" %>

<html>
<head>
    <title>${pageTitle}</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script type="text/javascript" src="<c:url value="/resources/cart_events.js" />"></script>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand fw-bold fs-1 my-auto" href="${pageContext.servletContext.contextPath}">Phonify</a>
            <c:if test="${_cart ne null}">
                <div class="d-flex">
                    <tags:cart cart="${_cart}"/>
                </div>
            </c:if>
        </div>
    </nav>
</header>
<main>
    <jsp:doBody/>
</main>
</body>
</html>