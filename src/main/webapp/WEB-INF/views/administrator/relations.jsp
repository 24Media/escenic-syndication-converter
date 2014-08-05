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
		<a href="<c:url value='/administrator/relations' />" onclick="return confirm('Existing Analysis Will Be Deleted. Proceed?')">Analyse Relations</a>
		<a href="<c:url value='/administrator/analysis' />">View Analysis</a>
	</div>
	
	<c:set var="totalContent" value="${0}" />
	<c:set var="totalNewsPhotostory" value="${0}" />
	
	<!-- Pictures -->
	<c:if test="${fn:length(pictureProblems) > 0}">
		<h4>'picture' Content Problems Summary</h4>
		<c:forEach var="entry" items="${pictureProblems}">
			<div>
				<div class="summaryLeft"><c:out value="${entry.key}"/></div>
		  		<div class="summaryRight"><c:out value="${entry.value}"/></div>
		  	</div>
		  	<c:set var="totalContent" value="${totalContent+entry.value}" />
		</c:forEach>
	</c:if>
	<!-- MultipleTypeVideos -->
	<c:if test="${fn:length(multipleTypeVideoProblems) > 0}">
		<h4>'multipleTypeVideo' Content Problems Summary</h4>
		<c:forEach var="entry" items="${multipleTypeVideoProblems}">
			<div>
				<div class="summaryLeft"><c:out value="${entry.key}"/></div>
		  		<div class="summaryRight"><c:out value="${entry.value}"/></div>
		  	</div>
		  	<c:set var="totalContent" value="${totalContent+entry.value}" />
		</c:forEach>
	</c:if>
	<!-- Tags -->
	<c:if test="${fn:length(tagProblems) > 0}">
		<h4>'tag' Content Problems Summary</h4>
		<c:forEach var="entry" items="${tagProblems}">
			<div>
				<div class="summaryLeft"><c:out value="${entry.key}"/></div>
		  		<div class="summaryRight"><c:out value="${entry.value}"/></div>
		  	</div>
		  	<c:set var="totalContent" value="${totalContent+entry.value}" />
		</c:forEach>
	</c:if>
	<!-- Photostories -->
	<c:if test="${fn:length(photostoryProblems) > 0}">
		<h4>'photostory' Content Problems Summary</h4>
		<c:forEach var="entry" items="${photostoryProblems}">
			<div>
				<div class="summaryLeft"><c:out value="${entry.key}"/></div>
		  		<div class="summaryRight"><c:out value="${entry.value}"/></div>
		  	</div>
		  	<c:set var="totalContent" value="${totalContent+entry.value}" />
		  	<c:set var="totalNewsPhotostory" value="${totalNewsPhotostory+entry.value}" />
		</c:forEach>
	</c:if>
	<!-- News -->
	<c:set var="analyseAnchors" value="${0}" />
	<c:if test="${fn:length(combinedProblems) > 0}">
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
			  	<c:if test="${problem.key=='RELATIONS_NEEDS_REPLACEMENT' && (map.key=='MISSING_RELATIONS' || map.key == 'null')}">
			  		<c:set var="analyseAnchors" value="${analyseAnchors+problem.value}"></c:set>
			  	</c:if>
			  	<c:set var="totalContent" value="${totalContent+problem.value}" />
		  		<c:set var="totalNewsPhotostory" value="${totalNewsPhotostory+problem.value}" />
			</c:forEach>
		</c:forEach>
	</c:if>
	
	<h3>GRAND TOTALS : <c:out value="${totalContent}" /> [ <c:out value="${totalNewsPhotostory}" /> 'photostory' | 'news' ]</h3>
	
	<div class="note important">Proceed With Analysis of Inline Anchors For <c:out value="${analyseAnchors}" /> 'news' Contents: <a href="<c:url value='/administrator/anchors/' />" onclick="return confirm('Existing Anchor Analysis Will Be Deleted. Proceed?')">/administrator/anchors/</a></div>
	
	<div class="footer">Copyright &copy; 2014 24MEDIA</div>
</body>
</html>