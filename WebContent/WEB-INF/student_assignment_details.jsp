<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.*" %>
<%@ page import="database.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% User user = (User)session.getAttribute("user_info"); 
   ProfessorAssignment pa = (ProfessorAssignment)request.getAttribute("assignment_info");
   boolean beforeDeadline = new Date().before(pa.getDeadline());
   GroupAssignment ga = Assignments.getGroupAssignment(user.getId(), pa.getId());
%>

<c:set var="bodyContent">
	<h3 style="text-align: center;"><%=pa.getTitle()%></h3>
	<span style=""><i>Deadline <%=pa.getDeadline()%></i></span>
	<span style="float: right;"><i>Created by <%=pa.getProf().getLastname()%> <%=pa.getProf().getFirstname()%> on <%=pa.getCreated()%></i></span>
	
	<t:message status="${status}" text="${message}"/>
	<p><a href="${pageContext.request.contextPath}/assignment/download?id=<%=pa.getId()%>" download="<%=pa.getFilename()%>">Description <i class="fa fa-download"></i></a></p>
	
	<%if(ga == null) { //den exei dhmiourgh8ei akoma to group %>
		<%if(beforeDeadline) { %>
		<ul><li>Create a group by using member's usernames, or wait until someone else makes for you.</li>
		   <li>Leave blank in case you don't want a full group.</li>
		   <li>Maximum group size is <%=pa.getMaxGroupSize()%>.</li></ul>
		   
		<form action="${pageContext.request.contextPath}/student/createGroup" method="post">
		<input type="hidden" name="id" value="<%=pa.getId()%>">
		<%for(int i=1; i<pa.getMaxGroupSize(); i++) {%>
			<input type="text" name="member" placeholder="Member <%=i%>" size="20"/><br/>
		<% } %>
		<input type="submit" value="Create group" class="button"/><br/>
		</form>
		<% }else{ %>
		<t:message status="error" text="Deadline has passed. You can't make group anymore."/>
		<% } %>
		
	<%} else { //exei dhmiourgh8ei %>
		<p>Members in your group</p>
		<table>
			<tr><th>AM/Username</th><th>First name</th><th>Last name</th></tr>
			<%for(User member : ga.getMembers()) {%>
			<tr><td><%=member.getUsername()%></td><td><%=member.getFirstname()%></td><td><%=member.getLastname()%></td></tr>
			<% } %>
		</table><br/>
	
		<%if(ga.getFilename()==null) { //den exoun anevasei akoma ergasia %>
			<%if(beforeDeadline) { %>
			<form action="${pageContext.request.contextPath}/student/uploadAssignment" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="<%=pa.getId()%>">
			<input type="hidden" name="gid" value="<%=ga.getGroup_id()%>">
			<label>Assignment files</label><br/>
			<input type="file" name="file"/><br/><br/>
			<input type="submit" value="Upload" class="button">
			</form>
			<% }else{ %>
			<t:message status="error" text="Deadline has passed. You can't upload files anymore."/>
			<% } %>
		<%} else { //exoun anevasei%>
			<p>You uploaded your assignment's
			<a href="${pageContext.request.contextPath}/assignment/download?id=<%=pa.getId()%>&amp;gid=<%=ga.getGroup_id()%>" download="<%=ga.getFilename()%>">
				files <i class="fa fa-download"></i></a>
				at <fmt:formatDate value="<%=ga.getUploaded()%>" pattern="dd-MM-yyyy"/></p>
			
			<%if(ga.getGrade()!=-1) { //exei va8mologh8ei %>
				<p><b>Your grade is <%=ga.getGrade()%> out of <%=pa.getMaxGrade()%>.</b></p> 
			<%} else { //den exei va8mologh8ei %>
				<p><i>Professor hasn't graded this assignment yet.</i></p>
			<% } %>
			
		<% } %>
		
	<% } %>
</c:set>

<t:template title="<%=pa.getTitle()%>" home_url="${pageContext.request.contextPath}/student/home.jsp" 
			logo="horizontal" logout="visible">
	${bodyContent}
</t:template>
