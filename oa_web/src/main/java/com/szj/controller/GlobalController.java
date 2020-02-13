package com.szj.controller;


import com.szj.entity.Employee;
import com.szj.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.dc.pr.PRError;

import javax.servlet.http.HttpSession;

/*
* 登录  个人中心
* */

@Controller("globalController")
public class GlobalController {
    @Autowired
    private GlobalService globalService;
    /*去登陆*/
    @RequestMapping("/to_login")
    public String to_login(){
        return "login";
    }
    /*登录成功
    * 登录成功要保存用户信息，要存在Session中
    * Session如何获取，只需要在参数里面直接声明即可，Spring会自动的将session注入进来，直接用即可
    * */
    @RequestMapping("/login")
    public String Login(HttpSession session, @RequestParam String sn, @RequestParam String password){
        //两个参数都是用来接收用户提交的信息的，则必须用 RequestParam 来标注
        /*
        * 判断是否登录成功
        * */
        Employee employee = globalService.login(sn,password);
        if (employee == null) {
            // 登陆失败 重新去登录
            return "redirect:to_login";
        }
        // 将登录成功对象信息放到 session 中
        session.setAttribute("employee",employee);
        return "redirect:self";
    }
    /*个人中心*/
    @RequestMapping("/self")
    public String self(){
        return "self";
    }

    // 退出 需要session信息  直接把session信息清空即可
    @RequestMapping("/quit")
    public String quit(HttpSession session){
        session.setAttribute("employee",null);
        return "redirect:to_login";
    }

    /*修改密码*/
    @RequestMapping("/to_change_password")
    public String toChangePassword(){
        return "change_password";
    }

    @RequestMapping("/change_password")
    public String changePassword(HttpSession session, @RequestParam String old, @RequestParam String new1 ,@RequestParam String new2){
        // 修改密码jsp页面中 input中的name属性 old new1 new2信息
        Employee employee = (Employee)session.getAttribute("employee");
        //当前登录的用户密码是不是jsp页面输入的旧密码
        if(employee.getPassword().equals(old)){
            if(new1.equals(new2)){
                employee.setPassword(new1);
                globalService.changePassword(employee);
                return "redirect:self";
            }
        }
        return "redirect:to_change_password";
    }


}
