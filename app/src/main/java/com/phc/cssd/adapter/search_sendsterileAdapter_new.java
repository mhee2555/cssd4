package com.phc.cssd.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.R;
import com.phc.cssd.SearchItem_SendSterile;
import com.phc.cssd.properties.Response_Aux_itemstock;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;

import java.util.ArrayList;

public class search_sendsterileAdapter_new extends ArrayAdapter {

    JSONArray setRs = null;
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();

    ListView Lv2;

    String Usage_code;

    private ArrayList<Response_Aux_itemstock> listData ;
    private Activity context;
    boolean IsAdmin = false;

    public search_sendsterileAdapter_new(Activity aActivity, ArrayList<Response_Aux_itemstock> listData) {
        super(aActivity, 0, listData);
        this.context = aActivity;
        this.listData = listData;
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
        final View v = inflater.inflate(R.layout.activity_search_sendsterile_adapter_new, parent, false);
        TextView tFields1 = (TextView) v.findViewById(R.id.txt_itemcode);
        TextView txt_qty = (TextView) v.findViewById(R.id.txt_qty);
        final EditText EditNum1 = (EditText) v.findViewById(R.id.etxt_qty);
        Button bt_import = (Button) v.findViewById(R.id.bt_import);

        if (listData.get(position).getxFields16().equals("1")) {
            tFields1.setText(listData.get(position).getFields2() + ": " + "[ SET ] " + listData.get(position).getFields9() + " " + "(" + listData.get(position).getxFields15() + " วัน " + ")");
        }else {
            tFields1.setText(listData.get(position).getFields2() + ":" + listData.get(position).getFields9() + " " + "(" + listData.get(position).getxFields15() + " วัน " + ")");
        }
        EditNum1.setText( listData.get(position).getFields6() );


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
        bt_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listData.get(position).getxFields11().equals("9")||listData.get(position).getxFields11().equals("10")){
                    Log.d("YUYU", listData.get(position).getxFields11()+" :: "+listData.get(position).getFields2()+"//"+EditNum1.getText()+"//"+listData.get(position).getxFields10());
                    (( SearchItem_SendSterile )context).senditemandqty( listData.get(position).getFields2(),EditNum1.getText()+"",listData.get(position).getxFields10());
                }else{
                    Log.d("YUYU",listData.get(position).getxFields11()+" :: "+listData.get(position).getFields2()+"//"+listData.get(position).getFields6()+"//");
                    ((SearchItem_SendSterile)context).additemandqty( listData.get(position).getFields2(),listData.get(position).getFields6()+"","");
                }
            }
        });

        return v;
    }

}
