package com.phc.cssd.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.cssd.CssdTakeBack;
import com.phc.cssd.R;
import com.phc.cssd.model.ModelPayoutDetail;

import java.util.List;

public class ListPayoutDetailBorrowAdapter extends ArrayAdapter<ModelPayoutDetail> {

    private final List<ModelPayoutDetail> DATA_MODEL;
    private final Activity context;
    boolean IsEnable = true;
    private String type = "";

    public ListPayoutDetailBorrowAdapter(Activity context, List<ModelPayoutDetail> DATA_MODEL, boolean IsEnable, final String type) {
        super(context, R.layout.list_payout_detail_borrow, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.IsEnable = IsEnable;
        this.type = type;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.list_payout_detail_borrow, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.txt_no = (TextView) view.findViewById(R.id.txt_no);
            viewHolder.txt_item_code = (TextView) view.findViewById(R.id.txt_item_code);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.txt_qty = (TextView) view.findViewById(R.id.txt_qty);
            viewHolder.txt_balance_qty = (TextView) view.findViewById(R.id.txt_balance_qty);
            viewHolder.txt_enter_qty = (EditText) view.findViewById(R.id.txt_enter_qty);
            viewHolder.txt_qr_qty = (TextView) view.findViewById(R.id.txt_qr_qty);
            viewHolder.del = (ImageView) view.findViewById(R.id.del);

            if(DATA_MODEL.get(position).getBalance_Qty().equals("0")){
                viewHolder.txt_enter_qty.setEnabled(false);
                viewHolder.txt_enter_qty.setBackground(ContextCompat.getDrawable(context, R.drawable.text_standard_gray));
            }else{
                viewHolder.txt_enter_qty.setEnabled(true);
            }

            if (type.equals("1")){
                viewHolder.del.setVisibility(View.GONE);
            }else {
                if (DATA_MODEL.get(position).getIsBorrowStatus().equals("5")){
                    viewHolder.del.setVisibility(View.GONE);
                }else {
                    viewHolder.del.setVisibility(View.VISIBLE);
                }
                viewHolder.txt_qty.setVisibility(View.GONE);
                viewHolder.txt_balance_qty.setVisibility(View.GONE);
                viewHolder.txt_enter_qty.setVisibility(View.GONE);
            }

            viewHolder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    builder.setTitle("ยืนยัน");
                    builder.setMessage("ต้องการลบรายการหรือไม่ ?");
                    builder.setPositiveButton("ตกลง",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    (( CssdTakeBack ) context).DelUsage(DATA_MODEL.get(position).getItemcode(),DATA_MODEL.get(position).getDocNo());
                                }
                            });
                    builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            viewHolder.txt_enter_qty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(final View v, boolean hasFocus) {
                    if (!hasFocus) {
                        try {
                            int enter_qty = Integer.valueOf(viewHolder.txt_enter_qty.getText().toString()).intValue();
                            int qr_qty = Integer.valueOf(viewHolder.txt_qr_qty.getText().toString()).intValue();
                            int balance_qty = Integer.valueOf(viewHolder.txt_balance_qty.getText().toString()).intValue();
                            if((enter_qty + qr_qty) <= balance_qty){
                                DATA_MODEL.get(viewHolder.index).setEnter_Qty(viewHolder.txt_enter_qty.getText().toString());
                            }else{
                                viewHolder.txt_enter_qty.setText("0");
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            });

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ListPayoutDetailBorrowAdapter.ViewHolder holder = (ListPayoutDetailBorrowAdapter.ViewHolder) view.getTag();
        holder.txt_no.setText(DATA_MODEL.get(position).getNo());
        holder.txt_item_code.setText(DATA_MODEL.get(position).getItemcode());
        holder.txt_item_name.setText(DATA_MODEL.get(position).getItemname());
        holder.txt_qty.setText(DATA_MODEL.get(position).getQty());

        if (!type.equals("1")){
            holder.txt_qr_qty.setText("1");
            holder.txt_balance_qty.setText("0");
            holder.txt_enter_qty.setText("1");
            holder.txt_enter_qty.setEnabled(false);
        }else {
            holder.txt_enter_qty.setText(DATA_MODEL.get(position).getEnter_Qty());
            holder.txt_balance_qty.setText(DATA_MODEL.get(position).getBalance_Qty());
            holder.txt_qr_qty.setText(DATA_MODEL.get(position).getQR_Qty());
        }

        holder.index = (DATA_MODEL.get(position).getIndex());
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        int index;
        TextView txt_no;
        TextView txt_item_code;
        TextView txt_item_name;
        EditText txt_enter_qty;
        TextView txt_qty;
        TextView txt_balance_qty;
        TextView txt_qr_qty;
        ImageView del;
    }

}