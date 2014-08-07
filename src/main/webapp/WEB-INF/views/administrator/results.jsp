<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<a href="<c:url value='/administrator/analyze' />" onclick="return confirm('Existing Analysis Will Be Deleted. Proceed?')">Analyze Contents</a>
		<a href="<c:url value='/administrator/analysis' />">View Analysis</a>
	</div>
	
	<br />
	
	<c:choose>
		<c:when test="${not empty random}">
			<div class="note">A Random Content With No Content or Relation Inline Problems Marshalled</div>
		</c:when>
		<c:otherwise>
			<div class="note">${marshalled} Content Items With Type = '${type}' Marshalled</div>
			<c:if test="${fileCounter!=0}"><div class="note">${fileCounter} Files Created with ${itemsPerFile} Items Per File</div></c:if>
			<c:if test="${problem=='C' || problem=='D'}"><div class="note">Replacements Have Been Made To Correct Duplicate Inline Relations</div></c:if>
			<c:if test="${(problem=='B' || problem=='D') && empty missing}"><div class="note">Generated Filenames Indicate How Many Relations Excluded For Every Content</div></c:if>
			<c:if test="${cannotCorrectDuplicates!=0}"><div class="note">${cannotCorrectDuplicates} Content With Inline Duplicate Relations Did Not Corrected</div></c:if>
		</c:otherwise>
	</c:choose>
	
	<br />
	
	<div class="note important">Continue Marshalling Here : <a href="<c:url value='/' />">Home Page</a> Or Here : <a href="<c:url value='/administrator/analysis' />">/administrator/analysis/</a></div>
		
	<div class="footer">Copyright &copy; 2014 24MEDIA</div>
</body>
</html>