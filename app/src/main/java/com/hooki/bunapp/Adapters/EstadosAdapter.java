package com.hooki.bunapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
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

public class EstadosAdapter extends ArrayAdapter<Estados> {

    private ArrayList<Estados> estadosList;
    private Context context;
    private int LayoutId;

    public EstadosAdapter(Context context, int resource, List<Estados> estados) {
        super(context,resource,estados);

        this.context=context;
        this.LayoutId=resource;
        estadosList = new ArrayList<>(estados);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(LayoutId,null);

            holder=new ViewHolder();
            holder.item_name_edo=(TextView) convertView.findViewById(R.id.item_name_edo);

            convertView.setTag(holder);
        } else{
            holder=(ViewHolder) convertView.getTag();
        }

        Estados estado= estadosList.get(position);

        holder.item_name_edo.setText(estado.getNombreEstados());

        return convertView;
    }

    private class ViewHolder{
        public TextView item_name_edo;
    }
}
