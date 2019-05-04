package com.sy.controller;

/**
 * Created by yangitano on 5/14/17.
 */

import com.sy.pojo.Blank;
import com.sy.pojo.Choice;
import com.sy.pojo.Wrong;
import com.sy.service.BlankService;
import com.sy.service.ChoiceService;
import com.sy.service.WrongService;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/wrong")
public class WrongController {

    @Resource
    private ChoiceService choService;
    @Resource
    private BlankService blkService;
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

    //按错题题型
    @RequestMapping("/errorT")
    public ModelAndView error1(HttpSession session) {
        ModelAndView view = new ModelAndView();
        String stuid = (String) session.getAttribute("userid");
        int choWngNum = wrongService.getWrongNumByStuid(Integer.parseInt(stuid), "cho","wrong");
        int blkWngNum = wrongService.getWrongNumByStuid(Integer.parseInt(stuid), "blk","wrong");
        view.addObject("choWngNum",choWngNum);
        view.addObject("blkWngNum",blkWngNum);
        view.setViewName("Student_error_type");
        return view;
    }

    //选择题
    @RequestMapping("/choice")
    public ModelAndView choWrongStart(HttpSession session) {
        String stuid = (String) session.getAttribute("userid");
        List<Wrong> wrongList = wrongService.selectByStuid(Integer.parseInt(stuid));
        List<Choice> choList = new ArrayList<Choice>();
        for (int i=0;i<wrongList.size();i++) {
            String questype = wrongList.get(i).getQuestype();
            if ("cho".equals(questype)) {
                String status = wrongList.get(i).getStatus();
                if ("wrong".equals(status)) {
                    for (int j = i + 1; j < wrongList.size(); j++) {
                        if (wrongList.get(i).getQuesid().equals(wrongList.get(j).getQuesid())) {
                            wrongList.remove(j);
                        }
                    }
                }
            }
        }
        for (int i=0;i<wrongList.size();i++) {
            String questype = wrongList.get(i).getQuestype();
            if ("cho".equals(questype)) {
                String status = wrongList.get(i).getStatus();
                if ("wrong".equals(status)) {
                    int quesid = wrongList.get(i).getQuesid();
                    choList.add(choService.selectByPrimaryKey(quesid));
                }
            }
        }
        session.setAttribute("choList",choList);
        ModelAndView view = new ModelAndView();
        view.addObject("choList",choList);
        view.setViewName("Student_errorC");
        return view;
    }

    //选择题评分
    @RequestMapping("/submitC")
    public ModelAndView choWrongRate(HttpSession session,
                                     HttpServletRequest request) {
        int a = 0;//cho回答数量
        int b = 0;//cho答对数量
        int c = 0;//cho总数量
        String stuid = (String) session.getAttribute("userid");
        List<Choice> choKeyList = (List<Choice>) session.getAttribute("choList");
        List<Choice> choCorrectList = new ArrayList<Choice>();
        c = choKeyList.size();
        Wrong wrong = new Wrong();
        for (int i=0;i<choKeyList.size();i++) {
            String choKey = choKeyList.get(i).getAnswer();
            String choAns = request.getParameter("ans"+i);
            if(choAns!=null){//cho作答
                a++;
                if(choKey.equals(choAns)){//cho答对
                    int quesid = choKeyList.get(i).getId();
                    wrong.setStuid(Integer.parseInt(stuid));
                    wrong.setQuesid(quesid);
                    wrong.setQuestype("cho");
                    wrong.setStatus("correct");
                    wrongService.updateByStuidQuesidSelective(wrong);
                    choCorrectList.add(choKeyList.get(i));
                    b++;
                }
            }
        }
        ModelAndView view = new ModelAndView();
        view.addObject("choCNum",b);
        view.addObject("choANum",a);
        view.addObject("choTNum",c);
        view.addObject("choCorrectList",choCorrectList);
        view.setViewName("Student_errorC_score");
        return view;
    }

    //填空题
    @RequestMapping("/blank")
    public ModelAndView blkWrongStart(HttpSession session) {
        String stuid = (String) session.getAttribute("userid");
        List<Wrong> wrongList = wrongService.selectByStuid(Integer.parseInt(stuid));
        List<Blank> blkList = new ArrayList<Blank>();
        for (int i=0;i<wrongList.size();i++) {
            String questype = wrongList.get(i).getQuestype();
            if ("blk".equals(questype)) {
                String status = wrongList.get(i).getStatus();
                if ("wrong".equals(status)) {
                    for (int j = i + 1; j < wrongList.size(); j++) {
                        if (wrongList.get(i).getQuesid().equals(wrongList.get(j).getQuesid())) {
                            wrongList.remove(j);
                        }
                    }
                }
            }
        }
        for (int i=0;i<wrongList.size();i++) {
            String questype = wrongList.get(i).getQuestype();
            if ("blk".equals(questype)) {
                String status = wrongList.get(i).getStatus();
                if ("wrong".equals(status)) {
                    int quesid = wrongList.get(i).getQuesid();
                    blkList.add(blkService.selectByPrimaryKey(quesid));
                }
            }
        }
        session.setAttribute("blkList",blkList);
        ModelAndView view = new ModelAndView();
        view.addObject("blkList",blkList);
        view.setViewName("Student_errorB");
        return view;
    }

