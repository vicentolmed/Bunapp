package com.hooki.bunapp.AsyncTask;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.hooki.bunapp.Adapters.Clinicas;
import com.hooki.bunapp.SqliteDb.ClinicasContract;
import com.hooki.bunapp.SqliteDb.ClinicasDbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by 7icente on 13/03/2018.
 */

public class DownloadClinicasAsyncTask extends AsyncTask<URL,Void,ArrayList<Clinicas>> {

public  DownloadClinicasInterface delegate;
private Context context;
private ProgressDialog dialog;
private int tipoConsulta;

public DownloadClinicasAsyncTask(Context context, int tipoConsulta) {
        this.context = context;
        this.tipoConsulta=tipoConsulta;
        dialog = new ProgressDialog(context);

        }

public  interface DownloadClinicasInterface{
    void onClinicasDownloaded( ArrayList<Clinicas> clinicasList);
}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Consultando clinicas ...");
        dialog.show();
    }

    @Override
    protected ArrayList<Clinicas> doInBackground(URL... urls) {
        String clinicaData;
        ArrayList<Clinicas> clinicasList=null;
        try{
            clinicaData=downloadData(urls[0]);
            clinicasList=parseDataFronJson(clinicaData);
            if(tipoConsulta==0){
                Borra_elementos();
                saveClinicasOnDatabase(clinicasList);
            }
        }
        catch(IOException e){

            e.printStackTrace();
        }
        return clinicasList;
    }

    @Override
    protected void onPostExecute(ArrayList<Clinicas> clinicasList) {
        super.onPostExecute(clinicasList);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        delegate.onClinicasDownloaded(clinicasList);
    }

    //Descargar datos de internet
    private String downloadData(URL url) throws IOException {
        String jsonResponse="";
        HttpURLConnection urlConnection =null;
        InputStream inputStream=null;

        try{
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);}
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection!= null){
                urlConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return  jsonResponse;
    }

    // El contenido que se descarga se pasa a formato jason
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader =new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader= new BufferedReader(inputStreamReader);
            String line=reader.readLine();
            while(line!=null){
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }

    public void Borra_elementos(){
        ClinicasDbHelper dbHelper = new ClinicasDbHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.delete(ClinicasContract.ClinicasColumns.TABLE_NAME, null, null);
        database.close();
    }

    // cargar los datos a la base de datos interna
    private void saveClinicasOnDatabase(ArrayList<Clinicas> clinicasList) {
        ClinicasDbHelper dbHelper = new ClinicasDbHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        for (Clinicas clinicas : clinicasList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ClinicasContract.ClinicasColumns.CLINICANAME,clinicas.getClinicaName());
            contentValues.put(ClinicasContract.ClinicasColumns.CLINICAADDRESS, clinicas.getClinicaAddress());
            contentValues.put(ClinicasContract.ClinicasColumns.CLINICAPHONE1, clinicas.getClinicaPhone1());
            contentValues.put(ClinicasContract.ClinicasColumns.CLINICAPHONE2, clinicas.getClinicaPhone2());
            contentValues.put(ClinicasContract.ClinicasColumns.CLINICAURLFB, clinicas.getClinicaURLfb());
            contentValues.put(ClinicasContract.ClinicasColumns.LATMAPS, clinicas.getClinicaLat());
            contentValues.put(ClinicasContract.ClinicasColumns.LONMAPS, clinicas.getClinicaLon());
            contentValues.put(ClinicasContract.ClinicasColumns.CLINICACOUNTRY, clinicas.getClinicaCountry());
            contentValues.put(ClinicasContract.ClinicasColumns.CLINICASTATE, clinicas.getClinicaState());
            contentValues.put(ClinicasContract.ClinicasColumns.CLINICASERVDOMICILIO, clinicas.getClinicaServicioDomicilio());
            contentValues.put(ClinicasContract.ClinicasColumns.CLINICASERVPENSION, clinicas.getClinicaServicioPension());
            contentValues.put(ClinicasContract.ClinicasColumns.CLINICASERV24HRS, clinicas.getClinicaServicio24hrs());
            database.insert(ClinicasContract.ClinicasColumns.TABLE_NAME, null, contentValues);
        }
    }

    //parsear los datos decargados a objetos
    private ArrayList<Clinicas> parseDataFronJson( String edoData){
        ArrayList <Clinicas> clinicasList = new ArrayList<>();
        try {
            //{jobject}
            JSONObject jsonObject = new JSONObject(edoData);
            //[jarray]
            JSONArray clinicaJsonArray =jsonObject.getJSONArray("clinicas");
            for (int i=0;i<clinicaJsonArray.length();i++){
                JSONObject clinicasJsonObject=clinicaJsonArray.getJSONObject(i);
                //{}
                String clinicaName=clinicasJsonObject.getString("clinicaName");
                String clinicaAddress=clinicasJsonObject.getString("clinicaAddress");
                String clinicaPhone1=clinicasJsonObject.getString("clinicaPhone1");
                String clinicaPhone2=clinicasJsonObject.getString("clinicaPhone2");
                String clinicaURLfb=clinicasJsonObject.getString("clinicaURLfb");
                String latMaps=clinicasJsonObject.getString("latMaps");
                String lonMaps=clinicasJsonObject.getString("lonMaps");
                String clinicaCountry=clinicasJsonObject.getString("clinicaCountry");
                String clinicaState=clinicasJsonObject.getString("clinicaState");
                String clinicaServicioDomicilio=clinicasJsonObject.getString("clinicaServicioDomicilio");
                String clinicaServicioPension=clinicasJsonObject.getString("clinicaServicioPension");
                String clinicaServicio24hrs=clinicasJsonObject.getString("clinicaServicio24hrs");

                clinicasList.add(new Clinicas(clinicaName,clinicaAddress,clinicaPhone1,clinicaPhone2,clinicaURLfb,latMaps,lonMaps,clinicaCountry,clinicaState,clinicaServicioDomicilio,clinicaServicioPension,clinicaServicio24hrs));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return clinicasList;
    }
}
