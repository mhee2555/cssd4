package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.model.ModelWashDetail;

import java.util.List;

public class WashDetailManagementGridViewAdapter extends ArrayAdapter<ModelWashDetail> {

    private final List<ModelWashDetail> DATA_MODEL;
    private final Activity context;

    public WashDetailManagementGridViewAdapter(Activity context, List<ModelWashDetail> DATA_MODEL) {
        super(context, R.layout.activity_grid_wash_detail_management, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_grid_wash_detail_management, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
            viewHolder.txt_item_code = (TextView) view.findViewById(R.id.txt_item_code);
            viewHolder.txt_no = (TextView) view.findViewById(R.id.txt_no);
            viewHolder.txt_packingmat = (TextView) view.findViewById(R.id.txt_packingmat);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.txt_qty = (TextView) view.findViewById(R.id.txt_qty);
            viewHolder.imv_add = (ImageView) view.findViewById(R.id.imv_add);
            viewHolder.txt_age = (TextView) view.findViewById(R.id.txt_age);
            viewHolder.chk = (CheckBox) view.findViewById(R.id.chk);

            viewHolder.chk.setOnClickListener(new View.OnClickListener() {
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
        final WashDetailManagementGridViewAdapter.ViewHolder holder = (WashDetailManagementGridViewAdapter.ViewHolder) view.getTag();
        holder.txt_item_code.setText( "รหัส : " + DATA_MODEL.get(position).getUsageCode());
        holder.txt_no.setText( (DATA_MODEL.get(position).getIndex()+1) + ".");
        holder.txt_item_name.setText("รายการ : " + DATA_MODEL.get(position).getItemname());
        holder.txt_qty.setText(DATA_MODEL.get(position).getQty());
        holder.index = (DATA_MODEL.get(position).getIndex());
        holder.txt_packingmat.setText("การห่อ : " + DATA_MODEL.get(position).getPackingMat() + "( " + DATA_MODEL.get(position).getShelflife() + " วัน )");
        holder.txt_age.setText("อายุ : " + DATA_MODEL.get(position).getAge() + " วัน");
        holder.chk.setChecked(DATA_MODEL.get(position).isCheck());
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        int index;
        TextView txt_item_code;
        TextView txt_no;
        TextView txt_packingmat;
        TextView txt_item_name;
        TextView txt_age;
        TextView txt_qty;
        ImageView imv_add;
        RelativeLayout relativeLayout;
        CheckBox chk;
    }

}