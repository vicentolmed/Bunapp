package com.hooki.bunapp.views;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hooki.bunapp.Adapters.Clinicas;
import com.hooki.bunapp.R;

public class DetailClinicasActivity extends AppCompatActivity {

    private String urlMaps= null;
    private String urlFacebook= null;

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

            txt_name_clinica.setText(clinicas.getClinicaName());
            address1.setText(clinicas.getClinicaAddress());
            txt_phone1.setText(clinicas.getClinicaPhone1());
            txt_phone2.setText(clinicas.getClinicaPhone2());
            urlMaps=clinicas.getClinicaURLMaps();
            urlFacebook=clinicas.getClinicaURLfb();
        }
    }

    public void goMaps (View view){
        Toast.makeText(getApplicationContext(), "Google Maps ", Toast.LENGTH_LONG).show();
        Uri gmmIntentUri = Uri.parse("geo:19.096160,-98.249542");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    public void goFacebook(View view){

    }

    public void showToobar( String title, boolean upBotton ){
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upBotton);
    }
}
