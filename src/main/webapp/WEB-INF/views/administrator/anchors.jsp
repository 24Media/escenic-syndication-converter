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
	</div>

	<h4>'news' Content Problems Summary</h4>
	<c:forEach var="entry" items="${newsProblems}">
		<div>
			<div class="summaryLeft"><c:out value="${entry.key}"/></div>
	  		<div class="summaryRight"><c:out value="${entry.value}"/></div>
	  	</div>
	</c:forEach>
	
	<div class="footer">Copyright © 2014 24MEDIA</div>
</body>
</html>