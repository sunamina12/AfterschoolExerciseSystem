package com.sy.pojo;

public class AsgnQues {
    private Integer id;

    private Integer asgnid;

    private Integer quesid;

    private String questype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAsgnid() {
        return asgnid;
    }

    public void setAsgnid(Integer asgnid) {
        this.asgnid = asgnid;
    }

    public Integer getQuesid() {
        return quesid;
    }

    public void setQuesid(Integer quesid) {
        this.quesid = quesid;
    }

    public String getQuestype() {
        return questype;
    }

    public void setQuestype(String questype) {
        this.questype = questype == null ? null : questype.trim();
    }
}