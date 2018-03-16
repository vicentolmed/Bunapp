package com.hooki.bunapp.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hooki.bunapp.Adapters.Pais;
import com.hooki.bunapp.Adapters.PaisAdapter;
import com.hooki.bunapp.AsyncTask.DownloadPaisAsyncTask;
import com.hooki.bunapp.R;

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
        downloadPaises();
    }

    // llamado del segundo plano
    private void downloadPaises(){
        DownloadPaisAsyncTask downloadPaisAsyncTask = new DownloadPaisAsyncTask(this);
        downloadPaisAsyncTask.delegate=this;

        try {
            downloadPaisAsyncTask.execute(new URL("http://192.168.1.65/rest/index.php/clinicas/pais"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

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
