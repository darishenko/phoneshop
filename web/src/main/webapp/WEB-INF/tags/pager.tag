<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag language="java" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageCount" required="true" %>
<%@ attribute name="currentPage" required="true" %>

<%
    int PREV_COUNT = 4;
    int NEXT_COUNT = 4;
    int TOTAL_COUNT = 9;
    int currentPageNumber = Integer.parseUnsignedInt(currentPage);
    int count = Integer.parseUnsignedInt(pageCount);
    int startPageNumber = currentPageNumber > PREV_COUNT ? (currentPageNumber - PREV_COUNT) : 1;
    int lastPageNumber = (count - currentPageNumber) > NEXT_COUNT ? (currentPageNumber + NEXT_COUNT) : count;
    int pageRange = lastPageNumber - startPageNumber + 1;
    int dif = TOTAL_COUNT - pageRange;

    if (dif > 0 && count > TOTAL_COUNT) {
        if (startPageNumber <= PREV_COUNT) {
            lastPageNumber += dif;
        } else {
            startPageNumber -= dif;
        }
    }
%>

<c:if test="${pageCount > 0}">
    <ul class="pagination">
        <li class="page-item ${currentPage eq 1 ? ' disabled' : ' '}">
            <a class="page-link"
               href="?page=${currentPage eq 1 ? '1' : currentPage - 1}&sort=${param.sort}&order=${param.order}&search=${param.search}"
               aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <c:forEach begin="<%=startPageNumber%>" var="pageNumber" end="<%=lastPageNumber%>">
            <li class="page-item ${currentPage eq pageNumber ? 'active' : ' '}">
                <a class="page-link"
                   href="?page=${pageNumber}&sort=${param.sort}&order=${param.order}&search=${param.search}">
                        ${pageNumber}
                </a>
            </li>
        </c:forEach>
        <li class="page-item ${currentPage eq pageCount ? ' disabled' : ' '}">
            <a class="page-link"
               href="?page=${currentPage eq pageCount ? pageCount : currentPage + 1}&sort=${param.sort}&order=${param.order}&search=${param.search}"
               aria-label="Previous">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>
</c:if>