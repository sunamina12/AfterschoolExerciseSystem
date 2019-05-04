package com.sy.service;

/**
 * Created by 板野洋洋 on 2017/3/2.
 */
import com.sy.pojo.Blank;

import java.util.List;

public interface BlankService {
    List selectAllBlk();
    public Blank selectBlkById(int id);
    public boolean deleteBlkById(int id);
    public boolean blkStemExist(String stem);
    public void updateBlank(Blank blk);
    public void addBlank(Blank blk);
    public int selectMaxId();
    public int getQuantity();
    List selectAllSbjt();
    List selectTopicBySbjt(String sbjt);
    public int selectQuanByTopic(String sbjt, String topic);
    List selectIdByST(String sbjt, String topic);
    public Blank selectByPrimaryKey(int id);
    public List selectByST(String sbjt, String topic);
}
