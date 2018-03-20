package com.hooki.bunapp.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hooki.bunapp.Adapters.Clinicas;
import com.hooki.bunapp.Adapters.Pais;
import com.hooki.bunapp.Adapters.PaisAdapter;
import com.hooki.bunapp.AsyncTask.DownloadPaisAsyncTask;
import com.hooki.bunapp.R;
import com.hooki.bunapp.SqliteDb.ClinicasContract;
import com.hooki.bunapp.SqliteDb.ClinicasDbHelper;
import com.hooki.bunapp.Utils.Utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PaisListActivity extends AppCompatActivity implements  DownloadPaisAsyncTask.DownloadPaisInterface {
    ListView lsv_paises;
    public final static String SELECTED_PAIS="selected_pais";
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pais_list);
        showToobar(getResources().getString(R.string.paises_title),true);
        lsv_paises =(ListView) findViewById(R.id.lsv_paises);
        avaibleConsult();
    }

    private void avaibleConsult(){
        if (Utils.isNetworkAvailable(this)) {
            downloadPaises();
        } else {
            //Toast.makeText(this, "Red No disponible para actualizar datos", Toast.LENGTH_SHORT).show();
            getPaisFromDb();
        }
    }

    // llamado del segundo plano
    private void downloadPaises(){
        DownloadPaisAsyncTask downloadPaisAsyncTask = new DownloadPaisAsyncTask(this);
        downloadPaisAsyncTask.delegate=this;

        try {
            downloadPaisAsyncTask.execute(new URL(getString(R.string.URLPAIS)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
    //select pais sqlite
    private void getPaisFromDb() {
        ClinicasDbHelper clinicasDbHelper = new ClinicasDbHelper(this);
        SQLiteDatabase database = clinicasDbHelper.getReadableDatabase();
        Cursor cursor=database.query(true,ClinicasContract.ClinicasColumns.TABLE_NAME, new String[] { ClinicasContract.ClinicasColumns.CLINICACOUNTRY }, null, null, null, null, null, null);
        ArrayList <Pais> paisList = new ArrayList<>();
        while(cursor.moveToNext()) {
            String clinicaCountry=cursor.getString(0);
            paisList.add(new Pais(clinicaCountry));
        }
        cursor.close();
        mostrarPaisList(paisList);
    }

    //respuesta de segundo plano
    @Override
    public void onPaisesDownloaded(ArrayList<Pais> paisList) {
        mostrarPaisList(paisList);
    }
    //mostrar datos
    private void mostrarPaisList(ArrayList<Pais> paisList) {
        final PaisAdapter paisAdapter= new PaisAdapter(this,R.layout.select_items_paises,paisList);
        lsv_paises.setAdapter(paisAdapter);
        lsv_paises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pais paisSeleccionado = paisAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra(SELECTED_PAIS,paisSeleccionado.getNombrePais());
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    public void showToobar( String title, boolean upBotton ){
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upBotton);
    }
}
