package com.phc.cssd;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetPositionStickerActivity extends AppCompatActivity {
    private HTTPConnect httpConnect = new HTTPConnect();
    JSONArray setRs = null;
    private static final String TAG_RESULTS="result";
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();
    ArrayList<Response_Aux> stk_type = new ArrayList<Response_Aux>();

    int xItemname1 = 0;
    int yItemname1 = 0;
    int xUsagecode1 = 0;
    int yUasgecode1 = 0;
    int xPrice = 0;
    int yPrice = 0;
    int xDept1 = 0;
    int yDept1 = 0;
    int xMachine = 0;
    int yMahcine = 0;
    int xRound = 0;
    int yRound = 0;
    int xPackdate = 0;
    int yPackdate = 0;
    int xExp = 0;
    int yExp = 0;
    int xEmp1 = 0;
    int yEmp1 = 0;
    int xEmp2 = 0;
    int yEmp2 = 0;
    int xItemname2 = 0;
    int yItemname2 = 0;
    int xDept2 = 0;
    int yDept2 = 0;
    int xUsagecode2 = 0;
    int yUsagecode2 = 0;
    int xQrcode1 = 0;
    int yQrcode1 = 0;
    int xQrcode2 = 0;
    int yQrcode2 = 0;
    int xPackdate2 = 0;
    int yPackdate2 = 0;
    int xExp2 = 0;
    int yExp2 = 0;
    int xEmp3 = 0;
    int yEmp3 = 0;
    EditText ED_xItemname1;
    EditText ED_yItemname1 ;
    EditText ED_xUsagecode1 ;
    EditText ED_yUasgecode1 ;
    EditText ED_xPrice ;
    EditText ED_yPrice ;
    EditText ED_xDept1 ;
    EditText ED_yDept1 ;
    EditText ED_xMachine ;
    EditText ED_yMahcine ;
    EditText ED_xRound ;
    EditText ED_yRound ;
    EditText ED_xPackdate ;
    EditText ED_yPackdate ;
    EditText ED_xExp ;
    EditText ED_yExp ;
    EditText ED_xEmp1 ;
    EditText ED_yEmp1 ;
    EditText ED_xEmp2 ;
    EditText ED_yEmp2 ;
    EditText ED_xItemname2 ;
    EditText ED_yItemname2 ;
    EditText ED_xDept2 ;
    EditText ED_yDept2 ;
    EditText ED_xUsagecode2 ;
    EditText ED_yUsagecode2 ;
    EditText ED_xQrcode1 ;
    EditText ED_yQrcode1 ;
    EditText ED_xQrcode2 ;
    EditText ED_yQrcode2 ;
    EditText ED_xPackdate2 ;
    EditText ED_yPackdate2 ;
    EditText ED_xExp2 ;
    EditText ED_yExp2 ;
    EditText ED_xEmp3 ;
    EditText ED_yEmp3 ;
    Spinner spn_sticker;
    Button bt_save;
    Button bt_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_position_sticker);
        initialize();
    }

    public void initialize(){
        ED_xItemname1= (EditText) findViewById(R.id.ED_xItemname1);
        ED_yItemname1= (EditText) findViewById(R.id.ED_yItemname1);
        ED_xUsagecode1= (EditText) findViewById(R.id.ED_xUsagecode1);
        ED_yUasgecode1= (EditText) findViewById(R.id.ED_yUasgecode1);
        ED_xPrice= (EditText) findViewById(R.id.ED_xPrice);
        ED_yPrice= (EditText) findViewById(R.id.ED_yPrice);
        ED_xDept1= (EditText) findViewById(R.id.ED_xDept1);
        ED_yDept1= (EditText) findViewById(R.id.ED_yDept1);
        ED_xMachine= (EditText) findViewById(R.id.ED_xMachine);
        ED_yMahcine= (EditText) findViewById(R.id.ED_yMahcine);
        ED_xRound= (EditText) findViewById(R.id.ED_xRound);
        ED_yRound= (EditText) findViewById(R.id.ED_yRound);
        ED_xPackdate= (EditText) findViewById(R.id.ED_xPackdate);
        ED_yPackdate= (EditText) findViewById(R.id.ED_yPackdate);
        ED_xExp= (EditText) findViewById(R.id.ED_xExp);
        ED_yExp= (EditText) findViewById(R.id.ED_yExp);
        ED_xEmp1= (EditText) findViewById(R.id.ED_xEmp1);
        ED_yEmp1= (EditText) findViewById(R.id.ED_yEmp1);
        ED_xEmp2= (EditText) findViewById(R.id.ED_xEmp2);
        ED_yEmp2= (EditText) findViewById(R.id.ED_yEmp2);
        ED_xItemname2= (EditText) findViewById(R.id.ED_xItemname2);
        ED_yItemname2= (EditText) findViewById(R.id.ED_yItemname2);
        ED_xDept2= (EditText) findViewById(R.id.ED_xDept2);
        ED_yDept2= (EditText) findViewById(R.id.ED_yDept2);
        ED_xUsagecode2= (EditText) findViewById(R.id.ED_xUsagecode2);
        ED_yUsagecode2= (EditText) findViewById(R.id.ED_yUsagecode2);
        ED_xQrcode1= (EditText) findViewById(R.id.ED_xQrcode1);
        ED_yQrcode1= (EditText) findViewById(R.id.ED_yQrcode1);
        ED_xQrcode2= (EditText) findViewById(R.id.ED_xQrcode2);
        ED_yQrcode2= (EditText) findViewById(R.id.ED_yQrcode2);
        ED_xPackdate2= (EditText) findViewById(R.id.ED_xPackdate2);
        ED_yPackdate2= (EditText) findViewById(R.id.ED_yPackdate2);
        ED_xExp2= (EditText) findViewById(R.id.ED_xExp2);
        ED_yExp2= (EditText) findViewById(R.id.ED_yExp2);
        ED_xEmp3= (EditText) findViewById(R.id.ED_xEmp3);
        ED_yEmp3= (EditText) findViewById(R.id.ED_yEmp3);
        spn_sticker = (Spinner) findViewById(R.id.spn_sticker);
        bt_save = (Button) findViewById(R.id.bt_save);
        bt_back = (Button) findViewById(R.id.bt_back);
        get_sticker();

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_sticker(stk_type.get(spn_sticker.getSelectedItemPosition()).getFields1());
                Get_label_sticker(stk_type.get(spn_sticker.getSelectedItemPosition()).getFields1());
                //Toast.makeText(SetPositionStickerActivity.this, "บันทึกแล้ว!!", Toast.LENGTH_SHORT).show();
            }
        });

        spn_sticker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Get_label_sticker(stk_type.get(spn_sticker.getSelectedItemPosition()).getFields1());
                Toast.makeText(SetPositionStickerActivity.this,stk_type.get(spn_sticker.getSelectedItemPosition()).getFields2() , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent intent = new Intent(PayoutActivity.this, Menu.class);
                //startActivity(intent);
                finish();
            }
        });
       /* ED_xItemname1.setFilters(new InputFilter[] { filter });
        ED_yItemname1.setFilters(new InputFilter[] { filter });
        ED_xUsagecode1.setFilters(new InputFilter[] { filter });
        ED_yUasgecode1.setFilters(new InputFilter[] { filter });
        ED_xPrice.setFilters(new InputFilter[] { filter });
        ED_yPrice.setFilters(new InputFilter[] { filter });
        ED_xDept1.setFilters(new InputFilter[] { filter });
        ED_yDept1.setFilters(new InputFilter[] { filter });
        ED_xMachine.setFilters(new InputFilter[] { filter });
        ED_yMahcine.setFilters(new InputFilter[] { filter });
        ED_xRound.setFilters(new InputFilter[] { filter });
        ED_yRound.setFilters(new InputFilter[] { filter });
        ED_xPackdate.setFilters(new InputFilter[] { filter });
        ED_yPackdate.setFilters(new InputFilter[] { filter });
        ED_xExp.setFilters(new InputFilter[] { filter });
        ED_yExp.setFilters(new InputFilter[] { filter });
        ED_xEmp1.setFilters(new InputFilter[] { filter });
        ED_yEmp1.setFilters(new InputFilter[] { filter });
        ED_xEmp2.setFilters(new InputFilter[] { filter });
        ED_yEmp2.setFilters(new InputFilter[] { filter });
        ED_xItemname2.setFilters(new InputFilter[] { filter });
        ED_yItemname2.setFilters(new InputFilter[] { filter });
        ED_xDept2.setFilters(new InputFilter[] { filter });
        ED_yDept2.setFilters(new InputFilter[] { filter });
        ED_xUsagecode2.setFilters(new InputFilter[] { filter });
        ED_yUsagecode2.setFilters(new InputFilter[] { filter });
        ED_xQrcode1.setFilters(new InputFilter[] { filter });
        ED_yQrcode1.setFilters(new InputFilter[] { filter });
        ED_xQrcode2.setFilters(new InputFilter[] { filter });
        ED_yQrcode2.setFilters(new InputFilter[] { filter });
        ED_xPackdate2.setFilters(new InputFilter[] { filter });
        ED_yPackdate2.setFilters(new InputFilter[] { filter });
        ED_xExp2.setFilters(new InputFilter[] { filter });
        ED_yExp2.setFilters(new InputFilter[] { filter });
        ED_xEmp3.setFilters(new InputFilter[] { filter });
        ED_yEmp3.setFilters(new InputFilter[] { filter });*/
    }



    public void Get_label_sticker(String xID) {
        class set_label_sticker extends AsyncTask<String, Void, String> {
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try{
                    JSONObject jsonObj = new JSONObject(s);
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        /*xItemname1=c.getInt("xItemname1");
                        yItemname1=c.getInt("yItemname1");
                        xUsagecode1=c.getInt("xUsagecode1");
                        yUasgecode1=c.getInt("yUasgecode1");
                        xPrice=c.getInt("xPrice");
                        yPrice=c.getInt("yPrice");
                        xDept1=c.getInt("xDept1");
                        yDept1=c.getInt("yDept1");
                        xMachine=c.getInt("xMachine");
                        yMahcine=c.getInt("yMahcine");
                        xRound=c.getInt("xRound");
                        yRound=c.getInt("yRound");
                        xPackdate=c.getInt("xPackdate");
                        yPackdate=c.getInt("yPackdate");
                        xExp=c.getInt("xExp");
                        yExp=c.getInt("yExp");
                        xEmp1=c.getInt("xEmp1");
                        yEmp1=c.getInt("yEmp1");
                        xEmp2=c.getInt("xEmp2");
                        yEmp2=c.getInt("yEmp2");
                        xItemname2=c.getInt("xItemname2");
                        yItemname2=c.getInt("yItemname2");
                        xDept2=c.getInt("xDept2");
                        yDept2=c.getInt("yDept2=");
                        xUsagecode2=c.getInt("xUsagecode2");
                        yUsagecode2=c.getInt("yUsagecode2");
                        xQrcode1=c.getInt("xQrcode1");
                        yQrcode1=c.getInt("yQrcode1");
                        xQrcode2=c.getInt("xQrcode2");
                        yQrcode2=c.getInt("yQrcode2");
                        xPackdate2=c.getInt("xPackdate2");
                        yPackdate2=c.getInt("yPackdate2");
                        xExp2=c.getInt("xExp2");
                        yExp2=c.getInt("yExp2");
                        xEmp3=c.getInt("xEmp3");
                        yEmp3=c.getInt("yEmp3");*/
                          ED_xItemname1.setText(c.getString("xItemname1"));
                          ED_yItemname1.setText(c.getString("yItemname1"));
                          ED_xUsagecode1.setText(c.getString("xUsagecode1")) ;
                          ED_yUasgecode1 .setText(c.getString("yUasgecode1"));
                          ED_xPrice .setText(c.getString("xPrice"));
                          ED_yPrice.setText(c.getString("yPrice") );
                          ED_xDept1 .setText(c.getString("xDept1"));
                          ED_yDept1 .setText(c.getString("yDept1"));
                          ED_xMachine .setText(c.getString("xMachine"));
                          ED_yMahcine .setText(c.getString("yMahcine"));
                          ED_xRound.setText(c.getString("xRound")) ;
                          ED_yRound .setText(c.getString("yRound"));
                          ED_xPackdate .setText(c.getString("xPackdate"));
                          ED_yPackdate.setText(c.getString("yPackdate") );
                          ED_xExp.setText(c.getString("xExp")) ;
                          ED_yExp .setText(c.getString("yExp"));
                          ED_xEmp1.setText(c.getString("xEmp1")) ;
                          ED_yEmp1.setText(c.getString("yEmp1")) ;
                          ED_xEmp2 .setText(c.getString("xEmp2"));
                          ED_yEmp2.setText(c.getString("yEmp2")) ;
                          ED_xItemname2.setText(c.getString("xItemname2")) ;
                          ED_yItemname2.setText(c.getString("yItemname2")) ;
                          ED_xDept2.setText(c.getString("xDept2")) ;
                          ED_yDept2.setText(c.getString("yDept2")) ;
                          ED_xUsagecode2.setText(c.getString("xUsagecode2")) ;
                          ED_yUsagecode2.setText(c.getString("yUsagecode2")) ;
                          ED_xQrcode1.setText(c.getString("xQrcode1")) ;
                          ED_yQrcode1.setText(c.getString("yQrcode1")) ;
                          ED_xQrcode2.setText(c.getString("xQrcode2")) ;
                          ED_yQrcode2.setText(c.getString("yQrcode2")) ;
                          ED_xPackdate2.setText(c.getString("xPackdate2")) ;
                          ED_yPackdate2.setText(c.getString("yPackdate2")) ;
                          ED_xExp2.setText(c.getString("xExp2")) ;
                          ED_yExp2.setText(c.getString("yExp2")) ;
                          ED_xEmp3.setText(c.getString("xEmp3")) ;
                          ED_yEmp3.setText(c.getString("yEmp3")) ;
                        //Log.d("ED_DEPTNAME",c.getString("ED_DepName") );
                    }
                }catch (JSONException e){
                    Log.d("XXX", "Error on get dept" );
                }
            }
            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xID",params[0]);
                Log.d("get_1 : ", data+"");
                String result = ruc.sendPostRequest(getUrl.xUrl+"p/sticker/get_label_stickerXY.php",data);


                Log.d("get_2 : ", result);
                return result;
            }
        }
        set_label_sticker ru = new set_label_sticker();
        ru.execute(xID);
    }

    public void get_sticker() {
        class get_sticker extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                List<String> list = new ArrayList<String>();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    stk_type.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields1(c.getString("xID"));
                        newsData.setFields2(c.getString("xLabelName"));
                        stk_type.add( newsData );
                        list.add(c.getString("xLabelName"));
                    }

                    ArrayAdapter<String> SpinnerList = new ArrayAdapter<String>(SetPositionStickerActivity.this,
                            android.R.layout.simple_dropdown_item_1line, list);
                    spn_sticker.setAdapter( SpinnerList );
                    Get_label_sticker(stk_type.get(spn_sticker.getSelectedItemPosition()).getFields1());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                String result = ruc.sendPostRequest(getUrl.xUrl+"p/sticker/get_stickerlabeltype.php",data);
                return  result;
            }
        }
        get_sticker ru = new get_sticker();
        ru.execute();
    }

    public void save_sticker(String xID) {
        class save_sticker extends AsyncTask<String, Void, String> {
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try{
                    JSONObject jsonObj = new JSONObject(s);
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        Log.d("flag", c.getString("flag") );
                        if(c.getString("flag").equals("true")){
                            Toast.makeText(SetPositionStickerActivity.this, "บันทึกสำเร็จ!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SetPositionStickerActivity.this, "บันทึกไม่สำเร็จ!!", Toast.LENGTH_SHORT).show();
                        }

                    }
                    //etxt_dept.setText(ED_DeptName);
                    //getlistdata(deptsp_id,edittext.getText().toString(),"");
                }catch (JSONException e){
                    Log.d("XXX", "Error on get dept" );
                }
            }
            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xID",params[0]);
                data.put("xItemname1",params[1]);
                data.put("yItemname1",params[2]);
                data.put("xUsagecode1",params[3]);
                data.put("yUasgecode1",params[4]);
                data.put("xPrice",params[5]);
                data.put("yPrice",params[6]);
                data.put("yDept1",params[7]);
                data.put("yDept1",params[8]);
                data.put("xMachine",params[9]);
                data.put("yMahcine",params[10]);
                data.put("xRound",params[11]);
                data.put("yRound",params[12]);
                data.put("xPackdate",params[13]);
                data.put("yPackdate",params[14]);
                data.put("xExp",params[15]);
                data.put("yExp",params[16]);
                data.put("xEmp1",params[17]);
                data.put("yEmp1",params[18]);
                data.put("xEmp2",params[19]);
                data.put("yEmp2",params[20]);
                data.put("xItemname2",params[21]);
                data.put("yItemname2",params[22]);
                data.put("xDept2",params[23]);
                data.put("yDept2",params[24]);
                data.put("xUsagecode2",params[25]);
                data.put("yUsagecode2",params[26]);
                data.put("xQrcode1",params[27]);
                data.put("yQrcode1",params[28]);
                data.put("xQrcode2",params[29]);
                data.put("yQrcode2",params[30]);
                data.put("xPackdate2",params[31]);
                data.put("yPackdate2",params[32]);
                data.put("xExp2",params[33]);
                data.put("yExp2",params[34]);
                data.put("xEmp3",params[35]);
                data.put("yEmp3",params[36]);



                Log.d("save_1 : ", data+"");
                String result = ruc.sendPostRequest(getUrl.xUrl+"p/sticker/set_stickerlabel_save.php",data);


                Log.d("save_2 : ", result);
                return result;
            }
        }
        save_sticker ru = new save_sticker();
        ru.execute( xID,
                (ED_xItemname1.getText().toString()== "" ? "0" :ED_xItemname1.getText().toString()),
                (ED_yItemname1.getText().toString()== "" ? "0" :ED_yItemname1.getText().toString()),
                ( ED_xUsagecode1.getText().toString()== "" ? "0" :ED_xUsagecode1.getText().toString()),
                ( ED_yUasgecode1.getText().toString()== "" ? "0" :ED_yUasgecode1.getText().toString()),
                ( ED_xPrice.getText().toString()== "" ? "0" :ED_xPrice.getText().toString()),
                ( ED_yPrice.getText().toString()== "" ? "0" :ED_yPrice.getText().toString()),
                ( ED_xDept1.getText().toString()== "" ? "0" :ED_xDept1.getText().toString()),
                ( ED_yDept1.getText().toString()== "" ? "0" :ED_yDept1.getText().toString()),
                (  ED_xMachine.getText().toString()== "" ? "0" :ED_xMachine.getText().toString()),
                (  ED_yMahcine.getText().toString()== "" ? "0" :ED_yMahcine.getText().toString()),
                ( ED_xRound.getText().toString()== "" ? "0" :ED_xRound.getText().toString()) ,
                (  ED_yRound.getText().toString()== "" ? "0" :ED_yRound.getText().toString()),
                (  ED_xPackdate.getText().toString()== "" ? "0" :ED_xPackdate.getText().toString()),
                ( ED_yPackdate.getText().toString()== "" ? "0" :ED_yPackdate.getText().toString()),
                (  ED_xExp.getText().toString()== "" ? "0" :ED_xExp.getText().toString()),
                (  ED_yExp.getText().toString()== "" ? "0" :ED_yExp.getText().toString()),
                (  ED_xEmp1.getText().toString()== "" ? "0" :ED_xEmp1.getText().toString()),
                (  ED_yEmp1.getText().toString()== "" ? "0" :ED_yEmp1.getText().toString()),
                (  ED_xEmp2.getText().toString()== "" ? "0" :ED_xEmp2.getText().toString()),
                (  ED_yEmp2.getText().toString()== "" ? "0" :ED_yEmp2.getText().toString()),
                (  ED_xItemname2.getText().toString()== "" ? "0" :ED_xItemname2.getText().toString()),
                (  ED_yItemname2.getText().toString()== "" ? "0" :ED_yItemname2.getText().toString()),
                ( ED_xDept2.getText().toString()== "" ? "0" :ED_xDept2.getText().toString()),
                ( ED_yDept2.getText().toString()== "" ? "0" :ED_yDept2.getText().toString()),
                ( ED_xUsagecode2.getText().toString()== "" ? "0" :ED_xUsagecode2.getText().toString()),
                (  ED_yUsagecode2.getText().toString()== "" ? "0" :ED_yUsagecode2.getText().toString()),
                ( ED_xQrcode1.getText().toString()== "" ? "0" :ED_xQrcode1.getText().toString()),
                ( ED_yQrcode1.getText().toString()== "" ? "0" :ED_yQrcode1.getText().toString()),
                ( ED_xQrcode2.getText().toString()== "" ? "0" :ED_xQrcode2.getText().toString()),
                ( ED_yQrcode2.getText().toString()== "" ? "0" :ED_yQrcode2.getText().toString()),
                ( ED_xPackdate2.getText().toString()== "" ? "0" :ED_xPackdate2.getText().toString()),
                (ED_yPackdate2.getText().toString()== "" ? "0" :ED_yPackdate2.getText().toString()),
                ( ED_xExp2.getText().toString()== "" ? "0" :ED_xExp2.getText().toString()),
                ( ED_yExp2.getText().toString()== "" ? "0" :ED_yExp2.getText().toString()),
                ( ED_xEmp3.getText().toString()== "" ? "0" :ED_xEmp3.getText().toString()),
                ( ED_yEmp3.getText().toString()== "" ? "0" :ED_yEmp3.getText().toString())
        );
    }

    /*InputFilter filter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
           *//* if(source.equals("")||source.equals(null)){
                    return "0";
            }else{
                for (int i = start; i < end; i++) {
                    if (Character.isSpaceChar(source.charAt(i))) {
                        return "0";
                    }
                }
            }*//*
            if(source.equals("")){
                return "0";
            }
            return null;
        }
    };*/


}
