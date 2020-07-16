package com.phc.cssd.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.R;
import com.phc.cssd.model.ModelRecallDetail;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AdapterItemdetaill extends ArrayAdapter {

    private final List<ModelRecallDetail> DATA_MODEL;
    private final Activity context;
    HTTPConnect ruc = new HTTPConnect();
    private String TAG_RESULTS="result";
    private JSONArray rs = null;

    TextView item_name_recall;
    TextView UsageCode;
    TextView expitem;


    public AdapterItemdetaill(Activity context, List<ModelRecallDetail> DATA_MODEL) {
        super(context, R.layout.activity_adapter_itemdetaill, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.activity_adapter_itemdetaill, parent, false);

        item_name_recall = (TextView) v.findViewById(R.id.item_name_recall);
        UsageCode = (TextView) v.findViewById(R.id.UsageCode);
        expitem = (TextView) v.findViewById(R.id.expitem);

        item_name_recall.setText(DATA_MODEL.get(position).getItemname());
        UsageCode.setText("("+DATA_MODEL.get(position).getUsageCode()+")");
        expitem.setText("EXP : "+DATA_MODEL.get(position).getExpireDate());

        return v;
    }

    public String convertdate(String date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        DateFormat newformat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        java.util.Date condate;
        try {
            condate = format.parse(date);
            condate = newformat.parse(condate.toString());
            date = condate.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  date;
    }

}