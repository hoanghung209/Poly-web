<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<tiles:importAttribute name="stylesheets" />
<tiles:importAttribute name="javascripts" />
<!DOCTYPE html>
<html lang="vi">
<c:set var="baseURL"
	value="${pageContext.request.requestURL.substring(0, pageContext.request.requestURL.length() - pageContext.request.requestURI.length())}${pageContext.request.contextPath}/" />
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content='text/html; charset=UTF-8' http-equiv='Content-Type' />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 10]>
	      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
	<title><tiles:insertAttribute name="title" ignore="true" /></title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/favicon.ico" type="image/x-icon">
	<link rel="icon" href="${pageContext.request.contextPath}/resources/favicon.ico" type="image/x-icon">
	<spring:url value="/resources/core/css/" var="css" />
	<spring:url value="/resources/core/images/" var="img" />
	<spring:url value="/resources/core/js/" var="js" />
	
	<c:forEach var="cssitem" items="${stylesheets}">
		<link rel="stylesheet" type="text/css"
			href="${baseURL }<c:url value="${cssitem}"/>">
	</c:forEach>
	<c:forEach var="script" items="${javascripts}">
		<script src="${baseURL }<c:url value="${script}"/>"></script>
	</c:forEach>	
	<!-- <script type="text/javascript" src="js/sivalabs.js"></script> -->
</head>
<body>
	<input type="hidden" value="${baseURL }" id="base_url" />		
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