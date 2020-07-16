package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.cssd.CssdWash;
import com.phc.cssd.R;
import com.phc.cssd.model.ModelWashDetail;

import java.util.List;

public class WashDetailAdapter extends ArrayAdapter<ModelWashDetail> {

    private final List<ModelWashDetail> DATA_MODEL;
    private final Activity context;
    private final boolean IsActive;

    public WashDetailAdapter(Activity context, List<ModelWashDetail> DATA_MODEL, boolean IsActive) {
        super(context, R.layout.activity_list_wash_detail_data, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.IsActive = IsActive;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_wash_detail_data, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.relativeLayout = ( RelativeLayout ) view.findViewById(R.id.relativeLayout);
            viewHolder.txt_id = (TextView) view.findViewById(R.id.txt_id);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.txt_usage_code = (TextView) view.findViewById(R.id.txt_usage_code);
            viewHolder.txt_qty = (TextView) view.findViewById(R.id.txt_qty);
            viewHolder.txt_age = (TextView) view.findViewById(R.id.txt_age);
            viewHolder.imv_remove = (ImageView) view.findViewById(R.id.imv_remove);
            viewHolder.txt_basket = (TextView) view.findViewById(R.id.txt_basket);
            viewHolder.isRemark = (ImageView) view.findViewById(R.id.isRemark);

            viewHolder.imv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CssdWash)context).removeWashDetail( viewHolder.ImportID.toString(), viewHolder.ID.toString(), viewHolder.ItemStockID.toString() );
                }
            });

            /*
            viewHolder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    ((CssdWash)context).openDialogBasketManagement( DATA_MODEL );
                    return false;
                }
            });
            */

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final WashDetailAdapter.ViewHolder holder = (WashDetailAdapter.ViewHolder) view.getTag();
        holder.txt_id.setText(DATA_MODEL.get(position).getID());
        holder.txt_item_name.setText(DATA_MODEL.get(position).getItemname());
        holder.txt_usage_code.setText(DATA_MODEL.get(position).getUsageCode());
        holder.txt_qty.setText(DATA_MODEL.get(position).getQty());
        holder.txt_age.setText(DATA_MODEL.get(position).getAge());
        holder.txt_basket.setText(DATA_MODEL.get(position).getBasketName());
        holder.imv_remove.setImageResource( IsActive ? R.drawable.ic_left_grey : R.drawable.ic_left_blue );
        holder.index = (DATA_MODEL.get(position).getIndex());
        holder.ID = DATA_MODEL.get(position).getID();
        holder.ImportID = DATA_MODEL.get(position).getImportID();
        holder.ItemStockID = DATA_MODEL.get(position).getItemStockID();

        if (DATA_MODEL.get(position).getIsRemarkExpress().equals("1")){
            holder.isRemark.setVisibility(View.VISIBLE);
        }else {
            holder.isRemark.setVisibility(View.GONE);
        }
        // =========================================================================================


        return view;
    }

    static class ViewHolder {
        RelativeLayout relativeLayout;

        int index;
        TextView txt_id;
        TextView txt_item_name;
        TextView txt_usage_code;
        TextView txt_qty;
        TextView txt_age;
        TextView txt_basket;
        ImageView imv_remove;
        ImageView isRemark;

        String ID;
        String ImportID;
        String ItemStockID;

    }

}