package com.phc.cssd;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CssdCreateItemStock extends AppCompatActivity {

    //------------------------------------------------
    // Background Worker Process Variable
    private boolean Success = false;
    private ArrayList<String> data = null;
    private int size = 0;
    //------------------------------------------------

    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();

    //------------------------------------------------
    // Session Variable
    private String userid;
    private String B_ID = null;
    private String Item_Code;
    private String Item_Name;
    private String Item_Stock;
    //------------------------------------------------

    private TextView txt_item_code;
    private TextView txt_all_item_stock;
    private EditText edt_qty;
    private Button btn_create;

    HTTPConnect ruc = new HTTPConnect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cssd_create_item_stock);
        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getSupportActionBar().hide();

        byIntent();

        txt_all_item_stock = (TextView)findViewById(R.id.txt_all_item_stock);

        ShowDetail();

        byWidget();
    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        Item_Code = intent.getStringExtra("Item_Code");
        Item_Name = intent.getStringExtra("Item_Name");
        Item_Stock = intent.getStringExtra("Item_Stock");
        B_ID = intent.getStringExtra("B_ID");
    }

    private void byWidget(){
        txt_item_code = (TextView)findViewById(R.id.txt_item_code);
        edt_qty = (EditText)findViewById(R.id.edt_qty);
        btn_create = (Button) findViewById(R.id.btn_create);
        txt_item_code.setText("รหัส : " + Item_Code + " ชื่อรายการ : " + Item_Name);
        btn_create.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(!edt_qty.getText().toString().trim().equals("")) {
                    onCreate();
                }else{
                    Toast.makeText(CssdCreateItemStock.this, "ยังไม่ได้ป้อนจำนวน !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void ShowDetail() {

        class ShowDetail extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);

                        txt_all_item_stock.setText("จำนวนในสต๊อค : " + c.getString("Qty"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Item_Code",Item_Code);
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_item_qty.php", data);
                }catch(Exception e){
                    e.printStackTrace();
                }

                return result;
            }
            // =========================================================================================
        }

        ShowDetail obj = new ShowDetail();
        obj.execute();
    }

    public void onCreate() {

        final String QTY = edt_qty.getText().toString();

        class DisplayDetail extends AsyncTask<String, Void, String> {

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);

                        String Result = c.getString("result");
                        if(Result.equals("true")){
                            Toast.makeText(CssdCreateItemStock.this, "เพิ่มรายการ " + edt_qty.getText().toString() + " รายการ. เรียบร้อย!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(CssdCreateItemStock.this, "ไม่สามารถเพิ่มรายการได้ !!", Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_item_code", Item_Code);
                data.put("p_item_qty", QTY);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_create_item_stock.php", data);
                Log.d("BALNDL",data+"");
                Log.d("BALNDL",result);

                return result;
            }
        }
        DisplayDetail obj = new DisplayDetail();
        obj.execute();
    }
}
