/**
 * Created by 板野洋洋 on 2017/2/28.
 */
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.sy.pojo.*;
import com.sy.service.*;

import java.lang.reflect.Array;
import java.util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})

public class TestMyBatis {
    private static Logger logger = Logger.getLogger(TestMyBatis.class);
    //  private ApplicationContext ac = null;
    @Resource
    private StudentService stuService = null;
    @Resource
    private TeacherService tchService = null;
    @Resource
    private ChoiceService choService = null;
    @Resource
    private AdminService adminService = null;
    @Resource
    private BlankService blkService = null;
    @Resource
    private  AssignmentService asgnService = null;
    @Resource
    private AsgnQuesService asqsService = null;
    @Resource
    private ScoreService scoreService = null;
    @Resource
    private WrongService wrongService = null;

//  @Before
//  public void before() {
//      ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//      userService = (IUserService) ac.getBean("userService");
//  }

    //按ID查询学生
    @Test
    public void testGetOneStudent() {
        Student stu = stuService.selectStuById(1);
        // System.out.println(user.getUserName());
        // logger.info("值："+user.getUserName());
        logger.info(JSON.toJSONString(stu));
    }
    //查询所有学生
    @Test
    public void testGetAllStudent() {
        List<Student> list = stuService.selectAllStudent();
        for(int i=0;i<list.size();i++)
        {
            Student stu = list.get(i);
            System.out.println(stu.getStuid());
        }
    }
    //检查学生是否存在
    @Test
    public void testStuIdExist() {
        if(stuService.stuIdExist(20131681)){
            System.out.println("学号不重复");
        }else{

            System.out.println("学号重复");
        }
    }
    //查询所有教师
    @Test
    public void testGetAllTeacher(){
        List<Teacher> list = tchService.selectAllTeacher();
        for(int i=0;i<list.size();i++)
        {
            Teacher tch = list.get(i);
            System.out.println(tch.getTchname());
        }
    }
    //查询所有单选题
    @Test
    public void testGetAllCho(){
        List<Choice> list = choService.selectAllCho();
        for(int i=0;i<list.size();i++)
        {
            Choice cho = list.get(i);
            System.out.println(cho.getStem());
        }
    }
    //查询教师工号密码是否匹配
    @Test
    public void testTchIdPwdMatch(){
        if(tchService.tchPwdExist("123",122)){
            System.out.println("工号密码匹配成功");
        }else{
            System.out.println("工号密码匹配失败");
        }
    }
    //查询学生学号密码是否匹配
    @Test
    public void testStuIdPwdMatch(){
        if(stuService.stuPwdExist("123",20131680)){
            System.out.println("学号密码匹配成功");
        }else{
            System.out.println("学号密码匹配失败");
        }
    }
    //查询管理员号密码是否匹配
    @Test
    public void testAdminIdPwdMatch(){
        if(adminService.adminPwdExist("123",20110001)){
            System.out.println("管理员号密码匹配成功");
        }else{
            System.out.println("管理员号密码匹配失败");
        }
    }
    //查询最大选择题id
    @Test
    public void selectMaxchoId(){
        int a = choService.selectMaxId();
        System.out.println(a);
    }
    //查询最大填空题id
    @Test
    public void selectMaxblkId(){
        int a = blkService.selectMaxId();
        System.out.println(a);
    }

    //从题库中随机抽取一定数量的题目的序号
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
    @Test
    public void tryM() {
        int[] b = {2,4,7,9,3,5};
        int[] a = selectM(b, 6);
        for (int i = 0;i<a.length;i++){
            System.out.print(" "+a[i]);
        }
    }
    @Test
    public void tryRam() {
        int[] a = getRamSeq(10,5);
        for (int i = 0;i<a.length;i++){
            System.out.print(" "+a[i]);
        }
    }

    //题目数量
    @Test
    public void getChoQuantity() {
        int a = choService.getQuantity();
        System.out.println(a);
    }
    @Test
    public void getBlkQuantity() {
        int a = blkService.getQuantity();
        System.out.println(a);
    }

    @Test
    public void num(){
        List<Choice> list = choService.selectAllCho();
        int[] a = new int[list.size()];
        for(int i=0;i<list.size();i++)
        {
            Choice cho = list.get(i);
            a[i] = Integer.parseInt(cho.getId().toString());
        }
        System.out.println(Arrays.toString(a));
        int b = choService.getQuantity();
        int[] c = selectM(a,b);
        System.out.println(Arrays.toString(c));
    }

