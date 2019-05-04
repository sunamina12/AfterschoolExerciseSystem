package com.sy.controller;

/**
 * Created by 板野洋洋 on 2017/2/28.
 */

import javax.annotation.Resource;
import javax.servlet.http.*;
import java.io.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import com.sy.pojo.Student;
import com.sy.service.StudentService;

@Controller
@RequestMapping("/Astudent")
public class AStudentController {
    @Resource
    private StudentService stuService;

    //查询所有学生
    @RequestMapping("/showAllStudent")
    public ModelAndView showAllStudent() {
        ModelAndView view = new ModelAndView();
        List<Student> list = stuService.selectAllStudent();
        view.addObject("list", list);
        view.setViewName("AshowStudent");
        return view;
    }

    //删除学生
    @RequestMapping(value = "/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        if (stuService.deleteStudent(id)) {
            return "redirect:/Astudent/showAllStudent";//删除成功
        } else {
            return "redirect:/Astudent/showAllStudent";//删除失败
        }
    }

    //跳转至添加页面
    @RequestMapping("/addStudent")
    public String addStudent() {
        return "AaddStudent";
    }

    //添加学生
    @RequestMapping("/addStudent/addStuCom")
    public String addCom(@RequestParam("stuid1") String stuid,
                         @RequestParam("stuname1") String stuname,
                         @RequestParam("classid1") String classid,
                         @RequestParam("major1") String major,
                         Student stu) {
        stu.setStupwd("123");
        stu.setStuid(Integer.parseInt(stuid));
        stu.setStuname(stuname);
        stu.setGrade(Integer.parseInt(classid.substring(0,4)));
        stu.setClassid(classid);
        stu.setFaculty("计算机科学与技术学院");
        stu.setMajor(major);
        stuService.addStudent(stu);
        return "redirect:/Astudent/showAllStudent";//添加成功
    }

    //跳转至修改页面
    @RequestMapping(value = "/updateStudent/{id}")
    public String updateStudent(@PathVariable("id") int id, Model model) {
        Student stu = this.stuService.selectStuById(id);
        model.addAttribute("stu", stu);
        return "AupdateStudent";
    }

    //修改学生
    @RequestMapping(value = "/updateStudent/updateStuCom")
    public String updateStuCom(@RequestParam("id") String id,
                               @RequestParam("stuid") String stuid,
                               @RequestParam("stuname") String stuname,
                               @RequestParam("classid") String classid,
                               @RequestParam("major") String major,
                               Student stu) {
        Student stu1 = this.stuService.selectStuById(Integer.parseInt(id));
        String stuid1 = stu1.getStuid().toString();
        String stuname1 = stu1.getStuname();
        String stupwd1 = stu1.getStupwd();
        String classid1 = stu1.getClassid();
        String major1 = stu1.getMajor();
        if (!stuService.stuIdExist(Integer.parseInt(stuid))) {//学号已存在
            stu.setStuid(Integer.parseInt(stuid1));
            stu.setStuname(stuname);
            stu.setStupwd(stupwd1);
            stu.setGrade(Integer.parseInt(classid.substring(0,4)));
            stu.setClassid(classid);
            stu.setFaculty("计算机科学与技术学院");
            stu.setMajor(major);
            stuService.updateStudent(stu);
            return "redirect:/Astudent/showAllStudent";//学号不变，其他变或全不变
        }else{
            stu.setStuid(Integer.parseInt(stuid));
            stu.setStuname(stuname);
            stu.setStupwd(stupwd1);
            stu.setGrade(Integer.parseInt(classid1));
            stu.setClassid(classid);
            stu.setFaculty("计算机科学与技术学院");
            stu.setMajor(major);
            stuService.updateStudent(stu);
            return "redirect:/Astudent/showAllStudent";//全变
        }
    }

    //添加的学号是否存在
    @RequestMapping("/addStudent/stuIdExist")
    public void validateUser(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        out = response.getWriter();
        if(stuService.stuIdExist(Integer.parseInt(id))){
            out.println("success");
        }else{
            out.println("error");
        }
    }

    //查询单个学生（修改用）
    @ResponseBody
    @RequestMapping(value = "/updtStudent/getid", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public Student getStu(int stuid){
        Student stu = stuService.selectStuById(stuid);
        return  stu;
    }

    //修改学生
    @RequestMapping(value = "/updateStudent/updateStuCom1")
    public String updateStuCom1(@RequestParam("id2") String id,
                                @RequestParam("stuname2") String stuname,
                                @RequestParam("classid2") String classid,
                                @RequestParam("major2") String major,
                                Student stu) {
        Student stu1 = this.stuService.selectStuById(Integer.parseInt(id));
        String stuid1 = stu1.getStuid().toString();
        String stuname1 = stu1.getStuname();
        String stupwd1 = stu1.getStupwd();
        String classid1 = stu1.getClassid();
        String faculty1 = stu1.getFaculty();
        String major1 = stu1.getMajor();

        stu.setId(Integer.parseInt(id));
        stu.setStuid(Integer.parseInt(stuid1));
        stu.setStuname(stuname);
        stu.setStupwd(stupwd1);
        stu.setGrade(Integer.parseInt(classid.substring(0,4)));
        stu.setClassid(classid);
        stu.setFaculty(faculty1);
        stu.setMajor(major);
        stuService.updateStudent(stu);
        return "redirect:/Astudent/showAllStudent";//学号不变，其他变或全不变
    }
}
