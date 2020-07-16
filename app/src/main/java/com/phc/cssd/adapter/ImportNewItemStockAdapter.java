package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.model.ModelImportWashDetail;

import java.util.List;

public class ImportNewItemStockAdapter extends ArrayAdapter<ModelImportWashDetail> {

    private final List<ModelImportWashDetail> DATA_MODEL;
    private final Activity context;

    public ImportNewItemStockAdapter(Activity context, List<ModelImportWashDetail> DATA_MODEL) {
        super(context, R.layout.activity_list_new_item_stock, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_new_item_stock, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.relativeLayout = (LinearLayout) view.findViewById(R.id.relativeLayout);
            viewHolder.chk = (CheckBox) view.findViewById(R.id.chk);
            viewHolder.txt_code = (TextView) view.findViewById(R.id.txt_code);
            viewHolder.txt_name = (TextView) view.findViewById(R.id.txt_name);
            viewHolder.txt_packingmat = (TextView) view.findViewById(R.id.txt_packingmat);
            viewHolder.txt_date = (TextView) view.findViewById(R.id.txt_date);

            viewHolder.chk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        DATA_MODEL.get(viewHolder.index).setCheck( ! DATA_MODEL.get(viewHolder.index).isCheck() );
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });

            viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        viewHolder.chk.setChecked(!viewHolder.chk.isChecked());
                        DATA_MODEL.get(viewHolder.index).setCheck( !DATA_MODEL.get(viewHolder.index).isCheck() );
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
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.txt_name.setText(DATA_MODEL.get(position).getI_name());
        holder.txt_code.setText(DATA_MODEL.get(position).getI_code());
        holder.txt_packingmat.setText(DATA_MODEL.get(position).getPackingMat());
        holder.txt_date.setText(DATA_MODEL.get(position).getI_barcode()); // Pack Date
        holder.chk.setChecked(DATA_MODEL.get(position).isCheck());
        holder.index = (DATA_MODEL.get(position).getIndex());

        holder.RowID = DATA_MODEL.get(position).getI_id();
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        LinearLayout relativeLayout;
        CheckBox chk;
        int index;
        String RowID;
        TextView txt_code;
        TextView txt_name;
        TextView txt_packingmat;
        TextView txt_date;
    }

}