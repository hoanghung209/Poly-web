<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="menu" uri="/WEB-INF/mytag/MenuHelper.tld" %>
<!DOCTYPE html>
<html lang="vi">
<head>
	<c:set var="baseURL" value="${menu:getContextPath()}" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content='text/html; charset=UTF-8' http-equiv='Content-Type' />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title><tiles:insertAttribute name="title" ignore="true" /></title>
	<link rel="shortcut icon" href="${baseURL}/resources/favicon.ico" type="image/x-icon">
	<link rel="icon" href="${baseURL}/resources/favicon.ico" type="image/x-icon">
	<spring:url value="${baseURL}/resources/core/css/" var="css" />
	<spring:url value="${baseURL}/resources/core/images/" var="img" />
	<spring:url value="${baseURL}/resources/core/js/" var="js" />
	
	<link rel="stylesheet" type="text/css" href="${baseURL}/resources/core/css/bootstrap.css"/>
	<link rel="stylesheet" type="text/css" href="${baseURL}/resources/core/css/font-awesome.css"/>
	<link rel="stylesheet" type="text/css" href="${baseURL}/resources/core/css/fontend.css"/>
	<link rel="stylesheet" type="text/css" href="${baseURL}/resources/core/css/settings.css"/>
	<link rel="stylesheet" type="text/css" href="${baseURL}/resources/core/css/style.css"/>
	<link rel="stylesheet" type="text/css" href="${baseURL}/resources/core/css/jcarousel.responsive.css"/>
	

	
	<script src="${baseURL}/resources/core/js/jquery.min.js"></script>
	<script src="${baseURL}/resources/core/js/jquery.themepunch.plugins.min.js"></script>
	<script src="${baseURL}/resources/core/js/jquery.themepunch.revolution.min.js"></script>
	<script src="${baseURL}/resources/core/js/jquery.jcarousel.min.js"></script>
	<script src="${baseURL}/resources/core/js/jcarousel.responsive.js"></script>
	<script src="${baseURL}/resources/core/js/bootstrap.min.js"></script>
	<script src="${baseURL}/resources/core/js/common.js"></script>
	<script src="${baseURL}/resources/core/js/myscript.js"></script>
	
	<script src="${baseURL}/resources/core/js/ie-emulation-modes-warning.js"></script>
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 10]>
	      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
	<!-- <script type="text/javascript" src="js/sivalabs.js"></script> -->
</head>
<body>		
		<tiles:insertAttribute name="navigation" />
		<tiles:insertAttribute name="top" />
		<div class=" clearfix container">
  			<div class="row">
			<tiles:insertAttribute name="left" />
			<tiles:insertAttribute name="body" />
			</div>
		</div>
		<tiles:insertAttribute name="footer" />		
</body>
</html>