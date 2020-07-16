package com.phc.cssd;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.phc.cssd.adapter.Aux4Adapter_Recall_dept;
import com.phc.cssd.adapter.Aux6Adapter_Recall_dept;
import com.phc.cssd.properties.Response_Aux_Recall_dept;
import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Recall_dept extends Activity {

    ArrayList<String> array_doctypeno = new ArrayList<String>();
    ArrayList<String> array_dept = new ArrayList<String>();
    ArrayList<Response_Aux_Recall_dept> array_recall = new ArrayList<Response_Aux_Recall_dept>();
    ArrayList<Response_Aux_Recall_dept> array_set = new ArrayList<Response_Aux_Recall_dept>();
    Spinner dept2;
    Spinner spinner_doctypeno2;
    EditText txtdate2;
    EditText txtno;
    EditText txtps;
    EditText txtrefer;
    Button button_search2;
    Button button_clear;
    Button button_save;
    Button button_print;
    ListView list_document;
    ListView list_itemset;
    Intent intent;
    Calendar myCalendar = Calendar.getInstance();
    String Stringdept;
    String UserID;
    String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recall_dept);
        Stringdept = "";
        initialize();
        getBundleuser();
        getDept(UserID);
    }

    private void getBundleuser() {
        intent = getIntent();
        Bundle bd = getIntent().getExtras();

        try{
            if(intent.hasExtra("UserID")) {
                UserID = bd.getString("UserID");
            }
        }catch (NullPointerException e){
            if(!intent.hasExtra("UserID")) {
                UserID = "";
            }
            Log.d("Error", "Error-bundle");
        }
    }

    private void initialize() {
        //Listview
        list_document  = (ListView) findViewById(R.id.list_document);
        list_itemset  = (ListView) findViewById(R.id.list_itemset);

        //spinner
        spinner_doctypeno2 = (Spinner) findViewById(R.id.spinner_doctypeno2);
        spinner_doctypeno2.setEnabled(false);
        dept2 = (Spinner) findViewById(R.id.dept2);
        dept2.setEnabled(false);
        doctypeno_define();
        SpinnerDept();

        //edittext
        txtdate2 = (EditText) findViewById(R.id.txtdate2);
        txtdate2.setInputType(InputType.TYPE_NULL);
        getsdate(txtdate2);
        txtno = (EditText) findViewById(R.id.txtno);
        txtno.setEnabled(false);
        txtps = (EditText) findViewById(R.id.txtps);
        txtps.setEnabled(false);
        txtrefer = (EditText) findViewById(R.id.txtrefer);
        txtrefer.setEnabled(false);

        //button
        button_search2 = (Button) findViewById(R.id.button_search2);
        button_search2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtdate2.getText().toString().equals("")){
                    dept2.setSelection(0);
                    txtno.setText("");
                    txtps.setText("");
                    txtrefer.setText("");
                    spinner_doctypeno2.setSelection(0);
                    array_recall.clear();
                    array_set.clear();
                    list_document.setAdapter(new Aux6Adapter_Recall_dept( Recall_dept.this, array_recall));
                    list_itemset.setAdapter(new Aux4Adapter_Recall_dept( Recall_dept.this, array_set));
                    listrecall(Stringdept,txtdate2.getText().toString());
                }else{
                    Toast.makeText(Recall_dept.this, "กรุณาเลือกวันที่", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button_clear = (Button) findViewById(R.id.button_clear);
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentdate(txtdate2);
                dept2.setSelection(0);
                txtno.setText("");
                txtps.setText("");
                txtrefer.setText("");
                spinner_doctypeno2.setSelection(0);
                array_recall.clear();
                array_set.clear();
                list_document.setAdapter(new Aux6Adapter_Recall_dept( Recall_dept.this, array_recall));
                list_itemset.setAdapter(new Aux4Adapter_Recall_dept( Recall_dept.this, array_set));
            }
        });
        button_print = (Button) findViewById(R.id.button_print);
        button_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void doctypeno_define() {
        array_doctypeno.add("-");
        array_doctypeno.add("1.เรียกคืนของใกล้หมดอายุ");
        array_doctypeno.add("2.เรียกคืนของหมดอายุ");
        array_doctypeno.add("3.ความเสี่ยง");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Recall_dept.this,android.R.layout.simple_spinner_dropdown_item,array_doctypeno);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_doctypeno2.setAdapter(adapter);
    }

    public void getDept(final String Userid) {
        class getDept extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            Stringdept = c.getString("Dept");
                            listrecall(c.getString("Dept"),txtdate2.getText().toString());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Userid",params[0]);
                String result = ruc.sendPostRequest(iFt.getdept(),data);
                Log.d("test", data+"");
                Log.d("test", result+"");
                return  result;
            }
        }

        getDept ru = new getDept();
        ru.execute(Userid);
    }

    public void SpinnerDept() {
        class SpinnerDept extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    array_dept.clear();
                    array_dept.add("-");
                    String Depsel = "";
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        array_dept.add(c.getString("xDepName2"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Recall_dept.this,android.R.layout.simple_spinner_dropdown_item,array_dept);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dept2.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Pass","");
                String result = ruc.sendPostRequest(iFt.get_department_spinner(),data);
                return  result;
            }
        }

        SpinnerDept ru = new SpinnerDept();
        ru.execute();
    }

    public void getsdate(final EditText txtdate){

        presentdate(txtdate);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                presentdate(txtdate);
            }

        };

        txtdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Recall_dept.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void presentdate(final EditText txtdate) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtdate.setText(sdf.format(myCalendar.getTime()));

    }

    public void listrecall(String Dept, String eDate) {
        class listrecall extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Aux_Recall_dept newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    array_recall.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux_Recall_dept();
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            newsData.setFields1(c.getString("DocNo"));
                            newsData.setFields2(c.getString("DocDate"));
                            newsData.setFields3(c.getString("Dept"));
                            newsData.setFields4(c.getString("RefDocNo"));
                            newsData.setFields5(c.getString("Remark"));
                            newsData.setFields6(c.getString("IsStatus"));
                            newsData.setFields7(c.getString("DeptID"));
                            newsData.setFields8(c.getString("DocTypeNo"));
                            if(c.getString("Delete").equals("1")) {
                                newsData.setIs_Check(true);
                            }else{
                                newsData.setIs_Check(false);
                            }

                            if(c.getString("Delete2").equals("1")) {
                                newsData.setIs_Check2(true);
                            }else{
                                newsData.setIs_Check2(false);
                            }
                            array_recall.add(newsData);
                        }else{
                            Toast.makeText(Recall_dept.this, "ไม่พบข้อมูล", Toast.LENGTH_SHORT).show();
                        }
                    }
                    list_document.setAdapter(new Aux6Adapter_Recall_dept( Recall_dept.this, array_recall));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Dept",params[0]);
                data.put("eDate",params[1]);
                String result = ruc.sendPostRequest(iFt.dialog_recall(),data);
                Log.d("test", data+"");
                Log.d("test", result+"");
                return  result;
            }
        }

        listrecall ru = new listrecall();
        ru.execute(Dept,eDate);
    }


    public void setdetail_itemset(String DocNo, final String bool) {
        class setdetail_itemset extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Aux_Recall_dept newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    array_set.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux_Recall_dept();
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            newsData.setFields1(c.getString("No"));
                            newsData.setFields2(c.getString("Itemname"));
                            newsData.setFields3(c.getString("Usagecode"));
                            newsData.setFields4(c.getString("Expiredate"));
                            if(bool.equals("true")) {
                                newsData.setIs_Check(true);
                            }else{
                                newsData.setIs_Check(false);
                            }
                            array_set.add(newsData);
                        }
                    }
                    list_itemset.setAdapter(new Aux4Adapter_Recall_dept( Recall_dept.this, array_set));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                String result = ruc.sendPostRequest(iFt.set_detail_itemset(),data);
                Log.d("test", data+"");
                Log.d("test", result+"");
                return  result;
            }
        }

        setdetail_itemset ru = new setdetail_itemset();
        ru.execute(DocNo,bool);
    }

    public void setclick_Doc(String docno, String expdate, String dept, String refer, String ps, String doctypeno){
        txtdate2.setText(expdate);
        txtno.setText(docno);
        txtps.setText(ps);
        txtrefer.setText(refer);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Recall_dept.this,android.R.layout.simple_spinner_dropdown_item,array_dept);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dept2.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(dept);
        dept2.setSelection(spinnerPosition);

        if(doctypeno.equals("1")) {
            spinner_doctypeno2.setSelection(1);
        }else if(doctypeno.equals("2")){
            spinner_doctypeno2.setSelection(2);
        }else{
            spinner_doctypeno2.setSelection(3);
        }
    }

    public void submitrecall(String DocNo) {
        class submitrecall extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            Toast.makeText(Recall_dept.this, "การยืนยันสำเร็จ", Toast.LENGTH_SHORT).show();
                            getDept(UserID);
                            array_set.clear();
                            list_itemset.setAdapter(new Aux4Adapter_Recall_dept( Recall_dept.this, array_set));
                        }else{
                            Toast.makeText(Recall_dept.this, "การยืนยันล้มเหลว", Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                String result = ruc.sendPostRequest(iFt.submit_document_recall(),data);
                Log.d("test", data+"");
                Log.d("test", result+"");
                return  result;
            }
        }

        submitrecall ru = new submitrecall();
        ru.execute(DocNo);
    }

    public void cancelitem(String UsageCode, String DocNo) {
        class cancelitem extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            Toast.makeText(Recall_dept.this, "ยกเลิกสำเร็จ", Toast.LENGTH_SHORT).show();
                            setdetail_itemset(txtno.getText().toString(),"true");
                        }else{
                            Toast.makeText(Recall_dept.this, "ยกเลิกล้มเหลว", Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("UsageCode",params[0]);
                data.put("DocNo",params[1]);
                String result = ruc.sendPostRequest(iFt.cancel_item(),data);
                Log.d("test", data+"");
                Log.d("test", result+"");
                return  result;
            }
        }

        cancelitem ru = new cancelitem();
        ru.execute(UsageCode,DocNo);
    }

    public String gettxtno(){
        return txtno.getText().toString();
    }

    public void cancelrecall(String DocNo) {
        class cancelrecall extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            Toast.makeText(Recall_dept.this, "ยกเลิกการยืนยันสำเร็จ", Toast.LENGTH_SHORT).show();
                            getDept(UserID);
                            array_set.clear();
                            list_itemset.setAdapter(new Aux4Adapter_Recall_dept( Recall_dept.this, array_set));
                        }else{
                            Toast.makeText(Recall_dept.this, "ยกเลิกการยืนยันล้มเหลว", Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                String result = ruc.sendPostRequest(iFt.cancel_document_recall(),data);
                Log.d("test", data+"");
                Log.d("test", result+"");
                return  result;
            }
        }

        cancelrecall ru = new cancelrecall();
        ru.execute(DocNo);
    }
}
