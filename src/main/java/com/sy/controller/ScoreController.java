package com.sy.controller;

import com.sy.pojo.*;
import com.sy.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.*;

/**
 * Created by yangitano on 4/29/17.
 */

@Controller
@RequestMapping("/score")
public class ScoreController {
    @Resource
    private StudentService stuService;
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

    //student
    //showAllScore
    @RequestMapping("SshowAllScore")
    public ModelAndView SshowAllScore(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String stuid = (String) session.getAttribute("userid");
        ModelAndView view = new ModelAndView();
        List<Score> list = scoreService.selectByStuid(Integer.parseInt(stuid));
        view.addObject("list",list);
        view.setViewName("Student_point");
        return view;
    }

    //showDetail
    @RequestMapping("showDetail/{asgnid}")
    public ModelAndView showDetail (HttpServletResponse response, HttpSession session,
                                   @PathVariable("asgnid") int asgnid) {
        ModelAndView view = new ModelAndView();
        //
        Assignment asgn  = asgnService.selectByAsgnid(asgnid);
        //
        List<AsgnQues> list = asqsService.selectByAsgnid(asgnid);
        List choList = new ArrayList();
        List blkList = new ArrayList();
        Choice cho = new Choice();
        Blank blk = new Blank();
        for (int i=0;i<list.size();i++) {
            AsgnQues asqs = list.get(i);
            if ("cho".equals(asqs.getQuestype())) {
                cho = choService.selectChoById(asqs.getQuesid());
                choList.add(cho);
            } else if ("blk".equals(asqs.getQuestype())) {
                blk = blankService.selectBlkById(asqs.getQuesid());
                blkList.add(blk);
            }
        }
        //
        view.addObject("asgn",asgn);
        view.addObject("choList",choList);
        view.addObject("blkList",blkList);
        view.setViewName("Student_point_detail");
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/getStuAnswer", method= RequestMethod.POST,produces="application/json;charset=UTF-8")
    public void getStuAnswer(HttpServletResponse response, int stuid, int asgnid) throws IOException {
        response.setCharacterEncoding("UTF-8");
        List<Wrong> wrongList = wrongService.selectByStuidAsgnid(stuid,asgnid);
        //
        JSONArray json = JSONArray.fromObject(wrongList);
        response.getWriter().print(json.toString());
        response.getWriter().flush();
        response.getWriter().close();
        //
    }

    //admin
    //showAllScore
    @RequestMapping("AshowAllScore")
    public ModelAndView AshowAllScore() {
        ModelAndView view = new ModelAndView();
        List<Score> list = scoreService.selectAll();
        view.addObject("list",list);
        view.setViewName("AshowScore");
        return view;
    }

    //showDetail
    @RequestMapping("AshowDetail/{asgnid}/{stuid}")
    public ModelAndView AshowDetail (HttpServletResponse response, HttpSession session,
                                    @PathVariable("asgnid") int asgnid,
                                     @PathVariable("stuid") int stuid) {
        ModelAndView view = new ModelAndView();
        //
        Assignment asgn  = asgnService.selectByAsgnid(asgnid);
        Student stu = stuService.selectByStuId(stuid);
        //
        List<AsgnQues> list = asqsService.selectByAsgnid(asgnid);
        List choList = new ArrayList();
        List blkList = new ArrayList();
        Choice cho = new Choice();
        Blank blk = new Blank();
        for (int i=0;i<list.size();i++) {
            AsgnQues asqs = list.get(i);
            if ("cho".equals(asqs.getQuestype())) {
                cho = choService.selectChoById(asqs.getQuesid());
                choList.add(cho);
            } else if ("blk".equals(asqs.getQuestype())) {
                blk = blankService.selectBlkById(asqs.getQuesid());
                blkList.add(blk);
            }
        }
        //
        view.addObject("stuid",stuid);
        view.addObject("stu",stu);
        view.addObject("asgn",asgn);
        view.addObject("choList",choList);
        view.addObject("blkList",blkList);
        view.setViewName("AshowScoreDetail");
        return view;
    }

    //deleteScore
    @RequestMapping("AdeleteScore/{id}")
    public String AdeleteScore(@PathVariable("id") int id) {
        int stuid = scoreService.selectById(id).getStuid();
        int asgnid = scoreService.selectById(id).getAsgnid();
        if (scoreService.deleteById(id)) {
            if (wrongService.deleteByStuidAsgnid(stuid,asgnid)) {
                return "redirect:/score/AshowAllScore";
            } else { return "redirect:/score/AshowAllScore"; }
        } else { return "redirect:/score/AshowAllScore"; }
    }

    //teacher
    //showAllScore
    @RequestMapping("TshowAllScore")
    public ModelAndView TshowAllScore() {
        ModelAndView view = new ModelAndView();
        List<Score> list = scoreService.selectAll();
        view.addObject("list",list);
        view.setViewName("TshowScore");
        return view;
    }

    //showDetail
    @RequestMapping("TshowDetail/{asgnid}/{stuid}")
    public ModelAndView TshowDetail (HttpServletResponse response, HttpSession session,
                                     @PathVariable("asgnid") int asgnid,
                                     @PathVariable("stuid") int stuid) {
        ModelAndView view = new ModelAndView();
        //
        Assignment asgn  = asgnService.selectByAsgnid(asgnid);
        //
        List<AsgnQues> list = asqsService.selectByAsgnid(asgnid);
        List choList = new ArrayList();
        List blkList = new ArrayList();
        Choice cho = new Choice();
        Blank blk = new Blank();
        for (int i=0;i<list.size();i++) {
            AsgnQues asqs = list.get(i);
            if ("cho".equals(asqs.getQuestype())) {
                cho = choService.selectChoById(asqs.getQuesid());
                choList.add(cho);
            } else if ("blk".equals(asqs.getQuestype())) {
                blk = blankService.selectBlkById(asqs.getQuesid());
                blkList.add(blk);
            }
        }
        //
        view.addObject("stuid",stuid);
        view.addObject("asgn",asgn);
        view.addObject("choList",choList);
        view.addObject("blkList",blkList);
        view.setViewName("TshowScoreDetail");
        return view;
    }

    //queryScoreByStuid
    @RequestMapping("TshowSScore/{stuid}")
    public ModelAndView AshowSScore(HttpServletRequest request, HttpSession session,
                                    @PathVariable("stuid") int stuid) {
        ModelAndView view = new ModelAndView();
        session = request.getSession();
        String tchid = (String) session.getAttribute("userid");
        List<Score> list = scoreService.selectByStuid(stuid);
        List<Score> list1 = new ArrayList<Score>();
        for (int i=0;i<list.size();i++) {
            int asgnid = list.get(i).getAsgnid();
            String uploader = asgnService.selectByAsgnid(asgnid).getUploader();
            if (tchid.equals(uploader)) {
                list1.add(list.get(i));
            }
        }
        view.addObject("list",list1);
        view.setViewName("TshowScoreByStu");
        return view;
    }

    //queryDetail
    @RequestMapping("TshowDetail1/{stuid}/{asgnid}")
    public ModelAndView TshowDetail(@PathVariable("stuid") int stuid,
                                    @PathVariable("asgnid") int asgnid) {
        ModelAndView view = new ModelAndView();
        //
        Assignment asgn  = asgnService.selectByAsgnid(asgnid);
        Student stu = stuService.selectByStuId(stuid);
        //
        List<AsgnQues> list = asqsService.selectByAsgnid(asgnid);
        List choList = new ArrayList();
        List blkList = new ArrayList();
        Choice cho = new Choice();
        Blank blk = new Blank();
        for (int i=0;i<list.size();i++) {
            AsgnQues asqs = list.get(i);
            if ("cho".equals(asqs.getQuestype())) {
                cho = choService.selectChoById(asqs.getQuesid());
                choList.add(cho);
            } else if ("blk".equals(asqs.getQuestype())) {
                blk = blankService.selectBlkById(asqs.getQuesid());
                blkList.add(blk);
            }
        }
        //
        view.addObject("stuid",stuid);
        view.addObject("stu",stu);
        view.addObject("asgn",asgn);
        view.addObject("choList",choList);
        view.addObject("blkList",blkList);
        view.setViewName("TshowScoreDetail");
        return view;
    }

    @RequestMapping("TqueryByAsgnid/{asgnid}")
    public ModelAndView TqueryByAsgnid(@PathVariable("asgnid") int asgnid) {
        ModelAndView view = new ModelAndView();
        List<Score> list = scoreService.selectByAsgnid(asgnid);
        view.addObject("list",list);
        view.setViewName("TshowScoreByAsgnid");
        return view;
    }
}
