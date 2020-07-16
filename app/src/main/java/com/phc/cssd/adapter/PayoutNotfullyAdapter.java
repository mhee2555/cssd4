package com.phc.cssd.adapter;

/**
 * Created by HPBO on 1/11/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.PayoutActivity;
import com.phc.cssd.R;
import com.phc.cssd.properties.Response_Aux;

import java.util.ArrayList;

/**
 * Created by User on 20/7/2560.
 */


public class PayoutNotfullyAdapter extends ArrayAdapter {

    private ArrayList<Response_Aux> listData;
    private PayoutActivity aActivity;
    int devicemode;

    public PayoutNotfullyAdapter(PayoutActivity aActivity, ArrayList<Response_Aux> listData,int devicemode) {
        super(aActivity, 0, listData);
        this.aActivity= aActivity;
        this.listData = listData;
        this.devicemode =devicemode ;
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
        final View v = inflater.inflate(R.layout.list_payoutnotfully, parent, false);

        TextView txtitemname = (TextView) v.findViewById(R.id.itemname);
        TextView xqty = (TextView) v.findViewById(R.id.xqty);
        txtitemname.setText(listData.get(position).getFields4()+" : "+listData.get(position).getFields3());

        if(devicemode==PayoutActivity.IsT2){
            xqty.setText("จำนวนค้าง : "+listData.get(position).getFields2());
        }else{

            xqty.setText(listData.get(position).getFields2());
        }

        return v;
    }

}

