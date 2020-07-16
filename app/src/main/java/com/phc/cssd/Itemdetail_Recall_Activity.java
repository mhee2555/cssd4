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
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.string.Cons;
import com.phc.cssd.adapter.AdapterItemdetaill;
import com.phc.cssd.model.ModelRecallDetail;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Itemdetail_Recall_Activity extends Activity {

    String DocNo;
    Button button5;
    ListView list_detail_recall;

    HTTPConnect ruc = new HTTPConnect();
    private String TAG_RESULTS="result";
    private JSONArray rs = null;

    private List<com.phc.cssd.model.ModelRecallDetail> ModelRecallDetail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetail__recall);

        byIntent();

        initialize();

        getlistdata();

    }

    private void byIntent(){
        Intent intent = getIntent();
        DocNo = intent.getStringExtra("DocNo");
    }

    private void initialize() {
        list_detail_recall = (ListView) findViewById(R.id.list_detail_recall);
        button5 = (Button) findViewById(R.id.button5);

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getlistdata() {
        class getlistdata extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(Itemdetail_Recall_Activity.this);
            // variable
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
                    List<ModelRecallDetail> list = new ArrayList<>();
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (c.getString("flag").equals("true")) {
                            list.add(
                                    get(
                                            c.getString("itemname"),
                                            c.getString("UsageCode"),
                                            c.getString("ExpireDate")
                                    )
                            );
                            ModelRecallDetail = list;
                            ArrayAdapter<ModelRecallDetail> adapter;
                            adapter = new AdapterItemdetaill(Itemdetail_Recall_Activity.this, ModelRecallDetail);
                            list_detail_recall.setAdapter(adapter);
                        }else {
                            list_detail_recall.setAdapter(null);
                            Toast.makeText(Itemdetail_Recall_Activity.this, "ไม่พบข้อมูล !!", Toast.LENGTH_SHORT).show();
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
                data.put("DocNo",DocNo);
                String result = null;
                try {
                    result = ruc.sendPostRequest(getUrl.xUrl + "cssd_select_itemdetail_recall.php", data);
                    Log.d("BANK",data+"");
                    Log.d("BANK",result);
                }catch(Exception e){
                    e.printStackTrace();
                }

                return result;
            }
            private ModelRecallDetail get(String itemname, String UsageCode,String ExpireDate) {
                return new ModelRecallDetail( itemname,UsageCode,ExpireDate);
            }
            // =========================================================================================
        }

        getlistdata obj = new getlistdata();
        obj.execute();
    }
}
