package com.phc.cssd.master_data;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.phc.cssd.R;
import com.phc.cssd.adapter.Aux3Adapter;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class OccurenceTypeActivity extends AppCompatActivity {

    ArrayList<Response_Aux> results = new ArrayList<Response_Aux>();
    ArrayList<String> arrList = new ArrayList<String>();
    private static final String TAG_RESULTS="result";
    JSONArray setRs = null;
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    Boolean chkUpdate = false;

    TextView tId;
    EditText tOccname;
    Spinner tProcessoc;
    EditText tSearch;

    ImageView toggle;
    LinearLayout Li1;
    LinearLayout Li2;
    Button btAdd;
    Button btSearch;
    Button btDel;
    Button btSave;
    Button btPrint;
    Button btResterile;
    ImageView backbtn;

    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_occurence_type);

        setTitle("จัดการข้อมูลความเสี่ยง");
        getSupportActionBar().hide();

        tId = (TextView) findViewById(R.id.tId);
        tOccname = (EditText) findViewById(R.id.tOccname);
        tProcessoc = (Spinner) findViewById(R.id.tProcessoc);
        tSearch = (EditText) findViewById(R.id.editSearch);

        Li1 = (LinearLayout) findViewById(R.id.Li1);
        Li2 = (LinearLayout) findViewById(R.id.Li2);
        toggle = (ImageView) findViewById(R.id.imageUD);
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

        btResterile = (Button) findViewById(R.id.button_resteriletype);
        btResterile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OccurenceTypeActivity.this, ResterileTypeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ListData("%");

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleChange();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String IsCancel = "0";
                if(chkUpdate) {
                    setData("1",tOccname.getText()+"",tProcessoc.getSelectedItem()+"",IsCancel,tId.getText()+"",iFt.setOccurenceType());
                    ListData(tSearch.getText()+"");
                }else {
                    setData("0",tOccname.getText()+"",tProcessoc.getSelectedItem()+"",IsCancel,tId.getText()+"",iFt.setOccurenceType());
                    ListData("%");
                }
                chkUpdate=false;
                toggleChange();
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chkUpdate=false;
                tId.setText("");
                tOccname.setText("");
                tProcessoc.setSelection(0);
                tSearch.setText("");
            }
        });

        btDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!tId.getText().toString().equals("")) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(OccurenceTypeActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(OccurenceTypeActivity.this);
                    }
                    builder.setTitle("การลบข้อมูลของคุณ อาจมีผลกระทบกับหลายส่วนของโปรแกรม")
                            .setMessage("คุณต้องการลบข้อมูลนี้ใช่หรือไม่?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    setData("2",tOccname.getText()+"",tProcessoc.getSelectedItem()+"","1",tId.getText()+"",iFt.setOccurenceType());
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

        SpinnerDataProcessoc("-");
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

    public void SpinnerDataProcessoc(final String sel) {
        class SpinnerDataProcessoc extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    arrList.clear();
                    arrList.add("-");
                    String Depsel = "";
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        arrList.add(c.getString("Process"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(OccurenceTypeActivity.this,android.R.layout.simple_spinner_dropdown_item,arrList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    tProcessoc.setAdapter(adapter);
                    int spinnerPosition = adapter.getPosition(sel);
                    tProcessoc.setSelection(spinnerPosition);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                String result = ruc.sendPostRequest(iFt.get_Spinner_Processoc(),data);
                return  result;
            }
        }
        SpinnerDataProcessoc ru = new SpinnerDataProcessoc();
        ru.execute();
    }

    public void ListData(String Search) {
        class ListData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(OccurenceTypeActivity.this, "Please Wait",null, true, true);
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
                        newsData.setFields2(c.getString("xOccuranceName"));
                        newsData.setFields3(c.getString("xProcess"));
                        results.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.listView);
                    lv1.setAdapter(new Aux3Adapter( OccurenceTypeActivity.this, results));

                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Object o = lv1.getItemAtPosition(position);
                            Response_Aux newsData = (Response_Aux) o;
                            tId.setText(newsData.getFields1());
                            tOccname.setText(newsData.getFields2());

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(OccurenceTypeActivity.this,android.R.layout.simple_spinner_dropdown_item,arrList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            tProcessoc.setAdapter(adapter);
                            int spinnerPosition = adapter.getPosition(newsData.getFields3());
                            tProcessoc.setSelection(spinnerPosition);
                            toggleChange();
                            chkUpdate = true;

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
                String result = ruc.sendPostRequest(iFt.getOccurenceType(),data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Search );
    }

    public void setData(String sel,String xDepName,String xDepName2,String xIsCancel,String xId,String xUrl) {
        class setData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(OccurenceTypeActivity.this, "Please Wait",null, true, true);
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
                data.put("xOccname",params[1]);
                data.put("xProcessoc",params[2]);
                data.put("xIsCancel",params[3]);
                data.put("xID",params[4]);
                String result = ruc.sendPostRequest( params[5],data);
                return  result;
            }
        }

        setData ru = new setData();
        ru.execute( sel,xDepName,xDepName2,xIsCancel,xId,xUrl );
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
