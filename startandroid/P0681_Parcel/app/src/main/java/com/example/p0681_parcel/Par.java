package com.example.p0681_parcel;

import android.os.Parcel;
import android.os.Parcelable;

public class Par implements Parcelable {
    protected Par(Parcel in) {
    }

    public static final Creator<Par> CREATOR = new Creator<Par>() {
        @Override
        public Par createFromParcel(Parcel in) {
            return new Par(in);
        }

        @Override
        public Par[] newArray(int size) {
            return new Par[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
