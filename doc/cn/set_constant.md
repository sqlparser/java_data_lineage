## show constant

```
SELECT deptno,  '001' sortcode  FROM   scott.emp  WHERE  city = 'NYC' 
```
数据流图示：

![png](../images/setting_constant_01.png)

如果您关闭“show constant”，数据流结果如下：

![png](../images/setting_constant_02.png)