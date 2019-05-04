package com.sy.service;

/**
 * Created by 板野洋洋 on 2017/3/1.
 */
import com.sy.pojo.Teacher;

import java.util.List;

public interface TeacherService {
    List selectAllTeacher();
    public boolean deleteTeacher(int id);
    public void addTeacher(Teacher tch);
    public boolean tchIdExist(int tchId);
    public void updateTeacher(Teacher tch);
    public Teacher selectTchById(int id);
    public boolean tchPwdExist(String tchPwd, int tchId);
    public Teacher selectByTchId(int tchid);
    public boolean updateTeacherSelective(Teacher teacher);
}
