package com.sy.service.impl;

import com.sy.dao.AssignmentMapper;
import com.sy.pojo.Assignment;
import com.sy.service.AssignmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yangitano on 4/15/17.
 */
@Service("AssignmentService")
public class AssignmentServiceImpl implements AssignmentService {
    @Resource
    private AssignmentMapper asgnDao;

    @Override
    public List selectAll() {
        List list = this.asgnDao.selectAll();
        return list;
    }

    @Override
    public List selectByUploader(int id) {
        List list = this.asgnDao.selectByUploader(id);
        return list;
    }

    @Override
    public List selectByClassId(int id) {
        List list = this.asgnDao.selectByClassId(id);
        return list;
    }

    @Override
    public boolean deleteById(int id) {
        if (asgnDao.deleteByPrimaryKey(id)>0) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Assignment selectById(int id) { return asgnDao.selectByPrimaryKey(id); }

    @Override
    public boolean asgnIdExist(int asgnid) {
        if (asgnDao.asgnIdExist(asgnid)!=0) { return false; }
        else { return true; }
    }

    @Override
    public void addAsgn(Assignment asgn) { int a = this.asgnDao.insert(asgn); }

    @Override
    public void updtAsgn(Assignment asgn) { int a = this.asgnDao.updateByPrimaryKey(asgn); }

    @Override
    public List selectAsgnByUploader(String uploader) {
        List list = this.asgnDao.selectAsgnByUploader(uploader);
        return  list;
    }

    @Override
    public Assignment selectByAsgnid(int asgnid) { return this.asgnDao.selectByAsgnid(asgnid); }
}
