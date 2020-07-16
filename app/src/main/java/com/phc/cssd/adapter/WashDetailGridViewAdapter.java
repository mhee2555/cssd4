package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.cssd.CssdWash;
import com.phc.cssd.R;
import com.phc.cssd.model.ModelWashDetail;

import java.util.List;

public class WashDetailGridViewAdapter extends ArrayAdapter<ModelWashDetail> {

    private final List<ModelWashDetail> DATA_MODEL;
    private final Activity context;
    private final boolean IsActive;

    public WashDetailGridViewAdapter(Activity context, List<ModelWashDetail> DATA_MODEL, boolean IsActive) {
        super(context, R.layout.activity_grid_wash_detail, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.IsActive = IsActive;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_grid_wash_detail, null);

            final WashDetailBigSizeAdapter.ViewHolder viewHolder = new WashDetailBigSizeAdapter.ViewHolder();

            viewHolder.txt_id = (TextView) view.findViewById(R.id.txt_id);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.txt_usage_code = (TextView) view.findViewById(R.id.txt_usage_code);
            viewHolder.txt_qty = (TextView) view.findViewById(R.id.txt_qty);
            viewHolder.txt_age = (TextView) view.findViewById(R.id.txt_age);
            viewHolder.imv_remove = (ImageView) view.findViewById(R.id.imv_remove);


            viewHolder.imv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CssdWash)context).removeWashDetail( viewHolder.ImportID.toString(), viewHolder.ID.toString(), viewHolder.ItemStockID.toString() );
                }
            });

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final WashDetailBigSizeAdapter.ViewHolder holder = (WashDetailBigSizeAdapter.ViewHolder) view.getTag();
        holder.txt_id.setText(DATA_MODEL.get(position).getID());
        holder.txt_item_name.setText(DATA_MODEL.get(position).getItemname());
        holder.txt_usage_code.setText(DATA_MODEL.get(position).getUsageCode());
        holder.txt_qty.setText(DATA_MODEL.get(position).getQty());
        holder.txt_age.setText(DATA_MODEL.get(position).getAge());
        holder.index = (DATA_MODEL.get(position).getIndex());
        holder.imv_remove.setImageResource( IsActive ? R.drawable.ic_left_grey : R.drawable.ic_left_blue );

        holder.ID = DATA_MODEL.get(position).getID();
        holder.ImportID = DATA_MODEL.get(position).getImportID();
        holder.ItemStockID = DATA_MODEL.get(position).getItemStockID();

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
        ImageView imv_remove;

        String ID;
        String ImportID;
        String ItemStockID;

    }

}