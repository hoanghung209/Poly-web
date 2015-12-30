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
	       
	       <div class="panel-group" id="accordion">
	       <c:forEach items="${lstFaq }" var="item">
	       		<div class="panel panel-default">
                         <a data-toggle="collapse" data-parent="#accordion" href="#collapse${item.id}"> <div class="panel-heading">
                            <h4 class="panel-title">
                              <i class="fa fa-comment"></i>${item.title}</
                            </h4>
                          </div></a>
                          <div id="collapse${item.id}" class="panel-collapse collapse <c:if test='${item.id==firstFaqId}'> in</c:if>">
                            <div class="panel-body"><p></i>${item.content}</p> </div>
                          </div>
                        </div>
           </c:forEach>
	       </div>
    </div>
    <!--endcontentmain--> 
    