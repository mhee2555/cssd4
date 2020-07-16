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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.SettingActivity;
import com.phc.cssd.adapter.Aux5Adapter_Previewstricker;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LabelActivity extends AppCompatActivity {
    ArrayList<Response_Aux> results = new ArrayList<Response_Aux>();
    ArrayList<String> arrgroup = new ArrayList<String>();
    ArrayList<String> arrprinter = new ArrayList<String>();
    private static final String TAG_RESULTS="result";
    JSONArray setRs = null;
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    Boolean chkUpdate = false;
    Boolean IsActive = false;

    CheckBox cb_active;
    Spinner spn_labelgroup;
    Spinner spn_printer;
    TextView tId;

    EditText tName;
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
    String grp_id;
    String prt_id;

    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Label");
        setarrprinter();
        getSupportActionBar().hide();

        setContentView(R.layout.setting_label);
        tId = (TextView) findViewById(R.id.tId);
        tName = (EditText) findViewById(R.id.editName);
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
        spn_labelgroup = (Spinner) findViewById(R.id.spn_labelgroup);
        spn_printer = (Spinner) findViewById(R.id.spn_printer);
        cb_active =(CheckBox) findViewById(R.id.cb_active);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(LabelActivity.this, SettingActivity.class);
                intent.putExtra("page","top");
                startActivity(intent);*/
                finish();
            }
        });

        ListData("%");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LabelActivity.this,android.R.layout.simple_spinner_dropdown_item,arrprinter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_printer.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition("0");
        spn_printer.setSelection(spinnerPosition);
        Spinnerlabelgroup("0");
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
                //Log.d("sasdasd: ", ( IsActive ) ? "1" : "0");
                if(!prt_id.equals("-")&&!grp_id.equals("-")){
                if(chkUpdate) {
                    setData("1",tName.getText()+"",IsCancel,tId.getText()+"",iFt.setUrlLabel(),prt_id,grp_id,( cb_active.isChecked() ) ? "1" : "0");
                    ListData(tName.getText()+"");
                }else {
                    setData("0",tName.getText()+"",IsCancel,tId.getText()+"",iFt.setUrlLabel(),prt_id,grp_id,( cb_active.isChecked() ) ? "1" : "0");
                    ListData("%");

                }
                }else{
                    Toast.makeText(LabelActivity.this, "กรุณาเลือกปริ้นท์เตอร์และกลุ่มสติ๊กเกอร์", Toast.LENGTH_SHORT).show();
                }
                chkUpdate=false;
                toggleChange();
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chkUpdate=false;
                tId.setText("");
                tName.setText("");
                cb_active.setChecked(false);
                spn_labelgroup.setSelection(0);
                spn_printer.setSelection(0);
            }
        });

        btDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!tId.getText().toString().equals("")) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(LabelActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(LabelActivity.this);
                    }
                    builder.setTitle("การลบข้อมูลของคุณ อาจมีผลกระทบกับหลายส่วนของโปรแกรม")
                            .setMessage("คุณต้องการลบข้อมูลนี้ใช่หรือไม่?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    setData("2",tName.getText()+"","1",tId.getText()+"",iFt.setUrlLabel(),"","","");
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
        spn_labelgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grp_id = arrgroup.get(position);
                Log.d("grp_id : ", grp_id);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spn_printer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prt_id = arrprinter.get(position);
                Log.d("prt_id : ", prt_id);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
                loading = ProgressDialog.show(LabelActivity.this, "Please Wait",null, true, true);
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
                        newsData.setFields2(c.getString("xName"));
                        newsData.setFields3(c.getString("xPrinter"));
                        newsData.setFields4(c.getString("xLabelGroup"));
                        newsData.setFields5(c.getString("xIsActive"));
                        newsData.setFields6(c.getString("xIsCancel"));
                        results.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.listView);
                    lv1.setAdapter(new Aux5Adapter_Previewstricker( LabelActivity.this, results));

                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Object o = lv1.getItemAtPosition(position);
                            Response_Aux newsData = (Response_Aux) o;
                            tId.setText(newsData.getFields1());
                            tName.setText(newsData.getFields2());
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(LabelActivity.this,android.R.layout.simple_spinner_dropdown_item,arrprinter);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spn_printer.setAdapter(adapter);
                            int spinnerPosition = adapter.getPosition(newsData.getFields3());
                            spn_printer.setSelection(spinnerPosition);
                            Spinnerlabelgroup(newsData.getFields4());
                            if(newsData.getFields5().equals("1")){
                                cb_active.setChecked(true);
                            }else{
                                cb_active.setChecked(false);
                            }

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
                Log.d("doInBackground: ", data+"");
                String result = ruc.sendPostRequest(iFt.getUrlLabel(),data);
                Log.d("result: ", result);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Search );
    }

    public void setData(String sel,String xName,String xIsCancel,String xId,String xUrl,String xPrinter,String xSticker,String xIsActive) {
        class ListData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LabelActivity.this, "Please Wait",null, true, true);
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
                data.put("xName",params[1]);
                data.put("xIsCancel",params[2]);
                data.put("xId",params[3]);
                data.put("xPrinter",params[5]);
                data.put("xSticker",params[6]);
                data.put("xIsActive",params[7]);
                Log.d("doInBackground: ", data+"");
                String result = ruc.sendPostRequest( params[4],data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( sel,xName,xIsCancel,xId,xUrl,xPrinter,xSticker,xIsActive );
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SettingActivity.class);
        intent.putExtra("page","top");
        startActivity(intent);
        this.finish();
    }

    public void Spinnerlabelgroup(final String sel) {
        class Spinnerlabelgroup extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    ArrayList<String> grp_name = new ArrayList<String>();
                    arrgroup.clear();
                    arrgroup.add("-");
                    grp_name.add("-");
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        grp_name.add(c.getString("xLabelGroup"));
                        arrgroup.add(c.getString("xID"));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(LabelActivity.this,android.R.layout.simple_spinner_dropdown_item,grp_name);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_labelgroup.setAdapter(adapter);
                    int spinnerPosition = adapter.getPosition(sel);
                    spn_labelgroup.setSelection(spinnerPosition);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                String result = ruc.sendPostRequest(iFt.get_Spinner_labelgroup(),data);
                Log.d("restult: ", result);
                return  result;
            }
        }
        Spinnerlabelgroup ru = new Spinnerlabelgroup();
        ru.execute();
    }
    public void setarrprinter(){
        arrprinter.add("-");
        for(int i =0;i<4;i++){
            arrprinter.add(i+1+"");
        }
    }
}