    //填空题评分
    @RequestMapping("/submitB")
    public ModelAndView blkWrongRate(HttpSession session,
                                     HttpServletRequest request) {
        int a = 0;//blk回答数量
        int b = 0;//blk答对数量
        int c = 0;//blk总数量
        String stuid = (String) session.getAttribute("userid");
        List<Blank> blkKeyList = (List<Blank>) session.getAttribute("blkList");
        List<Blank> blkCorrectList = new ArrayList<Blank>();
        c = blkKeyList.size();
        Wrong wrong = new Wrong();
        for (int i=0;i<blkKeyList.size();i++) {
            String blkKey = blkKeyList.get(i).getAnswer();
            String blkAns = request.getParameter("ans"+i);
            if(!"".equals(blkAns)){//blk作答
                a++;
                if(blkKey.equals(blkAns)){//blk答对
                    int quesid = blkKeyList.get(i).getId();
                    wrong.setStuid(Integer.parseInt(stuid));
                    wrong.setQuesid(quesid);
                    wrong.setQuestype("blk");
                    wrong.setStatus("correct");
                    wrongService.updateByStuidQuesidSelective(wrong);
                    blkCorrectList.add(blkKeyList.get(i));
                    b++;
                }
            }
        }
        ModelAndView view = new ModelAndView();
        view.addObject("blkCNum",b);
        view.addObject("blkANum",a);
        view.addObject("blkTNum",c);
        view.addObject("blkCorrectList",blkCorrectList);
        view.setViewName("Student_errorB_score");
        return view;
    }

