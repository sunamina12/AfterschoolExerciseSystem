package com.sy.service;

import com.sy.pojo.AsgnQues;

import java.util.List;

/**
 * Created by yangitano on 4/24/17.
 */
public interface AsgnQuesService {
    public void addQues(AsgnQues asgnQues);
    public boolean delQues(int asgnid);
    List selectByAsgnid(int asgnid);
}
