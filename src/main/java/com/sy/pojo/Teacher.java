package com.sy.pojo;

public class Teacher {
    private Integer id;

    private Integer tchid;

    private String tchname;

    private String tchpwd;

    private String faculty;

    private String major;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTchid() {
        return tchid;
    }

    public void setTchid(Integer tchid) {
        this.tchid = tchid;
    }

    public String getTchname() {
        return tchname;
    }

    public void setTchname(String tchname) {
        this.tchname = tchname == null ? null : tchname.trim();
    }

    public String getTchpwd() {
        return tchpwd;
    }

    public void setTchpwd(String tchpwd) {
        this.tchpwd = tchpwd == null ? null : tchpwd.trim();
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty == null ? null : faculty.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }
}