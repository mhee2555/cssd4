package com.phc.cssd.adapter;

/**
 * Created by HPBO on 1/11/2018.
 */

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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


public class OccuranceAdapter extends ArrayAdapter {

    private ArrayList<pCustomer> listData;
    private AppCompatActivity aActivity;
    private String REGISTER_URL;

    public OccuranceAdapter(AppCompatActivity aActivity, ArrayList<pCustomer> listData) {
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
        final View v = inflater.inflate(R.layout.list_get_ocdoc_occurance, parent, false);
        final pCustomer pCus = listData.get(position);
        final int xps = position;

        TextView txtdocno = (TextView) v.findViewById(R.id.oc_docno);
        TextView txtdocdate = (TextView)v.findViewById(R.id.oc_docdate);
        TextView txtmachine= (TextView)v.findViewById(R.id.oc_macchine);
        TextView txtround = (TextView) v.findViewById(R.id.oc_round);
        TextView txtoc= (TextView) v.findViewById(R.id.oc_Oc);
        ImageView bt_ocdetail = (ImageView) v.findViewById(R.id.bt_ocdetail);
        ImageView img_risk = (ImageView) v.findViewById(R.id.img_risk);

        TextView line0= (TextView) v.findViewById(R.id.line0);
        TextView line1= (TextView) v.findViewById(R.id.line1);
        TextView line2= (TextView) v.findViewById(R.id.line2);
        TextView line3= (TextView) v.findViewById(R.id.line3);
        Log.d("BANK", "list layouttype 2:adapter ");

        line0.setText(listData.get(position).getDocno()+" / "+listData.get(position).getDocdate());
        line1.setText(listData.get(position).getDocno()+" / "+listData.get(position).getDocdate());
        line2.setText("เครื่อง : "+listData.get(position).getMachine()+" รอบ : "+listData.get(position).getRound());
        line3.setText("ความเสี่ยง : "+(listData.get(position).getOccuranceID().equals("0") ? "ปกติ" : (listData.get(position).getOccuranceID().equals("1") ? "มีความเสี่ยง":false))+"");

        if(listData.get(position).getOccuranceID().equals("0"))
        {img_risk.setVisibility(View.INVISIBLE);
        }else
        { img_risk.setVisibility(View.VISIBLE);

        }

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

        txtdocno.setText(listData.get(position).getDocno());
        txtdocdate.setText(listData.get(position).getDocdate());
        txtmachine.setText( listData.get(position).getMachine() );
        txtround.setText( listData.get(position).getRound() );


        if(listData.get(position).getOccuranceID().equals("0"))
        {txtoc.setText("ปกติ");
        }else
        { txtoc.setText("มีความเสี่ยง");

        }
        return v;
    }


}