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
 * Created by 7icente on 12/03/2018.
 */

public class PaisAdapter extends ArrayAdapter<Pais> {
    private ArrayList<Pais> paisList;
    private Context context;
    private int LayoutId;

    public PaisAdapter(Context context, int resource, List<Pais> paises) {
        super(context,resource,paises);

        this.context=context;
        this.LayoutId=resource;
        paisList = new ArrayList<>(paises);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(LayoutId,null);

            holder=new ViewHolder();
            holder.item_namePais=(TextView) convertView.findViewById(R.id.item_name_pais);
            convertView.setTag(holder);
        } else{
            holder=(ViewHolder) convertView.getTag();
        }

        Pais pais= paisList.get(position);

        holder.item_namePais.setText(pais.getNombrePais());

        return convertView;
    }

    private class ViewHolder{
        public TextView item_namePais;
    }

}
