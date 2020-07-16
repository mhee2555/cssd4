package com.phc.cssd.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.properties.Response_Aux_itemstock;
import com.phc.cssd.url.xControl;

import java.util.ArrayList;

public class R_itemstock_Adapter extends ArrayAdapter {
    xControl xCtl = new xControl();
    private Context Mainac;
    private ArrayList<Response_Aux_itemstock> listData ;
    private Activity context;
    ListView Lv2;

    public R_itemstock_Adapter(Activity aActivity, ArrayList<Response_Aux_itemstock> listData,ListView Lv2) {
        super(aActivity, 0, listData);
        this.context = aActivity;
        this.listData = listData;
        this.Lv2 = Lv2;
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
        final View v = inflater.inflate(R.layout.list_data_r, parent, false);
        final TextView tFields1 = (TextView) v.findViewById(R.id.tFields1);
        final TextView tFields2 = (TextView) v.findViewById(R.id.tFields2);
        final TextView tFields3 = (TextView) v.findViewById(R.id.tFields3);
        final ImageView imageView1 = (ImageView) v.findViewById(R.id.imageView7);
        final ImageView imageView2 = (ImageView) v.findViewById(R.id.imageView14);

        tFields1.setText( listData.get(position).getFields6()  );
        if(listData.get(position).getxFields11().equals("0")) {

        }else if(listData.get(position).getxFields11().equals("1")) {
            tFields1.setTextColor(Color.RED);
            tFields2.setTextColor(Color.RED);
            tFields3.setTextColor(Color.RED);
            imageView2.setImageResource(R.drawable.ic_r_red);
            tFields1.setBackgroundResource( R.drawable.text_edit_red );
        }else {

        }

        tFields2.setText( listData.get(position).getxFields14()==null||listData.get(position).getxFields14().equals("0")?"หมายเหตุ : -":"หมายเหตุ : "+listData.get(position).getxFields14() );
        tFields3.setText( listData.get(position).getFields8()+" : "+listData.get(position).getFields9() );
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    listData.remove(position);
                    Log.d("OOOO",listData.size()+"");
                    Lv2.setAdapter(new R_itemstock_Adapter(context, listData,Lv2));
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.risk_dialog_sendsterile);
                dialog.setCancelable(true);
                dialog.setTitle("ประเภทความเสี่ยง...");

                final Spinner ResterileType = (Spinner) dialog.findViewById(R.id.spn_list);
                final Spinner spn_Step = (Spinner) dialog.findViewById(R.id.spn_Step);

                String[] StepList = context.getResources().getStringArray(R.array.StpeAdapter);
                ArrayAdapter<String> StepAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, StepList);
                spn_Step.setAdapter( StepAdapter );
                spn_Step.setVisibility(View.GONE);
                xCtl.ListResterileType(ResterileType, context );
                final ArrayList<Response_Aux> resultsResterileType = xCtl.getListResterileType();
                Log.d("onClick: ", listData.get(position).getxFields12());
                //ResterileType.setSelection(Integer.parseInt(listData.get(position).getxFields12()));
                ResterileType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pn, long id) {
                        Log.d("onClick2: ", listData.get(position).getxFields12());
                            listData.get(position).setxFields12(resultsResterileType.get(pn).getFields1());
                        Log.d("onClick3: ", listData.get(position).getxFields12());
                            listData.get(position).setxFields14(ResterileType.getSelectedItem().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //Another interface callback
                    }
                });

                Button button1 = (Button) dialog.findViewById(R.id.button1);
                button1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(!ResterileType.getSelectedItem().equals("-")) {
                            listData.get(position).setxFields11("1");
                            imageView2.setImageResource(R.drawable.ic_r_red);
                            tFields1.setTextColor(Color.RED);
                            tFields2.setTextColor(Color.RED);
                            tFields3.setTextColor(Color.RED);
                            tFields1.setBackgroundResource( R.drawable.text_edit_red );
                            notifyDataSetChanged();
                        }else{
                            listData.get(position).setxFields11("0");
                            imageView2.setImageResource(R.drawable.ic_r_grey);
                            tFields1.setTextColor(Color.BLACK);
                            tFields2.setTextColor(Color.BLACK);
                            tFields3.setTextColor(Color.BLACK);
                            notifyDataSetChanged();
                        }
                        dialog.dismiss();
                    }
                });

                Button button5 = (Button) dialog.findViewById(R.id.button5);
                button5.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        listData.get(position).setxFields11("0");
                        listData.get(position).setxFields12("");
                        listData.get(position).setxFields14("-");
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

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
