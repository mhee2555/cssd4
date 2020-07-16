package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.model.ModelSterile;

import java.util.List;

public class SterileDraftAdapter extends ArrayAdapter<ModelSterile> {

    private final List<ModelSterile> DATA_MODEL;
    private final Activity context;

    public SterileDraftAdapter(Activity context, List<ModelSterile> DATA_MODEL) {
        super(context, R.layout.activity_list_draft_sterile, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_draft_sterile, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.txt_id = (TextView) view.findViewById(R.id.txt_id);
            viewHolder.txt_doc_date = (TextView) view.findViewById(R.id.txt_doc_date);
            viewHolder.txt_doc_no = (TextView) view.findViewById(R.id.txt_doc_no);
            viewHolder.txt_program = (TextView) view.findViewById(R.id.txt_program);
            viewHolder.txt_round = (TextView) view.findViewById(R.id.txt_round);
            viewHolder.txt_usr_sterile = (TextView) view.findViewById(R.id.txt_usr_sterile);
            viewHolder.txt_usr_approve = (TextView) view.findViewById(R.id.txt_usr_approve);
            viewHolder.txt_usr_prepare = (TextView) view.findViewById(R.id.txt_usr_prepare);
            viewHolder.txt_print_balance = (TextView) view.findViewById(R.id.txt_print_balance);
            viewHolder.label_print_balance = (TextView) view.findViewById(R.id.label_print_balance);
            viewHolder.sum_qty_int = (TextView) view.findViewById(R.id.sum_qty_int);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        // =========================================================================================
        final SterileDraftAdapter.ViewHolder holder = (SterileDraftAdapter.ViewHolder) view.getTag();
        holder.txt_id.setText(DATA_MODEL.get(position).getID());
        holder.txt_doc_date.setText(DATA_MODEL.get(position).getDocDate());
        holder.txt_doc_no.setText(DATA_MODEL.get(position).getDocNo());
        holder.txt_program.setText(DATA_MODEL.get(position).getSterileName());
        holder.txt_round.setText(DATA_MODEL.get(position).getSterileRoundNumber());
        holder.txt_usr_sterile.setText(DATA_MODEL.get(position).getUsr_sterile());
        holder.txt_usr_approve.setText(DATA_MODEL.get(position).getUsr_approve());
        holder.txt_usr_prepare.setText(DATA_MODEL.get(position).getUsr_prepare());
        if (DATA_MODEL.get(position).getPrintAll().equals("0")){
            holder.txt_print_balance.setText("ไม่มีรายการ");
            holder.sum_qty_int.setText("ไม่มีรายการ");
            holder.txt_print_balance.setVisibility(View.GONE);
            holder.label_print_balance.setVisibility(View.GONE);
        }else {
            holder.txt_print_balance.setText(DATA_MODEL.get(position).getPrintBalance()+" รายการ");
            holder.sum_qty_int.setText(DATA_MODEL.get(position).getPrintAll()+" รายการ");
            holder.txt_print_balance.setVisibility(View.VISIBLE);
            holder.label_print_balance.setVisibility(View.VISIBLE);
        }
        holder.index = (DATA_MODEL.get(position).getIndex());
        return view;
    }
    static class ViewHolder {
        int index;
        TextView txt_id;
        TextView txt_doc_date;
        TextView txt_doc_no;
        TextView txt_program;
        TextView txt_round;
        TextView txt_usr_sterile;
        TextView txt_usr_approve;
        TextView txt_usr_prepare;
        TextView txt_print_balance;
        TextView label_print_balance;
        TextView sum_qty_int;
    }
}