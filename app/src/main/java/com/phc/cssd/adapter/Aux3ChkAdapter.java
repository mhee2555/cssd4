package com.phc.cssd.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.url.getUrl;
import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Aux3ChkAdapter extends ArrayAdapter {
    private ArrayList<Response_Aux> listData ;
    private Context context;
    private int IsStatus;

    public Aux3ChkAdapter(AppCompatActivity aActivity, ArrayList<Response_Aux> listData, int IsStatus) {
        super(aActivity, 0, listData);
        this.context = aActivity;
        this.listData = listData;
        this.IsStatus = IsStatus;
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
        final View v = inflater.inflate(R.layout.list_aux3chk, parent, false);
        final int xposition = position;

        TextView tFields1 = (TextView) v.findViewById(R.id.tFields1);
        TextView tFields2 = (TextView) v.findViewById(R.id.tFields2);
        TextView tFields3 = (TextView) v.findViewById(R.id.tFields3);
        final CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);

        if(IsStatus == 0)
            checkBox.setEnabled(true);
        else
            checkBox.setEnabled(false);

        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                    if (checkBox.isChecked()) {
                        listData.get(xposition).setFields4("1");
                        UpdateSterileDetail(listData.get(xposition).getFields1(), "1");
                    } else {
                        listData.get(xposition).setFields4("0");
                        UpdateSterileDetail(listData.get(xposition).getFields1(), "0");
                    }

            }
        });
        tFields1.setText( (position+1)+"" );
        //tFields1.setText( listData.get(position).getFields1() );
        tFields2.setText( listData.get(position).getFields2() );
        tFields3.setText( listData.get(position).getFields3() );
        if(listData.get(position).getFields4().equals("1")) {
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);
        }

        return v;
    }

    public void UpdateSterileDetail(String xId,String xStatus) {
        class ListData extends AsyncTask<String, Void, String> {
            getUrl iFt = new getUrl();
            HTTPConnect ruc = new HTTPConnect();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSONArray setRs = null;
                final String TAG_RESULTS="result";

                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        Log.d("AAAA",c.getString("Finish"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xId",params[0]);
                data.put("xStatus",params[1]);
                String result = ruc.sendPostRequest(iFt.setsteriledocdetail(),data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( xId,xStatus );
    }

}
