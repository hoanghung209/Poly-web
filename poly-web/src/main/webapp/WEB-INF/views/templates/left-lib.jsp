<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="menu" uri="/WEB-INF/mytag/MenuHelper.tld" %>
<%@page session="true"%>
<f:set var="baseURL" value="${menu:getContextPath()}" />
    <div class="col-sm-3 left">
      <ul class="clearfix menudrop">      
      <f:forEach items="${menu:getMenu('5','1')}" var="item">
      	<li class="menu-item-has-children
      	<f:if test='${item.id==cat}'> opened</f:if>">
      	<a href="#">${item.name}</a>
          <ul class="mlevelchild">
          <f:forEach items="${menu:getMenu(item.id,1)}" var="sub">
          	<li class="menu-item-has-children <f:if test='${sub.id==catSub}'>opened</f:if>">
          	<a href="#">${sub.name}</a>
          		 <ul class="mlevelchild">
          		 		<f:forEach items="${lstAlbum}" var="subAlbum">
          		 		<f:if test="${sub.id==59&&subAlbum.type==0}">          		 		
	          				<li <f:if test='${subAlbum.id==cur_id}'>class="mactive"</f:if>><a href="${baseURL}/image/${subAlbum.slug}-${subAlbum.hashid}">${subAlbum.title}</a>
	          				</li>	          			
          				</f:if>
          				<f:if test="${sub.id==61&&subAlbum.type==1}">
	          				<li <f:if test='${subAlbum.id==cur_id}'>class="mactive"</f:if>><a href="${baseURL}/video/${subAlbum.slug}-${subAlbum.hashid}">${subAlbum.title}</a>
	          				</li>
          				</f:if>
          				</f:forEach>
          		 </ul>          	
          	</li>            	        
          </f:forEach>  
          <f:if test="${item.id==58}">
          <f:forEach items="${lstAlbum}" var="nalbum">
          	<f:if test="${nalbum.type==2}">
          		<li <f:if test='${nalbum.id==cur_id}'>class="mactive"</f:if>><a href="${baseURL}/news/${nalbum.slug}-${nalbum.hashid}">${nalbum.title}</a>
          	</f:if>
          </f:forEach>
          </f:if>            
          </ul>
        </li>
      </f:forEach>   
      </ul>
    </div>
    <!--endleft-->   
