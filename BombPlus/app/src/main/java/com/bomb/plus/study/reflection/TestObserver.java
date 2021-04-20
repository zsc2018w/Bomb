package com.bomb.plus.study.reflection;

public class TestObserver {

    private String name;
    private String des;


    public TestObserver() {
        this.name = "default";
        this.des = "default";
    }

    public TestObserver(String name, String des) {
        this.name = name;
        this.des = des;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
