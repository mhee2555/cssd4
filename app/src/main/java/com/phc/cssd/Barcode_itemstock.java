package com.phc.cssd;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.phc.cssd.adapter.L_itemstock_Adapter;
import com.phc.cssd.function.KeyboardUtils;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.properties.Response_Aux_itemstock;
import com.phc.cssd.url.DateThai;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Barcode_itemstock extends Activity {

    ArrayList<Response_Aux_itemstock> ArrayData = new ArrayList<Response_Aux_itemstock>();
    ArrayList<Response_Aux_itemstock> ArrayData1 = new ArrayList<Response_Aux_itemstock>();
    ListView list_search;
    ListView list_import;
    LinearLayout Li1;
    LinearLayout Li2;
    ListView Lv2;
    CheckBox ck_IsWithDraw;
    Button button_search;
    Button button_import;
    EditText txtsearch;
    Spinner spDep;
    LinearLayout Lrx1;
    LinearLayout LL1;
    LinearLayout LL2;
    DateThai Dthai;
    private int year;
    private int month;
    private int day;
    private String DeptID;

    String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();
    String xSel = "0";
    ArrayList<Response_Aux> resultsDepartment = new ArrayList<Response_Aux>();
    ImageView imageView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_itemstock);

        Bundle bd = getIntent().getExtras();
        if (bd != null){
            xSel =  bd.getString("xSel");
        }

        TextView txt_thaidate = (TextView) findViewById(R.id.txt_thaidate);
        Date currentDate = Calendar.getInstance().getTime();
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("EEE,d,M,yyyy");
        String formattedCurrentDate = simpleDateFormat.format(currentDate);
        Dthai = new DateThai();
        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        String[] partsCollArr;
        String delimiter = ",";
        partsCollArr = formattedCurrentDate.split(delimiter);

        String ft = Dthai.getDayThai( partsCollArr[0] ) + " "
                + partsCollArr[1] + " "
                + Dthai.getMonthThai( Integer.parseInt( partsCollArr[2] ) ) + " "
                + Dthai.getYearThai( Integer.parseInt( partsCollArr[3] ) );

        txt_thaidate.setText( ft );
        initialize();
        if(!xSel.equals("2")){
            spDep.setVisibility(View.GONE);
            Li1.setVisibility(View.GONE);
            Li2.setVisibility(View.GONE);
            Lrx1.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = LL1.getLayoutParams();

// Changes the height and width to the specified *pixels*
            //params.height = 580;
            params.width = 780;
            LL1.setLayoutParams(params);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(10, 0, 0, 0);
            LL2.setLayoutParams(layoutParams);
//            Button okButton=new Button(this);
//            okButton.setText("some text");
//            LL2.addView(okButton, layoutParams);

        }else{
            xCtl.ListDepartment(spDep,"", this);
            resultsDepartment = xCtl.getListDepartment();
            //xCtl.ListNumber(xNum, 200,this);
        }


        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initialize(){
//        DeptID = "2";
        Lrx1 = (LinearLayout) findViewById(R.id.Lrx1);
        LL1 = (LinearLayout) findViewById(R.id.LL1);
        LL2 = (LinearLayout) findViewById(R.id.LL2);
        spDep = (Spinner) findViewById(R.id.spDep);
        Li1 = (LinearLayout) findViewById(R.id.Li1);
        Li2 = (LinearLayout) findViewById(R.id.Li2);
        txtsearch = (EditText) findViewById(R.id.item_search);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        ck_IsWithDraw = (CheckBox) findViewById(R.id.ck_iswithdraw);
        txtsearch.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                                checkduplicated(txtsearch.getText().toString());
                                txtsearch.requestFocus();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        button_search = (Button) findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkduplicated(txtsearch.getText().toString());
                txtsearch.requestFocus();
            }
        });

        button_import = (Button) findViewById(R.id.button_import) ;
        button_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getCountselected().equals("0")) {
                    Intent intent = new Intent();
                    if(xSel.equals("1")){
                        setResult(100, intent);
                        intent.putExtra("RowID",getId());
                    }else{
                        setResult(9999, intent);
                        intent.putExtra("RETURN_Dep",getDept());
                        intent.putExtra("RETURN_Id",getId());
                        intent.putExtra("RETURN_Qty",getQty());
                        intent.putExtra("RETURN_ReSterile",getReSterile());
                        intent.putExtra("RETURN_ReSterileType",getReSterileType());
                        intent.putExtra("RETURN_IsWithdraw",getIsWithdraw());
                    }
                    intent.putExtra("xSel",xSel);
                    finish();
                }else{
                    Toast.makeText(Barcode_itemstock.this,"กรุณาเลือกข้อมูลอย่างน้อย 1 รายการ", Toast.LENGTH_LONG).show();
                }
            }
        });

        list_search = (ListView) findViewById(R.id.list_search);
        list_import = (ListView) findViewById(R.id.list_import);
        KeyboardUtils.hideKeyboard(Barcode_itemstock.this);
    }

    public String getDept(){
        String Arraysel = "";
        for (int i=0;i<ArrayData.size();i++){
            if(i<ArrayData.size()) {
                Arraysel += resultsDepartment.get(spDep.getSelectedItemPosition()).getFields1() + ",";
                    //Arraysel += ArrayData.get(i).getxFields10() + ",";
            }
        }

        Arraysel = Arraysel.substring(0, Arraysel.length() - 1);
        return Arraysel;
    }

    public String getId(){
        String Arraysel = "";
        for (int i=0;i<ArrayData.size();i++){
            if(i<ArrayData.size()) {
                    Arraysel += ArrayData.get(i).getFields1() + ",";
            }
        }
        Arraysel = Arraysel.substring(0, Arraysel.length() - 1);
        return Arraysel;
    }

    public String getQty(){
        String Arraysel = "";
        for (int i=0;i<ArrayData.size();i++){
            if(i<ArrayData.size()) {
                    Arraysel += ArrayData.get(i).getFields6() + ",";
            }
        }
        Arraysel = Arraysel.substring(0, Arraysel.length() - 1);
        return Arraysel;
    }

    public String getReSterile(){
        String Arraysel = "";
        for (int i=0;i<ArrayData.size();i++){
            if(i<ArrayData.size()) {
                Arraysel += ArrayData.get(i).getxFields11() + ",";
            }
        }
        Arraysel = Arraysel.substring(0, Arraysel.length() - 1);
        return Arraysel;
    }

    public String getReSterileType(){
        String Arraysel = "";
        for (int i=0;i<ArrayData.size();i++){
            if(i<ArrayData.size()) {
                Arraysel += ArrayData.get(i).getxFields13() + ",";
            }
        }
        Arraysel = Arraysel.substring(0, Arraysel.length() - 1);
        return Arraysel;
    }
    public String getIsWithdraw(){
        String Arraysel = "";
        String isWD="0";
        if(ck_IsWithDraw.isChecked()){
           isWD="1";
        }
        for (int i=0;i<ArrayData.size();i++){
            if(i<ArrayData.size()) {
                Arraysel += isWD + ",";
            }
        }
        Arraysel = Arraysel.substring(0, Arraysel.length() - 1);
        return Arraysel;
    }

    public String getCountselected(){
        int count = ArrayData.size();
        return String.valueOf(count);
    }

    public void checkduplicated(final String Usage_code){
        int count = 0;
        if(xSel.equals("1")) {
            ListData(Usage_code, xSel, "0");
        }else {
            ListData(Usage_code, xSel, resultsDepartment.get(spDep.getSelectedItemPosition()).getFields1());
        }
    }

    public void ListData(final String Usage_code,final String xSel,final String xDepID) {
        class ListData extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    ArrayData1.clear();
                    Response_Aux_itemstock newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux_itemstock();
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            newsData.setFields1(c.getString("xID"));
                            newsData.setFields2(c.getString("xItem_Code"));
                            newsData.setFields3(c.getString("xPackDate"));
                            newsData.setFields4(c.getString("xExpDate"));
                            newsData.setFields5(c.getString("xDept"));
                            newsData.setFields6(c.getString("xQty"));
                            newsData.setFields7(c.getString("xStatus"));
                            newsData.setFields8(c.getString("xUsageID"));
                            newsData.setFields9(c.getString("xItem_Name"));
                            newsData.setxFields10(c.getString("xDeptID"));
                            newsData.setxFields11("0");
                            newsData.setxFields12("0");
                            newsData.setxFields13("0");
                            newsData.setxFields14("0");
                            newsData.setIs_Check(true);
                            ArrayData1.add( newsData );
                            if(i==0 && !Usage_code.equals("")){
                                txtsearch.setText("");
                                txtsearch.requestFocus();
                            }
                        }else{
                            txtsearch.setText("");
                            txtsearch.requestFocus();
                        }
                    }

                    list_search.setAdapter(new L_itemstock_Adapter(Barcode_itemstock.this, ArrayData1, list_import, ArrayData,Usage_code));
