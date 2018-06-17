<%@tag pageEncoding="UTF-8"%>
<%@attribute name="title" required="true"%>
<%@attribute name="home_url" required="true"%>
<%@attribute name="logo" required="false"%>

<!DOCTYPE html>
<html>
<head>
<title>${title}</title>
</head>
<body>
	<a href="${home_url}">
		<img src="${pageContext.request.contextPath}/images/logo<%= "horizontal".equals(logo)? "_horizontal.png" : ""%>.png"/>
	</a>
	<h1><a href="${home_url}">Assignment Management System</a></h1>

	<jsp:doBody/>

	<footer>
		2018 COPYLEFT <a href="#">@OMADARA</a>
	</footer>
</body>
</html>


	