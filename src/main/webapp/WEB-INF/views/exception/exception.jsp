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
	
	<c:if test="${not empty errorMessage}">
		<h4>Exception Error Message</h4>
		<p>${errorMessage}</p>
	</c:if>
	<c:if test="${not empty errorStackTrace}">
		<h4>Exception Stack Trace</h4>
		<p><code>${errorStackTrace}</code></p>
	</c:if>
	
	<div class="footer">Copyright &copy; 2014 24MEDIA</div>
</body>
</html>