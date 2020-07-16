package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.model.ModelWashDetail;

import java.util.List;

public class WashDetailBasketAdapter extends ArrayAdapter<ModelWashDetail> {

    private final List<ModelWashDetail> DATA_MODEL;
    private final Activity context;

    public WashDetailBasketAdapter(Activity context, List<ModelWashDetail> DATA_MODEL) {
        super(context, R.layout.activity_wash_detail_basket_adapter, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_wash_detail_basket_adapter, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.relativeLayout = ( RelativeLayout ) view.findViewById(R.id.relativeLayout);
            viewHolder.txt_id = ( TextView ) view.findViewById(R.id.txt_id);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.txt_usage_code = (TextView) view.findViewById(R.id.txt_usage_code);
            viewHolder.chk_ = ( CheckBox ) view.findViewById(R.id.chk_);
            viewHolder.txt_detail = (TextView) view.findViewById(R.id.txt_detail);

            viewHolder.chk_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        DATA_MODEL.get(viewHolder.index).setCheck( ! DATA_MODEL.get(viewHolder.index).IsCheck );
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final WashDetailBasketAdapter.ViewHolder holder = (WashDetailBasketAdapter.ViewHolder) view.getTag();
        holder.txt_id.setText(DATA_MODEL.get(position).getID());
        holder.txt_item_name.setText(DATA_MODEL.get(position).getItemname());
        holder.txt_usage_code.setText(DATA_MODEL.get(position).getUsageCode());
        holder.chk_.setChecked(DATA_MODEL.get(position).isCheck());
        holder.index = (DATA_MODEL.get(position).getIndex());
        holder.txt_detail.setText(DATA_MODEL.get(position).getBasketName());
        // =========================================================================================


        return view;
    }

    static class ViewHolder {
        RelativeLayout relativeLayout;

        int index;
        TextView txt_id;
        TextView txt_item_name;
        TextView txt_usage_code;
        TextView txt_detail;
        CheckBox chk_;
    }

}