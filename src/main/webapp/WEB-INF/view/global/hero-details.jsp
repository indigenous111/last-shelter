<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Last Shelter | Global | Heroes</title>
<script type="text/javascript"
	src="<c:url value='/js/jquery-3.4.1.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/jquery.sortElements.js'/>"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						var table = $('table');

						$('#damage-header, #resistance-header')
								.wrapInner('<span title="sort this column"/>')
								.each(
										function() {

											var th = $(this), thIndex = th
													.index(), inverse = false;

											th
													.click(function() {

														table
																.find('td')
																.filter(
																		function() {

																			return $(
																					this)
																					.index() === thIndex;

																		})
																.sortElements(
																		function(
																				a,
																				b) {

																			if ($
																					.text([ a ]) == $
																					.text([ b ]))
																				return 0;

																			return $
																					.text([ a ]) > $
																					.text([ b ]) ? inverse ? -1
																					: 1
																					: inverse ? 1
																							: -1;

																		},
																		function() {

																			// parentNode is the element we want to move
																			return this.parentNode;

																		});

														inverse = !inverse;

													});

										});
					});
</script>
</head>
<body>
	<div id="header">
		<h1>Last Shelter</h1>
		<h2>Global Heroes</h2>
	</div>
	<div id="breadcrumb">
		<a href="<c:url value='/home'/>">Home</a> | <a
			href="<c:url value='/global/home'/>">Global</a>
	</div>
	<div id="body">
		<table style="border: 1px solid black; padding: 2px;">
			<caption>Heroes</caption>
			<tr>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					S.No.</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Hero</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Color</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Leading Unit</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Marching Capacity</th>
				<th id="damage-header"
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Damage</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Range</th>
				<th id="resistance-header"
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Resistance</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Might</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Lower Might</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Lower Resistance</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					HP</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Minus Enemy Turns</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Lower Damage</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Combat Speed</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Seige Might</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Seige Defense Might</th>
				<th
					style="text-align: center; border: 1px solid black; background-color: black; color: white;">
					Bulwark</th>
			</tr>
			<c:forEach items="${heroDetails}" var="hero" varStatus="counter">
				<tr
					style="background-color:${fn:toLowerCase(hero.details.color)};<c:choose><c:when test="${hero.details.color eq purple}">color:white;</c:when></c:choose>">
					<td style="text-align: center; border: 1px solid black;">${counter.index + 1}</td>
					<td style="text-align: center; border: 1px solid black;">${hero.details.name}</td>
					<td style="text-align: center; border: 1px solid black;">${hero.details.color}</td>
					<td style="text-align: center; border: 1px solid black;">${hero.stats.leadingUnit}</td>
					<td style="text-align: center; border: 1px solid black;"><fmt:formatNumber
							type="number" maxFractionDigits="2"
							value="${hero.stats.marchingCapacity}" /></td>
					<td style="text-align: center; border: 1px solid black;"><fmt:formatNumber
							type="number" maxFractionDigits="2" value="${hero.stats.damage}" /></td>
					<td style="text-align: center; border: 1px solid black;">${hero.stats.range}</td>
					<td style="text-align: center; border: 1px solid black;"><fmt:formatNumber
							type="number" maxFractionDigits="2"
							value="${hero.stats.resitance}" /></td>
					<td style="text-align: center; border: 1px solid black;"><fmt:formatNumber
							type="number" maxFractionDigits="2" value="${hero.stats.might}" /></td>
					<td style="text-align: center; border: 1px solid black;"><fmt:formatNumber
							type="number" maxFractionDigits="2"
							value="${hero.stats.lowerMight}" /></td>
					<td style="text-align: center; border: 1px solid black;"><fmt:formatNumber
							type="number" maxFractionDigits="2"
							value="${hero.stats.lowerResistance}" /></td>
					<td style="text-align: center; border: 1px solid black;"><fmt:formatNumber
							type="number" maxFractionDigits="2" value="${hero.stats.hp}" /></td>
					<td style="text-align: center; border: 1px solid black;">${hero.stats.minusEnemyTurns}</td>
					<td style="text-align: center; border: 1px solid black;"><fmt:formatNumber
							type="number" maxFractionDigits="2"
							value="${hero.stats.lowerDamage}" /></td>
					<td style="text-align: center; border: 1px solid black;">${hero.stats.combatSpeed}</td>
					<td style="text-align: center; border: 1px solid black;"><fmt:formatNumber
							type="number" maxFractionDigits="2"
							value="${hero.stats.seigeMight}" /></td>
					<td style="text-align: center; border: 1px solid black;"><fmt:formatNumber
							type="number" maxFractionDigits="2"
							value="${hero.stats.seigeDefenseMight}" /></td>
					<td style="text-align: center; border: 1px solid black;"><fmt:formatNumber
							type="number" maxFractionDigits="2" value="${hero.stats.bulwark}" /></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div style="text-align: right; font-weight: lighter; font-size: 8pt;">
		&copy; Indigenous Software Pvt Ltd <sup>&reg;</sup>
	</div>
</body>
</html>