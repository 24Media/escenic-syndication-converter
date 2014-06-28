<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<html>
<head>
	<title>Escenic Syndication Converter</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/styles.css" />" />
</head>
<body>
<h1>Escenic Syndication Converter</h1>
<h3>READ</h3>
<a href="<c:url value='/section/unmarshall' />">Read Sections XML</a><br />
<a href="<c:url value='/content/unmarshall' />">Read Content XML</a><br />

<h3>WRITE</h3>
<div class="leftColumn">
	<h4 class="title">Results To One File</h4>
	<h4>Sections</h4>
		<a href="<c:url value='/section/marshall' />">All Sections</a><br />
	<h4>Content</h4>
		<!-- All -->
		<a href="<c:url value='/content/marshall' />">All Contents</a><br />
		<a href="<c:url value='/content/marshall?id=7070' />"> A (Not So) Random Content</a><br />
		<!-- Type -->
		<a href="<c:url value='/content/marshall?type=news' />">All 'news'</a><br />
		<a href="<c:url value='/content/marshall?type=picture' />">All 'picture'</a><br />
		<a href="<c:url value='/content/marshall?type=multipleTypeVideo' />">All 'multipleTypeVideo'</a><br />
		<!-- Type Excluding Home Section -->
		<a href="<c:url value='/content/marshall?type=news&homeSections=kairos&homeSectionsExcluded=exclude' />">'news' Excluding Home Section 'kairos'</a><br />
		<a href="<c:url value='/content/marshall?type=picture&homeSections=kairos&homeSectionsExcluded=exclude' />">'picture' Excluding Home Section 'kairos'</a><br />
		<a href="<c:url value='/content/marshall?type=multipleTypeVideo&homeSections=kairos&homeSectionsExcluded=exclude' />">'multipleTypeVideo' Excluding Home Section 'kairos'</a><br />
		<!-- Type and HomeSection -->
		<a href="<c:url value='/content/marshall?type=news&homeSections=kairos' />">'news' with Home Section 'kairos'</a><br />
		<a href="<c:url value='/content/marshall?type=picture&homeSections=kairos' />">'picture' with Home Section 'kairos'</a><br />
		<a href="<c:url value='/content/marshall?type=multipleTypeVideo&homeSections=kairos' />">'multipleTypeVideo' with Home Section 'kairos'</a><br />
</div>
<div class="rightColumn">
	<h4 class="title">Results To Multiple Files (*1)</h4>
	<h4>Content</h4>
		<!-- Type -->
		<a href="<c:url value='/content/marshallToMultipleFiles?itemsPerFile=10' />">All Contents</a><br />
		<a href="<c:url value='/content/marshallToMultipleFiles?type=news&itemsPerFile=10' />">All 'news'</a><br />
		<a href="<c:url value='/content/marshallToMultipleFiles?type=picture&itemsPerFile=10' />">All 'picture'</a><br />
		<a href="<c:url value='/content/marshallToMultipleFiles?type=multipleTypeVideo&itemsPerFile=10' />">All 'multipleTypeVideo'</a><br />
		<!-- Type Excluding Home Section -->
		<a href="<c:url value='/content/marshallToMultipleFiles?type=news&homeSections=kairos&homeSectionsExcluded=exclude&itemsPerFile=1' />">'news' Excluding Home Section 'kairos'</a><br />
		<a href="<c:url value='/content/marshallToMultipleFiles?type=picture&homeSections=kairos&homeSectionsExcluded=exclude&itemsPerFile=10' />">'picture' Excluding Home Section 'kairos'</a><br />
		<a href="<c:url value='/content/marshallToMultipleFiles?type=multipleTypeVideo&homeSections=kairos&homeSectionsExcluded=exclude&itemsPerFile=10' />">'multipleTypeVideo' Excluding Home Section 'kairos'</a><br />
		<!-- Type and HomeSection -->
		<a href="<c:url value='/content/marshallToMultipleFiles?type=news&homeSections=kairos&itemsPerFile=1' />">'news' with Home Section 'kairos'</a><br />
		<a href="<c:url value='/content/marshallToMultipleFiles?type=picture&homeSections=kairos&itemsPerFile=10' />">'picture' with Home Section 'kairos'</a><br />
		<a href="<c:url value='/content/marshallToMultipleFiles?type=multipleTypeVideo&homeSections=kairos&itemsPerFile=10' />">'multipleTypeVideo' with Home Section 'kairos'</a><br />
</div>
<h5>(*1) Default ItemsPerFile Value Is 10. For Now The Only Way To Change It Is Via The URL Parameter</h5>

<h3>OTHER</h3>
<a href="<c:url value='/administrator/relationCheck' />">Check Relations</a><br />

</body>
</html>
