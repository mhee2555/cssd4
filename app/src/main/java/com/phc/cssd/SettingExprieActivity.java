package com.phc.cssd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.string.Cons;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SettingExprieActivity extends Activity {

    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();

    ImageView switch_set;
    EditText set_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_exprie);

        byWidget();

        onDisplay();
    }

    private void byWidget() {
        switch_set = (ImageView) findViewById(R.id.switch_set);
        set_day = (EditText) findViewById(R.id.set_day);

        set_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_day.setSelectAllOnFocus(true);
                set_day.selectAll();
            }
        });

//        set_day.setOnFocusChangeListener(new View.OnFocusChangeListener(){
//            public void onFocusChange(View v, boolean hasFocus){
//                if (hasFocus) ((EditText)v).selectAll();
//            }
//        });

        switch_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder quitDialog = new AlertDialog.Builder(SettingExprieActivity.this);
                quitDialog.setTitle(Cons.TITLE);
                quitDialog.setIcon(R.drawable.pose_favicon_2x);
                quitDialog.setMessage("ต้องการบันทึกข้อมูลหรือไม่ ?");
                quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onSetDay(set_day.getText().toString(),"1");
                    }
                });
                quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                quitDialog.show();
            }
        });
    }

    private void onDisplay(){
        class onDisplay extends AsyncTask<String, Void, String> {
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
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);

                        set_day.setText(c.getString("Nobeforeexp"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_settingapp.php", data);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        onDisplay obj = new onDisplay();
        obj.execute();
    }

    private void onSetDay(final String day , final String type){
        class onSetDay extends AsyncTask<String, Void, String> {
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
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        if (c.getString("true").equals("true")){
                            Toast.makeText(SettingExprieActivity.this, "บันทึกข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("type",type);
                data.put("day",day);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_update_settingapp.php", data);
                    Log.d("BANK7",data+"");
                    Log.d("BANK7",result);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        onSetDay obj = new onSetDay();
        obj.execute();
    }

}