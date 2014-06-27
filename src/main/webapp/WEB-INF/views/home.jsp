<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<html>
<head>
	<title>Escenic Syndication Converter</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/styles.css" />" />
</head>
<body>
<h1>Escenic Syndication Converter</h1>

<h3>Basic Actions</h3>
<a href="<c:url value='/section/unmarshall' />">Read Sections XML</a>
<a href="<c:url value='/section/marshall' />">Create Sections XML</a>
<a href="<c:url value='/content/unmarshall' />">Read Content XML</a>
<a href="<c:url value='/content/marshall' />">Create Content XML</a>

<br /><br />

<h3>Export Contents By Type</h3>
<a href="<c:url value='/content/marshall?id=7070' />">Export Random</a>
<a href="<c:url value='/content/marshall?type=news' />">Export news</a>
<a href="<c:url value='/content/marshall?type=picture' />">Export picture</a>
<a href="<c:url value='/content/marshall?type=multipleTypeVideo' />">Export multipleTypeVideo</a>
<a href="<c:url value='/content/marshall?type=advertorial' />">Export advertorial</a>
<a href="<c:url value='/content/marshall?type=advertorial' />">Export All</a>

</body>
</html>
