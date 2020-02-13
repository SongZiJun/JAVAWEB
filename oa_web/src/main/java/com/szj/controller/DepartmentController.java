package com.szj.controller;


import com.szj.entity.Department;
import com.szj.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller("departmentController")
/*在这个类中写的所有方法都是一个个部门管理的控制器
* 把他们规定到一个统一的路径
* */
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /*设置当前列表的控制器路径：department/list */
    @RequestMapping("/list")
    /*向页面传递所有信息，使用Map类型，
    因为是java通用的类型，而不是sprngmvc特有的，
    存在和框架解耦的优点
    */
    public String list(Map<String,Object> map){
        /*传递所有部门信息*/
        map.put("list",departmentService.getAll());
        /*转发到jsp页面上，配置视图转换器时已经配置了前缀和后缀
        * 所以只用写jsp名字就可以
        * */
        return "department_list";
    }
    /*打开添加页面*/
    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        map.put("department",new Department());
        return "department_add";
    }
    /*w
    完成添加
    * */
    @RequestMapping("/add")
    public String add(Department department){
        departmentService.add(department);
        return "redirect:list"; /*重定向到list控制器*/
    }
    /*
    * 打开修改页面
    * */

    /*必须以sn为键传递这个参数,因为修改需要知道它的编号*/
    @RequestMapping(value = "/to_update",params = "sn")
    public String to_Update (Map<String,Object>map,String sn){
        /*通过接受过来的sn 来访问我得到的depatrment对象*/
        map.put("department",departmentService.get(sn));
        return "department_update";
    }
    /*完成修改*/
    @RequestMapping("/update")
    public String update(Department department){
        departmentService.edit(department);
        return "redirect:list";
    }

    /*删除信息*/
    @RequestMapping("/delete")
    public String delete(String sn){
        departmentService.remove(sn);
        return "redirect:list";
    }


}
