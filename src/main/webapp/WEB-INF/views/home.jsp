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
		<a href="<c:url value='/administrator/relations' />" onclick="return confirm('Old Analysis Will Be Deleted. Proceed?')">Analyse Relations</a>
		<a href="<c:url value='/administrator/analysis' />">View Analysis</a>
	</div>
	
	<h3>READ | UNMARSHALL</h3>
	<div class="leftColumn">
		<a href="<c:url value='/section/unmarshall' />">Read 'section' XML</a><br />
	</div>
	<div class="rightColumn">
		<a href="<c:url value='/content/unmarshall' />">Read 'content' XML</a><br />
	</div>
	
	<h3>WRITE | MARSHALL</h3>
	<div class="leftColumn">
		<h4 class="title">Marshall To One File</h4>
		<h4>Sections</h4>
			<a href="<c:url value='/section/marshall' />">'section'</a><br />
		<h4>Content</h4>
			<a href="<c:url value='/content/marshall?id=25979&type=type' />"> A (Not So) Random Content</a><br />
			<a href="<c:url value='/content/marshall?type=tag' />">'tag'</a><br />
			<a href="<c:url value='/content/marshall?type=picture' />">'picture'</a><br />
			<a href="<c:url value='/content/marshall?type=multipleTypeVideo' />">'multipleTypeVideo'</a><br />
			<a href="<c:url value='/content/marshall?type=photostory' />">'photostory'</a><br />
			<a href="<c:url value='/content/marshall?type=news' />">'news'</a><br />
	</div>
	<div class="rightColumn">
		<h4 class="title">Marshall To Multiple Files</h4>
		<h4>Content</h4>
			<a href="<c:url value='/content/marshallToMultipleFiles?type=tag&itemsPerFile=10' />">'tag'</a><br />
			<a href="<c:url value='/content/marshallToMultipleFiles?type=picture&itemsPerFile=10' />">'picture'</a><br />
			<a href="<c:url value='/content/marshallToMultipleFiles?type=multipleTypeVideo&itemsPerFile=10' />">'multipleTypeVideo'</a><br />
			<a href="<c:url value='/content/marshallToMultipleFiles?type=photostory&itemsPerFile=10' />">'photostory'</a><br />
		<a href="<c:url value='/content/marshallToMultipleFiles?type=news&itemsPerFile=10' />">'news'</a><br />	
	</div>
	<div class="footer">Copyright � 2014 24MEDIA</div>
</body>
</html>