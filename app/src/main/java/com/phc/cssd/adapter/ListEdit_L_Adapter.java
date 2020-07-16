package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.cssd.CssdEditSterile;
import com.phc.cssd.R;
import com.phc.cssd.model.ModelItemStock;

import java.util.List;

public class ListEdit_L_Adapter extends ArrayAdapter<ModelItemStock> {

    private final List<ModelItemStock> DATA_MODEL;
    private final Activity context;

    public ListEdit_L_Adapter(Activity context, List<ModelItemStock> DATA_MODEL) {
        super(context, R.layout.activity_list_edit_l, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_edit_l, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
            viewHolder.txt_item_code = (TextView) view.findViewById(R.id.txt_item_code);
            viewHolder.txt_no = (TextView) view.findViewById(R.id.txt_no);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.imv_add = (ImageView) view.findViewById(R.id.imv_add);

            viewHolder.imv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CssdEditSterile)context).toR(viewHolder.ID);
                }
            });


            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ListEdit_L_Adapter.ViewHolder holder = (ListEdit_L_Adapter.ViewHolder) view.getTag();
        holder.txt_item_code.setText( DATA_MODEL.get(position).getUsageCode());
        holder.txt_no.setText( (DATA_MODEL.get(position).getI()+1) + ".");
        holder.txt_item_name.setText(DATA_MODEL.get(position).getItemname());
        holder.index = (DATA_MODEL.get(position).getI());

        holder.RowID = DATA_MODEL.get(position).getRowID();
        holder.ID = DATA_MODEL.get(position).getID();
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        int index;
        String ID;
        String RowID;

        TextView txt_item_code;
        TextView txt_no;
        TextView txt_item_name;
        ImageView imv_add;
        RelativeLayout relativeLayout;

    }

}