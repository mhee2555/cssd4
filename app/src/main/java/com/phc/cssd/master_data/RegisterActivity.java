package com.phc.cssd.master_data;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.SettingActivity;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button register;
    EditText uname;
    EditText pword;
    EditText fname;
    EditText lname;
    TextView chklabel;
    ImageView backbtn;
    TextWatcher txt;
    Spinner dept;
    Spinner leg;
    ArrayList<String> SpinnerArr = new ArrayList<String>();
    ArrayList<String> SpinnerArr1 = new ArrayList<String>();
    Boolean checkbool;

    String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_register);
        getSupportActionBar().hide();
//        Intent intent = new Intent();
//        setResult(101, intent);
//        finish();
        initialize();
    }

    @Override
    public void onBackPressed() {
        RegisterActivity.super.onBackPressed();
        finish();
    }

    private void initialize() {
        final LinearLayout ckuname = (LinearLayout) findViewById(R.id.checkusernametxt);
        chklabel = (TextView) findViewById(R.id.chklabel);
        uname = (EditText) findViewById(R.id.txt_username);
        checkbool = false;
        txt = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ckuname.setVisibility(View.GONE);
            }
        };
        uname.addTextChangedListener(txt);
        pword = (EditText) findViewById(R.id.txt_password);
        fname = (EditText) findViewById(R.id.txt_fname);
        lname = (EditText) findViewById(R.id.txt_lname);

        final Spinner leg = findViewById(R.id.spinner_leg);
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.language,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leg.setAdapter(adapter);
        leg.setOnItemSelectedListener(this);

        dept = (Spinner) findViewById(R.id.spinner_dept);
        SpinnerData();




        register = (Button) findViewById(R.id.button_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!uname.getText().toString().equals("") &&
                        !pword.getText().toString().equals("") &&
                        !fname.getText().toString().equals("") &&
                        !lname.getText().toString().equals("")
                        ) {
                    if(!dept.getSelectedItem().equals("-")){
                        register_insert(uname.getText().toString(), pword.getText().toString(), fname.getText().toString(), lname.getText().toString(), dept.getSelectedItem().toString(), leg.getSelectedItem().toString());
                    }else{
                        Toast.makeText(RegisterActivity.this,"กรุณาเลือกแผนก", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this,"กรุณากรอกข้อมูลให้ถูกต้องและครบถ้วน", Toast.LENGTH_SHORT).show();
                }
            }
        });
        backbtn = (ImageView) findViewById(R.id.imageBack);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void register_insert( String uname, String pword, String fname, String lname, String dept, String leg) {
        class register_insert extends AsyncTask<String, Void, String> {
            LinearLayout ckuname = (LinearLayout) findViewById(R.id.checkusernametxt);
            EditText uname = (EditText) findViewById(R.id.txt_username);
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")){
                            Toast.makeText(RegisterActivity.this,"สมัครการใช้งานสำเร็จ", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, SettingActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this, "username นี้ถูกใช้แล้ว กรุณากรอก username ใหม่", Toast.LENGTH_SHORT).show();
                            uname.setText("");
                            uname.requestFocus();
                            ckuname.setVisibility(View.VISIBLE);
                            chklabel.setTextColor(Color.parseColor("#d00000"));
                            chklabel.setText("กรุณากรอก Username ใหม่");
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("uname",params[0]);
                data.put("pword",params[1]);
                data.put("fname",params[2]);
                data.put("lname",params[3]);
                data.put("dept",params[4]);
                data.put("leg",params[5]);
                String result = ruc.sendPostRequest(iFt.Register_User(),data);
                Log.d("BANK",data+"");
                Log.d("BANK",result);
                return  result;
            }

        }

        register_insert ru = new register_insert();
        ru.execute( uname,pword,fname,lname,dept,leg );
    }

    public void SpinnerData() {
        class SpinnerData extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    SpinnerArr.clear();
                    SpinnerArr.add("-");
                    String Depsel = "";
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        SpinnerArr.add(c.getString("xDepName2"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_spinner_dropdown_item,SpinnerArr);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dept.setAdapter(adapter);
                    int spinnerPosition = adapter.getPosition(Depsel);
                    dept.setSelection(spinnerPosition);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                String result = ruc.sendPostRequest(iFt.get_Spinner_dept(),data);
                return  result;
            }
        }
        SpinnerData ru = new SpinnerData();
        ru.execute();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String leg = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
