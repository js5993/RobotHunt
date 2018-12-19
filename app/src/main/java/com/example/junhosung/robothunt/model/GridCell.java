package com.example.junhosung.robothunt.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GridCell implements Parcelable {

    public boolean isRevealed = false;
    public boolean isBomb = false;

    public GridCell() {

    }

    protected GridCell(Parcel in) {
        isRevealed = in.readByte() != 0x00;
        isBomb = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeByte( (byte) (isRevealed ? 0x01 : 0x00));
        parcel.writeByte( (byte) (isBomb ? 0x01 : 0x00));

    }
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GridCell> CREATOR = new Parcelable.Creator<GridCell>() {
        @Override
        public GridCell createFromParcel(Parcel in) {
            return new GridCell(in);
        }

        @Override
        public GridCell[] newArray(int size) {
            return new GridCell[size];
        }
    };

}
