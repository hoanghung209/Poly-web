<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <div class="col-sm-9 contentmainright">
           <c:forEach items="${lstContent }" var="item">
           <c:choose>
	         <c:when test="${item.type==1}"> <h1 class="titlecontent">${item.content}</h1></c:when>
	         <c:when test="${item.type==2}"> <p>${item.content}</p></c:when>
	         <c:when test="${item.type==3}"><a name="${item.anchor}"></a>  <h2 class="titlecontent2">${item.content}</h2></c:when>
	         <c:when test="${item.type==5}"> <p><img src="${item.content}" alt="" class="img-responsive"></p></c:when>
	       </c:choose>
	       </c:forEach>   
	       <div class=" vien-1 ">
	          <ul class="nav nav-tabs nav-justified">
	          <c:forEach items="${lstCourse}" var="course">
		            <li <c:if test="${course.id==firstCourseId}">class="active"</c:if>><a data-toggle="tab" href="#tab${course.id}">${course.name}</a></li>	 
		      </c:forEach>          
	          </ul>
	          
	          <div class="tab-content space">
	          	 <c:forEach items="${lstCourse}" var="course">
	          	 	<div id="tab${course.id}" class="tab-pane fade <c:if test='${course.id==firstCourseId}'>in active</c:if>">
	          	 		${course.content}
	          	 		<div class="table-responsive">
           					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table lichhoc table-bordered">
           						<tr>
           							<c:forEach items="${lstHeader}" var="headName">
           								<th scope="col">${headName.content}</th>
           							</c:forEach>           							
           						</tr>
           						<c:forEach items="${lstScheduler}" var="scheduler">
           							<c:if test="${scheduler.chuongtrinh_id==course.id }">
	           							<c:if test="${scheduler.column==1 }"><tr></c:if>
	           							<td>${scheduler.content}</td>
	           							<c:if test="${scheduler.column==countCol }"></tr></c:if>
	           						</c:if>
           						</c:forEach>
           					</table>
           				</div>
	          	 	</div>
	          	 </c:forEach>
	          </div>
	       </div>
    </div>
    <!--endcontentmain--> 