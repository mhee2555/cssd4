package com.phc.cssd.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.cssd.CssdPrintSterile;
import com.phc.cssd.CssdSterile;
import com.phc.cssd.R;
import com.phc.cssd.model.ModelSterileDetail;

import java.util.List;

public class SterileDetailBigSizeAdapter extends ArrayAdapter<ModelSterileDetail> {

    private final List<ModelSterileDetail> DATA_MODEL;
    private final Activity context;
    private final boolean IsActive;
    private String B_ID = null;

    public SterileDetailBigSizeAdapter(Activity context, List<ModelSterileDetail> DATA_MODEL, boolean IsActive,String B_ID) {
        super(context, R.layout.activity_list_big_size_sterile_detail, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.IsActive = IsActive;
        this.B_ID = B_ID;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_big_size_sterile_detail, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.txt_id = (TextView) view.findViewById(R.id.txt_id);
            //viewHolder.txt_department = (TextView) view.findViewById(R.id.txt_department);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.txt_usage_code = (TextView) view.findViewById(R.id.txt_usage_code);
            viewHolder.txt_qty = (TextView) view.findViewById(R.id.txt_qty);
            viewHolder.txt_age = (TextView) view.findViewById(R.id.txt_age);
            //viewHolder.chk = (ImageView) view.findViewById(R.id.chk);
            viewHolder.imv_remove = (ImageView) view.findViewById(R.id.imv_remove);
            viewHolder.imv_print = (ImageView) view.findViewById(R.id.imv_print);
            viewHolder.chk = (CheckBox) view.findViewById(R.id.chk);
            viewHolder.relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);

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

            viewHolder.imv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CssdSterile)context).removeSterileDetail(
                            viewHolder.ImportID,
                            viewHolder.ID,
                            viewHolder.ItemStockID
                    );
                }
            });

            viewHolder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    /*
                    System.out.println("ID = " +  viewHolder.ID);
                    System.out.println("DocNo = " +  viewHolder.DocNo);
                    System.out.println("ItemStockID = " +  viewHolder.ItemStockID);
                    System.out.println("Qty = " +  viewHolder.Qty);
                    System.out.println("itemname = " +  viewHolder.itemname);

                    System.out.println("itemcode = " +  viewHolder.itemcode);
                    System.out.println("UsageCode = " +  viewHolder.UsageCode);
                    System.out.println("age = " +  viewHolder.age);
                    System.out.println("ImportID = " +  viewHolder.ImportID);
                    System.out.println("SterileDate = " +  viewHolder.SterileDate);

                    System.out.println("ExpireDate = " +  viewHolder.ExpireDate);
                    System.out.println("IsStatus = " +  viewHolder.IsStatus);
                    System.out.println("OccuranceQty = " +  viewHolder.OccuranceQty);
                    System.out.println("DepName = " +  viewHolder.DepName);
                    System.out.println("DepName2 = " +  viewHolder.DepName2);

                    System.out.println("LabelID = " +  viewHolder.LabelID);
                    System.out.println("usr_sterile = " +  viewHolder.usr_sterile);
                    System.out.println("usr_prepare = " +  viewHolder.usr_prepare);
                    System.out.println("usr_approve = " +  viewHolder.usr_approve);
                    System.out.println("SterileRoundNumber = " +  viewHolder.SterileRoundNumber);

                    System.out.println("MachineName = " +  viewHolder.MachineName);
                    System.out.println("Price = " +  viewHolder.Price);
                    System.out.println("Time = " +  viewHolder.Time);

                    System.out.println("Qty_Print = " +  viewHolder.Qty_Print);
                    */

                    Intent intent = new Intent(context, CssdPrintSterile.class);
                    intent.putExtra("ID", viewHolder.ID);
                    intent.putExtra("DocNo",  viewHolder.DocNo);
                    intent.putExtra("ItemStockID",  viewHolder.ItemStockID);
                    intent.putExtra("Qty",  viewHolder.Qty);
                    intent.putExtra("itemname",  viewHolder.itemname);

                    intent.putExtra("itemcode",  viewHolder.itemcode);
                    intent.putExtra("UsageCode",  viewHolder.UsageCode);
                    intent.putExtra("age",  viewHolder.age);
                    intent.putExtra("ImportID",  viewHolder.ImportID);
                    intent.putExtra("SterileDate",  viewHolder.SterileDate);

                    intent.putExtra("ExpireDate",  viewHolder.ExpireDate);
                    intent.putExtra("IsStatus",  viewHolder.IsStatus);
                    intent.putExtra("OccuranceQty",  viewHolder.OccuranceQty);
                    intent.putExtra("DepName",  viewHolder.DepName);
                    intent.putExtra("DepName2",  viewHolder.DepName2);

                    intent.putExtra("LabelID",  viewHolder.LabelID);
                    intent.putExtra("usr_sterile",  viewHolder.usr_sterile);
                    intent.putExtra("usr_prepare",  viewHolder.usr_prepare);
                    intent.putExtra("usr_approve",  viewHolder.usr_approve);
                    intent.putExtra("SterileRoundNumber",  viewHolder.SterileRoundNumber);

                    intent.putExtra("MachineName",  viewHolder.MachineName);
                    intent.putExtra("Price",  viewHolder.Price);
                    intent.putExtra("Time",  viewHolder.Time);

                    intent.putExtra("Qty_Print",  viewHolder.Qty_Print);

                    context.startActivity(intent);

                    return false;
                }
            });

            viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
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

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.txt_id.setText(DATA_MODEL.get(position).getID());
        //holder.txt_department.setText(DATA_MODEL.get(position).getDepName2());
        holder.txt_item_name.setText(DATA_MODEL.get(position).getItemname()); // + " - " + DATA_MODEL.get(position).getUsageCount());
        holder.txt_usage_code.setText(DATA_MODEL.get(position).getUsageCode());
        holder.txt_qty.setText(DATA_MODEL.get(position).getQty());
        holder.txt_age.setText(DATA_MODEL.get(position).getAge());
        // holder.chk.setImageResource(DATA_MODEL.get(position).getCheck() );
        holder.chk.setChecked(DATA_MODEL.get(position).isCheck());
        holder.index = (DATA_MODEL.get(position).getIndex());
        holder.imv_print.setImageResource(DATA_MODEL.get(position).getPrintStatus() );
        holder.imv_remove.setImageResource( IsActive ? R.drawable.ic_left_grey : R.drawable.ic_left_blue );

        holder.ID = DATA_MODEL.get(position).getID();
        holder.DocNo = DATA_MODEL.get(position).getDocNo();
        holder.ItemStockID = DATA_MODEL.get(position).getItemStockID();
        holder.Qty = DATA_MODEL.get(position).getQty();
        holder.itemname = DATA_MODEL.get(position).getItemname();

        holder.itemcode = DATA_MODEL.get(position).getItemcode();
        holder.UsageCode = DATA_MODEL.get(position).getUsageCode();
        holder.age = DATA_MODEL.get(position).getAge();
        holder.ImportID = DATA_MODEL.get(position).getImportID();
        holder.SterileDate = DATA_MODEL.get(position).getSterileDate();

        holder.ExpireDate = DATA_MODEL.get(position).getExpireDate();
        holder.IsStatus = DATA_MODEL.get(position).getIsStatus();
        holder.OccuranceQty = DATA_MODEL.get(position).getOccuranceQty();
        holder.DepName = DATA_MODEL.get(position).getDepName();
        holder.DepName2 = DATA_MODEL.get(position).getDepName2();

        holder.LabelID = DATA_MODEL.get(position).getLabelID();
        holder.usr_sterile = DATA_MODEL.get(position).getUsr_sterile();
        holder.usr_prepare = DATA_MODEL.get(position).getUsr_prepare();
        holder.usr_approve = DATA_MODEL.get(position).getUsr_approve();
        holder.SterileRoundNumber = DATA_MODEL.get(position).getSterileRoundNumber();

        holder.MachineName = DATA_MODEL.get(position).getMachineName();
        holder.Price = DATA_MODEL.get(position).getPrice();
        holder.Time = DATA_MODEL.get(position).getTime();

        holder.Qty_Print = DATA_MODEL.get(position).getQty_Print();
        // =========================================================================================

        /*
        holder.txt_department.setBackgroundResource( holder.index%2 == 0 ? R.color.even : R.color.odd);
        holder.txt_item_name.setBackgroundResource( holder.index%2 == 0 ? R.color.even : R.color.odd);
        holder.txt_usage_code.setBackgroundResource( holder.index%2 == 0 ? R.color.even : R.color.odd);
        holder.txt_qty.setBackgroundResource( holder.index%2 == 0 ? R.color.even : R.color.odd);
        holder.txt_age.setBackgroundResource( holder.index%2 == 0 ? R.color.even : R.color.odd);
        holder.chk.setBackgroundResource( holder.index%2 == 0 ? R.color.even : R.color.odd);
        */

        return view;
    }

    static class ViewHolder {
        int index;
        TextView txt_id;
        //TextView txt_department;
        TextView txt_item_name;
        TextView txt_usage_code;
        TextView txt_qty;
        TextView txt_age;
        ImageView imv_remove;
        ImageView imv_print;
        CheckBox chk;
        RelativeLayout relativeLayout;

        String ID;
        String DocNo;
        String ItemStockID;
        String Qty;
        String itemname;

        String itemcode;
        String UsageCode;
        String age;
        String ImportID;
        String SterileDate;

        String ExpireDate;
        String IsStatus;
        String OccuranceQty;
        String DepName;
        String DepName2;

        String LabelID;
        String usr_sterile;
        String usr_prepare;
        String usr_approve;
        String SterileRoundNumber;

        String MachineName;
        String Price;
        String Time;

        String Qty_Print;

    }

}