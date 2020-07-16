package com.phc.cssd.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.phc.cssd.properties.Response_Aux_Recall;
import com.phc.cssd.R;
import com.phc.cssd.Recall;

import java.util.ArrayList;


public class Aux6Adapter_Recall extends ArrayAdapter {

    private ArrayList<Response_Aux_Recall> listData ;
    private Context context;

    public Aux6Adapter_Recall(Activity aActivity, ArrayList<Response_Aux_Recall> listData) {
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

        Button cancelbtn = (Button) v.findViewById(R.id.button_cancel);

        tFields1.setText( listData.get(position).getFields1() );
        tFields2.setText( listData.get(position).getFields2() );
        tFields3.setText( listData.get(position).getFields3() );
        tFields4.setText( listData.get(position).getFields4() );
        tFields5.setText( listData.get(position).getFields5() );
        tFields6.setText( listData.get(position).getFields6() );
        if(listData.get(position).getFields6().equals("")){

        }
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setTitle("Confirm");
                builder.setMessage("ต้องการยกเลิกการส่งคืนของหรือไม่");
                builder.setPositiveButton("ตกลง",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ( (Recall)context ).deleterecall(listData.get(position).getFields1());
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
        });

        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ( (Recall)context ).setdetail_itemset(listData.get(position).getFields1());
                ( (Recall)context ).setclick_Doc(
                        listData.get(position).getFields1(),
                        listData.get(position).getFields2(),
                        listData.get(position).getFields3(),
                        listData.get(position).getFields4(),
                        listData.get(position).getFields5(),
                        listData.get(position).getFields8()
                        );
                if(listData.get(position).getFields6().equals("1")){
                    ( (Recall)context ).togglesave(false);
                }else{
                    ( (Recall)context ).togglesave(true);
                }
            }
        });

        return v;
    }

}
