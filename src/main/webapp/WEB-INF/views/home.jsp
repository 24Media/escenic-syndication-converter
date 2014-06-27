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

<h3>Export Contents By Type To One File</h3>
<a href="<c:url value='/content/marshall?id=7070' />">Not So Random Item</a>
<a href="<c:url value='/content/marshall?type=news' />">'news'</a>
<a href="<c:url value='/content/marshall?type=picture' />">'picture'</a>
<a href="<c:url value='/content/marshall?type=multipleTypeVideo' />">'multipleTypeVideo'</a>
<a href="<c:url value='/content/marshall?type=advertorial' />">'advertorial'</a>
<a href="<c:url value='/content/marshall' />">Export All</a>

<h3>Export Contents By Type To Multiple Files</h3>
<a href="<c:url value='/content/marshallToMultipleFiles?type=news&itemsPerFile=1' />">'news'</a>
<a href="<c:url value='/content/marshallToMultipleFiles?type=picture&itemsPerFile=1' />">'picture'</a>
<a href="<c:url value='/content/marshallToMultipleFiles?type=multipleTypeVideo&itemsPerFile=1' />">'multipleTypeVideo'</a>
<a href="<c:url value='/content/marshallToMultipleFiles?type=advertorial&itemsPerFile=1' />">'advertorial'</a>
<a href="<c:url value='/content/marshallToMultipleFiles?itemsPerFile=1' />">Export All</a>

</body>
</html>
