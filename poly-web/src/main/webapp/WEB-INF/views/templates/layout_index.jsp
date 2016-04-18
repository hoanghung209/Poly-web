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
	<meta name="google-site-verification" content="lcJJyf6kL09Qb7PNslaFvXY3GiFxWcgutDLbqCV8lRU" />
	<meta name="description" content="Trung tâm ngoại ngữ POLY - chuyên đào tạo chương trình mầm non, tiểu học và trung học Bắc Mỹ. Đạt chuẩn đầu vào của các trường Quốc Tế lớn như AIS, SSIS.">
	<meta name="keywords" content="POLY, Trung tâm ngoại ngữ POLY, POLY Quận 7, POLY HCM, POLY Hồ Chí Minh">
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
		<tiles:insertAttribute name="body" />	
		<tiles:insertAttribute name="footer" />

		<!-- Google Tag Manager -->
		<noscript><iframe src="//www.googletagmanager.com/ns.html?id=GTM-57WW3S"
		height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
		<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
		new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
		j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
		'//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
		})(window,document,'script','dataLayer','GTM-57WW3S');</script>
		<!-- End Google Tag Manager -->
</body>
</html>