## Show ResultSet Types：merge_update

oracle
```
merge into t_B_info_bb b
using t_B_info_aa a
on (a.id = b.id and a.type = b.type)
when matched then
update  set b.price = a.price

when not matched then
insert (id, type, price) values (a.id, a.type, a.price) 
```
显示前：

![png](../images/rt_merge_update_01.png)

显示后：

![png](../images/rt_merge_update_02.png)