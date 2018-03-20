package com.hooki.bunapp.SqliteDb;

import android.provider.BaseColumns;

/**
 * Created by 7icente on 18/03/2018.
 */

public class ClinicasContract {

    public class ClinicasColumns implements BaseColumns {
        public static final String TABLE_NAME = "clinicas";

        public static final String CLINICANAME = "name";
        public static final String CLINICAADDRESS = "address";
        public static final String CLINICAPHONE1 = "phone1";
        public static final String CLINICAPHONE2 = "phone2";
        public static final String CLINICAURLFB = "urlfacebook";
        public static final String LATMAPS = "lat";
        public static final String LONMAPS = "lon";
        public static final String CLINICACOUNTRY = "country";
        public static final String CLINICASTATE = "state";
        public static final String CLINICASERVDOMICILIO = "servdomicilio";
        public static final String CLINICASERVPENSION = "servpension";
        public static final String CLINICASERV24HRS = "serv24hrs";

        public static final int CLINICANAME_COLUMN_INDEX = 1;
        public static final int CLINICAADDRESS_COLUMN_INDEX = 2;
        public static final int CLINICAPHONE1_COLUMN_INDEX = 3;
        public static final int CLINICAPHONE2_COLUMN_INDEX = 4;
        public static final int CLINICAURLFB_COLUMN_INDEX =5;
        public static final int LATMAPS_COLUMN_INDEX=6;
        public static final int LONMAPS_COLUMN_INDEX= 7;
        public static final int CLINICACOUNTRY_COLUMN_INDEX = 8;
        public static final int CLINICASTATE_COLUMN_INDEX= 9;
        public static final int CLINICASERVDOMICILIO_COLUMN_INDEX=10;
        public static final int CLINICASERVPENSION_COLUMN_INDEX=11;
        public static final int CLINICASERV24HRS_COLUMN_INDEX=12;

    }
}
