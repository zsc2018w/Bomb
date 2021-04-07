package com.bomb.plus.study.ipc;

import androidx.annotation.TransitionRes;

import java.io.Serializable;

import kotlin.jvm.Transient;

public class TUser implements Serializable {
   // private static final long serialVersionUID = -3690291740366490689L;
    private static final long serialVersionUID = 7284696504558690604L;

    public TUser(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Transient
    private String age;
    private String name;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    private String sex;
/*
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;*/

    public TUser(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    @Override
    public String toString() {
        return "TUser{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }




}
