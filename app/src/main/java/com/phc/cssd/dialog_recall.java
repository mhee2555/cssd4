package com.phc.cssd;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.Aux4Adapter_Recall;
import com.phc.cssd.adapter.Aux6Adapter_dialog_Recall;
import com.phc.cssd.properties.Response_Aux_Recall;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class dialog_recall extends AppCompatActivity {

    ArrayList<Response_Aux_Recall> array_recall = new ArrayList<Response_Aux_Recall>();
    ArrayList<Response_Aux_Recall> array_set = new ArrayList<Response_Aux_Recall>();
    Intent intent;
    ListView list_document;
    ListView list_itemset;
    String UserID;
    String Dept;
    String B_ID;
    String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_recall);

        getBundleuser();

        initialize();

        listrecall(Dept);

    }

    private void initialize() {
        list_document = (ListView) findViewById(R.id.list_document);
        list_itemset = (ListView) findViewById(R.id.list_itemset);
    }

    private void getBundleuser() {
        intent = getIntent();
        Bundle bd = getIntent().getExtras();

        try{
            if(intent.hasExtra("UserID")||intent.hasExtra("Dept")) {
                UserID = bd.getString("UserID");
                Dept = bd.getString("Dept");
            }
        }catch (NullPointerException e){
            if(!intent.hasExtra("UserID") || !intent.hasExtra("Dept")) {
                UserID = "";
                Dept = "";
            }
        }
        B_ID = intent.getStringExtra("B_ID");
    }

    public void listrecall(String Dept) {
        class listrecall extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Aux_Recall newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    array_recall.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux_Recall();
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            newsData.setFields1(c.getString("DocNo"));
                            newsData.setFields2(c.getString("DocDate"));
                            newsData.setFields3(c.getString("Dept"));
                            newsData.setFields4(c.getString("RefDocNo"));
                            newsData.setFields5(c.getString("Remark"));
                            newsData.setFields6(c.getString("IsStatus"));
                            newsData.setFields7(c.getString("DeptID"));
                            newsData.setFields8(c.getString("DocTypeNo"));
                            array_recall.add(newsData);
                        }else{
                            Toast.makeText(dialog_recall.this, "ไม่พบข้อมูล", Toast.LENGTH_SHORT).show();
                        }
                    }
                    list_document.setAdapter(new Aux6Adapter_dialog_Recall( dialog_recall.this, array_recall));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Dept",params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.dialog_recall(),data);
                return  result;
            }
        }

        listrecall ru = new listrecall();
        ru.execute(Dept);
    }

    public void setdetail_itemset(String DocNo) {
        class setdetail_itemset extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Aux_Recall newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    array_set.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux_Recall();
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            newsData.setFields1(c.getString("No"));
                            newsData.setFields2(c.getString("Itemname"));
                            newsData.setFields3(c.getString("Usagecode"));
                            newsData.setFields4(c.getString("Expiredate"));
                            array_set.add(newsData);
                        }
                    }

                    list_itemset.setAdapter(new Aux4Adapter_Recall( dialog_recall.this, array_set));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.set_detail_itemset(),data);
                return  result;
            }
        }

        setdetail_itemset ru = new setdetail_itemset();
        ru.execute(DocNo);
    }
}
