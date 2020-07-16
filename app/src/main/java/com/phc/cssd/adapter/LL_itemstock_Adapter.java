package com.phc.cssd.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.R;
import com.phc.cssd.properties.Response_Aux_itemstock;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;

import java.util.ArrayList;

public class LL_itemstock_Adapter extends ArrayAdapter {
    JSONArray setRs = null;
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();
    private ArrayList<Response_Aux_itemstock> listData ;
    private ArrayList<Response_Aux_itemstock> listData2 ;
    private Activity context;

    public LL_itemstock_Adapter(Activity aActivity, ArrayList<Response_Aux_itemstock> listData) {
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
        final View v = inflater.inflate(R.layout.list_data_l, parent, false);
        TextView tFields1 = (TextView) v.findViewById(R.id.tFields1);
        TextView tFields2 = (TextView) v.findViewById(R.id.tFields2);
        final Spinner ResterileType = (Spinner) v.findViewById(R.id.spinner2);
        final CheckBox checkBox1 = (CheckBox) v.findViewById(R.id.checkBox1);
        final EditText EditNum1 = (EditText) v.findViewById(R.id.EditNum1);
        final ImageView imageView1 = (ImageView) v.findViewById(R.id.imageView1);
        final ImageView imageView2 = (ImageView) v.findViewById(R.id.imageView2);

        return v;
    }


    public int getCountselected(){
        int count = 0;
        for (int i=0;i<listData.size();i++){
            if(listData.get(i).isIs_Check()) {
                count++;
            }
        }
        return count;
    }

}
