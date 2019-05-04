package com.sy.controller;

/**
 * Created by 板野洋洋 on 2017/3/1.
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

import com.sy.pojo.Teacher;
import com.sy.service.TeacherService;

@Controller
@RequestMapping("/Ateacher")
public class ATeacherController {
    @Resource
    private TeacherService tchService;

    //查询所有教师
    @RequestMapping("/showAllTeacher")
    public ModelAndView showAllTeacher() {
        ModelAndView view = new ModelAndView();
        List<Teacher> list = tchService.selectAllTeacher();
        view.addObject("list", list);
        view.setViewName("AshowTeacher");
        return view;
    }

    //删除教师
    @RequestMapping(value = "/deleteTeacher/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        if (tchService.deleteTeacher(id)) {
            return "redirect:/Ateacher/showAllTeacher";//删除成功
        } else {
            return "redirect:/Ateacher/showAllTeacher";//删除失败
        }
    }

    //跳转至添加页面
    @RequestMapping("/addTeacher")
    public String addTeacher() {
        return "AaddTeacher";
    }

    //添加教师
    @RequestMapping("/addTeacher/addTchCom")
    public String addCom(@RequestParam("tchid1") String tchid,
                         @RequestParam("tchname1") String tchname,
                         @RequestParam("major1") String major,
                         Teacher tch) {
        if (tchService.tchIdExist(Integer.parseInt(tchid))) {
            tch.setTchpwd("123");
            tch.setTchid(Integer.parseInt(tchid));
            tch.setTchname(tchname);
            tch.setFaculty("计算机科学与技术学院");
            tch.setMajor(major);
            tchService.addTeacher(tch);
            return "redirect:/Ateacher/showAllTeacher";//添加成功
        } else {
            return "redirect:/Ateacher/showAllTeacher";//添加失败
        }
    }

    //跳转至修改页面
    @RequestMapping(value = "/updateTeacher/{id}")
    public String updateTeacher(@PathVariable("id") int id, Model model) {
        Teacher tch = this.tchService.selectTchById(id);
        model.addAttribute("tch", tch);
        return "AupdateTeacher";
    }

    //修改教师
    @RequestMapping(value = "/updateTeacher/updateTchCom")
    public String updateTchCom(@RequestParam("id") String id,
                               @RequestParam("tchid") String tchid,
                               @RequestParam("tchname") String tchname,
                               @RequestParam("major") String major,
                               Teacher tch) {
        //System.out.println(userName);
        Teacher tch1 = this.tchService.selectTchById(Integer.parseInt(id));
        String tchid1 = tch1.getTchid().toString();
        String tchname1 = tch1.getTchname();
        String tchpwd1 = tch1.getTchpwd();
        String faculty1 = tch1.getFaculty();
        String major1 = tch1.getMajor();
        if (!tchService.tchIdExist(Integer.parseInt(tchid))) {
            tch.setTchid(Integer.parseInt(tchid1));
            tch.setTchname(tchname);
            tch.setTchpwd(tchpwd1);
            tch.setFaculty("计算机科学与技术学院");
            tch.setMajor(major);
            tchService.updateTeacher(tch);
            return "redirect:/Ateacher/showAllTeacher";//仅工号不变
            //return "redirect:/Ateacher/showAllTeacher";//全不变
        }else{
            tch.setTchid(Integer.parseInt(tchid));
            tch.setTchname(tchname);
            tch.setTchpwd(tchpwd1);
            tch.setFaculty("计算机科学与技术学院");
            tch.setMajor(major);
            tchService.updateTeacher(tch);
            return "redirect:/Ateacher/showAllTeacher";//全变
        }
    }

    //添加的工号是否存在
    @RequestMapping("/addTeacher/tchIdExist")
    public void validateUser(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        out = response.getWriter();
        if(tchService.tchIdExist(Integer.parseInt(id))){
            out.println("success");
        }else{
            out.println("error");
        }
    }

    //查询单个学生（修改用）
    @ResponseBody
    @RequestMapping(value = "/updtTeacher/getid", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public Teacher getTch(int tchid){
        Teacher tch = tchService.selectTchById(tchid);
        return  tch;
    }

    //修改学生
    @RequestMapping(value = "/updateTeacher/updateTchCom1")
    public String updateStuCom1(@RequestParam("id2") String id,
                                @RequestParam("tchname2") String tchname,
                                @RequestParam("major2") String major,
                                Teacher tch) {
        Teacher tch1 = this.tchService.selectTchById(Integer.parseInt(id));
        String tchid1 = tch1.getTchid().toString();
        String tchname1 = tch1.getTchname();
        String tchpwd1 = tch1.getTchpwd();
        String faculty1 = tch1.getFaculty();
        String major1 = tch1.getMajor();

        tch.setId(Integer.parseInt(id));
        tch.setTchid(Integer.parseInt(tchid1));
        tch.setTchname(tchname);
        tch.setTchpwd(tchpwd1);
        tch.setFaculty(faculty1);
        tch.setMajor(major);
        tchService.updateTeacher(tch);
        return "redirect:/Ateacher/showAllTeacher";//学号不变，其他变或全不变
    }

}
