<%@tag pageEncoding="UTF-8"%>
<%@attribute name="status" required="true"%>
<%@attribute name="text" required="true"%>

<%if(!status.isEmpty()) {%>
<div class="message message-${status}">
	${text}
</div>
<% } %>
