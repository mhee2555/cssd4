package com.phc.cssd.adapter;

/**
 * Created by HPBO on 1/11/2018.
 */

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.properties.pCustomer;

import java.util.ArrayList;

/**
 * Created by User on 20/7/2560.
 */


public class Occurance_DocListDetailAdapter extends ArrayAdapter {

    private ArrayList<pCustomer> listData;
    private AppCompatActivity aActivity;


    public Occurance_DocListDetailAdapter(AppCompatActivity aActivity, ArrayList<pCustomer> listData) {
        super(aActivity, 0, listData);
        this.aActivity= aActivity;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) aActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.list_getdocdetail_occurance, parent, false);


        TextView txtitemname = (TextView) v.findViewById(R.id.itemname);
        txtitemname.setText(listData.get(position).getCnt()+" : "+listData.get(position).getItemname()+" : "+listData.get(position).getUsageCode());

        return v;
    }

}