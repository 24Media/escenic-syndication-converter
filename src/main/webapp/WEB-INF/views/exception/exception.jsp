<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Escenic Syndication Converter</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/styles.css" />" />
</head>
<body>
<h1>Escenic Syndication Converter</h1>

	<c:if test="${not empty errorMessage}">
		<p>
			<strong>Exception :</strong> 
			${errorMessage}
		</p>
	</c:if>

	<c:if test="${not empty errorStackTrace}">
		<p>
			<strong>Stack Trace :</strong><br /><br />
			<code>${errorStackTrace}</code>
		</p>
	</c:if>
	
</body>
</html>