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

public class ItemDetailAdapter extends ArrayAdapter<ModelItemDetail> {

    private final List<ModelItemDetail> DATA_MODEL;
    private final Activity context;

    public ItemDetailAdapter(Activity context, List<ModelItemDetail> DATA_MODEL) {
        super(context, R.layout.activity_list_item_set_data, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_item_set_data, null);

            final ViewHolder viewHolder = new ViewHolder();

            //viewHolder.chk = (CheckBox) view.findViewById(R.id.chk);
            viewHolder.txt_code = (TextView) view.findViewById(R.id.txt_code);
            viewHolder.txt_name = (TextView) view.findViewById(R.id.txt_name);
            viewHolder.txt_qty = (TextView) view.findViewById(R.id.txt_qty);
            viewHolder.txt_unit = (TextView) view.findViewById(R.id.txt_unit);

            /*
            viewHolder.txt_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        viewHolder.chk.setChecked(!viewHolder.chk.isChecked());
                        DATA_MODEL.get(viewHolder.index).setChecked( viewHolder.chk.isChecked() );
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });

            viewHolder.txt_unit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        viewHolder.chk.setChecked(!viewHolder.chk.isChecked());
                        DATA_MODEL.get(viewHolder.index).setChecked( viewHolder.chk.isChecked() );
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });

            viewHolder.chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    DATA_MODEL.get(viewHolder.index).setChecked( isChecked );
                }
            });
            */

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ItemDetailAdapter.ViewHolder holder = (ItemDetailAdapter.ViewHolder) view.getTag();
        holder.txt_name.setText(DATA_MODEL.get(position).getItemname());
        holder.txt_code.setText(DATA_MODEL.get(position).getID_set());
        holder.txt_qty.setText(DATA_MODEL.get(position).getQty());
        holder.txt_unit.setText(DATA_MODEL.get(position).getUnitName());
        //holder.chk.setChecked(DATA_MODEL.get(position).isChecked());
        holder.index = (DATA_MODEL.get(position).getIndex());
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        //CheckBox chk;
        int index;
        TextView txt_code;
        TextView txt_name;
        TextView txt_qty;
        TextView txt_unit;
    }

}