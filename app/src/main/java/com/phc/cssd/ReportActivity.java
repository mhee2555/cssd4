package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.properties.pCustomer;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ReportActivity extends AppCompatActivity {

    ArrayList<String> arrayitem = new ArrayList<String>();
    ArrayList<String> xDataDepartment= new ArrayList<>();
    ArrayList<String> listmonth = new ArrayList<String>();
    ArrayList<String> listhours = new ArrayList<String>();
    ArrayList<String> years = new ArrayList<String>();
    ArrayList<String> xProgram= new ArrayList<>();
    ArrayList<String> xProgram1= new ArrayList<>();
    ArrayList<String> xProgram2= new ArrayList<>();
    ArrayList<String> xProgram3= new ArrayList<>();

    Calendar myCalendar = Calendar.getInstance();

    EditText dtUsageCode;
    EditText txtdate;
    EditText txtedate;

    TextView txtdept;
    TextView txt_year;
    TextView txt_program;
    TextView txt_date30;
    TextView txt_date30_1;
    TextView txt_date30_2;
    TextView textView24_1;
    TextView textView30_year;
    TextView textView24_2;
    TextView txtname;
    TextView totxt;
    TextView text_search;
    TextView usage_text;

    Spinner txtyear;
    Spinner e_year;
    Spinner spn_dept;
    Spinner spn_Month;
    Spinner spn_Month2;
    Spinner spn_Year;
    Spinner spn_program;
    Spinner spn_Hours;
    Spinner spn_mr;
    Spinner spinnertype;
    Spinner spinnertype1;
    Spinner spn_type;
    Spinner spn_type_spore;
    Spinner spn_type_spore1;
    Spinner zone_report;

    ImageView ImageBack;

    Button btnpdf;

    ListView list_report;

    Switch switch2;
    Switch switch_zone;

    int month_1;
    int month_2;
    int Year_;

    String xSel = "";
    String slc = "1";
    String Typespore = "1";
    String dept_id;
    String Type_pdf;
    String Program_ID;
    String TAG_RESULTS="result";

    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();

    private String B_ID = null;

    ArrayList<String> liststatus = new ArrayList<String>();
    ArrayList<String> liststatus1 = new ArrayList<String>();
    ArrayList<String> liststatus2 = new ArrayList<String>();
    ArrayList<String> liststatus3 = new ArrayList<String>();

    private void byIntent(){
        Intent intent = getIntent();
        B_ID = intent.getStringExtra("B_ID");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Type_pdf="0";

        getSupportActionBar().hide();

        byIntent();

        initialize();

    }

    private void initialize() {
        txtname = (TextView) findViewById(R.id.txtname);
        totxt = (TextView) findViewById(R.id.totxt);
        txtdept = (TextView) findViewById(R.id.txtdept);
        list_report = (ListView) findViewById(R.id.listview);
        dtUsageCode = (EditText) findViewById(R.id.dtUsageCode);
        txtdate = (EditText) findViewById(R.id.txtdate);
        txtedate = (EditText) findViewById(R.id.e_date);
        txtyear = (Spinner ) findViewById(R.id.txtyear);
        e_year = (Spinner ) findViewById(R.id.e_year);
        txtedate.setInputType(InputType.TYPE_NULL);
        txtedate.setVisibility(View.INVISIBLE);
        txtdate.setInputType(InputType.TYPE_NULL);
        txtdate.setEnabled(false);
        e_year.setVisibility(View.INVISIBLE);
        txtyear.setVisibility(View.INVISIBLE);
        spn_dept = (Spinner) findViewById(R.id.spn_dept);
        spn_type = (Spinner) findViewById(R.id.spn_type);
        txtedate.setVisibility(View.INVISIBLE);
        totxt.setVisibility(View.INVISIBLE);
        txt_date30 = (TextView) findViewById(R.id.textView30);
        txt_date30_1 = (TextView) findViewById(R.id.textView30_1);
        txt_date30_2 = (TextView) findViewById(R.id.textView30_2);
        spn_dept.setVisibility(View.INVISIBLE);
        txtdept.setVisibility(View.INVISIBLE);
        txt_date30_1.setVisibility(View.INVISIBLE);
        txt_date30_2.setVisibility(View.INVISIBLE);
        btnpdf = (Button) findViewById(R.id.button_pdf);
        spn_mr = (Spinner)findViewById(R.id.spn_mr);
        spn_Year = (Spinner)findViewById(R.id.spn_year_report);
        spn_Month = (Spinner)findViewById(R.id.spn_month);
        spn_Month2 = (Spinner)findViewById(R.id.spn_month2);
        spn_program = (Spinner)findViewById(R.id.spn_program);
        spn_Hours = (Spinner)findViewById(R.id.spn_hours);
        spinnertype = (Spinner)findViewById(R.id.spinnertype);
        spinnertype1 = (Spinner)findViewById(R.id.spinnertype1);
        txt_year = (TextView) findViewById(R.id.txt_year);
        txt_program = (TextView) findViewById(R.id.txt_program);
        switch2 = (Switch) findViewById(R.id.switch2);
        switch_zone = (Switch) findViewById(R.id.switch_zone);
        zone_report = (Spinner)findViewById(R.id.zone_report);
        textView24_1 = (TextView) findViewById(R.id.textView24_1);
        textView30_year = (TextView) findViewById(R.id.textView30_year);
        spn_type_spore = (Spinner ) findViewById(R.id.spn_type_spore);
        spn_type_spore1 = (Spinner ) findViewById(R.id.spn_type_spore1);
        textView24_2 = (TextView ) findViewById(R.id.textView24_2);
        text_search = (TextView) findViewById(R.id.text_search);
        usage_text = (TextView) findViewById(R.id.usage_text);

        text_search.setVisibility(View.INVISIBLE);
        usage_text.setVisibility(View.INVISIBLE);
        switch2.setVisibility(View.INVISIBLE);
        textView24_2.setVisibility(View.INVISIBLE);
        spn_type_spore.setVisibility(View.INVISIBLE);
        spn_type_spore1.setVisibility(View.INVISIBLE);
        spn_type.setVisibility(View.INVISIBLE);
        textView24_1.setVisibility(View.INVISIBLE);
        textView30_year.setVisibility(View.INVISIBLE);
        zone_report.setVisibility(View.VISIBLE);

        btnpdf.setEnabled(false);

        getListReport();
        setSpinnerStatus();
        setSpinnerTimeType();
        setSpinnerSporeType();
        setSpinnerSporeType1();

        zone_report.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    xSel = "-";
                    arrayitem.clear();
                    getListReport();
                } else if (position == 1) {
                    xSel = "1";
                    arrayitem.clear();
                    getListReport();
                } else if (position == 2) {
                    xSel = "2";
                    arrayitem.clear();
                    getListReport();
                }else if (position == 3) {
                    xSel = "3";
                    arrayitem.clear();
                    getListReport();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_Year.setVisibility(View.INVISIBLE);
        spn_Month.setVisibility(View.INVISIBLE);
        spn_Month2.setVisibility(View.INVISIBLE);
        spn_program.setVisibility(View.INVISIBLE);
        spn_Hours.setVisibility(View.INVISIBLE);
        txt_year.setVisibility(View.INVISIBLE);
        txt_program.setVisibility(View.INVISIBLE);
        spinnertype.setVisibility(View.INVISIBLE);
        spinnertype1.setVisibility(View.INVISIBLE);

        getsDate(txtdate,txtedate);
        month();
        getyear();
        listprogram();
        sethours();

        ImageBack = (ImageView) findViewById(R.id.imageBack);
        ImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer Hour = spn_Hours.getSelectedItemPosition();
                GetReport(txtname.getText().toString(),txtdate.getText().toString(),txtedate.getText().toString(),Type_pdf,dept_id,month_1,month_2,Year_,Program_ID,Hour,dtUsageCode.getText().toString());
            }
        });

        ListDepartment();

        spn_dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pn, long id) {
                dept_id= xDataDepartment.get(pn);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spn_Month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pn, long id) {
                month_1= pn+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spn_Month2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pn, long id) {
                month_2= pn+1;
                if(month_1>month_2){
                    Toast.makeText(ReportActivity.this, "กรุณาเลือกเดือนให้ถูกต้อง", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spn_Year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pn, long id) {
                Year_= Integer.parseInt(years.get(pn));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        e_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pn, long id) {
                Year_= Integer.parseInt(years.get(pn));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        txtyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pn, long id) {
                Year_= Integer.parseInt(years.get(pn));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spn_program.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pn, long id) {
                Program_ID= xProgram.get(pn);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void GetReport(String Url, String sDate, String eDate,String Type,String DeptID,int xmonth1,int xmonth2,int xyear,String xprogram,Integer xhours,String UsageCode) {
        String url = "";
        if(Type.equals("0")){
            url = getUrl.xUrl+"report/"+Url+"?eDate="+sDate;
        }else if(Type.equals("1")){
            if(xhours==0) {
                url = getUrl.xUrl+"report/" + Url + "?sDate=" + sDate + "&eDate=" + eDate + "&DeptID=" + DeptID + "&In=true";
            }else if(xhours==1){
                url = getUrl.xUrl+"report/Report_Washing_afterhour.php?sDate=" + sDate + "&eDate=" + eDate + "&DeptID=" + DeptID + "&In=false";
            }
        }else if(Type.equals("2")){
            url = getUrl.xUrl + "report/Report_Before_Expire.php?eDate=" + sDate + "&BID=" + B_ID + "&DeptID=" + DeptID;
        }else if(Type.equals("3")){
            if(xhours==0) {
                url = getUrl.xUrl+"report/tcreport/" + Url + "?eDate=" + sDate + "&In=true";
            }else if(xhours==1){
                url = getUrl.xUrl+"report/Report_Sterile_afterhour.php?eDate=" + sDate + "&In=false";
            }
        }else if(Type.equals("5")){
            if(xhours==0) {
                url = getUrl.xUrl+"report/" + Url + "?eDate=" + sDate + "&Dept=" + DeptID;
            }
        }else if(Type.equals("9")){
            if(switch2.isChecked()) {
                url = getUrl.xUrl+"report/" + Url + "?UsageCode=" + UsageCode;
            }else{
                int xSel = Integer.parseInt( spn_mr.getSelectedItemPosition()+"" );
                url = getUrl.xUrl+"report/" + Url + "?Mac=" + spinnertype.getSelectedItem() + "&Round=" + spinnertype1.getSelectedItem() + "&eDate=" + sDate;
            }
        }else if(Type.equals("10")){
            url = getUrl.xUrl+"report/Report_Receive.php?eDate=" + sDate + "&bid=" + B_ID;
        }else if(Type.equals("11")){
            if (slc.equals("1")) {
                url = getUrl.xUrl + "report/Report_Sum_Receive.php?sDate=" + sDate + "&slc=" + slc + "&bid=" + B_ID + "&eDate=" + eDate;
            }else if (slc.equals("2")){
                url = getUrl.xUrl + "report/Report_Sum_Receive.php?&slc=" + slc + "&bid=" + B_ID + "&Month1=" + month_1 + "&Month2=" + month_2 + "&Year=" + spn_Year.getSelectedItem();
            }else if (slc.equals("3")){
                url = getUrl.xUrl + "report/Report_Sum_Receive.php?&slc=" + slc + "&bid=" + B_ID + "&Year1=" + txtyear.getSelectedItem() + "&Year2=" + e_year.getSelectedItem();
            }
        }else if(Type.equals("12")){
            if (slc.equals("1")) {
                url = getUrl.xUrl + "report/Report_Sum_Payout_Sub.php?sDate=" + sDate + "&slc=" + slc + "&bid=" + B_ID + "&eDate=" + eDate;
            }else if (slc.equals("2")){
                url = getUrl.xUrl + "report/Report_Sum_Payout_Sub.php?&slc=" + slc + "&bid=" + B_ID + "&Month1=" + month_1 + "&Month2=" + month_2 + "&Year=" + spn_Year.getSelectedItem();
            }else if (slc.equals("3")){
                url = getUrl.xUrl + "report/Report_Sum_Payout_Sub.php?&slc=" + slc + "&bid=" + B_ID + "&Year1=" + txtyear.getSelectedItem() + "&Year2=" + e_year.getSelectedItem();
            }
        }else if(Type.equals("13")){
            url = getUrl.xUrl+"report/Report_Payout_Sub.php?eDate=" + sDate + "&bid=" + B_ID;
        }else if(Type.equals("14")){
            url = getUrl.xUrl+"report/Report_Label.php?eDate=" + eDate + "&bid=" + B_ID + "&sDate=" + sDate;
        }else if(Type.equals("15")){
            url = getUrl.xUrl+"report/Report_ATP.php?eDate=" + eDate + "&bid=" + B_ID + "&sDate=" + sDate;
        }else if(Type.equals("16")){
            url = getUrl.xUrl+"report/Report_Test_Machine_Tosi.php?eDate=" + eDate + "&bid=" + B_ID + "&sDate=" + sDate;
        }else if(Type.equals("17")){
            url = getUrl.xUrl+"report/Report_Test_Machine_Sono.php?eDate=" + eDate + "&bid=" + B_ID + "&sDate=" + sDate;
        }else if(Type.equals("18")){
            if (slc.equals("1")) {
                url = getUrl.xUrl + "report/Report_Spore_Test.php?sDate=" + sDate + "&Type=" + slc + "&bid=" + B_ID + "&eDate=" + eDate;
            }else if (slc.equals("2")){
                url = getUrl.xUrl + "report/Report_Spore_Test.php?sDate=" + sDate + "&Type=" + slc + "&bid=" + B_ID + "&eDate=" + eDate;
            }else if (slc.equals("3")){
                url = getUrl.xUrl + "report/Report_Spore_Test.php?sDate=" + sDate + "&Type=" + slc + "&bid=" + B_ID + "&eDate=" + eDate;
            }
        }else if(Type.equals("19")){
            url = getUrl.xUrl+"report/Report_Bowie_Dick_Test.php?eDate=" + eDate + "&bid=" + B_ID + "&sDate=" + sDate;
        }else if(Type.equals("20")){
            url = getUrl.xUrl+"report/Report_Leak_Test.php?eDate=" + eDate + "&bid=" + B_ID + "&sDate=" + sDate;
        }else if(Type.equals("21")) {
            if (slc.equals("1")) {
                url = getUrl.xUrl + "report/Report_Sum_Receive_linen.php?sDate=" + sDate + "&slc=" + slc + "&bid=" + B_ID + "&eDate=" + eDate;
            } else if (slc.equals("2")) {
                url = getUrl.xUrl + "report/Report_Sum_Receive_linen.php?&slc=" + slc + "&bid=" + B_ID + "&Month1=" + month_1 + "&Month2=" + month_2 + "&Year=" + spn_Year.getSelectedItem();
            } else if (slc.equals("3")) {
                url = getUrl.xUrl + "report/Report_Sum_Receive_linen.php?&slc=" + slc + "&bid=" + B_ID + "&Year1=" + txtyear.getSelectedItem() + "&Year2=" + e_year.getSelectedItem();
            }
        }else if(Type.equals("22")) {
            if (slc.equals("1")) {
                url = getUrl.xUrl + "report/Report_Sum_EXP.php?sDate=" + sDate + "&slc=" + slc + "&bid=" + B_ID + "&eDate=" + eDate;
            } else if (slc.equals("2")) {
                url = getUrl.xUrl + "report/Report_Sum_EXP.php?&slc=" + slc + "&bid=" + B_ID + "&Month1=" + month_1 + "&Month2=" + month_2 + "&Year=" + spn_Year.getSelectedItem();
            } else if (slc.equals("3")) {
                url = getUrl.xUrl + "report/Report_Sum_EXP.php?&slc=" + slc + "&bid=" + B_ID + "&Year1=" + txtyear.getSelectedItem() + "&Year2=" + e_year.getSelectedItem();
            }
        }else if(Type.equals("23")) {
            if (slc.equals("1")) {
                url = getUrl.xUrl + "report/tcreport/Report_Sterile_New.php?type=" + "BETWEEN" + "&bid=" + B_ID + "&sDate=" + sDate + "&eDate=" + eDate;
            } else if (slc.equals("2")) {
                url = getUrl.xUrl + "report/tcreport/Report_Sterile_New.php?type=" + "MONTH" + "&bid=" + B_ID + "&sDate=" + month_1 + "&eDate=" + month_2 + "&Year=" + spn_Year.getSelectedItem();;
            } else if (slc.equals("3")) {
                url = getUrl.xUrl + "report/tcreport/Report_Sterile_New.php?type=" + "YEAR" + "&bid=" + B_ID + "&Year1=" + txtyear.getSelectedItem() + "&Year2=" + e_year.getSelectedItem();
            }
        }else if(Type.equals("24")) {
            if (slc.equals("1")) {
                url = getUrl.xUrl + "report/Report_Wash_Mac_Round.php?sDate=" + sDate + "&slc=" + slc + "&bid=" + B_ID + "&eDate=" + eDate;
            }else if (slc.equals("2")){
                url = getUrl.xUrl + "report/Report_Wash_Mac_Round.php?&slc=" + slc + "&bid=" + B_ID + "&Month=" + month_1 + "&Year=" + spn_Year.getSelectedItem();
            }else if (slc.equals("3")){
                url = getUrl.xUrl + "report/Report_Wash_Mac_Round.php?&slc=" + slc + "&bid=" + B_ID + "&Year1=" + txtyear.getSelectedItem() + "&Year2=" + e_year.getSelectedItem();
            }
        }else if(Type.equals("25")) {
            if (slc.equals("1")) {
                url = getUrl.xUrl + "report/Report_Sterile_Mac_Round.php?sDate=" + sDate + "&slc=" + slc + "&bid=" + B_ID + "&eDate=" + eDate;
            }else if (slc.equals("2")){
                url = getUrl.xUrl + "report/Report_Sterile_Mac_Round.php?&slc=" + slc + "&bid=" + B_ID + "&Month=" + month_1 + "&Year=" + spn_Year.getSelectedItem();
            }else if (slc.equals("3")){
                url = getUrl.xUrl + "report/Report_Sterile_Mac_Round.php?&slc=" + slc + "&bid=" + B_ID + "&Year1=" + txtyear.getSelectedItem() + "&Year2=" + e_year.getSelectedItem();
            }
        }else if(Type.equals("26")) {
            if (slc.equals("1")) {
                url = getUrl.xUrl + "report/Report_Stock_CSSD.php?type=" + "0" + "&bid=" + B_ID ;
            } else if (slc.equals("2")) {
                url = getUrl.xUrl + "report/Report_Stock_CSSD2.php?type=" + "1" + "&bid=" + B_ID ;
            }
        }else{
            url = getUrl.xUrl+"report/"+Url+"?Month1="+xmonth1+"&Month2="+xmonth2+"&Year="+xyear+"&SterileProgramID="+xprogram;
        }
        url = url + "&p_pid=" + B_ID;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void getsDate(final EditText txtdate,final EditText txtedate){
        presentdate(txtdate);
        enddate(txtedate);


        final DatePickerDialog.OnDateSetListener sdate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                presentdate(txtdate);
            }

        };

        final DatePickerDialog.OnDateSetListener edate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                enddate(txtedate);
            }

        };

        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ReportActivity.this, sdate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        txtedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ReportActivity.this, edate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void presentdate(final EditText txtdate) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtdate.setText(sdf.format(myCalendar.getTime()));
        myFormat = "yyyy-MM-dd"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        if( Type_pdf.equals("9") ){
            if( !switch2.isChecked() ) listmr(sdf.format(myCalendar.getTime()));
        }
        String myFormat1 = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        getMac(sdf1.format(myCalendar.getTime()));
    }

    private void enddate(final EditText txtedate) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtedate.setText(sdf.format(myCalendar.getTime()));
    }

    public void getListReport(){
        if (xSel == "-") {
            arrayitem.add("รายชื่อเซ็ตหน่วยจ่ายกลาง");
            arrayitem.add("สรุปรายการที่ใกล้หมดอายุ");
            arrayitem.add("สรุปรายการฆ่าเชื้อประจำวัน");
            arrayitem.add("สรุปรายการส่งล้างของแผนก");
            arrayitem.add("สรุปสถิติการฆ่าเชื้อของแต่ละแผนก");
            arrayitem.add("สรุปรายการส่ง-จ่ายอุปกรณ์ประจำวันของทุกแผนก");
            arrayitem.add("สรุปรายงานเอกสารที่ต้องจัดและค้างจัด");
            arrayitem.add("สรุปรายงานอุปกรณ์ชำรุด");
            arrayitem.add("สรุปรายงานบันทึกความเสี่ยง");
            arrayitem.add("สรุปรายงานสืบย้อนกลับ");
            arrayitem.add("รายงานการรับเข้า");
            arrayitem.add("รายงานสรุปยอดการรับเข้า");
            arrayitem.add("รายงานสรุปยอดการจ่ายออก");
            arrayitem.add("รายงานการจ่ายออก");
            arrayitem.add("รายงานประวัติการผลิตเครื่องมือแพทย์");
            arrayitem.add("ผลทดสอบประสิทธิภาพการทำความสะอาด(ATP)");
            arrayitem.add("ผลทดสอบประสิทธิภาพการทำความสะอาคเครื่อง(TOSI)");
            arrayitem.add("ผลทดสอบประสิทธิภาพการทำความสะอาดเครื่อง(Sono Check)");
            arrayitem.add("ผลตรวจสอบประสิทธิภาพการทำให้ปราศจากเชื้อ(Spore Test)");
            arrayitem.add("ผลตรวจสอบประสิทธิภาพการทำให้ปราศจากเชื้อ(Bowie Dick Test)");
            arrayitem.add("ผลตรวจสอบประสิทธิภาพการทำให้ปราศจากเชื้อ(Leak Test)");
            arrayitem.add("รายงานเครื่องผ้า");
            arrayitem.add("รายงานสรุปรายการหมดอายุ");
            arrayitem.add("สรุปรายการฆ่าเชื้อ");
            arrayitem.add("รายงานสถิติรอบ-เครื่อง การล้าง");
            arrayitem.add("รายงานสถิติรอบ-เครื่อง การฆ่าเชื้อ");
            arrayitem.add("รายงานสต๊อกของจ่ายกลาง");
        }else if (xSel == "1"){
            arrayitem.add("รายชื่อเซ็ตหน่วยจ่ายกลาง");
            arrayitem.add("สรุปรายการส่งล้างของแผนก");
            arrayitem.add("สรุปรายการส่ง-จ่ายอุปกรณ์ประจำวันของทุกแผนก");
            arrayitem.add("สรุปรายงานอุปกรณ์ชำรุด");
            arrayitem.add("สรุปรายงานบันทึกความเสี่ยง");
            arrayitem.add("สรุปรายงานสืบย้อนกลับ");
            arrayitem.add("รายงานการรับเข้า");
            arrayitem.add("รายงานสรุปยอดการรับเข้า");
            arrayitem.add("ผลทดสอบประสิทธิภาพการทำความสะอาด(ATP)");
            arrayitem.add("ผลทดสอบประสิทธิภาพการทำความสะอาคเครื่อง(TOSI)");
            arrayitem.add("ผลทดสอบประสิทธิภาพการทำความสะอาดเครื่อง(Sono Check)");
        }else if (xSel == "2"){
            arrayitem.add("รายชื่อเซ็ตหน่วยจ่ายกลาง");
            arrayitem.add("สรุปรายการฆ่าเชื้อประจำวัน");
            arrayitem.add("สรุปสถิติการฆ่าเชื้อของแต่ละแผนก");
            arrayitem.add("สรุปรายงานสืบย้อนกลับ");
            arrayitem.add("สรุปรายงานบันทึกความเสี่ยง");
            arrayitem.add("รายงานประวัติการผลิตเครื่องมือแพทย์");
            arrayitem.add("ผลทดสอบประสิทธิภาพการทำความสะอาด(ATP)");
            arrayitem.add("ผลตรวจสอบประสิทธิภาพการทำให้ปราศจากเชื้อ(Spore Test)");
            arrayitem.add("ผลตรวจสอบประสิทธิภาพการทำให้ปราศจากเชื้อ(Bowie Dick Test)");
            arrayitem.add("ผลตรวจสอบประสิทธิภาพการทำให้ปราศจากเชื้อ(Leak Test)");
        }else if (xSel == "3"){
            arrayitem.add("รายชื่อเซ็ตหน่วยจ่ายกลาง");
            arrayitem.add("สรุปรายการที่จะหมดอายุ");
            arrayitem.add("สรุปรายการส่ง-จ่ายอุปกรณ์ประจำวันของทุกแผนก");
            arrayitem.add("สรุปรายงานเอกสารที่ต้องจัดและค้างจัด");
            arrayitem.add("สรุปรายงานสืบย้อนกลับ");
            arrayitem.add("สรุปรายงานบันทึกความเสี่ยง");
            arrayitem.add("รายงานสรุปยอดการจ่ายออก");
            arrayitem.add("รายงานการจ่ายออก");
        }
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on){
                Type_pdf="9";
                if(on){
                    txtname.setText("Report_TraceBack.php");
                    txtname.setTextColor(Color.BLACK);
                    txt_date30.setText("รหัสใช้งาน");
                    txtdate.setEnabled(true);
                    text_search.setVisibility(View.VISIBLE);
                    usage_text.setVisibility(View.VISIBLE);
                    switch2.setVisibility(View.VISIBLE);
                    spn_mr.setVisibility(View.INVISIBLE);
                    dtUsageCode.setVisibility(View.VISIBLE);
                    txtdate.setVisibility(View.INVISIBLE);
                    txtedate.setVisibility(View.INVISIBLE);
                    totxt.setVisibility(View.INVISIBLE);
                    spn_dept.setVisibility(View.INVISIBLE);
                    txtdept.setVisibility(View.INVISIBLE);
                    spn_Year.setVisibility(View.INVISIBLE);
                    spn_Month.setVisibility(View.INVISIBLE);
                    spn_Month2.setVisibility(View.INVISIBLE);
                    spn_program.setVisibility(View.INVISIBLE);
                    txt_year.setVisibility(View.INVISIBLE);
                    txt_program.setVisibility(View.INVISIBLE);
                    spn_Hours.setVisibility(View.INVISIBLE);
                    spinnertype.setVisibility(View.INVISIBLE);
                    spinnertype1.setVisibility(View.INVISIBLE);
                    txt_date30_1.setVisibility(View.INVISIBLE);
                    txt_date30_2.setVisibility(View.INVISIBLE);
                    spn_type.setVisibility(View.INVISIBLE);
                    textView24_1.setVisibility(View.INVISIBLE);
                    textView30_year.setVisibility(View.INVISIBLE);
                    e_year.setVisibility(View.INVISIBLE);
                    txtyear.setVisibility(View.INVISIBLE);
                    textView24_2.setVisibility(View.INVISIBLE);
                    spn_type_spore.setVisibility(View.INVISIBLE);
                    spn_type_spore1.setVisibility(View.INVISIBLE);
                    String myFormat = "dd/MM/yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    getMac(sdf.format(myCalendar.getTime()));
                }else{
                    // UsageCode
                    txtname.setText("Report_Trace_Back1.php");
                    txtname.setTextColor(Color.BLACK);
                    txt_date30.setText("วันที่เอกสาร");
                    txtdate.setEnabled(true);
                    text_search.setVisibility(View.VISIBLE);
                    usage_text.setVisibility(View.VISIBLE);
                    switch2.setVisibility(View.VISIBLE);
                    spn_mr.setVisibility(View.INVISIBLE);
                    dtUsageCode.setVisibility(View.INVISIBLE);
                    txtdate.setVisibility(View.VISIBLE);
                    txtedate.setVisibility(View.INVISIBLE);
                    totxt.setVisibility(View.INVISIBLE);
                    spn_dept.setVisibility(View.INVISIBLE);
                    txtdept.setVisibility(View.INVISIBLE);
                    spn_Year.setVisibility(View.INVISIBLE);
                    spn_Month.setVisibility(View.INVISIBLE);
                    spn_Month2.setVisibility(View.INVISIBLE);
                    spn_program.setVisibility(View.INVISIBLE);
                    txt_year.setVisibility(View.INVISIBLE);
                    txt_program.setVisibility(View.INVISIBLE);
                    spn_Hours.setVisibility(View.INVISIBLE);
                    spinnertype.setVisibility(View.VISIBLE);
                    spinnertype1.setVisibility(View.VISIBLE);
                    txt_date30_1.setVisibility(View.VISIBLE);
                    txt_date30_2.setVisibility(View.VISIBLE);
                    spn_type.setVisibility(View.INVISIBLE);
                    textView24_1.setVisibility(View.INVISIBLE);
                    textView30_year.setVisibility(View.INVISIBLE);
                    e_year.setVisibility(View.INVISIBLE);
                    txtyear.setVisibility(View.INVISIBLE);
                    textView24_2.setVisibility(View.INVISIBLE);
                    spn_type_spore.setVisibility(View.INVISIBLE);
                    spn_type_spore1.setVisibility(View.INVISIBLE);
                    String myFormat = "dd/MM/yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    getMac(sdf.format(myCalendar.getTime()));
                }
            }
        });

        list_report.setAdapter(new ArrayAdapter<String>(ReportActivity.this,android.R.layout.simple_list_item_1, arrayitem));
        list_report.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                btnpdf.setEnabled(true);
                spn_Hours.setSelection(0);
                if (xSel == "-") {
                    switch (position) {
                        case 0:
                            Type_pdf = "0";
                            txtname.setText("Report_Itemstock.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.INVISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.INVISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 1:
                            Type_pdf = "2";
                            txtname.setText("Report_Before_Expire.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.VISIBLE);
                            txtdept.setVisibility(View.VISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            break;
                        case 2:
                            Type_pdf = "3";
                            txtname.setText("Report_Sterile.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 3:
                            Type_pdf = "1";
                            txtname.setText("Report_Washing.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.VISIBLE);
                            txtdept.setVisibility(View.VISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.VISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 4:
                            Type_pdf = "2";
                            txtname.setText("Report_Use_Resterile.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.INVISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.VISIBLE);
                            spn_Month.setVisibility(View.VISIBLE);
                            spn_Month2.setVisibility(View.VISIBLE);
                            spn_program.setVisibility(View.VISIBLE);
                            txt_year.setVisibility(View.VISIBLE);
                            txt_program.setVisibility(View.VISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 5:
                            Type_pdf = "5";
                            txtname.setText("Report_Summary_Payout.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.VISIBLE);
                            txtdept.setVisibility(View.VISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 6:
                            Type_pdf = "5";
                            txtname.setText("Report_Set_Order.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.INVISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.INVISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 7:
                            Type_pdf = "1";
                            txtname.setText("Report_Broken.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 8:
                            Type_pdf = "0";
                            txtname.setText("Report_Occurance.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 9:
                            Type_pdf = "9";
                            switch2.setVisibility(View.VISIBLE);
                            if (switch2.isChecked()) {
                                txtname.setText("Report_TraceBack.php");
                                txtname.setTextColor(Color.BLACK);
                                txt_date30.setText("UsageCode");
                                txtdate.setEnabled(true);
                                text_search.setVisibility(View.VISIBLE);
                                usage_text.setVisibility(View.VISIBLE);
                                switch2.setVisibility(View.VISIBLE);
                                spn_mr.setVisibility(View.INVISIBLE);
                                dtUsageCode.setVisibility(View.VISIBLE);
                                txtdate.setVisibility(View.INVISIBLE);
                                txtedate.setVisibility(View.INVISIBLE);
                                totxt.setVisibility(View.INVISIBLE);
                                spn_dept.setVisibility(View.INVISIBLE);
                                txtdept.setVisibility(View.INVISIBLE);
                                spn_Year.setVisibility(View.INVISIBLE);
                                spn_Month.setVisibility(View.INVISIBLE);
                                spn_Month2.setVisibility(View.INVISIBLE);
                                spn_program.setVisibility(View.INVISIBLE);
                                txt_year.setVisibility(View.INVISIBLE);
                                txt_program.setVisibility(View.INVISIBLE);
                                spn_Hours.setVisibility(View.INVISIBLE);
                                spinnertype.setVisibility(View.INVISIBLE);
                                spinnertype1.setVisibility(View.INVISIBLE);
                                txt_date30.setVisibility(View.VISIBLE);
                                txt_date30_1.setVisibility(View.INVISIBLE);
                                txt_date30_2.setVisibility(View.INVISIBLE);
                                spn_type.setVisibility(View.INVISIBLE);
                                textView24_1.setVisibility(View.INVISIBLE);
                                textView30_year.setVisibility(View.INVISIBLE);
                                e_year.setVisibility(View.INVISIBLE);
                                txtyear.setVisibility(View.INVISIBLE);
                                textView24_2.setVisibility(View.INVISIBLE);
                                spn_type_spore.setVisibility(View.INVISIBLE);
                                spn_type_spore1.setVisibility(View.INVISIBLE);
                                String myFormat = "dd/MM/yyyy"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                getMac(sdf.format(myCalendar.getTime()));
                            } else {
                                // UsageCode
                                txtname.setText("Report_Trace_Back1.php");
                                txtname.setTextColor(Color.BLACK);
                                txtdate.setEnabled(true);
                                text_search.setVisibility(View.VISIBLE);
                                usage_text.setVisibility(View.VISIBLE);
                                switch2.setVisibility(View.VISIBLE);
                                txt_date30.setText("วันที่เอกสาร");
                                String cc = txtdate.getText().subSequence(6, 10) + "-" + txtdate.getText().subSequence(3, 5) + "-" + txtdate.getText().subSequence(0, 2) + "";
                                listmr(cc);
                                spn_mr.setVisibility(View.INVISIBLE);
                                dtUsageCode.setVisibility(View.INVISIBLE);
                                txtdate.setVisibility(View.VISIBLE);
                                txtedate.setVisibility(View.INVISIBLE);
                                totxt.setVisibility(View.INVISIBLE);
                                spn_dept.setVisibility(View.INVISIBLE);
                                txtdept.setVisibility(View.INVISIBLE);
                                spn_Year.setVisibility(View.INVISIBLE);
                                spn_Month.setVisibility(View.INVISIBLE);
                                spn_Month2.setVisibility(View.INVISIBLE);
                                spn_program.setVisibility(View.INVISIBLE);
                                txt_year.setVisibility(View.INVISIBLE);
                                txt_program.setVisibility(View.INVISIBLE);
                                spn_Hours.setVisibility(View.INVISIBLE);
                                spinnertype.setVisibility(View.VISIBLE);
                                spinnertype1.setVisibility(View.VISIBLE);
                                txt_date30.setVisibility(View.VISIBLE);
                                txt_date30_1.setVisibility(View.VISIBLE);
                                txt_date30_2.setVisibility(View.VISIBLE);
                                spn_type.setVisibility(View.INVISIBLE);
                                textView24_1.setVisibility(View.INVISIBLE);
                                textView30_year.setVisibility(View.INVISIBLE);
                                e_year.setVisibility(View.INVISIBLE);
                                txtyear.setVisibility(View.INVISIBLE);
                                textView24_2.setVisibility(View.INVISIBLE);
                                spn_type_spore.setVisibility(View.INVISIBLE);
                                spn_type_spore1.setVisibility(View.INVISIBLE);
                                String myFormat = "dd/MM/yyyy"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                getMac(sdf.format(myCalendar.getTime()));
                            }
                            break;
                        case 10:
                            Type_pdf = "10";
                            txtname.setText("Report_Receive.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 11:
                            Type_pdf = "11";
                            txtname.setText("Report_Sum_Receive.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.VISIBLE);
                            textView24_1.setVisibility(View.VISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            spn_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position == 0) {
                                        slc = "1";
                                        Type_pdf = "11";
                                        txtname.setText("Report_Sum_Receive.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("วันที่เอกสาร");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.VISIBLE);
                                        txtedate.setVisibility(View.VISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);
                                    } else if (position == 1) {
                                        slc = "2";
                                        Type_pdf = "11";
                                        txtname.setText("Report_Sum_Receive.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("เดือน");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.VISIBLE);
                                        spn_Month.setVisibility(View.VISIBLE);
                                        spn_Month2.setVisibility(View.VISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView30_year.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);
                                    } else if (position == 2) {
                                        slc = "3";
                                        Type_pdf = "11";
                                        txtname.setText("Report_Sum_Receive.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("ปี");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.VISIBLE);
                                        txtyear.setVisibility(View.VISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            break;
                        case 12:
                            Type_pdf = "12";
                            txtname.setText("Report_Sum_Payout_Sub.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.VISIBLE);
                            textView24_1.setVisibility(View.VISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            spn_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position == 0) {
                                        slc = "1";
                                        Type_pdf = "12";
                                        txtname.setText("Report_Sum_Payout_Sub.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("วันที่เอกสาร");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.VISIBLE);
                                        txtedate.setVisibility(View.VISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 1) {
                                        slc = "2";
                                        Type_pdf = "12";
                                        txtname.setText("Report_Sum_Payout_Sub.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("เดือน");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.VISIBLE);
                                        spn_Month.setVisibility(View.VISIBLE);
                                        spn_Month2.setVisibility(View.VISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView30_year.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 2) {
                                        slc = "3";
                                        Type_pdf = "12";
                                        txtname.setText("Report_Sum_Payout_Sub.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("ปี");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.VISIBLE);
                                        txtyear.setVisibility(View.VISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            break;
                        case 13:
                            Type_pdf = "13";
                            txtname.setText("Report_Payout_Sub.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 14:
                            Type_pdf = "14";
                            txtname.setText("Report_Label.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 15:
                            Type_pdf = "15";
                            txtname.setText("Report_ATP.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 16:
                            Type_pdf = "16";
                            txtname.setText("Report_Test_Machine_Tosi.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 17:
                            Type_pdf = "17";
                            txtname.setText("Report_Test_Machine_Sono.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 18:
                            Type_pdf = "18";
                            txtname.setText("Report_Spore_Test.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.VISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.VISIBLE);
                            spn_type_spore.setVisibility(View.VISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            spn_type_spore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position == 0) {
                                        slc = "1";
                                        Type_pdf = "18";
                                        txtname.setText("Report_Spore_Test.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("วันที่เอกสาร");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.VISIBLE);
                                        txtedate.setVisibility(View.VISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.INVISIBLE);
                                        textView24_2.setVisibility(View.VISIBLE);
                                        spn_type_spore.setVisibility(View.VISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 1) {
                                        slc = "2";
                                        Type_pdf = "18";
                                        txtname.setText("Report_Spore_Test.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("วันที่เอกสาร");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.VISIBLE);
                                        txtedate.setVisibility(View.VISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.INVISIBLE);
                                        textView24_2.setVisibility(View.VISIBLE);
                                        spn_type_spore.setVisibility(View.VISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);
                                    } else if (position == 2) {
                                        slc = "3";
                                        Type_pdf = "18";
                                        txtname.setText("Report_Spore_Test.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("วันที่เอกสาร");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.VISIBLE);
                                        txtedate.setVisibility(View.VISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.INVISIBLE);
                                        textView24_2.setVisibility(View.VISIBLE);
                                        spn_type_spore.setVisibility(View.VISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            break;
                        case 19:
                            Type_pdf = "19";
                            txtname.setText("Report_Bowie_Dick_Test.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 20:
                            Type_pdf = "20";
                            txtname.setText("Report_Leak_Test.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 21:
                            Type_pdf = "21";
                            txtname.setText("Report_Sum_Receive_linen.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.VISIBLE);
                            textView24_1.setVisibility(View.VISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            spn_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position == 0) {
                                        slc = "1";
                                        Type_pdf = "21";
                                        txtname.setText("Report_Sum_Receive_linen.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("วันที่เอกสาร");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.VISIBLE);
                                        txtedate.setVisibility(View.VISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 1) {
                                        slc = "2";
                                        Type_pdf = "21";
                                        txtname.setText("Report_Sum_Receive_linen.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("เดือน");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.VISIBLE);
                                        spn_Month.setVisibility(View.VISIBLE);
                                        spn_Month2.setVisibility(View.VISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView30_year.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 2) {
                                        slc = "3";
                                        Type_pdf = "21";
                                        txtname.setText("Report_Sum_Receive_linen.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("ปี");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.VISIBLE);
                                        txtyear.setVisibility(View.VISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            break;
                        case 22:
                            Type_pdf = "22";
                            txtname.setText("Report_Sum_EXP.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.VISIBLE);
                            textView24_1.setVisibility(View.VISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            spn_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position == 0) {
                                        slc = "1";
                                        Type_pdf = "22";
                                        txtname.setText("Report_Sum_EXP.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("วันที่เอกสาร");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.VISIBLE);
                                        txtedate.setVisibility(View.VISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 1) {
                                        slc = "2";
                                        Type_pdf = "22";
                                        txtname.setText("Report_Sum_EXP.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("เดือน");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.VISIBLE);
                                        spn_Month.setVisibility(View.VISIBLE);
                                        spn_Month2.setVisibility(View.VISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView30_year.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 2) {
                                        slc = "3";
                                        Type_pdf = "22";
                                        txtname.setText("Report_Sum_EXP.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("ปี");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.VISIBLE);
                                        txtyear.setVisibility(View.VISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            break;
                        case 23:
                            Type_pdf = "23";
                            txtname.setText("Report_Sterile_New.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.VISIBLE);
                            textView24_1.setVisibility(View.VISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            spn_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position == 0) {
                                        slc = "1";
                                        Type_pdf = "23";
                                        txtname.setText("Report_Sterile_New.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("วันที่เอกสาร");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.VISIBLE);
                                        txtedate.setVisibility(View.VISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 1) {
                                        slc = "2";
                                        Type_pdf = "23";
                                        txtname.setText("Report_Sterile_New.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("เดือน");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.VISIBLE);
                                        spn_Month.setVisibility(View.VISIBLE);
                                        spn_Month2.setVisibility(View.VISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView30_year.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 2) {
                                        slc = "3";
                                        Type_pdf = "23";
                                        txtname.setText("Report_Sterile_New.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("ปี");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.VISIBLE);
                                        txtyear.setVisibility(View.VISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            break;
                        case 24:
                            Type_pdf = "24";
                            txtname.setText("Report_Wash_Mac_Round.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.VISIBLE);
                            textView24_1.setVisibility(View.VISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            spn_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position == 0) {
                                        slc = "1";
                                        Type_pdf = "24";
                                        txtname.setText("Report_Wash_Mac_Round.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("วันที่เอกสาร");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.VISIBLE);
                                        txtedate.setVisibility(View.VISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 1) {
                                        slc = "2";
                                        Type_pdf = "24";
                                        txtname.setText("Report_Wash_Mac_Round.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("เดือน");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.VISIBLE);
                                        spn_Month.setVisibility(View.VISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.INVISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView30_year.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 2) {
                                        slc = "3";
                                        Type_pdf = "24";
                                        txtname.setText("Report_Wash_Mac_Round.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("ปี");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.VISIBLE);
                                        txtyear.setVisibility(View.VISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            break;
                        case 25:
                            Type_pdf = "25";
                            txtname.setText("Report_Sterile_Mac_Round.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.VISIBLE);
                            textView24_1.setVisibility(View.VISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            spn_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position == 0) {
                                        slc = "1";
                                        Type_pdf = "25";
                                        txtname.setText("Report_Sterile_Mac_Round.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("วันที่เอกสาร");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.VISIBLE);
                                        txtedate.setVisibility(View.VISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 1) {
                                        slc = "2";
                                        Type_pdf = "25";
                                        txtname.setText("Report_Sterile_Mac_Round.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("เดือน");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.VISIBLE);
                                        spn_Month.setVisibility(View.VISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.INVISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView30_year.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 2) {
                                        slc = "3";
                                        Type_pdf = "25";
                                        txtname.setText("Report_Sterile_Mac_Round.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("ปี");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.VISIBLE);
                                        txtyear.setVisibility(View.VISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            break;
                        case 26:
                            Type_pdf = "26";
                            txtname.setText("Report_Stock_CSSD.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.INVISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.INVISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.VISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.VISIBLE);
                            spn_type_spore1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position == 0) {
                                        slc = "1";
                                        Type_pdf = "26";
                                        txtname.setText("Report_Stock_CSSD.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("วันที่เอกสาร");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        spn_type.setVisibility(View.INVISIBLE);
                                        textView24_1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        textView24_2.setVisibility(View.VISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.VISIBLE);

                                    } else if (position == 1) {
                                        slc = "2";
                                        Type_pdf = "26";
                                        txtname.setText("Report_Stock_CSSD2.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("วันที่เอกสาร");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        spn_type.setVisibility(View.INVISIBLE);
                                        textView24_1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        textView24_2.setVisibility(View.VISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.VISIBLE);
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            break;
                    }
                }else if (xSel == "1"){
                    switch (position) {
                        case 0:
                            Type_pdf = "0";
                            txtname.setText("Report_Itemstock.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.INVISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.INVISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 1:
                            Type_pdf = "1";
                            txtname.setText("Report_Washing.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.VISIBLE);
                            txtdept.setVisibility(View.VISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.VISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 2:
                            Type_pdf = "5";
                            txtname.setText("Report_Summary_Payout.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.VISIBLE);
                            txtdept.setVisibility(View.VISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 3:
                            Type_pdf = "1";
                            txtname.setText("Report_Broken.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 4:
                            Type_pdf = "0";
                            txtname.setText("Report_Occurance.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 5:
                            Type_pdf = "9";
                            switch2.setVisibility(View.VISIBLE);
                            if (switch2.isChecked()) {
                                txtname.setText("Report_TraceBack.php");
                                txtname.setTextColor(Color.BLACK);
                                txt_date30.setText("รหัสใช้งาน");
                                txtdate.setEnabled(true);
                                text_search.setVisibility(View.VISIBLE);
                                usage_text.setVisibility(View.VISIBLE);
                                switch2.setVisibility(View.VISIBLE);
                                spn_mr.setVisibility(View.INVISIBLE);
                                dtUsageCode.setVisibility(View.VISIBLE);
                                txtdate.setVisibility(View.INVISIBLE);
                                txtedate.setVisibility(View.INVISIBLE);
                                totxt.setVisibility(View.INVISIBLE);
                                spn_dept.setVisibility(View.INVISIBLE);
                                txtdept.setVisibility(View.INVISIBLE);
                                spn_Year.setVisibility(View.INVISIBLE);
                                spn_Month.setVisibility(View.INVISIBLE);
                                spn_Month2.setVisibility(View.INVISIBLE);
                                spn_program.setVisibility(View.INVISIBLE);
                                txt_year.setVisibility(View.INVISIBLE);
                                txt_program.setVisibility(View.INVISIBLE);
                                spn_Hours.setVisibility(View.INVISIBLE);
                                spinnertype.setVisibility(View.INVISIBLE);
                                spinnertype1.setVisibility(View.INVISIBLE);
                                txt_date30.setVisibility(View.VISIBLE);
                                txt_date30_1.setVisibility(View.INVISIBLE);
                                txt_date30_2.setVisibility(View.INVISIBLE);
                                spn_type.setVisibility(View.INVISIBLE);
                                textView24_1.setVisibility(View.INVISIBLE);
                                textView30_year.setVisibility(View.INVISIBLE);
                                e_year.setVisibility(View.INVISIBLE);
                                txtyear.setVisibility(View.INVISIBLE);
                                textView24_2.setVisibility(View.INVISIBLE);
                                spn_type_spore.setVisibility(View.INVISIBLE);
                                spn_type_spore1.setVisibility(View.INVISIBLE);
                                String myFormat = "dd/MM/yyyy"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                getMac(sdf.format(myCalendar.getTime()));
                            } else {
                                // UsageCode
                                txtname.setText("Report_Trace_Back1.php");
                                txtname.setTextColor(Color.BLACK);
                                txtdate.setEnabled(true);
                                text_search.setVisibility(View.VISIBLE);
                                usage_text.setVisibility(View.VISIBLE);
                                switch2.setVisibility(View.VISIBLE);
                                txt_date30.setText("วันที่เอกสาร");
                                String cc = txtdate.getText().subSequence(6, 10) + "-" + txtdate.getText().subSequence(3, 5) + "-" + txtdate.getText().subSequence(0, 2) + "";
                                listmr(cc);
                                spn_mr.setVisibility(View.INVISIBLE);
                                dtUsageCode.setVisibility(View.INVISIBLE);
                                txtdate.setVisibility(View.VISIBLE);
                                txtedate.setVisibility(View.INVISIBLE);
                                totxt.setVisibility(View.INVISIBLE);
                                spn_dept.setVisibility(View.INVISIBLE);
                                txtdept.setVisibility(View.INVISIBLE);
                                spn_Year.setVisibility(View.INVISIBLE);
                                spn_Month.setVisibility(View.INVISIBLE);
                                spn_Month2.setVisibility(View.INVISIBLE);
                                spn_program.setVisibility(View.INVISIBLE);
                                txt_year.setVisibility(View.INVISIBLE);
                                txt_program.setVisibility(View.INVISIBLE);
                                spn_Hours.setVisibility(View.INVISIBLE);
                                spinnertype.setVisibility(View.VISIBLE);
                                spinnertype1.setVisibility(View.VISIBLE);
                                txt_date30.setVisibility(View.VISIBLE);
                                txt_date30_1.setVisibility(View.VISIBLE);
                                txt_date30_2.setVisibility(View.VISIBLE);
                                spn_type.setVisibility(View.INVISIBLE);
                                textView24_1.setVisibility(View.INVISIBLE);
                                textView30_year.setVisibility(View.INVISIBLE);
                                e_year.setVisibility(View.INVISIBLE);
                                txtyear.setVisibility(View.INVISIBLE);
                                textView24_2.setVisibility(View.INVISIBLE);
                                spn_type_spore.setVisibility(View.INVISIBLE);
                                spn_type_spore1.setVisibility(View.INVISIBLE);
                                String myFormat = "dd/MM/yyyy"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                getMac(sdf.format(myCalendar.getTime()));
                            }
                            break;
                        case 6:
                            Type_pdf = "10";
                            txtname.setText("Report_Receive.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 7:
                            Type_pdf = "11";
                            txtname.setText("Report_Sum_Receive.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.VISIBLE);
                            textView24_1.setVisibility(View.VISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            spn_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position == 0) {
                                        slc = "1";
                                        Type_pdf = "11";
                                        txtname.setText("Report_Sum_Receive.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("วันที่เอกสาร");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.VISIBLE);
                                        txtedate.setVisibility(View.VISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 1) {
                                        slc = "2";
                                        Type_pdf = "11";
                                        txtname.setText("Report_Sum_Receive.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("เดือน");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.VISIBLE);
                                        spn_Month.setVisibility(View.VISIBLE);
                                        spn_Month2.setVisibility(View.VISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView30_year.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 2) {
                                        slc = "3";
                                        Type_pdf = "11";
                                        txtname.setText("Report_Sum_Receive.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("ปี");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.VISIBLE);
                                        txtyear.setVisibility(View.VISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            break;
                        case 8:
                            Type_pdf = "15";
                            txtname.setText("Report_ATP.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 9:
                            Type_pdf = "16";
                            txtname.setText("Report_Test_Machine_Tosi.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 10:
                            Type_pdf = "17";
                            txtname.setText("Report_Test_Machine_Sono.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                    }
                }else if (xSel == "2"){
                    switch (position) {
                        case 0:
                            Type_pdf = "0";
                            txtname.setText("Report_Itemstock.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.INVISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.INVISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 1:
                            Type_pdf = "3";
                            txtname.setText("Report_Sterile.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 2:
                            Type_pdf = "2";
                            txtname.setText("Report_Use_Resterile.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.INVISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.VISIBLE);
                            spn_Month.setVisibility(View.VISIBLE);
                            spn_Month2.setVisibility(View.VISIBLE);
                            spn_program.setVisibility(View.VISIBLE);
                            txt_year.setVisibility(View.VISIBLE);
                            txt_program.setVisibility(View.VISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 3:
                            Type_pdf = "9";
                            switch2.setVisibility(View.VISIBLE);
                            if (switch2.isChecked()) {
                                txtname.setText("Report_TraceBack.php");
                                txtname.setTextColor(Color.BLACK);
                                txt_date30.setText("รหัสใช้งาน");
                                txtdate.setEnabled(true);
                                text_search.setVisibility(View.VISIBLE);
                                usage_text.setVisibility(View.VISIBLE);
                                switch2.setVisibility(View.VISIBLE);
                                spn_mr.setVisibility(View.INVISIBLE);
                                dtUsageCode.setVisibility(View.VISIBLE);
                                txtdate.setVisibility(View.INVISIBLE);
                                txtedate.setVisibility(View.INVISIBLE);
                                totxt.setVisibility(View.INVISIBLE);
                                spn_dept.setVisibility(View.INVISIBLE);
                                txtdept.setVisibility(View.INVISIBLE);
                                spn_Year.setVisibility(View.INVISIBLE);
                                spn_Month.setVisibility(View.INVISIBLE);
                                spn_Month2.setVisibility(View.INVISIBLE);
                                spn_program.setVisibility(View.INVISIBLE);
                                txt_year.setVisibility(View.INVISIBLE);
                                txt_program.setVisibility(View.INVISIBLE);
                                spn_Hours.setVisibility(View.INVISIBLE);
                                spinnertype.setVisibility(View.INVISIBLE);
                                spinnertype1.setVisibility(View.INVISIBLE);
                                txt_date30.setVisibility(View.VISIBLE);
                                txt_date30_1.setVisibility(View.INVISIBLE);
                                txt_date30_2.setVisibility(View.INVISIBLE);
                                spn_type.setVisibility(View.INVISIBLE);
                                textView24_1.setVisibility(View.INVISIBLE);
                                textView30_year.setVisibility(View.INVISIBLE);
                                e_year.setVisibility(View.INVISIBLE);
                                txtyear.setVisibility(View.INVISIBLE);
                                textView24_2.setVisibility(View.INVISIBLE);
                                spn_type_spore.setVisibility(View.INVISIBLE);
                                spn_type_spore1.setVisibility(View.INVISIBLE);
                                String myFormat = "dd/MM/yyyy"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                getMac(sdf.format(myCalendar.getTime()));
                            } else {
                                // UsageCode
                                txtname.setText("Report_Trace_Back1.php");
                                txtname.setTextColor(Color.BLACK);
                                txtdate.setEnabled(true);
                                text_search.setVisibility(View.VISIBLE);
                                usage_text.setVisibility(View.VISIBLE);
                                switch2.setVisibility(View.VISIBLE);
                                txt_date30.setText("วันที่เอกสาร");
                                String cc = txtdate.getText().subSequence(6, 10) + "-" + txtdate.getText().subSequence(3, 5) + "-" + txtdate.getText().subSequence(0, 2) + "";
                                listmr(cc);
                                spn_mr.setVisibility(View.INVISIBLE);
                                dtUsageCode.setVisibility(View.INVISIBLE);
                                txtdate.setVisibility(View.VISIBLE);
                                txtedate.setVisibility(View.INVISIBLE);
                                totxt.setVisibility(View.INVISIBLE);
                                spn_dept.setVisibility(View.INVISIBLE);
                                txtdept.setVisibility(View.INVISIBLE);
                                spn_Year.setVisibility(View.INVISIBLE);
                                spn_Month.setVisibility(View.INVISIBLE);
                                spn_Month2.setVisibility(View.INVISIBLE);
                                spn_program.setVisibility(View.INVISIBLE);
                                txt_year.setVisibility(View.INVISIBLE);
                                txt_program.setVisibility(View.INVISIBLE);
                                spn_Hours.setVisibility(View.INVISIBLE);
                                spinnertype.setVisibility(View.VISIBLE);
                                spinnertype1.setVisibility(View.VISIBLE);
                                txt_date30.setVisibility(View.VISIBLE);
                                txt_date30_1.setVisibility(View.VISIBLE);
                                txt_date30_2.setVisibility(View.VISIBLE);
                                spn_type.setVisibility(View.INVISIBLE);
                                textView24_1.setVisibility(View.INVISIBLE);
                                textView30_year.setVisibility(View.INVISIBLE);
                                e_year.setVisibility(View.INVISIBLE);
                                txtyear.setVisibility(View.INVISIBLE);
                                textView24_2.setVisibility(View.INVISIBLE);
                                spn_type_spore.setVisibility(View.INVISIBLE);
                                spn_type_spore1.setVisibility(View.INVISIBLE);
                                String myFormat = "dd/MM/yyyy"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                getMac(sdf.format(myCalendar.getTime()));
                            }
                            break;
                        case 4:
                            Type_pdf = "0";
                            txtname.setText("Report_Occurance.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 5:
                            Type_pdf = "14";
                            txtname.setText("Report_Label.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 6:
                            Type_pdf = "15";
                            txtname.setText("Report_ATP.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 7:
                            Type_pdf = "18";
                            txtname.setText("Report_Spore_Test.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.VISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.VISIBLE);
                            spn_type_spore.setVisibility(View.VISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            spn_type_spore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position == 0) {
                                        slc = "1";
                                        Type_pdf = "18";
                                        txtname.setText("Report_Spore_Test.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("วันที่เอกสาร");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.VISIBLE);
                                        txtedate.setVisibility(View.VISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.INVISIBLE);
                                        textView24_2.setVisibility(View.VISIBLE);
                                        spn_type_spore.setVisibility(View.VISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 1) {
                                        slc = "2";
                                        Type_pdf = "18";
                                        txtname.setText("Report_Spore_Test.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("วันที่เอกสาร");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.VISIBLE);
                                        txtedate.setVisibility(View.VISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.INVISIBLE);
                                        textView24_2.setVisibility(View.VISIBLE);
                                        spn_type_spore.setVisibility(View.VISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);
                                    } else if (position == 2) {
                                        slc = "3";
                                        Type_pdf = "18";
                                        txtname.setText("Report_Spore_Test.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("วันที่เอกสาร");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.VISIBLE);
                                        txtedate.setVisibility(View.VISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.INVISIBLE);
                                        textView24_2.setVisibility(View.VISIBLE);
                                        spn_type_spore.setVisibility(View.VISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            break;
                        case 8:
                            Type_pdf = "19";
                            txtname.setText("Report_Bowie_Dick_Test.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 9:
                            Type_pdf = "20";
                            txtname.setText("Report_Leak_Test.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                    }
                }else if (xSel == "3"){
                    switch (position) {
                        case 0:
                            Type_pdf = "0";
                            txtname.setText("Report_Itemstock.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.INVISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.INVISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 1:
                            Type_pdf = "0";
                            txtname.setText("Report_Recall.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 2:
                            Type_pdf = "5";
                            txtname.setText("Report_Summary_Payout.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.VISIBLE);
                            txtdept.setVisibility(View.VISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 3:
                            Type_pdf = "5";
                            txtname.setText("Report_Set_Order.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.INVISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.INVISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 4:
                            Type_pdf = "9";
                            switch2.setVisibility(View.VISIBLE);
                            if (switch2.isChecked()) {
                                txtname.setText("Report_TraceBack.php");
                                txtname.setTextColor(Color.BLACK);
                                txt_date30.setText("รหัสใช้งาน");
                                txtdate.setEnabled(true);
                                text_search.setVisibility(View.VISIBLE);
                                usage_text.setVisibility(View.VISIBLE);
                                switch2.setVisibility(View.VISIBLE);
                                spn_mr.setVisibility(View.INVISIBLE);
                                dtUsageCode.setVisibility(View.VISIBLE);
                                txtdate.setVisibility(View.INVISIBLE);
                                txtedate.setVisibility(View.INVISIBLE);
                                totxt.setVisibility(View.INVISIBLE);
                                spn_dept.setVisibility(View.INVISIBLE);
                                txtdept.setVisibility(View.INVISIBLE);
                                spn_Year.setVisibility(View.INVISIBLE);
                                spn_Month.setVisibility(View.INVISIBLE);
                                spn_Month2.setVisibility(View.INVISIBLE);
                                spn_program.setVisibility(View.INVISIBLE);
                                txt_year.setVisibility(View.INVISIBLE);
                                txt_program.setVisibility(View.INVISIBLE);
                                txt_date30.setVisibility(View.VISIBLE);
                                txt_date30_1.setVisibility(View.INVISIBLE);
                                txt_date30_2.setVisibility(View.INVISIBLE);
                                spn_Hours.setVisibility(View.INVISIBLE);
                                spinnertype.setVisibility(View.INVISIBLE);
                                spinnertype1.setVisibility(View.INVISIBLE);
                                spn_type.setVisibility(View.INVISIBLE);
                                textView24_1.setVisibility(View.INVISIBLE);
                                textView30_year.setVisibility(View.INVISIBLE);
                                e_year.setVisibility(View.INVISIBLE);
                                txtyear.setVisibility(View.INVISIBLE);
                                textView24_2.setVisibility(View.INVISIBLE);
                                spn_type_spore.setVisibility(View.INVISIBLE);
                                spn_type_spore1.setVisibility(View.INVISIBLE);
                                String myFormat = "dd/MM/yyyy"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                getMac(sdf.format(myCalendar.getTime()));
                            } else {
                                // UsageCode
                                txtname.setText("Report_Trace_Back1.php");
                                txtname.setTextColor(Color.BLACK);
                                txtdate.setEnabled(true);
                                text_search.setVisibility(View.VISIBLE);
                                usage_text.setVisibility(View.VISIBLE);
                                switch2.setVisibility(View.VISIBLE);
                                txt_date30.setText("วันที่เอกสาร");
                                String cc = txtdate.getText().subSequence(6, 10) + "-" + txtdate.getText().subSequence(3, 5) + "-" + txtdate.getText().subSequence(0, 2) + "";
                                listmr(cc);
                                spn_mr.setVisibility(View.INVISIBLE);
                                dtUsageCode.setVisibility(View.INVISIBLE);
                                txtdate.setVisibility(View.VISIBLE);
                                txtedate.setVisibility(View.INVISIBLE);
                                totxt.setVisibility(View.INVISIBLE);
                                spn_dept.setVisibility(View.INVISIBLE);
                                txtdept.setVisibility(View.INVISIBLE);
                                spn_Year.setVisibility(View.INVISIBLE);
                                spn_Month.setVisibility(View.INVISIBLE);
                                spn_Month2.setVisibility(View.INVISIBLE);
                                spn_program.setVisibility(View.INVISIBLE);
                                txt_year.setVisibility(View.INVISIBLE);
                                txt_program.setVisibility(View.INVISIBLE);
                                txt_date30.setVisibility(View.VISIBLE);
                                txt_date30_1.setVisibility(View.VISIBLE);
                                txt_date30_2.setVisibility(View.VISIBLE);
                                spn_Hours.setVisibility(View.INVISIBLE);
                                spinnertype.setVisibility(View.VISIBLE);
                                spinnertype1.setVisibility(View.VISIBLE);
                                spn_type.setVisibility(View.INVISIBLE);
                                textView24_1.setVisibility(View.INVISIBLE);
                                textView30_year.setVisibility(View.INVISIBLE);
                                e_year.setVisibility(View.INVISIBLE);
                                txtyear.setVisibility(View.INVISIBLE);
                                textView24_2.setVisibility(View.INVISIBLE);
                                spn_type_spore.setVisibility(View.INVISIBLE);
                                spn_type_spore1.setVisibility(View.INVISIBLE);
                                String myFormat = "dd/MM/yyyy"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                getMac(sdf.format(myCalendar.getTime()));
                            }
                            break;
                        case 5:
                            Type_pdf = "0";
                            txtname.setText("Report_Occurance.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                        case 6:
                            Type_pdf = "12";
                            txtname.setText("Report_Sum_Payout_Sub.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.VISIBLE);
                            totxt.setVisibility(View.VISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.VISIBLE);
                            textView24_1.setVisibility(View.VISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            spn_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position == 0) {
                                        slc = "1";
                                        Type_pdf = "12";
                                        txtname.setText("Report_Sum_Payout_Sub.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("วันที่เอกสาร");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.VISIBLE);
                                        txtedate.setVisibility(View.VISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 1) {
                                        slc = "2";
                                        Type_pdf = "12";
                                        txtname.setText("Report_Sum_Payout_Sub.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("เดือน");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.VISIBLE);
                                        spn_Month.setVisibility(View.VISIBLE);
                                        spn_Month2.setVisibility(View.VISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.INVISIBLE);
                                        txtyear.setVisibility(View.INVISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView30_year.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);

                                    } else if (position == 2) {
                                        slc = "3";
                                        Type_pdf = "12";
                                        txtname.setText("Report_Sum_Payout_Sub.php");
                                        txtname.setTextColor(Color.BLACK);
                                        txt_date30.setText("ปี");
                                        txtdate.setEnabled(true);
                                        text_search.setVisibility(View.INVISIBLE);
                                        usage_text.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_mr.setVisibility(View.INVISIBLE);
                                        dtUsageCode.setVisibility(View.INVISIBLE);
                                        switch2.setVisibility(View.INVISIBLE);
                                        spn_dept.setVisibility(View.INVISIBLE);
                                        txtdept.setVisibility(View.INVISIBLE);
                                        spn_Year.setVisibility(View.INVISIBLE);
                                        spn_Month.setVisibility(View.INVISIBLE);
                                        spn_Month2.setVisibility(View.INVISIBLE);
                                        spn_program.setVisibility(View.INVISIBLE);
                                        txt_year.setVisibility(View.INVISIBLE);
                                        txt_program.setVisibility(View.INVISIBLE);
                                        spn_Hours.setVisibility(View.INVISIBLE);
                                        txt_date30_1.setVisibility(View.INVISIBLE);
                                        txt_date30_2.setVisibility(View.INVISIBLE);
                                        spinnertype.setVisibility(View.INVISIBLE);
                                        spinnertype1.setVisibility(View.INVISIBLE);
                                        textView30_year.setVisibility(View.INVISIBLE);
                                        e_year.setVisibility(View.VISIBLE);
                                        txtyear.setVisibility(View.VISIBLE);
                                        txt_date30.setVisibility(View.VISIBLE);
                                        txtdate.setVisibility(View.INVISIBLE);
                                        txtedate.setVisibility(View.INVISIBLE);
                                        totxt.setVisibility(View.VISIBLE);
                                        spn_type.setVisibility(View.VISIBLE);
                                        textView24_1.setVisibility(View.VISIBLE);
                                        textView24_2.setVisibility(View.INVISIBLE);
                                        spn_type_spore.setVisibility(View.INVISIBLE);
                                        spn_type_spore1.setVisibility(View.INVISIBLE);
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            break;
                        case 7:
                            Type_pdf = "13";
                            txtname.setText("Report_Payout_Sub.php");
                            txtname.setTextColor(Color.BLACK);
                            txt_date30.setText("วันที่เอกสาร");
                            txtdate.setEnabled(true);
                            text_search.setVisibility(View.INVISIBLE);
                            usage_text.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            spn_mr.setVisibility(View.INVISIBLE);
                            dtUsageCode.setVisibility(View.INVISIBLE);
                            switch2.setVisibility(View.INVISIBLE);
                            txtdate.setVisibility(View.VISIBLE);
                            txtedate.setVisibility(View.INVISIBLE);
                            totxt.setVisibility(View.INVISIBLE);
                            spn_dept.setVisibility(View.INVISIBLE);
                            txtdept.setVisibility(View.INVISIBLE);
                            spn_Year.setVisibility(View.INVISIBLE);
                            spn_Month.setVisibility(View.INVISIBLE);
                            spn_Month2.setVisibility(View.INVISIBLE);
                            spn_program.setVisibility(View.INVISIBLE);
                            txt_year.setVisibility(View.INVISIBLE);
                            txt_program.setVisibility(View.INVISIBLE);
                            txt_date30.setVisibility(View.VISIBLE);
                            txt_date30_1.setVisibility(View.INVISIBLE);
                            txt_date30_2.setVisibility(View.INVISIBLE);
                            spn_Hours.setVisibility(View.INVISIBLE);
                            spinnertype.setVisibility(View.INVISIBLE);
                            spinnertype1.setVisibility(View.INVISIBLE);
                            spn_type.setVisibility(View.INVISIBLE);
                            textView24_1.setVisibility(View.INVISIBLE);
                            textView30_year.setVisibility(View.INVISIBLE);
                            e_year.setVisibility(View.INVISIBLE);
                            txtyear.setVisibility(View.INVISIBLE);
                            textView24_2.setVisibility(View.INVISIBLE);
                            spn_type_spore.setVisibility(View.INVISIBLE);
                            spn_type_spore1.setVisibility(View.INVISIBLE);
                            break;
                    }
                }
            }
        });
    }

//    public void GetReport(final String Url,String eDate) {
//        class GetReport extends AsyncTask<String, Void, String> {
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                try {
//                    JSONObject jsonObj = new JSONObject(s);
//                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
//                    for(int i=0;i<setRs.length();i++){
//                        JSONObject c = setRs.getJSONObject(i);
//                        if(c.getString("bool").equals("true")) {
//
//                        }
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//                HashMap<String, String> data = new HashMap<String,String>();
//                data.put("eDate",params[0]);
//                String result = ruc.sendPostRequest(iFt.GetReport(Url),data);
//                Log.d("test", data+"");
//                Log.d("test", result+"");
//                return  result;
//            }
//        }
//
//        GetReport ru = new GetReport();
//        ru.execute(Url,eDate);
//    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    public void ListDepartment() {
        class ListDepartment extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                List<String> list = new ArrayList<String>();
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    xDataDepartment.clear();
                    xDataDepartment.add("0");
                    list.add("ทุกแผนก");
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        xDataDepartment.add(c.getString("xId"));
                        list.add(c.getString("xName"));
                    }
                    ArrayAdapter<String> SpinnerList = new ArrayAdapter<String>(ReportActivity.this,
                            android.R.layout.simple_dropdown_item_1line, list);
                    spn_dept.setAdapter( SpinnerList );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                String result = ruc.sendPostRequest(iFt.getdepartment(),data);
                return  result;
            }
        }
        ListDepartment ru = new ListDepartment();
        ru.execute();
    }

    public void getyear(){
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= thisYear-1; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, years);
        spn_Year.setAdapter(adapter);
        e_year.setAdapter(adapter);
        txtyear.setAdapter(adapter);
    }

    public void month(){
        listmonth.add("มกราคม");
        listmonth.add("กุมภาพันธ์");
        listmonth.add("มีนาคม");
        listmonth.add("เมษายน");
        listmonth.add("พฤษภาคม");
        listmonth.add("มิถุนายน");
        listmonth.add("กรกฎาคม");
        listmonth.add("สิงหาคม");
        listmonth.add("กันยายน");
        listmonth.add("ตุลาคม");
        listmonth.add("พฤศจิกายน");
        listmonth.add("ธันวาคม");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listmonth);
        spn_Month.setAdapter(adapter);
        spn_Month2.setAdapter(adapter);
    }

    public void sethours(){
        listhours.add("ในเวลาราชการ");
        listhours.add("นอกเวลาราชการ");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listhours);
        spn_Hours.setAdapter(adapter);
    }

    public void listprogram() {
        class listprogram extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                List<String> list = new ArrayList<String>();
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    xProgram.clear();
                    xProgram.add("-");
                    list.add("ทุกโปรแกรม");
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        xProgram.add(c.getString("SR_ID"));
                        list.add(c.getString("SR_Name"));
                    }
                    ArrayAdapter<String> SpinnerList = new ArrayAdapter<String>(ReportActivity.this,
                            android.R.layout.simple_dropdown_item_1line, list);
                    spn_program.setAdapter( SpinnerList );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                String result = ruc.sendPostRequest( Url.URL + "/get_sterileProgram_report.php",data);
                return  result;
            }
        }
        listprogram ru = new listprogram();
        ru.execute();
    }

    public void listmr(String xDate) {
        class listprogram extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                List<String> list = new ArrayList<String>();
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    xProgram.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        xProgram.add(c.getString("DocNo"));
                        list.add(c.getString("mr"));
                    }
                    ArrayAdapter<String> SpinnerList = new ArrayAdapter<String>(ReportActivity.this,
                            android.R.layout.simple_dropdown_item_1line, list);
                    spn_mr.setAdapter( SpinnerList );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDate",params[0]);
                String result = ruc.sendPostRequest( Url.URL + "/get_sterile_mr_report.php",data);
                Log.d("BAKND",data+"");
                Log.d("BAKND",result);
                return  result;
            }
        }
        listprogram ru = new listprogram();
        ru.execute(xDate);
    }

    public void getMac(final String dateTime) {
        class getMac extends AsyncTask<String, Void, String> {
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
                    xProgram1.clear();
                    xProgram1.add("-");
                    xProgram3.clear();
                    ArrayList<String> list_sp1 =new  ArrayList<String>();
                    list_sp1.add("-");
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xCus = new pCustomer();
                        xProgram1.add(c.getString("Mac"));
                        xProgram3.add(c.getString("DocNo"));
                        list_sp1.add(c.getString("Mac"));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReportActivity.this,android.R.layout.simple_spinner_dropdown_item,list_sp1);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnertype.setAdapter(adapter);
                }catch (JSONException e){

                }
                spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            spinnertype1.setEnabled(false);
                        } else {
                            spinnertype1.setEnabled(true);
                            getRound(txtdate.getText().toString(), String.valueOf(spinnertype.getSelectedItem()));
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("B_ID",B_ID);
                data.put("date", String.valueOf(dateTime));
                String result = ruc.sendPostRequest(iFt.getmac_report(),data);
                Log.d("DKDHKDHD",data+"");
                Log.d("DKDHKDHD",result);
                return result;
            }
        }
        getMac ru = new getMac();
        ru.execute();
    }

    public void getRound(final String dateTime,final String MacType) {
        class getRound extends AsyncTask<String, Void, String> {
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
                    xProgram2.clear();
                    xProgram2.add("-");
                    ArrayList<String> list_sp =new  ArrayList<String>();
                    list_sp.add("-");
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xCus = new pCustomer();
                        xProgram2.add(c.getString("round"));
                        list_sp.add(c.getString("round"));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReportActivity.this,android.R.layout.simple_spinner_dropdown_item,list_sp);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnertype1.setAdapter(adapter);
                }catch (JSONException e){
                }
            }
            //class connect php RegisterUserClass important !!!!!!!
            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("B_ID",B_ID);
                data.put("MacType",MacType);
                data.put("date", String.valueOf(dateTime));
                String result = ruc.sendPostRequest(iFt.getround_report(),data);
                Log.d("DHJSJD",data+"");
                Log.d("DHJSJD",result+"");
                return result;
            }
        }
        getRound ru = new getRound();
        ru.execute();
    }

    public void setSpinnerStatus(){
        liststatus.add("-");
        liststatus.add("Dirty zone");
        liststatus.add("Clean zone");
        liststatus.add("Sterile zone");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, liststatus);
        zone_report.setAdapter(adapter);
    }

    public void setSpinnerTimeType(){
        liststatus1.add("รายวัน");
        liststatus1.add("รายเดือน");
        liststatus1.add("รายปี");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, liststatus1);
        spn_type.setAdapter(adapter);
    }

    public void setSpinnerSporeType(){
        liststatus2.add("4 ชั่วโมง (SA)");
        liststatus2.add("4 ชั่วโมง (EO)");
        liststatus2.add("24 นาที (SA)");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, liststatus2);
        spn_type_spore.setAdapter(adapter);
    }

    public void setSpinnerSporeType1(){
        liststatus3.add("รวมรหัสใช้งาน");
        liststatus3.add("แยกรหัสใช้งาน");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, liststatus3);
        spn_type_spore1.setAdapter(adapter);
    }

}
