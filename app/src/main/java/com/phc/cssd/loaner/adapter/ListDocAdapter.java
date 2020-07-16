package com.phc.cssd.loaner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.loaner.LoanerMainActivity;
import com.phc.cssd.loaner.model.LoanerDocument;

import java.util.ArrayList;

public class ListDocAdapter extends ArrayAdapter {
    private ArrayList<LoanerDocument> listData ;
    private LoanerMainActivity context;
    ArrayList<RelativeLayout> listMainDoc = new ArrayList<>();

    public ListDocAdapter(LoanerMainActivity aActivity, ArrayList<LoanerDocument> listData) {
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
        final View v = inflater.inflate(R.layout.adapter_list_ipd_opd_doc, parent, false);

        final int pos = position;
        final RelativeLayout maindoc = (RelativeLayout) v.findViewById(R.id.maindoc);
        TextView tFields1 = (TextView) v.findViewById(R.id.w_docno);
        TextView tFields2 = (TextView) v.findViewById(R.id.w_docdate);
        TextView tFields3 = (TextView) v.findViewById(R.id.w_dapt);
        TextView tFields4 = (TextView) v.findViewById(R.id.w_note);
        ImageView img_sttus = (ImageView) v.findViewById(R.id.IsStatus);

        listMainDoc.add(maindoc);

        tFields1.setText( (position+1)+"."+listData.get(position).getDocNo()+" / ");
        String[] xDate = listData.get(position).getDocDate().split(" ");
        tFields2.setText( context.setDateFormat(xDate[0])+" "+xDate[1].substring(0,5));
        tFields3.setText( listData.get(position).getSendUser());
        tFields4.setText("หมายเหตุ : "+listData.get(position).getRemark());
        tFields4.setVisibility(View.GONE);

        if (listData.get(position).getIsStatus().equals("1")) {
            img_sttus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_radiobox_fill));
        } else {
            img_sttus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_radiobox_unfill));
        }

        maindoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<listMainDoc.size();i++){
                    listMainDoc.get(i).setBackground(null);
                }

                maindoc.setBackground(context.getResources().getDrawable(R.drawable.rectangle_red));

                context.showDoc(listData.get(pos));
            }
        });

        return v;
    }


}
