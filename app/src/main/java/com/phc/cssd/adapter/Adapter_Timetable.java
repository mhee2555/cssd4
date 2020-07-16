package com.phc.cssd.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.properties.Response_Timetable;

import java.util.ArrayList;

public class Adapter_Timetable extends ArrayAdapter{
    private ArrayList<Response_Timetable> listData ;
    private Context context;

    public Adapter_Timetable(AppCompatActivity aActivity, ArrayList<Response_Timetable> listData) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.list_timetable, parent, false);

        TextView tFields1 = (TextView) v.findViewById(R.id.tWeek);
        TextView tFields2 = (TextView) v.findViewById(R.id.tEmp1);
        TextView tFields3 = (TextView) v.findViewById(R.id.tEmp2);
        TextView tFields4 = (TextView) v.findViewById(R.id.tEmp3);

        tFields1.setText( listData.get(position).getWeek() );
        tFields2.setText( listData.get(position).getEmp1() );
        tFields3.setText( listData.get(position).getEmp3() );
        tFields4.setText( listData.get(position).getEmp2() );

        return v;
    }


}
