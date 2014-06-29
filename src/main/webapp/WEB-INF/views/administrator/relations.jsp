<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
	<title>Escenic Syndication Converter</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/styles.css" />" />
</head>
<body>
<h1>Escenic Syndication Converter</h1>
<h3>${fn:length(relations)} Relations | Content Does Not Exist For ${nullRelations}</h3>
<table>
	<tr>
		<th style="width:50px;">Content Application Id</th>
		<th>Content Type</th>
		<th>Content Home Section</th>
		<th>Relation Source</th>
		<th>Relation Source Id</th>
		<th>Relation Type</th>
		<th>Related Content Type</th>
		<th>Picture Binary Name</th>
	</tr>
	<c:forEach var="relation" items="${relations}">
		<tr>
			<td><c:out value="${relation.contentApplicationId}" /></td>
			<td><c:out value="${relation.contentType}" /></td>
			<td><c:out value="${relation.contentHomeSection}" /></td>
			<td><c:out value="${relation.source}" /></td>
			<td><c:out value="${relation.sourceId}" /></td>
			<td><c:out value="${relation.relationType}" /></td>
			<!-- Related Content Type -->
			<c:choose>
				<c:when test="${empty relation.relatedContentType}">
					<td class="error">-</td>			
				</c:when>
				<c:otherwise>
					<td><c:out value="${relation.relatedContentType}" /></td>
				</c:otherwise>
			</c:choose>
			<!-- Picture Binary Name -->
			<c:choose>
				<c:when test="${not empty relation.pictureBinaryName}">
					<td><c:out value="${relation.pictureBinaryName}" /></td>
				</c:when>
				<c:when test="${relation.relationType=='INLINE'}">
					<td class="warning">-</td>
				</c:when>
				<c:when test="${relation.relationType=='PICTUREREL'}">
					<td class="error">-</td>
				</c:when>
				<c:otherwise>
					<td>-</td>
				</c:otherwise>
			</c:choose>
		</tr>
	</c:forEach>
</table>	
</body>
</html>