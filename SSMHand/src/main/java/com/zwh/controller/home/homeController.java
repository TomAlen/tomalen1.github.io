package com.zwh.controller.home;

import com.github.pagehelper.PageHelper;
import com.zwh.pojo.Account;
import com.zwh.pojo.fore.RoomType;
import com.zwh.service.AccountService;
import com.zwh.service.LogService;
import com.zwh.service.fore.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 酒店管理系统前台首页
 */
@RequestMapping("/home")
@Controller
public class homeController {

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LogService logService;
    /**
     * 跳转到前台首页
     * @param model
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView toHomeIndex(ModelAndView model,
                                    @RequestParam(value = "name",required = false,defaultValue = "")String name
                                    ) {
        Map<String,Object> queryMap = new HashMap<>(0);
        queryMap.put("name",name);
        //将搜得的记录不显示分页
        PageHelper.startPage(0,999);
        List<RoomType> roomTypeList = roomTypeService.getRoomTypeList(queryMap);
        model.addObject("roomTypeList",roomTypeList);
        model.setViewName("home/index/index");
        return model;
    }

    /**
     * 跳转到登录页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView toLogin(ModelAndView model) {
        model.setViewName("home/index/login");
        return model;
    }

    /**
     * 跳转到注册页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/reg",method = RequestMethod.GET)
    public ModelAndView toRegister(ModelAndView model) {
        model.setViewName("home/index/reg");
        return model;
    }

    /**
     * 客户登录
     * @param account
     * @param request
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(Account account,String vcode, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(0);
        if(account == null) {
            result.put("success",false);
            result.put("msg","用户名和密码不能为空！");
            return result;
        }
        //从前端拿到会话值，判断验证码
        Object attribute = request.getSession().getAttribute("loginCpacha");
        if(attribute == null){
            result.put("type", "error");
            result.put("msg", "验证码过期，请刷新！");
            return result;
        }
        if(!vcode.toUpperCase().equals(attribute.toString().toUpperCase())) {
            result.put("success",false);
            result.put("msg","验证码错误！");
            logService.insertLog("客户" + account.getName() + " 登录前台时验证码错误!");
            return result;
        }
        //根据用户名来查找该客户，并进行与数据库进行比较
        Account accountByName = accountService.getAccountByName(account.getName());
        if(accountByName == null) {
            result.put("success",false);
            result.put("msg","用户不存在！");
            return result;
        }
        if(!account.getName().equals(accountByName.getName())) {
            result.put("success",false);
            result.put("msg","用户名不存在！");
            return result;
        }
        //判断密码是否相等
        if(!account.getPassword().equals(accountByName.getPassword())) {
            result.put("success",false);
            result.put("msg","密码错误！");
            return result;
        }
        if(accountByName.getStatus() == -1) {
            result.put("success",false);
            result.put("msg","该用户已被禁用！");
            return result;
        }
        //创建客户的会话，将验证码的会话清空
        request.getSession().setAttribute("account",accountByName);
        request.getSession().setAttribute("accountLoginCpacha",null);
        result.put("success",true);
        logService.insertLog("客户" + account.getName() + " 登录前台成功");
        return result;
    }


    /**
     * 客户注册
     * @param account
     * @return
     */
    @RequestMapping(value = "/reg",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> reg (Account account,HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(0);
        if(account == null) {
           result.put("success",false);
           result.put("msg","注册的信息不能为空");
           return result;
        }
        //判断用户名是否存在
        Account accountByName = accountService.getAccountByName(account.getName());
        if(accountByName != null) {
            result.put("success",false);
            result.put("msg","用户名已存在，注册失败");
            return result;
        }
        if(accountService.insertAccount(account) > 0) {
            result.put("success", false);
            result.put("msg", "注册失败");
            logService.insertLog("客户" + account.getName() + " 注册失败！");
            return result;
        }
        result.put("success",true);
        logService.insertLog("客户" +account.getName() + " 注册成功！");
        return result;
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logOut(HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("account");
        //添加日志
        logService.insertLog("客户" +account.getName() + " 退出登录！");
        //清空缓存,清空登录缓存信息
        request.getSession().invalidate();
        //重定向到登录页面
        return "redirect:login";
}


}
