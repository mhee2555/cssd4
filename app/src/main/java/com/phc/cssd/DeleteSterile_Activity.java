package com.phc.cssd;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.Delete_sterile_adapter;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class DeleteSterile_Activity extends AppCompatActivity {
    String ED_UserCode;
    private static final String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();
    Calendar myCalendar = Calendar.getInstance();
    ArrayList<Response_Aux> resultsDoc = new ArrayList<Response_Aux>();
    EditText edit_sdate;
    EditText edit_edate;
    EditText edit_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_sterile_);
        initialize();
    }

    public void initialize(){
         edit_sdate = (EditText) findViewById(R.id.edit_sdate);
         edit_edate = (EditText) findViewById(R.id.edit_edate);
        edit_search= (EditText) findViewById(R.id.edit_search);
        Bundle bd = getIntent().getExtras();
        if (bd != null){
            ED_UserCode =  bd.getString("userid");
            //Log.d("ED_UserCode : ", ED_UserCode);
        }

        getsdate();
        getedate();
        getlistdoc("",edit_sdate.toString(),edit_edate.toString());
        Button bt_search = (Button) findViewById(R.id.bt_search);
        bt_search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                getlistdoc(edit_search.getText().toString(),edit_sdate.getText().toString(),edit_edate.getText().toString());
            }
        });

    }


    public void getsdate(){

        updateSadte();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateSadte();
            }

        };

        edit_sdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DeleteSterile_Activity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateSadte() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edit_sdate.setText(sdf.format(myCalendar.getTime()));

    }


    public void getedate(){

        updateEdate();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEdate();
            }

        };

        edit_edate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DeleteSterile_Activity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateEdate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edit_edate.setText(sdf.format(myCalendar.getTime()));

    }

    public void getlistdoc(String search, final String sdate, final String edate) {
        class getlistdoc extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                // loading = ProgressDialog.show(ApproveStockActivity.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                try {

                    System.out.println("DATA = " + s);

                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultsDoc.clear();
                    //String finish = "";
                    //String count="";
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields1(c.getString("xssDoc"));
                        newsData.setFields2(c.getString("xssDate"));
                        newsData.setFields3(c.getString("xDepName2"));
                        newsData.setFields4(c.getString("xwDoc"));
                        newsData.setFields5(c.getString("xwDate"));
                        newsData.setFields6(c.getString("xwMDate"));
                        newsData.setFields7(c.getString("xUsageCode"));
                        newsData.setFields8(c.getString("xWashID"));
                        newsData.setFields9(sdate);
                        newsData.setFields10(edate);
                        newsData.setFields11((i+1)+"");
                        newsData.setFields12(c.getString("xitemname"));
                        resultsDoc.add(newsData);
                        //finish=c.getString("Finish");
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.listdelete_sterile);
                    lv1.setAdapter(new Delete_sterile_adapter(DeleteSterile_Activity.this, resultsDoc));
                   /* if(finish.equals("true")){
                        Toast.makeText(DeleteSterile_Activity.this, "บันทึกแล้ว", Toast.LENGTH_SHORT).show();
                    }*/


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Search",params[0]);
                data.put("Sdate",params[1]);
                data.put("Edate",params[2]);
                Log.d("xDocNo: ", data+"");
                String result = ruc.sendPostRequest(getUrl.xUrl+"p/get_deletedoc_sterile.php",data);
                Log.d("result finish: ", result);
                return  result;
            }
        }
        getlistdoc ru = new getlistdoc();
        ru.execute( search,sdate,edate );
    }



}
