package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.model.Model;

import java.util.List;

public class BasketAdapter extends ArrayAdapter<Model> {

    private final List<Model> DATA_MODEL;
    private final Activity context;

    public BasketAdapter(Activity context, List<Model> DATA_MODEL) {
        super(context, R.layout.list_basket, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.list_basket, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.txt_id = ( TextView ) view.findViewById(R.id.txt_id);
            viewHolder.txt_name = (TextView) view.findViewById(R.id.txt_name);
            viewHolder.txt_code = (TextView) view.findViewById(R.id.txt_code);

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final BasketAdapter.ViewHolder holder = (BasketAdapter.ViewHolder) view.getTag();
        holder.txt_id.setText(DATA_MODEL.get(position).getId());
        holder.txt_name.setText(DATA_MODEL.get(position).getName());
        holder.txt_code.setText(DATA_MODEL.get(position).getCode());
        holder.index = (DATA_MODEL.get(position).getIndex());


        // =========================================================================================


        return view;
    }

    static class ViewHolder {

        int index;
        TextView txt_id;
        TextView txt_name;
        TextView txt_code;
    }

}