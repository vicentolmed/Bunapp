package com.hooki.bunapp.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.hooki.bunapp.Adapters.Estados;

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


public class DownloadEdoAsyncTask extends AsyncTask<URL,Void,ArrayList<Estados>> {

    public  DownloadEstadosInterface delegate;
    private Context context;
    private ProgressDialog dialog;

    public DownloadEdoAsyncTask(Context context) {
        this.context = context;
        dialog = new ProgressDialog(context);
    }
    public  interface DownloadEstadosInterface{
        void onEstadosDownloaded( ArrayList<Estados> estadosList);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Consultando estados ...");
        dialog.show();
    }

    @Override
    protected ArrayList<Estados> doInBackground(URL... urls) {
        String edoData;
        ArrayList<Estados> estadosList=null;
        try{
            edoData=downloadData(urls[0]);
            estadosList=parseDataFronJson(edoData);
        }
        catch(IOException e){

            e.printStackTrace();
        }
        return estadosList;
    }

    @Override
    protected void onPostExecute(ArrayList<Estados> estadosList) {
        super.onPostExecute(estadosList);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        delegate.onEstadosDownloaded(estadosList);
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
    private ArrayList<Estados> parseDataFronJson( String edoData){
        ArrayList <Estados> estadoList = new ArrayList<>();
        try {
            //{jobject}
            JSONObject jsonObject = new JSONObject(edoData);
            //[jarray]
            JSONArray estadoJsonArray =jsonObject.getJSONArray("estados");
            for (int i=0;i<estadoJsonArray.length();i++){
                JSONObject estadosJsonObject=estadoJsonArray.getJSONObject(i);
                //{}
                String nameEstados=estadosJsonObject.getString("clinicaState");

                estadoList.add(new Estados(nameEstados));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return estadoList;
    }

}
