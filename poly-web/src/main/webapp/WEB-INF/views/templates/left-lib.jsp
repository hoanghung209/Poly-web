<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="menu" uri="/WEB-INF/mytag/MenuHelper.tld" %>
<spring:url value="/resources/core/images" var="img" />
<spring:url value="/resources/core/icons" var="icon" />
<%@page session="true"%>

    <div class="col-sm-3 left">
      <ul class="clearfix menudrop">      
      <f:forEach items="${menu:getMenu('5','1')}" var="item">
      	<li class="<f:if test='${menu:existSub(item.id,1)}'>menu-item-has-children</f:if>
      	<f:if test='${item.id==cat}'> opened</f:if>">
      	<a <f:if test="${menu:existSub(item.id,1)}">href="#"</f:if>
      		<f:if test="${!menu:existSub(item.id,1)}">href="${item.url}"</f:if>>${item.name}</a>
          <ul class="mlevelchild">
          <f:forEach items="${menu:getMenu(item.id,1)}" var="sub">
          	<li class="menu-item-has-children <f:if test='${sub.id==catSub}'>opened</f:if>">
          	<a href="#">${sub.name}</a>
          		 <ul class="mlevelchild">
          		 		<f:forEach items="${lstAlbum}" var="subAlbum">
          		 		<f:if test="${sub.id==59&&subAlbum.is_video==0}">          		 		
	          				<li <f:if test='${subAlbum.id==cur_id}'>class="mactive"</f:if>><a href="image?id=${subAlbum.id}">${subAlbum.title}</a>
	          				</li>	          			
          				</f:if>
          				<f:if test="${sub.id==61&&subAlbum.is_video==1}">
	          				<li <f:if test='${subAlbum.id==cur_id}'>class="mactive"</f:if>><a href="video?id=${subAlbum.id}">${subAlbum.title}</a>
	          				</li>
          				</f:if>
          				</f:forEach>
          		 </ul>          	
          	</li>            
          </f:forEach>            
          </ul>
        </li>
      </f:forEach>   
      </ul>
    </div>
    <!--endleft-->   
