## Show ResultSet Types：function

oracle
```
SELECT
COUNT( CASE WHEN AGE = 18 THEN 'countof18' END) EIGHTENN,
COUNT( CASE WHEN AGE = 19 THEN 'countof19' END) NINETEEN
FROM
PeopleInfo
```

显示前：

![png](../images/rt_function_01.png)

显示后：

![png](../images/rt_function_02.png)