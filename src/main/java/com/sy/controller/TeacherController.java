package com.sy.controller;

/**
 * Created by 板野洋洋 on 2017/3/2.
 */
import com.sy.pojo.Blank;
import com.sy.pojo.Choice;
import com.sy.pojo.Wrong;
import com.sy.service.AssignmentService;
import com.sy.service.BlankService;
import com.sy.service.ChoiceService;
import com.sy.service.WrongService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private ChoiceService choService;
    @Resource
    private BlankService blkService;
    @Resource
    private WrongService wrongService;
    @Resource
    private AssignmentService asgnService;

    // /进入教师页面
    @RequestMapping("/home")
    public ModelAndView adminhome() {
        List<Wrong> wnglist = wrongService.selectAll();
        List<Choice> cholist = new ArrayList<Choice>();
        List<Blank> blklist = new ArrayList<Blank>();
        List<String> choSbjtlist = new ArrayList<String>();
        List<String> blkSbjtlist = new ArrayList<String>();
        List<Integer> choNumlist = new ArrayList<Integer>();
        List<Integer> blkNumlist = new ArrayList<Integer>();
        for (int i=0;i<wnglist.size();i++) {
            if ("cho".equals(wnglist.get(i).getQuestype())) {
                int choid = wnglist.get(i).getQuesid();
                cholist.add(choService.selectByPrimaryKey(choid));
            } else if ("blk".equals(wnglist.get(i).getQuestype())) {
                int blkid = wnglist.get(i).getQuesid();
                blklist.add(blkService.selectByPrimaryKey(blkid));
            }
        }
        //选择题
        for (int i=0;i<cholist.size();i++) {
            String sbjt = cholist.get(i).getSbjt();
            for (int j=0;j<cholist.size();j++) {
                if (sbjt.equals(cholist.get(j).getSbjt())) {
                    choSbjtlist.add(sbjt+" "+cholist.get(j).getTopic());
                }
            }
        }
        choSbjtlist = new ArrayList<String>(new LinkedHashSet(choSbjtlist));
        int a=0;
        for (int i=0;i<choSbjtlist.size();i++) {
            String[] aa =choSbjtlist.get(i).split("\\s+");
            String sbjt = aa[0];
            String topic = aa[1];
            for (int j=0;j<cholist.size();j++) {
                if (sbjt.equals(cholist.get(j).getSbjt()) && topic.equals(cholist.get(j).getTopic())) {
                    a++;
                }
            }
            choNumlist.add(a);
            a=0;
        }
        //填空题
        for (int i=0;i<blklist.size();i++) {
            String sbjt = blklist.get(i).getSubj();
            for (int j=0;j<blklist.size();j++) {
                if (sbjt.equals(blklist.get(j).getSubj())) {
                    blkSbjtlist.add(sbjt+" "+blklist.get(j).getTopic());
                }
            }
        }
        blkSbjtlist = new ArrayList<String>(new LinkedHashSet(blkSbjtlist));
        int b=0;
        for (int i=0;i<blkSbjtlist.size();i++) {
            String[] aa =blkSbjtlist.get(i).split("\\s+");
            String sbjt = aa[0];
            String topic = aa[1];
            for (int j=0;j<blklist.size();j++) {
                if (sbjt.equals(blklist.get(j).getSubj()) && topic.equals(blklist.get(j).getTopic())) {
                    b++;
                }
            }
            blkNumlist.add(b);
            b=0;
        }
        ModelAndView view = new ModelAndView();
        view.addObject("choSbjtlist",choSbjtlist);
        view.addObject("choNumlist",choNumlist);
        view.addObject("blkSbjtlist",blkSbjtlist);
        view.addObject("blkNumlist",blkNumlist);
        view.setViewName("Teacher_home");
        return view;
    }

    //跳转至查看学生页面
    @RequestMapping("/stuManagement")
    public String stuManagement() {
        return "redirect:/Tstudent/showAllStudent";
    }

    //跳转至单选题管理页面
    @RequestMapping("/choManagement")
    public String choManagement() {
        return "redirect:/choice/TshowAllChoice";
    }

    //跳转至填空题管理页面
    @RequestMapping("/blkManagement")
    public String blkManagement() {
        return "redirect:/blank/TshowAllBlank";
    }

    //跳转至作业管理页面
    @RequestMapping("/assignment")
    public String assignment() { return "redirect:/assignment/TshowPerBlank"; }
}
