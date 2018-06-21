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
		<td id="gmem_<%=g.getGroup_id()%>"><%for(User member : g.getMembers()) {%>
		<%=member.getUsername()%> | <%=member.getFirstname()%> <%=member.getLastname()%><br>
		<%}%>
		</td>
		<%if(g.getFilename()==null){ //den thn exoun anevasei akoma %>
			<td>
				<i class="fa fa-spinner" aria-hidden="true"></i>
			</td>
			<td></td>
		<%} else { //thn exoun anevasei %>
			<td>
			<a href="${pageContext.request.contextPath}/assignment/download?id=<%=pa.getId()%>&amp;gid=<%=g.getGroup_id()%>" download="<%=g.getFilename()%>"><i class="fa fa-download"></i></a>
			</td>
			<% if(g.getGrade()!=-1) { //exei va8mologh8ei %>
				<td><%=g.getGrade()%></td>			
			<% }else{ //den exei va8mologh8ei %>
				<td>
				<c:set var="modalContent">
					<form action="${pageContext.request.contextPath}/professor/grade" method="post">
						<input type="hidden" name="id" value="<%=pa.getId()%>"/>
						<input type="hidden" name="gid" value="<%=g.getGroup_id()%>"/>
						<h2>Set Grade for Group:</h2>
						<ul>
						<% for(User member : g.getMembers()) {%>
							<li><%=member.getUsername()%> | <%=member.getFirstname()%> <%=member.getLastname()%></li>
						<% } %>
						</ul>
						<input type="number" name="grade" min="1" max="<%=pa.getMaxGrade()%>" size="5" placeholder="Grade" step="0.01"><br>
						<input type="submit" value="Set" class="button"/>
					</form>
				</c:set>
				<t:modal_popup div_id="gradeGroup-modal<%=g.getGroup_id()%>" button_type="anchor" title="<i class='fa fa-graduation-cap'></i>">
					${modalContent}
				</t:modal_popup>
				</td>
			<% } %>
		<%} %>
		</tr>
	<% } %>
	</table>

	<% } %>
</c:set>

<t:template title="<%=pa.getTitle()%>" home_url="${pageContext.request.contextPath}/professor/home.jsp"
			logo="horizontal" logout="visible">
	${bodyContent}
</t:template>
