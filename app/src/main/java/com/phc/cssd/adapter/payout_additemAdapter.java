package com.phc.cssd.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.R;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;

import java.util.ArrayList;

public class payout_additemAdapter extends ArrayAdapter {
    JSONArray setRs = null;
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();
    private ArrayList<Response_Aux> listData ;
    private ArrayList<Response_Aux> listData2 ;
    private Activity context;
    ListView Lv2;
    String Usage_code;

    public payout_additemAdapter(Activity aActivity, ArrayList<Response_Aux> listData) {
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
        final View v = inflater.inflate(R.layout.list_additem_payout, parent, false);
        TextView txt_itemcode_po = (TextView) v.findViewById(R.id.txt_doc);
        TextView img_ischeck = (TextView) v.findViewById(R.id.img_ischeck);
        if( listData.get(position).getFields10().equals("1")){img_ischeck.setVisibility(View.VISIBLE);}
        //Button bt_import = (Button) v.findViewById(R.id.bt_import);
        if(listData.get(position).getFields12().equals("1")){
            txt_itemcode_po.setText( listData.get(position).getFields1()+"  "+ listData.get(position).getFields2()+" (S)"+"\n"+ listData.get(position).getFields3() );
        }else{
            txt_itemcode_po.setText( listData.get(position).getFields1()+"  "+ listData.get(position).getFields2()+"\n"+ listData.get(position).getFields3() );
        }

       /* v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ( (Payout_additemActivity)context ).ListPayoutDetail(listData.get(position).getFields1());
                Log.d("onClick: ", listData.get(position).getFields1());

            }
        });*/

      /*  bt_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((SearchItem_SendSterile)context).senditemandqty( listData.get(position).getFields2(),etxt_num_po.getText()+"");
            }
        });*/

        return v;
    }

}
