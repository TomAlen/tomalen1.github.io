package com.zwh.controller.fore;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwh.pojo.Role;
import com.zwh.pojo.User;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/book_order")
public class BookOrderController {

    @Autowired
    private BookOrderService bookOrderService;

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LogService logService;

    /**
     * 跳转到预定订单管理页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView toBookOrder(ModelAndView model) {
        model.addObject("roomTypeList",roomTypeService.getList());
        model.addObject("accountList",accountService.getList());
        model.setViewName("book_order/list");
        return model;
    }


    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getBookOrderList(Integer page, Integer rows,
                                               @RequestParam(value = "name",required = false,defaultValue = "")String name,
                                               @RequestParam(value = "idCard",required = false,defaultValue = "")String idCard,
                                               @RequestParam(value = "mobile",required = false,defaultValue = "")String mobile,
                                               @RequestParam(value = "accountId",required = false)Integer accountId,
                                               @RequestParam(value = "roomTypeId",required = false)Integer roomTypeId,
                                               @RequestParam(value = "status",required = false)Integer status) {
        Map<String,Object> result = new HashMap<>(0);
        Map<String,Object> queryMap = new HashMap<>(0);
        queryMap.put("name",name);
        queryMap.put("idCard",idCard);
        queryMap.put("mobile",mobile);
        queryMap.put("accountId",accountId);
        queryMap.put("roomTypeId",roomTypeId);
        queryMap.put("status",status);
        //分页
        PageHelper.startPage(page,rows);
        //将模糊查询的条件查出
        List<BookOrder> bookOrderList = bookOrderService.getBookOrderList(queryMap);
        //封装
        PageInfo<BookOrder> pageInfo = new PageInfo<>(bookOrderList);
        result.put("rows",pageInfo.getList());
        result.put("total",pageInfo.getTotal());
        return result;
    }

    /**
     * 添加预定订单
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> insertBookOrder(BookOrder bookOrder, HttpServletRequest request) {
        Map<String,Object> result = new HashMap<>(0);
        Role role = (Role) request.getSession().getAttribute("role");
        User admin = (User)request.getSession().getAttribute("admin");
        if(bookOrder == null) {
            result.put("success",false);
            result.put("msg","添加的信息不能为空！");
            return result;
        }
        //设置当前日期，添加进去
        bookOrder.setCreateTime(new Date());
        if (bookOrderService.insertBookOrder(bookOrder) > 0) {
            result.put("success",false);
            result.put("msg","添加失败");
            logService.insertLog(role.getName() + admin.getUsername() + " 添加预定订单失败");
            return result;
        }
        //订单成功后去修改房型的可预定数、已预定数、入住数等信息
        RoomType roomType = roomTypeService.findById(bookOrder.getRoomTypeId());
        if(roomType != null) {
            //可预定数、已预定数相继 - 1 ，+ 1
            roomType.setAvilableNum(roomType.getAvilableNum() - 1);
            roomType.setBookNum(roomType.getBookNum() + 1);
            roomTypeService.updateNum(roomType);
            //判断如果可预定数为0，就将状态设置为已满
            if(roomType.getAvilableNum() == 0 ) {
                roomType.setStatus(0);
                roomTypeService.updateRoomType(roomType);
            }
        }
        result.put("success",true);
        logService.insertLog(role.getName() + admin.getUsername() + " 添加预定订单成功！");
        return result;
    }


    /**
     *
     * 编辑订单信息
     * @param bookOrder
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> updateBookOrder(BookOrder bookOrder,HttpServletRequest request) {
        Map<String,Object> result = new HashMap<>(0);
        Role role = (Role) request.getSession().getAttribute("role");
        User admin = (User) request.getSession().getAttribute("admin");
        if(bookOrder == null) {
            result.put("success",false);
            result.put("msg","更新的数据不能为空！");
            return result;
        }
        //先根据id找出找出原预定订单信息
        BookOrder ExistBook = bookOrderService.findById(bookOrder.getId());
        if(ExistBook  == null) {
            result.put("success",false);
            result.put("msg","请选择正确的数据进行编辑！");
            return result;
        }
        if(bookOrderService.updateBookOrder(bookOrder) > 0) {
            result.put("success",false);
            result.put("msg","更新失败！");
            logService.insertLog(role.getName() + admin.getUsername() + " 更新预定订单信息失败！");
            return result;
        }
        //思路：
        //先将原房型查出，再对该可预定数、已预定数进行修改
        //再将新房型查出，一样对可预定数和已预订数进行修改

        //如果id不同，表示id不重复，可用
        if(ExistBook.getId().longValue() != bookOrder.getId().longValue()) {
            //原预定订单
            RoomType oldRoomType = roomTypeService.findById(ExistBook.getRoomTypeId());
            //可预定数 +1
            oldRoomType.setAvilableNum(oldRoomType.getAvilableNum() + 1);
            //已预订数 -1
            oldRoomType.setBookNum(oldRoomType.getBookNum() - 1);
            //更新
            roomTypeService.updateNum(oldRoomType);
            //如果原先的是满房，恢复后则为不满
            if(oldRoomType.getAvilableNum() > 0) {
                roomTypeService.updateStatus(1);
            }

            //新预定订单
            RoomType newRoomType = roomTypeService.findById(bookOrder.getRoomTypeId());
            //可预定数+1
            newRoomType.setAvilableNum(newRoomType.getAvilableNum() - 1);
            //已预订数 +1
            newRoomType.setBookNum(newRoomType.getBookNum() + 1);
            roomTypeService.updateNum(newRoomType);
            //如果可预定数为0，那么就将状态设置为已住满
            if(newRoomType.getAvilableNum() == 0) {
                //将状态设置为已满
                roomTypeService.updateStatus(0);
            }
        }
        result.put("success",true);
        logService.insertLog(role.getName() + admin.getUsername() + " 更新预定订单信息成功！");
        return result;
    }


}
