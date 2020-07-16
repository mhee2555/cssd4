package com.phc.cssd.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.R;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.properties.Response_Aux_itemstock;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;

import java.util.ArrayList;

public class L_itemstock_Adapter extends ArrayAdapter {
    JSONArray setRs = null;
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();
    private ArrayList<Response_Aux_itemstock> listData ;
    private ArrayList<Response_Aux_itemstock> listData2 ;
    private Activity context;
    ListView Lv2;
    String Usage_code;

    public L_itemstock_Adapter(Activity aActivity, ArrayList<Response_Aux_itemstock> listData, ListView Lv2,ArrayList<Response_Aux_itemstock> listData2,String Usage_code) {
        super(aActivity, 0, listData);
        this.context = aActivity;
        this.listData = listData;
        this.listData2 = listData2;
        this.Lv2 = Lv2;
        this.Usage_code = Usage_code;
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

        tFields1.setText( listData.get(position).getFields8()+" : "+listData.get(position).getFields9()  );
        EditNum1.setText( listData.get(position).getFields6() );

        //xCtl.ListResterileType(ResterileType, context );

        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResterileType.setEnabled( checkBox1.isChecked() );
            }
        });



        EditNum1.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View v, boolean hasFocus){
                if (hasFocus) ((EditText)v).selectAll();
            }
        });

        EditNum1.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                listData.get(position).setFields6( EditNum1.getText()+"" );
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response_Aux_itemstock newsData = new Response_Aux_itemstock();
                newsData.setFields1(listData.get(position).getFields1());
                newsData.setFields2(listData.get(position).getFields2());
                newsData.setFields3(listData.get(position).getFields3());
                newsData.setFields4(listData.get(position).getFields4());
                newsData.setFields5(listData.get(position).getFields5());
                newsData.setFields6(EditNum1.getText().toString());
                newsData.setFields7(listData.get(position).getFields7());
                newsData.setFields8(listData.get(position).getFields8());
                newsData.setFields9(listData.get(position).getFields9());
                newsData.setxFields10(listData.get(position).getxFields10());
                newsData.setxFields11(listData.get(position).getxFields11());
                newsData.setxFields12(listData.get(position).getxFields12());
                newsData.setxFields13(listData.get(position).getxFields13());
                newsData.setxFields14(listData.get(position).getxFields14());
                listData2.add(0,newsData);
                Lv2.setAdapter(new R_itemstock_Adapter(context,listData2,Lv2));
            }
        });


        if(Usage_code.length()==11){
            Response_Aux_itemstock newsData = new Response_Aux_itemstock();
            newsData.setFields1(listData.get(position).getFields1());
            newsData.setFields2(listData.get(position).getFields2());
            newsData.setFields3(listData.get(position).getFields3());
            newsData.setFields4(listData.get(position).getFields4());
            newsData.setFields5(listData.get(position).getFields5());
            newsData.setFields6(listData.get(position).getFields6());
            newsData.setFields7(listData.get(position).getFields7());
            newsData.setFields8(listData.get(position).getFields8());
            newsData.setFields9(listData.get(position).getFields9());
            newsData.setxFields10(listData.get(position).getxFields10());
            newsData.setxFields11(listData.get(position).getxFields11());
            newsData.setxFields12(listData.get(position).getxFields12());
            newsData.setxFields13(listData.get(position).getxFields13());
            newsData.setxFields14(listData.get(position).getxFields14());
            listData2.add(0,newsData);
            Lv2.setAdapter(new R_itemstock_Adapter(context,listData2,Lv2));

        }


        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.spinner_dialog);
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
                ResterileType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pn, long id) {
                            listData.get(position).setxFields12(resultsResterileType.get(pn).getFields1());
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
                            //imageView2.setImageResource(R.drawable.ic_r_red);
                        }else{
                            listData.get(position).setxFields11("0");
                            //imageView2.setImageResource(R.drawable.ic_r_grey);
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
