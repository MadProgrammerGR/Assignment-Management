<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, beans.*, database.*" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">
	<% User user = (User)session.getAttribute("user_info"); %>
	<h3>Welcome <%=user.getLastname()%> <%=user.getFirstname()%></h3>
	
	<t:message status="${status}" text="${message}"/>
	<t:modal_popup button_type="button" title="Create Assignment" div_id="createAssignment-modal">
		<h2>Create a new assignment</h2><br>
		<form action="${pageContext.request.contextPath}/professor/uploadAssignment" method="post" enctype="multipart/form-data">
		    <label>Title</label><br>
		    <input type="text" name="title" placeholder="e.g. Software Technology - 1st" size="30"/><br>
		    <label>Max grade</label><br>
		    <input type="number" name="max_grade" min="1" max="10" value="3" size="5"><br><br>
		    <label>Max group size</label><br>
		    <input type="number" name="max_group_size" min="1" max="10" value="3"><br><br>
		    <label>Description file</label><br>
		    <input type="file" name="description_file"/><br><br>
		    <input type="submit" value="Upload" class="button"/>
		</form>
	</t:modal_popup>

	<% List<ProfessorAssignment> list = Assignments.getFromProfessor(user.getId()); %>
	<% if (list.isEmpty()){ %>
		<p><i>No assignments have been created yet.</i></p>
	<% }else{ %>
		<table>
		<tr><th>Assignment</th><th style="width:1%;white-space:nowrap;">Description</th></tr>
		<% for(ProfessorAssignment pa : list) { %>
		<tr>
			<td><a href="${pageContext.request.contextPath}/professor/grade?id=<%=pa.getId()%>"><%=pa.getTitle()%></a></td>
			<td><a href="${pageContext.request.contextPath}/assignment/download?id=<%=pa.getId()%>" download="<%=pa.getFilename()%>"><i class="fa fa-download"></i></a></td>
		</tr>
		<% } %>
		</table>
	<% } %>
</c:set>

<t:template title="Home" home_url="${pageContext.request.contextPath}/professor/home.jsp" 
			logo="horizontal" logout="visible">
	${bodyContent}
</t:template>
