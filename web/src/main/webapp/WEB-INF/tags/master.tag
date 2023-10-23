<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
            <a class="navbar-brand fw-bold fs-1 my-auto" href="<c:url value="/" />">Phonify</a>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <sec:authorize access="isAuthenticated()">
                            <c:if test="${!requestScope['javax.servlet.forward.request_uri'].contains('/admin')}">
                                <a href="<c:url value="/admin/orders" />" class="nav-link active fw-bold">Admin</a>
                            </c:if>
                        </sec:authorize>
                    </li>
                    <li class="nav-item">
                        <sec:authorize access="!isAuthenticated()">
                            <a href="<c:url value="/login" />" class="nav-link active fw-bold">Login</a>
                        </sec:authorize>

                        <sec:authorize access="isAuthenticated()">
                            <a href="<c:url value="/perform_logout" />" class="nav-link active fw-bold">Logout</a>
                        </sec:authorize>
                    </li>
                </ul>
                <div class="d-flex">
                    <div class="container text-center">
                        <div class="row">
                            <div class="col nav-item">
                                <sec:authorize access="isAuthenticated()">
                                    <p class="navbar-text fw-bold text-light">Welcome, <sec:authentication property="name"/>!</p>
                                </sec:authorize>
                            </div>
                            <div class="col">
                                <c:if test="${_cart ne null}">
                                    <div class="d-flex">
                                        <tags:cart cart="${_cart}"/>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </nav>
</header>
<main>
    <jsp:doBody/>
</main>
</body>
</html>