package com.sy.pojo;

public class Blank {
    private Integer id;

    private Integer blkid;

    private String stem;

    private String answer;

    private String subj;

    private String topic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlkid() {
        return blkid;
    }

    public void setBlkid(Integer blkid) {
        this.blkid = blkid;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem == null ? null : stem.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public String getSubj() {
        return subj;
    }

    public void setSubj(String subj) {
        this.subj = subj == null ? null : subj.trim();
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }
}