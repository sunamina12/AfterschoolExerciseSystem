package com.sy.pojo;

public class Wrong {
    private Integer id;

    private Integer stuid;

    private Integer asgnid;

    private Integer quesid;

    private String questype;

    private String uranswer;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStuid() {
        return stuid;
    }

    public void setStuid(Integer stuid) {
        this.stuid = stuid;
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

    public String getUranswer() {
        return uranswer;
    }

    public void setUranswer(String uranswer) {
        this.uranswer = uranswer == null ? null : uranswer.trim();
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status == null ? null : status.trim(); }
}