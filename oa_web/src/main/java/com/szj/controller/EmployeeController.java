package com.szj.controller;


import com.szj.entity.Department;
import com.szj.entity.Employee;
import com.szj.global.Contant;
import com.szj.service.DepartmentService;
import com.szj.service.EmployeeService;
import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller("employeeController")
@RequestMapping("/employee")
/*在这个类中写的所有方法都是一个个员工管理的控制器
 * 把他们规定到一个统一的路径
 * */
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;


    @RequestMapping("/list")
    public String list(Map<String,Object> map){
        map.put("list",employeeService.getAll());
        return "employee_list";
    }

    @RequestMapping("/to_add")
    public String toAdd(Map<String, Object>map){
        /*new Employee对象 放到作用域中*/
        map.put("employee",new Employee());
        /*需要把所有部门传递到添加页面上*/
        map.put("dlist",departmentService.getAll());
        map.put("plist", Contant.getPosts());   /*插入职务信息*/
        return "employee_add";
    }
    @RequestMapping("/add")
    public String add(Employee employee){
        employeeService.add(employee);
        return "redirect:list";
    }

    @RequestMapping("/to_update")
    public String to_update(String sn, Map <String,Object> map){
        map.put("employee",employeeService.get(sn));
        /*修改也需要添加部门信息职务信息*/
        map.put("dlist",departmentService.getAll());    /*根据编号获取当前员工的信息*/
        map.put("plist", Contant.getPosts());
        return "employee_update";
    }
    @RequestMapping("/update")
    public String update(Employee employee){
        employeeService.edit(employee);
        return "redirect:list";
    }

    @RequestMapping(value ="/delete",params = "sn")
    public String remove(String sn){
        employeeService.remove(sn);
        return "redirect:list";
    }



}
