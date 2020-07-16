package com.phc.cssd.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.DeleteSterile_Activity;
import com.phc.cssd.R;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Delete_sterile_adapter extends ArrayAdapter {

    private ArrayList<Response_Aux> listData;
    private DeleteSterile_Activity aActivity;
    private static final String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();
    Calendar myCalendar = Calendar.getInstance();
    String sdate="";
    String edate="";
    public Delete_sterile_adapter(DeleteSterile_Activity aActivity, ArrayList<Response_Aux> listData) {
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
        final View v = inflater.inflate(R.layout.list_delete_sterile_adapter, parent, false);


        TextView txt_ssdoc = (TextView) v.findViewById(R.id.txt_ssdoc);
        TextView txt_ssdate = (TextView) v.findViewById(R.id.txt_ssdate);
        TextView txt_dep = (TextView) v.findViewById(R.id.txt_dep);
        TextView txt_wdoc = (TextView) v.findViewById(R.id.txt_wdoc);
        TextView txt_wdate = (TextView) v.findViewById(R.id.txt_wdate);
        TextView txt_wmdate = (TextView) v.findViewById(R.id.txt_wmdate);
        TextView txt_usagecode = (TextView) v.findViewById(R.id.txt_usagecode);
        TextView txt_del = (TextView) v.findViewById(R.id.txt_del);
        TextView txt_no = (TextView) v.findViewById(R.id.txt_no);
        TextView txt_itemname = (TextView) v.findViewById(R.id.txt_itemname12);

        txt_no.setText(listData.get(position).getFields11()+". ");
        txt_itemname.setText(listData.get(position).getFields12());
        txt_ssdoc.setText(listData.get(position).getFields1());
        txt_ssdate.setText(listData.get(position).getFields2());
        txt_dep.setText(listData.get(position).getFields3());
        txt_wdoc.setText(listData.get(position).getFields4());
        txt_wdate.setText(listData.get(position).getFields5());
        txt_wmdate.setText(listData.get(position).getFields6());
        txt_usagecode.setText(listData.get(position).getFields7());

        txt_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Confirm");
                builder.setMessage("ต้องการลบรายล้างนี้หรือไม่");
                builder.setPositiveButton("ใช่",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updatewash_id(listData.get(position).getFields8(),listData.get(position).getFields7());
                                sdate=listData.get(position).getFields9();
                                edate=listData.get(position).getFields10();
                                ((DeleteSterile_Activity)getContext()).getlistdoc("",sdate,edate);
                            }
                        });
                builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        return v;
    }


    public void updatewash_id(String wash_id,String usagecode) {
        class updatewash_id extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                // loading = ProgressDialog.show(ApproveStockActivity.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);

                    String finish = "";
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        finish=c.getString("Finish");
                    }
                    if(finish.equals("true")){
                        Toast.makeText(getContext(), "ลบรายการแล้ว", Toast.LENGTH_SHORT).show();
                        //((DeleteSterile_Activity)aActivity).getlistdoc("",sdate,edate);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("wash_id",params[0]);
                data.put("usagecode",params[1]);
                Log.d("xDocNo: ", data+"");
                String result = ruc.sendPostRequest(getUrl.xUrl+"p/get_updatewashdetail.php",data);
                Log.d("result finish: ", result);
                return  result;
            }
        }
        updatewash_id ru = new updatewash_id();
        ru.execute( wash_id,usagecode );
    }


}

