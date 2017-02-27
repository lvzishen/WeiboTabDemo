package com.ifengart.tablistdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lvzishen on 17/2/10.
 */
public class UserNickname implements Parcelable {

    public String uname;

    protected UserNickname(Parcel in) {
        uname = in.readString();
    }

    public UserNickname(){}


    public static final Creator<UserNickname> CREATOR = new Creator<UserNickname>() {
        @Override
        public UserNickname createFromParcel(Parcel in) {
            return new UserNickname(in);
        }

        @Override
        public UserNickname[] newArray(int size) {
            return new UserNickname[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uname);
    }
}
