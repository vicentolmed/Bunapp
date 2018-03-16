package com.hooki.bunapp.AsyncTask;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.hooki.bunapp.Adapters.Pais;

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

public class DownloadPaisAsyncTask extends AsyncTask<URL,Void,ArrayList<Pais>> {

    public  DownloadPaisInterface delegate;
    private Context context;
    private ProgressDialog dialog;


    public DownloadPaisAsyncTask(Context context) {
        this.context = context;
        dialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Consultando países ...");
        dialog.show();
    }

    public  interface DownloadPaisInterface{
        void onPaisesDownloaded( ArrayList<Pais> paisList);
    }


    @Override
    protected ArrayList<Pais> doInBackground(URL... urls) {
        String paisData;
        ArrayList<Pais> paisList=null;
        try{
            paisData=downloadData(urls[0]);
            paisList=parseDataFronJson(paisData);
        }
        catch(IOException e){

            e.printStackTrace();
        }
        return paisList;
    }

    @Override
    protected void onPostExecute(ArrayList<Pais> paisList) {
        super.onPostExecute(paisList);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        delegate.onPaisesDownloaded(paisList);
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
    private ArrayList<Pais> parseDataFronJson( String paisData){
        ArrayList <Pais> paisList = new ArrayList<>();
        try {
            //{jobject}
            JSONObject jsonObject = new JSONObject(paisData);
            //[jarray]
            JSONArray paisJsonArray =jsonObject.getJSONArray("pais");
            for (int i=0;i<paisJsonArray.length();i++){
                JSONObject paisesJsonObject=paisJsonArray.getJSONObject(i);
                //{}
                String namePaises=paisesJsonObject.getString("clinicaCountry");

                paisList.add(new Pais(namePaises));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return paisList;
    }

}
