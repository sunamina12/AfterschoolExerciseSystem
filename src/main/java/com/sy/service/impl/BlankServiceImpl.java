package com.sy.service.impl;

/**
 * Created by 板野洋洋 on 2017/3/2.
 */
import javax.annotation.Resource;

import com.sy.service.BlankService;
import org.springframework.stereotype.Service;

import com.sy.dao.BlankMapper;
import com.sy.pojo.Blank;

import java.util.List;

@Service("blkService")
public class BlankServiceImpl implements BlankService {
    @Resource
    private BlankMapper blkDao;
    //按ID查询填空题
    @Override
    public Blank selectBlkById(int id){
        return this.blkDao.selectByPrimaryKey(id);
    }
    //查询全部填空题
    @Override
    public List selectAllBlk(){
        List l = this.blkDao.selectAll();
        return l;
    }
    //按ID删除填空题
    @Override
    public boolean deleteBlkById(int id){
        if(blkDao.deleteByPrimaryKey(id)>0){
            return true;
        }else {
            return false;
        }
    }
    //检查题干是否存在
    @Override
    public boolean blkStemExist(String stem){
        if(blkDao.blkStemExist(stem)>0){
            return false;
        }else{
            return true;
        }
    }
    //修改填空题
    @Override
    public void updateBlank(Blank blk){
        int d = blkDao.updateByPrimaryKey(blk);
    }
    //添加填空题
    @Override
    public void addBlank(Blank blk){ int d = this.blkDao.insert(blk); }

    //查找最大id
    public int selectMaxId(){ return blkDao.selectMaxId(); }

    //题目总数量
    public int getQuantity(){ return blkDao.getQuantity(); }

    @Override
    public List selectAllSbjt() { return blkDao.selectAllSbjt(); }

    @Override
    public List selectTopicBySbjt(String sbjt) { return blkDao.selectTopicBySbjt(sbjt); }

    @Override
    public int selectQuanByTopic(String sbjt, String topic) { return blkDao.selectQuanByTopic(sbjt,topic); }

    @Override
    public List selectIdByST(String sbjt, String topic) { return blkDao.selectIdByST(sbjt,topic); }

    @Override
    public Blank selectByPrimaryKey(int id) { return blkDao.selectByPrimaryKey(id); }

    @Override
    public List selectByST(String sbjt, String topic) { return blkDao.selectByST(sbjt,topic); }
}
