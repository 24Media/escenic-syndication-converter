/*
* COUNT INLINE <relation/> OR <field/> TAGS TO ENSURE THAT EVERYTHING IS IN PLACE
* @return COUNT OF TAGS PER FIELD ELEMENT. IT'S THE SUM() THAT MAY BE MORE INTERESTING
*/
SET @Tag := '<relation '; #Change This To '<field ' To Count Inline Fields

SELECT C.applicationId AS ContentId, C.type AS ContentType, F.name AS FieldName, @Tag AS Tag,
	   (LENGTH(F.field)-LENGTH(REPLACE(F.field, @Tag, ''))) / LENGTH(@Tag) AS TagCount
FROM field AS F
LEFT JOIN content AS C ON F.contentApplicationId = C.applicationId
HAVING TagCount > 0
ORDER BY TagCount DESC
