package com.uc.teparapps.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LDR implements Parcelable {
    String status;

    protected LDR(Parcel in) {
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LDR> CREATOR = new Creator<LDR>() {
        @Override
        public LDR createFromParcel(Parcel in) {
            return new LDR(in);
        }

        @Override
        public LDR[] newArray(int size) {
            return new LDR[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
