package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.cssd.ItemSet;
import com.phc.cssd.R;
import com.phc.cssd.model.ModelItems;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<ModelItems> {

    private final List<ModelItems> DATA_MODEL;
    private final Activity context;

    public ItemAdapter(Activity context, List<ModelItems> DATA_MODEL) {
        super(context, R.layout.activity_list_item_data, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_item_data, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.txt_item_code = (TextView) view.findViewById(R.id.txt_item_code);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.imv_add = (ImageView) view.findViewById(R.id.imv_add);

            viewHolder.imv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ItemSet)context).onAddSet( viewHolder.txt_item_code.getText().toString() );
                }
            });

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ItemAdapter.ViewHolder holder = (ItemAdapter.ViewHolder) view.getTag();
        holder.txt_item_name.setText(DATA_MODEL.get(position).getItemcode() + " : " + DATA_MODEL.get(position).getItemname());
        holder.txt_item_code.setText(DATA_MODEL.get(position).getItemcode());
        holder.index = (DATA_MODEL.get(position).getIndex());
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        ImageView imv_add;
        TextView txt_item_code;
        TextView txt_item_name;
        int index;
    }


}