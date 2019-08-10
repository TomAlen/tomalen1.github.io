package com.zwhzzz.Control;

import com.github.pagehelper.util.StringUtil;
import com.zwhzzz.pojo.Student;
import com.zwhzzz.pojo.Teacher;
import com.zwhzzz.pojo.User;
import com.zwhzzz.service.StudentService;
import com.zwhzzz.service.TeacherService;
import com.zwhzzz.service.UserService;
import com.zwhzzz.util.CpachaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/system")
public class systemController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    /**
     * 登录页面
     * @return
     */

     @GetMapping("/goLogin")
    public String goLogin() {
        return "system/login";
    }

    /**
     * 登录表单提交
     * @return
     */
    //利用ajax将对象转化为json格式传入界面
    @RequestMapping(value = "/UserLogin",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(  //给定参数，required为true,表示必须填写。
                                       HttpServletRequest request,
                                       HttpServletResponse response,
                                       @RequestParam(value = "username",required = true)String username,
                                       @RequestParam(value = "password",required = true)String password,
                                       @RequestParam(value = "verifiCode",required = true)String verifiCode,
                                       @RequestParam(value = "userType",required = true)Integer userType) {
       Map<String,Object> result = new HashMap<>(0);
        //获取验证码
        String vcode = (String) request.getSession().getAttribute("loginCapacha");
        if("".equals(vcode)) {
            result.put("success",false);
            result.put("message","长时间为操作,会话已失效,请刷新页面后重试!");
            return result;
        }else if(! vcode.toUpperCase().equals(verifiCode.toUpperCase())) {
            result.put("success",false);
            result.put("message","验证码错误!");
            return result;
        }
        //清空验证码缓存
        request.getSession().removeAttribute("loginCapacha");

        //验证登陆者的身份验证
        //代表管理员
        if(userType == 1) {
            User user = userService.findUserByUserName(username);
            if(user == null) {
                result.put("success",false);
                result.put("message","不存在该用户！");
                return result;
            }else if (! user.getPassword().equals(password)) {
                result.put("success",false);
                result.put("message","密码错误！");
                return result;
            }
                request.getSession().setAttribute("user",user);
                request.getSession().setAttribute("userType",userType);
                result.put("success",true);
                result.put("message","登录成功！");
        }
        //代表学生
        if(userType == 2) {
            Student student = studentService.findStudentByName(username);
            if(student == null) {
                result.put("success",false);
                result.put("message","不存在该学生！");
                return result;
            }else if (! student.getPassword().equals(password)) {
                result.put("success",false);
                result.put("message","密码错误！");
                return result;
            }
            request.getSession().setAttribute("user",student);
            request.getSession().setAttribute("userType",userType);
            result.put("success",true);
            result.put("message","登录成功！");

        }
        //代表老师
        if(userType == 3) {
            Teacher teacher = teacherService.findTeacherByName(username);
            if(teacher == null) {
                result.put("success",false);
                result.put("message","不存在该老师！");
                return result;
            }else if (! teacher.getPassword().equals(password)) {
                result.put("success",false);
                result.put("message","密码错误！");
                return result;
            }
            request.getSession().setAttribute("user",teacher);
            request.getSession().setAttribute("userType",userType);
            result.put("success",true);
            result.put("message","登录成功！");
        }
        return result;
    }
    /**
     * 获取验证码
     * @param request
     * @param vl
     * @param width
     * @param height
     * @param response
     */
    @RequestMapping(value="/get_codeC",method = RequestMethod.GET)
    public void getCodeCha(HttpServletRequest request,
                            //设置自定义的字母个数，宽度和高度。
                           @RequestParam(value = "vl",defaultValue = "4",required = false)Integer vl,
                           @RequestParam(value = "width",defaultValue = "98",required = false)Integer width,
                           @RequestParam(value = "height",defaultValue = "33",required = false)Integer height,
                           HttpServletResponse response) {
        //System.out.println("获取验证码！");
        CpachaUtil cpachaUtil = new CpachaUtil(vl,width,height);
        String  StringGeneratorVCode= cpachaUtil.generatorVCode();
        //把验证码存入会话中
        request.getSession().setAttribute("loginCapacha",StringGeneratorVCode);
        //干扰线
        BufferedImage bufferedImage = cpachaUtil.generatorRotateVCodeImage(StringGeneratorVCode, true);
        try {
            //把图片写入gif流中
            ImageIO.write(bufferedImage, "gif", response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * 登录成功转为的主页面
     */
    @RequestMapping(value = "/goLoginSuccess",method = RequestMethod.GET)
    public ModelAndView goLoginSuccess(ModelAndView model) {
        model.setViewName("system/main");
        return model;
    }

    /**
     * 登录退出
     * @return
     */
    @RequestMapping(value = "/loginOut",method = RequestMethod.GET)
    public String loginOut(HttpServletRequest request) {
        request.getSession().setAttribute("user",null);
        return "redirect:goLogin";

    }
}
