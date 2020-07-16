package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.AdapterItemSearch_Risk;
import com.phc.cssd.properties.Response_Itemname_Search;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class dialog_search_itemname_risk extends Activity {

    ArrayList<Response_Itemname_Search> arr_search = new ArrayList<Response_Itemname_Search>();
    ListView listview;
    EditText txt_search;
    EditText tDocno;
    EditText tDocdate;
    EditText tProgram;
    EditText tMachine;
    EditText tRound;
    Button importbtn;
    LinearLayout DetailDoc;
    String bo;
    String OccDocNo;
    String OccDoc;
    String OccName;
    private ArrayList<String> list_sp = new ArrayList<String>();
    private ArrayList<String> array_occ_docno = new ArrayList<String>();
    ArrayAdapter<String> adapter_spinner;
    SearchableSpinner occ_docno;
    TextView occ_docno1;

    Intent intent;
    String doctypeno;
    String TAG_RESULTS="result";
    String B_ID;
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    private HTTPConnect httpConnect = new HTTPConnect();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search_itemname_risk);

        getBundleType();
        initialize();

        occ_docno.setVisibility(View.INVISIBLE);
        occ_docno1.setVisibility(View.INVISIBLE);

        occ_docno.setTitle("เลือกเอกสาร");
        occ_docno.setPositiveButton("");
        occ_docno.requestFocus();

    }

    private void getBundleType() {
        intent = getIntent();
        Bundle bd = getIntent().getExtras();

        try{
            if(intent.hasExtra("type")) {
                doctypeno = bd.getString("type");
            }
        }catch (NullPointerException e){
            if(!intent.hasExtra("type")) {
                doctypeno = "";
            }
        }

        B_ID = intent.getStringExtra("B_ID");

    }

    private void initialize() {
        txt_search = (EditText) findViewById(R.id.edit_search);

        occ_docno = ( SearchableSpinner ) findViewById(R.id.occ_docno);
        occ_docno1 = ( TextView ) findViewById(R.id.occ_docno1);

        txt_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            GetOccDocNo();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });


        occ_docno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!occ_docno.getSelectedItem().equals("-")){
                    ListData(txt_search.getText().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        tDocno = (EditText) findViewById(R.id.tDocno);
        tDocdate = (EditText) findViewById(R.id.tDocdate);
        tProgram = (EditText) findViewById(R.id.tProgram);
        tMachine = (EditText) findViewById(R.id.tMachine);
        tRound = (EditText) findViewById(R.id.tRound);
        importbtn = (Button) findViewById(R.id.button_import);
        importbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tDocno.getText().toString().equals("")){
                    String strtxt = concatstring();
                    GetDataitem(strtxt,tDocno.getText().toString());
                }
            }
        });
        DetailDoc = (LinearLayout) findViewById(R.id.Layout_DetailDoc);
        DetailDoc.setVisibility(View.GONE);
    }

    private String concatstring() {
        String txt ="";
        for (int i=0;i<arr_search.size();i++){
            txt += arr_search.get(i).getItemcode();
            txt += ",";
        }
        txt = txt.substring(0, txt.length() - 1);
        return txt;
    }

    public void GetOccDocNo () {
        class GetOccDocNo extends AsyncTask<String, Void, String> {
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    array_occ_docno.clear();
                    list_sp.add("-");
                    for (int i = 0; i < setRs.length(); i++) {

                        JSONObject c = setRs.getJSONObject(i);

                        if (c.getString("flage").equals("FALSE")){
                            occ_docno.setVisibility(View.INVISIBLE);
                            occ_docno1.setVisibility(View.INVISIBLE);
                            Toast.makeText(dialog_search_itemname_risk.this, "ไม่พบข้อมูล", Toast.LENGTH_SHORT).show();
                        }else if (c.getString("flage").equals("FALSE1")){
                            occ_docno.setVisibility(View.INVISIBLE);
                            occ_docno1.setVisibility(View.INVISIBLE);
                            Toast.makeText(dialog_search_itemname_risk.this, "เอกสารถูกสร้างความเสี่ยงก่อนหน้านี้แล้ว", Toast.LENGTH_SHORT).show();
                        } else {
                            occ_docno.setVisibility(View.VISIBLE);
                            occ_docno1.setVisibility(View.VISIBLE);
                            OccDoc = c.getString("DocNo");
                            OccName = c.getString("OccuranceName");
                            list_sp.add(OccDoc + "   (" + OccName + ")");
                            array_occ_docno.add(c.getString("DocNo"));
                        }

                    }
                    adapter_spinner = new ArrayAdapter<String>(dialog_search_itemname_risk.this, android.R.layout.simple_spinner_dropdown_item, list_sp);
                    adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    occ_docno.setAdapter(adapter_spinner);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("B_ID",B_ID);
                data.put("Usage",txt_search.getText().toString());
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_recall_docno_occ.php", data);
                    Log.d("BANK",result);
                    Log.d("BANK",data+"");
                }catch(Exception e){
                    e.printStackTrace();
                }

                return result;
            }
        }
        GetOccDocNo ru = new GetOccDocNo();
        ru.execute();
    }

    public void ListData(String Search) {
        class ListData extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Itemname_Search newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    arr_search.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Itemname_Search();
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")){
                            newsData.setDepName2(c.getString("DepName2"));
                            newsData.setIsstatus(c.getString("IsStatus"));
                            newsData.setItemcode(c.getString("UsageCode"));
                            newsData.setItemname(c.getString("Itemname"));
                            newsData.setIsPay(c.getString("IsPay"));
                            tDocno.setText(c.getString("DocNo"));
                            tDocdate.setText(c.getString("DocDate"));
                            tProgram.setText(c.getString("Program"));
                            tMachine.setText(c.getString("Machine"));
                            tRound.setText(c.getString("RoundNumber"));
                            arr_search.add( newsData );
                            DetailDoc.setVisibility(View.VISIBLE);
                        }else{
                            Toast.makeText(dialog_search_itemname_risk.this, "ไม่พบข้อมูล", Toast.LENGTH_SHORT).show();
                            DetailDoc.setVisibility(View.GONE);
                            tDocno.setText("");
                            tDocdate.setText("");
                            tProgram.setText("");
                            tMachine.setText("");
                            tRound.setText("");
                        }

                    }
                    final ListView lv1 = (ListView) findViewById(R.id.listview);
                    lv1.setAdapter(new AdapterItemSearch_Risk( dialog_search_itemname_risk.this, arr_search));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Search",params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.searchItem_risk(),data);
                Log.d("BANKPPP",result);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Search );
    }

    public void GetDataitem(String temp,String Docno){
        Intent intent = new Intent();
        setResult(112, intent);
        intent.putExtra("type","2");
        intent.putExtra("Docno",Docno);
        intent.putExtra("StrData",temp);
        OccDocNo = String.valueOf(occ_docno.getSelectedItem());
        String Doc = OccDocNo.substring(0,12);
        intent.putExtra("OccDocno",Doc);
        finish();
    }

    public void CheckDocTypeOcc(final String UsageCode) {
        class CheckDocTypeOcc extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        bo = c.getString("flag");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("UsageCode",UsageCode);
                String result = null;
                try {
                    result = ruc.sendPostRequest(Url.URL + "check_doc_type_occ.php", data);
                    Log.d("FNGDDF",data+"");
                    Log.d("FNGDDF",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        CheckDocTypeOcc obj = new CheckDocTypeOcc();
        obj.execute();
    }
}
