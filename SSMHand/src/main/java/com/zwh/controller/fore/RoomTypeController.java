package com.zwh.controller.fore;

import com.zwh.page.Page;
import com.zwh.pojo.Role;
import com.zwh.pojo.User;
import com.zwh.pojo.fore.RoomType;
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
import java.util.Map;

@Controller
@RequestMapping("/admin/RoomType")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private LogService logService;

    /**
     * 转到房型页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView getRoomTypeList(ModelAndView model) {
        model.setViewName("room_type/list");
        return model;
    }

    /**
     * 获取房型列表
     * @param page
     * @param name
     * @param status
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(Page page,
                                      @RequestParam(value = "name",required = false,defaultValue = "")String name,
                                      @RequestParam(value = "status",required = false)Integer status) {
        Map<String,Object> result = new HashMap<>(0);
        Map<String,Object> queryMap = new HashMap<>(0);
        queryMap.put("name",name);
        queryMap.put("status",status);
        //分页
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        result.put("rows",roomTypeService.getRoomTypeList(queryMap));
        result.put("total",roomTypeService.getCount(queryMap));
        return result;
    }


    /**
     * 添加房型
     * @param roomType
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> insertRoomType(RoomType roomType,HttpServletRequest request) {
        Map<String,String> result = new HashMap<>(0);
        User admin = (User)request.getSession().getAttribute("admin");
        Role role = (Role)request.getSession().getAttribute("role");
        if(roomType == null) {
            result.put("type","error");
            result.put("msg","添加的信息为空！");
            return result;
        }
        RoomType roomTypeByName = roomTypeService.findRoomTypeByName(roomType.getName());
        if(roomTypeByName != null) {
            result.put("type","error");
            result.put("msg","该房型已存在！");
            return result;
        }
        roomType.setAvilableNum(roomType.getRoomNum());//初始为可预订房间为房间数
        //默认已预订数和已入住数为0
        roomType.setBookNum(0);
        roomType.setLivedNum(0);
        if(roomTypeService.insertRoomType(roomType) > 0) {
            result.put("type","error");
            result.put("msg","添加失败！");
            logService.insertLog(role.getName() + admin.getUsername() + " 添加房型信息失败！");
            return result;
        }
        result.put("type","success");
        logService.insertLog(role.getName() + admin.getUsername() + " 添加房型信息成功！");
        return result;
    }


    /**
     * 修改房型
     * @param roomType
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> updateRoomType(RoomType roomType,HttpServletRequest request) {
        Map<String,Object> result = new HashMap<>(0);
        User admin = (User)request.getSession().getAttribute("admin");
        Role role = (Role)request.getSession().getAttribute("role");
        if(roomType == null) {
            result.put("type","error");
            result.put("msg","信息不能为空");
            return result;
        }
        //修改房间数，相应的可预订数页随之改变
        //根据id找到房型
        RoomType roomTypeById = roomTypeService.findById(roomType.getId());
        if(roomTypeById == null) {
            result.put("type","error");
            result.put("msg","改房型不存在！");
            return result;
        }
        //先算出修改后与修改前的差值，然后计算出可预定数。
        int offset = roomType.getRoomNum() - roomTypeById.getRoomNum();
        //用修改前的可预定数加上偏移量(差值)
        roomType.setAvilableNum(roomTypeById.getAvilableNum() + offset);
        roomType.setStatus(1);
        System.out.println(offset+"->"+roomType.getAvilableNum());
        //如果得出的可预定数小于0，那么就设置为0，状态也设置为已满
        if(roomType.getAvilableNum() <= 0) {
            roomType.setAvilableNum(0);
            roomType.setStatus(0);
            if(roomType.getAvilableNum() + roomTypeById.getBookNum() + roomTypeById.getLivedNum() > roomType.getRoomNum()) {
                result.put("type","error");
                result.put("msg","房间数设置不合理！");
                return result;
            }
        }
        if(roomTypeService.updateRoomType(roomType) > 0) {
            result.put("type","error");
            result.put("msg","修改失败！");
            logService.insertLog(role.getName() + admin.getUsername() + " 修改房型信息失败！");
            return result;
        }
        result.put("type","success");
        logService.insertLog(role.getName() + admin.getUsername() + " 修改房型信息成功！");
        return result;
    }

    /**
     * 删除房型信息
     * @param id
     * @param request
     * @return
     */

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteRoomType(Integer id,HttpServletRequest request) {
        User admin = (User)request.getSession().getAttribute("admin");
        Role role = (Role)request.getSession().getAttribute("role");
        Map<String,Object> result = new HashMap<>(0);
        if(id == null) {
            result.put("success",false);
            result.put("msg","请选择一条数据删除！");
            return result;
        }
        try{
            if(roomTypeService.deleteRoomType(id) > 0) {
                result.put("type","error");
                result.put("msg","删除失败");
                logService.insertLog(role.getName() + admin.getUsername() + " 删除房型失败！");
                return  result;
            }
        }catch (Exception e){
            result.put("type","error");
            result.put("msg","该房型下的存在房间信息，请先删除该楼层下的房间信息！");
            e.printStackTrace();
            return result;
        }
        result.put("type","success");
        logService.insertLog(role.getName() + admin.getUsername() + " 删除房型信息成功！");
        return result;
    }


}
