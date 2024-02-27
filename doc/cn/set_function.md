## setting:show function
在数据流分析过程中，function起着关键作用，它接受列作为参数，并生成可能是标量值或集合值的结果。

oracle
```
select round(salary) as sal from scott.emp
```
在上述SQL中，从列salary到round函数生成一个直接数据流：

> scott.emp.salary -> direct -> round(salary) -> direct -> sal

数据流图示：

![setting_indirect_01.png](../images/setting_function_01.png)

如果您关闭“show function”，数据流结果如下：

![setting_indirect_01.png](../images/setting_function_02.png)

