package com.phc.cssd;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Export_database extends AppCompatActivity {

    private Spinner database;
    private Button Export;
    private ListView list_database;
    private Button download;

    private String TAG_RESULTS = "result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();

    String database_name = "";
    String tag = "";

    ArrayList<String> liststatus = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_export_database);

        getSupportActionBar().hide();

        byWidget();
    }

    private void byWidget() {
        database = (Spinner) findViewById(R.id.database);
        setSpinnerStatus();

        database.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    database_name = "";
                } else if (position == 1) {
                    database_name = "cssd_vcy";
                } else if (position == 2) {
                    database_name = "cssd_vcy_2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Export = (Button) findViewById(R.id.Export);
        Export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!database_name.equals("-")){
                    ExportDataBase();
                }else {
                    Toast.makeText(Export_database.this, "กรุณาเลือกฐานข้อมูล", Toast.LENGTH_SHORT).show();
                }
            }
        });
        list_database = (ListView) findViewById(R.id.list_database);
        download = (Button) findViewById(R.id.download);

    }

    public void ExportDataBase() {
        class ExportDataBase extends AsyncTask<String, Void, String> {
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
                        tag = c.getString("Successfully backed up");
                        if (!tag.equals("")){
                            Toast.makeText(Export_database.this, "ทำรายการเสร็จสิ้น", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Export_database.this, "ทำรายการไม่เสร็จสิ้น", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("BANK111",tag);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("table",database_name);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "data_export/export_database.php", data);
                    Log.d("BANK",data+"");
                    Log.d("BANK",result);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
        }
        ExportDataBase obj = new ExportDataBase();
        obj.execute();
    }

    public void setSpinnerStatus(){
        liststatus.add("-");
        liststatus.add("cssd_vcy");
        liststatus.add("cssd_vcy_2");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, liststatus);
        database.setAdapter(adapter);
    }
}
