package com.sy.controller;

/**
 * Created by 板野洋洋 on 2017/3/2.
 */
import javax.annotation.Resource;
import javax.servlet.http.*;
import java.io.*;

import com.sy.pojo.Choice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import com.sy.pojo.Blank;
import com.sy.service.BlankService;

@Controller
@RequestMapping("/blank")
public class BlankController {
    @Resource
    private BlankService blkService;

    //管理员身份
    //查询所有填空题
    @RequestMapping("/AshowAllBlank")
    public ModelAndView AshowAllBlank() {
        ModelAndView view = new ModelAndView();
        List<Blank> list = blkService.selectAllBlk();
        view.addObject("list", list);
        view.setViewName("AshowBlank");
        return view;
    }

    //删除填空题
    @RequestMapping(value = "/AdeleteBlank/{id}")
    public String AdeleteBlank(@PathVariable("id") int id) {
        if (blkService.deleteBlkById(id)) {
            return "redirect:/blank/AshowAllBlank";//删除成功
        } else {
            return "redirect:/blank/AshowAllBlank";//删除失败
        }
    }

    //跳转至修改页面
    @RequestMapping(value = "/AupdateBlank/{id}")
    public String AupdateBlank(@PathVariable("id") int id, Model model) {
        Blank cho = this.blkService.selectBlkById(id);
        model.addAttribute("blk", cho);
        return "AupdateBlank";
    }

    //修改填空题
    @RequestMapping(value = "/AupdateBlank/updateBlkCom")
    public String AupdateTchCom(@RequestParam("id") String id,
                               @RequestParam("sbjt") String sbjt,
                               @RequestParam("topic") String topic,
                               @RequestParam("stem") String stem,
                               @RequestParam("answer") String answer,
                               Blank blk) {
        //System.out.println(userName);
        Blank blk1 = this.blkService.selectBlkById(Integer.parseInt(id));
        String sbjt1 = blk1.getSubj();
        String topic1 = blk1.getTopic();
        String stem1 = blk1.getStem();
        String answer1 = blk1.getAnswer();
        if (!blkService.blkStemExist(stem)) {
            blk.setStem(stem1);
            blk.setSubj(sbjt);
            blk.setTopic(topic);
            blk.setAnswer(answer);
            blkService.updateBlank(blk);
            return "redirect:/blank/AshowAllBlank";//仅题干不变
            //return "redirect:/choice/showAllChoice";//全不变
        }else{
            blk.setStem(stem);
            blk.setSubj(sbjt);
            blk.setTopic(topic);
            blk.setAnswer(answer);
            blkService.updateBlank(blk);
            return "redirect:/blank/AshowAllBlank";//全变
        }
    }

    //跳转至添加页面
    @RequestMapping("/AaddBlank")
    public String AaddChoice() {
        return "AaddBlank";
    }

    //添加单选题
    @RequestMapping("/AaddBlank/addBlkCom")
    public String AaddCom(@RequestParam("sbjt1") String sbjt,
                         @RequestParam("topic1") String topic,
                         @RequestParam("stem1") String stem,
                         @RequestParam("answer1") String answer,
                         Blank blk) {
        //System.out.println(stem);
        if (blkService.blkStemExist(stem)) {
            int blkid = blkService.selectMaxId()+1;
            blk.setBlkid(blkid);
            blk.setStem(stem);
            blk.setSubj(sbjt);
            blk.setTopic(topic);
            blk.setAnswer(answer);
            blkService.addBlank(blk);
            return "redirect:/blank/AshowAllBlank";//添加成功
        } else {
            return "redirect:/blank/AshowAllBlank";//添加失败
        }
    }

