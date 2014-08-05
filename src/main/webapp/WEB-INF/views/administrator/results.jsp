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
		<a href="<c:url value='/administrator/relations' />" onclick="return confirm('Existing Analysis Will Be Deleted. Proceed?')">Analyse Relations</a>
		<a href="<c:url value='/administrator/analysis' />">View Analysis</a>
	</div>
	
	<c:choose>
		<c:when test="${not empty random}">
			<div class="note">A Random Content With No Content or Relation Inline Problems Marshalled</div>
		</c:when>
		<c:otherwise>

			<div class="note">${countMarshalled} Content Items Marshalled</div>
			<div class="note">${cannotCorrectDuplicates} Content With Inline Duplicate Relations Did Not Corrected</div>
		
		</c:otherwise>
	</c:choose>
	
	<div class="note important">Continue Marshalling Here : <a href="<c:url value='/' />">Home Page</a> Or Here : <a href="<c:url value='/administrator/analysis' />">/administrator/analysis/</a></div>
		
	<div class="footer">Copyright &copy; 2014 24MEDIA</div>
</body>
</html>