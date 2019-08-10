package com.zwh.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwh.page.Page;
import com.zwh.pojo.Role;
import com.zwh.pojo.Room;
import com.zwh.pojo.User;
import com.zwh.service.FloorService;
import com.zwh.service.LogService;
import com.zwh.service.RoomService;
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

@Controller
@RequestMapping("/admin/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private LogService logService;

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private FloorService floorService;

    /**
     * 转向房间页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView toRoomIndex(ModelAndView model,HttpServletRequest request) {
        //将楼层信息和房型信息存入model中，交给前端处理
        model.addObject("roomTypeList",roomTypeService.getList());
        model.addObject("floorList",floorService.getListAll());
        model.setViewName("room/list");
        return model;
    }

    /**
     * 得到房间列表
     * @param page
     * @param rows
     * @param sn
     * @param roomTypeId
     * @param floorId
     * @param status
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getRoomList(Integer page, Integer rows,
                                          @RequestParam(value = "sn",required = false,defaultValue = "")String sn,
                                          @RequestParam(value = "roomTypeId",required = false)Integer roomTypeId,
                                          @RequestParam(value = "floorId",required = false)Integer floorId,
                                          @RequestParam(value = "status",required = false)Integer status
                                          ) {
        Map<String,Object> result = new HashMap<>(0);
        Map<String,Object> queryMap = new HashMap<>(0);
        queryMap.put("sn",sn);
        queryMap.put("roomTypeId",roomTypeId);
        queryMap.put("floorId",floorId);
        queryMap.put("status",status);
        //设置分页的起始页
        PageHelper.startPage(page,rows);
        List<Room> roomList = roomService.getRoomList(queryMap);
        //封装信息
        PageInfo<Room> pageInfo = new PageInfo<>(roomList);
        result.put("rows",pageInfo.getList());
        result.put("total",pageInfo.getTotal());
        return result;
    }

    /**
     * 添加房间信息
     * @param room
     * @param request
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> insertRoom(Room room, HttpServletRequest request) {
        Map<String,Object> result = new HashMap<>(0);
        Role role = (Role) request.getSession().getAttribute("role");
        User admin = (User) request.getSession().getAttribute("admin");

        if(room == null) {
            result.put("type","error");
            result.put("msg","房间信息不能为空！");
            return result;
        }
        if(isExist(room.getSn(),room.getId())) {
            result.put("type","error");
            result.put("msg","该用户已存在！");
            return result;
        }
        if(roomService.insertRoom(room) > 0){
            result.put("type","error");
            result.put("msg","添加房间失败！");
            logService.insertLog(role.getName() + admin.getUsername() + " 添加房间信息失败！");
            return result;
        }
        result.put("type","success");
        result.put("msg","添加房间信息成功！");
        return result;
    }

    /**
     * 更新房间信息
     * @param room
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> updateRoom(Room room,HttpServletRequest request) {
        Map<String,Object> result = new HashMap<>(0);
        Role role = (Role) request.getSession().getAttribute("role");
        User admin = (User) request.getSession().getAttribute("admin");
        if(room == null) {
            result.put("type","error");
            result.put("msg","编辑信息不能为空！");
            return result;
        }
        if(isExist(room.getSn(),room.getId())) {
            result.put("type","error");
            result.put("msg","该用户已存在！");
            return result;
        }
        if(roomService.updateRoom(room) > 0) {
            result.put("type","error");
            result.put("msg","更新房间失败！");
            logService.insertLog(role.getName() + admin.getUsername() + " 更新房间信息失败！");
            return result;
        }
        result.put("type","success");
        logService.insertLog(role.getName() + admin.getUsername() + " 更新房间信息成功！");
        return result;
    }

    /**
     * 删除房间信息
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteRoom(Integer id,HttpServletRequest request) {
        Map<String,Object> result = new HashMap<>(0);
        Role role = (Role) request.getSession().getAttribute("role");
        User admin = (User) request.getSession().getAttribute("admin");
        if(id == null) {
            result.put("type","error");
            result.put("msg","请选择要删除的信息");
            return result;
        }
        try {
            if(roomService.deleteRoom(id) > 0) {
                result.put("type","error");
                result.put("msg","删除失败!");
                logService.insertLog(role.getName() + admin.getUsername() + " 删除房间信息失败！");
                return result;
            }
        } catch (Exception e) {
            result.put("type","error");
            result.put("msg","该房型下的存在房间信息，请先删除该楼层下的房间信息！");
            return result;
        }
        result.put("type","success");
        logService.insertLog(role.getName() + admin.getUsername() + " 删除房间信息成功！");
        return result;
    }


    //依据编号和id进行查找是否相同
    public boolean isExist(String sn,Integer id) {
        Room roomBySn = roomService.getRoomBySn(sn);
        if(roomBySn == null) return false;
        //如果查的id与传入的id相同，就表示修改的是本身
        if (roomBySn.getId() == id) return false;
        return true;
    }

}
