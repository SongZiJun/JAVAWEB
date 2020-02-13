package com.szj.global;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*配置登陆拦截器
* SpringMVC 可以通过登录拦截器处理 （HandlerInerceptor）
* */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        /*
        * 获取当前请求路径，查看有路径没有login的关键字，有可以直接放行。（将url转为小写）
        * */
        String url = httpServletRequest.getRequestURI();
        if( url.toLowerCase().indexOf("login") >= 0 ){
            return  true;
        }
        /*判断当前session有没有用户信息*/
        HttpSession session = httpServletRequest.getSession();
        //从session中获取登录成功储存的employee对象信息 有的话代表已经登录 可以放行
        if(session.getAttribute("employee")!=null){
            return true;
        }
        //没有重定向到去登陆
        httpServletResponse.sendRedirect("/to_login");
        return false;
        /*定义好拦截器  要去spring配置文件中配置拦截器*/
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
