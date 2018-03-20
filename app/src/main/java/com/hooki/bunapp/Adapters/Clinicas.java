package com.hooki.bunapp.Adapters;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 7icente on 13/03/2018.
 */

public class Clinicas implements Parcelable {

    private String clinicaName;
    private String clinicaAddress;
    private String clinicaPhone1;
    private String clinicaPhone2;
    private String clinicaURLfb;
    private String clinicaLat;
    private String clinicaLon;
    private String clinicaCountry;
    private String clinicaState;
    private String clinicaServicioDomicilio;
    private String clinicaServicioPension;
    private String clinicaServicio24hrs;

    public Clinicas(String clinicaName, String clinicaAddress, String clinicaPhone1, String clinicaPhone2, String clinicaURLfb, String clinicaLat,String clinicaLon,String clinicaCountry,String clinicaState,String clinicaServicioDomicilio, String clinicaServicioPension, String clinicaServicio24hrs) {
        this.clinicaName = clinicaName;
        this.clinicaAddress = clinicaAddress;
        this.clinicaPhone1 = clinicaPhone1;
        this.clinicaPhone2 = clinicaPhone2;
        this.clinicaURLfb = clinicaURLfb;
        this.clinicaLat = clinicaLat;
        this.clinicaLon=clinicaLon;
        this.clinicaCountry=clinicaCountry;
        this.clinicaState=clinicaState;
        this.clinicaServicioDomicilio = clinicaServicioDomicilio;
        this.clinicaServicioPension = clinicaServicioPension;
        this.clinicaServicio24hrs = clinicaServicio24hrs;
    }

    public String getClinicaName() {
        return clinicaName;
    }

    public String getClinicaAddress() {
        return clinicaAddress;
    }

    public String getClinicaPhone1() {
        return clinicaPhone1;
    }

    public String getClinicaPhone2() {
        return clinicaPhone2;
    }

    public String getClinicaURLfb() {
        return clinicaURLfb;
    }

    public String getClinicaLat() {
        return clinicaLat;
    }

    public String getClinicaLon() {
        return clinicaLon;
    }

    public String getClinicaCountry() {
        return clinicaCountry;
    }

    public String getClinicaState() {
        return clinicaState;
    }

    public String getClinicaServicioDomicilio() {
        return clinicaServicioDomicilio;
    }

    public String getClinicaServicioPension() {
        return clinicaServicioPension;
    }

    public String getClinicaServicio24hrs() {
        return clinicaServicio24hrs;
    }

    protected Clinicas(Parcel in) {
        clinicaName = in.readString();
        clinicaAddress = in.readString();
        clinicaPhone1 = in.readString();
        clinicaPhone2 = in.readString();
        clinicaURLfb = in.readString();
        clinicaLat = in.readString();
        clinicaLon=in.readString();
        clinicaCountry=in.readString();
        clinicaState=in.readString();
        clinicaServicioDomicilio = in.readString();
        clinicaServicioPension = in.readString();
        clinicaServicio24hrs = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(clinicaName);
        dest.writeString(clinicaAddress);
        dest.writeString(clinicaPhone1);
        dest.writeString(clinicaPhone2);
        dest.writeString(clinicaURLfb);
        dest.writeString(clinicaLat);
        dest.writeString(clinicaLon);
        dest.writeString(clinicaCountry);
        dest.writeString(clinicaState);
        dest.writeString(clinicaServicioDomicilio);
        dest.writeString(clinicaServicioPension);
        dest.writeString(clinicaServicio24hrs);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Clinicas> CREATOR = new Parcelable.Creator<Clinicas>() {
        @Override
        public Clinicas createFromParcel(Parcel in) {
            return new Clinicas(in);
        }

        @Override
        public Clinicas[] newArray(int size) {
            return new Clinicas[size];
        }
    };
}
