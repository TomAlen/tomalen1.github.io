package com.zwhzzz.interceptor.admin;

import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {

    //本方法在控制器前执行，它的返回值表示是否中断运行
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url = httpServletRequest.getRequestURI();
        System.out.println("进入拦截器,url:"+url);
        Object user = httpServletRequest.getSession().getAttribute("user");
        if(user == null) {
            //表示未登录或者登陆是失效
            System.out.println("未登录或者登陆是失效，URL:"+url);
            //判断请求的方式是否为ajax请求
            if("XMLHttpRequest".equals(httpServletResponse.getHeader("X-Requested-With"))) {
                //ajax请求
                Map<String,String> result = new HashMap<>(0);
                result.put("type","error");
                result.put("message","登录状态已失效，请重新登录！");
                httpServletResponse.getWriter().write(JSONObject.fromObject(result).toString());
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/system/UserLogin");
                return false;
            }
            //转向到登录页面
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/system/UserLogin");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
