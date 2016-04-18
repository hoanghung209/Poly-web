<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="menu" uri="/WEB-INF/mytag/MenuHelper.tld" %>
<%@page session="true"%>
<f:set var="baseURL" value="${menu:getContextPath()}" />
<div class=" clearfix par-pic"> <img src="${menuChinh.banner}" class="img-responsive" alt="">
  <div class="text-pic">
    <p>${menuChinh.name}
    <div class="magic_line1" ></div>
    </p>
  </div>
</div>

<!--end pic dai dien-->
<div class="breadcrumb">
  <div class="container">
    <ol >
      <li><a href="${baseURL}">Trang chủ</a> </li>      
      <li><a href="${baseURL}/${menuChinh.url}/${menuChinh.slug}-${menuChinh.hashid}">${menuChinh.name} </a> </li>
      <li><a href="${baseURL}/${menuChuongtrinh.url}/${menuChuongtrinh.slug}-${menuChuongtrinh.hashid}">${menuChuongtrinh.name} </a> </li>
      <li class="active">${menuCap.name}</li>      
    </ol>
  </div>
</div>
<!--end breadcrumb-->
