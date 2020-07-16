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
import com.phc.cssd.adapter.Aux3Adapter;
import com.phc.cssd.adapter.AuxSterileMachine;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SterilemachineActivity extends AppCompatActivity {
    ArrayList<Response_Aux> results = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> machine = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> program = new ArrayList<Response_Aux>();
    ArrayList<String> arrtype = new ArrayList<String>();
    ArrayList<String> arrprogram = new ArrayList<String>();
    private static final String TAG_RESULTS="result";
    JSONArray setRs = null;
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    Boolean chkUpdate = false;


    ListView lvMachine;
    TextView tID;
    EditText tMacName;
    EditText sterileProID;
    EditText tSearch;
    CheckBox cball;
    CheckBox cball2;

    ImageView toggle;
    LinearLayout Li1;
    LinearLayout Li2;
    LinearLayout Li3;
    LinearLayout Li4;
    Spinner type;
    Spinner machinespn;

    Button btMaster;
    Button btLink;
    Button btLink2;
    Button btAdd;
    Button btSearch;
    Button btDel;
    Button btSave;
    Button btPrint;
    Button btSel;
    Button btlinkSave;
    Button btSel2;
    Button btlinkSave2;

    ImageView backbtn;
    boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("เครื่อง Sterile");
        getSupportActionBar().hide();

        setContentView(R.layout.setting_sterilemachine);
        tID = (TextView) findViewById(R.id.tId);
        tMacName = (EditText) findViewById(R.id.tMacName);
        sterileProID = (EditText) findViewById(R.id.sterileProID);
        tSearch = (EditText) findViewById(R.id.editSearch);

        Li1 = (LinearLayout) findViewById(R.id.Li1);
        Li1.setVisibility(View.VISIBLE);
        Li2 = (LinearLayout) findViewById(R.id.Li2);
        Li3 = (LinearLayout) findViewById(R.id.Li3);
        Li4 = (LinearLayout) findViewById(R.id.Li4);
        toggle = (ImageView) findViewById(R.id.imageUD);
        backbtn = (ImageView) findViewById(R.id.imageBack);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btMaster = (Button) findViewById(R.id.button_master);
        btMaster.setEnabled(false);
        btMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Li1.setVisibility(View.VISIBLE);
                Li2.setVisibility(View.GONE);
                Li3.setVisibility(View.GONE);
                Li4.setVisibility(View.GONE);
                btMaster.setEnabled(false);
                btLink.setEnabled(true);
                btLink2.setEnabled(true);
                toggle.setVisibility(View.VISIBLE);
                btMaster.setBackgroundResource(R.drawable.tab_master_cyanblue);
                btLink.setBackgroundResource(R.drawable.tab_master_cyanblue_noselect);
                btLink2.setBackgroundResource(R.drawable.tab_master_cyanblue_noselect);
                btMaster.setTextColor(Color.WHITE);
                btLink.setTextColor(Color.DKGRAY);
                btLink2.setTextColor(Color.DKGRAY);
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
                Li4.setVisibility(View.GONE);
                btLink.setEnabled(false);
                btLink2.setEnabled(true);
                btMaster.setEnabled(true);
                toggle.setVisibility(View.GONE);
                btMaster.setBackgroundResource(R.drawable.tab_master_cyanblue_noselect);
                btLink.setBackgroundResource(R.drawable.tab_master_cyanblue);
                btLink2.setBackgroundResource(R.drawable.tab_master_cyanblue_noselect);
                btMaster.setTextColor(Color.DKGRAY);
                btLink.setTextColor(Color.WHITE);
                btLink2.setTextColor(Color.DKGRAY);
            }
        });
        btLink2 = (Button) findViewById(R.id.button_link2);
        btLink2.setEnabled(true);
        btLink2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Li1.setVisibility(View.GONE);
                Li2.setVisibility(View.GONE);
                Li3.setVisibility(View.GONE);
                Li4.setVisibility(View.VISIBLE);
                btLink.setEnabled(true);
                btLink2.setEnabled(false);
                btMaster.setEnabled(true);
                toggle.setVisibility(View.GONE);
                btMaster.setBackgroundResource(R.drawable.tab_master_cyanblue_noselect);
                btLink.setBackgroundResource(R.drawable.tab_master_cyanblue_noselect);
                btLink2.setBackgroundResource(R.drawable.tab_master_cyanblue);
                btMaster.setTextColor(Color.DKGRAY);
                btLink.setTextColor(Color.DKGRAY);
                btLink2.setTextColor(Color.WHITE);
            }
        });

        btAdd = (Button) findViewById(R.id.b_Add);
        btSearch = (Button) findViewById(R.id.b_txt);
        btDel = (Button) findViewById(R.id.b_Del);
        btSave = (Button) findViewById(R.id.b_Save);
        btPrint = (Button) findViewById(R.id.b_Print);
        btSel = (Button) findViewById(R.id.button_select);
        btlinkSave = (Button) findViewById(R.id.button_save);
        btSel2 = (Button) findViewById(R.id.button_select_2);
        btlinkSave2 = (Button) findViewById(R.id.button_save_2);
        btlinkSave.setEnabled(false);
        btlinkSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getCountselected().equals("0")) {
                    Log.d("test", " count: "+getCountselected()+" type: "+type.getSelectedItem().toString());
                    if(getCountselected().equals("0")){
                        LinkProcess("",getCountselected(),type.getSelectedItem().toString());
                    }else{
                        LinkProcess(getSelectedcheckbox(),getCountselected(),type.getSelectedItem().toString());
                    }
                    ListData_Machine(type.getSelectedItem().toString());
                    btlinkSave.setEnabled(false);
                    cball.setChecked(false);
                }else{
                    Toast.makeText(SterilemachineActivity.this,"กรุณาเลือกข้อมูลอย่างน้อย 1 รายการ",Toast.LENGTH_LONG).show();
                }
            }
        });
        btlinkSave2.setEnabled(false);
        btlinkSave2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getCountselected2().equals("0")) {
                    Log.d("test", " count: "+getCountselected2()+" machinespn: "+machinespn.getSelectedItem().toString());
                    if(getCountselected2().equals("0")){
                        LinkProgram("",getCountselected2(),machinespn.getSelectedItem().toString());
                    }else{
                        LinkProgram(getSelectedcheckbox2(),getCountselected2(),machinespn.getSelectedItem().toString());
                    }
                    ListData_Machine(machinespn.getSelectedItem().toString());
                    btlinkSave2.setEnabled(false);
                    cball2.setChecked(false);
                }else{
                    Toast.makeText(SterilemachineActivity.this,"กรุณาเลือกข้อมูลอย่างน้อย 1 รายการ",Toast.LENGTH_LONG).show();
                }
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
                String IsCancel = "0";
                if(chkUpdate) {
                    setData("1",tID.getText()+"",tMacName.getText()+"",sterileProID.getText()+"",IsCancel,iFt.setsterilemachine());
                    ListData(tSearch.getText()+"");
                }else {
                    setData("0",tID.getText()+"",tMacName.getText()+"",sterileProID.getText()+"",IsCancel,iFt.setsterilemachine());
                    ListData(tSearch.getText()+"");
                }
                chkUpdate=false;
                toggleChange();
                Spinnerprogram();
                Spinnertype();
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chkUpdate=false;
                tID.setText("");
                tMacName.setText("");
                sterileProID.setText("");

            }
        });

        btDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!tID.getText().toString().equals("")) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(SterilemachineActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(SterilemachineActivity.this);
                    }
                    builder.setTitle("การลบข้อมูลของคุณ อาจมีผลกระทบกับหลายส่วนของโปรแกรม")
                            .setMessage("คุณต้องการลบข้อมูลนี้ใช่หรือไม่?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    setData("2",tID.getText()+"",tMacName.getText()+"",sterileProID.getText()+"","1",iFt.setsterilemachine());
                                    ListData("%");
                                    toggleChange();
                                    Spinnerprogram();
                                    Spinnertype();
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

        btSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!type.getSelectedItem().toString().equals("-")){
                    ListData_Machine(type.getSelectedItem().toString());
                    btlinkSave.setEnabled(true);
                }else{
                    machine.clear();
                    final ListView lv1 = (ListView) findViewById(R.id.list_machine);
                    lv1.setAdapter(new AuxSterileMachine( SterilemachineActivity.this, machine));
                    Toast.makeText(SterilemachineActivity.this, "กรุณาเลือกประเภทการฆ่าเชื้อ", Toast.LENGTH_SHORT).show();
                    btlinkSave.setEnabled(false);
                }
            }
        });

        btSel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!machinespn.getSelectedItem().toString().equals("-")){
                    ListData_Program(machinespn.getSelectedItem().toString());
                    btlinkSave2.setEnabled(true);
                }else{
                    program.clear();
                    final ListView lv1 = (ListView) findViewById(R.id.list_program);
                    lv1.setAdapter(new AuxSterileMachine( SterilemachineActivity.this, program));
                    Toast.makeText(SterilemachineActivity.this, "กรุณาเลือกเครื่องฆ่าเชื้อ", Toast.LENGTH_SHORT).show();
                    btlinkSave2.setEnabled(false);
                }
            }
        });

        type = (Spinner) findViewById(R.id.spinner_type);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                machine.clear();
                final ListView lv1 = (ListView) findViewById(R.id.list_machine);
                lv1.setAdapter(new AuxSterileMachine( SterilemachineActivity.this, machine));
                btlinkSave.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
        Spinnertype();

        machinespn = (Spinner) findViewById(R.id.spinner_machine);
        machinespn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                program.clear();
                final ListView lv1 = (ListView) findViewById(R.id.list_program);
                lv1.setAdapter(new AuxSterileMachine( SterilemachineActivity.this, program));
                btlinkSave2.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
        Spinnerprogram();

        cball = (CheckBox) findViewById(R.id.chx_all);
        cball.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               toggleCheckboxAll_link(cball.isChecked());
            }
        });

        cball2 = (CheckBox) findViewById(R.id.chx_all_2);
        cball2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleCheckboxAll_link2(cball2.isChecked());
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

    private void toggleCheckboxAll_link(Boolean temp) {
        for (int i=0;i<machine.size();i++){
            machine.get(i).setIs_Check(temp);
        }
        final ListView lv1 = (ListView) findViewById(R.id.list_machine);
        lv1.setAdapter(new AuxSterileMachine( SterilemachineActivity.this, machine));
    }

    private void toggleCheckboxAll_link2(Boolean temp) {
        for (int i=0;i<program.size();i++){
            program.get(i).setIs_Check(temp);
        }
        final ListView lv1 = (ListView) findViewById(R.id.list_program);
        lv1.setAdapter(new AuxSterileMachine( SterilemachineActivity.this, program));
    }

    public String getSelectedcheckbox(){
        String Arraysel = "";
        for (int i=0;i<machine.size();i++){
            if(i<machine.size()) {
                if(machine.get(i).isIs_Check()) {
                    Arraysel += machine.get(i).getFields2() + ",";
                }
            }
        }
        Arraysel = Arraysel.substring(0, Arraysel.length() - 1);
        return Arraysel;
    }

    public String getCountselected(){
        int count = 0;
        for (int i=0;i<machine.size();i++){
            if(i<machine.size()) {
                if(machine.get(i).isIs_Check()) {
                    count++;
                }
            }
        }
        return String.valueOf(count);
    }

    public String getSelectedcheckbox2(){
        String Arraysel = "";
        for (int i=0;i<program.size();i++){
            if(i<program.size()) {
                if(program.get(i).isIs_Check()) {
                    Arraysel += program.get(i).getFields2() + ",";
                }
            }
        }
        Arraysel = Arraysel.substring(0, Arraysel.length() - 1);
        return Arraysel;
    }

    public String getCountselected2(){
        int count = 0;
        for (int i=0;i<program.size();i++){
            if(i<program.size()) {
                if(program.get(i).isIs_Check()) {
                    count++;
                }
            }
        }
        return String.valueOf(count);
    }

    public void Spinnerprogram() {
        class Spinnerprogram extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    arrprogram.clear();
                    arrprogram.add("-");
                    String Depsel = "";
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        arrprogram.add(c.getString("MachineName"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SterilemachineActivity.this,android.R.layout.simple_spinner_dropdown_item,arrprogram);
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
                String result = ruc.sendPostRequest(iFt.getSpinner_machine(),data);
                return  result;
            }
        }

        Spinnerprogram ru = new Spinnerprogram();
        ru.execute();
    }

    public void Spinnertype() {
        class Spinnertype extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    arrtype.clear();
                    arrtype.add("-");
                    String Depsel = "";
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        arrtype.add(c.getString("SterileName2"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SterilemachineActivity.this,android.R.layout.simple_spinner_dropdown_item,arrtype);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    type.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Pass","");
                String result = ruc.sendPostRequest(iFt.getSpinner_sterileprocess(),data);
                return  result;
            }
        }

        Spinnertype ru = new Spinnertype();
        ru.execute();
    }

    public void LinkProgram(String SelectedCB,String Countn,String typeprocess) {
        class LinkProgram extends AsyncTask<String, Void, String> {

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
                            Toast.makeText(SterilemachineActivity.this, "ผูกสำเร็จ", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SterilemachineActivity.this, "ผูกล้มเหลว", Toast.LENGTH_SHORT).show();
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
                String result = ruc.sendPostRequest(iFt.reCreateSterileMachine_program(),data);
                return  result;
            }
        }

        LinkProgram ru = new LinkProgram();
        ru.execute(SelectedCB,Countn,typeprocess);
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
                            Toast.makeText(SterilemachineActivity.this, "ผูกสำเร็จ", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SterilemachineActivity.this, "ผูกล้มเหลว", Toast.LENGTH_SHORT).show();
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
                String result = ruc.sendPostRequest(iFt.reCreateSterileMachine_process(),data);
                return  result;
            }
        }

        LinkProcess ru = new LinkProcess();
        ru.execute(SelectedCB,Countn,typeprocess);
    }

    public void ListData(String Search) {
        class ListData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SterilemachineActivity.this, "Please Wait",null, true, true);
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
                        newsData.setFields2(c.getString("xMachineName"));
                        newsData.setFields3(c.getString("xMachineName2"));
                        newsData.setFields4(c.getString("xIsCancel"));
                        results.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.listView);
                    lv1.setAdapter(new Aux3Adapter( SterilemachineActivity.this, results));

                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Object o = lv1.getItemAtPosition(position);
                            Response_Aux newsData = (Response_Aux) o;
                            tID.setText(newsData.getFields1());
                            tMacName.setText(newsData.getFields2());
                            sterileProID.setText(newsData.getFields3());
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
                String result = ruc.sendPostRequest(iFt.getsterilemachine(),data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Search );
    }

    public void ListData_Machine(String type) {
        class ListData_Machine extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SterilemachineActivity.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    machine.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields2(c.getString("xId"));
                        newsData.setFields1(c.getString("MachineName"));
                        if(c.getString("checked").equals("true")){
                            newsData.setIs_Check(true);
                        }
                        machine.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.list_machine);
                    lv1.setAdapter(new AuxSterileMachine( SterilemachineActivity.this, machine));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("type",params[0]);
                String result = ruc.sendPostRequest(iFt.getListMachine(),data);
                return  result;
            }
        }

        ListData_Machine ru = new ListData_Machine();
        ru.execute( type );
    }

    public void ListData_Program(String type) {
        class ListData_Program extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SterilemachineActivity.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    program.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields2(c.getString("xId"));
                        newsData.setFields1(c.getString("SterileName"));
                        if(c.getString("checked").equals("true")){
                            newsData.setIs_Check(true);
                        }
                        program.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.list_program);
                    lv1.setAdapter(new AuxSterileMachine( SterilemachineActivity.this, program));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("type",params[0]);
                String result = ruc.sendPostRequest(iFt.getListProgram(),data);
                return  result;
            }
        }

        ListData_Program ru = new ListData_Program();
        ru.execute( type );
    }

    public void setData(String sel,String xID,String tMacName,String tMacName2,String xIsCancel,String xUrl) {
        class ListData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SterilemachineActivity.this, "Please Wait",null, true, true);
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
                data.put("tMacName",params[2]);
                data.put("tMacName2",params[3]);
                data.put("xIsCancel",params[4]);
                //Log.d("params[3] :", params[3]);
                String result = ruc.sendPostRequest( params[5],data);
                //Log.d("result :", result);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( sel,xID,tMacName,tMacName2,xIsCancel,xUrl );
    }


    @Override
    public void onBackPressed() {
        this.finish();
    }

}
