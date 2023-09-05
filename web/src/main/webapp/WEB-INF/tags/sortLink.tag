<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sort" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="?sort=${sort}&order=${order}&search=${param.search}"
    class="${sort eq param.sort and order eq param.order ? ' disabled ': ' '}
    link-underline link-underline-opacity-0 icon-link icon-link-hover">
    ${order eq "asc" ? '&#11014;' : '&#11015;'}
</a>