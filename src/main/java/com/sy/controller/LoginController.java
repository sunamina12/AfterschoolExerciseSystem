package com.sy.controller;

/**
 * Created by 板野洋洋 on 2017/3/2.
 */
import javax.annotation.Resource;
import javax.json.Json;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import javax.jms.Session;

import com.alibaba.fastjson.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;

import com.sy.pojo.*;
import com.sy.service.*;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Resource
    private StudentService stuService;
    @Resource
    private TeacherService tchService;
    @Resource
    private AdminService adminService;

    //跳转至登录页面
    @RequestMapping("/home")
    public String loginhome() {
        return "login";
    }

    //检查是否符合登陆条件
    @RequestMapping("/validate")
    public void validate(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        out = response.getWriter();
        if(stuService.stuPwdExist(pwd, Integer.parseInt(id))){
            out.println("success");
        }else{
            out.println("error");
        }
    }

    //跳转至注册页面
    @RequestMapping("register")
    public String registerhome() {
        return "register";
    }

    //检查用户名是否已经注册
    @RequestMapping("register/validateUser")
    public void validateUser(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        out = response.getWriter();
        if(stuService.stuIdExist(Integer.parseInt(name))){
            out.println("success");
        }else{
            out.println("error");
        }
    }

    //注册
    @RequestMapping("register/registerCon")
    public String loginCon(@RequestParam("stuid") String stuid,
                           @RequestParam("pwd2") String pwd2,
                           @RequestParam("stuname") String stuname,
                           @RequestParam("classid") String classid,
                           @RequestParam("major") String major) {
        System.out.println(stuid);
        System.out.println(pwd2);
        System.out.println(stuname);
        System.out.println(classid);
        System.out.println(major);
        Student stu = new Student();
        stu.setStuid(Integer.parseInt(stuid));
        stu.setStupwd(pwd2);
        stu.setStuname(stuname);
        stu.setGrade(Integer.parseInt(classid.substring(0,4)));
        stu.setClassid(classid);
        stu.setFaculty("计算机科学与技术学院");
        stu.setMajor(major);
        stuService.addStudent(stu);
        return "redirect:/login/home";
    }

    //登录Version1.0
    @RequestMapping("/home/loginCon")
    public String loginCon(@RequestParam("id") String id,
                           @RequestParam("pwd") String pwd,
                           @RequestParam("authority") String authority,
                           HttpServletRequest request) {
        Enumeration em = request.getSession().getAttributeNames();
        while(em.hasMoreElements()){
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        //System.out.println("tchId="+id+" "+" tchPwd="+pwd+" authority="+authority);
        if("tch".equals(authority)){//教师登录
            System.out.println("=====教师登录=====");
            if(!tchService.tchIdExist(Integer.parseInt(id))){//工号存在
                System.out.println("工号存在");
                if(tchService.tchPwdExist(pwd,Integer.parseInt(id))){
                    System.out.println("工号密码正确");
                    return "redirect:/teacher/home";//工号密码正确
                }else{
                    System.out.println("工号或密码错误");
                    return "redirect:/login/home";//工号或密码错误
                }
            }else {
                System.out.println("工号不存在");
                return "redirect:/login/home";//工号不存在
            }
        }else if("stu".equals(authority)){//学生登录
            //System.out.println("stuId="+id+" "+" stuPwd="+pwd+" authority="+authority);
            System.out.println("=====学生登录=====");
            if(!stuService.stuIdExist(Integer.parseInt(id))){//学号存在
                System.out.println("学号学号存在");
                if(stuService.stuPwdExist(pwd,Integer.parseInt(id))){
                    System.out.println("密码正确");
                    return "redirect:/student/home";//学号密码正确
                }else{
                    System.out.println("学号或密码错误");
                    return "redirect:/login/home";//学号或密码错误
                }
            }else {
                System.out.println("学号不存在");
                return "redirect:/login/home";//学号不存在
            }
        }else if("admin".equals(authority)){//管理员登录
            //System.out.println("adminId="+id+" "+" adminPwd="+pwd+" authority="+authority);
            System.out.println("=====管理员登录=====");
            if(!adminService.adminIdExist(Integer.parseInt(id))){//管理员号存在
                System.out.println("管理员号存在");
                if(adminService.adminPwdExist(pwd,Integer.parseInt(id))){
                    System.out.println("密码正确");

                    return "redirect:/admin/home";//管理员号密码正确
                }else{
                    System.out.println("管理员号或密码错误");
                    return "redirect:/login/home";//管理员号或密码错误
                }
            }else {
                System.out.println("管理员号不存在");
                return "redirect:/login/home";//管理员号不存在
            }
        }
        return "redirect:/login/home";//返回登录页
    }

    //登录Version2.0(在用)
    @RequestMapping("home/validateLogin")
    public void validateLogin(HttpServletRequest request,
                              HttpServletResponse response,
                              Model model) throws Exception {
        Enumeration em = request.getSession().getAttributeNames();
        while(em.hasMoreElements()){
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        HttpSession session = request.getSession();
        String id = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        String authority = request.getParameter("authority");
        System.out.println(id+" "+pwd+" "+authority);
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        out = response.getWriter();
        Map<String,Object> map = new HashMap<String,Object>();
        if("tch".equals(authority)){//教师登录
            System.out.println("=====教师登录=====");
            if(!tchService.tchIdExist(Integer.parseInt(id))){//工号存在
                System.out.println("工号存在");
                if(tchService.tchPwdExist(pwd,Integer.parseInt(id))){
                    System.out.println("工号密码正确");
                    Teacher teacher = tchService.selectByTchId(Integer.parseInt(id));
                    session.setAttribute("teacher",teacher);
                    session.setAttribute("userid",id);
                    out.println("tchSuccess");//工号密码正确
                    map.put("msg", "tchSuccess");
                }else{
                    System.out.println("工号或密码错误");
                    out.println("error");//工号或密码错误
                }
            }else {
                System.out.println("工号不存在");
                out.println("error");//工号不存在
            }
        }else if("stu".equals(authority)){//学生登录
            System.out.println("=====学生登录=====");
            if(!stuService.stuIdExist(Integer.parseInt(id))){//学号存在
                System.out.println("学号学号存在");
                if(stuService.stuPwdExist(pwd,Integer.parseInt(id))){
                    System.out.println("密码正确");
                    Student student = stuService.selectByStuId(Integer.parseInt(id));
                    session.setAttribute("student",student);
                    session.setAttribute("userid",id);
                    out.println("stuSuccess");//学号密码正确
                    map.put("msg", "stuSuccess");
                }else{
                    System.out.println("学号或密码错误");
                    out.println("error");//学号或密码错误
                }
            }else {
                System.out.println("学号不存在");
                out.println("error");//学号不存在
            }
        }else if("admin".equals(authority)){//管理员登录
            //System.out.println("adminId="+id+" "+" adminPwd="+pwd+" authority="+authority);
            System.out.println("=====管理员登录=====");
            if(!adminService.adminIdExist(Integer.parseInt(id))){//管理员号存在
                System.out.println("管理员号存在");
                if(adminService.adminPwdExist(pwd,Integer.parseInt(id))){
                    System.out.println("密码正确");
                    Admin admin = adminService.selectByAdminId(Integer.parseInt(id));
                    session.setAttribute("admin",admin);
                    session.setAttribute("userid",id);
                    out.println("adminSuccess");//管理员号密码正确
                    map.put("msg", "adminSuccess");
                }else{
                    System.out.println("管理员号或密码错误");
                    out.println("error");//管理员号或密码错误
                }
            }else {
                System.out.println("管理员号不存在");
                out.println("error");//管理员号不存在
            }
        }
    }

    //登录Version3.0(测试)
    @ResponseBody
    @RequestMapping("home/validateLogin1")
    public String validateLogin1(HttpServletRequest request,
                              HttpServletResponse response,
                              Model model) throws Exception {
        Enumeration em = request.getSession().getAttributeNames();
        while(em.hasMoreElements()){
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        HttpSession session = request.getSession();
        String id = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        String authority = request.getParameter("authority");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        out = response.getWriter();
        Map<String,Object> map = new HashMap<String,Object>();
        if("admin".equals(authority)){
            if(adminService.adminPwdExist(pwd,Integer.parseInt(id))){
                Admin admin = adminService.selectById(Integer.parseInt(id));
                model.addAttribute("admin",admin);
                session.setAttribute("admin",admin);
                response.sendRedirect("/admin/home");
                return "redirect:/admin/home";
            }else {
                out.println("error");
                map.put("msg", "error");

            }
        }
        return null;
    }

    //logout
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        Enumeration em = request.getSession().getAttributeNames();
        while(em.hasMoreElements()){
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        System.out.println("=====已退出=====");
        return "redirect:/login/home";
    }

    //updtpwd
    @RequestMapping("updtpwd")
    public ModelAndView updtpwd(HttpSession session,
                          HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        Student stu = (Student) session.getAttribute("student");
        Teacher tch = (Teacher) session.getAttribute("teacher");
        Admin admin = (Admin) session.getAttribute("admin");
        if (stu!=null) {
            view.addObject("pwd",stu.getStupwd());
        } else if (tch!=null) {
            view.addObject("pwd",tch.getTchpwd());
        } else if (admin!=null) {
            view.addObject("pwd",admin.getAdminpwd());
        }
        view.setViewName("updt_pwd");
        return view;
    }

    //确认修改密码
    @RequestMapping("updtpwdCon")
    public String updtpwdCon(HttpServletRequest request,
                           HttpSession session) {
        String newpwd = request.getParameter("newpwd");
        String newpwdCon = request.getParameter("newpwdCon");
        Student stu = (Student) session.getAttribute("student");
        Teacher tch = (Teacher) session.getAttribute("teacher");
        Admin admin = (Admin) session.getAttribute("admin");
        if (stu!=null) {
            if (newpwd.equals(newpwdCon)) {
                Student student = new Student();
                student.setId(stu.getId());
                student.setStupwd(newpwd);
                stuService.updateByPrimaryKeySelective(student);
            }
        } else if (tch!=null) {
            if (newpwd.equals(newpwdCon)) {
                Teacher teacher = new Teacher();
                teacher.setId(tch.getId());
                teacher.setTchpwd(newpwd);
                tchService.updateTeacherSelective(teacher);
            }
        } else if (admin!=null) {
            if (newpwd.equals(newpwdCon)) {
                Admin admin1 = new Admin();
                admin1.setId(admin.getId());
                admin1.setAdminpwd(newpwd);
                adminService.updateAdminSelective(admin1);
            }
        }
        return "redirect:/login/logout";
    }
}
