package com.sy.controller;

/**
 * Created by 板野洋洋 on 2017/3/2.
 */
import com.sy.pojo.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.sy.service.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Resource
    private ChoiceService choService;
    @Resource
    private BlankService blkService;
    @Resource
    private WrongService wrongService;
    @Resource
    private AssignmentService asgnService;

    //进入学生页面
    @RequestMapping("/home")
    public ModelAndView adminhome(HttpServletRequest request,
                                  HttpSession session) {
        String stuid = (String) request.getSession().getAttribute("userid");
        Student stu = (Student) request.getSession().getAttribute("student");
        List<Wrong> wnglist = wrongService.selectByStuid(Integer.parseInt(stuid));
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
        int unfinished = asgnService.selectByClassId(Integer.parseInt(stu.getClassid())).size() - wrongService.finishedNum(Integer.parseInt(stuid));
        ModelAndView view = new ModelAndView();
        view.addObject("unfinished",unfinished);
        view.addObject("choSbjtlist",choSbjtlist);
        view.addObject("choNumlist",choNumlist);
        view.addObject("blkSbjtlist",blkSbjtlist);
        view.addObject("blkNumlist",blkNumlist);
        view.setViewName("Student_home");
        return view;
    }

    //进入课后作业页面
    @RequestMapping("/assignment")
    public String assignment() {
        return "redirect:/SAsgn/SshowAllAssignment";
    }

    //进入成绩查看页面
    @RequestMapping("/point")
    public String point() {
        return "redirect:/score/SshowAllScore";
    }

    //进入自主练习页面
    @RequestMapping("/practiceT")
    public ModelAndView practice1() {
        ModelAndView view = new ModelAndView();
        int QtyC = choService.getQuantity();
        int QtyB = blkService.getQuantity();
        view.addObject("QtyC", QtyC);
        view.addObject("QtyB", QtyB);
        view.setViewName("Student_practiceT");
        return view;
    }
    @RequestMapping("/practiceS")
    public ModelAndView practice2() {
        ModelAndView view = new ModelAndView();
        List<String> choSbjt = choService.selectAllSbjt();
        List<String> blkSbjt = blkService.selectAllSbjt();
        List<String> sbjtlist = new ArrayList();
        sbjtlist.addAll(choSbjt);
        sbjtlist.addAll(blkSbjt);
        sbjtlist = new ArrayList<String>(new LinkedHashSet(sbjtlist));
        view.addObject("sbjtlist",sbjtlist);
        view.setViewName("Student_practiceS");
        return view;
    }

    //进入错题练习页面
    @RequestMapping("/errorT")
    public String error1() { return "redirect:/wrong/errorT"; }
    @RequestMapping("/errorS")
    public String error2() { return "Student_errorS"; }
}
