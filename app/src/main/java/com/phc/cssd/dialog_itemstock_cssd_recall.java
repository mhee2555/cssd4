package com.phc.cssd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.string.Cons;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class dialog_itemstock_cssd_recall extends Activity {

    String UsageCode;

    TextView item_n;
    TextView usage_c;
    TextView doc_pay;
    TextView time_d;
    TextView department;

    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dialog_itemstock_cssd_recall);

        byIntent();

        init();

    }

    private void byIntent(){
        Intent intent = getIntent();
        UsageCode = intent.getStringExtra("UsageCode");
    }

    public void init(){
        item_n = ( TextView ) findViewById(R.id.item_n);
        usage_c = ( TextView ) findViewById(R.id.usage_c);
        doc_pay = ( TextView ) findViewById(R.id.doc_pay);
        time_d = ( TextView ) findViewById(R.id.time_d);
        department = ( TextView ) findViewById(R.id.department);
        DisplayData();
    }

    public void DisplayData() {
        class DisplayData extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(dialog_itemstock_cssd_recall.this);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.setCancelable(false);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        item_n.setText(c.getString("itemname"));
                        usage_c.setText(c.getString("UsageCode"));
                        doc_pay.setText(c.getString("DocNo"));
                        time_d.setText(c.getString("CreateDate"));
                        department.setText(c.getString("DepName"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally{
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("UsageCode",UsageCode);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_data_recall.php", data);
                    Log.d("DHKJHF",data+"");
                    Log.d("DHKJHF",result);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        DisplayData obj = new DisplayData();
        obj.execute();
    }
}
