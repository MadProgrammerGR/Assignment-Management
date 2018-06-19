<%@tag pageEncoding="UTF-8"%>
<%@attribute name="title" required="true"%>
<%@attribute name="home_url" required="true"%>
<%@attribute name="logo" required="false"%>
<%@attribute name="logout" required="false"%>

<!DOCTYPE html>
<html>
<head>
<title>${title}</title>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/css/style.css" />
	
<script type="text/javascript">
	function display(mId, value) {
		document.getElementById(mId).style.display = value;
	}

	window.onclick = function(event) {
		if (event.target.classList.contains("modal-back")) {
			event.target.style.display = "none"; //otan pataei sto background tou modal
		}
	}
</script>
	
</head>
<body>
<div class="container">
	<header>
		<a href="${home_url}">
			<img src="${pageContext.request.contextPath}/images/logo<%= "horizontal".equals(logo)? "_horizontal" : ""%>.png"/>
		</a>
		<h1><a href="${home_url}">Assignment Management System</a></h1>
	</header>
	
	<% if ("visible".equals(logout)) {%>
	<!-- TODO: kalyterh 8esh k css -->
	<a href="${pageContext.request.contextPath}/logout">Logout</a> 
	<% } %>
	
	<article>
		<jsp:doBody/>
	</article>
	
	<footer>
		<div>
			<a class="footer-left">
				TODO-mikri eikona/logo
			</a>
			<span class="footer-right">
				2018 COPYLEFT <a href="#" onclick="display('about-modal','block')">@OMADARA</a>
			</span>
		</div>
	</footer>
	
	<div id="about-modal" class="modal-back">
		<div class="modal-content">
			<span class="close" onclick="display('about-modal','none')">&times;</span>
			School project made by:<br>
			<ul><li>Giorgos</li><li>Julio</li><li>Fanis</li></ul>
		</div>
	</div>
</div>
</body>
</html>


	
