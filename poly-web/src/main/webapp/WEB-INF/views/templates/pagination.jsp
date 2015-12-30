<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="firstUrl" value="/" />
<c:url var="lastUrl" value="${query }p=${totalPages}" >
</c:url>
<c:url var="prevUrl" value="${query }p=${currIndex < 2 ? 1: currIndex - 1}" >
</c:url>
<c:url var="nextUrl" value="${query }p=${currIndex >= totalPages ? totalPages: currIndex + 1}" >
</c:url>
    <ul class="pagination ">
    	<c:if test="${totalPages > 1 }">
        <c:choose>
            <c:when test="${currIndex == 1}">
                <li class="prev disabled"><a class="prev-a">&nbsp; </a></li>
            </c:when>
            <c:otherwise>
                <li class="prev"><a href="${prevUrl }" class="prev-a">&nbsp;</a></li>
            </c:otherwise>
        </c:choose>
        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
            <c:url var="pageUrl" value="${query }p=${i}" />
            <c:choose>
                <c:when test="${i == currIndex}">
                    <li class="active"><a href="${pageUrl}"><c:out value="${i}" /></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageUrl}"><c:out value="${i}" /></a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:choose>
            <c:when test="${currIndex == totalPages}">
                <li class="next disabled"><a class="next-a">&nbsp;</a></li>
            </c:when>
            <c:otherwise>
                <li class="next"><a href="${nextUrl }" class="next-a">&nbsp;</a></li>
            </c:otherwise>
        </c:choose>
        </c:if>
    </ul>
