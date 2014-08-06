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
	
	<h3>READ | UNMARSHALL</h3>
	<div class="leftColumn">
		<a href="<c:url value='/section/unmarshall' />">Unmarshall 'section' XML</a><br />
	</div>
	<div class="rightColumn">
		<a href="<c:url value='/content/unmarshall' />">Unmarshall 'content' XML</a><br />
	</div>
	
	<h3>WRITE | MARSHALL</h3>
	<div class="leftColumn">
		<h4>Sections</h4>
		<a href="<c:url value='/section/marshall' />">'section'</a><code>[<c:out value="${countSection}" />]</code><br />	
	</div>
	<div class="rightColumn">
		<h4>Contents</h4>
		
		<a href="<c:url value='/content/marshall?random=random&type=type&itemsPerFile=1' />"> A Random Content</a><br />
	
		<!-- Marshall Tags -->
		<strong style="padding-left:1.4em;">'tag'</strong>
		<code>[<c:out value="${countTag}" />]</code>
		<a href="<c:url value='/content/marshall?type=tag&itemsPerFile=1' />">1 / File</a>
		<a href="<c:url value='/content/marshall?type=tag&itemsPerFile=10' />">10 / File</a>
		
		<br />
		
		<!-- Marshall Pictures -->
		<strong style="padding-left:1.4em;">'picture'</strong>
		<code>[<c:out value="${countPicture}" />]</code>
		<a href="<c:url value='/content/marshall?type=picture&itemsPerFile=1' />">1 / File</a>
		<a href="<c:url value='/content/marshall?type=picture&itemsPerFile=10' />">10 / File</a>
		
		<br />
		
		<!-- Marshall MultipleTypeVideos -->
		<strong style="padding-left:1.4em;">'multipleTypeVideo'</strong>
		<code>[<c:out value="${countMultipleTypeVideo}" />]</code>
		<a href="<c:url value='/content/marshall?type=multipleTypeVideo&itemsPerFile=1' />">1 / File</a>
		<a href="<c:url value='/content/marshall?type=multipleTypeVideo&itemsPerFile=10' />">10 / File</a>
		
		<br />

	</div>
	
	<div class="note important">To Marshall 'photostory' or 'news' You Have To Analyse Relations First : <a href="<c:url value='/administrator/relations/' />" onclick="return confirm('Existing Analysis Will Be Deleted. Proceed?')">/administrator/relations/</a></div>
	
	<div class="footer">Copyright &copy; 2014 24MEDIA</div>
</body>
</html>