package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.data.AsonData;
import com.phc.cssd.adapter.ItemDetailAdapter;
import com.phc.cssd.model.ModelItemDetail;
import com.phc.cssd.model.ModelItems;
import com.phc.cssd.model.ModelMasterData;
import com.phc.cssd.url.Url;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class dialog_Load_Img extends Activity {

    String itemcode,sel,usagecode,itemname;
    TextView itemname_set,usagecode_set,sub_itemname_set,unit_set;
    ImageView img;

    private HTTPConnect httpConnect = new HTTPConnect();
    private String TAG_RESULTS = "result";
    private JSONArray rs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_load_img);
        byIntent();
        initialize();
        ShowDetail();
    }

    private void byIntent() {
        // Argument
        Intent intent = getIntent();
        itemcode = intent.getStringExtra("itemcode");
        usagecode = intent.getStringExtra("usagecode");
        itemname = intent.getStringExtra("itemname");
        sel = intent.getStringExtra("sel");
    }

    public void initialize() {
        itemname_set = (TextView) findViewById(R.id.itemname_set);
        usagecode_set = (TextView) findViewById(R.id.usagecode_set);
        sub_itemname_set = (TextView) findViewById(R.id.sub_itemname_set);
        unit_set = (TextView) findViewById(R.id.unit_set);
        img = (ImageView) findViewById(R.id.img);
    }

    public void ShowDetail() {
        class ShowDetail extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() { super.onPreExecute(); }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        try {
                            if (sel.equals("2")){
                                itemname_set.setText("ชื่อรายการเซ็ท : " + c.getString("itemnamesel2"));
                            }else {
                                itemname_set.setText("ชื่อรายการเซ็ท : " + itemname);
                            }
                            usagecode_set.setText("รหัสใช้งาน : " + usagecode);
                            if (sel.equals("1")){
                                sub_itemname_set.setVisibility(View.GONE);
                            }else {
                                sub_itemname_set.setText("รายละเอียดเซ็ท : " + itemname);
                            }
                            if (c.getString("ID").equals("null")){
                                unit_set.setText("Unit : ไม่มีข้อมูล");
                            }else {
                                unit_set.setText("Unit : "+ c.getString("ID"));
                            }

                            URL imageUrl = new URL(Url.URL + "cssd_image/"+c.getString("Picture"));
                            System.out.println(Url.URL + "cssd_image/"+c.getString("Picture"));

                            if (!c.getString("Picture").equals("")){
                                img.setBackgroundResource(R.color.colorWhite);
                                Picasso.get().load(String.valueOf(imageUrl)).networkPolicy(NetworkPolicy.NO_CACHE)
                                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                                        .into(img);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            //return;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                if(usagecode != null){
                    data.put("usagecode",usagecode);
                }else {
                    data.put("usagecode","");
                }

                if(itemcode != null){
                    data.put("itemcode",itemcode);
                }else {
                    data.put("itemcode","");
                }

                if(itemname != null){
                    data.put("itemname",itemname);
                }else {
                    data.put("itemname","");
                }

                data.put("sel",sel);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_show_detail_item_send.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
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
