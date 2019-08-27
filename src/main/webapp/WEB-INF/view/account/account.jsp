<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Last Shelter | Account | ${account}</title>
</head>
<body>
	<div id="header">
		<h1>Last Shelter</h1>
		<h2>Account - ${account}</h2>
	</div>
	<div id="breadcrumb">
		<a href="<c:url value='/home'/>">Home</a> | <a
				href="<c:url value='/account/home'/>">Account</a>
	</div>
	<div id="body">
		<div style="font-size: 14pt; font-weight: bolder;">Menu</div>
		<ul style="list-style: none;">
			<li><a href="/account/hero/${account}"> Hero Details </a></li>
			<li><a href="/account/apc/${account}"> APC Details </a></li>
		</ul>
	</div>
	<div style="text-align: right;font-weight: lighter;font-size: 8pt;"> &copy; Indigenous Software Pvt Ltd <sup>&reg;</sup></div>
</body>
</html>