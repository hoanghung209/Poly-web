<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="menu" uri="/WEB-INF/mytag/MenuHelper.tld" %>
<c:set var="baseURL" value="${menu:getContextPath()}" />
    <div class="col-sm-9 contentmainright">
       ${content}	   
	   <div class="clearfix">
			<h3>Các bài viết liên quan khác :</h3>
			<ul class="list">
			<c:forEach items="${dsNews}" var="ralated">
				<li>
					<a href="${baseURL}/news_detail/${ralated.slug}-${ralated.hashid}">
					${ralated.title}
					</a>
				</li>
			</c:forEach>	
			</ul>
	   </div>
    </div>
    <!--endcontentmain--> 
    