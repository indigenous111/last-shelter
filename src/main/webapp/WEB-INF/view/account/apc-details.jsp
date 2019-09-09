<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Last Shelter | Account | ${account} | APC Details</title>
</head>
<body>
	<div id="header">
		<h1>Last Shelter</h1>
		<h2>Account - ${account} APC Details</h2>
	</div>
	<div id="breadcrumb">
		<a href="<c:url value='/home'/>">Home</a> | <a
			href="<c:url value='/account/home'/>">Account</a>
	</div>
	<div id="body">
		<table style="border: 1px solid black; padding: 2px;">
			<caption>
				<div style="font-size: 16pt; font-weight: bold;">APC</div>
			</caption>
			<tr>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					S.No.</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					APC Name</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Layer</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Marching Capacity</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Leading Unit</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Hero</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Net Marching Capacity</th>
			</tr>
			<c:forEach items="${apcDetails}" var="apc" varStatus="counter">
				<c:forEach items="${apc.layer}" var="layer" varStatus="counter">
					<tr style="">
						<td style="text-align: center; border: 1px solid black;">${counter.index + 1}</td>
						<td style="text-align: center; border: 1px solid black;">${apc.name}</td>
						<td style="text-align: center; border: 1px solid black;">${layer.id}</td>
						<td style="text-align: center; border: 1px solid black;">${layer.marchingCapacity}</td>
						<td style="text-align: center; border: 1px solid black;">${layer.leadingUnit.name}</td>
						<td style="text-align: center; border: 1px solid black;"><c:choose>
								<c:when test="${not empty layer.hero}">${layer.hero.name}</c:when>
								<c:otherwise>X</c:otherwise>
							</c:choose></td>
						<td style="text-align: center; border: 1px solid black;"></td>
					</tr>
				</c:forEach>
			</c:forEach>
		</table>
	</div>
	<div style="text-align: right; font-weight: lighter; font-size: 8pt;">
		&copy; Indigenous Software Pvt Ltd <sup>&reg;</sup>
	</div>
</body>
</html>