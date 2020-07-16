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
import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.R;
import com.phc.cssd.properties.pCustomer;
import com.phc.cssd.url.getUrl;
import java.util.ArrayList;

/**
 * Created by User on 20/7/2560.
 */


public class OccuranceDetailAdapter extends ArrayAdapter {

    private ArrayList<pCustomer> listData;
    private AppCompatActivity aActivity;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    private String REGISTER_URL;

    public OccuranceDetailAdapter(AppCompatActivity aActivity, ArrayList<pCustomer> listData) {
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
        final View v = inflater.inflate(R.layout.list_createocdetail_occurance, parent, false);
        final pCustomer pCus = listData.get(position);
        final int xps = position;

        TextView txtUsagecode = (TextView)v.findViewById(R.id.octxt_itemcode);
        TextView txtitemname = (TextView) v.findViewById(R.id.octxt_itemname);


        txtUsagecode.setText(listData.get(position).getUsageCode());
        txtitemname.setText(listData.get(position).getItemname());




        return v;
    }

}