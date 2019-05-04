package com.sy.controller;
/**
 * Created by yangitano on 4/11/17.
 */

import com.sy.pojo.AsgnQues;
import com.sy.pojo.Blank;
import com.sy.pojo.Choice;
import com.sy.service.*;
import net.sf.json.*;
import com.sy.pojo.Assignment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/assignment")
public class AssignmentController {
    @Resource
    private AssignmentService asgnService;
    @Resource
    private ChoiceService choService;
    @Resource
    private BlankService blankService;
    @Resource
    private StudentService stuService;
    @Resource
    private AsgnQuesService asqsService;
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

    //Admin
    //SelectAllAssignment
    @RequestMapping("AshowAllAssignment")
    public ModelAndView AshowAllAssignment() {
        ModelAndView view = new ModelAndView();
        List<Assignment> asgnlist = asgnService.selectAll();
        view.addObject("list", asgnlist);
        //
        List<String> classlist = stuService.selectAllClassId();
        view.addObject("classlist", classlist);
        //
        List<String> choSbjt = choService.selectAllSbjt();
        List<String> blkSbjt = blankService.selectAllSbjt();
        List<String> sbjtlist = new ArrayList();
        sbjtlist.addAll(choSbjt);
        sbjtlist.addAll(blkSbjt);
        sbjtlist = new ArrayList<String>(new LinkedHashSet(sbjtlist));
        view.addObject("sbjtlist",sbjtlist);
        //
        view.setViewName("AshowAssignment");
        return view;
    }

    //DeleteAssignment
    @RequestMapping(value = "/AdeleteAssignment/{id}")
    public String AdeleteAssignment(@PathVariable("id") int id) {
        int asgnid = asgnService.selectById(id).getAsgnid();
        if (asgnService.deleteById(id) && asqsService.delQues(asgnid)) {
            return "redirect:/assignment/AshowAllAssignment";//删除成功
        } else {
            return "redirect:/assignment/AshowAllAssignment";//删除失败
        }
    }

    //find the info to be updated
    @ResponseBody
    @RequestMapping(value = "/AupdtAsgn/getid", method= RequestMethod.POST,produces="application/json;charset=UTF-8")
    public Assignment getAsgn(int asgnid) {
        Assignment asgn = asgnService.selectById(asgnid);
        return asgn;
    }

