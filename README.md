### About
Convertion of Exported Escenic Syndication Files to the Required by the Import Mechanism Format

### Technologies / Tools
<ul>
<li>Spring Framework</li>
<li>Spring MVC</li>
<li>Hibernate ORM</li>
<li>JAXB</li>
<li>dom4j</li>
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

### Future Improvements
1) It's ridiculous to parse the marshalled files in order to replace escaped HTML Characters just because you don't know how to completely disable HTML Character escaping. Find the way or change your JAXB Implementation to some other.

2) Due to tight deadlines Field's `((ANYTHING|<relation>...</relation>|text)*|<field>...</field>*|<value>...</value>*)<options>...</options>?` Element substituted by text inside CDATA tokens. Further parsing of this Element may be a good idea.

3) Create a real User Interface.
