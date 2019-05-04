package com.sy.pojo;

import java.util.Date;

public class Assignment {
    private Integer id;

    private Integer asgnid;

    private String sbjt;

    private String topic;

    private Integer classid;

    private Integer chonum;

    private Integer blknum;

    private Integer asgntime;

    private Date deadline;

    private String uploader;

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

    public String getSbjt() {
        return sbjt;
    }

    public void setSbjt(String sbjt) {
        this.sbjt = sbjt == null ? null : sbjt.trim();
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public Integer getChonum() {
        return chonum;
    }

    public void setChonum(Integer chonum) {
        this.chonum = chonum;
    }

    public Integer getBlknum() {
        return blknum;
    }

    public void setBlknum(Integer blknum) {
        this.blknum = blknum;
    }

    public Integer getAsgntime() {
        return asgntime;
    }

    public void setAsgntime(Integer asgntime) {
        this.asgntime = asgntime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader == null ? null : uploader.trim();
    }
}