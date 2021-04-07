package com.bomb.plus.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    public String name;
    public String price;


    public Book(String name, String price) {
        this.name = name;
        this.price = price;
    }


    protected Book(Parcel in) {
        name = in.readString();
        price = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(price);
    }
}
