<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
	<title>Escenic Syndication Converter</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/styles.css" />" />
</head>
<body>
	<h1>Escenic Syndication Converter</h1>
	<div class="menu">
		<a href="<c:url value='/' />">Home Page</a>
		<a href="#">Exclude Content</a>
		<a href="#">Delete Section</a>
		<a href="#">Delete Content</a>
		<a href="<c:url value='/administrator/relations' />" onclick="return confirm('Old Analysis Will Be Deleted. Proceed?')">Analyse Relations</a>
		<a href="<c:url value='/administrator/analysis' />">View Analysis</a>
	</div>

	<!-- Total Counters -->
	<c:set var="totalContent" value="${0}" />
	<c:set var="totalProblem" value="${0}" />
	<c:set var="totalCorrect" value="${0}" />
	<c:set var="totalToCorrect" value="${0}" />
	<!-- Problem Counters -->
	<c:set var="excludedBySection" value="${0}" />
	<c:set var="draftOrDeleted" value="${0}" />
	<c:set var="missingInlineRelations" value="${0}" />
	<c:set var="cannotBeReplaced" value="${0}" />
	<c:set var="canBeReplaced" value="${0}" />
	<c:set var="missingRelations" value="${0}" />
	<c:set var="canBeReplacedMissingRelations" value="${0}" />
	
	<h4>'news' Content & Relations Inline Problems Summary</h4>
	<c:forEach var="map" items="${combinedProblems}">
		<c:forEach var="problem" items="${map.value}">
			<div>
				<div class="summaryLeft">
					<c:out value="${map.key}"/>&nbsp;&mdash;&nbsp;
					<c:out value="${problem.key}"/>
				</div>
		  		<div class="summaryRight"><c:out value="${problem.value}"/></div>
		  	</div>
			<c:if test="true">
				<c:set var="totalContent" value="${totalContent+problem.value}" />
			</c:if>
			<c:choose>
				<c:when test="${map.key=='EXCLUDED_BY_SECTION'}">
					<c:set var="excludedBySection" value="${excludedBySection+problem.value}" />
					<c:set var="totalProblem" value="${totalProblem+problem.value}" />
				</c:when>
				<c:when test="${map.key=='DRAFT_OR_DELETED'}">
					<c:set var="draftOrDeleted" value="${draftOrDeleted+problem.value}" />
					<c:set var="totalProblem" value="${totalProblem+problem.value}" />				
				</c:when>
				<c:when test="${map.key=='MISSING_INLINE_RELATIONS'}">
					<c:set var="missingInlineRelations" value="${missingInlineRelations+problem.value}" />
					<c:set var="totalProblem" value="${totalProblem+problem.value}" />
				</c:when>
				<c:when test="${problem.key=='RELATIONS_CANNOT_BE_REPLACED'}">
					<c:set var="cannotBeReplaced" value="${cannotBeReplaced+problem.value}" />
					<c:set var="totalProblem" value="${totalProblem+problem.value}" />
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${problem.key=='null' && map.key=='null'}">
							<c:set var="totalCorrect" value="${totalCorrect+problem.value}" />
						</c:when>
						<c:when test="${problem.key=='RELATIONS_CAN_BE_REPLACED' && map.key=='null'}">
							<c:set var="canBeReplaced" value="${canBeReplaced+problem.value}" />
							<c:set var="totalToCorrect" value="${totalToCorrect+problem.value}" />
						</c:when>
						<c:when test="${problem.key=='null' && map.key=='MISSING_RELATIONS'}">
							<c:set var="missingRelations" value="${missingRelations+problem.value}" />
							<c:set var="totalToCorrect" value="${totalToCorrect+problem.value}" />
						</c:when>
						<c:when test="${problem.key=='RELATIONS_CAN_BE_REPLACED' && map.key=='MISSING_RELATIONS'}">
							<c:set var="canBeReplacedMissingRelations" value="${canBeReplacedMissingRelations+problem.value}" />
							<c:set var="totalToCorrect" value="${totalToCorrect+problem.value}" />
						</c:when>						
					</c:choose>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:forEach>
	<h3>GRAND TOTALS : <c:out value="${totalContent}" /></h3>
	<div style="text-align:center;"><c:out value="${excludedBySection}" /> 'EXCLUDED_BY_SECTION'</div>
	<div style="text-align:center;"><c:out value="${draftOrDeleted}" /> 'DRAFT OR DELETED'</div>
	<div style="text-align:center;"><c:out value="${missingInlineRelations}" /> 'MISSING_INLINE_RELATIONS'</div>
	<div style="text-align:center;"><c:out value="${cannotBeReplaced}" /> 'RELATIONS_CANNOT_BE_REPLACED'</div>
	<h4 style="background-color:#DA4747;">TOTAL EXCUDED ITEMS : <c:out value="${totalProblem}" /></h4>
	<h4 style="background-color:#66FF33;">TOTAL ITEMS WITHOUT PROBLEM : <c:out value="${totalCorrect}" /></h4>	
	<div style="text-align:center;"><c:out value="${canBeReplaced}" /> RELATIONS_CAN_BE_REPLACED&nbsp;&mdash;&nbsp;null</div>
	<div style="text-align:center;"><c:out value="${missingRelations}" /> MISSING_RELATIONS&nbsp;&mdash;&nbsp;null</div>
	<div style="text-align:center;"><c:out value="${canBeReplacedMissingRelations}" /> MISSING_RELATIONS&nbsp;&mdash;&nbsp;RELATIONS_CAN_BE_REPLACED</div>
	<h4 style="background-color:#66FF33;">TOTAL ITEMS WITH PROBLEMS THAT CAN BE CORRECTED : <c:out value="${totalToCorrect}" /></h4>
	<h3>GRAND TOTALS : <c:out value="${totalProblem+totalCorrect+totalToCorrect}" /></h3>

	<div class="note important">Proceed With Content Marshalling : <a href="<c:url value='/' />">Home Page</a></div>
	
	<div class="footer">Copyright � 2014 24MEDIA</div>
</body>
</html>