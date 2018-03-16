package com.hooki.bunapp.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.hooki.bunapp.Adapters.Clinicas;

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

public DownloadClinicasAsyncTask(Context context) {
        this.context = context;
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
                String clinicaURLMaps=clinicasJsonObject.getString("clinicaURLMaps");
                String clinicaServicioDomicilio=clinicasJsonObject.getString("clinicaServicioDomicilio");
                String clinicaServicioPension=clinicasJsonObject.getString("clinicaServicioPension");
                String clinicaServicio24hrs=clinicasJsonObject.getString("clinicaServicio24hrs");

                clinicasList.add(new Clinicas(clinicaName,clinicaAddress,clinicaPhone1,clinicaPhone2,clinicaURLfb,clinicaURLMaps,clinicaServicioDomicilio,clinicaServicioPension,clinicaServicio24hrs));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return clinicasList;
    }
}
