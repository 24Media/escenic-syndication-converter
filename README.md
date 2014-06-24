### In Progress
I cannot guarantee you'll clone a stable version. Proceed at yout own risk.

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
