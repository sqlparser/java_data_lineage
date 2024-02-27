## setting: show constant
oracle
```
SELECT deptno,  '001' sortcode  FROM   scott.emp  WHERE  city = 'NYC' 
```
以上sql中存在2个常量，其显示常量数据血缘的数据流图如下图所示：

![png](../images/setting_constant_01.png)

如果您关闭“show constant”，数据流结果如下：

![png](../images/setting_constant_02.png)