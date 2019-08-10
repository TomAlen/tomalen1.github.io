package com.zwhzzz.Control;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwhzzz.pojo.Teacher;
import com.zwhzzz.service.ClazzService;
import com.zwhzzz.service.TeacherService;
import com.zwhzzz.util.join;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    ClazzService clazzService;
    @Autowired
    TeacherService teacherService;

    Map<String,String> result = new HashMap<>(0);

    /**
     * 跳转到教师列表界面
     * @param model
     * @return
     */
    @RequestMapping(value = "/goTeacherListView",method = RequestMethod.GET)
    public ModelAndView goClazzListView(ModelAndView model) {
        model.setViewName("/teacher/teacherList");
        model.addObject("clazzList",clazzService.findAll());
        model.addObject("clazzListJson", JSONArray.fromObject(clazzService.findAll()));
        return model;
    }

    @RequestMapping("/getTeacherList")
    @ResponseBody
    public Map<String,Object> getListTeacher(@RequestParam(value = "username",defaultValue = "",required = true)String username,
                                             @RequestParam(value = "clazzId",required = false)Integer clazzId,
                                             Integer rows, Integer page,
                                             HttpServletRequest request) {
        Map<String,Object> rt = new HashMap<>(0);
        Map<String,Object> queryMap = new HashMap<>(0);
        //初始化模糊查询
        queryMap.put("username","%"+username+"%");
        Integer userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
        if (3 == userType) {
            //说明是老师
            Teacher LoginTeacher = (Teacher) request.getSession().getAttribute("user");
            queryMap.put("username","%" + LoginTeacher.getUsername() + "%");
        }
        if (clazzId != null) {
            queryMap.put("clazzId",clazzId);
        }
        //pageHelper插件
        PageHelper.startPage(page,rows);
        List<Teacher> teacher_list = teacherService.get_Teacher_List(queryMap);
        //封装教师信息
        PageInfo<Teacher> pageInfo = new PageInfo<>(teacher_list);
        Long total = pageInfo.getTotal();
        List<Teacher> list = pageInfo.getList();
        rt.put("total",total);
        rt.put("rows",list);
        return rt;
    }

    /**
     * 添加教师
     * @param teacher
     * @return
     */

    @RequestMapping(value = "/addTeacher",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> insertTeacher(Teacher teacher) {
        Map<String,String> rt = new HashMap<>(0);
        if(teacher == null) {
            rt.put("type","error");
            rt.put("message","添加的信息为空！");
            return rt;
        }
        if(teacher.getClazzId() == null) {
            rt.put("type","error");
            rt.put("message","所选的班级为空！");
            return rt;
        }
        teacher.setTno(join.generateTn("T","0"));
        Teacher teacherByName = teacherService.findTeacherByName(teacher.getUsername());
        if(teacherByName != null) {
            rt.put("type","error");
            rt.put("message","用户名已存在，添加失败");
            return rt;
        }
        if(teacherService.insertTeacher(teacher) <= 0) {
            rt.put("type","error");
            rt.put("message","添加失败！");
            return rt;
        }
        rt.put("type","success");
        rt.put("message","添加成功！");
        return rt;
    }

    /**
     * 更新教师
     * @param teacher
     * @return
     */
    @RequestMapping(value = "/editTeacher",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> updateTeacher (Teacher teacher) {
        if(teacher == null) {
            result.put("type","error");
            result.put("message","更新的信息为空！");
            return result;
        }
        Teacher teacherByName = teacherService.findTeacherByName(teacher.getUsername());
        if(teacherByName != null) {
            if(teacher.getId() == teacherByName.getId()) {
                result.put("type","error");
                result.put("message","用户名已存在，修改失败");
                return result;
            }
        }
        if(teacherService.updateTeacher(teacher) <= 0) {
            result.put("type","error");
            result.put("message","更新失败！");
            return result;
        }
        result.put("type","success");
        result.put("message","更新成功！");
        return result;
    }

    /**
     * 批量删除教师
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteTeacher",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> deleteTeacher(@RequestParam(value = "ids[]",required = true)Long[] ids) {
        if(ids == null) {
            result.put("type","error");
            result.put("message","删除的信息为空！");
            return result;
        }
        if(teacherService.deleteTeacher(join.joinString(Arrays.asList(ids),",")) <= 0) {
            result.put("type","error");
            result.put("message","删除失败！");
            return result;
        }
        result.put("type","success");
        result.put("message","删除成功");
        return result;
    }
}
