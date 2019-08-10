package com.zwhzzz.Control;

import com.zwhzzz.page.page;
import com.zwhzzz.pojo.User;
import com.zwhzzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;


@RequestMapping("/admin")
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/goAdminListView",method = RequestMethod.GET)
    public ModelAndView goAdminListView(ModelAndView model) {
        model.setViewName("/user/user_list");
        return model;
    }

    /**
     * 添加用户
     */

    @RequestMapping(value = "/addAdmin",method = RequestMethod.POST)
    @ResponseBody
    public  java.util.Map<String,String> addUser(User user) {

        java.util.Map<String,String> result = new HashMap<String,String>(0);
        if(user == null) {
            result.put("type","error");
            result.put("message","无有效数据!");
            return result;
        }
       /* //判断用户名和密码是否为空
        if(StringUtil.isEmpty(user.getUsername())) {
            result.put("type","error");
            result.put("message","用户名不能为空！");
            return result;
        }
        if(StringUtil.isEmpty(user.getPassword())) {
            result.put("type", "error");
            result.put("message", "密码不能为空!");
            return result;
        }*/
        //从数据库中搜得管理员用户
        User existUser = userService.findUserByUserName(user.getUsername());
            if(existUser != null) {
                result.put("type", "error");
                result.put("message", "该用户名已经存在!");
            return result;
        }
        //如果返回的数据是0，就添加失败。
        if(userService.addUser(user) <= 0) {
            result.put("type", "error");
            result.put("message", "添加失败!");
            System.out.println(user.toString());
            return result;
        }
        result.put("type","success");
        result.put("message","添加成功!");
        System.out.println(user.toString());
        return result;
    }


    /**
     * 获取用户列表
     * @param username
     * @param page
     * @return
     */
    @RequestMapping(value = "/getAdminList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getUserList(
            @RequestParam(value = "username",required = false,defaultValue = "")String username,
            page page
    ) {
        Map<String,Object> result= new HashMap<>(0);
        Map<String,Object> querymap = new HashMap<>(0);
        //模糊查询，什么都不写查询全部
        querymap.put("username","%"+username+"%");
        querymap.put("offset",page.getOffset());
        querymap.put("pageSize",page.getRows());
        result.put("rows",userService.get_User_List(querymap));
        result.put("total",userService.getCount(querymap));
        return result;
    }

    /**
     *
     * 修改用户
     */

    @RequestMapping(value = "/editAdmin",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> updateUser(User user) {
        Map<String,String> result = new HashMap<>(0);
        //先查询出传入参数的名字，再判断名字和id是否存在
        User user1 = userService.findUserByUserName(user.getUsername());
        if(user1 != null) {
            if(user.getId() != user1.getId()){
                result.put("type", "error");
                result.put("message", "该用户名已经存在!");
                return result;
            }
        }
        if(userService.updateUser(user) <= 0){
            result.put("type","error");
            result.put("message","修改失败！");
            return result;
        }
        result.put("type","success");
        result.put("message","修改成功");
        return result;
    }

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteAdmin",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> deleteUser (
            //将前台的数据传入后端
            @RequestParam(value = "ids[]",required = true)Integer[] ids
    ) {
        Map<String,String> result = new HashMap<>(0);
        //将Integer数据类型转化为String类型。
        if(ids == null) {
            result.put("type","error");
            result.put("message","请选择要删除的数据！");
            return result;
        }
        String idsString = "";
        //对id进行foreach循环
        for (Integer id : ids) {
            ///将id添加进字符串中
            idsString += id + ",";
        }
        idsString = idsString.substring(0,idsString.length()-1);
        if(userService.delete(idsString) <= 0){
            result.put("type","error");
            result.put("message","删除失败");
            return result;
        }
        //result.put("ids",ids.toString());
        return result;
    }

}
