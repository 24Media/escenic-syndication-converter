<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
	<title>List Of Sections</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/styles.css" />" />
</head>
<body>
<h1>List Of Sections</h1>
<h2>${fn:length(sectionList)} Sections In Database</h2>
<table>
	<tr>
		<th>Id</th>
		<th>Source</th>
		<th>Source-Id</th>
		<th>Db-Id</th>
		<th>Exported-Dd-Id</th>
		<th>Name</th>
		<th>Unique Name Attribute</th>
		<th>Mirror Source Attribute</th>
		<th>Parent</th>
		<th>Mirror Source Element</th>
		<th>Unique Name Element</th>
		<th>Directory</th>
		<th>Section Layout</th>
		<th>Article Layout</th>
		<th>Priority</th>
	</tr>
	<c:forEach var="sections" items="${sectionList}">
		<tr>
			<td><c:out value="${sections.id}" /></td>
			<td class="blue"><c:out value="${sections.source}" /></td>
			<td class="blue"><c:out value="${sections.sourceId}" /></td>
			<td><c:out value="${sections.dbId}" /></td>
			<td><c:out value="${sections.exportedDbId}" /></td>
			<td><c:out value="${sections.name}" /></td>
			<td><c:out value="${sections.uniqueNameAttribute}" /></td>
			<td><c:out value="${sections.mirrorSourceAttribute}" /></td>
			<td class="red"><a href="<c:url value='/parent/list?applicationId=${sections.parent.applicationId}' />"><c:out value="${sections.parent.uniqueName}" /></a></td>
			<td><c:out value="${sections.mirrorSourceElement.uniqueName}" /></td>
			<td class="red"><a href="<c:url value='/section/view?applicationId=${sections.applicationId}' />"><c:out value="${sections.uniqueNameElement}" /></a></td>
			<td><c:out value="${sections.directory}" /></td>
			<td><c:out value="${sections.sectionLayout}" /></td>
			<td><c:out value="${sections.articleLayout}" /></td>
			<td><c:out value="${sections.priority}" /></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
