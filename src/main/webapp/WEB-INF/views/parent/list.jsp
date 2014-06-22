<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
	<title>List Of Sections</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/styles.css" />" />
</head>
<body>
<h1>View Of Parent</h1>
<h2>${fn:length(sectionList)} Sections In Database</h2>
<table>
	<tr>
		<th>Attibute</th><th>Value</th>
	</tr>
	<tr>
		<td>Id-Ref</td><td>${parent.idRef}</td>
	</tr>
	<tr>
		<td>Source</td><td>${parent.source}</td>
	</tr>
	<tr>
		<td>Source-Id</td><td>${parent.sourceId}</td>
	</tr>
	<tr>
		<td>Db-Id</td><td>${parent.dbId}</td>
	</tr>
	<tr>
		<td>Exported-Db-Id</td><td>${parent.exportedDbId}</td>
	</tr>
	<tr>
		<td>Unique Name</td><td>${parent.uniqueName}</td>
	</tr>
	<tr>
		<td>Inherit Access Control List</td>${parent.inheritAccessControlList}<td></td>
	</tr>
	
	
	
	<c:forEach var="childSection" items="">
		<tr>
			<td><c:out value="" /></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
