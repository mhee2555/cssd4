package com.phc.cssd.master_data;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.R;
import com.phc.cssd.adapter.Adapter_Timetable;
import com.phc.cssd.properties.Response_Timetable;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class TimeTableActivity extends AppCompatActivity {

    ArrayList<String> arrYear = new ArrayList<String>();
    ArrayList<String> arrMonth = new ArrayList<String>();
    ArrayList<String> arrWeek = new ArrayList<String>();
    ArrayList<Response_Timetable> arrList = new ArrayList<Response_Timetable>();
    Button btnSearch;
    Button btnSave;
    Button button_employee;
    Button button_user;
    ImageView ImageBack;
    ListView listView;
    Spinner spnYear;
    Spinner spnMonth;
    Spinner spnWeek;
    Button tEmp1;
    Button tEmp2;
    Button tEmp3;
    LinearLayout Li1;
    String xID="";

    boolean flag = true;
    boolean saveflag = false;

    String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_time_table);

        getSupportActionBar().hide();
        initialize();
    }

    private void initialize() {
        button_employee = (Button) findViewById(R.id.button_employee);
        button_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeTableActivity.this, EmployeeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        button_user = (Button) findViewById(R.id.button_user);
        button_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeTableActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSearch = (Button) findViewById(R.id.b_Search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListData(spnYear.getSelectedItem().toString(),(spnMonth.getSelectedItemPosition()+1)+"",spnWeek.getSelectedItem().toString());
                tEmp1.setText("");
                tEmp2.setText("");
                tEmp3.setText("");
            }
        });
        btnSave = (Button) findViewById(R.id.b_Save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tEmp1.getText().toString().equals("") && !tEmp2.getText().toString().equals("") && !tEmp3.getText().toString().equals("")){
                    SetData(spnYear.getSelectedItem().toString(),(spnMonth.getSelectedItemPosition()+1)+"",spnWeek.getSelectedItem().toString(),tEmp1.getText().toString(),tEmp2.getText().toString(),tEmp3.getText().toString(),xID);
                    ListData(spnYear.getSelectedItem().toString(),(spnMonth.getSelectedItemPosition()+1)+"",spnWeek.getSelectedItem().toString());
                }else{
                    Toast.makeText(TimeTableActivity.this, "กรุณากรอกรายชื่อผู้เตรียม/ผู้ฆ่าเชื้อ/ผู้ตรวจ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageBack = (ImageView) findViewById(R.id.imageBack);
        ImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Li1 = (LinearLayout) findViewById(R.id.Li1);
        listView = (ListView) findViewById(R.id.listView);
        spnYear = (Spinner) findViewById(R.id.tYear);
        spnMonth = (Spinner) findViewById(R.id.tMonth);
        spnMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CreateSpnWeek(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnWeek = (Spinner) findViewById(R.id.tWeek);
        tEmp1 = (Button) findViewById(R.id.tEmp1);
        tEmp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TimeTableActivity.this, employee_search.class);
                i.putExtra("number","1");
                startActivityForResult(i,111);
            }
        });
        tEmp2 = (Button) findViewById(R.id.tEmp2);
        tEmp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TimeTableActivity.this, employee_search.class);
                i.putExtra("number","2");
                startActivityForResult(i,111);
            }
        });
        tEmp3 = (Button) findViewById(R.id.tEmp3);
        tEmp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TimeTableActivity.this, employee_search.class);
                i.putExtra("number","3");
                startActivityForResult(i,111);
            }
        });

        CreateSpnYear();
        CreateSpnMonth();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if(data.getStringExtra("StrData")!=null) {
                String RETURN_DATA = data.getStringExtra("StrData");
                if (!RETURN_DATA.equals("")) {
                    String[] separated = RETURN_DATA.split("/");
                    if (separated[3].equals("1")) {
                        tEmp1.setText(separated[1] + " (" + separated[0] + ") " + " (แผนก : " + separated[2] + ")");
                    } else if (separated[3].equals("2")) {
                        tEmp2.setText(separated[1] + " (" + separated[0] + ") " + " (แผนก : " + separated[2] + ")");
                    } else {
                        tEmp3.setText(separated[1] + " (" + separated[0] + ") " + " (แผนก : " + separated[2] + ")");
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            return;
        }
    }

    private void CreateSpnWeek(int months) {
        String yearstr = spnYear.getSelectedItem().toString();
        int year = Integer.parseInt(yearstr.toString());
        int month = months+1;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        switch (month){
            case 1:
                cal.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case 2:
                cal.set(Calendar.MONTH, Calendar.FEBRUARY);
                break;
            case 3:
                cal.set(Calendar.MONTH, Calendar.MARCH);
                break;
            case 4:
                cal.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case 5:
                cal.set(Calendar.MONTH, Calendar.MAY);
                break;
            case 6:
                cal.set(Calendar.MONTH, Calendar.JUNE);
                break;
            case 7:
                cal.set(Calendar.MONTH, Calendar.JULY);
                break;
            case 8:
                cal.set(Calendar.MONTH, Calendar.AUGUST);
                break;
            case 9:
                cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
                break;
            case 10:
                cal.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            case 11:
                cal.set(Calendar.MONTH, Calendar.NOVEMBER);
                break;
            case 12:
                cal.set(Calendar.MONTH, Calendar.DECEMBER);
                break;
        }
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date date = cal.getTime();
        String strday = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        switch (strday){
            case "Monday":
                if(daysInMonth == 28){
                    SetandCreateLimitweek(4);
                }else{
                    SetandCreateLimitweek(5);
                }
                break;
            case "Saturday":
                if(daysInMonth == 31){
                    SetandCreateLimitweek(6);
                }else{
                    SetandCreateLimitweek(5);
                }
                break;
            case "Sunday":
                if(daysInMonth == 28 || daysInMonth == 29){
                    SetandCreateLimitweek(4);
                }else{
                    SetandCreateLimitweek(5);
                }
                break;

                default:
                    SetandCreateLimitweek(5);
                    break;

            }

    }

    private void SetandCreateLimitweek(int limit) {
        arrWeek.clear();
        arrWeek.add("1");
        arrWeek.add("2");
        arrWeek.add("3");
        arrWeek.add("4");
        arrWeek.add("5");
        arrWeek.add("6");
//        if(limit==5){
//            arrWeek.add("5");
//        }else if(limit==6){
//            arrWeek.add("5");
//            arrWeek.add("6");
//        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TimeTableActivity.this,android.R.layout.simple_spinner_dropdown_item,arrWeek);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnWeek.setAdapter(adapter);
    }

    private void CreateSpnMonth() {
        arrMonth.add("มกราคม");
        arrMonth.add("กุมภาพันธ์");
        arrMonth.add("มีนาคม");
        arrMonth.add("เมษายน");
        arrMonth.add("พฤษภาคม");
        arrMonth.add("มิถุนายน");
        arrMonth.add("กรกฎาคม");
        arrMonth.add("สิงหาคม");
        arrMonth.add("กันยายน");
        arrMonth.add("ตุลาคม");
        arrMonth.add("พฤศจิกายน");
        arrMonth.add("ธันวาคม");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TimeTableActivity.this,android.R.layout.simple_spinner_dropdown_item,arrMonth);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        spnMonth.setAdapter(adapter);
        spnMonth.setSelection(month);
    }

    private void CreateSpnYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String yearstr = Integer.valueOf(year).toString();
        arrYear.add(yearstr);
        arrYear.add(Integer.valueOf(year+1).toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TimeTableActivity.this,android.R.layout.simple_spinner_dropdown_item,arrYear);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnYear.setAdapter(adapter);
    }

    public void ListData(String Year,String Month,String Week) {
        class ListData extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Timetable newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    arrList.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Timetable();
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            newsData.setWeek(c.getString("Week"));
                            newsData.setEmp1(c.getString("Emp1"));
                            newsData.setEmp2(c.getString("Emp2"));
                            newsData.setEmp3(c.getString("Emp3"));
                            newsData.setMonth(c.getString("Month"));
                            newsData.setYear(c.getString("Year"));
                            newsData.setEmpCode1(c.getString("EmpCode1"));
                            newsData.setEmpCode2(c.getString("EmpCode2"));
                            newsData.setEmpCode3(c.getString("EmpCode3"));
                            newsData.setDepName1(c.getString("DepName1"));
                            newsData.setDepName2(c.getString("DepName2"));
                            newsData.setDepName3(c.getString("DepName3"));
                            newsData.setMonthno(c.getString("Monthno"));
                            newsData.setxID(c.getString("xID"));
                            arrList.add( newsData );
                        }

                    }
                    xID="";
                    listView.setAdapter(new Adapter_Timetable( TimeTableActivity.this, arrList));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Object o = listView.getItemAtPosition(position);
                            Response_Timetable newsData = (Response_Timetable) o;
