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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrganizationActivity extends AppCompatActivity {
    ArrayList<Response_Aux> results = new ArrayList<Response_Aux>();
    ArrayList<String> arrsticker = new ArrayList<String>();
    private static final String TAG_RESULTS="result";
    JSONArray setRs = null;
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    Boolean chkUpdate = false;

    TextView tId;
    EditText tName;
    EditText tTel;
    EditText tEmail;
    Spinner tSticker;
    EditText tAddress;
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
        setContentView(R.layout.setting_organization);
        setTitle("องค์กร");
        getSupportActionBar().hide();

        tId = (TextView) findViewById(R.id.tId);
        tName = (EditText) findViewById(R.id.editName);
        tSearch = (EditText) findViewById(R.id.editSearch);
        tTel = (EditText) findViewById(R.id.editTel);
        tEmail = (EditText) findViewById(R.id.editEmail);
        tSticker = (Spinner) findViewById(R.id.editSticker);
        tAddress = (EditText) findViewById(R.id.editAddress);

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
                if(emailValidator(tEmail.getText().toString())){
                    if(tId.getText().toString().equals("")){
                        setData("0",tName.getText()+"",tId.getText()+"",tTel.getText()+"",tEmail.getText()+"",tSticker.getSelectedItem().toString()+"",tAddress.getText()+"",iFt.setUrlOrganization());
                        ListData(tName.getText()+"");
                        chkUpdate=false;
                        toggleChange();
                    }else{
                        setData("1",tName.getText()+"",tId.getText()+"",tTel.getText()+"",tEmail.getText()+"",tSticker.getSelectedItem().toString()+"",tAddress.getText()+"",iFt.setUrlOrganization());
                        ListData(tName.getText()+"");
                        chkUpdate=false;
                        toggleChange();
                    }

                }else{
                    Toast.makeText(OrganizationActivity.this, "กรุณากรอกอีเมล์ให้ถูกต้อง", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chkUpdate=false;
                tId.setText("");
                tName.setText("");
                tTel.setText("");
                tEmail.setText("");
                tSticker.setSelection(0);
                tAddress.setText("");
            }
        });

        btDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!tId.getText().toString().equals("")) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(OrganizationActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(OrganizationActivity.this);
                    }
                    builder.setTitle("การลบข้อมูลของคุณ อาจมีผลกระทบกับหลายส่วนของโปรแกรม")
                            .setMessage("คุณต้องการลบข้อมูลนี้ใช่หรือไม่?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    setData("2", tName.getText() + "", tId.getText() + "", tTel.getText() + "", tEmail.getText() + "", tSticker.getSelectedItem().toString() + "", tAddress.getText() + "", iFt.setUrlOrganization());
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

        SpinnerDataSticker("AutoClave Sticker");
    }

    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
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

    public void SpinnerDataSticker(final String sel) {
        class SpinnerDataSticker extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    arrsticker.clear();
                    arrsticker.add("-");
                    String Depsel = "";
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        arrsticker.add(c.getString("LabelName"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(OrganizationActivity.this,android.R.layout.simple_spinner_dropdown_item,arrsticker);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    tSticker.setAdapter(adapter);
                    int spinnerPosition = adapter.getPosition(sel);
                    tSticker.setSelection(spinnerPosition);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                String result = ruc.sendPostRequest(iFt.get_Spinner_sticker(),data);
                return  result;
            }
        }
        SpinnerDataSticker ru = new SpinnerDataSticker();
        ru.execute();
    }

    public void ListData(String Search) {
        class ListData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(OrganizationActivity.this, "Please Wait",null, true, true);
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
                        newsData.setFields3(c.getString("xTel"));
                        newsData.setFields4(c.getString("xEmail"));
                        newsData.setFields5(c.getString("StickerType"));
                        newsData.setFields6(c.getString("Address"));
                        results.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.listView);
                    lv1.setAdapter(new Aux6Adapter( OrganizationActivity.this, results));

                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Object o = lv1.getItemAtPosition(position);
                            Response_Aux newsData = (Response_Aux) o;
                            tId.setText(newsData.getFields1());
                            tName.setText(newsData.getFields2());
                            tTel.setText(newsData.getFields3());
                            tEmail.setText(newsData.getFields4());
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(OrganizationActivity.this,android.R.layout.simple_spinner_dropdown_item,arrsticker);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            tSticker.setAdapter(adapter);
                            int spinnerPosition = adapter.getPosition(newsData.getFields5());
                            tSticker.setSelection(spinnerPosition);
                            tAddress.setText(newsData.getFields6());
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
                String result = ruc.sendPostRequest(iFt.getUrlOrganization(),data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Search );
    }

    public void setData(String sel,String xName,String xId,String xTel,String xEmail,String xSticker,String xAddress,String xUrl) {
        class ListData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(OrganizationActivity.this, "Please Wait",null, true, true);
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
                data.put("xId",params[2]);
                data.put("xTel",params[3]);
                data.put("xEmail",params[4]);
                data.put("xStickerType",params[5]);
                data.put("xAddress",params[6]);
                Log.d("data: ", data+"");
                String result = ruc.sendPostRequest( params[7],data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( sel,xName,xId,xTel,xEmail,xSticker,xAddress,xUrl );
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
