<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Last Shelter | Global</title>
</head>
<body>
	<div id="header">
		<h1>Last Shelter</h1>
		<h2>Global</h2>
	</div>
	<div id="breadcrumb">
		<a href="<c:url value='/home'/>">Home</a>
	</div>
	<div id="body">
		<div style="font-size: 14pt; font-weight: bolder;">Menu</div>
		<ul style="list-style: none;">
			<li><a href="<c:url value='/global/hero/detail'/>">Hero
					Details</a></li>
			<li><a href="<c:url value='/global/troops/detail'/>">Troop
					Details</a></li>
		</ul>
	</div>
	<div style="text-align: right;font-weight: lighter;font-size: 8pt;"> &copy; Indigenous Software Pvt Ltd <sup>&reg;</sup></div>
</body>
</html>