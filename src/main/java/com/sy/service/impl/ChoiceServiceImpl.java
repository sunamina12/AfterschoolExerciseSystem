package com.sy.service.impl;

/**
 * Created by 板野洋洋 on 2017/3/1.
 */
import javax.annotation.Resource;

import com.sy.service.ChoiceService;
import org.springframework.stereotype.Service;

import com.sy.dao.ChoiceMapper;
import com.sy.pojo.Choice;

import java.util.List;

@Service("choService")
public class ChoiceServiceImpl implements ChoiceService {
    @Resource
    private ChoiceMapper choDao;
    //按ID查询单选题
    @Override
    public Choice selectChoById(int id){
        return this.choDao.selectByPrimaryKey(id);
    }
    //查询全部单选题
    @Override
    public List selectAllCho(){
        List l = this.choDao.selectAll();
        return l;
    }
    //删除单选题
    @Override
    public boolean deleteChoice(int id){
        if(choDao.deleteByPrimaryKey(id)>0){
            return true;
        }else{
            return false;
        }
    }
    //检查题干是否存在
    @Override
    public boolean choStemExist(String stem){
        if(choDao.choStemExist(stem)>0){
            return false;
        }else{
            return true;
        }
    }
    //修改单选题
    @Override
    public void updateChoice(Choice cho){
        int d = choDao.updateByPrimaryKey(cho);
    }
    //添加单选题
    @Override
    public void addChoice(Choice cho){ int d = this.choDao.insert(cho); }

    //查找最大id
    @Override
    public int selectMaxId(){ return choDao.selectMaxId(); }

    //题目总数量
    @Override
    public int getQuantity(){ return choDao.getQuantity(); }

    @Override
    public List selectNum(){ return choDao.selectNum(); }

    @Override
    public List selectAllSbjt() { return choDao.selectAllSbjt(); }

    @Override
    public List selectTopicBySbjt(String sbjt) { return choDao.selectTopicBySbjt(sbjt); }

    @Override
    public int selectQuanByTopic(String sbjt, String topic) { return choDao.selectQuanByTopic(sbjt,topic); }

    @Override
    public List selectIdByST(String sbjt, String topic) { return choDao.selectIdByST(sbjt,topic); }

    @Override
    public Choice selectByPrimaryKey(int id) { return choDao.selectByPrimaryKey(id); }

    @Override
    public List selectByST(String sbjt, String topic) { return choDao.selectByST(sbjt,topic); }
}
