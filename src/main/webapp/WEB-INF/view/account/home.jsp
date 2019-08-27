<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Last Shelter | Accounts</title>
</head>
<body>
	<div id="header">
		<h1>Last Shelter</h1>
		<h2>Accounts</h2>
	</div>
	<div id="breadcrumb">
		<a href="<c:url value='/home'/>">Home</a>
	</div>
	<div id="body">
		<div style="font-size: 14pt; font-weight: bolder;">Menu</div>
		<ul style="list-style: none;">
			<li><a href="<c:url value="/account/1"/>">Account 1</a></li>
			<li><a href="<c:url value="/account/2"/>">Account 2</a></li>
			<li><a href="<c:url value="/account/3"/>">Account 3</a></li>
			<li><a href="<c:url value="/account/4"/>">Account 4</a></li>
		</ul>
	</div>
	<div style="text-align: right;font-weight: lighter;font-size: 8pt;"> &copy; Indigenous Software Pvt Ltd <sup>&reg;</sup></div>
</body>
</html>