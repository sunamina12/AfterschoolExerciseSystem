package com.sy.service;

import com.sy.pojo.Assignment;

import java.util.List;

/**
 * Created by yangitano on 4/15/17.
 */
public interface AssignmentService {
    List selectAll();
    List selectByUploader(int id);
    List selectByClassId(int id);
    List selectAsgnByUploader(String uploader);
    public boolean deleteById(int id);
    public Assignment selectById(int id);
    public boolean asgnIdExist(int asgnid);
    public void addAsgn(Assignment asgn);
    public void updtAsgn(Assignment asgn);
    public Assignment selectByAsgnid(int asgnid);
}
