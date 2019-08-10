package com.zwhzzz.Control;

import com.zwhzzz.pojo.Student;
import com.zwhzzz.pojo.Teacher;
import com.zwhzzz.pojo.User;
import com.zwhzzz.service.StudentService;
import com.zwhzzz.service.TeacherService;
import com.zwhzzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    Map<String,String> result = new HashMap<>(0);


    @GetMapping("/goSettingView")
    public String goLogin() {
        return "common/settings";
    }


    @RequestMapping(value = "/editPassword",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> editPassword(String oldPassword,
                                           String password,
                                           HttpServletRequest request) {
        int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());

            //管理员
            if(userType == 1) {
                User admin = (User) request.getSession().getAttribute("user");
                //如果旧密码不等
                if (!oldPassword.equals(admin.getPassword())) {
                    result.put("type", "error");
                    result.put("message", "原密码错误!");
                    return result;
                }
                try {
                    admin.setPassword(password);
                    if (userService.updatePassword(admin) > 0) {
                        result.put("type", "success");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result.put("type", "error");
                    result.put("message", "修改密码失败！");
                }
            }
               //学生
            if(userType == 2) {
                Student student = (Student) request.getSession().getAttribute("user");
                if (!oldPassword.equals(student.getPassword())) {
                    result.put("type", "error");
                    result.put("message", "原密码错误!");
                    return result;
                }
                try{
                    student.setPassword(password);
                    if(studentService.updatePassword(student) > 0) {
                        result.put("type","success");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    result.put("type","error");
                    result.put("message","更新密码错误！");
                }
            }
                //教师
            if(userType == 3) {
                Teacher teacher = (Teacher) request.getSession().getAttribute("user");
                if (!oldPassword.equals(teacher.getPassword())) {
                    result.put("type", "error");
                    result.put("message", "原密码错误!");
                    return result;
                }
                try{
                    teacher.setPassword(password);
                    if(teacherService.updatePassword(teacher) > 0 ) {
                        result.put("type","success");
                    }
                }catch (Exception e) {
                    result.put("type","error");
                    result.put("message","修改密码错误！");
                }
            }
            return result;
        }
}
