<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Last Shelter | Home</title>
</head>
<body>
	<div id="header">
		<h1>Last Shelter</h1>
		<h2>Home</h2>
	</div>
	<div id="body">
	<div style="font-size: 14pt; font-weight: bolder;">Menu</div>
	<ul style="list-style: none;">
		<li><a href="<c:url value="/global/home"/>">Global</a></li>
		<li><a href="<c:url value="/account/home"/>">Account</a></li>
	</ul>
	</div>
	<div style="text-align: right;font-weight: lighter;font-size: 8pt;"> &copy; Indigenous Software Pvt Ltd <sup>&reg;</sup></div>
</body>
</html>