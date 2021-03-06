<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template title="Home" home_url="${pageContext.request.contextPath}/login.jsp" logo="normal">

	<form id="lin" action="${pageContext.request.contextPath}/Login" method="POST">
		<div class="row">
			<div class="lin-title">Login</div>
		</div>
		<div class="row">
			<t:message status="${status}" text="${message}"/>
		</div>
		<div class="form-input row">
			<input id="lin-username" type="text" name="username" placeholder="Username" autofocus="autofocus">
		</div>
		<div class="form-input row">
			<input id="lin-password" type="password" name="password" placeholder="Password">
		</div>
		<div class="row">
			<div class="col-l-submit">
				<input type="submit" value="Login" class="button button-block"/>
			</div>
		</div>
		<hr class="line">

		<div class="row">
			<div class="col-l-last">
				<t:modal_popup button_type="anchor" title="Don't have an account?" div_id="noAcc-modal">
					<p>Please contact your <a href="mailto:secretary@university.gr?Subject=Request%20Account" target="_top">secretary</a> 
					or <a href="mailto:admin@system.gr?Subject=Request%20Account" target="_top">site admin.</a></p>
				</t:modal_popup>
			</div>
		</div>
	</form>

</t:template>