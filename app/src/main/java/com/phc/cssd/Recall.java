package com.phc.cssd;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.Aux4Adapter_Recall;
import com.phc.cssd.adapter.Aux6Adapter_Recall;
import com.phc.cssd.adapter.AuxAdapter_Recall;
import com.phc.cssd.bean.DirRecall;
import com.phc.cssd.bean.FileRecall;
import com.phc.cssd.function.KeyboardUtils;
import com.phc.cssd.properties.Response_Aux_Recall;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.viewbinder.DirectoryNodeBinder_Recall;
import com.phc.cssd.viewbinder.FileNodeBinder_Recall;
import com.stargreatsoftware.sgtreeview.TreeNode;
import com.stargreatsoftware.sgtreeview.TreeViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class Recall extends AppCompatActivity {

    HashMap<String, String> DelAlldata = new HashMap<String,String>();
    public ArrayList<String> DepId = new ArrayList<String>();
    private JSONArray rs = null;
    ArrayList<String> array_doctypeno = new ArrayList<String>();
    ArrayList<String> array_dept = new ArrayList<String>();
    ArrayList<String> array_itemname = new ArrayList<String>();
    ArrayList<Response_Aux_Recall> array_itemstock = new ArrayList<Response_Aux_Recall>();
    ArrayList<Response_Aux_Recall> array_recall = new ArrayList<Response_Aux_Recall>();
    ArrayList<Response_Aux_Recall> array_set = new ArrayList<Response_Aux_Recall>();
    ArrayList<Response_Aux_Recall> array_risk = new ArrayList<Response_Aux_Recall>();
    TreeViewAdapter adapter;
    Spinner spinner_doctypeno;
    Spinner dept1;
    Spinner dept2;
    Spinner spinner_doctypeno2;
    EditText datetopic;
    EditText txtdate1;
    EditText txtdate2;
    EditText txtno;
    EditText txtps;
    EditText txtrefer;
    Button button_search1;
    Button button_search2;
    Button button_create;
    Button button_clear;
    Button button_save;
    Button button_print;
    Button itemname1;
    Intent intent;
    ListView list_itemstock;
    ListView list_document;
    ListView list_itemset;
    LinearLayout layout_item;
    RecyclerView Rv;
    Calendar myCalendar = Calendar.getInstance();
    String bo = null;
    String Stringdept;
    String Docnotype;
    String UserID;
    String TAG_RESULTS="result";
    String B_ID = null;
    String IsPay = null;
    String IsStatus = null;
    String UsageCode = null;
    String OccDocNo = "";
    String DocNoTypeDoc = "";
    String DocNo = "";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    ImageView backbtn;
    ImageView imgsave;
    ImageView imagerecalled;
    DirectoryNodeBinder_Recall dirr;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recall);
        getSupportActionBar().hide();
        getBundleuser();
        initialize();
        KeyboardUtils.hideKeyboard(Recall.this);
    }

    private void getBundleuser() {
        intent = getIntent();
        Bundle bd = getIntent().getExtras();
        try{
            if(intent.hasExtra("userid")) {
                UserID = bd.getString("userid");
            }
        }catch (NullPointerException e){
            if(!intent.hasExtra("userid")) {
                UserID = "";
            }
        }
        B_ID = intent.getStringExtra("B_ID");
    }

    private void initialize() {
        dirr = new DirectoryNodeBinder_Recall(Recall.this);
        backbtn = (ImageView) findViewById(R.id.imageBack);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Recall.this, Menu.class);
                //startActivity(intent);
                finish();
            }
        });

        datetopic = (EditText) findViewById(R.id.datetopic);
        getsdate(datetopic);
        list_itemstock = (ListView) findViewById(R.id.list_itemstock);
        list_document  = (ListView) findViewById(R.id.list_document);
        list_itemset  = (ListView) findViewById(R.id.list_itemset);
        layout_item =  (LinearLayout) findViewById(R.id.Itemlayout);
        layout_item.setVisibility(View.GONE);
        spinner_doctypeno = (Spinner) findViewById(R.id.spinner_doctypeno);
        spinner_doctypeno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                array_itemstock.clear();
                list_itemstock.setAdapter(new AuxAdapter_Recall( Recall.this, array_itemstock));
                String txt = spinner_doctypeno.getSelectedItem().toString();
                if(!txt.equals("-")) {
                    String[] type = txt.split("\\.");
                    TextView label = (TextView) findViewById(R.id.textView7);
                    if(type[0].equals("2")){
                        layout_item.setVisibility(View.VISIBLE);
                        button_search1.setVisibility(View.GONE);
                        itemname1.setText("กดเพื่อค้นหาอุปกรณ์");
                    }else{
                        layout_item.setVisibility(View.GONE);
                        button_search1.setVisibility(View.VISIBLE);
                        itemname1.setText("");
                    }
                }else{
                    layout_item.setVisibility(View.GONE);
                    button_search1.setVisibility(View.VISIBLE);
                    itemname1.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_doctypeno2 = (Spinner) findViewById(R.id.spinner_doctypeno2);
        itemname1 =  (Button) findViewById(R.id.itemname1);
        itemname1.setText("กดเพื่อค้นหาอุปกรณ์");
        itemname1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = spinner_doctypeno.getSelectedItem().toString();
                if(!txt.equals("-")) {
                    String[] type = txt.split("\\.");
                    Docnotype = type[0];
                    if(type[0].equals("2")){
                        Intent i = new Intent(Recall.this, dialog_search_itemname_risk.class);
                        i.putExtra("type", type[0]);
                        i.putExtra("B_ID",B_ID);
                        startActivityForResult(i, 112);
                    }else{
                        Intent i = new Intent(Recall.this, dialog_search_itemname.class);
                        i.putExtra("type", type[0]);
                        i.putExtra("B_ID",B_ID);
                        startActivityForResult(i, 111);
                    }

                }else{
                    Toast.makeText(Recall.this, "กรุณาเลือกประเภทการเรียกคืน", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dept1 = (Spinner) findViewById(R.id.dept1);
        dept1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //button_create.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dept2 = (Spinner) findViewById(R.id.dept2);
        doctypeno_define();
        SpinnerDept();
        Rv = (RecyclerView) findViewById(R.id.Rv);
        txtdate1 = (EditText) findViewById(R.id.txtdate1);
        txtdate1.setInputType(InputType.TYPE_NULL);
        getsdate(txtdate1);
        txtdate2 = (EditText) findViewById(R.id.txtdate2);
        txtdate2.setInputType(InputType.TYPE_NULL);
        getsdate(txtdate2);
        txtno = (EditText) findViewById(R.id.txtno);
        txtps = (EditText) findViewById(R.id.txtps);
        txtrefer = (EditText) findViewById(R.id.txtrefer);
        button_search1 = (Button) findViewById(R.id.button_search1);
        button_search2 = (Button) findViewById(R.id.button_search2);
        button_create = (Button) findViewById(R.id.button_create);
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
                list_document.setAdapter(new Aux6Adapter_Recall( Recall.this, array_recall));
                list_itemset.setAdapter(new Aux4Adapter_Recall( Recall.this, array_set));
                togglesave(false);
            }
        });
        button_save = (Button) findViewById(R.id.button_save);
        button_save.setEnabled(false);
        button_print = (Button) findViewById(R.id.button_print);

        button_search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!spinner_doctypeno.getSelectedItem().toString().equals("-") &&
                        !txtdate1.getText().toString().equals("")) {
                    String txt = spinner_doctypeno.getSelectedItem().toString();
                    String[] type = txt.split("\\.");
                    if(!type[0].equals("2")){
                        CheckDocType();
                    }else{
                        Toast.makeText(Recall.this, "กรุณาลือกข้อมูลเงื่อนไข", Toast.LENGTH_SHORT).show();
                        button_create.setEnabled(false);
                    }
                }else{
                    Toast.makeText(Recall.this, "กรุณาลือกข้อมูลเงื่อนไข", Toast.LENGTH_SHORT).show();
                    button_create.setEnabled(false);
                }
            }
        });
        button_search2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtdate2.getText().toString().equals("") &&
                        !spinner_doctypeno2.getSelectedItem().equals("-")){
                    listrecall(txtdate2.getText().toString(),txtno.getText().toString(),dept2.getSelectedItem().toString(),spinner_doctypeno2.getSelectedItem().toString(),txtrefer.getText().toString());
                }else{
                    Toast.makeText(Recall.this, "กรุณาเลือกวันที่และประเภทเอกสาร", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object o = list_itemstock.getItemAtPosition(position);
                Response_Aux_Recall newsData = ( Response_Aux_Recall ) o;
                IsPay = newsData.getxFields13();
                IsStatus = newsData.getxFields11();
                if (!IsStatus.equals("3:1")) {
                    if (!spinner_doctypeno.getSelectedItem().toString().equals("-") && !txtdate1.getText().toString().equals("")) {
                        if (!Stringdept.equals("")) {
                            String txt = spinner_doctypeno.getSelectedItem().toString();
                            String[] type = txt.split("\\.");
                            if (!type[0].equals("2")) {
                                createdoc_recall(UserID, dept1.getSelectedItem().toString(), spinner_doctypeno.getSelectedItem().toString(), Stringdept, getItemstockstring(), type[0]);
                                button_create.setEnabled(false);
                                getsdate(txtdate1);
                                spinner_doctypeno.setSelection(0);
                                itemname1.setText("");
                                dept1.setSelection(0);
                                array_itemstock.clear();
                                list_itemstock.setAdapter(new AuxAdapter_Recall(Recall.this, array_itemstock));
                            } else {
                                if (!itemname1.equals("")) {
                                    createdoc_recall1(UserID, dept1.getSelectedItem().toString(), spinner_doctypeno.getSelectedItem().toString(), Stringdept, getItemstockstring(), type[0]);
                                    button_create.setEnabled(false);
                                    getsdate(txtdate1);
                                    spinner_doctypeno.setSelection(0);
                                    itemname1.setText("");
                                    dept1.setSelection(0);
                                    array_itemstock.clear();
                                    list_itemstock.setAdapter(new AuxAdapter_Recall(Recall.this, array_itemstock));
                                } else {
                                    Toast.makeText(Recall.this, "กรุณาเลือกอุปกรณ์", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Log.d("Error", "Error-search/createbutton");
                        }
                    } else {
                        Toast.makeText(Recall.this, "กรุณาลือกข้อมูลเงื่อนไข", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Recall.this, "มีรายการถูกจองในเอกสารเบิก", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_doc_recall(txtdate2.getText().toString(),txtno.getText().toString(),dept2.getSelectedItem().toString(),spinner_doctypeno2.getSelectedItem().toString(),txtrefer.getText().toString(),txtps.getText().toString());
            }
        });
        button_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgsave = (ImageView) findViewById(R.id.imagesave);
        imgsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Recall.this);
                builder.setCancelable(true);
                builder.setTitle("ยืนยันการส่งเอกสารเรียกคืนอุปกรณ์");
                builder.setMessage("คุณต้องการส่งเอกสารเรียกคืนอุปกรณ์หรือไม่ ?");
                builder.setPositiveButton("ตกลง",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updatestatus_recall();
                            }
                        });
                builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                android.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        imagerecalled = (ImageView) findViewById(R.id.imagerecalled);
        imagerecalled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Recall.this, dialog_search_recall.class);
                i.putExtra("B_ID",B_ID);
                startActivityForResult(i, 554);
            }
        });
        get_datatree_recall();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (data != null) {
                if(data.getStringExtra("StrData")!=null) {
                    if(data.getStringExtra("type").equals("0")){
                        String RETURN_DATA = data.getStringExtra("StrData");
                        if (!RETURN_DATA.equals("")) {
                            itemname1.setText(RETURN_DATA);
                        }
                    }else{
                        String RETURN_DATA = data.getStringExtra("StrData");
                        if (!RETURN_DATA.equals("")) {
                            itemname1.setText("Set-item");
                            DocNo = data.getStringExtra("Docno");
                            OccDocNo = data.getStringExtra("OccDocno");
                            list_risk(RETURN_DATA);
                            button_create.setEnabled(true);
                        }
                    }

                }
            }else{
            }
        }catch(Exception e){
            e.printStackTrace();
            return;
        }
    }



    public void doctypeno_define() {
        array_doctypeno.add("-");
//        array_doctypeno.add("1.เรียกคืนของใกล้หมดอายุ");
        array_doctypeno.add("1.เรียกคืนของหมดอายุ");
        array_doctypeno.add("2.ความเสี่ยง");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Recall.this,android.R.layout.simple_spinner_dropdown_item,array_doctypeno);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_doctypeno.setAdapter(adapter);
        spinner_doctypeno2.setAdapter(adapter);
    }

    public String getItemstockstring(){
        String itemstring ="";
        int sizearray = array_itemstock.size();
        for (int i=0;i<sizearray;i++){
            if(i==0){
                itemstring = itemstring + array_itemstock.get(i).getFields1();
            }else {
                itemstring = itemstring + "," + array_itemstock.get(i).getFields1();
            }
        }
        itemstring.substring(0,itemstring.length());
        return itemstring.substring(0,itemstring.length());
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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Recall.this,android.R.layout.simple_spinner_dropdown_item,array_dept);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dept1.setAdapter(adapter);
                    dept2.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Pass","");
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.get_department_spinner(),data);
                return  result;
            }
        }

        SpinnerDept ru = new SpinnerDept();
        ru.execute();
    }

    public void Spinneritem() {
        class Spinneritem extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    array_itemname.clear();
                    array_itemname.add("-");
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        itemname1.setText(c.getString("xItemcode")+":"+c.getString("xItemname"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Pass","");
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.get_itemname_spinner(),data);
                return  result;
            }
        }

        Spinneritem ru = new Spinneritem();
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
                new DatePickerDialog(Recall.this, date, myCalendar
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

    public void list_risk(String UsageCode) {
        class list_risk extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Aux_Recall newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    int type = 1;
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    array_itemstock.clear();
                    for(int i=0;i<setRs.length();i++) {
                        newsData = new Response_Aux_Recall();
                        JSONObject c = setRs.getJSONObject(i);
                        if (c.getString("bool").equals("true")){
                            newsData.setFields1(c.getString("UsageCode"));
                            newsData.setFields2(c.getString("itemname"));
                            newsData.setFields3(c.getString("ExpireDate"));
                            newsData.setFields4(c.getString("DepName2"));
                            newsData.setxFields11(c.getString("IsStatus"));
                            newsData.setxFields13(c.getString("IsPay"));
                            if (!c.getString("Stringdept").toString().equals("")) {
                                Stringdept = c.getString("Stringdept");
                            } else {
                                Stringdept = "";
                            }
                            array_itemstock.add(newsData);
                        }else{
                            Toast.makeText(Recall.this, "ไม่พบข้อมูล", Toast.LENGTH_SHORT).show();
                        }
                    }
                    list_itemstock.setAdapter(new AuxAdapter_Recall( Recall.this, array_itemstock));
                    list_itemstock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Object o = list_itemstock.getItemAtPosition(position);
                            Response_Aux_Recall newsData = ( Response_Aux_Recall ) o;
                            IsStatus = newsData.getxFields11();
                            if (IsStatus.equals("3:1")) {
                                Intent intent = new Intent(Recall.this, dialog_itemstock_cssd_recall.class);
                                intent.putExtra("UsageCode",newsData.getFields1());
                                startActivity(intent);
                            }else {
                                onStop();
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("UsageCode",params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.get_list_itemstock_risk(),data);
                Log.d("test", data+"");
                Log.d("test", result+"");
                return  result;
            }
        }

        list_risk ru = new list_risk();
        ru.execute(UsageCode);
    }

    public void listitemstock(String expdate,String ItemCode,String Dept,String Doctypeno) {
        class listitemstock extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Aux_Recall newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    int type = 0;
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    array_itemstock.clear();
                    for(int i=0;i<setRs.length();i++) {
                        newsData = new Response_Aux_Recall();
                        JSONObject c = setRs.getJSONObject(i);
                        if (c.getString("bool").equals("true")){
                            newsData.setFields1(c.getString("UsageCode"));
                            newsData.setFields2(c.getString("itemname"));
                            newsData.setFields3(c.getString("ExpireDate"));
                            newsData.setFields4(c.getString("DepName2"));
                            newsData.setxFields11(c.getString("IsStatus"));
                            newsData.setxFields13(c.getString("IsPay"));
                            if (!c.getString("Stringdept").equals("")) {
                                Stringdept = c.getString("Stringdept");
                            } else {
                                Stringdept = "";
                            }
                            array_itemstock.add(newsData);
                        }else{
                            Toast.makeText(Recall.this, "ไม่พบข้อมูล", Toast.LENGTH_SHORT).show();
                        }
                    }
                    list_itemstock.setAdapter(new AuxAdapter_Recall( Recall.this, array_itemstock));
                    list_itemstock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Object o = list_itemstock.getItemAtPosition(position);
                            Response_Aux_Recall newsData = ( Response_Aux_Recall ) o;
                            IsStatus = newsData.getxFields11();
                            if (IsStatus.equals("3:1")) {
                                Intent intent = new Intent(Recall.this, dialog_itemstock_cssd_recall.class);
                                intent.putExtra("UsageCode",newsData.getFields1());
                                startActivity(intent);
                            }else {
                                onStop();
                            }
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("expdate",params[0]);
                data.put("ItemCode",params[1]);
                data.put("Dept",params[2]);
                data.put("Doctypeno",params[3]);
                data.put("DocNo",DocNo);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.get_list_itemstock(),data);
                Log.d("BANK",data+"");
                Log.d("BANK",result);
                Log.d("BANK",iFt.get_list_itemstock());
                return  result;
            }
        }

        listitemstock ru = new listitemstock();
        ru.execute(expdate,ItemCode,Dept,Doctypeno);
    }

    public void listrecall(String expdate,String Docno,String Dept,String Doctypeno,String docrefer) {
        class listrecall extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Aux_Recall newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    array_recall.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux_Recall();
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
                            array_recall.add(newsData);
                        }else{
                            Toast.makeText(Recall.this, "ไม่พบข้อมูล", Toast.LENGTH_SHORT).show();
                        }
                    }
                    list_document.setAdapter(new Aux6Adapter_Recall( Recall.this, array_recall));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("expdate",params[0]);
                data.put("Docno",params[1]);
                data.put("Dept",params[2]);
                data.put("Doctypeno",params[3]);
                data.put("Docrefer",params[4]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.get_list_recall(),data);
                return  result;
            }
        }

        listrecall ru = new listrecall();
        ru.execute(expdate,Docno,Dept,Doctypeno,docrefer);
    }

    public void update_doc_recall(final String expdate, final String Docno,final String Dept, final String Doctypeno, final String docrefer, String remark) {
        class update_doc_recall extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    array_recall.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            Toast.makeText(Recall.this, "บันทึกข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
                            listrecall(expdate,Docno,Dept,Doctypeno,docrefer);
                        }else{
                            Toast.makeText(Recall.this, "บันทึกข้อมูลล้มเหลว", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("expdate",params[0]);
                data.put("Docno",params[1]);
                data.put("Dept",params[2]);
                data.put("Doctypeno",params[3]);
                data.put("Docrefer",params[4]);
                data.put("Remark",params[5]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.updatedoc_recall(),data);
                return  result;
            }
        }

        update_doc_recall ru = new update_doc_recall();
        ru.execute(expdate,Docno,Dept,Doctypeno,docrefer,remark);
    }

    public void createdoc_recall(String UserID, String Dept, final String Doctypeno, final String stringdept, String itemstocklist, final String type) {
        class createdoc_recall extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Aux_Recall newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    array_recall.clear();
                    String Docstr1 = "";
                    String Docstr2 = "";
                    String Docstr3 = "";
                    String Docstr4 = "";
                    String Docstr5 = "";
                    String Docstr6 = "";
                    String Docstr7 = "";
                    Boolean flag = false;
                    List<List<String>> listOfLists = new ArrayList<List<String>>();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);

                        if(c.getString("bool").equals("true")){
                            Toast.makeText(Recall.this, "สร้างเอกสารสำเร็จ", Toast.LENGTH_SHORT).show();
                            get_datatree_recall();
                        }else{
                            Toast.makeText(Recall.this, "สร้างเอกสารล้มเหลว", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                String[] type = Doctypeno.split("\\.");
                data.put("UserID",params[0]);
                data.put("Dept",params[1]);
                data.put("Doctypeno",type[0]);
                data.put("Stringdept",params[3]);
                data.put("Itemstocklist",params[4]);
                data.put("DocNo",DocNo);
                data.put("B_ID",B_ID);
                data.put("OccDocNo",OccDocNo);
                String result = "";
                result = ruc.sendPostRequest(iFt.createdoc_recall(),data);
                Log.d("FDLMNF",data+"");
                Log.d("FDLMNF",result);
                return  result;
            }
        }

        createdoc_recall ru = new createdoc_recall();
        ru.execute(UserID,Dept,Doctypeno,stringdept,itemstocklist,type);
    }

    public void createdoc_recall1(String UserID, String Dept, final String Doctypeno, String stringdept, String itemstocklist, final String type) {
        class createdoc_recall1 extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Aux_Recall newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    array_recall.clear();
                    String Docstr1 = "";
                    String Docstr2 = "";
                    String Docstr3 = "";
                    String Docstr4 = "";
                    String Docstr5 = "";
                    String Docstr6 = "";
                    String Docstr7 = "";
                    Boolean flag = false;
                    List<List<String>> listOfLists = new ArrayList<List<String>>();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);

                        if(c.getString("bool").equals("true")){
                            Toast.makeText(Recall.this, "สร้างเอกสารสำเร็จ", Toast.LENGTH_SHORT).show();
                            get_datatree_recall();
                        }else{
                            Toast.makeText(Recall.this, "สร้างเอกสารล้มเหลว", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                String[] type = Doctypeno.split("\\.");
                data.put("UserID",params[0]);
                data.put("Dept",params[1]);
                data.put("Doctypeno",type[0]);
                data.put("Stringdept",params[3]);
                data.put("Itemstocklist",params[4]);
                data.put("DocNo",DocNo);
                data.put("B_ID",B_ID);
                data.put("OccDocNo",OccDocNo);
                String result = "";

                result = ruc.sendPostRequest(iFt.createdoc_recall_risk(),data);

                return  result;
            }
        }

        createdoc_recall1 ru = new createdoc_recall1();
        ru.execute(UserID,Dept,Doctypeno,stringdept,itemstocklist,type);
    }

    public void updatestatus_recall() {
        class updatestatus_recall extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Aux_Recall newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    array_set.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux_Recall();
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            Toast.makeText(Recall.this, "ส่งคำขอเรียกคืนอุปกรณ์สำเร็จ", Toast.LENGTH_SHORT).show();
                            get_datatree_recall();
                        }else{
                            Toast.makeText(Recall.this, "ส่งคำขอเรียกคืนอุปกรณ์ล้มเหลว", Toast.LENGTH_SHORT).show();
                        }
                    }
                    list_itemset.setAdapter(new Aux4Adapter_Recall( Recall.this, array_set));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo","");
                data.put("B_ID",B_ID);
                data.put("UserID",UserID);
                String result = ruc.sendPostRequest(iFt.updatestatus_recall(),data);
                Log.d("BANK",data+"");
                Log.d("BANK",result);
                return  result;
            }
        }

        updatestatus_recall ru = new updatestatus_recall();
        ru.execute();
    }

    public void get_datatree_recall() {
        class get_datatree_recall extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Aux_Recall newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    array_recall.clear();
                    String Docstr1 = "";
                    String Docstr2 = "";
                    String Docstr3 = "";
                    String Docstr4 = "";
                    String Docstr5 = "";
                    String Docstr6 = "";
                    String Docstr7 = "";
                    Boolean flag = false;
                    List<List<String>> listOfLists = new ArrayList<List<String>>();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")){
                            Docstr1 = c.getString("DocNo");
                            Docstr2 = c.getString("Dept");
                            Docstr3 = c.getString("Remark");
                            Docstr4 = c.getString("IsStatus");
                            Docstr5 = c.getString("RefDocNo");
                            Docstr6 = c.getString("Detail");
                            Docstr7 = c.getString("DocTypeNo");
                            flag = true;
                        }
                    }

                    if(flag==true) {
                        try {
                            String[] Doctypeno = Docstr7.split(",");
                            String[] Doctemp = Docstr1.split("//");
                            String[] Depttemp = Docstr2.split("//");
                            String[] Remarktemp = Docstr3.split("//");
                            String[] Reftemp = Docstr5.split("//");
                            String[] Statustemp = Docstr4.split("//");
                            String[] Itemtemp = Docstr6.split("//");

                            if(Doctypeno.length==1){
                                Itemtemp[0] = Itemtemp[0].substring(0, Itemtemp[0].length() - 1);
                            }else if(Doctypeno.length==2){
                                Itemtemp[0] = Itemtemp[0].substring(0, Itemtemp[0].length() - 1);
                                Itemtemp[1] = Itemtemp[1].substring(0, Itemtemp[1].length() - 1);
                            }else if(Doctypeno.length==3){
                                Itemtemp[0] = Itemtemp[0].substring(0, Itemtemp[0].length() - 1);
                                Itemtemp[1] = Itemtemp[1].substring(0, Itemtemp[1].length() - 1);
                                Itemtemp[2] = Itemtemp[2].substring(0, Itemtemp[2].length() - 1);
                            }

                            String Docstr10 ="";
                            if(Doctypeno.length==1){
                                Docstr10 = Itemtemp[0];
                            }else if(Doctypeno.length==2){
                                Docstr10 = Itemtemp[0] + "//" + Itemtemp[1];
                            }else if(Doctypeno.length==3){
                                Docstr10 = Itemtemp[0] + "//" + Itemtemp[1] + "//" + Itemtemp[2];
                            }

                            if(!Docstr1.toString().equals("null")){
                                initData(Docstr7,Docstr1,Docstr10,Docstr5,Docstr3,Docstr4,Docstr2);
                            }else{
                                Toast.makeText(Recall.this, "ไม่พบข้อมูลเอกสารของวันนี้", Toast.LENGTH_SHORT).show();
                            }
                        }catch (ArrayIndexOutOfBoundsException e){
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("UserID","");
                data.put("B_ID",B_ID);
                String result="";
                result = ruc.sendPostRequest(iFt.get_datatree_recall(),data);
                return  result;
            }
        }

        get_datatree_recall ru = new get_datatree_recall();
        ru.execute();
    }


    private void initData(String Data1,String Data2,String Data3,String Data4,String Data5,String Data6,String Data7) {
        List getData1 = new ArrayList<String>(Arrays.asList(Data1.split(",")));
        List getData2 = new ArrayList<String>(Arrays.asList(Data2.split("//")));
        List getData3 = new ArrayList<String>(Arrays.asList(Data3.split("//")));
        List getData4 = new ArrayList<String>(Arrays.asList(Data4.split("//")));
        List getData5 = new ArrayList<String>(Arrays.asList(Data5.split("//")));
        List getData6 = new ArrayList<String>(Arrays.asList(Data6.split("//")));
        List getData7 = new ArrayList<String>(Arrays.asList(Data7.split("//")));

        List<TreeNode> nodes = new ArrayList<>();
        TreeNode<DirRecall> app = new TreeNode<>(new DirRecall("เอกสารเรียกคืนทั้งหมด@@0"));
        app.expand();
        nodes.add(app);

        for (int i= 0;i<getData1.size();i++) {
            TreeNode treeNode = new TreeNode<>(new DirRecall( getData1.get(i).toString()+"@@0" ));
            List ListData = new ArrayList<String>(Arrays.asList(getData2.get(i).toString().split(",")));
            List ListData4 = new ArrayList<String>(Arrays.asList(getData4.get(i).toString().split(",")));
            List ListData5 = new ArrayList<String>(Arrays.asList(getData5.get(i).toString().split(",")));
            List ListData6 = new ArrayList<String>(Arrays.asList(getData6.get(i).toString().split(",")));
            List ListData7 = new ArrayList<String>(Arrays.asList(getData7.get(i).toString().split(",")));
            List ListData2 = new ArrayList<String>(Arrays.asList(getData3.get(i).toString().split("#")));
            for (int k = 0; k < ListData.size(); k++) {
                List ListData3 = new ArrayList<String>(Arrays.asList(ListData2.get(k).toString().split(",")));
                String State ="";
                if(ListData6.get(k).toString().equals("0")){
                    State = "ร่างเอกสาร";
                }else if(ListData6.get(k).toString().equals("1")){
                    State = "รอยืนยัน";
                }else if(ListData6.get(k).toString().equals("2")){
                    State = "ยืนยันแล้ว";
                }else if(ListData6.get(k).toString().equals("3")){
                    State = "ยกเลิก";
                }

                String ref = "";
                if(ListData4.get(k).toString().equals("%")){
                    ref = "";
                }else{
                    ref = "[REF:"+ListData4.get(k).toString()+"]";
                }
                TreeNode treeNode1 = new TreeNode(new DirRecall( "แผนก : "+ListData7.get(k).toString()+" ["+ListData.get(k).toString()+"]    "+ref+"\nสถานะ : "+State+"@@1@@"+ListData6.get(k).toString()+"@@"+ListData5.get(k).toString()+"@@"+ListData.get(k).toString() ));
                treeNode.addChild(treeNode1);
                for (int j=0;j<ListData3.size();j++){
                    TreeNode treeNode2 = new TreeNode(new FileRecall( ListData3.get(j).toString() ));
                    treeNode1.addChild(treeNode2);

                }
            }
            app.addChild( treeNode );
        }

        Rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TreeViewAdapter(nodes, Arrays.asList(new FileNodeBinder_Recall(), new DirectoryNodeBinder_Recall(Recall.this)));
        adapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
            @Override
            public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
                if (!node.isLeaf()) {
                    //Update and toggle the node.
                    onToggle(!node.isExpand(), holder);
                    if (!node.isExpand()) {
                        adapter.collapseBrotherNode(node);
                        int xPosition = holder.getAdapterPosition();
                    }
                }
                return false;
            }

            @Override
            public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {
                DirectoryNodeBinder_Recall.ViewHolder dirViewHolder = (DirectoryNodeBinder_Recall.ViewHolder) holder;
                final ImageView ivArrow = dirViewHolder.getIvArrow();
                int rotateDegree = isExpand ? 90 : -90;
                ivArrow.animate().rotationBy(rotateDegree)
                        .start();
            }
        });
        Rv.setAdapter(adapter);
    }


    public void setdetail_itemset(String DocNo) {
        class setdetail_itemset extends AsyncTask<String, Void, String> {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Aux_Recall newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    array_set.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux_Recall();
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            newsData.setFields1(c.getString("No"));
                            newsData.setFields2(c.getString("Itemname"));
                            newsData.setFields3(c.getString("Usagecode"));
                            newsData.setFields4(c.getString("Expiredate"));
                            newsData.setFields5(c.getString("DocNo"));
                            newsData.setFields6(c.getString("Dept"));
                            array_set.add(newsData);
                        }
                    }
                    list_itemset.setAdapter(new Aux4Adapter_Recall( Recall.this, array_set));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.set_detail_itemset(),data);
                return  result;
            }
        }
        setdetail_itemset ru = new setdetail_itemset();
        ru.execute(DocNo);
    }

    public void setclick_Doc(String docno,String expdate,String dept,String refer,String ps,String doctypeno){
        txtdate2.setText(expdate);
        txtno.setText(docno);
        txtps.setText(ps);
        txtrefer.setText(refer);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Recall.this,android.R.layout.simple_spinner_dropdown_item,array_dept);
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

    public void togglesave(boolean bool){
        button_save.setEnabled(bool);
    }

    public void opendialog(String Dept){
        Intent i = new Intent(Recall.this, dialog_recall.class);
        i.putExtra("UserID",UserID);
        i.putExtra("Dept",Dept);
        i.putExtra("B_ID",B_ID);
        startActivityForResult(i,101);
    }

    public void deleterecall(String DocNo) {
        class deleterecall extends AsyncTask<String, Void, String> {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            Toast.makeText(Recall.this, "ยกเลิกเอกสารสำเร็จ", Toast.LENGTH_SHORT).show();
                            get_datatree_recall();
                        }else{
                            Toast.makeText(Recall.this, "ยกเลิกเอกสารล้มเหลว", Toast.LENGTH_SHORT).show();
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
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.delete_document_recall(),data);
                return  result;
            }
        }
        deleterecall ru = new deleterecall();
        ru.execute(DocNo);
    }

    public void remark_recall(String DocNo,String Remark) {
        class remark_recall extends AsyncTask<String, Void, String> {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            Toast.makeText(Recall.this, "บันทึกหมายเหตุสำเร็จ", Toast.LENGTH_SHORT).show();
                            //get_datatree_recall();
                        }else{
                            Toast.makeText(Recall.this, "บันทึกหมายเหตุล้มเหลว", Toast.LENGTH_SHORT).show();
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
                data.put("Remark",params[1]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.remark_recall(),data);
                Log.d("FNGDDF",data+"");
                Log.d("FNGDDF",result);
                return  result;
            }
        }
        remark_recall ru = new remark_recall();
        ru.execute(DocNo,Remark);
    }

    public void CheckDocType() {
        class CheckDocType extends AsyncTask<String, Void, String> {
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
                        DocNoTypeDoc = c.getString("DocTypeNo");
                        if (DocNoTypeDoc.equals("0")){
                            listitemstock(txtdate1.getText().toString(), itemname1.getText().toString(), dept1.getSelectedItem().toString(),spinner_doctypeno.getSelectedItem().toString());
                            button_create.setEnabled(true);
                        }else {
                            Toast.makeText(Recall.this, "เอกสารถูกสร้างก่อนหน้านี้แล้ว", Toast.LENGTH_SHORT).show();
                        }
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
                    result = ruc.sendPostRequest(Url.URL + "Recall/cssd_display_doctype_recall.php", data);
                    Log.d("FNGDDF",result);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        CheckDocType obj = new CheckDocType();
        obj.execute();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
