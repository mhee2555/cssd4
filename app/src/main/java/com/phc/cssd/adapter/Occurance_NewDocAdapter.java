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
import android.widget.CheckBox;
import android.widget.TextView;

import com.phc.cssd.Occurance_MainActivity;
import com.phc.cssd.R;
import com.phc.cssd.properties.Response_Aux_itemstock;

import java.util.ArrayList;

public class Occurance_NewDocAdapter extends ArrayAdapter {

    private Context Mainac;
    private ArrayList<Response_Aux_itemstock> listData ;
    private Context context;
    private CheckBox chx_change;

    public Occurance_NewDocAdapter(Activity aActivity, ArrayList<Response_Aux_itemstock> listData) {
        super(aActivity, 0, listData);
        this.context = aActivity;
        this.listData = listData;
        this.Mainac = context;
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
        final View v = inflater.inflate(R.layout.list_newdoc_occurance, parent, false);
        Button bt_del_oc =(Button) v.findViewById(R.id.bt_del_oc);
        TextView tFields6 = (TextView) v.findViewById(R.id.tFields6);
        TextView tFields8 = (TextView) v.findViewById(R.id.tFields8);
        TextView tFields9 = (TextView) v.findViewById(R.id.tFields9);

        tFields6.setText( listData.get(position).getFields6() );
        tFields8.setText( listData.get(position).getFields8() );
        tFields9.setText("จำนวน : "+listData.get(position).getFields9() );

        bt_del_oc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setCancelable(true);
                builder.setTitle("Confirm");
                builder.setMessage("ต้องการลบหรือไม่");
                builder.setPositiveButton("ตกลง",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("onClick: ", listData.get(position).getFields6()+"  ");
                                Log.d("onClick: ", listData.get(position).getFields8()+"  ");
                                Log.d("onClick: ", listData.get(position).getFields9()+"  ");
                                ( (Occurance_MainActivity)context ).xDeleteToDetail(listData.get(position).getFields8());
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

        return v;
    }




}
