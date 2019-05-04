package com.sy.service.impl;

import com.sy.dao.ScoreMapper;
import com.sy.pojo.Score;
import com.sy.service.ScoreService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yangitano on 4/27/17.
 */
@Service("scoreService")
public class ScoreServiceImpl implements ScoreService {
    @Resource
    private ScoreMapper scoreDao;

    @Override
    public void insert(Score score) { int a = this.scoreDao.insert(score); }

    @Override
    public boolean asgnIfFinished(int stuid, int asgnid) {
        if (this.scoreDao.asgnIfFinished(stuid,asgnid)>0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List selectAll() { return this.scoreDao.selectAll(); }

    @Override
    public List selectByStuid(int stuid) { return this.scoreDao.selectByStuid(stuid); }

    @Override
    public List selectByAsgnid(int asgnid) { return this.scoreDao.selectByAsgnid(asgnid); }

    @Override
    public boolean deleteById(int id) {
        if (scoreDao.deleteById(id)>0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Score selectById(int id) { return this.scoreDao.selectByPrimaryKey(id); }
}
