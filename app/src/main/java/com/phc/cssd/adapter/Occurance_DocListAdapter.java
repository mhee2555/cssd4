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
import android.widget.ImageView;
import android.widget.TextView;
import com.phc.cssd.Occurance_MainActivity;
import com.phc.cssd.R;
import com.phc.cssd.properties.pCustomer;
import java.util.ArrayList;

/**
 * Created by User on 20/7/2560.
 */


public class Occurance_DocListAdapter extends ArrayAdapter {

    private ArrayList<pCustomer> listData;
    private AppCompatActivity aActivity;
    private String REGISTER_URL;

    public Occurance_DocListAdapter(AppCompatActivity aActivity, ArrayList<pCustomer> listData) {
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
        final View v = inflater.inflate(R.layout.list_getdoc_occurance, parent, false);
        final pCustomer pCus = listData.get(position);
        final int xps = position;

        TextView txtdocno = (TextView) v.findViewById(R.id.docno);
        TextView txtdocdate = (TextView)v.findViewById(R.id.docdate);
        TextView txtmachine= (TextView)v.findViewById(R.id.d_macchine);
        TextView txtoc= (TextView) v.findViewById(R.id.d_Oc);
        TextView txtround = (TextView) v.findViewById(R.id.d_round);
        ImageView bt_ocdetail = (ImageView) v.findViewById(R.id.bt_ocdetail);

        TextView line1= (TextView) v.findViewById(R.id.line1);
        TextView line2= (TextView) v.findViewById(R.id.line2);
        TextView line3= (TextView) v.findViewById(R.id.line3);


        line1.setText(listData.get(position).getDocno()+" / "+listData.get(position).getDocdate());

        if (!listData.get(position).getMachine().equals("0") && !listData.get(position).getRound().equals("0")) {
            line2.setText("เครื่อง : " + listData.get(position).getMachine() + " รอบ : " + listData.get(position).getRound());
        }else {
            line2.setText("เครื่อง : " + "0" + " รอบ : " + "0");
        }

        line3.setText("ความเสี่ยง : "+listData.get(position).getOccuranceID());


        txtdocno.setText(listData.get(position).getDocno());
        txtdocdate.setText(listData.get(position).getDocdate());
        txtmachine.setText( listData.get(position).getMachine() );
        txtround.setText( listData.get(position).getRound() );
        txtoc.setText( listData.get(position).getOccuranceID() );

        bt_ocdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Occurance_MainActivity)aActivity).getlistdocdetail(listData.get(position).getDocno(),listData.get(position).getType(),listData.get(position).getCnt());
            }
        });

        return v;
    }


}