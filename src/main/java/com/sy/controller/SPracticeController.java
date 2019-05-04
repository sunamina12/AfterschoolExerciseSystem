package com.sy.controller;

/**
 * Created by yangitano on 4/1/17.
 */

import com.sy.pojo.Blank;
import com.sy.pojo.Choice;
import com.sy.service.BlankService;
import com.sy.service.ChoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/practice")
public class SPracticeController {
    @Resource
    private ChoiceService choService;
    @Resource
    private BlankService blkService;

    //从题库中随机抽取一定数量的题目的序号
    //输入Num，Num
    public static int[] getRamSeq(int max, int no) {
        int[] sequence = new int[max];
        for (int i = 0; i < max; i++) {
            sequence[i] = i+1;
        }
        int len = sequence.length;
        int[] res = new int[no];
        for (int i = 0;i<no;i++){
            int randomIndex = len - 1 - new Random().nextInt(len-i);
            res[i]=sequence[randomIndex];
            int tmp = sequence[randomIndex];
            sequence[randomIndex]=sequence[i];
            sequence[i]=tmp;
        }
        return res;
    }
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

    //进行单选题练习
    @RequestMapping("/choice")
    public ModelAndView getChoice(ModelMap model, HttpSession session){
        ModelAndView view = new ModelAndView();
        List<Choice> list = choService.selectAllCho();
        int[] a = new int[list.size()];
        for(int i=0;i<list.size();i++)
        {
            Choice cho = list.get(i);
            a[i] = Integer.parseInt(cho.getId().toString());
        }
        int num = choService.getQuantity();
        int[] b = this.selectM(a,num);
        List<Choice> getCho = new ArrayList<Choice>();
        for (int i=0;i<b.length;i++){
            //System.out.println(i+": "+b[i]);
            Choice cho = choService.selectChoById(b[i]);
            getCho.add(cho);
            //System.out.println(getCho.get(i).getStem());
        }
        session.setAttribute("getCho",getCho);
        view.addObject("list", getCho);
        view.setViewName("Student_practiceC");
        return view;
    }

    //单选题练习评分
    @RequestMapping("/choice/submit")
    public ModelAndView choSbmt(HttpServletRequest request, HttpSession session){
        int a = choService.getQuantity();//总数量
        int b = 0;//共回答数量
        int c = 0;//共答对数量
        List<Choice> keylist = (List<Choice>)session.getAttribute("getCho");
        List<String> anslist = new ArrayList<String>();
        for (int i=0;i<keylist.size();i++){
            String anskey = keylist.get(i).getAnswer();//key
            String ans = request.getParameter("ans"+i);
            if(ans!=null){ b++; }
            if(anskey.equals(ans)){ c++; }
            //System.out.println("key："+anskey+" response："+ans);
            anslist.add(ans);
        }
        //System.out.println("total："+a+" answered："+b+" correct："+c);
        ModelAndView view = new ModelAndView();
        view.addObject("total",a);
        view.addObject("response",b);
        view.addObject("correct",c);
        view.setViewName("Student_practiceT_score");
        return view;
    }

    //进行填空题练习
    @RequestMapping("/blank")
    public ModelAndView getBlank(HttpSession session){
        ModelAndView view = new ModelAndView();
        List<Blank> list = blkService.selectAllBlk();
        int[] a = new int[list.size()];
        for (int i=0;i<list.size();i++){
            Blank blk = list.get(i);
            a[i] = Integer.parseInt(blk.getId().toString());
        }
        int num = blkService.getQuantity();
        int[] b = this.selectM(a,num);
        List<Blank> getBlk = new ArrayList<Blank>();
        for (int i=0;i<b.length;i++){
            Blank blk = blkService.selectBlkById(b[i]);
            getBlk.add(blk);
        }
        session.setAttribute("getBlk",getBlk);
        view.addObject("list",getBlk);
        view.setViewName("Student_practiceB");
        return view;
    }

    //填空题练习评分
    @RequestMapping("/blank/submit")
    public ModelAndView blkSbmt(HttpServletRequest request, HttpSession session){
        int a = blkService.getQuantity();
        int b = 0;
        int c = 0;
        List<Blank> keylist = (List<Blank>) session.getAttribute("getBlk");
        List<String> anslist = new ArrayList<String>();
        for (int i=0;i<keylist.size();i++){
            String anskey = keylist.get(i).getAnswer();
            String ans = request.getParameter("ans"+i);
            if(!"".equals(ans)){ b++; }
            if (anskey.equals(ans)){ c++; }
            anslist.add(ans);
        }
        ModelAndView view = new ModelAndView();
        view.addObject("total",a);
        view.addObject("response",b);
        view.addObject("correct",c);
        view.setViewName("Student_practiceT_score");
        return view;
    }

    //按章节练习
    @RequestMapping("/startBySbjt")
    public ModelAndView startBySbjt(@RequestParam("sbjt1") String sbjt,
                                    @RequestParam("topic1") String topic,
                                    HttpSession session) {
        List<Choice> choiceList = choService.selectByST(sbjt, topic);
        List<Blank> blankList = blkService.selectByST(sbjt, topic);
        session.setAttribute("choiceList", choiceList);
        session.setAttribute("blankList", blankList);
        ModelAndView view = new ModelAndView();
        view.addObject("choiceList", choiceList);
        view.addObject("blankList", blankList);
        view.setViewName("Student_practiceS_start");
        return view;
    }

    //章节练习评分
    @RequestMapping("/PracticeSbmt")
    public ModelAndView PracticeRate(HttpSession session,
                                     HttpServletRequest request) {
        List<Choice> choiceKeyList = (List<Choice>) session.getAttribute("choiceList");
        List<Blank> blankKeyList = (List<Blank>) session.getAttribute("blankList");
        int total = choiceKeyList.size()+blankKeyList.size();//总数量
        int choNum = choiceKeyList.size();
        int blkNum = blankKeyList.size();
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
                    d++;
                }
            }
        }
        ModelAndView view = new ModelAndView();
        view.addObject("total",total);
        view.addObject("response",a+b);
        view.addObject("correct",c+d);
        view.setViewName("Student_practiceS_score");
        return view;
    }
}