    @Test
    public void admin(){
        Admin admin = adminService.selectByAdminId(20110001);
        String adminname = admin.getAdminname();
        System.out.print(adminname);
    }
    @Test
    public void stu(){
        Student stu = stuService.selectByStuId(20131680);
        String stuname = stu.getStuname();
        System.out.println(stuname);
    }
    @Test
    public void tch(){
        Teacher tch = tchService.selectByTchId(20110002);
        String tchname = tch.getTchname();
        System.out.println(tchname);
    }

    @Test
    public void AsgnList() {
        List<Assignment> list = asgnService.selectAll();
        for(int i=0;i<list.size();i++)
        {
            Assignment asgn = list.get(i);
            System.out.println(asgn.getDeadline());
        }
    }

    @Test
    public void AsgnListByUploader() {
        List<Assignment> list = asgnService.selectByUploader(20110001);
        for(int i=0;i<list.size();i++)
        {
            Assignment asgn = list.get(i);
            System.out.println(asgn.getAsgnid());
        }
    }
    @Test
    public void AsgnListByClassId() {
        List<Assignment> list = asgnService.selectByClassId(2013221);
        for(int i=0;i<list.size();i++)
        {
            Assignment asgn = list.get(i);
            System.out.println(asgn.getAsgnid());
        }
    }

    @Test
    public void AsgnById() {
        Assignment asgn = asgnService.selectById(1);
        System.out.println(asgn.getDeadline());
    }

    @Test
    public void classid() {
        List list = stuService.selectAllClassId();
        for(int i=0;i<list.size();i++)
        {
            System.out.println(list.get(i));
        }
    }

    @Test
    public void seleceSbjt() {
        List l1 = choService.selectAllSbjt();
        List l2 = blkService.selectAllSbjt();
        System.out.println("cho:");
        System.out.println(l1);
        System.out.println("blk:");
        System.out.println(l2);
        System.out.println("all:");
        List listAll = new ArrayList();
        listAll.addAll(l1);
        listAll.addAll(l2);
        listAll = new ArrayList(new LinkedHashSet(listAll));
        System.out.println(listAll);
    }

    @Test
    public void selectTopicBySbjt() {
        List l1 = choService.selectTopicBySbjt("C#");
        List l2 = blkService.selectTopicBySbjt(".Net");
        System.out.println("cho:");
        System.out.println(l1);
        System.out.println("blk:");
        System.out.println(l2);
        System.out.println("all:");
        List listAll = new ArrayList();
        listAll.addAll(l1);
        listAll.addAll(l2);
        listAll = new ArrayList(new LinkedHashSet(listAll));
        System.out.println(listAll);
    }

    @Test
    public void selectQuanByTopic() {
        int a = choService.selectQuanByTopic("C#", "基础");
        int b = blkService.selectQuanByTopic(".Net", "应用");
        System.out.println(a + " " + b);
    }

    @Test
    public void asgnIdExist() {
        if(asgnService.asgnIdExist(1)) {
            System.out.println("不存在");
        } else {
            System.out.println("存在");
        }
    }

    @Test
    public void selectAllId() {
        List a = choService.selectIdByST("C#","基础");
        for (int i=0;i<a.size();i++) {
            System.out.println(a.get(i));
        }
    }

    @Test
    public void selectAsgnByUploader() {
        List<Assignment> list = asgnService.selectAsgnByUploader("20110001");
        for (int i=0;i<list.size();i++) {
            Assignment asgn = list.get(i);
            System.out.println(asgn.getDeadline());
        }
    }

    @Test
    public void selectAllByAsgnid() {
        List<AsgnQues> list = asqsService.selectByAsgnid(1);
        for (int i=0;i<list.size();i++) {
            AsgnQues asqs = list.get(i);
            System.out.println(asqs.getQuesid()+" "+asqs.getQuestype());
        }
    }

    @Test
    public void selectByAsgnid() {
        Assignment asgn = asgnService.selectByAsgnid(1);
        System.out.println(asgn.getAsgntime());
    }

    @Test
    public void asgnIfFinished() {
        if (scoreService.asgnIfFinished(20131680,1)) {
            System.out.println("已完成");
        } else {
            System.out.println("其他情况");
        }
    }

    @Test
    public void selectAllScore() {
        List<Score> list = scoreService.selectAll();
        for (int i=0;i<list.size();i++) {
            System.out.println(list.get(i).getRate());
        }
    }

