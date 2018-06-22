<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="title" required="true"%>
<%@attribute name="home_url" required="true"%>
<%@attribute name="logo" required="false"%>
<%@attribute name="logout" required="false"%>

<!DOCTYPE html>
<html>
<head>
<title>${title}</title>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/css/style.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
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
	
	<article>
		<% if ("visible".equals(logout)) {%>
		<a href="${pageContext.request.contextPath}/logout" class="button" style="float: right;color: white;">Logout <i class="fa fa-sign-out"></i></a> 
		<% } %>
		<jsp:doBody/>
	</article>
	
	<footer>
		<div>
			<span class="footer-left">
  				<img src="/Assignment_Management/images/logo_horizontal.png" height="30px">
  				<i>Managing student's assignments since 2018</i>
  			</span>
			<span class="footer-right">
				2018 COPYLEFT 
				<t:modal_popup button_type="anchor" title="@OMADARA" div_id="about-modal">
					<b>School project made by</b><br>
					<ul><li>Giorgos</li><li>Julio</li><li>Fanis</li></ul>
				</t:modal_popup>
			</span>
		</div>
	</footer>
	
</div>
</body>
</html>


	
