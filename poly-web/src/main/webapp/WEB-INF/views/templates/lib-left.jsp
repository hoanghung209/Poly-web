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
      	<li class="menu-item-has-children opened"><a href="#" >${menuName}</a>
          <ul class="mlevelchild">
          <f:forEach items="${lstAlbum}" var="album">
          	<li class="<f:if test='${album.id==cur_id}'>mactive</f:if>">
          	<a href="${menuName}?id=${album.id}">${album.title}</a>          		      	
          	</li>            
          </f:forEach>            
          </ul>
        </li>
      </ul>
    </div>
    <!--endleft-->   
