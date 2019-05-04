package com.sy.service.impl;

/**
 * Created by 板野洋洋 on 2017/3/1.
 */
import javax.annotation.Resource;

import com.sy.service.StudentService;
import com.sy.service.TeacherService;
import org.springframework.stereotype.Service;

import com.sy.dao.TeacherMapper;
import com.sy.pojo.Teacher;

import java.util.List;

@Service("tchService")
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherMapper tchDao;
    //按ID查询学生
    @Override
    public Teacher selectTchById(int id){
        return this.tchDao.selectByPrimaryKey(id);
    }
    //查询全部教师
    @Override
    public List selectAllTeacher(){
        List l = this.tchDao.selectAllTeacher();
        return l;
    }
    //删除教师
    @Override
    public boolean deleteTeacher(int id){
        if(tchDao.deleteByPrimaryKey(id)>0){
            return true;
        }else{
            return false;
        }
    }
    //添加教师
    @Override
    public void addTeacher(Teacher tch){ int d = this.tchDao.insert(tch); }
    //检查工号是否存在
    @Override
    public boolean tchIdExist(int tchId){
        if(tchDao.tchIdExist(tchId)>0){
            return false;
        }else{
            return true;
        }
    }
    //修改教师
    @Override
    public void updateTeacher(Teacher tch){
        int d = tchDao.updateByPrimaryKey(tch);
    }
    //检查密码是否正确
    @Override
    public boolean tchPwdExist(String tchPwd, int tchId){
        if(tchDao.tchPwdExist(tchPwd,tchId)>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Teacher selectByTchId(int tchid) { return this.tchDao.selectByTchId(tchid); }

    @Override
    public boolean updateTeacherSelective(Teacher teacher) {
        if (tchDao.updateByPrimaryKeySelective(teacher)>0){
            return true;
        } else {
            return false;
        }
    }
}
