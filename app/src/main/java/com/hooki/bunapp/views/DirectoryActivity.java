package com.hooki.bunapp.views;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hooki.bunapp.Adapters.Clinicas;
import com.hooki.bunapp.Adapters.ClinicasAdapter;
import com.hooki.bunapp.AsyncTask.DownloadClinicasAsyncTask;
import com.hooki.bunapp.R;
import com.hooki.bunapp.SqliteDb.ClinicasContract;
import com.hooki.bunapp.SqliteDb.ClinicasDbHelper;
import com.hooki.bunapp.Utils.Utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DirectoryActivity extends AppCompatActivity implements DownloadClinicasAsyncTask.DownloadClinicasInterface {
    private static final int RESULT_CODE_PAIS = 1;
    private static final int RESULT_COD_EDO = 2;
    public final static String SELECTED_CLINICAS="selected_clinicas";
    private int ID_CONSULTA=0;
    ListView lsv_clinicas;
    TextView txt_Pais_selecter;
    TextView txt_edo_selecter;
    Button btn_consultar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
        showToobar(getResources().getString(R.string.directory_title),true);
        lsv_clinicas =(ListView) findViewById(R.id.lsv_clinicas);
        txt_Pais_selecter=(TextView) findViewById(R.id.txt_Pais_selecter);
        txt_edo_selecter=(TextView) findViewById(R.id.txt_edo_selecter);
        btn_consultar=(Button) findViewById(R.id.btn_consultar);
        avaibleConsult();

    }

    private void avaibleConsult(){
        if (Utils.isNetworkAvailable(this)) {
            downloadClinicas(ID_CONSULTA);
        } else {
                //Toast.makeText(this, "Red No disponible para actualizar datos", Toast.LENGTH_SHORT).show();
            if(checkEmpty()){
                Toast.makeText(this, "Necesita una conexion de Internet", Toast.LENGTH_SHORT).show();
            }else {
                getClinicasFromDb(ID_CONSULTA);
            }
        }
    }
    // llamado del segundo plano
    private void downloadClinicas(int idConsulta){
        DownloadClinicasAsyncTask downloadClinicasAsyncTask = new DownloadClinicasAsyncTask(this,idConsulta);
        downloadClinicasAsyncTask.delegate=this;
        if(idConsulta==0){
            try {
                downloadClinicasAsyncTask.execute(new URL(getString(R.string.URLCLINICAS)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else if(idConsulta==1){
            try {
                downloadClinicasAsyncTask.execute(new URL(getString(R.string.URLCLINICASPAIS)+txt_Pais_selecter.getText().toString()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else if(idConsulta==2){
            try {
                downloadClinicasAsyncTask.execute(new URL(getString(R.string.URLCLINICASPAISEDO)+txt_Pais_selecter.getText().toString()+"/"+txt_edo_selecter.getText().toString()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkEmpty(){
        int count = 0;
        ClinicasDbHelper clinicasDbHelper = new ClinicasDbHelper(this);
        SQLiteDatabase db = clinicasDbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT count(*) FROM " + ClinicasContract.ClinicasColumns.TABLE_NAME, null);

        try {
            if(cursor != null)
                if(cursor.getCount() > 0){
                    cursor.moveToFirst();
                    count = cursor.getInt(0);
                }
        }finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        if(count>0)
            return false;
        else
            return true;
    }

    private void getClinicasFromDb(int tipoConsulta) {
        ClinicasDbHelper clinicasDbHelper = new ClinicasDbHelper(this);
        SQLiteDatabase database = clinicasDbHelper.getReadableDatabase();
        Cursor cursor=null;
        if(tipoConsulta==0){
            cursor = database.query(ClinicasContract.ClinicasColumns.TABLE_NAME, null, null, null, null, null, null);
        }
        if(tipoConsulta==1){
            cursor = database.query(ClinicasContract.ClinicasColumns.TABLE_NAME, null, "country=?", new String[] {txt_Pais_selecter.getText().toString()}, null, null, null);
        }
        if(tipoConsulta==2){
            cursor = database.query(ClinicasContract.ClinicasColumns.TABLE_NAME, null, "country=? AND state=?",new String[] {txt_Pais_selecter.getText().toString(),txt_edo_selecter.getText().toString()}, null, null, null);
        }
        ArrayList<Clinicas> clinicasList = new ArrayList<>();
        while(cursor.moveToNext()) {
            String clinicaName=cursor.getString(ClinicasContract.ClinicasColumns.CLINICANAME_COLUMN_INDEX);
            String clinicaAddress=cursor.getString(ClinicasContract.ClinicasColumns.CLINICAADDRESS_COLUMN_INDEX);
            String clinicaPhone1=cursor.getString(ClinicasContract.ClinicasColumns.CLINICAPHONE1_COLUMN_INDEX);
            String clinicaPhone2=cursor.getString(ClinicasContract.ClinicasColumns.CLINICAPHONE2_COLUMN_INDEX);
            String clinicaURLfb=cursor.getString(ClinicasContract.ClinicasColumns.CLINICAURLFB_COLUMN_INDEX);
            String latMaps=cursor.getString(ClinicasContract.ClinicasColumns.LATMAPS_COLUMN_INDEX);
            String lonMaps=cursor.getString(ClinicasContract.ClinicasColumns.LONMAPS_COLUMN_INDEX);
            String clinicaCountry=cursor.getString(ClinicasContract.ClinicasColumns.CLINICACOUNTRY_COLUMN_INDEX);
            String clinicaState=cursor.getString(ClinicasContract.ClinicasColumns.CLINICASTATE_COLUMN_INDEX);
            String clinicaServicioDomicilio=cursor.getString(ClinicasContract.ClinicasColumns.CLINICASERVDOMICILIO_COLUMN_INDEX);
            String clinicaServicioPension=cursor.getString(ClinicasContract.ClinicasColumns.CLINICASERVPENSION_COLUMN_INDEX);
            String clinicaServicio24hrs=cursor.getString(ClinicasContract.ClinicasColumns.CLINICASERV24HRS_COLUMN_INDEX);
            clinicasList.add(new Clinicas(clinicaName,clinicaAddress,clinicaPhone1,clinicaPhone2,clinicaURLfb,latMaps,lonMaps,clinicaCountry,clinicaState,clinicaServicioDomicilio,clinicaServicioPension,clinicaServicio24hrs));
        }
        cursor.close();
        mostrarClinicasList(clinicasList);
    }

    public void goResultados (View view){
        avaibleConsult();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
           Toast.makeText(this, "No selecciono una opción", Toast.LENGTH_SHORT).show();
        }else{
            if(requestCode==RESULT_CODE_PAIS ){
                String resultadoPais = data.getStringExtra("selected_pais");
                txt_Pais_selecter.setText(resultadoPais);
                ID_CONSULTA=1;
            }
            else if (requestCode==RESULT_COD_EDO){
                String resultadoEstado = data.getStringExtra("selected_estado");
                txt_edo_selecter.setText(resultadoEstado);
                ID_CONSULTA=2;
            }
        }
    }


    public void goListaPais (View view){
        Intent i = new Intent(DirectoryActivity.this, PaisListActivity.class);
        startActivityForResult(i, RESULT_CODE_PAIS);
    }

    public void goListaEstados (View view){
        Intent i = new Intent(DirectoryActivity.this, EdosListActivity.class);
        i.putExtra("pais",txt_Pais_selecter.getText().toString());
        startActivityForResult(i, RESULT_COD_EDO);
    }


    //mostrar datos
    private void mostrarClinicasList(ArrayList<Clinicas> clinicasList) {
        final ClinicasAdapter clinicaAdapter= new ClinicasAdapter(this,R.layout.select_items_clinicas,clinicasList);
        lsv_clinicas.setAdapter(clinicaAdapter);
        lsv_clinicas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Clinicas selectedClinicas = clinicaAdapter.getItem(position);
                Intent intent = new Intent(DirectoryActivity.this,DetailClinicasActivity.class);
                intent.putExtra(SELECTED_CLINICAS,selectedClinicas);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClinicasDownloaded(ArrayList<Clinicas> clinicasList) {
        mostrarClinicasList(clinicasList);
    }


    public void showToobar( String title, boolean upBotton ){
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upBotton);
    }

}
