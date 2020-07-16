package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class dialog_remark_sendsterile extends Activity {

    LinearLayout R1,R2,R3;
    CheckBox check1,check2,check3,check4,check5,check6;
    EditText text_remark;
    Button summit,cancle;

    String Itemname,Usagecode,DepID,DocNoSend;
    String datacheck,EmpCode;

    Intent intent;
    private String TAG_RESULTS = "result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_remark_sendsterile);
        byIntent();
        initialize();
        PutDataCheckBox1();
        PutDataCheckBox2();
        PutDataCheckBox3();
        PutDataCheckBox4();
        PutDataCheckBox5();
        PutDataCheckBox6();
    }

    public void byIntent() {
        intent = getIntent();
        Itemname = intent.getStringExtra("Itemname");
        Usagecode = intent.getStringExtra("Usagecode");
        DepID = intent.getStringExtra("DepID");
        DocNoSend = intent.getStringExtra("DocNoSend");
        EmpCode = intent.getStringExtra("EmpCode");
    }

    public void initialize() {
        check1 = (CheckBox) findViewById(R.id.check1);
        check2 = (CheckBox) findViewById(R.id.check2);
        check3 = (CheckBox) findViewById(R.id.check3);
        check4 = (CheckBox) findViewById(R.id.check4);
        check5 = (CheckBox) findViewById(R.id.check5);
        check6 = (CheckBox) findViewById(R.id.check6);

        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (check1.isChecked()) {
                    check1.setChecked(true);
                    check2.setChecked(false);
                    check3.setChecked(false);
                    check4.setChecked(false);
                    check5.setChecked(false);
                    check6.setChecked(false);
                }
            }
        });

        check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (check2.isChecked()) {
                    check2.setChecked(true);
                    check1.setChecked(false);
                    check3.setChecked(false);
                    check4.setChecked(false);
                    check5.setChecked(false);
                    check6.setChecked(false);
                }
            }
        });

        check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (check3.isChecked()) {
                    check3.setChecked(true);
                    check1.setChecked(false);
                    check2.setChecked(false);
                    check4.setChecked(false);
                    check5.setChecked(false);
                    check6.setChecked(false);
                }
            }
        });

        check4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (check4.isChecked()) {
                    check4.setChecked(true);
                    check1.setChecked(false);
                    check2.setChecked(false);
                    check3.setChecked(false);
                    check5.setChecked(false);
                    check6.setChecked(false);
                }
            }
        });

        check5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (check5.isChecked()) {
                    check5.setChecked(true);
                    check1.setChecked(false);
                    check2.setChecked(false);
                    check3.setChecked(false);
                    check4.setChecked(false);
                    check6.setChecked(false);
                }
            }
        });

        check6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (check6.isChecked()) {
                    check6.setChecked(true);
                    check1.setChecked(false);
                    check2.setChecked(false);
                    check3.setChecked(false);
                    check4.setChecked(false);
                    check5.setChecked(false);
                }
            }
        });

        R1 = (LinearLayout) findViewById(R.id.R1);
        R2 = (LinearLayout) findViewById(R.id.R2);
        R3 = (LinearLayout) findViewById(R.id.R3);
        text_remark = (EditText) findViewById(R.id.text_remark);
        summit = (Button) findViewById(R.id.summit);
        summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check1.isChecked()){
                    datacheck = "1";
                }else if (check2.isChecked()){
                    datacheck = "2";
                }else if (check3.isChecked()){
                    datacheck = "3";
                }else if (check4.isChecked()){
                    datacheck = "4";
                }else if (check5.isChecked()){
                    datacheck = "5";
                }else if (check6.isChecked()){
                    datacheck = "6";
                }
                SaveRemark(datacheck,text_remark.getText().toString(),DocNoSend,Usagecode,Itemname,DepID);
            }
        });

        cancle = (Button ) findViewById(R.id.cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void SaveRemark(final String remarkselect,
                           final String noteremark,
                           final String senddocno,
                           final String usagecode,
                           final String itemname,
                           final String depname) {
        class SaveRemark extends AsyncTask<String, Void, String> {
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

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("remarkselect",remarkselect);
                data.put("noteremark",noteremark);
                data.put("senddocno",senddocno);
                data.put("usagecode",usagecode);
                data.put("itemname",itemname);
                data.put("depname",depname);
                data.put("EmpCode",EmpCode);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_save_remark_send.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        SaveRemark obj = new SaveRemark();
        obj.execute();
    }

    public void PutDataCheckBox1() {
        class PutDataCheckBox1 extends AsyncTask<String, Void, String> {
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
                        if (!c.getString("NameType").equals("")){
                            check1.setText(c.getString("NameType"));
                        }else {
                            R1.setVisibility(View.GONE);
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
                data.put("data","1");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_remark_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheckBox1 obj = new PutDataCheckBox1();
        obj.execute();
    }

    public void PutDataCheckBox2() {
        class PutDataCheckBox2 extends AsyncTask<String, Void, String> {
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
                        if (!c.getString("NameType").equals("")){
                            check2.setText(c.getString("NameType"));
                        }else {
                            check2.setVisibility(View.GONE);
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
                data.put("data","2");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_remark_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheckBox2 obj = new PutDataCheckBox2();
        obj.execute();
    }

    public void PutDataCheckBox3() {
        class PutDataCheckBox3 extends AsyncTask<String, Void, String> {
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
                        if (!c.getString("NameType").equals("")){
                            check3.setText(c.getString("NameType"));
                        }else {
                            R2.setVisibility(View.GONE);
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
                data.put("data","3");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_remark_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheckBox3 obj = new PutDataCheckBox3();
        obj.execute();
    }

    public void PutDataCheckBox4() {
        class PutDataCheckBox4 extends AsyncTask<String, Void, String> {
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
                        if (!c.getString("NameType").equals("")){
                            check4.setText(c.getString("NameType"));
                        }else {
                            check4.setVisibility(View.GONE);
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
                data.put("data","4");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_remark_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheckBox4 obj = new PutDataCheckBox4();
        obj.execute();
    }

    public void PutDataCheckBox5() {
        class PutDataCheckBox5 extends AsyncTask<String, Void, String> {
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
                        if (!c.getString("NameType").equals("")){
                            check5.setText(c.getString("NameType"));
                        }else {
                            R3.setVisibility(View.GONE);
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
                data.put("data","5");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_remark_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheckBox5 obj = new PutDataCheckBox5();
        obj.execute();
    }

    public void PutDataCheckBox6() {
        class PutDataCheckBox6 extends AsyncTask<String, Void, String> {
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
                        if (!c.getString("NameType").equals("")){
                            check6.setText(c.getString("NameType"));
                        }else {
                            check6.setVisibility(View.GONE);
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
                data.put("data","6");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_remark_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheckBox6 obj = new PutDataCheckBox6();
        obj.execute();
    }
}
