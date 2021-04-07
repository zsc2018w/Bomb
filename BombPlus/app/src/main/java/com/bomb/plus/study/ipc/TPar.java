package com.bomb.plus.study.ipc;

import android.os.Parcel;
import android.os.Parcelable;

public class TPar implements Parcelable {
    private String name;

    protected TPar(Parcel in) {
        name = in.readString();
        age = in.readString();
        tar = in.readParcelable(Tar.class.getClassLoader());
    }

    public static final Creator<TPar> CREATOR = new Creator<TPar>() {
        @Override
        public TPar createFromParcel(Parcel in) {
            return new TPar(in);
        }

        @Override
        public TPar[] newArray(int size) {
            return new TPar[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Tar getTar() {
        return tar;
    }

    public void setTar(Tar tar) {
        this.tar = tar;
    }

    @Override
    public String toString() {
        return "TPar{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", tar=" + tar +
                '}';
    }

    private String age;
    private Tar tar;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(age);
        parcel.writeParcelable(tar, i);
    }
}
