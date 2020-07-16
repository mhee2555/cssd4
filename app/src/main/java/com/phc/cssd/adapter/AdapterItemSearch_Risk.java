package com.phc.cssd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.dialog_search_itemname_risk;
import com.phc.cssd.properties.Response_Itemname_Search;

import java.util.ArrayList;

public class AdapterItemSearch_Risk extends ArrayAdapter {
    private ArrayList<Response_Itemname_Search> listData ;
    private Context context;
    private int temppos;

    public AdapterItemSearch_Risk(dialog_search_itemname_risk aActivity, ArrayList<Response_Itemname_Search> listData) {
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
        final View v = inflater.inflate(R.layout.list_item_search_risk, parent, false);

        temppos = position;

        TextView tFields1 = (TextView) v.findViewById(R.id.ItemCode);
        TextView tFields2 = (TextView) v.findViewById(R.id.ItemName);
        TextView tFields3 = (TextView) v.findViewById(R.id.IsStatus);

        tFields1.setText( listData.get(position).getItemcode() );
        tFields2.setText( listData.get(position).getItemname() );

        if (listData.get(position).getIsstatus().equals("0")) {
            tFields3.setText("ส่งล้าง");
        }else if (listData.get(position).getIsstatus().equals("1")){
            tFields3.setText("ห้องล้าง");
        }else if (listData.get(position).getIsstatus().equals("2")){
            tFields3.setText("ห้องแพ็ค");
        }else if (listData.get(position).getIsstatus().equals("3") && listData.get(position).getIsPay().equals("0")){
            tFields3.setText("ห้องปลอดเชื้อ");
        }else if (listData.get(position).getIsstatus().equals("3") && listData.get(position).getIsPay().equals("1")){
            tFields3.setText("ห้องปลอดเชื้อ" + "( จอง )");
            tFields1.setTextColor(Color.RED);
            tFields2.setTextColor(Color.RED);
            tFields3.setTextColor(Color.RED);
        }else if (listData.get(position).getIsstatus().equals("4")){
            tFields3.setText(listData.get(position).getDepName2() + "( รอรับ )");
        }else if (listData.get(position).getIsstatus().equals("5")){
            tFields3.setText(listData.get(position).getDepName2());
        }

        return v;
    }
}
