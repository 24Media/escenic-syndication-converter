/*
* ENSURE THAT ALL FIELDS INSIDE BODY FIELDS ARE SELF CLOSING TAGS OF THE FOLLOWING TYPES :
* title, captiontitle, caption, alttext, description
* @return 0 ROWS IF EVERYTHING IS ALRIGHT
*/
SELECT * FROM (
	SELECT InitialSet.contentApplicationId AS ContentId, InitialSet.name AS FieldName, 
	REPLACE(	
		REPLACE(
			REPLACE(
				REPLACE(
					REPLACE(InitialSet.field, '<field name="title"/>', ''), 
				'<field name="captiontitle"/>', ''),
			'<field name="caption"/>', ''),
		'<field name="alttext"/>', ''),
	'<field name="description"/>', '')
	AS UnknownCases
	#LIMIT INITIAL SET ONLY TO FIELDS THAT INTERESTS YOU
	FROM (	SELECT contentApplicationId, name, field
			FROM field
			WHERE field LIKE '%<field %'
	) AS InitialSet
) AS FinalSet WHERE FinalSet.UnknownCases LIKE '%<field %'
