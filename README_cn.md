## Gudu SQLFlow Lite version for java

[Gudu SQLFlow](https://sqlflow.gudusoft.com) 是一款用来分析各种数据库的SQL语句和存储过程来获取复杂的数据血缘关系并进行可视化的工具。

[Gudu SQLFlow Lite version for Java](https://github.com/sqlparser/java_data_lineage)适用于Java，允许Java开发人员快速将数据血缘分析和可视化功能集成到自己的Java应用程序中。数据分析人员也可以在日常工作中使用它，以快速发现复杂SQL脚本中的数据血缘关系，这些脚本通常用于在大型数据平台上进行数据转换的ETL作业。

[Gudu SQLFlow Lite version for Java](https://github.com/sqlparser/java_data_lineage)适用于非商业用途，并且可以处理长度最多为10k的任何复杂SQL语句，包括对存储过程的支持。它支持来自20多个主要数据库供应商（如Oracle、DB2、Snowflake、Redshift、Postgres等）的SQL。

[Gudu SQLFlow Lite version for Java](https://github.com/sqlparser/java_data_lineage)适用于Java 包括一个用于分析复杂SQL语句和存储过程以检索数据血缘关系的Java库，以及一个用于可视化数据血缘关系的JavaScript库。

[Gudu SQLFlow Lite version for Java](https://github.com/sqlparser/java_data_lineage)适用于Java 还可以自动[从数据库导出的DDL脚本](https://docs.gudusoft.com/6.-sqlflow-ingester/introduction)中提取表和列约束，以及表和字段之间的关系，并生成ER图。


### 构建和运行程序
这是一个将浏览器端应用程序用Thymeleaf集成的Spring Boot Web服务。因此，您无需安装额外的Web容器，如Nginx。

#### 先决条件
* 安装Maven
* 安装Java jdk1.8
### 构建
使用以下Maven命令进行编译，编译后的JAR包位于target文件夹下。
```
mvn package
```
当然，您也可以跳过此步骤，因为Bin文件夹中已经有一个编译好的可执行文件。

### 运行程序
```
java -jar java_data_lineage-1.0.0.jar
```
启动完成后，在浏览器中打开以下网址以访问程序：http://localhost:9600

默认端口号是9600，如果您需要更改端口号，例如改为8000，可以使用以下命令启动：
```
java -jar java_data_lineage-1.0.0.jar --server.port=8000
```
### 界面参数说明
##### dbvendor 指定数据库类型，默认是 oracle。

>支持 access,bigquery,couchbase,dax,db2,greenplum, gaussdb, hana,hive,impala,informix,mdx,mssql,
sqlserver,mysql,netezza,odbc,openedge,oracle,postgresql,postgres,redshift,snowflake,
sybase,teradata,soql,vertica 

##### Setting 常用参数设置

* indirect 显示间接血缘关系

* show function 显示函数

* show constant 显示常量

* ignoreRecordSet 忽略中间结果集

* table level 显示表级血缘关系

##### Show ResultSet Types: 带有指定结果集类型的简单输出
>可选结果集类型有： array, struct, result_of, cte, insert_select, update_select, merge_update, merge_insert, output, update_set pivot_table, unpivot_table, alias, rs, function, case_when

### 从各种数据库导出元数据
您可以使用[SQLFlow ingester](https://github.com/sqlparser/sqlflow_public/releases) 从数据库导出元数据，并将其交给Gudu SQLFlow进行数据血缘分析。

[SQLFlow ingester的文档](https://docs.gudusoft.com/6.-sqlflow-ingester/introduction)

### 联系方式
如需更多信息，请联系support@gudusoft.com。
