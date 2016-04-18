<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Poly-Web Example</title>

</head>
 <body>
 <script src="./resources/core/js/jquery.min.js"></script>
 <h1>Welcome to Poly Example</h1>
<form>
	<button type="button" onclick="changeLang('en');">English</button>
	<button type="button" onclick="changeLang('vn');">Vietnam</button>
</form>
<h2>
<spring:message code="welcome" text="${msg}" />
</h2>
<script type="text/javascript">
	function changeLang(lang){		
		$.ajax({
			type: "POST",
			url: "${pageContext.request.contextPath}/changeLanguage",
			data: "lang=" + lang,
			success: function(response){				
				location.reload(); 
			}
		});
	}
</script>
</body>
</html>