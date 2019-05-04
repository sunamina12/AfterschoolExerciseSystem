package com.sy.pojo;

public class Choice {
    private Integer id;

    private Integer choid;

    private String stem;

    private String option1;

    private String option2;

    private String option3;

    private String option4;

    private String answer;

    private String sbjt;

    private String topic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChoid() {
        return choid;
    }

    public void setChoid(Integer choid) {
        this.choid = choid;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem == null ? null : stem.trim();
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1 == null ? null : option1.trim();
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2 == null ? null : option2.trim();
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3 == null ? null : option3.trim();
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4 == null ? null : option4.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
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
}