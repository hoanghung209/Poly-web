<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <div class="col-sm-9 contentmainright">
       ${content}
	   
	   <div class="clearfix">
			<h3>Các bài viết liên quan khác :</h3>
			<ul class="list">
			<c:forEach items="${dsNews}" var="ralated">
				<li>
					<a href="news?id=${ralated.id}">
					${ralated.title}
					</a>
				</li>
			</c:forEach>	
			</ul>
	   </div>
    </div>
    <!--endcontentmain--> 
    