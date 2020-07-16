package com.phc.cssd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.string.Cons;
import com.phc.cssd.adapter.ListUsageEms;
import com.phc.cssd.model.ModelUsageEms;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class dialog_usagecode_ems_sterile extends Activity {

    String UsageCode;
    String DocNo;
    String B_ID;
    String sel;
    String cnt;
    String itemcode;

    TextView cnt_item;
    ListView rq_listdocdetail;

    Button saveems;

    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();
    private List<ModelUsageEms> Model_RQ = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_usagecode_ems_sterile);
        byIntent();
        initialize();
        getlistdata();
    }

    private void byIntent() {
        // Argument
        Intent intent = getIntent();
        UsageCode = intent.getStringExtra("UsageCode");
        DocNo = intent.getStringExtra("DocNo");
        B_ID = intent.getStringExtra("B_ID");
        sel = intent.getStringExtra("sel");
        cnt = intent.getStringExtra("cnt");
        itemcode = intent.getStringExtra("itemcode");
    }

    public void initialize() {
        rq_listdocdetail = (ListView) findViewById(R.id.rq_listdocdetail);
        cnt_item = (TextView) findViewById(R.id.cnt_item);
        saveems = (Button) findViewById(R.id.saveems);
        saveems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetIsstatus();
            }
        });
    }

    public void SetIsstatus() {
        class SetIsstatus extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(dialog_usagecode_ems_sterile.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        if (c.getString("finish").equals("true")){
                            finish();
                        }else {
                            finish();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DOC_NO", DocNo);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_set_status_ems_sterile.php", data);
                    Log.d("KJDGDK",data+"");
                    Log.d("KJDGDK",result);
                }catch(Exception e){
                    e.printStackTrace();
                }

                return result;
            }
            // =========================================================================================
        }
        SetIsstatus obj = new SetIsstatus();
        obj.execute();
    }

    public void getlistdata() {
        class getlistdata extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(dialog_usagecode_ems_sterile.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    List<ModelUsageEms> list = new ArrayList<>();
                    int index = 1;
                    int index1 = 0;
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        list.add(
                                get(
                                        c.getString("UsageCode"),
                                        c.getString("itemname"),
                                        c.getString("Shelflife"),
                                        c.getString("RemarkExpress"),
                                        index
                                )
                        );
                        index++;
                        index1++;
                        Model_RQ = list;
                        ArrayAdapter<ModelUsageEms> adapter;
                        adapter = new ListUsageEms(dialog_usagecode_ems_sterile.this,Model_RQ);
                        rq_listdocdetail.setAdapter(adapter);
                    }
                    cnt_item.setText("จำนวน :  "+index1+"  รายการ");
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DOC_NO", DocNo);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_check_usage_ems_dialog_sterile.php", data);
                    Log.d("KJDGDK",data+"");
                    Log.d("KJDGDK",result);
                }catch(Exception e){
                    e.printStackTrace();
                }

                return result;
            }
            private ModelUsageEms get(String UsageCode, String ItemName, String day, String remark, int index) {
                return new ModelUsageEms(UsageCode,ItemName,day,remark,index);
            }
            // =========================================================================================
        }
        getlistdata obj = new getlistdata();
        obj.execute();
    }
}