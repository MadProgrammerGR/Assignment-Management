<%@tag pageEncoding="UTF-8"%>
<%@attribute name="button_type" required="true"%>
<%@attribute name="title" required="true"%>
<%@attribute name="div_id" required="true"%>


<% if(button_type.equals("anchor")) { %>
	<a href="#" onclick="display('${div_id}','block')">${title}</a>
<% }else if(button_type.equals("button")) { %>
	<button onclick="display('${div_id}','block')">${title}</button>
<% } %>

<div id="${div_id}" class="modal-back">
	<div class="modal-content">
		<span class="close" onclick="display('${div_id}','none')">&times;</span>
		<jsp:doBody/>
	</div>
</div>


