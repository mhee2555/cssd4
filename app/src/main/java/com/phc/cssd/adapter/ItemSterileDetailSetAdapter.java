package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.model.ModelItemDetail;

import java.util.List;

public class ItemSterileDetailSetAdapter extends ArrayAdapter<ModelItemDetail> {

    private final List<ModelItemDetail> DATA_MODEL;
    private final Activity context;

    public ItemSterileDetailSetAdapter(Activity context, List<ModelItemDetail> DATA_MODEL) {
        super(context, R.layout.activity_list_sterile_detail_set_data, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_sterile_detail_set_data, null);

            final ViewHolder viewHolder = new ViewHolder();
            
            viewHolder.txt_code = (TextView) view.findViewById(R.id.txt_code);
            viewHolder.txt_no = (TextView) view.findViewById(R.id.txt_no);
            viewHolder.txt_name = (TextView) view.findViewById(R.id.txt_name);
            viewHolder.txt_qty = (TextView) view.findViewById(R.id.txt_qty);
            viewHolder.txt_unit = (TextView) view.findViewById(R.id.txt_unit);

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ItemSterileDetailSetAdapter.ViewHolder holder = (ItemSterileDetailSetAdapter.ViewHolder) view.getTag();
        holder.txt_code.setText(DATA_MODEL.get(position).getID());
        holder.txt_no.setText(DATA_MODEL.get(position).getRow());
        holder.txt_name.setText(DATA_MODEL.get(position).getItemname());
        holder.txt_qty.setText(DATA_MODEL.get(position).getQty());
        //holder.txt_unit.setText(DATA_MODEL.get(position).get);
        holder.index = (DATA_MODEL.get(position).getIndex());
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        int index;
        TextView txt_code;
        TextView txt_no;
        TextView txt_name;
        TextView txt_qty;
        TextView txt_unit;
    }

}