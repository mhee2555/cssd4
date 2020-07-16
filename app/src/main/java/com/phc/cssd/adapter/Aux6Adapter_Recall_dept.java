package com.phc.cssd.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.phc.cssd.R;
import com.phc.cssd.Recall_dept;
import com.phc.cssd.properties.Response_Aux_Recall_dept;

import java.util.ArrayList;

public class Aux6Adapter_Recall_dept extends ArrayAdapter {

    private ArrayList<Response_Aux_Recall_dept> listData ;
    private Context context;

    public Aux6Adapter_Recall_dept(Activity aActivity, ArrayList<Response_Aux_Recall_dept> listData) {
        super(aActivity, 0, listData);
        this.context = aActivity;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.list_recall, parent, false);
        TextView tFields1 = (TextView) v.findViewById(R.id.tFields1);
        TextView tFields2 = (TextView) v.findViewById(R.id.tFields2);
        TextView tFields3 = (TextView) v.findViewById(R.id.tFields3);
        TextView tFields4 = (TextView) v.findViewById(R.id.tFields4);
        TextView tFields5 = (TextView) v.findViewById(R.id.tFields5);
        TextView tFields6 = (TextView) v.findViewById(R.id.tFields6);
        Button confirmbtn = (Button) v.findViewById(R.id.button_submit);
        Button cancelbtn = (Button) v.findViewById(R.id.button_cancel);

        tFields1.setText( listData.get(position).getFields1() );
        tFields2.setText( listData.get(position).getFields2() );
        tFields3.setText( listData.get(position).getFields3() );
        tFields4.setText( listData.get(position).getFields4() );
        tFields5.setText( listData.get(position).getFields5() );
        tFields6.setText( listData.get(position).getFields6() );

        Log.d("ADAP",listData.get(position).isIs_Check()+"");


        if(listData.get(position).isIs_Check() == true){
            confirmbtn.setVisibility(v.VISIBLE);
        }else{
            confirmbtn.setVisibility(v.INVISIBLE);
        }

        if(listData.get(position).isIs_Check2() == true){
            cancelbtn.setVisibility(v.VISIBLE);
        }else{
            cancelbtn.setVisibility(v.INVISIBLE);
        }


        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listData.get(position).isIs_Check() == true) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    builder.setTitle("Confirm");
                    builder.setMessage("ต้องการยืนยันการส่งคืนของหรือไม่");
                    builder.setPositiveButton("ตกลง",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ( (Recall_dept)context ).submitrecall(listData.get(position).getFields1());
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listData.get(position).isIs_Check2() == true) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    builder.setTitle("Confirm");
                    builder.setMessage("ต้องการยกเลิกการส่งคืนของหรือไม่");
                    builder.setPositiveButton("ตกลง",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ( (Recall_dept)context ).cancelrecall(listData.get(position).getFields1());
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });

        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String bool;
                if(listData.get(position).isIs_Check() == true){
                    bool = "true";
                }else{
                    bool = "false";
                }
                ( (Recall_dept)context ).setdetail_itemset(listData.get(position).getFields1(),bool);
                ( (Recall_dept)context ).setclick_Doc(listData.get(position).getFields1(),
                        listData.get(position).getFields2(),
                        listData.get(position).getFields3(),
                        listData.get(position).getFields4(),
                        listData.get(position).getFields5(),
                        listData.get(position).getFields8()
                        );
            }
        });

        return v;
    }

}
