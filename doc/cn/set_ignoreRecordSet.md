## setting:ignoreRecordSet
oracle
```
INSERT ALL
	WHEN ottl < 100000 THEN
		INTO small_orders
			VALUES(oid, ottl, sid, cid)
	WHEN ottl > 100000 and ottl < 200000 THEN
		INTO medium_orders
			VALUES(oid, ottl, sid, cid)
	WHEN ottl > 200000 THEN
		into large_orders
			VALUES(oid, ottl, sid, cid)
	WHEN ottl > 290000 THEN
		INTO special_orders
SELECT o.order_id oid, o.customer_id cid, o.order_total ottl,
o.sales_rep_id sid, c.credit_limit cl, c.cust_email cem
FROM orders o, customers c
WHERE o.customer_id = c.customer_id;
```
![png](../images/setting_ignoreRecordSet_01.png)

以上sql未忽略中间结果集情况下，会有一个insert_select中间结果，其数据流图如下所示：

![png](../images/setting_ignoreRecordSet_02.png)

如果您打开“ignoreRecordSet”，数据流结果如下：

![png](../images/setting_ignoreRecordSet_03.png) 