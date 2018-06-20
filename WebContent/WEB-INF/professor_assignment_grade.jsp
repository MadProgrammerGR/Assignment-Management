<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.*" %>
<%@ page import="database.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	User user = (User)session.getAttribute("user_info");
	ProfessorAssignment pa = (ProfessorAssignment)request.getAttribute("assignment_info");
	List<GroupAssignment> lg = (List<GroupAssignment>)request.getAttribute("students_assignments_info");
%>

<c:set var="bodyContent">
	<h3 style="text-align: center;"><%=pa.getTitle()%></h3>
	<br><p><a href="${pageContext.request.contextPath}/assignment/download?id=<%=pa.getId()%>" download="<%=pa.getFilename()%>">Description <i class="fa fa-download"></i></a></p>

	<%if(lg.isEmpty()) {%>
		<p><i>Waiting for students to upload assignments...</i></p>
	<%} else { %>
	<br>
	<table>
	<tr><th>Group Members</th><th>Assignment</th><th>Grade</th>
	<%for(GroupAssignment g : lg) {%>
		<tr>
		<td><%for(User member : g.getMembers()) {%>
		<%=member.getUsername()%> | <%=member.getFirstname()%> <%=member.getLastname()%><br>
		<%}%></td>
		<td><a href="${pageContext.request.contextPath}/assignment/download?id=<%=pa.getId()%>&gid=<%=g.getGroup_id()%>" download="<%=g.getFilename()%>"><i class="fa fa-download"></i></a></td>
		
		<!-- TODO input grade form with post-->
		<td><a href="${pageContext.request.contextPath}/professor/grade?id=<%=pa.getId()%>&amp;gid=<%=g.getGroup_id()%>&amp;grade=2"><%=g.getGrade()%></a> TODO:popup set grade</td>
		</tr>
	<%} %>
	</table>
	<%} %>
</c:set>

<t:template title="<%=pa.getTitle()%>" home_url="${pageContext.request.contextPath}/professor/home.jsp"
			logo="horizontal" logout="visible">
	${bodyContent}
</t:template>
