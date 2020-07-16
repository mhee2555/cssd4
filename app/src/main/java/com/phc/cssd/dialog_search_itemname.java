package com.phc.cssd;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.AdapterItemSearch;
import com.phc.cssd.properties.Response_Itemname_Search;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class dialog_search_itemname extends Activity {

    ArrayList<Response_Itemname_Search> arr_search = new ArrayList<Response_Itemname_Search>();
    ListView listview;
    EditText txt_search;
    Intent intent;
    String doctypeno;
    String B_ID;
    String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search_itemname);
        getBundleType();
        initialize();
    }

    private void getBundleType() {
        intent = getIntent();
        Bundle bd = getIntent().getExtras();

        try{
            if(intent.hasExtra("type")) {
                doctypeno = bd.getString("type");
            }
        }catch (NullPointerException e){
            if(!intent.hasExtra("type")) {
                doctypeno = "";
            }
        }
        B_ID = intent.getStringExtra("B_ID");

    }

    private void initialize() {
        txt_search = (EditText) findViewById(R.id.edit_search);
        txt_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            ListData(txt_search.getText().toString());
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }

    public void ListData(String Search) {
        class ListData extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Itemname_Search newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    arr_search.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Itemname_Search();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setItemcode(c.getString("itemcode"));
                        newsData.setItemname(c.getString("itemname"));
                        arr_search.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.listview);
                    lv1.setAdapter(new AdapterItemSearch( dialog_search_itemname.this, arr_search));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Search",params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.searchItem(),data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Search );
    }

    public void GetDataitem(String temp){
        Intent intent = new Intent();
        setResult(111, intent);
        intent.putExtra("type","0");
        intent.putExtra("StrData",temp);
        finish();
    }
}
