<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template title="Home" home_url="${pageContext.request.contextPath}/student/home.jsp" logo="normal">
	<div id="info-message">
		${message}
	</div>
	<form action="${pageContext.request.contextPath}/Login" method="POST">
		<label>Username:</label>
		<input type="text" name="username"> <br>
		<label>Password:</label>
		<input type="password" name="password"> <br>
		<input type="submit" value="Login"/>
	</form>
</t:template>