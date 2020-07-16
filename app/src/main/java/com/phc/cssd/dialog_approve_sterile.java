package com.phc.cssd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.string.Cons;
import com.phc.cssd.adapter.ListApproveSterileAdapter;
import com.phc.cssd.model.ModelApprove;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class dialog_approve_sterile extends Activity {

    String userid;
    String DocNo;
    String B_ID;
    String sel;
    String Cnt;

    TextView approve_cnt;
    ListView rq_listdocdetail;

    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();
    private List<ModelApprove> Model_RQ = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_approve_sterile);
        byIntent();
        initialize();
        getlistdata();
    }

    private void byIntent() {
        // Argument
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        DocNo = intent.getStringExtra("DocNo");
        B_ID = intent.getStringExtra("B_ID");
        sel = intent.getStringExtra("sel");
        Cnt = intent.getStringExtra("Cnt");
    }

    public void initialize() {
        rq_listdocdetail = (ListView) findViewById(R.id.rq_listdocdetail);
        approve_cnt = (TextView ) findViewById(R.id.approve_cnt);
        approve_cnt.setText("จำนวน :  "+Cnt+"  รายการ");
    }

    public void getlistdata() {
        class getlistdata extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(dialog_approve_sterile.this);

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
                    List<ModelApprove> list = new ArrayList<>();
                    int index = 1;
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        list.add(
                                get(
                                        c.getString("UsageCode"),
                                        c.getString("DepID"),
                                        c.getString("itemname"),
                                        index
                                )
                        );
                        index++;
                        Model_RQ = list;
                        ArrayAdapter<ModelApprove> adapter;
                        adapter = new ListApproveSterileAdapter(dialog_approve_sterile.this,Model_RQ);
                        rq_listdocdetail.setAdapter(adapter);
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
                data.put("B_ID",B_ID);
                data.put("DocNo", DocNo);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_approve_sterile.php", data);
                    Log.d("KJDGDK",data+"");
                    Log.d("KJDGDK",result);
                }catch(Exception e){
                    e.printStackTrace();
                }

                return result;
            }
            private ModelApprove get(String UsageCode, String DepID, String ItemName, int index) {
                return new ModelApprove(UsageCode,DepID,ItemName,index);
            }
            // =========================================================================================
        }
        getlistdata obj = new getlistdata();
        obj.execute();
    }
}