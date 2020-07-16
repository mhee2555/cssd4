package com.phc.cssd.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.model.ModelPayout;

import java.util.List;

public class List_Payout_Borrow_Adapter extends ArrayAdapter {

    private List<ModelPayout> model;
    private AppCompatActivity aActivity;


    public List_Payout_Borrow_Adapter(AppCompatActivity aActivity, List<ModelPayout> model) {
        super(aActivity, 0, model);
        this.aActivity = aActivity;
        this.model = model;

    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ( LayoutInflater ) aActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.list_payout_borrow_adapter, parent, false);
        RelativeLayout r = ( RelativeLayout ) v.findViewById(R.id.r);
        TextView w_docno = ( TextView ) v.findViewById(R.id.w_docno);
        final TextView w_note = ( TextView ) v.findViewById(R.id.w_note);
        TextView w_docdate = ( TextView ) v.findViewById(R.id.w_docdate);
        TextView w_dapt = ( TextView ) v.findViewById(R.id.w_dapt);
        TextView img_sttus = ( TextView ) v.findViewById(R.id.iswashdept);
        final TextView w_bt_note = ( TextView ) v.findViewById(R.id.w_bt_note);


        w_docdate.setText(model.get(position).getCreateDate());
        w_dapt.setText(model.get(position).getDepName());
        w_docno.setText(model.get(position).getDocNo());
        w_note.setText(model.get(position).getPayout_Status());


        if (model.get(position).getIsStatus().equals("2")) {
            img_sttus.setBackgroundResource(R.drawable.ic_radiobox_unfill);
        }else if (model.get(position).getIsStatus().equals("5")){
            img_sttus.setBackgroundResource(R.drawable.ic_radiobox_unfill);
        }else {
            img_sttus.setBackgroundResource(R.drawable.ic_radiobox_fill);
        }

        /*
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((com.phc.cssd_4_sd.CssdTakeBack)aActivity).onSelectItem(model.get(position).getDocNo(), model.get(position).getCreateDate());
            }
        });
        */

        return v;
    }



}


