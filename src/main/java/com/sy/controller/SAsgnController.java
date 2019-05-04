package com.sy.controller;

import com.sy.pojo.*;
import com.sy.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by yangitano on 4/25/17.
 */

@Controller
@RequestMapping("/SAsgn")
public class SAsgnController {
    @Resource
    private AssignmentService asgnService;
    @Resource
    private ChoiceService choService;
    @Resource
    private BlankService blankService;
    @Resource
    private AsgnQuesService asqsService;
    @Resource
    private ScoreService scoreService;
    @Resource
    private WrongService wrongService;

    //输入Array，Num
    public static int[] selectM(int[] arr,int m){
        int len=arr.length;
        int[] res=new int[m];
        for(int i=0;i<m;i++){
            int randomIndex=len-1-new Random().nextInt(len-i);
            res[i]=arr[randomIndex];
            int tmp=arr[randomIndex];
            arr[randomIndex]=arr[i];
            arr[i]=tmp;
        }
        return res;
    }

    //SelectAllAssignment
    @RequestMapping("SshowAllAssignment")
    public ModelAndView AshowAllAssignment(HttpSession session) {
        Student stu = (Student) session.getAttribute("student");
        int classid = Integer.parseInt(stu.getClassid());
        ModelAndView view = new ModelAndView();
        List<Assignment> asgnlist = asgnService.selectByClassId(classid);
        view.addObject("list", asgnlist);
        view.setViewName("Student_assignment");
        return view;
    }

