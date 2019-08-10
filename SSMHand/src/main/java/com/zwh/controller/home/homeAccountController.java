package com.zwh.controller.home;

import com.github.pagehelper.PageHelper;
import com.zwh.pojo.Account;
import com.zwh.pojo.fore.BookOrder;
import com.zwh.pojo.fore.RoomType;
import com.zwh.service.AccountService;
import com.zwh.service.LogService;
import com.zwh.service.fore.BookOrderService;
import com.zwh.service.fore.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 前台用户中心控制器
 */
@Controller
@RequestMapping("/home/account")
public class homeAccountController {

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BookOrderService bookOrderService;

    @Autowired
    private LogService logService;

    /**
     * 前台用户中心首页
     * @param model
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView toIndex(ModelAndView model,HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("account");
        Map<String,Object> queryMap = new HashMap<>(0);
        //根据客户id查询订单信息
        queryMap.put("accountId",account.getId());
        //分页
        PageHelper.startPage(0,999);

        model.addObject("bookOrderList",bookOrderService.getBookOrderList(queryMap));
        model.addObject("roomTypeList",roomTypeService.getList());
        model.setViewName("home/account/index");
        return model;
    }

    /**
     * 预订房间页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/book_order",method = RequestMethod.GET)
    public ModelAndView BookOrderRoom(ModelAndView model,Integer roomTypeId) {
        //通过roomTypeId查找出roomType
        RoomType roomTypeById = roomTypeService.findById(roomTypeId);
        model.addObject("roomType",roomTypeById);
        model.setViewName("home/account/book_order");
        return model;
    }

    /**
     * 预定信息提交
     * @param bookOrder
     * @param request
     * @return
     */
    @RequestMapping(value = "/book_order",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getBook_order (BookOrder bookOrder,HttpServletRequest request) {
        Map<String,Object> result = new HashMap<>(0);
        if(bookOrder == null) {
            result.put("success",false);
            result.put("msg","预定信息不能为空");
            return result;
        }
        //从会话中取出客户的信息
        Account account = (Account) request.getSession().getAttribute("account");
        if(account == null){
            result.put("type", "error");
            result.put("msg", "客户不能为空!");
            return result;
        }
        //将登录客户id设为预定订单中的id
        bookOrder.setAccountId(account.getId());
        //设置时间
        bookOrder.setCreateTime(new Date());
        //设置状态为预定中
        bookOrder.setStatus(0);
        if(bookOrderService.insertBookOrder(bookOrder) > 0) {
            result.put("success",false);
            result.put("msg","添加预定订单失败！");
            logService.insertLog("客户" + account.getName() + " 添加预定订单失败！");
            return result;
        }
        //添加成功后，房型的可用房间数、预定数、状态数改变
        //根据roomTypeId获取房间数
        RoomType roomType = roomTypeService.findById(bookOrder.getRoomTypeId());
        if(roomType != null) {
            roomType.setAvilableNum(roomType.getAvilableNum() - 1);
            roomType.setBookNum(roomType.getBookNum() + 1);
            //如果可用房间数小于0，状态设为已满
            if(roomType.getAvilableNum() <= 0) {
                roomTypeService.updateStatus(0);
            }
            //更新相应的数
            roomTypeService.updateNum(roomType);
        }
        result.put("success",true);
        logService.insertLog("客户" + account.getName() + " 添加预定信息成功！");
        return result;
    }


    /**
     * 个人用户信息更新
     * @param account
     * @param request
     * @return
     */
    @RequestMapping(value = "/update_info",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> update_info(Account account,HttpServletRequest request) {
        Map<String,Object> result = new HashMap<>(0);
        if(account == null) {
            result.put("success",false);
            result.put("msg","用户信息不能为空");
            return result;
        }
        //从session中得到登录时的客户
        Account accountBySession = (Account) request.getSession().getAttribute("account");
        //判断登录时的用户名与更新后的是否存在
        if(isExist(account.getName(),accountBySession.getId())) {
            result.put("success",false);
            result.put("msg","用户名已存在");
            return result;
        }
        //对的登录时的客户更新
        accountBySession.setName(account.getName());
        accountBySession.setRealname(account.getRealname());
        accountBySession.setIdCard(account.getIdCard());
        accountBySession.setMobile(account.getMobile());
        accountBySession.setAddress(account.getAddress());
        if(accountService.updateAccount(accountBySession) > 0) {
            result.put("success",false);
            result.put("msg","更新用户信息失败！");
            logService.insertLog("客户" + account.getName() + " 更新用户信息失败！");
            return  result;
        }
        result.put("success",true);
        result.put("msg","更新成功！");
        logService.insertLog("客户" + account.getName() + " 更新用户信息成功！");
        return result;
    }

    /**
     * 修改个人信息密码
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "/update_pwd",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> update_pwd(String oldPassword,String newPassword,HttpServletRequest request) {
        Map<String,Object> result = new HashMap<>(0);
        //判空操作交给前端处理
        //获取登录的原密码
        Account account = (Account) request.getSession().getAttribute("account");
        if(! oldPassword.equals(account.getPassword())) {
            result.put("success",false);
            result.put("msg","原密码错误");
            return result;
        }
        //设置为新密码
        account.setPassword(newPassword);
        if(accountService.updateAccount(account) > 0 ) {
            result.put("success",false);
            result.put("msg","密码更新失败！");
            logService.insertLog("客户" + account.getName() + " 更新密码失败!");
            return  result;
        }
        result.put("success",true);
        result.put("msg","密码更新成功！");
        logService.insertLog("客户" + account.getName() + " 更新密码成功!");
        return result;
    }

    /**
     * 判断用户名是否存在
     * @param name
     * @param id
     * @return
     */
    public boolean isExist(String name,Integer id) {
        Account accountByName = accountService.getAccountByName(name);
        if(accountByName == null)return false;
        //编辑时表明是它本身,
        if(accountByName != null && accountByName.getId().longValue() == id.longValue()) return false;
        return true;
    }




}
