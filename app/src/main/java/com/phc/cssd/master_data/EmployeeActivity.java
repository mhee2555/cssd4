package com.phc.cssd.master_data;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.Aux5Adapter;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeActivity extends AppCompatActivity {
    ArrayList<Response_Aux> results = new ArrayList<Response_Aux>();
    ArrayList<String> arrdepartment = new ArrayList<String>();
    private static final String TAG_RESULTS="result";
    JSONArray setRs = null;
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    Boolean chkUpdate = false;

    TextView tID;
    EditText tEmp;
    EditText tFname;
    EditText tLname;
    Spinner tDepId;
    EditText tSearch;
    ImageView toggle;
    Button switchview;
    Button switchview2;
    LinearLayout Li1;
    LinearLayout Li2;

    Button btAdd;
    Button btSearch;
    Button btDel;
    Button btSave;
    Button btPrint;
    ImageView backbtn;

    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("จัดการรายชื่อ");
        getSupportActionBar().hide();

        setContentView(R.layout.setting_employee);
        tID = (TextView) findViewById(R.id.tId);
        tEmp = (EditText) findViewById(R.id.tEmp);
        tFname = (EditText) findViewById(R.id.tFname);
        tLname = (EditText) findViewById(R.id.tLname);
        tDepId  = (Spinner) findViewById(R.id.tDepId);
        tSearch = (EditText) findViewById(R.id.editSearch);

        Li1 = (LinearLayout) findViewById(R.id.Li1);
        Li2 = (LinearLayout) findViewById(R.id.Li2);
        toggle = (ImageView) findViewById(R.id.imageUD);
        switchview = (Button) findViewById(R.id.button_user);
        switchview2 = (Button) findViewById(R.id.button_timetable);
        btAdd = (Button) findViewById(R.id.b_Add);
        btSearch = (Button) findViewById(R.id.b_txt);
        btDel = (Button) findViewById(R.id.b_Del);
        btSave = (Button) findViewById(R.id.b_Save);
        btPrint = (Button) findViewById(R.id.b_Print);
        backbtn = (ImageView) findViewById(R.id.imageBack);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ListData("");

        // BUTTON CLICK
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleChange();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(chkUpdate) {
                    setData("1",tID.getText()+"",tEmp.getText()+"",tFname.getText()+"",tLname.getText()+"",tDepId.getSelectedItem().toString(),iFt.setEmployee());
                    ListData(tSearch.getText()+"");
                }else {
                    setData("0",tID.getText()+"",tEmp.getText()+"",tFname.getText()+"",tLname.getText()+"",tDepId.getSelectedItem().toString(),iFt.setEmployee());
                    ListData(tSearch.getText()+"");
                }
                chkUpdate=false;
                toggleChange();
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chkUpdate=false;
                tID.setText("");
                tEmp.setText("");
                tFname.setText("");
                tLname.setText("");
                tDepId.setSelection(0);

            }
        });

        btDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(EmployeeActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(EmployeeActivity.this);
                }
                builder.setTitle("การลบข้อมูลของคุณ อาจมีผลกระทบกับหลายส่วนของโปรแกรม")
                        .setMessage("คุณต้องการลบข้อมูลนี้ใช่หรือไม่?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                setData("2",tID.getText()+"",tEmp.getText()+"",tFname.getText()+"",tLname.getText()+"",tDepId.getSelectedItem().toString(),iFt.setEmployee());
                                ListData("%");
                                toggleChange();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        btPrint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        btSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ListData( tSearch.getText()+"" );
                toggleChange();
            }
        });

        switchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        switchview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeActivity.this, TimeTableActivity.class);
                startActivity(intent);
                finish();
            }
        });

        SpinnerDataDept("CSSD");

    }

    private void toggleChange() {
        if(flag){
            Li1.setVisibility(View.INVISIBLE);
            Li2.setVisibility(View.VISIBLE);
            toggle.setImageResource(R.drawable.ic_up);
        }else{
            Li1.setVisibility(View.VISIBLE);
            Li2.setVisibility(View.INVISIBLE);
            toggle.setImageResource(R.drawable.ic_down);
        }
        flag = !flag;
    }

    public void SpinnerDataDept(final String sel) {
        class SpinnerDataDept extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    arrdepartment.clear();
                    arrdepartment.add("-");
                    String Depsel = "";
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        arrdepartment.add(c.getString("xDepName2"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EmployeeActivity.this,android.R.layout.simple_spinner_dropdown_item,arrdepartment);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    tDepId.setAdapter(adapter);
                    int spinnerPosition = adapter.getPosition(sel);
                    tDepId.setSelection(spinnerPosition);


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
        SpinnerDataDept ru = new SpinnerDataDept();
        ru.execute();
    }

    public void ListData(final String Search) {
        class ListData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EmployeeActivity.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    results.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields1(c.getString("xID"));
                        newsData.setFields2(c.getString("xEmpCode"));
                        newsData.setFields3(c.getString("xFname"));
                        newsData.setFields4(c.getString("xLname"));
                        newsData.setFields5(c.getString("xDepID"));
                        results.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.listView);
                    lv1.setAdapter(new Aux5Adapter( EmployeeActivity.this, results));

                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Object o = lv1.getItemAtPosition(position);
                            Response_Aux newsData = (Response_Aux) o;
                            tID.setText(newsData.getFields1());
                            tEmp.setText(newsData.getFields2());
                            tFname.setText(newsData.getFields3());
                            tLname.setText(newsData.getFields4());
//                            Log.d("Tag", newsData.getFields5());
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(EmployeeActivity.this,android.R.layout.simple_spinner_dropdown_item,arrdepartment);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            tDepId.setAdapter(adapter);
                            int spinnerPosition = adapter.getPosition(newsData.getFields5());
                            tDepId.setSelection(spinnerPosition);

                            chkUpdate = true;
                            toggleChange();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Search",params[0]);
                String result = ruc.sendPostRequest(iFt.getEmployee(),data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Search );
    }

    public void setData(String sel,String xID,String xEmp,String xUser,String xPass,String xDepID,String xUrl) {
        class setData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EmployeeActivity.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    results.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        Log.d("AAA",c.getString("Finish"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xSel",params[0]);
                data.put("xID",params[1]);
                data.put("xEmp",params[2]);
                data.put("xFname",params[3]);
                data.put("xLname",params[4]);
                data.put("xDepID",params[5]);
                String result = ruc.sendPostRequest( params[6],data);
                Log.d("test", data+"");
                return  result;
            }
        }

        setData ru = new setData();
        ru.execute( sel,xID,xEmp,xUser,xPass,xDepID,xUrl );
    }


    @Override
    public void onBackPressed() {
        this.finish();
    }

}
