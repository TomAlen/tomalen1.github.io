package com.zwhzzz.Control;

import com.github.pagehelper.util.StringUtil;
import com.zwhzzz.page.page;
import com.zwhzzz.pojo.clazz;
import com.zwhzzz.service.ClazzService;
import com.zwhzzz.service.GradeService;
import com.zwhzzz.util.join;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/clazz")
public class ClazzController {

    @Autowired
    GradeService gradeService;
    @Autowired
    ClazzService clazzService;


    /**
     * 班级列表页
     * @param model
     * @return
     */
    @RequestMapping(value = "/goClazzListView",method = RequestMethod.GET)
    public ModelAndView goClazzListView(ModelAndView model) {
        model.setViewName("/clazz/clazzList");
        model.addObject("gradeList",gradeService.findAll());
        //将查询到的数据全转化为json格式
        model.addObject("gradeListJson", JSONArray.fromObject(gradeService.findAll()));
        return model;
    }


    /**
     * 得到班级列表
     * @param name
     * @param page
     * @return
     */
    @RequestMapping("/getClazzList")
    @ResponseBody
    public Map<String,Object> getClazzList(@RequestParam(value = "name",required = false,defaultValue = "")String name,
                                           @RequestParam(value = "gradeId",required = false)Integer gradeId,
                                           page page) {
        Map<String,Object> result = new HashMap<>(0);
        Map<String,Object> querymap = new HashMap<>(0);
        //模糊查询
        querymap.put("name","%"+name+"%");
        if(gradeId != null) {
            querymap.put("gradeId",gradeId);
        }
        //分页
        querymap.put("offset",page.getOffset());//表示从第几页开始
        querymap.put("pageSize",page.getRows());//得到一行的数据
        //将查出的数据和分页信息封装传入前端
        result.put("rows",clazzService.get_Clazz_List(querymap));
        result.put("total",clazzService.getCount(querymap));
        return result;
    }

    /**
     * 添加班级信息
     * @param clazz
     * @return
     */
    @RequestMapping(value = "/addClazz",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> insertClazz (clazz clazz) {
        Map<String,String> result = new HashMap<>(0);
        if(StringUtil.isEmpty(clazz.getName())) {
            result.put("type","error");
            result.put("message","班级名称不能为空");
            return result;
        }
        if(clazz.getGradeId() == null) {
            result.put("type","error");
            result.put("message","请选择所属年级");
            return result;
        }
        if(clazzService.insertClazz(clazz) <= 0) {
            result.put("type","error");
            result.put("message","添加失败！");
            return result;
        }
        result.put("type","success");
        result.put("message","添加班级成功");
        return result;
    }

    /**
     *修改班级信息
     *
     */

    @RequestMapping(value = "/editClazz",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> updateClazz(clazz clazz) {
        Map<String,String> result = new HashMap<>(0);
        if(clazz == null) {
            result.put("type","error");
            result.put("message","更新失败！");
            return result;
        }
        clazz clazz1 = clazzService.findClazzByName(clazz.getName());
        if(clazz1 != null) {
            result.put("type","error");
            result.put("message","改用户已存在！");
            return result;
        }
        if(clazzService.updateClazz(clazz) <= 0) {
            result.put("type","error");
            result.put("message","更新失败！");
            return result;
        }
        result.put("type","success");
        result.put("message","更新成功！");
        return result;
    }


    /**
     * 删除班级信息
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteClazz",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> deleteClazz(
            @RequestParam(value = "ids[]",required = true)Long[] ids
    ) {
        Map<String,String> result = new HashMap<>(0);
        if(ids == null) {
            result.put("type","error");
            result.put("message","请选择要删除的数据！");
            return result;
        }
        if(clazzService.deleteClazz(join.joinString(Arrays.asList(ids),",")) <= 0) {
            result.put("type","error");
            result.put("message","删除失败！");
            return result;
        }
        result.put("type","success");
        result.put("message","删除成功！");
        return result;
    }

}
