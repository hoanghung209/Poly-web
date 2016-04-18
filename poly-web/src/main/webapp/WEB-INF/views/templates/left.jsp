<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="menu" uri="/WEB-INF/mytag/MenuHelper.tld" %>
<%@page session="true"%>
<f:set var="baseURL" value="${menu:getContextPath()}" />
    <div class="col-sm-3 left">
      <ul class="clearfix menudrop">      
      <f:forEach items="${menu:getMenu(menuChinh.id,1)}" var="item">
      	<li class="menu-item-has-children <f:if test='${item.id==menuChuongtrinh.id}'>opened</f:if> <f:if test='${item.id==menuCap.id}'>opened</f:if>"><a href="#" >${item.name}</a>
          <ul class="mlevelchild">
          <f:forEach items="${menu:getMenu(item.id,1)}" var="sub">
          	<li class="<f:if test='${menu:existSub(sub.id,1)}'>menu-item-has-children <f:if test='${sub.id==menuCap.id}'>opened</f:if></f:if> <f:if test='${!menu:existSub(sub.id,4)}'><f:if test='${sub.id==menuLop.id}'>mactive</f:if></f:if>">
          	<a <f:if test="${menu:existSub(sub.id,1)}">href="#"</f:if> <f:if test="${!menu:existSub(sub.id,1)}">href="${baseURL}/${sub.url}/${sub.slug}-${sub.hashid}${sub.neo}"</f:if>>${sub.name}</a>
          		 <ul class="mlevelchild">
          		 		<f:forEach items="${menu:getMenu(sub.id,1)}" var="sub2">
          				<li <f:if test='${sub2.id==menuLop.id}'>class="mactive"</f:if>><a href="${baseURL}/${sub2.url}/${sub2.slug}-${sub2.hashid}${sub2.neo}">${sub2.name}</a>
          				</li>
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
