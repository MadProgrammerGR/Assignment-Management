<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.*" %>
<%@ page import="database.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% User user = (User)session.getAttribute("user_info"); 
   ProfessorAssignment pa = (ProfessorAssignment)request.getAttribute("assignment_info");
   GroupAssignment ga = Assignments.getGroupAssignment(user.getId(), pa.getId());%>

<c:set var="bodyContent">
	<h3 style="text-align: center;"><%=pa.getTitle()%></h3>
	<p style="text-align: right;"><i>Created by <%=pa.getProf().getLastname()%> <%=pa.getProf().getFirstname()%></i></p>
	<br><p><a href="${pageContext.request.contextPath}/assignment/download?id=<%=pa.getId()%>" download=<%=pa.getFilename()%>>Description <i class="fa fa-download"></i></a></p>
	
	<!-- TODO: dhmiourgia h provolh group, anevasma ergasias kai provolh va8mou -->
	<%if(ga == null) {%>
	<href ="createGroup">Create Group</href>
	<%} else { %>
	<br>
	<%List<User> members = Accounts.getGroupMembers(ga.getGroup_id());%>
	<table>
	<tr><th>AM</th><th>First name</th><th>Last name</th>
	<%for(User member : members){%>
	<tr><td><%=member.getUsername() %></td><td><%=member.getFirstname() %></td><td><%=member.getLastname() %></td>
	</tr>
	<%}%>
	</table>
	<%} %>
	
	
	
	
</c:set>

<t:template title="<%=pa.getTitle()%>" home_url="${pageContext.request.contextPath}/student/home.jsp" 
			logo="horizontal" logout="visible">
	${bodyContent}
</t:template>