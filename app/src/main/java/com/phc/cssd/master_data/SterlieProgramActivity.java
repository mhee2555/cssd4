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
import com.phc.cssd.adapter.Aux6Adapter;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SterlieProgramActivity extends AppCompatActivity {

    ArrayList<Response_Aux> results = new ArrayList<Response_Aux>();
    ArrayList<String> SpinnerArr = new ArrayList<String>();
    private static final String TAG_RESULTS="result";
    JSONArray setRs = null;
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    Boolean chkUpdate = false;

    TextView tId;
    EditText tProgram;
    EditText tShortn;
    Spinner sType;
    EditText tProcesstime;
    EditText tRemark;
    EditText tSearch;

    ImageView toggle;
    LinearLayout Li1;
    LinearLayout Li2;
    Button switchview;
    Button switchview2;
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
        setContentView(R.layout.setting_sterlieprogram);
        setTitle("โปรแกรม Sterile");
        getSupportActionBar().hide();

        tId = (TextView) findViewById(R.id.tId);
        tProgram = (EditText) findViewById(R.id.tProgram);
        tShortn = (EditText) findViewById(R.id.tShortn);
        sType = (Spinner) findViewById(R.id.spinner);
        tProcesstime = (EditText) findViewById(R.id.tProcesstime);
        tRemark = (EditText) findViewById(R.id.tRemark);
        tSearch = (EditText) findViewById(R.id.editSearch);

        Li1 = (LinearLayout) findViewById(R.id.Li1);
        Li2 = (LinearLayout) findViewById(R.id.Li2);
        toggle = (ImageView) findViewById(R.id.imageUD);
        switchview = (Button) findViewById(R.id.button_process);
        switchview2 = (Button) findViewById(R.id.button_programitem);
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
        SpinnerData("","false");
        ListData("%");

        // BUTTON CLICK
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleChange();
            }
        });
        btSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String IsCancel = "0";
                Log.d("aa",IsCancel);
                if(chkUpdate) {
                    setData("1",tProgram.getText()+"",tShortn.getText()+"",sType.getSelectedItem().toString(),tProcesstime.getText().toString(),tRemark.getText().toString(),IsCancel,tId.getText()+"",iFt.setSterlieProgram());
                    ListData(tSearch.getText()+"");
                }else {
                    setData("0",tProgram.getText()+"",tShortn.getText()+"",sType.getSelectedItem().toString(),tProcesstime.getText().toString(),tRemark.getText().toString(),IsCancel,tId.getText()+"",iFt.setSterlieProgram());
                    ListData(tSearch.getText()+"");

                }
                chkUpdate=false;
                toggleChange();
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chkUpdate=false;
                tId.setText("");
                tProgram.setText("");
                tShortn.setText("");
                tProcesstime.setText("");
                tRemark.setText("");
                sType.setSelection(0);
                tSearch.setText("");
            }
        });

        btDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!tId.getText().toString().equals("")) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(SterlieProgramActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(SterlieProgramActivity.this);
                    }
                    builder.setTitle("การลบข้อมูลของคุณ อาจมีผลกระทบกับหลายส่วนของโปรแกรม")
                            .setMessage("คุณต้องการลบข้อมูลนี้ใช่หรือไม่?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    setData("2",tProgram.getText()+"",tShortn.getText()+"",sType.getSelectedItem().toString(),tProcesstime.getText().toString(),tRemark.getText().toString(),"1",tId.getText()+"",iFt.setSterlieProgram());
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

        switchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SterlieProgramActivity.this, SterileprocessActivity.class);
                startActivity(intent);
                finish();
            }
        });

        switchview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SterlieProgramActivity.this, SterileProgramItemActivity.class);
                startActivity(intent);
                finish();
            }
        });
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

    public void ListData(String Search) {
        class ListData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SterlieProgramActivity.this, "Please Wait",null, true, true);
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
                        newsData.setFields2(c.getString("SterileName"));
                        newsData.setFields3(c.getString("SterileName2"));
                        newsData.setFields4(c.getString("SterileProcessID"));
                        newsData.setFields5(c.getString("ProcessTime"));
                        newsData.setFields6(c.getString("Remark"));

                        results.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.listView);
                    lv1.setAdapter(new Aux6Adapter( SterlieProgramActivity.this, results));

                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Object o = lv1.getItemAtPosition(position);
                            Response_Aux newsData = (Response_Aux) o;
                            tId.setText(newsData.getFields1());
                            tProgram.setText(newsData.getFields2());
                            tShortn.setText(newsData.getFields3());
                            tProcesstime.setText(newsData.getFields5());
                            tRemark.setText(newsData.getFields6());
                            toggleChange();
                            SpinnerData(newsData.getFields4(),"true");
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
                String result = ruc.sendPostRequest(iFt.getSterlieProgram(),data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Search );
    }

    public void setData(String sel,String xProgramn,String xShortn,String xType,String xTime,String xRemark,String IsCancel,String xId,String xUrl) {
        class setData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SterlieProgramActivity.this, "Please Wait",null, true, true);
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
                data.put("xProgramn",params[1]);
                data.put("xShortn",params[2]);
                data.put("xType",params[3]);
                data.put("xTime",params[4]);
                data.put("xRemark",params[5]);
                data.put("xIsCancel",params[6]);
                data.put("xID",params[7]);
                Log.d("dataSterileProc:",data+"");
                String result = ruc.sendPostRequest( params[8],data);
                Log.d("result",result+"");
                return  result;
            }
        }

        setData ru = new setData();
        ru.execute( sel,xProgramn,xShortn,xType,xTime,xRemark,IsCancel,xId,xUrl );
    }


    public void SpinnerData(final String type, final String boolclick) {
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
                        SpinnerArr.add(c.getString("SterileName2"));
                        if(i==0 && (boolclick!=null && boolclick.equals("true"))){
                            Depsel = type;
                            Log.d("checka", Depsel);
                        }else if(i==0 && (boolclick!=null && boolclick.equals("false"))){
                            Depsel = "-";
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SterlieProgramActivity.this,android.R.layout.simple_spinner_dropdown_item,SpinnerArr);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sType.setAdapter(adapter);
                    int spinnerPosition = adapter.getPosition(Depsel);
                    sType.setSelection(spinnerPosition);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("type",params[0]);
                String result = ruc.sendPostRequest(iFt.getSterlieProcess_spinner(),data);
                return  result;
            }
        }

        SpinnerData ru = new SpinnerData();
        ru.execute( type,boolclick );
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
