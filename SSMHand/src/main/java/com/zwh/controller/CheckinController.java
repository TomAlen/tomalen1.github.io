package com.zwh.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwh.pojo.Checkin;
import com.zwh.pojo.Role;
import com.zwh.pojo.Room;
import com.zwh.pojo.User;
import com.zwh.pojo.fore.BookOrder;
import com.zwh.pojo.fore.RoomType;
import com.zwh.service.CheckinService;
import com.zwh.service.LogService;
import com.zwh.service.RoomService;
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
import java.util.*;

@Controller
@RequestMapping("/admin/checkin")
public class CheckinController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private CheckinService checkinService;

    @Autowired
    private LogService logService;

    @Autowired
    private BookOrderService bookOrderService;


    /**
     * 跳转到入住管理页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView toCheckin(ModelAndView model){
        model.addObject("roomList",roomService.getList());
        model.addObject("roomTypeList",roomTypeService.getList());
        model.setViewName("checkin/list");
        return model;
    }


    /**
     * 获取入住管理的列表
     * @param page
     * @param rows
     * @param name
     * @param idCard
     * @param mobile
     * @param roomId
     * @param roomTypeId
     * @param status
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getCheckinList(Integer page, Integer rows,
                                             @RequestParam(value = "name",required = false,defaultValue = "")String name,
                                             @RequestParam(value = "idCard",required = false,defaultValue = "")String idCard,
                                             @RequestParam(value = "mobile",required = false,defaultValue = "")String mobile,
                                             @RequestParam(value = "roomId",required = false)Integer roomId,
                                             @RequestParam(value = "roomTypeId",required = false)Integer roomTypeId,
                                             @RequestParam(value = "status",required = false)Integer status) {
        Map<String,Object> result = new HashMap<>(0);
        Map<String,Object> queryMap = new HashMap<>(0);
        queryMap.put("name",name);
        queryMap.put("idCard",idCard);
        queryMap.put("mobile",mobile);
        queryMap.put("roomId",roomId);
        queryMap.put("roomTypeId",roomTypeId);
        queryMap.put("status",status);
        //分页
        PageHelper.startPage(page,rows);
        //将模糊查询的条件查出
        List<Checkin> checkinList = checkinService.getCheckinList(queryMap);
        //封装
        PageInfo<Checkin> pageInfo = new PageInfo<>(checkinList);
        result.put("rows",pageInfo.getList());
        result.put("total",pageInfo.getTotal());
        return result;
    }

    /**
     * 添加入住信息
     * 依次更改房型的预定订单数、已入住数，然后根据id查出订单，更改订单的状态，改为已入住
     * 最后更新房间状态信息
     * @param checkin
     * @param request
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> insertCheckin(Checkin checkin,
                                            @RequestParam(value = "bookOrderId",required = false)Integer bookOrderId,
                                            HttpServletRequest request) {
        Map<String,Object> result = new HashMap<>(0);
        Role role = (Role) request.getSession().getAttribute("role");
        User admin = (User) request.getSession().getAttribute("admin");
        if(checkin == null) {
            result.put("success",false);
            result.put("msg","入住信息不能为空！");
            return  result;
        }
        checkin.setCreateTime(new Date());
        if(checkinService.insertCheckin(checkin) > 0) {
            result.put("success",false);
            result.put("msg","添加入住信息失败！");
            logService.insertLog(role.getName() + admin.getUsername() + " 添加入住信息失败！");
            return result;
        }
        //添加成功后修改房型的预定数
        RoomType roomTypeServiceById = roomTypeService.findById(checkin.getRoomTypeId());
        //从预定单来的(入住管理既可以是直接入住也可以是预定来的)
        if(bookOrderId != null) {
            BookOrder bookOrderServiceById = bookOrderService.findById(bookOrderId);
            //设置为已入住
            bookOrderServiceById.setStatus(1);
            //更新订单
            bookOrderService.updateBookOrder(bookOrderServiceById);
            //roomTypeServiceById.setBookNum(roomTypeServiceById.getBookNum() - 1);//预定订单数-1
        }else {
            //表示不是预定订单
            roomTypeServiceById.setAvilableNum(roomTypeServiceById.getAvilableNum() - 1);

        }
        if(roomTypeServiceById != null) {
            roomTypeServiceById.setLivedNum(roomTypeServiceById.getLiveNum() + 1);//入住数+1
            roomTypeService.updateNum(roomTypeServiceById);//更新相关操作
        }
        //更改房间状态,设置为已入住
        Room roomById = roomService.getRoomById(checkin.getRoomId());
        if(roomById != null) {
            roomById.setStatus(1);
            roomService.updateRoom(roomById);
        }

        result.put("success",true);
        logService.insertLog(role.getName() + admin.getUsername() + " 添加入住信息成功！");
        return result;
    }

    /**
     * 修改入住管理
     * @param checkin
     * @param bookOrderId
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> updateCheckin(Checkin checkin,
                                            @RequestParam(value = "bookOrderId",required = false)Integer bookOrderId,
                                            HttpServletRequest request) {

        Map<String,Object> result = new HashMap<>(0);
        Role role = (Role) request.getSession().getAttribute("role");
        User admin = (User) request.getSession().getAttribute("admin");
        if(checkin == null) {
            result.put("success",false);
            result.put("msg","编辑信息不能为空！");
            return result;
        }
        Checkin Existcheckin = checkinService.findById(checkin.getId());
        if(Existcheckin == null) {
            result.put("success",false);
            result.put("msg","请选择正确的入住信息进行编辑！");
            return result;
        }
        if(checkinService.updateCheckin(checkin) > 0) {
            result.put("success",false);
            result.put("msg","更新信息失败！");
            logService.insertLog(role.getName() + admin.getUsername() + " 更新入住信息失败!");
            return result;
        }
        //思路：
        /*
        1、判断房型是否发生变化
        2、判断房间是否发生变化
        3、判断是否是从预定订单来的信息
         */
        //获取编辑前的房型和传入的房型
        RoomType oldRoomType = roomTypeService.findById(Existcheckin.getRoomTypeId());
        RoomType newRoomType = roomTypeService.findById(checkin.getRoomTypeId());
        //表示房型入住数不受预定订单的影响
        if(oldRoomType.getId().longValue() != newRoomType.getId().longValue()) {
            //表示房型发生了变化
            //编辑前的入住返回到以前，即-1
            oldRoomType.setLivedNum(oldRoomType.getLivedNum() - 1);
            //传入的就为新的入住，即+1
            newRoomType.setLivedNum(newRoomType.getLivedNum() + 1);
        }
        //就是表示预定数为0时，将可用房间数恢复
        if(bookOrderId == null) {
            oldRoomType.setAvilableNum(oldRoomType.getAvilableNum() + 1);
            newRoomType.setAvilableNum(newRoomType.getAvilableNum() - 1);
        }
        //首先判断是否是从预定订单来的信息
        /*if(bookOrderId == null) {
            //表示不是从预定订单来的，此时应该判断原入住信息是否为预定
            if(Existcheckin.getBookOrderId() == null) {
                //表示原来的信息不是来源于预定
                oldRoomType.setAvilableNum(oldRoomType.getAvilableNum() + 1);
                newRoomType.setAvilableNum(newRoomType.getAvilableNum() - 1);
            }else {
                //表示旧的信息来源于预定订单，而新的则不是来源于预定，需恢复到原来的状态
                BookOrder oldBookOrder = bookOrderService.findById(Existcheckin.getBookOrderId());
                //把订单恢复
                oldBookOrder.setStatus(0);
                //更新订单
                bookOrderService.updateBookOrder(oldBookOrder);
                //将房型也随之改变
//                oldRoomType.setLivedNum(oldRoomType.getLivedNum() - 1);
                //恢复订单，+1
                oldRoomType.setBookNum(oldRoomType.getBookNum() + 1);
//                newRoomType.setLivedNum(newRoomType.getLivedNum() + 1);
            }
        }
        //表示此时的订单来源于预定订单
        if(bookOrderId != null){

            if(Existcheckin.getBookOrderId() == null) {
                BookOrder oldBookOrder = bookOrderService.findById(Existcheckin.getBookOrderId());
                //如果新的订单不等于编辑前的订单，就把编辑前的订单的状态恢复。
                if(checkin.getBookOrderId() != oldBookOrder.getId()) {
                    oldBookOrder.setStatus(0);
                    bookOrderService.updateBookOrder(oldBookOrder);
                    //把新订单的订单数+1
                   // oldRoomType.setBookNum(oldRoomType.getBookNum() + 1);
                }
            }
            if(oldRoomType.getId().longValue() != newRoomType.getId().longValue()) {
                oldRoomType.setBookNum(oldRoomType.getBookNum() + 1);
                //恢复可用数
                if(Existcheckin.getBookOrderId() == null) {
                    oldRoomType.setAvilableNum(oldRoomType.getAvilableNum() + 1);
                }else{
                    newRoomType.setBookNum(newRoomType.getBookNum() - 1);
                }
            }
            BookOrder newBookOrder = bookOrderService.findById(bookOrderId);
            newBookOrder.setStatus(1);
            bookOrderService.updateBookOrder(newBookOrder);
        }*/
        roomTypeService.updateNum(oldRoomType);
        roomTypeService.updateNum(newRoomType);
        //对房间的改变
        if(checkin.getRoomId().longValue() != Existcheckin.getRoomId().longValue()) {
            Room oldRoom = roomService.getRoomById(Existcheckin.getRoomId());
            Room newRoom = roomService.getRoomById(checkin.getRoomId());
            //将原来的房间设置为可入住，将新的房间设置为已入住
            oldRoom.setStatus(0);
            newRoom.setStatus(1);
            roomService.updateRoom(oldRoom);
            roomService.updateRoom(newRoom);
        }
        result.put("success",true);
        logService.insertLog(role.getName() + admin.getUsername() + " 更新入住信息成功!");
        return result;
    }


    /**
     * 退房操作
     * @param checkId
     * @return
     */
    @RequestMapping(value = "/checkout",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> checkOut(Integer checkId,HttpServletRequest request) {
        Map<String,Object> result = new HashMap<>(0);
        Role role = (Role) request.getSession().getAttribute("role");
        User admin = (User) request.getSession().getAttribute("admin");
        if(checkId == null) {
            result.put("success",false);
            result.put("msg","数据不能为空！");
            return  result;
        }
        //将入住信息获取，将其状态改变
        Checkin checkinById = checkinService.findById(checkId);
        if(checkinById == null) {
            result.put("success",false);
            result.put("msg","请选择正确的数据！");
            return  result;
        }
        //设置为已结算
        checkinById.setStatus(1);
        //更新状态
        checkinService.updateCheckin(checkinById);
        //首先对房间进行操作
        Room roomById = roomService.getRoomById(checkinById.getRoomId());
        if(roomById != null) {
            //对其状态设置为打扫中
            roomById.setStatus(2);
            //更新状态
            roomService.updateRoom(roomById);
        }

        //再对房型进行操作
        RoomType roomType = roomTypeService.findById(checkinById.getRoomTypeId());
        if(roomType != null) {
            //可用数恢复
            roomType.setAvilableNum(roomType.getAvilableNum() + 1);
            //如果可用数大于房间数，就将可用数设置为房间数
            if(roomType.getAvilableNum() > roomType.getRoomNum()) {
                roomType.setAvilableNum(roomType.getRoomNum());
            }
            //如果有预定，恢复
            if(roomType.getBookNum() != null) {
                roomType.setBookNum(roomType.getBookNum() - 1);
            }
            //已入住数恢复
            roomType.setLivedNum(roomType.getLivedNum() - 1);
            //如果状态为已满，就释放为可预订可入住
            if(roomType.getStatus() == 0) {
                roomTypeService.updateStatus(1);
            }
            roomTypeService.updateNum(roomType);
        }

        //最后判断是否为预定，有订单信息
        if(checkinById.getBookOrderId() != null) {
            BookOrder bookOrder = bookOrderService.findById(checkinById.getBookOrderId());
            //如果有预定，表示是预定
            if (bookOrder != null) {
                //将状态设置为已结算离店
                bookOrder.setStatus(2);
                bookOrderService.updateBookOrder(bookOrder);
            }
        }

        result.put("success",true);
        logService.insertLog(role.getName() + admin.getUsername() + " 进行退房操作成功！");
        return result;
    }


    /**
     *根据房间类型获取房间号
     * @return
     */
    @RequestMapping(value = "/load_room_list",method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String,Object>> load_room_list(Integer roomTypeId) {
        List<Map<String,Object>> result = new ArrayList<>(0);
        Map<String,Object> queryMap = new HashMap<>(0);
        queryMap.put("roomTypeId",roomTypeId);
        queryMap.put("status",0);//必须为0，可入住才行
        //分页，表示可以无限(上限999条记录)查找
        PageHelper.startPage(0,999);
        List<Room> roomList = roomService.getRoomList(queryMap);
        //将查出的记录遍历
        for(Room room : roomList) {
            //将查出的房间名和与之对应的房型传给前端处理
            Map<String,Object> option = new HashMap<>(0);
            option.put("value",room.getId());
            option.put("text",room.getSn());
            result.add(option);
        }
        return result;
    }


}
