package com.hooki.bunapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

            convertView.setTag(holder);
        } else{
            holder=(ViewHolder) convertView.getTag();
        }

        Clinicas clinica= clinicasList.get(position);

        holder.item_name_clinicas.setText(clinica.getClinicaName());

        return convertView;
    }

    private class ViewHolder{
        public TextView item_name_clinicas;
    }
}
