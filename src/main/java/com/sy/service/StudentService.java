package com.sy.service;

/**
 * Created by 板野洋洋 on 2017/2/28.
 */
import com.sy.pojo.Student;

import java.util.List;

public interface StudentService {
    public Student selectStuById(int id);
    public List selectAllStudent();
    public void updateStudent(Student stu);
    public boolean deleteStudent(int id);
    public void addStudent(Student stu);
    public boolean regiStudent(Student stu);
    public boolean stuIdExist(int stuId);
    public boolean stuPwdExist(String stuPwd, int stuId);
    public Student selectByStuId(int stuid);
    public List selectAllClassId();
    public boolean updateByPrimaryKeySelective(Student record);
}
