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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.Aux4Adapter;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SterileprocessActivity extends AppCompatActivity {
    ArrayList<Response_Aux> results = new ArrayList<Response_Aux>();
    private static final String TAG_RESULTS="result";
    JSONArray setRs = null;
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    Boolean chkUpdate = false;

    TextView tId;
    EditText tSterileName;
    EditText tSterileName2;
    EditText tProcessTime;
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
        setContentView(R.layout.setting_sterileprocess);
        setTitle("ประเภทการฆ่าเชื้อ");
        getSupportActionBar().hide();

        tId = (TextView) findViewById(R.id.tId);
        tSterileName = (EditText) findViewById(R.id.editSterile);
        tSterileName2 = (EditText) findViewById(R.id.editSterile2);
        tProcessTime = (EditText) findViewById(R.id.editProcesstime);
        tSearch = (EditText) findViewById(R.id.editSearch);

        Li1 = (LinearLayout) findViewById(R.id.Li1);
        Li2 = (LinearLayout) findViewById(R.id.Li2);
        toggle = (ImageView) findViewById(R.id.imageUD);
        switchview = (Button) findViewById(R.id.button_program);
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
               /* Intent intent = new Intent(SterileprocessActivity.this, SettingActivity.class);
                intent.putExtra("page","bottom");
                startActivity(intent);*/
                finish();
            }
        });
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
                if(chkUpdate) {
                    setData("1",tId.getText()+"",tSterileName.getText()+"",tSterileName2.getText()+"",tProcessTime.getText().toString(),IsCancel,iFt.setUrlSterileprocess());
                    ListData(tSterileName.getText()+"");
                }else {

                    setData("0",tId.getText()+"",tSterileName.getText()+"",tSterileName2.getText()+"",tProcessTime.getText().toString(),IsCancel,iFt.setUrlSterileprocess());
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
                tSterileName.setText("");
                tSterileName2.setText("");
                tProcessTime.setText("");
            }
        });

        btDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!tId.getText().toString().equals("")) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(SterileprocessActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(SterileprocessActivity.this);
                    }
                    builder.setTitle("การลบข้อมูลของคุณ อาจมีผลกระทบกับหลายส่วนของโปรแกรม")
                            .setMessage("คุณต้องการลบข้อมูลนี้ใช่หรือไม่?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    setData("2", tId.getText() + "", tSterileName.getText() + "", tSterileName2.getText() + "", tProcessTime.getText().toString(), "1", iFt.setUrlSterileprocess());
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
                Intent intent = new Intent(SterileprocessActivity.this, SterlieProgramActivity.class);
                startActivity(intent);
                finish();
            }
        });

        switchview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SterileprocessActivity.this, SterileProgramItemActivity.class);
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
                loading = ProgressDialog.show(SterileprocessActivity.this, "Please Wait",null, true, true);
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
                        newsData.setFields1(c.getString("xId"));
                        newsData.setFields2(c.getString("xSterileName"));
                        newsData.setFields3(c.getString("xSterileName2"));
                        newsData.setFields4(c.getString("xProcessTime"));
                        newsData.setFields5(c.getString("xIsCancel"));
                        results.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.listView);
                    lv1.setAdapter(new Aux4Adapter( SterileprocessActivity.this, results));

                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Object o = lv1.getItemAtPosition(position);
                            Response_Aux newsData = (Response_Aux) o;
                            tId.setText(newsData.getFields1());
                            tSterileName.setText(newsData.getFields2());
                            tSterileName2.setText(newsData.getFields3());
                            tProcessTime.setText(newsData.getFields4());
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
                String result = ruc.sendPostRequest(iFt.getUrlSterileprocess(),data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Search );
    }

    public void setData(String sel,String xId,String xst1,String xst2,String xProcessTime,String xIsCancel,String xUrl) {
        class ListData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SterileprocessActivity.this, "Please Wait",null, true, true);
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
                data.put("xId",params[1]);
                data.put("xst1",params[2]);
                data.put("xst2",params[3]);
                data.put("xProcessTime",params[4]);
                data.put("xIsCancel",params[5]);
                String result = ruc.sendPostRequest( params[6],data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( sel,xId,xst1,xst2,xProcessTime,xIsCancel,xUrl );
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
