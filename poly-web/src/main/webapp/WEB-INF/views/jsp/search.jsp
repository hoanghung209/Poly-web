<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
   
<div class="thuvien clearfix">
     <div class="title1 clearfix">
		<h2>${resultTitle}</h2>
    	
	 </div>
     <div class="tab-content search_">
     <c:set var="cnt" value="1"></c:set>
     <c:forEach items="${lstResult}" var="item">
		<c:if test="${cnt%2==1 }"><div class="col-sm-12 row"></c:if>
		<div class="col-sm-6">
			<div class="col-sm-2">
				<div class="img-thumbnail search_title">
					<div class="date_d">${item.day }</div>
					<div class="date_m">${item.month }</div>
				</div>
			</div>
			<div class="col-sm-10 search_ct">
			<h3 class="text-left"><a href="${item.href}">${item.title }</a></h3>
			<p class="text-justify">${item.content }</p>
			</div>
		</div>
		
		<c:if test="${cnt%2==0 }"></div></c:if>
		<c:set var="cnt" value="${cnt+1}"></c:set>
		</c:forEach>
     </div>
	 
</div>  