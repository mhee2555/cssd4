package com.phc.cssd.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.properties.Response_Aux_Recall;
import com.phc.cssd.R;

import java.util.ArrayList;

public class AuxAdapter_Recall extends ArrayAdapter {
    private ArrayList<Response_Aux_Recall> listData ;
    private Context context;
    public AuxAdapter_Recall(Activity aActivity, ArrayList<Response_Aux_Recall> listData) {
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
    @SuppressLint("WrongConstant")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.list_itemstock, parent, false);
        TextView tFields1 = (TextView) v.findViewById(R.id.tFields1);
        TextView tFields2 = (TextView) v.findViewById(R.id.tFields2);
        TextView tFields3 = (TextView) v.findViewById(R.id.tFields3);
        TextView tFields4 = (TextView) v.findViewById(R.id.tFields4);
        tFields1.setText( listData.get(position).getFields1() );
        tFields2.setText( listData.get(position).getFields2() );
        tFields3.setText( listData.get(position).getFields3() );
        if (listData.get(position).getxFields11().equals("3:0")) {
            tFields4.setText("ห้องปลอดเชื้อ");
        }else if (listData.get(position).getxFields11().equals("3:1")) {
            tFields4.setText("ห้องปลอดเชื้อ (จอง)");
            tFields4.setTextColor(Color.RED);
            tFields3.setTextColor(Color.RED);
            tFields2.setTextColor(Color.RED);
            tFields1.setTextColor(Color.RED);
        } else if (listData.get(position).getxFields11().equals("4:0")) {
            tFields4.setText(listData.get(position).getFields4() + "( รอรับ )");
        }else if (listData.get(position).getxFields11().equals("4:1")) {
            tFields4.setText(listData.get(position).getFields4() + "( รอรับ )");
        }else if (listData.get(position).getxFields11().equals("5:0")) {
            tFields4.setText(listData.get(position).getFields4());
            tFields4.setTextAlignment(500);
            tFields4.setTextSize(14);
        }else if (listData.get(position).getxFields11().equals("5:1")) {
            tFields4.setText(listData.get(position).getFields4());
            tFields4.setTextAlignment(500);
            tFields4.setTextSize(14);
        }
        return v;
    }
}
