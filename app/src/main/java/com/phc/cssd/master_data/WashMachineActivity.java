package com.phc.cssd.master_data;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.Aux2Adapter;
import com.phc.cssd.adapter.AuxSterileMachine;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class WashMachineActivity extends AppCompatActivity {
    ArrayList<Response_Aux> results = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> process = new ArrayList<Response_Aux>();
    ArrayList<String> arrmachine = new ArrayList<String>();
    private static final String TAG_RESULTS="result";
    JSONArray setRs = null;
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    Boolean chkUpdate = false;

    TextView tId;
    EditText tName;
    EditText tSearch;
    CheckBox cball;

    ImageView toggle;
    LinearLayout Li1;
    LinearLayout Li2;
    LinearLayout Li3;
    Spinner machinespn;

    Button btMaster;
    Button btLink;
    Button btSel;
    Button btlinkSave;
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
        setTitle("เครื่องล้าง");
        getSupportActionBar().hide();

        setContentView(R.layout.setting_washmachine);
        tId = (TextView) findViewById(R.id.tId);
        tName = (EditText) findViewById(R.id.tName);
        tSearch = (EditText) findViewById(R.id.editSearch);

        Li1 = (LinearLayout) findViewById(R.id.Li1);
        Li2 = (LinearLayout) findViewById(R.id.Li2);
        Li3 = (LinearLayout) findViewById(R.id.Li3);
        toggle = (ImageView) findViewById(R.id.imageUD);
        btAdd = (Button) findViewById(R.id.b_Add);
        btSearch = (Button) findViewById(R.id.b_txt);
        btDel = (Button) findViewById(R.id.b_Del);
        btSave = (Button) findViewById(R.id.b_Save);
        btPrint = (Button) findViewById(R.id.b_Print);
        btMaster = (Button) findViewById(R.id.button_master);
        btMaster.setEnabled(false);
        btMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Li1.setVisibility(View.VISIBLE);
                Li2.setVisibility(View.GONE);
                Li3.setVisibility(View.GONE);
                btMaster.setEnabled(false);
                btLink.setEnabled(true);
                toggle.setVisibility(View.VISIBLE);
                btMaster.setBackgroundResource(R.drawable.tab_master_cyanblue);
                btLink.setBackgroundResource(R.drawable.tab_master_cyanblue_noselect);
                btMaster.setTextColor(Color.WHITE);
                btLink.setTextColor(Color.DKGRAY);
            }
        });

        btLink = (Button) findViewById(R.id.button_link);
        btLink.setEnabled(true);
        btLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Li1.setVisibility(View.GONE);
                Li2.setVisibility(View.GONE);
                Li3.setVisibility(View.VISIBLE);
                btLink.setEnabled(false);
                btMaster.setEnabled(true);
                toggle.setVisibility(View.GONE);
                btMaster.setBackgroundResource(R.drawable.tab_master_cyanblue_noselect);
                btLink.setBackgroundResource(R.drawable.tab_master_cyanblue);
                btMaster.setTextColor(Color.DKGRAY);
                btLink.setTextColor(Color.WHITE);
            }
        });
        backbtn = (ImageView) findViewById(R.id.imageBack);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    setData("1",tName.getText()+"",IsCancel,tId.getText()+"",iFt.setUrlWashMachine());
                    ListData(tName.getText()+"");
                }else {
                    setData("0",tName.getText()+"",IsCancel,tId.getText()+"",iFt.setUrlWashMachine());
                    ListData("%");

                }
                chkUpdate=false;
                toggleChange();
                Spinnerprocess();
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chkUpdate=false;
                tId.setText("");
                tName.setText("");
            }
        });

        btDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!tId.getText().toString().equals("")) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(WashMachineActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(WashMachineActivity.this);
                    }
                    builder.setTitle("การลบข้อมูลของคุณ อาจมีผลกระทบกับหลายส่วนของโปรแกรม")
                            .setMessage("คุณต้องการลบข้อมูลนี้ใช่หรือไม่?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    setData("2",tName.getText()+"","1",tId.getText()+"",iFt.setUrlWashMachine());
                                    ListData("%");
                                    toggleChange();
                                    Spinnerprocess();
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

        btSel = (Button) findViewById(R.id.button_select);
        btlinkSave = (Button) findViewById(R.id.button_save);
        btlinkSave.setEnabled(false);
        btlinkSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getCountselected().equals("0")) {
                    Log.d("test", " count: "+getCountselected()+" type: "+machinespn.getSelectedItem().toString());
                    if(getCountselected().equals("0")){
                        LinkProcess("",getCountselected(),machinespn.getSelectedItem().toString());
                    }else{
                        LinkProcess(getSelectedcheckbox(),getCountselected(),machinespn.getSelectedItem().toString());
                    }
                    ListData_Process(machinespn.getSelectedItem().toString());
                    btlinkSave.setEnabled(false);
                    cball.setChecked(false);
                }else{
                    Toast.makeText(WashMachineActivity.this,"กรุณาเลือกข้อมูลอย่างน้อย 1 รายการ",Toast.LENGTH_LONG).show();
                }
            }
        });
        btSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!machinespn.getSelectedItem().toString().equals("-")){
                    ListData_Process(machinespn.getSelectedItem().toString());
                    btlinkSave.setEnabled(true);
                }else{
                    process.clear();
                    final ListView lv1 = (ListView) findViewById(R.id.list_process);
                    lv1.setAdapter(new AuxSterileMachine( WashMachineActivity.this, process));
                    Toast.makeText(WashMachineActivity.this, "กรุณาเลือกเครื่องล้าง", Toast.LENGTH_SHORT).show();
                    btlinkSave.setEnabled(false);
                }
            }
        });



        machinespn = (Spinner) findViewById(R.id.spinner_machine);
        machinespn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                process.clear();
                final ListView lv1 = (ListView) findViewById(R.id.list_process);
                lv1.setAdapter(new AuxSterileMachine( WashMachineActivity.this, process));
                btlinkSave.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
        Spinnerprocess();

        cball = (CheckBox) findViewById(R.id.chx_all);
        cball.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleCheckboxAll_link(cball.isChecked());
            }
        });
    }
    private void toggleCheckboxAll_link(Boolean temp) {
        for (int i=0;i<process.size();i++){
            process.get(i).setIs_Check(temp);
        }
        final ListView lv1 = (ListView) findViewById(R.id.list_process);
        lv1.setAdapter(new AuxSterileMachine( WashMachineActivity.this, process));
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

    public String getSelectedcheckbox(){
        String Arraysel = "";
        for (int i=0;i<process.size();i++){
            if(i<process.size()) {
                if(process.get(i).isIs_Check()) {
                    Arraysel += process.get(i).getFields2() + ",";
                }
            }
        }
        Arraysel = Arraysel.substring(0, Arraysel.length() - 1);
        return Arraysel;
    }

    public String getCountselected(){
        int count = 0;
        for (int i=0;i<process.size();i++){
            if(i<process.size()) {
                if(process.get(i).isIs_Check()) {
                    count++;
                }
            }
        }
        return String.valueOf(count);
    }

    public void Spinnerprocess() {
        class Spinnerprocess extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    arrmachine.clear();
                    arrmachine.add("-");
                    String Depsel = "";
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        arrmachine.add(c.getString("MachineName"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(WashMachineActivity.this,android.R.layout.simple_spinner_dropdown_item,arrmachine);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    machinespn.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Pass","");
                String result = ruc.sendPostRequest(iFt.getSpinner_washmachine(),data);
                return  result;
            }
        }

        Spinnerprocess ru = new Spinnerprocess();
        ru.execute();
    }

    public void LinkProcess(String SelectedCB,String Countn,String typeprocess) {
        class LinkProcess extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    String Depsel = "";
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")){
                            Toast.makeText(WashMachineActivity.this, "ผูกสำเร็จ", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(WashMachineActivity.this, "ผูกล้มเหลว", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Selected",params[0]);
                data.put("Count",params[1]);
                data.put("type",params[2]);
                String result = ruc.sendPostRequest(iFt.reCreateWashMachine_process(),data);
                return  result;
            }
        }

        LinkProcess ru = new LinkProcess();
        ru.execute(SelectedCB,Countn,typeprocess);
    }

    public void ListData_Process(String type) {
        class ListData_Process extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(WashMachineActivity.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    process.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields2(c.getString("xId"));
                        newsData.setFields1(c.getString("WashingProcess"));
                        if(c.getString("checked").equals("true")){
                            newsData.setIs_Check(true);
                        }
                        process.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.list_process);
                    lv1.setAdapter(new AuxSterileMachine( WashMachineActivity.this, process));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("type",params[0]);
                String result = ruc.sendPostRequest(iFt.getListProcess(),data);
                return  result;
            }
        }

        ListData_Process ru = new ListData_Process();
        ru.execute( type );
    }

    public void ListData(String Search) {
        class ListData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(WashMachineActivity.this, "Please Wait",null, true, true);
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
                        newsData.setFields2(c.getString("MachineName"));
                        newsData.setFields3(c.getString("xIsCancel"));
                        results.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.listView);
                    lv1.setAdapter(new Aux2Adapter( WashMachineActivity.this, results));

                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Object o = lv1.getItemAtPosition(position);
                            Response_Aux newsData = (Response_Aux) o;
                            tId.setText(newsData.getFields1());
                            tName.setText(newsData.getFields2());
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
                String result = ruc.sendPostRequest(iFt.getUrlWashMachine(),data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Search );
    }

    public void setData(String sel,String xName,String xIsCancel,String xId,String xUrl) {
        class ListData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(WashMachineActivity.this, "Please Wait",null, true, true);
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
                String result = ruc.sendPostRequest( params[4],data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( sel,xName,xIsCancel,xId,xUrl );
    }


    @Override
    public void onBackPressed() {
        this.finish();
    }


}