package com.uc.teparapps.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ParkingSpace implements Parcelable {
    String status;
    String parkCode;

    public ParkingSpace(){

    }

    public ParkingSpace(String status, String parkCode) {
        this.status = status;
        this.parkCode = parkCode;
    }

    protected ParkingSpace(Parcel in) {
        status = in.readString();
        parkCode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(parkCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ParkingSpace> CREATOR = new Creator<ParkingSpace>() {
        @Override
        public ParkingSpace createFromParcel(Parcel in) {
            return new ParkingSpace(in);
        }

        @Override
        public ParkingSpace[] newArray(int size) {
            return new ParkingSpace[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParkCode() {
        return parkCode;
    }

    public void setParkCode(String parkCode) {
        this.parkCode = parkCode;
    }
}
