package com.phc.cssd.loaner;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.R;
import com.phc.cssd.loaner.adapter.ListDocAdapter;
import com.phc.cssd.loaner.adapter.ListSSDetailExpandbleAdapter;
import com.phc.cssd.loaner.model.LoanerDocument;
import com.phc.cssd.model.Item;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class LoanerMainActivity extends AppCompatActivity {
    String B_ID="1";

    HTTPConnect ruc = new HTTPConnect();
    private static final String TAG_RESULTS = "result";

    static final int DATE_PICKER_ID = 1111;
    private int year;
    private int month;
    private int day;

    ArrayList<LoanerDocument> arrDoc = new ArrayList<>();
    ArrayList<Item> expandableListTitle = new ArrayList<>();
    HashMap<String, ArrayList<String>> expandableListDetail = new HashMap<String, ArrayList<String>>();

    private EditText editSupplier;
    private EditText editUserSend;
    private EditText editSendTime;
    private EditText editSendDate;
    private Button editNewDate;
    private Button editNewDateTime;
    private RadioButton radioUsed;
    private RadioButton radioNoUsed;
    private Button editDateDocSearch;
    private EditText editDocSearch;
    private Button btDocSearch;
    private ListView listDocument;
    private ExpandableListView ListItem;

    private Button btSave;
    private Button bt_cancel;
    private Button btSaveReDate;
    private TextView textViewDocNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaner_main);
        getSupportActionBar().hide();

        Bundle bd = getIntent().getExtras();
        if (bd != null){
            B_ID = bd.getString("B_ID");
        }
        textViewDocNo = (TextView) findViewById(R.id.textViewDocNo);

        editSupplier = (EditText) findViewById(R.id.editSupplier);
        editUserSend = (EditText) findViewById(R.id.editUserSend);
        editSendTime = (EditText) findViewById(R.id.editSendTime);
        editSendDate = (EditText) findViewById(R.id.editSendDate);

        editDocSearch = (EditText) findViewById(R.id.editDocSearch);

        editNewDate = (Button) findViewById(R.id.editNewDate);
        editNewDateTime = (Button) findViewById(R.id.editNewDateTime);

        editDateDocSearch = (Button) findViewById(R.id.editDateDocSearch);
        btDocSearch = (Button) findViewById(R.id.btDocSearch);

        radioUsed = (RadioButton) findViewById(R.id.radioUsed);
        radioNoUsed = (RadioButton) findViewById(R.id.radioNoUsed);

        listDocument = (ListView) findViewById(R.id.listDocument);
        ListItem = (ExpandableListView) findViewById(R.id.ListItem);

        btSave = (Button) findViewById(R.id.btSave);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        btSaveReDate = (Button) findViewById(R.id.btSaveReDate);

        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        editDateDocSearch.setText(inrToTwoChar(day+"") + "/" + inrToTwoChar((month + 1)+"") + "/" + year);

        editNewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(LoanerMainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

                                editNewDate.setText(inrToTwoChar(dayOfMonth+"") + "/" + inrToTwoChar((monthOfYear + 1)+"") + "/" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        editDateDocSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(LoanerMainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

                                editDateDocSearch.setText(inrToTwoChar(dayOfMonth+"") + "/" + inrToTwoChar((monthOfYear + 1)+"") + "/" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        editNewDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(LoanerMainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,int minute) {

                                editNewDateTime.setText(inrToTwoChar(hourOfDay+"") + ":" + inrToTwoChar(minute+""));
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        btDocSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_Doc(editDocSearch.getText().toString(),editDateDocSearch.getText().toString());
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textViewDocNo.getText().toString().equals("")){
                    Toast.makeText(LoanerMainActivity.this,"ยังไม่ได้เลือกเอกสาร",Toast.LENGTH_SHORT).show();
                }else{
                    save_doc(textViewDocNo.getText().toString());
                }
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewDocNo.getText().toString().equals("")){
                    Toast.makeText(LoanerMainActivity.this,"ยังไม่ได้เลือกเอกสาร",Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(LoanerMainActivity.this);
                    alert.setTitle("หมายเหตุ");
                    final EditText input = new EditText(LoanerMainActivity.this);
//                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
//                    input.setRawInputType(Configuration.KEYBOARD_12KEY);
                    alert.setView(input);
                    alert.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if(!input.getText().toString().equals("")){
                                cancel_doc(textViewDocNo.getText().toString(),input.getText().toString());
                            }else{
                                Toast.makeText(LoanerMainActivity.this,"กรุณาใส่หมายเหตุ",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    alert.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //Put actions for CANCEL button here, or leave in blank
                        }
                    });
                    alert.show();
                }
            }
        });

        btSaveReDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewDocNo.getText().toString().equals("")){
                    Toast.makeText(LoanerMainActivity.this,"ยังไม่ได้เลือกเอกสาร",Toast.LENGTH_SHORT).show();
                }else if(editNewDate.getText().toString().equals("") || editNewDateTime.getText().toString().equals("")){
                    Toast.makeText(LoanerMainActivity.this,"กรุณาระบุวันและเวลานัดใหม่",Toast.LENGTH_SHORT).show();
                }else{
                    save_newdate_doc_loaner(textViewDocNo.getText().toString(),editNewDate.getText().toString(),editNewDateTime.getText().toString()+ ":00");
                }
            }
        });

        get_Doc(editDocSearch.getText().toString(),editDateDocSearch.getText().toString());
    }

    public void get_Doc(String key,String sDate) {
        class get_Doc extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                try {
                    arrDoc.clear();
                    JSONObject jsonObj = new JSONObject(s);
                    JSONArray setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        LoanerDocument newsData = new LoanerDocument();
                        newsData.setDocNo(c.getString("DocNo"));
                        newsData.setDocDate(c.getString("DocDate"));
                        newsData.setSupplierID(c.getString("SupplierID"));
                        newsData.setSupplierName(c.getString("SupplierName"));
                        newsData.setSendUser(c.getString("UserSend"));
                        newsData.setIsUsed(c.getInt("IsUsed"));
                        newsData.setSendDate(c.getString("SentDate"));
                        newsData.setSendTime(c.getString("SendTime"));
                        newsData.setIsStatus(c.getString("IsStatus"));
                        newsData.setAppointmentDate(c.getString("AppointmentDate"));
                        newsData.setAppointmentTime(c.getString("AppointmentTime"));
                        arrDoc.add(newsData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listDocument.setAdapter(new ListDocAdapter(LoanerMainActivity.this,arrDoc));
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("key",params[0]);
                data.put("Doc_date",params[1]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(getUrl.xUrl+"Loaner/getlistdoc_loaner.php",data);
                Log.d("ttest_doc", data+"");
                Log.d("ttest_doc", result);
                return  result;
            }
        }
        get_Doc ru = new get_Doc();
        ru.execute( key ,sDate);
    }

    public void showDoc(LoanerDocument doc){
        textViewDocNo.setText(doc.getDocNo());
        editSupplier.setText(doc.getSupplierName());
        editUserSend.setText(doc.getSendUser());
        editSendDate.setText(setDateFormat(doc.getSendDate()));
        editSendTime.setText(doc.getSendTime().substring(0,5));

        if(!doc.getAppointmentDate().equals("null")){
            editNewDate.setText(setDateFormat(doc.getAppointmentDate()));
            editNewDateTime.setText(doc.getAppointmentTime().substring(0,5));
        }else {
            editNewDate.setText("");
            editNewDateTime.setText("");
        }

        if(doc.getIsUsed()==0){
            radioNoUsed.setChecked(true);
        }else{
            radioUsed.setChecked(true);
        }

        int isStatus = Integer.parseInt(doc.getIsStatus());

        if(isStatus>1){
            editNewDate.setEnabled(false);
            editNewDateTime.setEnabled(false);
            bt_cancel.setEnabled(false);
        }else{
            editNewDate.setEnabled(true);
            editNewDateTime.setEnabled(true);
            bt_cancel.setEnabled(true);
        }

        get_Doc_Detail(doc.getDocNo());
    }

    public void get_Doc_Detail(String DocNo) {
        class get_Doc extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                expandableListTitle.clear();
                expandableListDetail.clear();
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    JSONArray rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for (int i = 0; i < rs.length(); i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if(expandableListDetail.get(c.getString("itemcode"))==null){
                            Item doc_detail = new Item();
                            doc_detail.setName(c.getString("itemname"));
                            doc_detail.setItemCode(c.getString("itemcode"));

                            expandableListTitle.add(doc_detail);

                            ArrayList<String> detail = new ArrayList<>();
                            detail.add(c.getString("UsageCode"));
                            expandableListDetail.put(c.getString("itemcode"),detail);
                            Log.d("ttestDetailexpan",detail+"");
                        }else{
                            ArrayList<String> detail = expandableListDetail.get(c.getString("itemcode"));
                            detail.add(c.getString("UsageCode"));
                            expandableListDetail.put(c.getString("itemcode"),detail);
                            Log.d("ttestDetailexpan",detail+"");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ListSSDetailExpandbleAdapter ListSSDetailExpandbleAdapterOb = new ListSSDetailExpandbleAdapter(LoanerMainActivity.this, expandableListTitle, expandableListDetail,textViewDocNo.getText().toString());
                ListItem.setAdapter(ListSSDetailExpandbleAdapterOb);
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                String result = ruc.sendPostRequest(getUrl.xUrl+"Loaner/getdetail_loaner.php",data);
                Log.d("ttest_detail", data+"");
                Log.d("ttest_detail", result);
                return  result;
            }
        }
        get_Doc ru = new get_Doc();
        ru.execute( DocNo);
    }

    public String inrToTwoChar(String x){
        if(x.length()<2){
            x = "0"+x;
        }
        return x;
    }

    public String setDateFormat(String x){
        String[] date = x.split("-");

        return date[2]+"/"+date[1]+"/"+date[0];
    }

    public void save_doc(String DocNo) {
        class save_doc extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(LoanerMainActivity.this,"บันทึกสำเร็จ",Toast.LENGTH_SHORT).show();
                get_Doc(editDocSearch.getText().toString(),editDateDocSearch.getText().toString());
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                String result = ruc.sendPostRequest(getUrl.xUrl+"Loaner/save_doc_loaner.php",data);
                Log.d("ttest_save_doc", data+"");
                Log.d("ttest_save_doc", result);
                return  result;
            }
        }
        save_doc ru = new save_doc();
        ru.execute( DocNo);
    }

    public void cancel_doc(String DocNo,String remark) {
        class cancel_doc extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(LoanerMainActivity.this,"ยกเลิกเอกสารสำเร็จ",Toast.LENGTH_SHORT).show();
                get_Doc(editDocSearch.getText().toString(),editDateDocSearch.getText().toString());
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                data.put("remark",params[1]);
                String result = ruc.sendPostRequest(getUrl.xUrl+"Loaner/cancel_doc_loaner.php",data);
                Log.d("ttest_save_doc", data+"");
                Log.d("ttest_save_doc", result);
                return  result;
            }
        }
        cancel_doc ru = new cancel_doc();
        ru.execute(DocNo,remark);
    }

    public void save_newdate_doc_loaner(String DocNo,String date,String Time) {
        String[] arrDate = date.split("/");
        String xDate = arrDate[2]+"-"+arrDate[1]+"-"+arrDate[0];
        class save_newdate_doc_loaner extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(LoanerMainActivity.this,"บันทึกสำเร็จ",Toast.LENGTH_SHORT).show();
                get_Doc(editDocSearch.getText().toString(),editDateDocSearch.getText().toString());
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                data.put("AppointmentDate",params[1]);
                data.put("AppointmentTime",params[2]);
                String result = ruc.sendPostRequest(getUrl.xUrl+"Loaner/save_newdate_doc_loaner.php",data);
                Log.d("ttest_save_newdate", data+"");
                Log.d("ttest_save_newdate", result);
                return  result;
            }
        }
        save_newdate_doc_loaner ru = new save_newdate_doc_loaner();
        ru.execute( DocNo,xDate,Time);
    }

    public void delete_detail(String DocNo,String usagecode) {
        class delete_detail extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(LoanerMainActivity.this,"ลบรายการสำเร็จ",Toast.LENGTH_SHORT).show();
                get_Doc_Detail(textViewDocNo.getText().toString());
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                data.put("usagecode",params[1]);
                String result = ruc.sendPostRequest(getUrl.xUrl+"Loaner/delete_detail_loaner.php",data);
                Log.d("ttest_delete", data+"");
                Log.d("ttest_delete", result);
                return  result;
            }
        }
        delete_detail ru = new delete_detail();
        ru.execute(DocNo,usagecode);
    }

}
