<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.User" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">
	<% User user = (User)session.getAttribute("user_info"); %>
	<h2>Welcome <%=user.getLastname()%> <%=user.getFirstname()%></h2>
	
	
	<button onclick="display('createAssignment-modal','block')">Create <span>&plus;</span></button>
	<div id="status-message">${message}</div>
	<div id="createAssignment-modal" class="modal-back">
		<div class="modal-content">
			<span class="close" onclick="display('createAssignment-modal','none')">&times;</span>
			
			<h2>Create a new assignment</h2><br>
			<form action="${pageContext.request.contextPath}/professor/uploadAssignment" method="post" enctype="multipart/form-data">
			    <label>Title:</label><input type="text" name="title" placeholder="e.g. Software Technology - 1st" size="30"/><br>
			    <label>Max grade:</label><input type="number" name="max_grade" min="1" max="10" value="3" size="5"><br>
			    <label>Max group size:</label><input type="number" name="max_group_size" min="1" max="10" value="3"><br>
			    <label>Description file:</label><input type="file" name="description_file"/><br>
			    <input type="submit" value="Upload"/>
			</form>
		</div>
	</div>

	<!-- TODO -->

</c:set>

<t:template title="Home" home_url="${pageContext.request.contextPath}/professor/home.jsp" logo="horizontal">
	${bodyContent}
</t:template>
