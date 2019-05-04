package com.sy.service.impl;

import com.sy.dao.AsgnQuesMapper;
import com.sy.pojo.AsgnQues;
import com.sy.service.AsgnQuesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yangitano on 4/24/17.
 */
@Service("AsQsService")
public class AsgnQuesServiceImpl implements AsgnQuesService {
    @Resource
    private AsgnQuesMapper asqsDao;

    @Override
    public void addQues(AsgnQues asgnQues) { int a = this.asqsDao.insert(asgnQues); }

    @Override
    public boolean delQues(int asgnid) {
        if (this.asqsDao.delByAsgnId(asgnid)>0) { return true; }
        else { return false; }
    }

    @Override
    public List selectByAsgnid(int asgnid) { return this.asqsDao.selectByAsgnid(asgnid); }
}
