package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.model.ModelSendSterileDetailGroupBy;

import java.util.List;

public class SendSterileDetailAdapter extends ArrayAdapter<ModelSendSterileDetailGroupBy> {

    private final List<ModelSendSterileDetailGroupBy> DATA_MODEL;
    private final Activity context;

    public SendSterileDetailAdapter(Activity context, List<ModelSendSterileDetailGroupBy> DATA_MODEL) {
        super(context, R.layout.activity_list_send_sterile_detail, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_send_sterile_detail, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.txt_id = (TextView) view.findViewById(R.id.txt_id);
            viewHolder.txt_no = (TextView) view.findViewById(R.id.txt_no);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.txt_qty = (TextView) view.findViewById(R.id.txt_qty);
            viewHolder.chk = (ImageView) view.findViewById(R.id.chk);


            viewHolder.chk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        DATA_MODEL.get(viewHolder.index).setCheck( ! DATA_MODEL.get(viewHolder.index).IsCheck );
                        viewHolder.chk.setImageResource( DATA_MODEL.get(position).getCheck() );
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
        final SendSterileDetailAdapter.ViewHolder holder = (SendSterileDetailAdapter.ViewHolder) view.getTag();
        holder.txt_id.setText( DATA_MODEL.get(position).getI_id());
        holder.txt_no.setText( DATA_MODEL.get(position).getRow());
        holder.txt_item_name.setText(DATA_MODEL.get(position).getI_name());
        holder.txt_qty.setText(DATA_MODEL.get(position).getI_qty());
        holder.chk.setImageResource(DATA_MODEL.get(position).getCheck() );
        holder.index = (DATA_MODEL.get(position).getIndex());
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        int index;
        TextView txt_id;
        TextView txt_no;
        TextView txt_item_name;
        TextView txt_qty;
        ImageView chk;
    }

}