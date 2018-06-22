<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, beans.*, database.*" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
							   
<c:set var="bodyContent">
	<% User user = (User)session.getAttribute("user_info"); %>
	<h3>Welcome <%=user.getLastname()%> <%=user.getFirstname()%></h3>
	
	<t:message status="${status}" text="${message}"/>
	<% List<ProfessorAssignment> list = Assignments.getAll(); %>
	<% if(list.isEmpty()) { %>
		<p><i>No assignments available at the moment.</i></p>
	<% }else{ %>
		<h4>Currently available assignments</h4>
		<table>
		<tr><th>Assignment</th><th>Uploaded by</th></tr>
		<% for(ProfessorAssignment pa : list) { %>
		<tr>
			<td><a href="${pageContext.request.contextPath}/student/assignment?id=<%=pa.getId()%>"><%=pa.getTitle()%></a></td>
			<td><%=pa.getProf().getLastname()%> <%=pa.getProf().getFirstname()%></td>
		</tr>
		<% } %>
		</table>
	<% } %>
</c:set>

<t:template title="Home" home_url="${pageContext.request.contextPath}/student/home.jsp" 
			logo="horizontal" logout="visible">
	${bodyContent}
</t:template>
