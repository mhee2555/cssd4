package com.phc.cssd.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.properties.Response_Aux;

import java.util.ArrayList;

public class Aux12Adapter extends ArrayAdapter {
    private ArrayList<Response_Aux> listData ;
    private Context context;

    public Aux12Adapter(AppCompatActivity aActivity, ArrayList<Response_Aux> listData) {
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
        final View v = inflater.inflate(R.layout.list_aux12, parent, false);

        TextView tFields1 = (TextView) v.findViewById(R.id.tFields1);
        TextView tFields2 = (TextView) v.findViewById(R.id.tFields2);
        TextView tFields3 = (TextView) v.findViewById(R.id.tFields3);
        TextView tFields4 = (TextView) v.findViewById(R.id.tFields4);
        TextView tFields5 = (TextView) v.findViewById(R.id.tFields5);
        TextView tFields6 = (TextView) v.findViewById(R.id.tFields6);
        TextView tFields7 = (TextView) v.findViewById(R.id.tFields7);
        TextView tFields8 = (TextView) v.findViewById(R.id.tFields8);
        TextView tFields9 = (TextView) v.findViewById(R.id.tFields9);
        TextView tFields10 = (TextView) v.findViewById(R.id.tFields10);
        TextView tFields11 = (TextView) v.findViewById(R.id.tFields11);
        TextView tFields12 = (TextView) v.findViewById(R.id.tFields12);

        tFields1.setText( listData.get(position).getFields1() );
        tFields2.setText( listData.get(position).getFields2() );
        tFields3.setText( listData.get(position).getFields3() );
        tFields4.setText( listData.get(position).getFields4() );
        tFields5.setText( listData.get(position).getFields5() );
        tFields6.setText( listData.get(position).getFields6() );
        tFields7.setText( listData.get(position).getFields7() );
        tFields8.setText( listData.get(position).getFields8() );
        tFields9.setText( listData.get(position).getFields9() );
        tFields10.setText( listData.get(position).getFields10() );
        tFields11.setText( listData.get(position).getFields11() );
        tFields12.setText( listData.get(position).getFields12() );

        return v;
    }


}
