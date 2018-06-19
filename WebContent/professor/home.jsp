<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, beans.*, database.*" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">
	<% User user = (User)session.getAttribute("user_info"); %>
	<h3>Welcome <%=user.getLastname()%> <%=user.getFirstname()%></h3>
	
	<div class="message message-${status}">${message}</div><br>
	<button onclick="display('createAssignment-modal','block')">Create Assignment</button>
	<div id="createAssignment-modal" class="modal-back">
		<div class="modal-content">
			<span class="close" onclick="display('createAssignment-modal','none')">&times;</span>
			
			<h2>Create a new assignment</h2><br>
			<form action="${pageContext.request.contextPath}/professor/uploadAssignment" method="post" enctype="multipart/form-data">
			    <label>Title:</label><input type="text" name="title" placeholder="e.g. Software Technology - 1st" size="30"/><br>
			    <label>Max grade:</label><input type="number" name="max_grade" min="1" max="10" value="3" size="5"><br>
			    <label>Max group size:</label><input type="number" name="max_group_size" min="1" max="10" value="3"><br>
			    <label>Description file:</label><input type="file" name="description_file"/><br>
			    <input type="submit" value="Upload" class="button"/>
			</form>
		</div>
	</div>


	<% List<ProfessorAssignment> list = Assignments.getFromProfessor(user.getId()); %>
	<% if (list.isEmpty()){ %>
		<p><i>No assignments have been created yet.</i></p>
	<% }else{ %>
		<table>
		<tr><th>Title</th><th>Description</th><th>Grade</th></tr>
		<% for(ProfessorAssignment pa : list) { %>
		<tr>
			<td><%=pa.getTitle()%></td>
			<td><a href="${pageContext.request.contextPath}/assignment/download?id=<%=pa.getId()%>" download=<%=pa.getFilename()%>>(download icon)</a></td>
			<td><a href="${pageContext.request.contextPath}/professor/grade?id=<%=pa.getId()%>">(grade icon or arrow or something)</a></td>
		</tr>
		<% } %>
		</table>
	<% } %>
</c:set>

<t:template title="Home" home_url="${pageContext.request.contextPath}/professor/home.jsp" 
			logo="horizontal" logout="visible">
	${bodyContent}
</t:template>
