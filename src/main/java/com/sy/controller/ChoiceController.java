package com.sy.controller;

/**
 * Created by 板野洋洋 on 2017/3/1.
 */

import javax.annotation.Resource;
import javax.servlet.http.*;
import java.io.*;

import com.sy.pojo.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import com.sy.pojo.Choice;
import com.sy.service.ChoiceService;

@Controller
@RequestMapping("/choice")
public class ChoiceController {
    @Resource
    private ChoiceService choService;

    //管理员身份
    //查询所有单选题
    @RequestMapping("/AshowAllChoice")
    public ModelAndView AshowAllChoice() {
        ModelAndView view = new ModelAndView();
        List<Choice> list = choService.selectAllCho();
        view.addObject("list", list);
        view.setViewName("AshowChoice");
        return view;
    }

    //删除单选题
    @RequestMapping(value = "/AdeleteChoice/{id}")
    public String AdeleteChoice(@PathVariable("id") int id) {
        if (choService.deleteChoice(id)) {
            return "redirect:/choice/AshowAllChoice";//删除成功
        } else {
            return "redirect:/choice/AshowAllChoice";//删除失败
        }
    }

    //跳转至修改页面
    @RequestMapping(value = "/AupdateChoice/{id}")
    public String AupdateChoice(@PathVariable("id") int id, Model model) {
        Choice cho = this.choService.selectChoById(id);
        model.addAttribute("cho", cho);
        return "AupdateChoice";
    }

    //修改单选题
    @RequestMapping(value = "/AupdateChoice/updateChoCom")
    public String AupdateTchCom(@RequestParam("id") String id,
                               @RequestParam("sbjt") String sbjt,
                               @RequestParam("topic") String topic,
                               @RequestParam("stem") String stem,
                               @RequestParam("option1") String optionA,
                               @RequestParam("option2") String optionB,
                               @RequestParam("option3") String optionC,
                               @RequestParam("option4") String optionD,
                               @RequestParam("answer") String answer,
                               Choice cho) {
        //System.out.println(userName);
        Choice cho1 = this.choService.selectChoById(Integer.parseInt(id));
        String sbjt1 = cho1.getSbjt();
        String topic1 = cho1.getTopic();
        String stem1 = cho1.getStem();
        String optionA1 = cho1.getOption1();
        String optionB1 = cho1.getOption2();
        String optionC1 = cho1.getOption3();
        String optionD1 = cho1.getOption4();
        String answer1 = cho1.getAnswer();
        if (!choService.choStemExist(stem)) {
            cho.setStem(stem1);
            cho.setSbjt(sbjt);
            cho.setTopic(topic);
            cho.setOption1(optionA);
            cho.setOption2(optionB);
            cho.setOption3(optionC);
            cho.setOption4(optionD);
            cho.setAnswer(answer);
            choService.updateChoice(cho);
            return "redirect:/choice/AshowAllChoice";//仅题干不变
            //return "redirect:/choice/showAllChoice";//全不变
        }else{
            cho.setStem(stem);
            cho.setSbjt(sbjt);
            cho.setTopic(topic);
            cho.setOption1(optionA);
            cho.setOption2(optionB);
            cho.setOption3(optionC);
            cho.setOption4(optionD);
            cho.setAnswer(answer);
            choService.updateChoice(cho);
            return "redirect:/choice/AshowAllChoice";//全变
        }
    }

    //跳转至添加页面
    @RequestMapping("/AaddChoice")
    public String AaddChoice() {
        return "AaddChoice";
    }

