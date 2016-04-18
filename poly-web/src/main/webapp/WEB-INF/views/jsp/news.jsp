<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="menu" uri="/WEB-INF/mytag/MenuHelper.tld" %>
<c:set var="baseURL" value="${menu:getContextPath()}" />
    <div class="col-sm-9 contentmainright">
    
	   <div class="clearfix">
			<h1 class=" titlecontent title1">${news_header}</h1>
			<ul class=" row step">
			<c:forEach items="${dsNews}" var="item">
      <li class="col-md-3"> 
      <a href="${baseURL}/news_detail/${item.slug}-${item.hashid }"> 
        <img src="${item.avatar}" class="img-responsive" alt=""> </a>
       <a href="${baseURL}/news_detail/${item.slug}-${item.hashid }">   <h4>${item.title }</h4></a>
        <p>
          ${item.content}
        </p>
       </li>
     	</c:forEach>
    </ul>
	   </div>	
	    <div class="clearfix">
	    <%@ include file="/WEB-INF/views/templates/pagination.jsp" %>
	    </div>  
    </div>
    <!--endcontentmain--> 
    