    //asgnIfFinished
    @RequestMapping(value = "asgnIfFinished")
    public void asgnIfFinished(HttpServletRequest request,
                               HttpServletResponse response,
                               String stuid, String asgnid,String i) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        out = response.getWriter();
        if (scoreService.asgnIfFinished(Integer.parseInt(stuid),Integer.parseInt(asgnid))) {
            out.println(i+"@finished");
        } else {
            out.println(i+"@other");
        }
    }

    //StartAssignment
    @RequestMapping(value = "/startAsgn/{asgnid}")
    public ModelAndView startAsgn(HttpSession session,
                                    @PathVariable("asgnid") int asgnid) {
        ModelAndView view = new ModelAndView();
        Assignment asgn = asgnService.selectByAsgnid(asgnid);
        List<AsgnQues> list = asqsService.selectByAsgnid(asgnid);
        List<Choice> choList = new ArrayList<Choice>();
        List<Blank> blkList = new ArrayList<Blank>();
        List<Choice> choList1 = new ArrayList<Choice>();
        List<Blank> blkList1 = new ArrayList<Blank>();
        int[] a = new int[asgn.getChonum()];
        int[] b = new int[asgn.getBlknum()];
        for (int i=0;i<list.size();i++) {
            AsgnQues asqs = list.get(i);
            if ("cho".equals(asqs.getQuestype())) {
                Choice cho = choService.selectChoById(asqs.getQuesid());
                choList.add(cho);
            } else if ("blk".equals(asqs.getQuestype())) {
                Blank blk = blankService.selectBlkById(asqs.getQuesid());
                blkList.add(blk);
            }
        }
        for (int i = 0;i<choList.size();i++) {
            Choice cho = choList.get(i);
            a[i] = Integer.parseInt(cho.getId().toString());
        }
        for (int i = 0;i<blkList.size();i++) {
            Blank blk = blkList.get(i);
            b[i] = Integer.parseInt(blk.getId().toString());
        }
        int[] a1 = this.selectM(a,asgn.getChonum());
        int[] b1 = this.selectM(b,asgn.getBlknum());
        for (int i=0;i<a1.length;i++) {
            Choice cho = choService.selectChoById(a1[i]);
            choList1.add(cho);
        }
        for (int i=0;i<b1.length;i++) {
            Blank blk = blankService.selectBlkById(b1[i]);
            blkList1.add(blk);
        }
        session.setAttribute("asgn",asgn);
        view.addObject("asgn",asgn);
        session.setAttribute("choList",choList1);
        session.setAttribute("blkList",blkList1);
        view.addObject("choList",choList1);
        view.addObject("blkList",blkList1);
        view.setViewName("Student_assignment_start");
        return view;
    }

    //RateAssignemnt
    @RequestMapping("/AsgnSbmt")
    public ModelAndView AsgnSbmt(HttpSession session,
                                 HttpServletRequest request,
                                 Score score, Wrong wrong) {
        String stuid = (String) session.getAttribute("userid");
        Assignment asgn = (Assignment) session.getAttribute("asgn");
        int total = asgn.getChonum()+asgn.getBlknum();//总数量
        int choNum = asgn.getChonum();
        int blkNum = asgn.getBlknum();
        int a = 0;//cho回答数量
        int b = 0;//blk回答数量
        int c = 0;//cho答对数量
        int d = 0;//blk答对数量
        List<Choice> choKeyList = (List<Choice>) session.getAttribute("choList");
        List<Blank> blkKeyList = (List<Blank>) session.getAttribute("blkList");
        for (int i=0;i<choKeyList.size();i++) {
            String choKey = choKeyList.get(i).getAnswer();
            String choAns = request.getParameter("choans"+i);
            if(choAns!=null){//cho作答
                a++;
                if(choKey.equals(choAns)){//cho答对
                    c++;
                } else {
                    wrong.setStuid(Integer.parseInt(stuid));
                    wrong.setAsgnid(asgn.getAsgnid());
                    wrong.setQuesid(choKeyList.get(i).getId());
                    wrong.setQuestype("cho");
                    wrong.setUranswer(choAns);
                    wrong.setStatus("wrong");
                    wrongService.addWrong(wrong);
                }
            } else {
                wrong.setStuid(Integer.parseInt(stuid));
                wrong.setAsgnid(asgn.getAsgnid());
                wrong.setQuesid(choKeyList.get(i).getId());
                wrong.setQuestype("cho");
                wrong.setUranswer("未作答");
                wrong.setStatus("wrong");
                wrongService.addWrong(wrong);
            }
        }
        for (int i=0;i<blkKeyList.size();i++) {
            String blkKey = blkKeyList.get(i).getAnswer();
            String blkAns = request.getParameter("blkans"+i);
            if(!"".equals(blkAns)){//blk作答
                b++;
                if(blkKey.equals(blkAns)){//blk答对
                    d++;
                } else {
                    wrong.setStuid(Integer.parseInt(stuid));
                    wrong.setAsgnid(asgn.getAsgnid());
                    wrong.setQuesid(blkKeyList.get(i).getId());
                    wrong.setQuestype("blk");
                    wrong.setUranswer(blkAns);
                    wrong.setStatus("wrong");
                    wrongService.addWrong(wrong);
                }
            } else {
                wrong.setStuid(Integer.parseInt(stuid));
                wrong.setAsgnid(asgn.getAsgnid());
                wrong.setQuesid(blkKeyList.get(i).getId());
                wrong.setQuestype("blk");
                wrong.setUranswer("未作答");
                wrong.setStatus("wrong");
                wrongService.addWrong(wrong);
            }
        }
        float point = c+d;
        point = point/total*100;
        Student stu = (Student) session.getAttribute("student");
        //
        score.setStuid(stu.getStuid());
        score.setAsgnid(asgn.getAsgnid());
        score.setChocnum(c);
        score.setChototal(choNum);
        score.setBlkcnum(d);
        score.setBlktotal(blkNum);
        score.setRate((int)point);
        scoreService.insert(score);
        //
        ModelAndView view = new ModelAndView();
        view.addObject("total",total);
        view.addObject("chototal",choNum);
        view.addObject("choAnswered",a);
        view.addObject("choCorrect",c);
        view.addObject("blktotal",blkNum);
        view.addObject("blkAnswered",b);
        view.addObject("blkCorrect",d);
        view.addObject("score",(int)point);
        view.setViewName("Student_assignment_score");
        return view;
    }

    //中途退出
    @RequestMapping("quit")
    public String quitAsgn(HttpSession session,
                           HttpServletRequest request,
                           Score score, Wrong wrong) {
        String stuid = (String) session.getAttribute("userid");
        Student stu = (Student) session.getAttribute("student");
        Assignment asgn = (Assignment) session.getAttribute("asgn");
        score.setStuid(stu.getStuid());
        score.setAsgnid(asgn.getAsgnid());
        score.setChocnum(0);
        score.setChototal(0);
        score.setBlkcnum(0);
        score.setBlktotal(0);
        score.setRate(0);
        scoreService.insert(score);
        //
        /*List<Choice> choKeyList = (List<Choice>) session.getAttribute("choList");
        List<Blank> blkKeyList = (List<Blank>) session.getAttribute("blkList");
        for (int i=0;i<choKeyList.size();i++) {
            wrong.setStuid(Integer.parseInt(stuid));
            wrong.setAsgnid(asgn.getAsgnid());
            wrong.setQuesid(choKeyList.get(i).getId());
            wrong.setQuestype("cho");
            wrong.setUranswer("");
            wrong.setStatus("wrong");
            wrongService.addWrong(wrong);
        }
        for (int i=0;i<blkKeyList.size();i++) {
            wrong.setStuid(Integer.parseInt(stuid));
            wrong.setAsgnid(asgn.getAsgnid());
            wrong.setQuesid(blkKeyList.get(i).getId());
            wrong.setQuestype("blk");
            wrong.setUranswer("");
            wrong.setStatus("wrong");
            wrongService.addWrong(wrong);
        }*/
        //
        return "redirect:/student/home";
    }
}
