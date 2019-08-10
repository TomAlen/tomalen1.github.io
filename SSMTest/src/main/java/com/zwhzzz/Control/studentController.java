package com.zwhzzz.Control;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.zwhzzz.page.page;
import com.zwhzzz.pojo.Student;
import com.zwhzzz.service.ClazzService;
import com.zwhzzz.service.StudentService;
import com.zwhzzz.util.join;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/student")
public class studentController {

    @Autowired
    ClazzService clazzService;
    @Autowired
    StudentService studentService;

    //用于前端返回的信息
    Map<String, String> result = new HashMap<>(0);

    @RequestMapping(value = "/goStudentListView",method = RequestMethod.GET)
    public ModelAndView goClazzListView(ModelAndView model) {
        model.setViewName("/student/studentList");
        model.addObject("clazzList",clazzService.findAll());
        //将查询到的数据全转化为json格式
        model.addObject("clazzListJson",JSONArray.fromObject(clazzService.findAll()));
        return model;
    }

    /**
     * 获取学生列表
     * @param username
     * @param clazzId
     * @param rows
     * @param page
     * @param pages
     * @param request
     * @return
     */

    @RequestMapping(value = "/getStudentList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getStudentList(@RequestParam(value = "username",required = false,defaultValue = "")String username,
                                             @RequestParam(value = "clazzId",required = false)Integer clazzId,
                                             Integer rows, Integer page,page pages,
                                             HttpServletRequest request) {

        Map<String,Object> result = new HashMap<>(0);
        Map<String,Object> querymap = new HashMap<>(0);
        //获取会话中的值
        //获取所有学生
        querymap.put("username","%"+username+"%");
        Object userType = request.getSession().getAttribute("userType");
        //判断是否为学生
        if("2".equals(userType.toString())) {
            Student loginStudent = (Student)request.getSession().getAttribute("user");
            //只获取登录者的信息
            querymap.put("username","%" + loginStudent.getUsername()+"%");
        }
        if(clazzId != null) {
            querymap.put("clazzId",clazzId);//转向前端的模糊查询
        }
        querymap.put("offset",pages.getOffset());
        querymap.put("pageSize",pages.getRows());
        result.put("rows",studentService.get_Student_List(querymap));
        result.put("total",studentService.getCount(querymap));
        //分页
        //Mybatis的PageIHelper分页插件
        //设置每页的分页数
      /*  PageHelper.startPage(page,rows);
        System.out.println(querymap);
        List<Student> list = studentService.get_Student_List(querymap);
       //System.out.println(list);
        //封装数据列表
        PageInfo<Student> pageInfo = new PageInfo<>(list);
        //获取总记录数
        Long total = pageInfo.getTotal();
        //获取当前页数
        List<Student> stuList = pageInfo.getList();
        //存储记录数
        result.put("total",total);
        result.put("rows",stuList);*/
        return result;
    }
    /**
     * 添加学生功能
     * @param student
     * @return
     */
    @RequestMapping(value = "/addStudent",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> insertStudent(Student student) {

        System.out.println(student.getPortrait_path());
        if(StringUtil.isEmpty(student.getUsername())) {
            result.put("type","error");
            result.put("message","学生信息为空，添加失败！");
            return result;
        }
        student.setSno(join.generateSn("S",""));
        if(student.getClazzId() == null) {
            result.put("type","error");
            result.put("message","请选择所属班级！");
            return result;
        }
        Student student1 = studentService.findStudentByName(student.getUsername());
        if(student1 != null) {
            result.put("type","error");
            result.put("message","学生姓名已存在！");
            return result;
        }
        if(studentService.insertStudent(student) <= 0) {
            result.put("type","error");
            result.put("message","添加失败！");
            return result;
        }
        result.put("type","success");
        result.put("message","添加成功！");
        return result;
    }

    /**
     * 修改学生信息
     * @param student
     * @return
     */
    @RequestMapping(value = "/editStudent",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> updateStudent(Student student) {
        if(student == null) {
            result.put("type","error");
            result.put("message","学生信息为空，修改失败！");
            return result;
        }
        Student existName = studentService.findStudentByName(student.getUsername());
        if(existName != null) {
            //判断两者的id是否一致
            if(student.getId() == existName.getId()) {
                result.put("type","error");
                result.put("message","学生姓名已存在！");
                return result;
            }
        }
        if(studentService.updateStudent(student) <= 0) {
            result.put("type","error");
            result.put("message","修改失败！");
            return result;
        }
        result.put("type","success");
        result.put("message","修改成功！");
        return result;
    }

    /**
     * 批量删除学生
     * @param ids
     * @return
     */

    @RequestMapping(value = "/deleteStudent",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> deleteStudent(@RequestParam(value = "ids[]",required = true)Long[] ids) {
        if(ids == null){
            result.put("type","error");
            result.put("message","要删除的数据不能为空！");
            return result;
        }
        if(studentService.deleteStudent(join.joinString(Arrays.asList(ids),",")) <= 0) {
            result.put("type","error");
            result.put("message","删除失败！");
            return result;
        }
        result.put("type","success");
        result.put("message","删除成功！！");
        return result;
    }

    /**
     * 学生头像上传功能
     * @param photo
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadPhoto")
    @ResponseBody
    public Map<String,Object> uploadPhoto(MultipartFile photo, HttpServletRequest request) throws IOException {

        Map<String,Object> result = new HashMap<>(0);
        if (photo == null) {
            result.put("success",false);
            result.put("message", "图片上传失败！");
            return result;
        }
        if (photo.getSize() > 20971520) {
            result.put("success",false);
            result.put("message", "上传的图片请下于20M！");
            return result;
        }
        //判断上传的图片的后缀名,或取.后面的后缀名
        String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1, photo.getOriginalFilename().length());
        //判断文件是否包含以下类型
        if (!"jpg,png,gif,jpeg".contains(suffix.toLowerCase())) {
            result.put("success",false);
            result.put("message", "文件格式不正确，请上传jpg,png,gif,jpeg格式的文件！");
            return result;
        }
        //获取本地的真实路径，保存在本地文件夹下,创建的的文件夹名字为upload
        String savePath = request.getServletContext().getRealPath("/") + "\\upload\\";
        System.out.println(savePath);
        File savePathFile = new File(savePath);
        //如果不存在就创建一个文件夹
        if (!savePathFile.exists()) {
            savePathFile.mkdir();
        }
        //把文件转存到目标文件夹下
        String fileName = new Date().getTime() + "." + suffix;
        photo.transferTo(new File(savePath + fileName));
        result.put("success", true);
        //result.put("message", "图片上传成功");
        //把路径传给前台进行处理
        result.put("portrait_path", request.getServletContext().getContextPath() + "/upload/" + fileName);
        return result;
    }
}
