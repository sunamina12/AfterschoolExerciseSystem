package com.sy.service;

/**
 * Created by 板野洋洋 on 2017/3/1.
 */
import com.sy.pojo.Choice;

import java.util.List;

public interface ChoiceService {
    List selectAllCho();
    public boolean deleteChoice(int id);
    public boolean choStemExist(String stem);
    public void updateChoice(Choice cho);
    public Choice selectChoById(int id);
    public void addChoice(Choice cho);
    public int selectMaxId();
    public int getQuantity();
    List selectNum();
    List selectAllSbjt();
    List selectTopicBySbjt(String sbjt);
    int selectQuanByTopic(String sbjt, String topic);
    List selectIdByST(String sbjt, String topic);
    public Choice selectByPrimaryKey(int id);
    public List selectByST(String sbjt, String topic);
}
