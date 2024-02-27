## Show ResultSet Types：update_select

oracle
```
UPDATE employees SET salary = (SELECT salary * 1.1 FROM employees WHERE department_id = 80) WHERE department_id = 80; 
```
显示前：

![png](../images/rt_update_select_01.png)

显示后：

![png](../images/rt_update_select_02.png)