<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <div class="col-sm-9 contentmainright">
         <c:forEach items="${lstContent }" var="item">
	         <c:if test="${item.type==1}"> <h1 class="titlecontent">${item.content}</h1></c:if>
	         <c:if test="${item.type==2}"> <p>${item.content}</p></c:if>
	         <c:if test="${item.type==3}">  <h2 class="titlecontent2">${item.content}</h2></c:if>
	          <c:if test="${item.type==5}"> <p><img src="${item.content}" alt="" class="img-responsive"></p></c:if>
         </c:forEach> 
                
        <section id="conference-timeline" class=" clearfix ${classCss}">
	        <div class="conference-center-line ke${color }"></div>
		    <div class="conference-timeline-content"> 
		        <c:set var="cnt" value="0"></c:set>
		        <c:forEach items="${lstSubject}" var="item">
			        <c:if test="${cnt%2==0}"><c:set var="rl" value="left"></c:set> </c:if>
			        <c:if test="${cnt%2==1}"><c:set var="rl" value="right"></c:set> </c:if>
			        <c:if test="${cnt%2==0}"><div class=" clearfix timeline-article"></c:if>
			            <div class="content-${rl}-container">
			              <div class="content-${rl}">
			                <p class="title">${item.title }</p>
			                <p class="arrowcontent">${item.content } </p>
			                <span class=" iconcont iconctimg${cnt+1}"></span>
			                <p class="lhxemthem"><a>Xem thêm</a></p>
			              </div>
			              <c:if test="${cnt%2==1}"><div class="meta-date cl${color }"> </div></c:if>
			            </div>            
			            <c:if test="${cnt%2==1}"><div class="meta-date cl${color }"> </div></c:if>
			          <c:if test="${cnt%2==1}"></div></c:if>
			          <c:set var="cnt" value="${cnt+1 }"></c:set>
		        </c:forEach>
	        
	         </div>
      </section>
    </div>
    <!--endcontentmain--> 
    
  </div>
</div>