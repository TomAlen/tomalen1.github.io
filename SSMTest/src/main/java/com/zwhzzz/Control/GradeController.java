package com.zwhzzz.Control;

import com.zwhzzz.page.page;
import com.zwhzzz.pojo.Grade;
import com.zwhzzz.service.GradeService;
import com.zwhzzz.util.join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    GradeService gradeService;

    /**
     * 年级列表页
     * @param model
     * @return
     */
    @RequestMapping(value = "/goGradeListView",method = RequestMethod.GET)
    public ModelAndView goAdminListView(ModelAndView model) {
        model.setViewName("/grade/gradeList");
        return model;
    }

    @RequestMapping("/getGradeList")
    @ResponseBody
    public Map<String,Object> getGradeList(@RequestParam(value = "name",required = true,defaultValue = "")String name,
                                            page page) {
        Map<String,Object> result = new HashMap<>(0);
        Map<String,Object> querymap = new HashMap<>(0);
        //模糊查询
        querymap.put("name","%" + name + "%");
        //分页
        querymap.put("offset",page.getOffset());//表示从第几页开始
        querymap.put("pageSize",page.getRows());//得到一行的数据
        //将查出的数据和分页信息封装传入前端
        result.put("rows",gradeService.get_Grade_List(querymap));
        result.put("total",gradeService.getCount(querymap));
        return result;
    }

    /**
     * 班级添加功能
     * @param grade 班级信息
     * @return
     */

    @RequestMapping("/addGrade")
    @ResponseBody
    public Map<String,String> AddGrade(Grade grade) {
        Map<String,String> result = new HashMap<>(0);
        if(grade == null) {
            result.put("type","error");
            result.put("message","无有效数据!");
            return result;
        }
        //查出是否有相同相同姓名的数据
        Grade grade1 = gradeService.findGradeByName(grade.getName());
        if(grade1 != null) {
            //判断两个用户的id是否相同
            if(grade.getId() != grade1.getId()){
                result.put("type","error");
                result.put("message","该用户已经存在!");
                return result;
            }
        }
        if(gradeService.insertGrade(grade) <= 0) {
            result.put("type","error");
            result.put("message","添加不成功！!");
            return result;
        }
            result.put("type","success");
            result.put("message","添加成功!");
            return result;
    }

    @RequestMapping(value = "/editGrade",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> updateGrade(Grade grade) {
        Map<String,String> result = new HashMap<>(0);

        if(gradeService.updateGrade(grade) <= 0) {
            result.put("type","error");
            result.put("message","更新失败！");
            return result;
        }
        //查询出更新后的数据是否在数据库中存在
        Grade grade1 = gradeService.findGradeByName(grade.getName());
        //不为空就表示存在
        if(grade1 != null) {
            result.put("type","error");
            result.put("message","用户名已存在，更新失败！");
            return result;
        }
        result.put("type","success");
        result.put("message","更新成功！");
        return result;
    }

    /**
     *
     * 批量删除
     *
     */

    @RequestMapping(value = "/deleteGrade",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> deleteGrade(
            //传入一个数组参数
            @RequestParam(value = "ids[]",required = true)Long[] ids
    ) {

        Map<String,String> result = new HashMap<>(0);
        if(ids == null) {
            result.put("type","error");
            result.put("message","请选择要删除的数据！");
            return result;
        }
  /*      String idsString = "";
       //将ids转换为字符串。
        for(Integer id : ids){
            idsString += id + ",";
        }
        idsString = idsString.substring(0,idsString.length()-1);*/

        if(gradeService.deleteGrade(join.joinString(Arrays.asList(ids),",")) <= 0) {
            result.put("type","error");
            result.put("message","删除失败！");
            return result;
        }
        result.put("type","success");
        result.put("message","删除成功！");
        return result;
    }

}
