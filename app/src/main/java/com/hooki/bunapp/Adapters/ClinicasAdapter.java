package com.hooki.bunapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hooki.bunapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 7icente on 13/03/2018.
 */

public class ClinicasAdapter extends ArrayAdapter<Clinicas> {

    private ArrayList<Clinicas> clinicasList;
    private Context context;
    private int LayoutId;

    public ClinicasAdapter(Context context, int resource, List<Clinicas> clinicas ) {
        super(context,resource,clinicas);

        this.context=context;
        this.LayoutId=resource;
        clinicasList = new ArrayList<>(clinicas);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(LayoutId,null);

            holder=new ViewHolder();
            holder.item_name_clinicas=(TextView) convertView.findViewById(R.id.item_name_clinicas);
            holder.item_name_estado=(TextView) convertView.findViewById(R.id.item_name_estado);
            holder.icoBandera=(ImageView) convertView.findViewById(R.id.icoBandera);

            convertView.setTag(holder);
        } else{
            holder=(ViewHolder) convertView.getTag();
        }

        Clinicas clinica= clinicasList.get(position);

        holder.item_name_clinicas.setText(clinica.getClinicaName());
        holder.item_name_estado.setText(clinica.getClinicaState());

        if(clinica.getClinicaCountry().trim().equals("MEXICO")){
            holder.icoBandera.setBackgroundResource(R.drawable.mexico);
        }

        else if (clinica.getClinicaCountry().trim().equals("COSTA RICA")){
            holder.icoBandera.setBackgroundResource(R.drawable.costarica);
        }

        else if (clinica.getClinicaCountry().trim().equals("ARGENTINA")){
            holder.icoBandera.setBackgroundResource(R.drawable.argentina);
        }

        else if (clinica.getClinicaCountry().trim().equals("CHILE")){
            holder.icoBandera.setBackgroundResource(R.drawable.chile);
        }

        else if (clinica.getClinicaCountry().trim().equals("COLOMBIA")){
            holder.icoBandera.setBackgroundResource(R.drawable.colombia);
        }

        else if (clinica.getClinicaCountry().trim().equals("ECUADOR")){
            holder.icoBandera.setBackgroundResource(R.drawable.ecuador);
        }

        else if (clinica.getClinicaCountry().trim().equals("PERU")){
            holder.icoBandera.setBackgroundResource(R.drawable.peru);
        }


        else if (clinica.getClinicaCountry().trim().equals("ESPAÑA")){
            holder.icoBandera.setBackgroundResource(R.drawable.espana);
        }

        else if (clinica.getClinicaCountry().trim().equals("ITALIA")){
            holder.icoBandera.setBackgroundResource(R.drawable.italia);
        }

        return convertView;
    }

    private class ViewHolder{
        public TextView item_name_clinicas;
        public TextView item_name_estado;
        public ImageView icoBandera;
    }
}
