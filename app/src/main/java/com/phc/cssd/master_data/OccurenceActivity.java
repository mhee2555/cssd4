package com.phc.cssd.master_data;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.Aux12Adapter;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class OccurenceActivity extends AppCompatActivity {
    ArrayList<Response_Aux> results = new ArrayList<Response_Aux>();
    private static final String TAG_RESULTS="result";
    JSONArray setRs = null;
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    Boolean chkUpdate = false;

    TextView tId;
    EditText tDocno;
    EditText tDocdate;
    EditText tModifydate;
    EditText tOcid;
    EditText tUsercode;
    EditText tMachineid;
    EditText tRound;
    EditText tRefdocno;
    EditText tRemark;
    EditText tDepid;
    EditText tStepno;
    EditText tSearch;

    ImageView toggle;
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
        setTitle("เหตุการณ์");
        getSupportActionBar().hide();

        setContentView(R.layout.setting_occurence);
        tId = (TextView) findViewById(R.id.tId);
        tDocno = (EditText) findViewById(R.id.tDocno);
        tDocdate = (EditText) findViewById(R.id.tDocDate);
        tModifydate = (EditText) findViewById(R.id.tModifyDate);
        tOcid = (EditText) findViewById(R.id.tOcId);
        tUsercode = (EditText) findViewById(R.id.tUserCode);
        tMachineid = (EditText) findViewById(R.id.tMachineId);
        tRound = (EditText) findViewById(R.id.tRound);
        tRefdocno = (EditText) findViewById(R.id.tRefDocno);
        tRemark = (EditText) findViewById(R.id.tRemark);
        tDepid = (EditText) findViewById(R.id.tDepId);
        tStepno = (EditText) findViewById(R.id.tStepno);
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
        ListData("%");

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleChange();
            }
        });
        // BUTTON CLICK
        btSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String IsCancel = "0";
                if(chkUpdate) {
                    setData("1",tDocno.getText()+"",tDocdate.getText()+"",tModifydate.getText()+"",tOcid.getText()+"",tUsercode.getText()+"",tMachineid.getText()+"",tRound.getText()+"",tRefdocno.getText()+"",tRemark.getText()+"",tDepid.getText()+"",tStepno.getText()+"",IsCancel,tId.getText()+"",iFt.setUrlOccurence());
                    ListData(tDocno.getText()+"");
                }else {

                    setData("0",tDocno.getText()+"",tDocdate.getText()+"",tModifydate.getText()+"",tOcid.getText()+"",tUsercode.getText()+"",tMachineid.getText()+"",tRound.getText()+"",tRefdocno.getText()+"",tRemark.getText()+"",tDepid.getText()+"",tStepno.getText()+"",IsCancel,tId.getText()+"",iFt.setUrlOccurence());
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
                tDocno.setText("");
                tDocdate.setText("");
                tModifydate.setText("");
                tOcid.setText("");
                tUsercode.setText("");
                tMachineid.setText("");
                tRound.setText("");
                tRefdocno.setText("");
                tRemark.setText("");
                tDepid.setText("");
                tStepno.setText("");
            }
        });

        btDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(OccurenceActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(OccurenceActivity.this);
                }
                builder.setTitle("การลบข้อมูลของคุณ อาจมีผลกระทบกับหลายส่วนของโปรแกรม")
                        .setMessage("คุณต้องการลบข้อมูลนี้ใช่หรือไม่?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                setData("2",tDocno.getText()+"",tDocdate.getText()+"",tModifydate.getText()+"",tOcid.getText()+"",tUsercode.getText()+"",tMachineid.getText()+"",tRound.getText()+"",tRefdocno.getText()+"",tRemark.getText()+"",tDepid.getText()+"",tStepno.getText()+"","1",tId.getText()+"",iFt.setUrlOccurence());
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
                loading = ProgressDialog.show(OccurenceActivity.this, "Please Wait",null, true, true);
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
                        newsData.setFields1(c.getString("Id"));
                        newsData.setFields2(c.getString("DocNo"));
                        newsData.setFields3(c.getString("DocDate"));
                        newsData.setFields4(c.getString("ModifyDate"));
                        newsData.setFields5(c.getString("OccuranceID"));
                        newsData.setFields6(c.getString("UserCode"));
                        newsData.setFields7(c.getString("MachineID"));
                        newsData.setFields8(c.getString("Round"));
                        newsData.setFields9(c.getString("RefDocNo"));
                        newsData.setFields10(c.getString("Remark"));
                        newsData.setFields11(c.getString("DepID"));
                        newsData.setFields12(c.getString("StepNo"));
                        newsData.setFields13(c.getString("IsCancel"));
                        results.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.listView);
                    lv1.setAdapter(new Aux12Adapter( OccurenceActivity.this, results));

                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Object o = lv1.getItemAtPosition(position);
                            Response_Aux newsData = (Response_Aux) o;
                            tId.setText(newsData.getFields1());
                            tDocno.setText(newsData.getFields2());
                            tDocdate.setText(newsData.getFields3());
                            tModifydate.setText(newsData.getFields4());
                            tOcid.setText(newsData.getFields5());
                            tUsercode.setText(newsData.getFields6());
                            tMachineid.setText(newsData.getFields7());
                            tRound.setText(newsData.getFields8());
                            tRefdocno.setText(newsData.getFields9());
                            tRemark.setText(newsData.getFields10());
                            tDepid.setText(newsData.getFields11());
                            tStepno.setText(newsData.getFields12());
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
                String result = ruc.sendPostRequest(iFt.getUrlOccurence(),data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Search );
    }

    public void setData(String sel,String tDocno,String tDocdate,String tModifydate,String tOcid,String tUsercode,String tMachineid,String tRound,String tRefdocno,String tRemark,String tDepid,String tStepno,String xIsCancel,String xId,String xUrl) {
        class ListData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(OccurenceActivity.this, "Please Wait",null, true, true);
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
                data.put("tDocno",params[1]);
                data.put("tDocdate",params[2]);
                data.put("tModifydate",params[3]);
                data.put("tOcid",params[4]);
                data.put("tUsercode",params[5]);
                data.put("tMachineid",params[6]);
                data.put("tRound",params[7]);
                data.put("tRefdocno",params[8]);
                data.put("tRemark",params[9]);
                data.put("tDepid",params[10]);
                data.put("tStepno",params[11]);
                data.put("xIsCancel",params[12]);
                data.put("xId",params[13]);
                String result = ruc.sendPostRequest( params[14],data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute(sel,tDocno,tDocdate,tModifydate,tOcid,tUsercode,tMachineid,tRound,tRefdocno,tRemark,tDepid,tStepno,xIsCancel,xId,xUrl);
    }


    @Override
    public void onBackPressed() {
        this.finish();
    }


}

