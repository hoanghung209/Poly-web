<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Maven + Spring MVC</title>

</head>
 <body>
 <h1>Welcome</h1>
 Language : <a href="?lang=en">English</a>|<a href="?lang=vn">Vietnames</a> 
<form>
	<button type="button" onclick="changeLang('en');">English</button>
	<button type="button" onclick="changeLang('vn');">Vietnam</button>
</form>
<h2>
welcome - Xin chào : <spring:message code="welcome" text="default text" />
</h2>

Current Locale : ${pageContext.response.locale}
</body>
</html>