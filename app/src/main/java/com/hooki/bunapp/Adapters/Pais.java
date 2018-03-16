package com.hooki.bunapp.Adapters;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 7icente on 12/03/2018.
 */

public class Pais implements Parcelable {
    private String nombrePais;

    public Pais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    protected Pais(Parcel in) {
        nombrePais = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombrePais);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Pais> CREATOR = new Parcelable.Creator<Pais>() {
        @Override
        public Pais createFromParcel(Parcel in) {
            return new Pais(in);
        }

        @Override
        public Pais[] newArray(int size) {
            return new Pais[size];
        }
    };
}