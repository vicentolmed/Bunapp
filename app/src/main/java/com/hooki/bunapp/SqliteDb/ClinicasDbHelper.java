package com.hooki.bunapp.SqliteDb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 7icente on 18/03/2018.
 */

public class ClinicasDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "clinicas.db";

    public ClinicasDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CLINICAS_DATABASE = "CREATE TABLE " + ClinicasContract.ClinicasColumns.TABLE_NAME + " (" +
                ClinicasContract.ClinicasColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ClinicasContract.ClinicasColumns.CLINICANAME + " TEXT NOT NULL," +
                ClinicasContract.ClinicasColumns.CLINICAADDRESS + " TEXT NOT NULL," +
                ClinicasContract.ClinicasColumns.CLINICAPHONE1 + " TEXT NOT NULL," +
                ClinicasContract.ClinicasColumns.CLINICAPHONE2 + " TEXT NOT NULL," +
                ClinicasContract.ClinicasColumns.CLINICAURLFB + " TEXT NOT NULL," +
                ClinicasContract.ClinicasColumns.LATMAPS + " TEXT NOT NULL," +
                ClinicasContract.ClinicasColumns.LONMAPS + " TEXT NOT NULL," +
                ClinicasContract.ClinicasColumns.CLINICACOUNTRY + " TEXT NOT NULL," +
                ClinicasContract.ClinicasColumns.CLINICASTATE + " TEXT NOT NULL," +
                ClinicasContract.ClinicasColumns.CLINICASERVDOMICILIO + " TEXT NOT NULL," +
                ClinicasContract.ClinicasColumns.CLINICASERVPENSION + " TEXT NOT NULL," +
                ClinicasContract.ClinicasColumns.CLINICASERV24HRS + " TEXT NOT NULL" +
                ")";
                 sqLiteDatabase.execSQL(CLINICAS_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ClinicasContract.ClinicasColumns.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
