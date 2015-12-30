<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <div class="col-sm-9 contentmainright">
          <c:forEach items="${lstContent }" var="item">
	         <c:if test="${item.type==1}"> <h1 class="titlecontent">${item.content}</h1></c:if>
	         <c:if test="${item.type==2}"> <p>${item.content}</p></c:if>
	         <c:if test="${item.type==3}">  <h2 class="titlecontent2">${item.content}</h2></c:if>
         </c:forEach> 
    </div>
    <!--endcontentmain--> 
    
  </div>
</div>