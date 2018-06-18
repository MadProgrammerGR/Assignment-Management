<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template title="Home" home_url="${pageContext.request.contextPath}/login.jsp" logo="normal">
	<div id="info-message">
		${message}
	</div>
	<form action="${pageContext.request.contextPath}/Login" method="POST">
		<label>Username:</label>
		<input type="text" name="username"> <br>
		<label>Password:</label>
		<input type="password" name="password"> <br>
		<input type="submit" value="Login" class="button"/>
	</form>

	<a href="#" onclick="display('noAcc-modal','block')">Don't have an account?</a>
	<div id="noAcc-modal" class="modal-back">
		<div class="modal-content">
			<span class="close" onclick="display('noAcc-modal','none')">&times;</span>
			<p>Please contact your <a href="mailto:secretary@university.gr?Subject=Request%20Account" target="_top">secretary</a> 
			 or <a href="mailto:admin@system.gr?Subject=Request%20Account" target="_top">site admin.</a></p>
		</div>
	</div>
	
</t:template>