package com.hooki.bunapp.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.hooki.bunapp.Adapters.Clinicas;
import com.hooki.bunapp.Adapters.ClinicasAdapter;
import com.hooki.bunapp.AsyncTask.DownloadClinicasAsyncTask;
import com.hooki.bunapp.R;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DirectoryActivity extends AppCompatActivity implements DownloadClinicasAsyncTask.DownloadClinicasInterface {
    private static final int RESULT_CODE_PAIS = 1;
    private static final int RESULT_COD_EDO = 2;
    public final static String SELECTED_CLINICAS="selected_clinicas";
    private int ID_CONSULTA;
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
        ID_CONSULTA=0;
    }


    // llamado del segundo plano
    private void downloadClinicas(int idConsulta){
        DownloadClinicasAsyncTask downloadClinicasAsyncTask = new DownloadClinicasAsyncTask(this);
        downloadClinicasAsyncTask.delegate=this;
        if(ID_CONSULTA==0){
            try {
                downloadClinicasAsyncTask.execute(new URL("http://192.168.1.65/rest/index.php/clinicas/"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else if(ID_CONSULTA==1){
            try {
                downloadClinicasAsyncTask.execute(new URL("http://192.168.1.65/rest/index.php/clinicas/por_pais/"+txt_Pais_selecter.getText().toString()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else if(ID_CONSULTA==2){
            try {
                downloadClinicasAsyncTask.execute(new URL("http://192.168.1.65/rest/index.php/clinicas/por_paisedo/"+txt_Pais_selecter.getText().toString()+"/"+txt_edo_selecter.getText().toString()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else{

        }
    }
    public void goResultados (View view){
        downloadClinicas(ID_CONSULTA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
           /* Toast.makeText(this, "No se selecciono", Toast.LENGTH_SHORT)
                    .show();*/
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
