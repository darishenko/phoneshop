<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<html>
<head>
    <title>${pageTitle}</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
</head>
<body>
<header>
    <a href="${pageContext.servletContext.contextPath}">Phonify</a>
</header>
<main>
    <hr>
    <jsp:doBody/>
</main>
</body>
</html>