    //添加单选题
    @RequestMapping("/AaddChoice/addChoCom")
    public String AaddCom(@RequestParam("sbjt1") String sbjt,
                         @RequestParam("topic1") String topic,
                         @RequestParam("stem1") String stem,
                         @RequestParam("optionA1") String optionA,
                         @RequestParam("optionB1") String optionB,
                         @RequestParam("optionC1") String optionC,
                         @RequestParam("optionD1") String optionD,
                         @RequestParam("answer1") String answer,
                         Choice cho) {
        //System.out.println(stem);
        if (choService.choStemExist(stem)) {
            int choid = choService.selectMaxId()+1;
            cho.setChoid(choid);
            cho.setStem(stem);
            cho.setSbjt(sbjt);
            cho.setTopic(topic);
            cho.setOption1(optionA);
            cho.setOption2(optionB);
            cho.setOption3(optionC);
            cho.setOption4(optionD);
            cho.setAnswer(answer);
            choService.addChoice(cho);
            return "redirect:/choice/AshowAllChoice";//添加成功
        } else {
            return "redirect:/choice/AshowAllChoice";//添加失败
        }
    }
    //添加的题干是否存在
    @RequestMapping("/Aaddchoice/choIdExist")
    public void validateUser(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        String stem = request.getParameter("stem");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        out = response.getWriter();
        if(choService.choStemExist(stem)){
            out.println("success");
        }else{
            out.println("error");
        }
    }
    //查询单个选择题（修改用）
    @ResponseBody
    @RequestMapping(value = "/AupdtChoice/getid", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public Choice getCho(int choid){
        Choice cho = choService.selectChoById(choid);
        return  cho;
    }
    //修改
    @RequestMapping(value = "/AupdateChoice/updatechoCom1")
    public String updateStuCom1(@RequestParam("id2") String id,
                                @RequestParam("sbjt2") String sbjt,
                                @RequestParam("topic2") String topic,
                                @RequestParam("stem2") String stem,
                                @RequestParam("optionA2") String optionA,
                                @RequestParam("optionB2") String optionB,
                                @RequestParam("optionC2") String optionC,
                                @RequestParam("optionD2") String optionD,
                                @RequestParam("answer2") String answer,
                                Choice cho) {
        Choice cho1 = this.choService.selectChoById(Integer.parseInt(id));
        int choid1 = cho1.getChoid();
        String sbjt1 = cho1.getSbjt();
        String topic1 = cho1.getTopic();
        String stem1 = cho1.getStem();
        String optionA1 = cho1.getOption1();
        String optionB1 = cho1.getOption2();
        String optionC1 = cho1.getOption3();
        String optionD1 = cho1.getOption4();
        String answer1 = cho1.getAnswer();

        cho.setId(Integer.parseInt(id));
        cho.setChoid(choid1);
        cho.setSbjt(sbjt);
        cho.setTopic(topic);
        cho.setStem(stem1);
        cho.setOption1(optionA);
        cho.setOption2(optionB);
        cho.setOption3(optionC);
        cho.setOption4(optionD);
        cho.setAnswer(answer);
        choService.updateChoice(cho);
        return "redirect:/choice/AshowAllChoice";
    }



    //教师身份
    //查询所有单选题
    @RequestMapping("/TshowAllChoice")
    public ModelAndView TshowAllChoice() {
        ModelAndView view = new ModelAndView();
        List<Choice> list = choService.selectAllCho();
        view.addObject("list", list);
        view.setViewName("TshowChoice");
        return view;
    }

    //删除单选题
    @RequestMapping(value = "/TdeleteChoice/{id}")
    public String TdeleteChoice(@PathVariable("id") int id) {
        if (choService.deleteChoice(id)) {
            return "redirect:/choice/TshowAllChoice";//删除成功
        } else {
            return "redirect:/choice/TshowAllChoice";//删除失败
        }
    }

    //跳转至修改页面
    @RequestMapping(value = "/TupdateChoice/{id}")
    public String TupdateChoice(@PathVariable("id") int id, Model model) {
        Choice cho = this.choService.selectChoById(id);
        model.addAttribute("cho", cho);
        return "TupdateChoice";
    }

    //修改单选题
    @RequestMapping(value = "/TupdateChoice/updateChoCom")
    public String TupdateTchCom(@RequestParam("id") String id,
                                @RequestParam("sbjt") String sbjt,
                                @RequestParam("topic") String topic,
                                @RequestParam("stem") String stem,
                                @RequestParam("option1") String optionA,
                                @RequestParam("option2") String optionB,
                                @RequestParam("option3") String optionC,
                                @RequestParam("option4") String optionD,
                                @RequestParam("answer") String answer,
                                Choice cho) {
        //System.out.println(userName);
        Choice cho1 = this.choService.selectChoById(Integer.parseInt(id));
        String sbjt1 = cho1.getSbjt();
        String topic1 = cho1.getTopic();
        String stem1 = cho1.getStem();
        String optionA1 = cho1.getOption1();
        String optionB1 = cho1.getOption2();
        String optionC1 = cho1.getOption3();
        String optionD1 = cho1.getOption4();
        String answer1 = cho1.getAnswer();
        if (!choService.choStemExist(stem)) {
            cho.setStem(stem1);
            cho.setSbjt(sbjt);
            cho.setTopic(topic);
            cho.setOption1(optionA);
            cho.setOption2(optionB);
            cho.setOption3(optionC);
            cho.setOption4(optionD);
            cho.setAnswer(answer);
            choService.updateChoice(cho);
            return "redirect:/choice/TshowAllChoice";//仅题干不变
            //return "redirect:/choice/showAllChoice";//全不变
        }else{
            cho.setStem(stem);
            cho.setSbjt(sbjt);
            cho.setTopic(topic);
            cho.setOption1(optionA);
            cho.setOption2(optionB);
            cho.setOption3(optionC);
            cho.setOption4(optionD);
            cho.setAnswer(answer);
            choService.updateChoice(cho);
            return "redirect:/choice/TshowAllChoice";//全变
        }
    }

    //跳转至添加页面
    @RequestMapping("/TaddChoice")
    public String TaddChoice() {
        return "TaddChoice";
    }

    //添加单选题
    @RequestMapping("/TaddChoice/addChoCom")
    public String TaddCom(@RequestParam("sbjt1") String sbjt,
                          @RequestParam("topic1") String topic,
                          @RequestParam("stem1") String stem,
                          @RequestParam("optionA1") String optionA,
                          @RequestParam("optionB1") String optionB,
                          @RequestParam("optionC1") String optionC,
                          @RequestParam("optionD1") String optionD,
                          @RequestParam("answer1") String answer,
                          Choice cho) {
        //System.out.println(stem);
        if (choService.choStemExist(stem)) {
            int choid = choService.selectMaxId()+1;
            cho.setChoid(choid);
            cho.setStem(stem);
            cho.setSbjt(sbjt);
            cho.setTopic(topic);
            cho.setOption1(optionA);
            cho.setOption2(optionB);
            cho.setOption3(optionC);
            cho.setOption4(optionD);
            cho.setAnswer(answer);
            choService.addChoice(cho);
            return "redirect:/choice/TshowAllChoice";//添加成功
        } else {
            return "redirect:/choice/TshowAllChoice";//添加失败
        }
    }
    //添加的题干是否存在
    @RequestMapping("/Taddchoice/choIdExist")
    public void validateUser1(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        String stem = request.getParameter("stem");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        out = response.getWriter();
        if(choService.choStemExist(stem)){
            out.println("success");
        }else{
            out.println("error");
        }
    }
    //查询单个学生（修改用）
    @ResponseBody
    @RequestMapping(value = "/TupdtChoice/getid", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public Choice getCho1(int choid){
        Choice cho = choService.selectChoById(choid);
        return  cho;
    }
    //修改学生
    @RequestMapping(value = "/TupdateChoice/updatechoCom1")
    public String updateStuCom(@RequestParam("id2") String id,
                                @RequestParam("sbjt2") String sbjt,
                                @RequestParam("topic2") String topic,
                                @RequestParam("stem2") String stem,
                                @RequestParam("optionA2") String optionA,
                                @RequestParam("optionB2") String optionB,
                                @RequestParam("optionC2") String optionC,
                                @RequestParam("optionD2") String optionD,
                                @RequestParam("answer2") String answer,
                                Choice cho) {
        Choice cho1 = this.choService.selectChoById(Integer.parseInt(id));
        int choid1 = cho1.getChoid();
        String sbjt1 = cho1.getSbjt();
        String topic1 = cho1.getTopic();
        String stem1 = cho1.getStem();
        String optionA1 = cho1.getOption1();
        String optionB1 = cho1.getOption2();
        String optionC1 = cho1.getOption3();
        String optionD1 = cho1.getOption4();
        String answer1 = cho1.getAnswer();
        cho.setId(Integer.parseInt(id));
        cho.setChoid(choid1);
        cho.setSbjt(sbjt);
        cho.setTopic(topic);
        cho.setStem(stem1);
        cho.setOption1(optionA);
        cho.setOption2(optionB);
        cho.setOption3(optionC);
        cho.setOption4(optionD);
        cho.setAnswer(answer);
        choService.updateChoice(cho);
        return "redirect:/choice/TshowAllChoice";//学号不变，其他变或全不变
    }
}