    @ResponseBody
    @RequestMapping(value = "/getTopicBySbjt", method= RequestMethod.POST,produces="application/json;charset=UTF-8")
    public void selectTopicBySbjt(HttpServletResponse response, String sbjt) throws IOException {
        response.setCharacterEncoding("UTF-8");
        List l1 = choService.selectTopicBySbjt(sbjt);
        List l2 = blankService.selectTopicBySbjt(sbjt);
        List listAll = new ArrayList();
        listAll.addAll(l1);
        listAll.addAll(l2);
        listAll = new ArrayList(new LinkedHashSet(listAll));
        JSONArray json = JSONArray.fromObject(listAll);
        response.getWriter().print(json.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }

    @ResponseBody
    @RequestMapping(value = "/getQuanByTopic", method= RequestMethod.POST,produces="application/json;charset=UTF-8")
    public void selectQuanByTopic(HttpServletRequest request, HttpServletResponse response,
                                          String sbjt, String topic) throws IOException {
        response.setCharacterEncoding("UTF-8");
        int choQuan = choService.selectQuanByTopic(sbjt,topic);
        int blkQuan = blankService.selectQuanByTopic(sbjt,topic);
        List list = new ArrayList();
        list.add(choQuan);
        list.add(blkQuan);
        //System.out.println(list);
        JSONArray json = JSONArray.fromObject(list);
        response.getWriter().print(json.toString());
        response.getWriter().flush();
        response.getWriter().close();
        HttpSession session = request.getSession();
        session.setAttribute("choQuan", choQuan);
        session.setAttribute("blkQuan", blkQuan);
    }

    @RequestMapping("/addAsgn/asgnIdExist")
    public void validateAsgnId(HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        int asgnid = Integer.parseInt(request.getParameter("asgnid"));
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        out = response.getWriter();
        if(asgnService.asgnIdExist(asgnid)&&wrongService.asgnIdExist(asgnid)) {
            out.print("success");
        } else {
            out.print("error");
        }
    }

    //添加作业
    @RequestMapping("/AaddAssignment/addAsgnCom")
    public String addAsgn(HttpServletRequest request, HttpSession session,
                          @RequestParam("id1") String id,
                          @RequestParam("sbjt1") String sbjt,
                          @RequestParam("topic1") String topic,
                          @RequestParam("classid1") String classid,
                          @RequestParam("choNum1") String chonum,
                          @RequestParam("blkNum1") String blknum,
                          @RequestParam("asgntime1") String asgntime,
                          @RequestParam("deadline1") String deadline,
                          Assignment asgn, AsgnQues asgnQues) {
        //挑选出选择题
        if (Integer.parseInt(chonum)>0) {
            List<Integer> choNumList = choService.selectIdByST(sbjt,topic);
            int[] a = new int[choNumList.size()];
            for (int i=0;i<choNumList.size();i++) {
                a[i] = choNumList.get(i);
            }
            int[] b = this.selectM(a,Integer.parseInt(chonum));
            for (int i=0;i<b.length;i++) {
                asgnQues.setAsgnid(Integer.parseInt(id));
                asgnQues.setQuesid(b[i]);
                asgnQues.setQuestype("cho");
                asqsService.addQues(asgnQues);
            }
        }
        //挑选出填空题
        if (Integer.parseInt(blknum)>0) {
            List<Integer> blkNumList = blankService.selectIdByST(sbjt, topic);
            int[] a1 = new int[blkNumList.size()];
            for (int i = 0; i < blkNumList.size(); i++) {
                a1[i] = blkNumList.get(i);
            }
            int[] b1 = this.selectM(a1, Integer.parseInt(blknum));
            for (int i = 0; i < b1.length; i++) {
                asgnQues.setAsgnid(Integer.parseInt(id));
                asgnQues.setQuesid(b1[i]);
                asgnQues.setQuestype("blk");
                asqsService.addQues(asgnQues);
            }
        }
        //
        String uploader = (String) session.getAttribute("userid");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date date=sdf.parse(deadline.toString());
            asgn.setDeadline(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        asgn.setAsgnid(Integer.parseInt(id));
        asgn.setSbjt(sbjt);
        asgn.setTopic(topic);
        asgn.setClassid(Integer.parseInt(classid));
        asgn.setChonum(Integer.parseInt(chonum));
        asgn.setBlknum(Integer.parseInt(blknum));
        asgn.setAsgntime(Integer.parseInt(asgntime));
        asgn.setUploader(uploader);
        asgnService.addAsgn(asgn);
        return "redirect:/assignment/AshowAllAssignment";
    }

    //修改作业
    @RequestMapping("AupdateAssignment/updateAsgnCom")
    public String updtAsgnCom(@RequestParam("ld2") String id,
                              @RequestParam("classid2") String classid,
                              @RequestParam("asgntime2") String asgntime,
                              @RequestParam("deadline2") String deadline,
                              Assignment asgn) {
        Assignment asgn1 = this.asgnService.selectById(Integer.parseInt(id));
        int asgnid1 = asgn1.getAsgnid();
        String sbjt1 = asgn1.getSbjt();
        String topic1 = asgn1.getTopic();
        int choNum1 = asgn1.getChonum();
        int blkNum1 = asgn1.getBlknum();
        String uploader1 = asgn1.getUploader();
        asgn.setId(Integer.parseInt(id));
        asgn.setAsgnid(asgnid1);
        asgn.setSbjt(sbjt1);
        asgn.setTopic(topic1);
        asgn.setClassid(Integer.parseInt(classid));
        asgn.setChonum(choNum1);
        asgn.setBlknum(blkNum1);
        asgn.setAsgntime(Integer.parseInt(asgntime));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date date=sdf.parse(deadline.toString());
            asgn.setDeadline(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        asgn.setUploader(uploader1);
        asgnService.updtAsgn(asgn);
        return "redirect:/assignment/AshowAllAssignment";
    }

    //showDetail
    @RequestMapping(value = "/AshowDetail/{asgnid}")
    public ModelAndView AshowDetail(@PathVariable("asgnid") int asgnid) {
        ModelAndView view = new ModelAndView();
        Assignment asgn = asgnService.selectByAsgnid(asgnid);
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
        view.addObject("asgn",asgn);
        view.addObject("choList",choList);
        view.addObject("blkList",blkList);
        view.setViewName("AshowAsgnDetail");
        return view;
    }


    //Teacher
    //SelectAllAssignment
    @RequestMapping("TshowAllAssignment")
    public ModelAndView TshowAllAssignment(HttpSession session) {
        String tchId = (String) session.getAttribute("userid");
        ModelAndView view = new ModelAndView();
        List<Assignment> asgnlist = asgnService.selectAsgnByUploader(tchId);
        view.addObject("list", asgnlist);
        //
        List<String> classlist = stuService.selectAllClassId();
        view.addObject("classlist", classlist);
        //
        List<String> choSbjt = choService.selectAllSbjt();
        List<String> blkSbjt = blankService.selectAllSbjt();
        List<String> sbjtlist = new ArrayList();
        sbjtlist.addAll(choSbjt);
        sbjtlist.addAll(blkSbjt);
        sbjtlist = new ArrayList<String>(new LinkedHashSet(sbjtlist));
        view.addObject("sbjtlist",sbjtlist);
        //
        view.setViewName("TshowAssignment");
        return view;
    }

    //DeleteAssignment
    @RequestMapping(value = "/TdeleteAssignment/{id}")
    public String TdeleteAssignment(@PathVariable("id") int id) {
        int asgnid = asgnService.selectById(id).getAsgnid();
        if (asgnService.deleteById(id) && asqsService.delQues(asgnid)) {
            return "redirect:/assignment/TshowAllAssignment";//删除成功
        } else {
            return "redirect:/assignment/TshowAllAssignment";//删除失败
        }
    }

    //添加作业
    @RequestMapping("/TaddAssignment/addAsgnCom")
    public String TaddAsgn(HttpServletRequest request, HttpSession session,
                          @RequestParam("id1") String id,
                          @RequestParam("sbjt1") String sbjt,
                          @RequestParam("topic1") String topic,
                          @RequestParam("classid1") String classid,
                          @RequestParam("choNum1") String chonum,
                          @RequestParam("blkNum1") String blknum,
                          @RequestParam("asgntime1") String asgntime,
                          @RequestParam("deadline1") String deadline,
                          Assignment asgn, AsgnQues asgnQues) {
        //挑选出选择题
        if (Integer.parseInt(chonum)>0) {
            List<Integer> choNumList = choService.selectIdByST(sbjt,topic);
            int[] a = new int[choNumList.size()];
            for (int i=0;i<choNumList.size();i++) {
                a[i] = choNumList.get(i);
            }
            int[] b = this.selectM(a,Integer.parseInt(chonum));
            for (int i=0;i<b.length;i++) {
                asgnQues.setAsgnid(Integer.parseInt(id));
                asgnQues.setQuesid(b[i]);
                asgnQues.setQuestype("cho");
                asqsService.addQues(asgnQues);
            }
        }
        //挑选出填空题
        if (Integer.parseInt(blknum)>0) {
            List<Integer> blkNumList = blankService.selectIdByST(sbjt, topic);
            int[] a1 = new int[blkNumList.size()];
            for (int i = 0; i < blkNumList.size(); i++) {
                a1[i] = blkNumList.get(i);
            }
            int[] b1 = this.selectM(a1, Integer.parseInt(blknum));
            for (int i = 0; i < b1.length; i++) {
                asgnQues.setAsgnid(Integer.parseInt(id));
                asgnQues.setQuesid(b1[i]);
                asgnQues.setQuestype("blk");
                asqsService.addQues(asgnQues);
            }
        }
        //
        String uploader = (String) session.getAttribute("userid");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date date=sdf.parse(deadline.toString());
            asgn.setDeadline(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        asgn.setAsgnid(Integer.parseInt(id));
        asgn.setSbjt(sbjt);
        asgn.setTopic(topic);
        asgn.setClassid(Integer.parseInt(classid));
        asgn.setChonum(Integer.parseInt(chonum));
        asgn.setBlknum(Integer.parseInt(blknum));
        asgn.setAsgntime(Integer.parseInt(asgntime));
        asgn.setUploader(uploader);
        asgnService.addAsgn(asgn);
        return "redirect:/assignment/TshowAllAssignment";
    }

    //修改作业
    @RequestMapping("TupdateAssignment/updateAsgnCom")
    public String TupdtAsgnCom(@RequestParam("ld2") String id,
                              @RequestParam("classid2") String classid,
                              @RequestParam("asgntime2") String asgntime,
                              @RequestParam("deadline2") String deadline,
                              Assignment asgn) {
        Assignment asgn1 = this.asgnService.selectById(Integer.parseInt(id));
        int asgnid1 = asgn1.getAsgnid();
        String sbjt1 = asgn1.getSbjt();
        String topic1 = asgn1.getTopic();
        int choNum1 = asgn1.getChonum();
        int blkNum1 = asgn1.getBlknum();
        String uploader1 = asgn1.getUploader();
        asgn.setId(Integer.parseInt(id));
        asgn.setAsgnid(asgnid1);
        asgn.setSbjt(sbjt1);
        asgn.setTopic(topic1);
        asgn.setClassid(Integer.parseInt(classid));
        asgn.setChonum(choNum1);
        asgn.setBlknum(blkNum1);
        asgn.setAsgntime(Integer.parseInt(asgntime));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date date=sdf.parse(deadline.toString());
            asgn.setDeadline(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        asgn.setUploader(uploader1);
        asgnService.updtAsgn(asgn);
        return "redirect:/assignment/TshowAllAssignment";
    }

    //showDetail
    @RequestMapping(value = "/TshowDetail/{asgnid}")
    public ModelAndView TshowDetail(@PathVariable("asgnid") int asgnid) {
        ModelAndView view = new ModelAndView();
        Assignment asgn = asgnService.selectByAsgnid(asgnid);
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
        view.addObject("asgn",asgn);
        view.addObject("choList",choList);
        view.addObject("blkList",blkList);
        view.setViewName("TshowAsgnDetail");
        return view;
    }
}
