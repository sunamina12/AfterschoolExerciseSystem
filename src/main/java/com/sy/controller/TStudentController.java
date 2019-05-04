package com.sy.controller;

/**
 * Created by 板野洋洋 on 2017/3/1.
 */
import javax.annotation.Resource;
import javax.servlet.http.*;
import java.io.*;

import com.sy.pojo.Assignment;
import com.sy.service.AssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.sy.pojo.Student;
import com.sy.service.StudentService;

@Controller
@RequestMapping("/Tstudent")
public class TStudentController {
    @Resource
    private StudentService stuService;
    @Resource
    private AssignmentService asgnService;

    //查询所有学生
    @RequestMapping("/showAllStudent")
    public ModelAndView showAllStudent(){
        ModelAndView view = new ModelAndView();
        List<Student> list = stuService.selectAllStudent();
        view.addObject("list",list);
        view.setViewName("TshowStudent");
        return view;
    }

    @RequestMapping("/showAllAsgn")
    public ModelAndView showAllAsgn(HttpServletRequest request){
        ModelAndView view = new ModelAndView();
        HttpSession session = request.getSession();
        String tchid = (String) session.getAttribute("userid");
        List<Assignment> list = asgnService.selectByUploader(Integer.parseInt(tchid));
        view.addObject("list",list);
        view.setViewName("TshowScoreAsgn");
        return view;
    }
}
