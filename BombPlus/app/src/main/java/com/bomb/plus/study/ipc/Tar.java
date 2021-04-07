package com.bomb.plus.study.ipc;

import android.os.Parcel;
import android.os.Parcelable;

public class Tar implements Parcelable {

    private String name;
    private String age;


    protected Tar(Parcel in) {
        name = in.readString();
        age = in.readString();
    }

    public static final Creator<Tar> CREATOR = new Creator<Tar>() {
        @Override
        public Tar createFromParcel(Parcel in) {
            return new Tar(in);
        }

        @Override
        public Tar[] newArray(int size) {
            return new Tar[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Tar{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(age);
    }
}
