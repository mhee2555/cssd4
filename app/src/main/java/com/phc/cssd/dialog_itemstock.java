package com.phc.cssd;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class dialog_itemstock extends Activity {

    TextView itemcode_stock;
    TextView itemname_stock;
    TextView qty_stock;

    String Item_Code;
    String Item_Name;
    String Item_Stock;
    String B_ID;
    String userid;

    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_itemstock);
        byIntent();
        initialize();
        ShowDetail();
    }

    private void byIntent() {
        // Argument
        Intent intent = getIntent();
        Item_Code = intent.getStringExtra("Item_Code");
        Item_Name = intent.getStringExtra("Item_Name");
        B_ID = intent.getStringExtra("B_ID");
        Item_Stock = intent.getStringExtra("Item_Stock");
    }

    public void initialize() {
        itemcode_stock = ( TextView ) findViewById(R.id.itemcode_stock);
        itemname_stock = ( TextView ) findViewById(R.id.itemname_stock);
        qty_stock = ( TextView ) findViewById(R.id.qty_stock);

        itemcode_stock.setText("รหัส : "+Item_Code);
        itemname_stock.setText("ชื่อรายการ : "+Item_Name);
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

                        if (c.getString("Qty").equals("null")){
                            qty_stock.setText("จำนวนในสต๊อค : " + "0");
                        }else {
                            qty_stock.setText("จำนวนในสต๊อค : " + c.getString("Qty"));
                        }

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
                    Log.d("KJDGDK",data+"");
                    Log.d("KJDGDK",result);
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
}
