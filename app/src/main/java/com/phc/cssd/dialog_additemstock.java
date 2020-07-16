package com.phc.cssd;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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

import java.util.HashMap;

public class dialog_additemstock extends Activity {

    String ItemCode;
    String B_ID;

    TextView txt_item_code;

    EditText edt_qty;

    Button btn_create;

    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_additemstock);
        byIntent();
        init();
    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        ItemCode = intent.getStringExtra("ItemCode");
        B_ID = intent.getStringExtra("B_ID");
    }

    public void init(){
        txt_item_code= (TextView) findViewById(R.id.txt_item_code);
        edt_qty= (EditText ) findViewById(R.id.edt_qty);
        btn_create= (Button ) findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(!edt_qty.getText().toString().trim().equals("")) {
                    onCreate();
                }else{
                    Toast.makeText(dialog_additemstock.this, "ยังไม่ได้ป้อนจำนวน !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txt_item_code.setText("รหัส : "+ItemCode);
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
                            Toast.makeText(dialog_additemstock.this, "เพิ่มรายการ " + edt_qty.getText().toString() + " รายการ. เรียบร้อย!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(dialog_additemstock.this, "ไม่สามารถเพิ่มรายการได้ !!", Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("p_item_code", ItemCode);
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
