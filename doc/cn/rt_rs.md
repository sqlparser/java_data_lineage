## Show ResultSet Types：rs

oracle
```
with aa as
(
select country_id ,row_number() over(order by country_id ) as ida
from contry a
)
, bb as
(
select country_id ,row_number() over(order by country_id ) as idb
from contry
)
, cc as
(
select  aa.ida, bb.idb from aa left join bb on aa.ida=bb.idb
)
select * from cc;
```
显示前：

![png](../images/rt_rs_01.png)

显示后：

![png](../images/rt_rs_02.png)