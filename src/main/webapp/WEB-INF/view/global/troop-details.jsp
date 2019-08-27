<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Last Shelter | Global | Troops</title>
</head>
<body>
	<div id="header">
		<h1>Last Shelter</h1>
		<h2>Global Troops</h2>
	</div>
	<div id="breadcrumb">
		<a href="<c:url value='/home'/>">Home</a> | <a
			href="<c:url value='/global/home'/>">Global</a>
	</div>
	<div id="body">
		<table style="border: 1px solid black; padding: 2px;">
			<caption>Troops</caption>
			<tr>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					S.No.</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Troop</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Level</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Attack</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Speed</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Defense</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Load</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					HP</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Food</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Combat</th>
			</tr>
			<c:forEach items="${troopDetails.keySet()}" var="level">
				<tr>
					<td colspan="10">Troop Level ${level}</td>
				</tr>
				<c:forEach items="${troopDetails.get(level)}" var="troop"
					varStatus="counter">
					<tr>
						<td style="text-align: center; border: 1px solid black;">${counter.index + 1}</td>
						<td style="text-align: center; border: 1px solid black;">${troop.name}</td>
						<td style="text-align: center; border: 1px solid black;">${troop.level}</td>
						<td style="text-align: center; border: 1px solid black;">${troop.attack}</td>
						<td style="text-align: center; border: 1px solid black;">${troop.speed}</td>
						<td style="text-align: center; border: 1px solid black;">${troop.defense}</td>
						<td style="text-align: center; border: 1px solid black;">${troop.load}</td>
						<td style="text-align: center; border: 1px solid black;">${troop.hp}</td>
						<td style="text-align: center; border: 1px solid black;">${troop.food}</td>
						<td style="text-align: center; border: 1px solid black;">${troop.combat}</td>
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