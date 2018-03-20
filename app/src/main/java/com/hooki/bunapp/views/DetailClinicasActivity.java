package com.hooki.bunapp.views;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hooki.bunapp.Adapters.Clinicas;
import com.hooki.bunapp.R;

public class DetailClinicasActivity extends AppCompatActivity {

    private String urlFacebook= null;
    private String urlLat= null;
    private String urlLon= null;
    private String phone1,phone2=null;
    private String callPhone=null;
    public static final int CALL_PHONE_REQUEST_CODE = 010;
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_clinicas);
        showToobar( "Datos de la clínica", true );
        Bundle extras = getIntent().getExtras();
        Clinicas clinicas= extras.getParcelable(DirectoryActivity.SELECTED_CLINICAS);

        if (clinicas != null) {
            TextView txt_name_clinica = (TextView) findViewById(R.id.txt_name_clinica);
            TextView address1 = (TextView) findViewById(R.id.address1);
            TextView txt_phone1 = (TextView) findViewById(R.id.txt_phone1);
            TextView txt_phone2 = (TextView) findViewById(R.id.txt_phone2);
            TextView txt_serv_24hrs= (TextView) findViewById(R.id.txt_serv_24hrs);
            TextView txt_serv_domicilio= (TextView) findViewById(R.id.txt_serv_domicilio);
            TextView txt_serv_pension= (TextView) findViewById(R.id.txt_serv_pension);

            txt_name_clinica.setText(clinicas.getClinicaName());
            address1.setText(clinicas.getClinicaAddress());
            phone1=clinicas.getClinicaPhone1();
            txt_phone1.setText(phone1);
            phone2=clinicas.getClinicaPhone2();
            txt_phone2.setText(phone2);
            urlFacebook=clinicas.getClinicaURLfb();
            urlLat=clinicas.getClinicaLat();
            urlLon=clinicas.getClinicaLon();
            txt_serv_24hrs.setText(clinicas.getClinicaServicio24hrs());
            txt_serv_domicilio.setText(clinicas.getClinicaServicioDomicilio());
            txt_serv_pension.setText(clinicas.getClinicaServicioPension());
        }
    }

    public void goMaps (View view){
        try {
            String ruta = "https://maps.google.com/maps?saddr=" + "&daddr=" + urlLat + "," + urlLon;
            Intent mapgoo = new Intent(Intent.ACTION_VIEW, Uri.parse(ruta));
            startActivity(mapgoo);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "Debe de terner Google Maps en su Telefono", Toast.LENGTH_LONG).show();
        }
    }

    public void goFacebook(View view){
        try {
            Intent fbgoo = new Intent(Intent.ACTION_VIEW, Uri.parse(urlFacebook));
            startActivity(fbgoo);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "Debe de terner Facebook en su Telefono", Toast.LENGTH_LONG).show();
        }
    }

    public void goCallPhone1( View view){
        callPhone=phone1;
        makeCall(callPhone);
    }

    public void goCallPhone2( View view){
        callPhone=phone2;
        makeCall(callPhone);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CALL_PHONE_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                makeCall(callPhone);
            }
             else  if (shouldShowRequestPermissionRationale(CALL_PHONE)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Hacer llamadas");
                builder.setMessage("Debes aceptar este permiso para hacer llamadas desde la app");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String[] permissions = new String[]{CALL_PHONE};
                        requestPermissions(permissions,CALL_PHONE_REQUEST_CODE);
                    }
                });
                builder.show();
            }
        }
    }

    private void makeCall(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone));

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
                startActivity(intent);
            } else {
            final String[] permission = new String[]{CALL_PHONE};
            requestPermissions(permission,CALL_PHONE_REQUEST_CODE);
            }
        }
        else {
            startActivity(intent);
        }
    }

    public void showToobar(String title, boolean upBotton ){
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upBotton);
    }
}