    @Test
    public void SselectAllScore() {
        List<Score> list = scoreService.selectByStuid(20131680);
        for (int i=0;i<list.size();i++) {
            System.out.println(list.get(i).getRate());
        }
    }

    @Test
    public void ATselectAllScore() {
        List<Score> list = scoreService.selectByAsgnid(5);
        for (int i=0;i<list.size();i++) {
            System.out.println(list.get(i).getRate());
        }
    }

    @Test
    public void selectByStuidAsgnid() {
        List<Wrong> list = wrongService.selectByStuidAsgnid(20131680, 1);
        for (int i=0;i<list.size();i++) {
            System.out.println(list.get(i).getUranswer());
        }
    }

    @Test
    public void getWrongNumByStuid() {
        int a = wrongService.getWrongNumByStuid(20131680, "blk","wrong");
        System.out.println(a);
    }

    @Test
    public void selectByStuid() {
        List<Wrong> list = wrongService.selectByStuid(20131680);
        for (int i=0;i<list.size();i++) {
            System.out.println(list.get(i).getQuestype());
        }
    }

    @Test
    public void updateByStuidQuesidSelective() {
        Wrong wrong = new Wrong();
        wrong.setStatus("wrong");
        wrong.setStuid(20131680);
        wrong.setQuesid(23);
        wrong.setQuestype("blk");
        wrongService.updateByStuidQuesidSelective(wrong);
    }

    @Test
    public void selectByST() {
        List<Blank> list = blkService.selectByST("C#","基础");
        for (int i=0;i<list.size();i++) {
            System.out.println(list.get(i).getStem());
        }
    }

    @Test
    public void dis() {
        List list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("b");
        list.add("c");
        list.add("a");
        list.add("a");
        list.add("a");

        System.out.println("\n统计每一个元素出现的频率");
        //将List转换为Set
        Set<String> uniqueSet = new HashSet<String>(list);
        for (String temp : uniqueSet) {
            System.out.println(temp + ": " + Collections.frequency(list, temp));
        }
    }

    @Test
    public void Distinct() {
        List<Wrong> wnglist = wrongService.selectByStuid(20131680);
        List<Choice> cholist = new ArrayList<Choice>();
        List<Blank> blklist = new ArrayList<Blank>();
        for (int i=0;i<wnglist.size();i++) {
            if ("cho".equals(wnglist.get(i).getQuestype())) {
                int choid = wnglist.get(i).getQuesid();
                cholist.add(choService.selectByPrimaryKey(choid));
            } else if ("blk".equals(wnglist.get(i).getQuestype())) {
                int blkid = wnglist.get(i).getQuesid();
                blklist.add(blkService.selectByPrimaryKey(blkid));
            }
        }
        for (Choice each:cholist) {
            System.out.println(each.getSbjt()+each.getTopic());
        }
        List<String> alllist = new ArrayList<String>();
        for (int i=0;i<cholist.size();i++) {
            String sbjt = cholist.get(i).getSbjt();
            for (int j=0;j<cholist.size();j++) {
                if (sbjt.equals(cholist.get(j).getSbjt())) {
                    alllist.add(sbjt+" "+cholist.get(j).getTopic());
                }
            }
        }
        alllist = new ArrayList<String>(new LinkedHashSet(alllist));
        System.out.println(alllist);
        /*Set<String> uniqueSet = new HashSet<String>(alllist);
        for (String temp : uniqueSet) {
            System.out.println(temp + ": " + Collections.frequency(alllist, temp));
        }
        alllist = new ArrayList<String>(new LinkedHashSet(alllist));
        System.out.println(alllist);*/
        int a=0;
        for (int i=0;i<alllist.size();i++) {
            String[] aa =alllist.get(i).split("\\s+");
            String sbjt = aa[0];
            String topic = aa[1];
            System.out.println("现在是："+sbjt+topic);
            for (int j=0;j<cholist.size();j++) {
                if (sbjt.equals(cholist.get(j).getSbjt()) && topic.equals(cholist.get(j).getTopic())) {
                    a++;
                }
            }
            /*for (Choice each:cholist) {
                System.out.println(each.getSbjt()+" "+each.getTopic());
            }*/
            System.out.println(sbjt+" "+topic+" "+a);
            a=0;
        }
    }

    @Test
    public void finishedNum() {
        System.out.println(wrongService.finishedNum(20131680));
    }
    @Test
    public void allwng() {
        List<Wrong> wnglist = wrongService.selectAll();
        for (Wrong each : wnglist) {
            System.out.println(each.getUranswer());
        }
    }
}
