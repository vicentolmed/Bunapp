package com.hooki.bunapp.views;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hooki.bunapp.Adapters.Estados;
import com.hooki.bunapp.Adapters.EstadosAdapter;
import com.hooki.bunapp.AsyncTask.DownloadEdoAsyncTask;
import com.hooki.bunapp.R;
import com.hooki.bunapp.SqliteDb.ClinicasContract;
import com.hooki.bunapp.SqliteDb.ClinicasDbHelper;
import com.hooki.bunapp.Utils.Utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class EdosListActivity extends AppCompatActivity implements  DownloadEdoAsyncTask.DownloadEstadosInterface{
    ListView lsv_estados;
    Intent paisReceived=null;
    String pais = null;
    public final static String SELECTED_ESTADO="selected_estado";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edos_list);
        showToobar(getResources().getString(R.string.estados_title),true);
        lsv_estados =(ListView) findViewById(R.id.lsv_estados);
        paisReceived = getIntent();
        pais=paisReceived.getStringExtra("pais");
        avaibleConsult();
    }

    private void avaibleConsult(){
        if (Utils.isNetworkAvailable(this)) {
            downloadEstados();
        } else {
            //Toast.makeText(this, "Red No disponible para actualizar datos", Toast.LENGTH_SHORT).show();
            getEstadosFromDb();
        }
    }


    //Select estados
    private void getEstadosFromDb() {
        ClinicasDbHelper clinicasDbHelper = new ClinicasDbHelper(this);
        SQLiteDatabase database = clinicasDbHelper.getReadableDatabase();
        Cursor cursor=database.query(true, ClinicasContract.ClinicasColumns.TABLE_NAME, new String[] { ClinicasContract.ClinicasColumns.CLINICASTATE  }, "country=?", new String[] {pais}, null, null, null, null);
        ArrayList <Estados> estadosList = new ArrayList<>();
        while(cursor.moveToNext()) {
            String nameEstados=cursor.getString(0);
            estadosList.add(new Estados(nameEstados));
        }
        cursor.close();
        mostrarPaisList(estadosList);
    }

    // llamado del segundo plano
    private void downloadEstados(){
        DownloadEdoAsyncTask downloadEdoAsyncTask = new DownloadEdoAsyncTask(this);
        downloadEdoAsyncTask.delegate=this;

        try {
            downloadEdoAsyncTask.execute(new URL(getString(R.string.URLESTADOS)+pais));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    //respuesta de segundo plano
    @Override
    public void onEstadosDownloaded(ArrayList<Estados> estadosList) {
        mostrarPaisList(estadosList);
    }
    //mostrar datos
    private void mostrarPaisList(ArrayList<Estados> estadosList) {
        final EstadosAdapter estadoAdapter= new EstadosAdapter(this,R.layout.select_items_estados,estadosList);
        lsv_estados.setAdapter(estadoAdapter);
        lsv_estados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Estados estadoSeleccionado = estadoAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra(SELECTED_ESTADO,estadoSeleccionado.getNombreEstados());
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
