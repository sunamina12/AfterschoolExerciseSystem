package com.sy.service.impl;

/**
 * Created by 板野洋洋 on 2017/2/28.
 */
import javax.annotation.Resource;

import com.sy.service.StudentService;
import org.springframework.stereotype.Service;

import com.sy.dao.StudentMapper;
import com.sy.pojo.Student;

import java.util.List;

@Service("stuService")
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper stuDao;
    //按ID查询学生
    @Override
    public Student selectStuById(int id){
        return this.stuDao.selectByPrimaryKey(id);
    }
    //查询全部学生
    @Override
    public List selectAllStudent(){
        List l = this.stuDao.selectAllStudent();
        return l;
    }
    //修改学生
    @Override
    public void updateStudent(Student stu){
        int d = stuDao.updateByPrimaryKey(stu);
    }
    //删除学生
    @Override
    public boolean deleteStudent(int id){
        if(stuDao.deleteByPrimaryKey(id)>0){
            return true;
        }else{
            return false;
        }
    }
    //添加学生
    @Override
    public void addStudent(Student stu){
        int d = stuDao.insert(stu);
    }
    //检查学号是否存在
    @Override
    public boolean stuIdExist(int stuId){
        if(stuDao.stuIdExist(stuId)>0){
            return false;
        }else{
            return true;
        }
    }
    //检查密码是否正确
    @Override
    public boolean stuPwdExist(String stuPwd, int stuId){
        if(stuDao.stuPwdExist(stuPwd,stuId)>0){
            return true;
        }else{
            return false;
        }
    }
    //注册是否成功
    @Override
    public boolean regiStudent(Student stu){
        int d = stuDao.insert(stu);
        if(d>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Student selectByStuId(int stuid){ return this.stuDao.selectByStuId(stuid); }

    @Override
    public List selectAllClassId() { return this.stuDao.selectAllClassId(); }

    @Override
    public boolean updateByPrimaryKeySelective(Student record) {
        int a = stuDao.updateByPrimaryKeySelective(record);
        if (a>0) {
            return true;
        } else {
            return false;
        }
    }
}