    //按错题章节
    @RequestMapping("errorS")
    public ModelAndView errorS(HttpSession session) {
        String stuid = (String) session.getAttribute("userid");
        List<Wrong> wnglist = wrongService.selectByStuid(Integer.parseInt(stuid));
        List<Integer> choNumList = new ArrayList<Integer>();
        List<Integer> blkNumList = new ArrayList<Integer>();
        List<String> choSbjtList = new ArrayList<String>();
        List<String> blkSbjtList = new ArrayList<String>();
        for (int i=0;i<wnglist.size();i++) {
            String questype = wnglist.get(i).getQuestype();
            if ("cho".equals(questype)) {
                if ("wrong".equals(wnglist.get(i).getStatus())) {
                    choNumList.add(wnglist.get(i).getQuesid());
                }
            } else if ("blk".equals(questype)) {
                if ("wrong".equals(wnglist.get(i).getStatus())) {
                    blkNumList.add(wnglist.get(i).getQuesid());
                }
            }
        }
        for (int i=0;i<choNumList.size();i++) {
            Choice cho = choService.selectByPrimaryKey(choNumList.get(i));
            choSbjtList.add(cho.getSbjt());
        }
        for (int i=0;i<blkNumList.size();i++) {
            Blank blk = blkService.selectByPrimaryKey(blkNumList.get(i));
            blkSbjtList.add(blk.getSubj());
        }
        List<String> sbjtlist = new ArrayList<String>();
        sbjtlist.addAll(choSbjtList);
        sbjtlist.addAll(blkSbjtList);
        sbjtlist = new ArrayList<String>(new LinkedHashSet(sbjtlist));
        ModelAndView view = new ModelAndView();
        view.addObject("sbjtlist",sbjtlist);
        view.setViewName("Student_error_sbjt");
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/getTopicBySbjt", method= RequestMethod.POST,produces="application/json;charset=UTF-8")
    public void selectTopicBySbjt(HttpServletResponse response,
                                  HttpSession session,
                                  String sbjt) throws IOException {
        String stuid = (String) session.getAttribute("userid");
        List<Wrong> wnglist = wrongService.selectByStuid(Integer.parseInt(stuid));
        List<Integer> choNumList = new ArrayList<Integer>();
        List<Integer> blkNumList = new ArrayList<Integer>();
        List<String> choTopicList = new ArrayList<String>();
        List<String> blkTopicList = new ArrayList<String>();
        for (int i=0;i<wnglist.size();i++) {
            String questype = wnglist.get(i).getQuestype();
            if ("cho".equals(questype)) {
                choNumList.add(wnglist.get(i).getQuesid());
            } else if ("blk".equals(questype)) {
                blkNumList.add(wnglist.get(i).getQuesid());
            }
        }
        for (int i=0;i<choNumList.size();i++) {
            if (sbjt.equals(choService.selectByPrimaryKey(choNumList.get(i)).getSbjt())) {
                String choTopic = choService.selectByPrimaryKey(choNumList.get(i)).getTopic();
                choTopicList.add(choTopic);
            }
        }
        for (int i=0;i<blkNumList.size();i++) {
            String blkTopic = blkService.selectByPrimaryKey(blkNumList.get(i)).getTopic();
            blkTopicList.add(blkTopic);
        }
        List listAll = new ArrayList();
        listAll.addAll(choTopicList);
        listAll.addAll(blkTopicList);
        listAll = new ArrayList(new LinkedHashSet(listAll));
        JSONArray json = JSONArray.fromObject(listAll);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(json.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }

    //开始按章节练习
    @RequestMapping("/startBySbjt")
    public ModelAndView startBySbjt(@RequestParam("sbjt1") String sbjt,
                                    @RequestParam("topic1") String topic,
                                    HttpSession session) {
        String stuid = (String) session.getAttribute("userid");
        List<Wrong> wnglist = wrongService.selectByStuid(Integer.parseInt(stuid));
        List<Integer> choNumList = new ArrayList<Integer>();
        List<Integer> blkNumList = new ArrayList<Integer>();
        List<Choice> cholist = new ArrayList<Choice>();
        List<Blank> blklist = new ArrayList<Blank>();
        for (int i=0;i<wnglist.size();i++) {
            String questype = wnglist.get(i).getQuestype();
            if ("cho".equals(questype)) {
                if ("wrong".equals(wnglist.get(i).getStatus())) {
                    choNumList.add(wnglist.get(i).getQuesid());
                }
            } else if ("blk".equals(questype)) {
                if ("wrong".equals(wnglist.get(i).getStatus())) {
                    blkNumList.add(wnglist.get(i).getQuesid());
                }
            }
        }
        choNumList = new ArrayList(new LinkedHashSet(choNumList));
        blkNumList = new ArrayList(new LinkedHashSet(blkNumList));
        for (int i=0;i<choNumList.size();i++) {
            Choice cho = choService.selectByPrimaryKey(choNumList.get(i));
            if (sbjt.equals(cho.getSbjt()) && topic.equals(cho.getTopic())) {
                cholist.add(cho);
            }
        }
        for (int i=0;i<blkNumList.size();i++) {
            Blank blk = blkService.selectByPrimaryKey(blkNumList.get(i));
            if (sbjt.equals(blk.getSubj()) && topic.equals(blk.getTopic())) {
                blklist.add(blk);
            }
        }
        session.setAttribute("cholist",cholist);
        session.setAttribute("blklist",blklist);
        ModelAndView view = new ModelAndView();
        view.addObject("sbjt",sbjt);
        view.addObject("topic",topic);
        view.addObject("cholist",cholist);
        view.addObject("blklist",blklist);
        view.setViewName("Student_error_sbjt_start");
        return view;
    }

    //按章节练习错题评分
    @RequestMapping("SbmtWngBysbjt")
    public ModelAndView SbmtWngBysbjt(HttpSession session,
                                      HttpServletRequest request) {
        String stuid = (String) session.getAttribute("userid");
        List<Choice> choiceKeyList = (List<Choice>) session.getAttribute("cholist");
        List<Blank> blankKeyList = (List<Blank>) session.getAttribute("blklist");
        List<Choice> choCorrectList = new ArrayList<Choice>();
        List<Blank> blkCorrectList = new ArrayList<Blank>();
        Wrong wrong = new Wrong();
        int total = choiceKeyList.size()+blankKeyList.size();//总数量
        int a = 0;//cho回答数量
        int b = 0;//blk回答数量
        int c = 0;//cho答对数量
        int d = 0;//blk答对数量
        for (int i=0;i<choiceKeyList.size();i++) {
            String choKey = choiceKeyList.get(i).getAnswer();
            String choAns = request.getParameter("choans"+i);
            if(choAns!=null) {//cho作答
                a++;
                if (choKey.equals(choAns)) {//cho答对
                    int quesid = choiceKeyList.get(i).getId();
                    wrong.setStuid(Integer.parseInt(stuid));
                    wrong.setQuesid(quesid);
                    wrong.setQuestype("cho");
                    wrong.setStatus("correct");
                    wrongService.updateByStuidQuesidSelective(wrong);
                    choCorrectList.add(choiceKeyList.get(i));
                    c++;
                }
            }
        }
        for (int i=0;i<blankKeyList.size();i++) {
            String blkKey = blankKeyList.get(i).getAnswer();
            String blkAns = request.getParameter("blkans"+i);
            if(!"".equals(blkAns)) {
                b++;
                if (blkKey.equals(blkAns)) {
                    int quesid = blankKeyList.get(i).getId();
                    wrong.setStuid(Integer.parseInt(stuid));
                    wrong.setQuesid(quesid);
                    wrong.setQuestype("blk");
                    wrong.setStatus("correct");
                    wrongService.updateByStuidQuesidSelective(wrong);
                    blkCorrectList.add(blankKeyList.get(i));
                    d++;
                }
            }
        }
        ModelAndView view = new ModelAndView();
        view.addObject("total",total);
        view.addObject("response",a+b);
        view.addObject("correct",c+d);
        view.addObject("choCorrectList",choCorrectList);
        view.addObject("blkCorrectList",blkCorrectList);
        view.setViewName("Student_error_sbjt_score");
        return view;
    }
}