//                    new Timer().schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            // this code will be executed after 2 seconds
//                        }
//                    }, 1000);



//
//                    if(xSel.equals("2")){
//                        list_search.setAdapter(new L_itemstock_Adapter(Barcode_itemstock.this, ArrayData1, list_import, ArrayData,Usage_code));
//                    }else{
//                        if(ArrayData1.size()>0) {
//                            Response_Aux_itemstock newsData2 = new Response_Aux_itemstock();
//                            Assert.assertNotEquals(ArrayData, ArrayData1);
//                            boolean chk=false;
//                            for (int i=0;i<ArrayData.size();i++){
//                                if(ArrayData1.get(0).getFields8().equals(ArrayData.get(i).getFields8())){ chk = true;}
//                            }
//                            if( !chk ) {
//                                newsData2.setFields1(ArrayData1.get(0).getFields1());
//                                newsData2.setFields2(ArrayData1.get(0).getFields2());
//                                newsData2.setFields3(ArrayData1.get(0).getFields3());
//                                newsData2.setFields4(ArrayData1.get(0).getFields4());
//                                newsData2.setFields5(ArrayData1.get(0).getFields5());
//                                newsData2.setFields6(ArrayData1.get(0).getFields6());
//                                newsData2.setFields7(ArrayData1.get(0).getFields7());
//                                newsData2.setFields8(ArrayData1.get(0).getFields8());
//                                newsData2.setFields9(ArrayData1.get(0).getFields9());
//                                newsData2.setxFields10(ArrayData1.get(0).getxFields10());
//                                newsData2.setxFields11(ArrayData1.get(0).getxFields11());
//                                newsData2.setxFields12(ArrayData1.get(0).getxFields12());
//                                newsData2.setxFields13(ArrayData1.get(0).getxFields13());
//                                newsData2.setxFields14(ArrayData1.get(0).getxFields14());
//                                ArrayData.add(newsData2);
//                            }
//                            list_import.setAdapter(new R_itemstock_Adapter(Barcode_itemstock.this, ArrayData,Lv2));
//                        }
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Usage_code",params[0]);
                data.put("xSel",params[1]);
                data.put("xDepID",params[2]);
                Log.d("doInBackground: ", data+"");
                String result = ruc.sendPostRequest(iFt.Check_usagecode(),data);
                Log.d("result: ", result+"");
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Usage_code,xSel,xDepID );
    }

    @Override
    public void onBackPressed() {
        //Intent intent = new Intent(this, CssdSterile.class);
        //startActivity(intent);
        this.finish();
    }
}
