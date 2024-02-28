## Show ResultSet Types：unpivot_table

oracle
```
select * from pivot_sales_data
unpivot(
amount for month in (jan, feb, mar, apr)
)
order by prd_type_id;
```
显示前：

![png](../images/rt_unpivot_table_01.png) 

显示后：

![png](../images/rt_unpivot_table_02.png)