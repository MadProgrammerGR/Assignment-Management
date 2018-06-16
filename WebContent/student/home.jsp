<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
							   
<c:set var="bodyContent">
	<!-- stub -->
	<h2>Welcome FirstName Lastname</h2>
	<table>
	<tr><th>Title</th><th>Description</th><th>Professor</th><th>Group</th><th>Grade</th></tr>
	<tr><td>Subject something - exercise 1</td>
		<td><a href="${pageContext.request.contextPath}/assignment/download?id=1">(download icon)</a></td>
		<td>Name</td>
		<td>p100<br>p101<br>p102<br></td>
		<td>3/3</tr>
	<tr><td>Subject something else - exercise 5</td>
		<td><a href="${pageContext.request.contextPath}/assignment/download?id=2">(download icon)</a></td>
		<td>Name2</td>
		<td><a href="${pageContext.request.contextPath}/student/create_group.jsp?id=2">create</a></td>
		<td></td></tr>
	</table>
</c:set>

<t:template title="Home" home_url="${pageContext.request.contextPath}/student/home.jsp">
	${bodyContent}
</t:template>