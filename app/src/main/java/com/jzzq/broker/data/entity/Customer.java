package com.jzzq.broker.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhaopan on 16/7/27.
 * e-mail: kangqiao610@gmail.com
 */
public class Customer implements Parcelable {
    public String name;
    public String message;
    public long time;
    public int avatar;

    public Customer() {
    }

    protected Customer(Parcel in) {
        name = in.readString();
        message = in.readString();
        time = in.readLong();
        avatar = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(message);
        dest.writeLong(time);
        dest.writeInt(avatar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };
}
