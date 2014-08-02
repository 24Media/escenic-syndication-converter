escenic-syndication-converter
=============================

### About
Convertion of Exported Escenic Syndication Files to the Required by the Import Mechanism Format

### Technologies / Tools
<ul>
<li>Spring Framework</li>
<li>Spring MVC</li>
<li>Hibernate ORM</li>
<li>JAXB</li>
<li>dom4j</li>
<li>jsoup</li>
</ul>


### To Get You Started
1) mysqldump Restore the Database you'll find in src/main/resources/database/mysql

2) Create src/main/resources/database/database.properties File having at least the following properties :
```
dataSource.driverClassName=com.mysql.jdbc.Driver
dataSource.url=jdbc:mysql://localhost:3306/escenic-syndication-converter?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=UTF-8
dataSource.username=your-username
dataSource.password=your-password
```
3) Create src/main/resources/miscellaneous.properties having at least the following property :
```
filepath.syndicationFiles=/home/blixabargeld/Desktop/folder-for-read-and-write
```
### Import Methodology
1) The first thing is to ensure that 'picture' and 'multipleTypeVideo' Content Items have correctly imported. There are -fortunately few- cases that 'picture' binaries exported are damaged, 'multipleTypeVideo' with no obvious reason can not get imported et al. Manually we must give the characterization MISSING_BINARIES to all these cases.      

2) 'news' Contents with home sections that do not interest us gets excluded next. Home sections like '%kairos%' is one good example. The characterization is EXCLUDED_BY_SECTION and can be given with /administrator/relations Controller.

3) All Content Items with state 'draft' or 'deleted' gets excluded also. The characterization is -surprisingly- DRAFT_OR_DELETED and the Controller is /administrator/relations.

4) The next thing is to ensure that all Contents' Relations exist. If the related Content Item does not exist or has a characterization such as MISSING_BINARIES, DRAFT_OR_DELETED etc. the Content gets charactezized as MISSING_BINARIES. The same characterization is given to the missing Relation Items also because, if we like, we can remove these Relations from the Content Item and proceed with the marshalling. The job is also done with the /administrator/relations Controller.

5) To make our lives a little more difficult, if anchor tags follow inline 'news' relations (such as 'picture' or 'multipleTypeVideo') exports the article with the inline relation replacing all following anchors (till it finds the next inline relation). So as an exception we define the RelationInline Item and we try to correct these duplicate occurences. The /administrator/relations Controller parses all 'news' body fields to persist Inline Relations, ensure that these relations exist (if not the Content gets characterized as MISSING_INLINE_RELATIONS and considered rubbish) and finally finds the duplicate Inline Relations giving the characterization RELATIONS_NEEDS_REPLACEMENT to both the Content and RelationInline Items.

### Future Improvements
1) Due to tight deadlines Field's `((ANYTHING|<relation>...</relation>|text)*|<field>...</field>*|<value>...</value>*)<options>...</options>?` Element substituted by text inside CDATA tokens. Further parsing of this Element may be a good idea.

2) Find a way to correctly Import of Author and Creator Entities to Escenic.

3) Model remaining Escenic Entities (Person, Inbox et al.)

4) Create a real User Interface.