//                            spnYear
                            arrYear.clear();
                            arrYear.add(newsData.getYear());
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(TimeTableActivity.this,android.R.layout.simple_spinner_dropdown_item,arrYear);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnYear.setAdapter(adapter);

//                            spnMonth
                            spnMonth.setSelection(Integer.valueOf(newsData.getMonthno())-1);
//                            spnWeek
                            spnWeek.setSelection(Integer.valueOf(newsData.getWeek())-1);
                            tEmp1.setText(newsData.getEmp1() + " (" + newsData.getEmpCode1() + ") " + " (แผนก : " + newsData.getDepName1() + ")");
                            tEmp2.setText(newsData.getEmp2() + " (" + newsData.getEmpCode2() + ") " + " (แผนก : " + newsData.getDepName2() + ")");
                            tEmp3.setText(newsData.getEmp3() + " (" + newsData.getEmpCode3() + ") " + " (แผนก : " + newsData.getDepName3() + ")");
                            xID= newsData.getxID();
                            saveflag = true;
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Year",params[0]);
                data.put("Month",params[1]);
                data.put("Week",params[2]);
                String result = ruc.sendPostRequest(iFt.getTimetable(),data);
                Log.d("DATATABLE", data+"");
                Log.d("RESULTTABLE", result);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Year,Month,Week );
    }

    public void SetData(String Year,String Month,String Week,String Emp1,String Emp2,String Emp3,String xID) {
        class SetData extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Timetable newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    arrList.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            Toast.makeText(TimeTableActivity.this, "บันทึกข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TimeTableActivity.this, "บันทึกข้อมูลล้มเหลว", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Year",params[0]);
                data.put("Month",params[1]);
                data.put("Week",params[2]);
                data.put("Emp1",params[3]);
                data.put("Emp2",params[4]);
                data.put("Emp3",params[5]);
                data.put("xID",params[6]);
                Log.d("data: ", data+"");
                String result = ruc.sendPostRequest(iFt.setTimetable(),data);
                return  result;
            }
        }

        SetData ru = new SetData();
        ru.execute( Year,Month,Week,Emp1,Emp2,Emp3,xID );
    }


}
