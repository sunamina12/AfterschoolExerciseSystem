package com.sy.service.impl;

import com.sy.dao.WrongMapper;
import com.sy.pojo.Wrong;
import com.sy.service.WrongService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yangitano on 4/29/17.
 */

@Service("wrongService")
public class WrongServiceImpl implements WrongService {
    @Resource
    private WrongMapper wrongDao;

    @Override
    public void addWrong(Wrong wrong){ int a = this.wrongDao.insert(wrong); }

    @Override
    public List selectByStuidAsgnid(int stuId, int asgnId) { return this.wrongDao.selectByStuidAsgnid(stuId,asgnId); }

    @Override
    public boolean deleteByStuidAsgnid(int stuId, int asgnId) {
        if (wrongDao.deleteByStuidAsgnid(stuId,asgnId)>0) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int getWrongNumByStuid(int stuid, String questype, String status) { return this.wrongDao.getWrongNumByStuid(stuid,questype,status); }

    @Override
    public List selectByStuid(int stuid) { return this.wrongDao.selectByStuid(stuid); }

    @Override
    public int updateByStuidQuesidSelective(Wrong record) { return this.wrongDao.updateByStuidQuesidSelective(record); }

    @Override
    public boolean asgnIdExist(int asgnid) {
        if (wrongDao.asgnIdExist(asgnid)!=0) { return false; }
        else { return true; }
    }

    @Override
    public int finishedNum(int stuid) {
        return wrongDao.finishedNum(stuid);
    }

    @Override
    public List selectAll() {
        return wrongDao.selectAll();
    }
}
