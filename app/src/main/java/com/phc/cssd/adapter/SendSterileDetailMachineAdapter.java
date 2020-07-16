package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.cssd.CssdWash;
import com.phc.cssd.R;
import com.phc.cssd.model.ModelSendSterileDetailMachine;

import java.util.List;

public class SendSterileDetailMachineAdapter extends ArrayAdapter<ModelSendSterileDetailMachine> {

    private final List<ModelSendSterileDetailMachine> DATA_MODEL;
    private final Activity context;

    public SendSterileDetailMachineAdapter(Activity context, List<ModelSendSterileDetailMachine> DATA_MODEL) {
        super(context, R.layout.activity_list_import_send_sterile_detail, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_import_send_sterile_detail, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.txt_id = (TextView) view.findViewById(R.id.txt_id);
            viewHolder.txt_wash_process = (TextView) view.findViewById(R.id.txt_wash_process);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.txt_qty = (TextView) view.findViewById(R.id.txt_qty);
            viewHolder.chk = (CheckBox) view.findViewById(R.id.chk);
            viewHolder.imv_add = (ImageView) view.findViewById(R.id.imv_add);
            viewHolder.txt_item_code = (TextView) view.findViewById(R.id.txt_item_code);

            viewHolder.chk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        DATA_MODEL.get(viewHolder.index).setCheck( ! DATA_MODEL.get(viewHolder.index).IsCheck );
                        //viewHolder.chk.setImageResource( DATA_MODEL.get(position).getCheck() );
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });

            viewHolder.imv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CssdWash)context).importSendSterileDetail( viewHolder.txt_id.getText().toString(), viewHolder.program_id.toString(), viewHolder.program_name.toString());
                }
            });

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final SendSterileDetailMachineAdapter.ViewHolder holder = (SendSterileDetailMachineAdapter.ViewHolder) view.getTag();
        holder.txt_id.setText( DATA_MODEL.get(position).getI_id());
        holder.txt_wash_process.setText( DATA_MODEL.get(position).getI_program());
        holder.txt_item_name.setText(DATA_MODEL.get(position).getI_name());
        holder.txt_qty.setText(DATA_MODEL.get(position).getI_qty());
        //holder.chk.setImageResource(DATA_MODEL.get(position).getCheck() );
        holder.chk.setChecked(DATA_MODEL.get(position).isCheck());
        holder.index = (DATA_MODEL.get(position).getIndex());
        holder.program_id = DATA_MODEL.get(position).getI_program_id();
        holder.program_name = DATA_MODEL.get(position).getI_program();
        holder.txt_item_code.setText(DATA_MODEL.get(position).getI_code());
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        int index;
        TextView txt_id;
        TextView txt_wash_process;
        TextView txt_item_name;
        TextView txt_qty;
        ImageView imv_add;
        CheckBox chk;
        String program_id;
        String program_name;
        TextView txt_item_code;
    }

}