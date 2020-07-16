package com.phc.cssd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.dialog_search_itemname;
import com.phc.cssd.properties.Response_Itemname_Search;

import java.util.ArrayList;

public class AdapterItemSearch extends ArrayAdapter {

    private ArrayList<Response_Itemname_Search> listData ;
    private Context context;
    private int temppos;

    public AdapterItemSearch(dialog_search_itemname aActivity, ArrayList<Response_Itemname_Search> listData) {
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
        final View v = inflater.inflate(R.layout.list_item_search, parent, false);

        temppos = position;

        TextView tFields1 = (TextView) v.findViewById(R.id.ItemCode);
        TextView tFields2 = (TextView) v.findViewById(R.id.ItemName);
        Button importbtn = (Button) v.findViewById(R.id.button_import);

        tFields1.setText( listData.get(position).getItemcode() );
        tFields2.setText( listData.get(position).getItemname() );

        importbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = listData.get(position).getItemcode()+":"+listData.get(position).getItemname();
                ((dialog_search_itemname)context).GetDataitem(temp);
            }
        });

        return v;
    }
}