    //添加的题干是否存在
    @RequestMapping("/Aaddblank/blkIdExist")
    public void validateUser(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        String stem = request.getParameter("stem");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        out = response.getWriter();
        if(blkService.blkStemExist(stem)){
            out.println("success");
        }else{
            out.println("error");
        }
    }
    //查询单个学生（修改用）
    @ResponseBody
    @RequestMapping(value = "/AupdtBlank/getid", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public Blank getCho(int blkid){
        Blank blk = blkService.selectBlkById(blkid);
        return  blk;
    }
    //修改学生
    @RequestMapping(value = "/AupdateBlank/updateblkCom1")
    public String updateStuCom1(@RequestParam("id2") String id,
                                @RequestParam("sbjt2") String sbjt,
                                @RequestParam("topic2") String topic,
                                @RequestParam("stem2") String stem,
                                @RequestParam("answer2") String answer,
                                Blank blk) {
        Blank blk1 = this.blkService.selectBlkById(Integer.parseInt(id));
        String blkid1 = blk1.getBlkid().toString();
        String sbjt1 = blk1.getSubj();
        String topic1 = blk1.getTopic();
        String stem1 = blk1.getStem();
        String answer1 = blk1.getAnswer();

        blk.setId(Integer.parseInt(id));
        blk.setBlkid(Integer.parseInt(blkid1));
        blk.setSubj(sbjt);
        blk.setTopic(topic);
        blk.setStem(stem1);
        blk.setAnswer(answer);
        blkService.updateBlank(blk);
        return "redirect:/blank/AshowAllBlank";//学号不变，其他变或全不变
    }


    //教师身份
    //查询所有填空题
    @RequestMapping("/TshowAllBlank")
    public ModelAndView TshowAllBlank() {
        ModelAndView view = new ModelAndView();
        List<Blank> list = blkService.selectAllBlk();
        view.addObject("list", list);
        view.setViewName("TshowBlank");
        return view;
    }

    //删除填空题
    @RequestMapping(value = "/TdeleteBlank/{id}")
    public String TdeleteBlank(@PathVariable("id") int id) {
        if (blkService.deleteBlkById(id)) {
            return "redirect:/blank/TshowAllBlank";//删除成功
        } else {
            return "redirect:/blank/TshowAllBlank";//删除失败
        }
    }

    //跳转至修改页面
    @RequestMapping(value = "/TupdateBlank/{id}")
    public String TupdateBlank(@PathVariable("id") int id, Model model) {
        Blank cho = this.blkService.selectBlkById(id);
        model.addAttribute("blk", cho);
        return "TupdateBlank";
    }

    //修改填空题
    @RequestMapping(value = "/TupdateBlank/updateBlkCom")
    public String TupdateTchCom(@RequestParam("id") String id,
                               @RequestParam("sbjt") String sbjt,
                               @RequestParam("topic") String topic,
                               @RequestParam("stem") String stem,
                               @RequestParam("answer") String answer,
                               Blank blk) {
        //System.out.println(userName);
        Blank blk1 = this.blkService.selectBlkById(Integer.parseInt(id));
        String sbjt1 = blk1.getSubj();
        String topic1 = blk1.getTopic();
        String stem1 = blk1.getStem();
        String answer1 = blk1.getAnswer();
        if (!blkService.blkStemExist(stem)) {
            blk.setStem(stem1);
            blk.setSubj(sbjt);
            blk.setTopic(topic);
            blk.setAnswer(answer);
            blkService.updateBlank(blk);
            return "redirect:/blank/TshowAllBlank";//仅题干不变
            //return "redirect:/choice/showAllChoice";//全不变
        }else{
            blk.setStem(stem);
            blk.setSubj(sbjt);
            blk.setTopic(topic);
            blk.setAnswer(answer);
            blkService.updateBlank(blk);
            return "redirect:/blank/TshowAllBlank";//全变
        }
    }

    //跳转至添加页面
    @RequestMapping("/TaddBlank")
    public String TaddChoice() {
        return "TaddBlank";
    }

    //添加单选题
    @RequestMapping("/TaddBlank/addBlkCom")
    public String TaddCom(@RequestParam("sbjt1") String sbjt,
                         @RequestParam("topic1") String topic,
                         @RequestParam("stem1") String stem,
                         @RequestParam("answer1") String answer,
                         Blank blk) {
        //System.out.println(stem);
        if (blkService.blkStemExist(stem)) {
            int blkid = blkService.selectMaxId()+1;
            blk.setBlkid(blkid);
            blk.setStem(stem);
            blk.setSubj(sbjt);
            blk.setTopic(topic);
            blk.setAnswer(answer);
            blkService.addBlank(blk);
            return "redirect:/blank/TshowAllBlank";//添加成功
        } else {
            return "redirect:/blank/TshowAllBlank";//添加失败
        }
    }
    //添加的题干是否存在
    @RequestMapping("/Taddblank/blkIdExist")
    public void validateUser1(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        String stem = request.getParameter("stem");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        out = response.getWriter();
        if(blkService.blkStemExist(stem)){
            out.println("success");
        }else{
            out.println("error");
        }
    }
    //查询单个学生（修改用）
    @ResponseBody
    @RequestMapping(value = "/TupdtBlank/getid", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public Blank getCho1(int blkid){
        Blank blk = blkService.selectBlkById(blkid);
        return  blk;
    }
    //修改学生
    @RequestMapping(value = "/TupdateBlank/updateblkCom1")
    public String updateStuCom(@RequestParam("id2") String id,
                                @RequestParam("sbjt2") String sbjt,
                                @RequestParam("topic2") String topic,
                                @RequestParam("stem2") String stem,
                                @RequestParam("answer2") String answer,
                                Blank blk) {
        Blank blk1 = this.blkService.selectBlkById(Integer.parseInt(id));
        String blkid1 = blk1.getBlkid().toString();
        String sbjt1 = blk1.getSubj();
        String topic1 = blk1.getTopic();
        String stem1 = blk1.getStem();
        String answer1 = blk1.getAnswer();

        blk.setId(Integer.parseInt(id));
        blk.setBlkid(Integer.parseInt(blkid1));
        blk.setSubj(sbjt);
        blk.setTopic(topic);
        blk.setStem(stem1);
        blk.setAnswer(answer);
        blkService.updateBlank(blk);
        return "redirect:/blank/TshowAllBlank";//学号不变，其他变或全不变
    }
}
