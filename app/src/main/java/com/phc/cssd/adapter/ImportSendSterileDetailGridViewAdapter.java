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
import com.phc.cssd.model.ModelSendSterileDetail;

import java.util.List;

public class ImportSendSterileDetailGridViewAdapter extends ArrayAdapter<ModelSendSterileDetail> {

    private final List<ModelSendSterileDetail> DATA_MODEL;
    private final Activity context;
    private boolean IsActive;

    public ImportSendSterileDetailGridViewAdapter(Activity context, List<ModelSendSterileDetail> DATA_MODEL, boolean IsActive) {
        super(context, R.layout.activity_grid_import_send_sterile_detail, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.IsActive = IsActive;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_grid_import_send_sterile_detail, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
            viewHolder.txt_item_code = (TextView) view.findViewById(R.id.txt_item_code);
            viewHolder.txt_item_program_id = (TextView) view.findViewById(R.id.txt_item_program_id);
            viewHolder.txt_no = (TextView) view.findViewById(R.id.txt_no);
            viewHolder.txt_program = (TextView) view.findViewById(R.id.txt_program);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.txt_qty = (TextView) view.findViewById(R.id.txt_qty);
            viewHolder.imv_add = (ImageView) view.findViewById(R.id.imv_add);
            viewHolder.txt_packingmat = (TextView) view.findViewById(R.id.txt_packingmat);

            viewHolder.imv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CssdWash)context).importSendSterileDetail( viewHolder.id.toString(), viewHolder.program_id.toString(), viewHolder.program_name.toString());
                }
            });


            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ImportSendSterileDetailGridViewAdapter.ViewHolder holder = (ImportSendSterileDetailGridViewAdapter.ViewHolder) view.getTag();
        holder.txt_item_code.setText( DATA_MODEL.get(position).getI_id());
        holder.txt_no.setText(  DATA_MODEL.get(position).getIsWashDept() + (DATA_MODEL.get(position).getIndex()+1) + ".");
        holder.txt_item_program_id.setText( DATA_MODEL.get(position).getI_program_id());
        holder.item_program = DATA_MODEL.get(position).getI_program();
        holder.txt_program.setText(DATA_MODEL.get(position).getI_program());
        holder.txt_item_name.setText(DATA_MODEL.get(position).getI_name());
        holder.txt_qty.setText(DATA_MODEL.get(position).getI_qty());
        holder.txt_packingmat.setText( DATA_MODEL.get(position).getPackingMat() + "( " + DATA_MODEL.get(position).getShelflife() + " วัน )");
        holder.index = (DATA_MODEL.get(position).getIndex());
        holder.PackingMatID = DATA_MODEL.get(position).getPackingMatID();

        holder.program_id = DATA_MODEL.get(position).getI_program_id();
        holder.program_name = DATA_MODEL.get(position).getI_program();
        holder.id = DATA_MODEL.get(position).getI_id();
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        int index;
        TextView txt_item_code;
        TextView txt_no;
        TextView txt_item_program_id;
        TextView txt_program;
        TextView txt_item_name;
        TextView txt_qty;
        ImageView imv_add;
        RelativeLayout relativeLayout;
        String item_program;
        TextView txt_packingmat;
        String PackingMatID;

        String id;
        String program_id;
        String program_name;
    }

}