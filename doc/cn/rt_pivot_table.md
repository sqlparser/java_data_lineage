## Show ResultSet Types：pivot_table 

oracle
```
select * from table2 pivot(max(value) as attr for(attr) in('age' as age,'sex' as sex));
```
显示前：

![png](../images/rt_pivot_table_01.png)

显示后：

![png](../images/rt_pivot_table_02.png)