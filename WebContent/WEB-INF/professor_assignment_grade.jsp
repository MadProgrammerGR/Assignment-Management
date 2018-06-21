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
		<%}%></td>
		<td><a href="${pageContext.request.contextPath}/assignment/download?id=<%=pa.getId()%>&amp;gid=<%=g.getGroup_id()%>" download="<%=g.getFilename()%>"><i class="fa fa-download"></i></a></td>
		
		<!-- TODO input grade form with post-->
		<td><a href="#" onclick="fillSetGradeForm(<%=pa.getId()%>,<%=g.getGroup_id()%>)"><%=g.getGrade()%></a></td>
		</tr>
	<%} %>
	</table>

	<t:modal_popup button_type="NULL" title="" div_id="gradeGroup-modal">
		<form action="${pageContext.request.contextPath}/professor/grade" method="post">
			<input type="number" id="inp_id" name="id" hidden/>
			<input type="number" id="inp_gid" name="gid" hidden/>
			<h2>Set Grade for Group</h2>
			(todo:?css todo:?prof/grade forward uresult error)<br>
			<div id="gradeGroup_members"></div><br>
			<input type="number" id="inp_grade" name="grade" min="1" max="10" size="5" placeholder="Grade" step="0.01"><br>
			<input type="submit" value="Set" class="button"/>
		</form>
		<script>
		function fillSetGradeForm(id,gid){
			document.getElementById("inp_id").value = id;
			document.getElementById("inp_gid").value = gid;
			document.getElementById("gradeGroup_members").innerHTML = document.getElementById("gmem_"+gid).innerHTML;
			display('gradeGroup-modal','block');
		}
		</script>
	</t:modal_popup>
	<%} %>
</c:set>

<t:template title="<%=pa.getTitle()%>" home_url="${pageContext.request.contextPath}/professor/home.jsp"
			logo="horizontal" logout="visible">
	${bodyContent}
</t:template>
