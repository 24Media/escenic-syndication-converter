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
	<!--
		*
		* Content Problems Analysis Per Type
		*
	-->
	<!-- Pictures -->
	<c:if test="${fn:length(pictureProblems) > 0}">
		<h4>'picture' Content Problems Summary</h4>
		<c:forEach var="entry" items="${pictureProblems}">
			<div>
				<div class="summaryLeft"><c:out value="${entry.key}"/></div>
		  		<div class="summaryRight"><c:out value="${entry.value}"/></div>
		  	</div>
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
		</c:forEach>
	</c:if>
	<!-- news -->
	<c:if test="${fn:length(newsProblems) > 0}">
		<h4>'news' Content Problems Summary</h4>
		<c:forEach var="entry" items="${newsProblems}">
			<div>
				<div class="summaryLeft"><c:out value="${entry.key}"/></div>
		  		<div class="summaryRight"><c:out value="${entry.value}"/></div>
		  	</div>
		</c:forEach>
	</c:if>	
	<!--
		*
		* Relations Inline Problems Analysis For 'news'
		*
	-->
	<c:if test="${fn:length(newsDuplicates) > 0}">
		<h4 style="background-color:#DA4747;">'news' Relations Inline Problems Summary</h4>
		<c:forEach var="entry" items="${newsDuplicates}">
			<div>
				<div class="summaryLeft"><c:out value="${entry.key}"/></div>
		  		<div class="summaryRight"><c:out value="${entry.value}"/></div>
		  	</div>
		</c:forEach>
	</c:if>
	<!--
		*
		* Combined Problems Analysis For 'news'
		*
	-->
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
			  		<c:set var="analyseAnchors" value="${analyseAnchors+problem.value}">
			  	</c:set></c:if>		  	
			</c:forEach>
		</c:forEach>
	</c:if>
	
	<div class="note important">Proceed With Analysis of Inline Anchors For <c:out value="${analyseAnchors}" /> 'news' Contents: <a href="<c:url value='/administrator/anchors/' />">/administrator/anchors/</a></div>
	
	<div class="footer">Copyright © 2014 24MEDIA</div>
</body>
</html>