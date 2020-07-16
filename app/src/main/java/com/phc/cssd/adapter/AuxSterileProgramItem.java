package com.phc.cssd.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.properties.Response_Aux;

import java.util.ArrayList;

public class AuxSterileProgramItem extends ArrayAdapter {
    private ArrayList<Response_Aux> listData ;
    private Context context;
    private CheckBox chx_change;

    public AuxSterileProgramItem(Activity aActivity, ArrayList<Response_Aux> listData) {
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
        final View v = inflater.inflate(R.layout.list_sterilemachine, parent, false);

        chx_change = (CheckBox) v.findViewById(R.id.chx_change);
        TextView tFields1 = (TextView) v.findViewById(R.id.tFields1);

        tFields1.setText( listData.get(position).getFields1() );

        chx_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean newState = !listData.get(position).isIs_Check();
                listData.get(position).setIs_Check(newState);
            }
        });
        chx_change.setChecked(listData.get(position).isIs_Check());

        return v;
    }

    public void toggleCheckboxAll(){
        for (int i=0;i<listData.size();i++){
            boolean newState = !listData.get(i).isIs_Check();
            listData.get(i).setIs_Check(newState);
        }
    }


}
