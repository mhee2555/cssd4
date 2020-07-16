package com.phc.cssd.master_data;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.R;
import com.phc.cssd.adapter.Adapter_WashProgram_Item;
import com.phc.cssd.adapter.AuxSterileProgramItem;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.properties.Response_Item_Washprogram;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class WashProgramActivity extends AppCompatActivity {

    ArrayList<Response_Item_Washprogram> ArrayItem = new ArrayList<Response_Item_Washprogram>();
    ArrayList<Response_Aux> ArrayProgram = new ArrayList<Response_Aux>();
    EditText editsearch;
    Button switchview;
    Button switchview2;
    Button btnsave;
    ImageView backbtn;
    CheckBox cball;

    ListView list_item;
    ListView list_program;

    String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_wash_program);

        setTitle("เครื่องมือและโปรแกรมฆ่าเชื้อ");
        getSupportActionBar().hide();

        editsearch = (EditText) findViewById(R.id.edit_search);
        switchview = (Button) findViewById(R.id.button_process);
        switchview2 = (Button) findViewById(R.id.button_program);
        btnsave = (Button) findViewById(R.id.button_save);
        backbtn = (ImageView) findViewById(R.id.imageBack);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WashProgramActivity.this, WashProcessActivity.class);
                startActivity(intent);
                finish();
            }
        });


        editsearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                ListData(editsearch.getText().toString());
                return false;
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCountselected().equals("0")){
                    LinkProcess("",getCountselected(),editsearch.getText().toString());
                }else{
                    LinkProcess(getSelectedcheckbox(),getCountselected(),editsearch.getText().toString());
                }
                ListData_Process(editsearch.getText().toString());
                cball.setChecked(false);
            }
        });

        cball = (CheckBox) findViewById(R.id.chx_all);
        cball.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleCheckboxAll_link(cball.isChecked());
            }
        });

    }

    private void toggleCheckboxAll_link(Boolean temp) {
        for (int i=0;i<ArrayProgram.size();i++){
            ArrayProgram.get(i).setIs_Check(temp);
        }
        final ListView lv1 = (ListView) findViewById(R.id.list_program);
        lv1.setAdapter(new AuxSterileProgramItem( WashProgramActivity.this, ArrayProgram));
    }

    public String getSelectedcheckbox(){
        String Arraysel = "";
        for (int i=0;i<ArrayProgram.size();i++){
            if(i<ArrayProgram.size()) {
                if(ArrayProgram.get(i).isIs_Check()) {
                    Arraysel += ArrayProgram.get(i).getFields2() + ",";
                }
            }
        }
        Arraysel = Arraysel.substring(0, Arraysel.length() - 1);
        Log.d("Sel", Arraysel);
        return Arraysel;
    }

    public String getCountselected(){
        int count = 0;
        for (int i=0;i<ArrayProgram.size();i++){
            if(i<ArrayProgram.size()) {
                if(ArrayProgram.get(i).isIs_Check()) {
                    count++;
                }
            }
        }
        Log.d("SEL", count+"");
        return String.valueOf(count);
    }

    public void LinkProcess(String SelectedCB,String Countn,String itemcode) {
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
                            Toast.makeText(WashProgramActivity.this, "ผูกสำเร็จ", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(WashProgramActivity.this, "ผูกล้มเหลว", Toast.LENGTH_SHORT).show();
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
                data.put("Itemcode",params[2]);
                String result = ruc.sendPostRequest(iFt.reCreateWashProgram_Item(),data);
                return  result;
            }
        }

        LinkProcess ru = new LinkProcess();
        ru.execute(SelectedCB,Countn,itemcode);
    }

    public void ListData(String Search) {
        class ListData extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Item_Washprogram newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    ArrayItem.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Item_Washprogram();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.settItemCode(c.getString("tItemCode"));
                        newsData.settName(c.getString("tName"));
                        newsData.settLife(c.getString("tLife"));
                        ArrayItem.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.list_item);
                    lv1.setAdapter(new Adapter_WashProgram_Item( WashProgramActivity.this, ArrayItem));
                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            editsearch.setText(ArrayItem.get(position).gettItemCode());
                            ListData_Process(ArrayItem.get(position).gettItemCode());
                            cball.setChecked(false);
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
                String result = ruc.sendPostRequest(iFt.searchItem_Washprogram(),data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Search );
    }

    public void ListData_Process(String Itemcode) {
        class ListData_Process extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(WashProgramActivity.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    ArrayProgram.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields2(c.getString("Id"));
                        newsData.setFields1(c.getString("WashProcessID"));
                        if(c.getString("checked").equals("true")){
                            newsData.setIs_Check(true);
                        }
                        ArrayProgram.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.list_program);
                    lv1.setAdapter(new AuxSterileProgramItem( WashProgramActivity.this, ArrayProgram));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Itemcode",params[0]);
                Log.d("itemcode", data + "");
                String result = ruc.sendPostRequest(iFt.getListProgram_Item_Wash(),data);
                Log.d("itemcode", result + "");
                return  result;
            }
        }

        ListData_Process ru = new ListData_Process();
        ru.execute( Itemcode );
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
