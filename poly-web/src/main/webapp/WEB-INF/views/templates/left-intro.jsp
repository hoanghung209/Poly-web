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
      	<li class="menu-item-has-children opened"><a href="#" >${menuChinh.name}</a>
          <ul class="mlevelchild">
          <f:forEach items="${menu:getMenu(menuChinh.id,1)}" var="sub">
          	<li class="<f:if test='${sub.id==menuCap.id}'>mactive</f:if>"><a href="${sub.url}">${sub.name}</a>          		       	
          	</li>            
          </f:forEach>            
          </ul>
        </li>     
      </ul>
    </div>
    <!--endleft-->   
