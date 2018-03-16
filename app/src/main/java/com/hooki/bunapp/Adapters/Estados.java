package com.hooki.bunapp.Adapters;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 7icente on 13/03/2018.
 */

public class Estados implements Parcelable {

    private String nombreEstados;

    public Estados(String nombreEstados) {
        this.nombreEstados = nombreEstados;
    }

    public String getNombreEstados() {
        return nombreEstados;
    }

    protected Estados(Parcel in) {
        nombreEstados = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombreEstados);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Estados> CREATOR = new Parcelable.Creator<Estados>() {
        @Override
        public Estados createFromParcel(Parcel in) {
            return new Estados(in);
        }

        @Override
        public Estados[] newArray(int size) {
            return new Estados[size];
        }
    };
}
