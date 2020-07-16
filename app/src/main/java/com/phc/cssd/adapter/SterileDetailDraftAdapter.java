package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.model.ModelSterileDetail;

import java.util.List;

public class SterileDetailDraftAdapter extends ArrayAdapter<ModelSterileDetail> {

    private final List<ModelSterileDetail> DATA_MODEL;
    private final Activity context;

    public SterileDetailDraftAdapter(Activity context, List<ModelSterileDetail> DATA_MODEL) {
        super(context, R.layout.activity_list_draft_sterile_detail, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_draft_sterile_detail, null);

            final SterileDetailDraftAdapter.ViewHolder viewHolder = new SterileDetailDraftAdapter.ViewHolder();

            viewHolder.txt_id = (TextView) view.findViewById(R.id.txt_id);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.txt_usage_code = (TextView) view.findViewById(R.id.txt_usage_code);
            viewHolder.txt_qty = (TextView) view.findViewById(R.id.txt_qty);
            viewHolder.txt_age = (TextView) view.findViewById(R.id.txt_age);
            viewHolder.imv_print = (ImageView) view.findViewById(R.id.imv_print);


            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final SterileDetailDraftAdapter.ViewHolder holder = (SterileDetailDraftAdapter.ViewHolder) view.getTag();
        holder.txt_id.setText(DATA_MODEL.get(position).getID());
        holder.txt_item_name.setText(DATA_MODEL.get(position).getItemname());
        holder.txt_usage_code.setText(DATA_MODEL.get(position).getUsageCode());
        holder.txt_qty.setText(DATA_MODEL.get(position).getQty());
        holder.txt_age.setText(DATA_MODEL.get(position).getAge());
        holder.index = (DATA_MODEL.get(position).getIndex());
        holder.imv_print.setImageResource( DATA_MODEL.get(position).getPrintStatus() );
        // =========================================================================================


        return view;
    }

    static class ViewHolder {
        int index;
        TextView txt_id;
        TextView txt_item_name;
        TextView txt_usage_code;
        TextView txt_qty;
        TextView txt_age;
        ImageView imv_print;
        CheckBox chk;
    }

}