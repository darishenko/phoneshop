<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sort" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="?sort=${sort}&order=${order}&search=${param.search}"
   style="text-decoration: none; ${sort eq param.sort and order eq param.order ? 'font-weight: bold;': ''}">
    ${order eq "asc" ? '&#11014;' : '&#11015;'}</a>