package com.phc.cssd.master_data;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.R;
import com.phc.cssd.adapter.AdapterEmployeeSearch;
import com.phc.cssd.properties.Response_Employee_Search;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class employee_search extends Activity {
    ArrayList<Response_Employee_Search> arr_search = new ArrayList<Response_Employee_Search>();
    ListView listview;
    EditText txt_search;

    String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();

    String numberbtn="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_search);
        setTitle("ค้นหาข้อมูลพนักงาน");

        try{
            Bundle bd = getIntent().getExtras();
            if(bd!=null){
                numberbtn = bd.getString("number");
            }else {
                numberbtn = "";
            }
        }catch (Throwable e){
            numberbtn = "";
        }
        initialize();
    }

    private void initialize() {
        txt_search = (EditText) findViewById(R.id.edit_search);
        txt_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                ListData(txt_search.getText().toString());
                return false;
            }
        });
    }

    public void ListData(String Search) {
        class ListData extends AsyncTask<String, Void, String> {
//            ProgressDialog loading;
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(employee_search.this, "Please Wait",null, true, true);
//            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();
                try {
                    Response_Employee_Search newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    arr_search.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Employee_Search();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setEmpCode(c.getString("EmpCode"));
                        newsData.setName(c.getString("Fname"));
                        newsData.setDept(c.getString("Dept"));
                        arr_search.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.listview);
                    lv1.setAdapter(new AdapterEmployeeSearch( employee_search.this, arr_search));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Search",params[0]);
                String result = ruc.sendPostRequest(iFt.searchEmployee(),data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Search );
    }

    public void GetDataEmp(String temp){
        Intent intent = new Intent();
        setResult(111, intent);
        intent.putExtra("StrData",temp+"/"+numberbtn);
        finish();
    }


}
