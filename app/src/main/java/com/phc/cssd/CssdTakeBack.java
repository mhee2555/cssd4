package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.string.Cons;
import com.phc.core.string.TwoZero;
import com.phc.cssd.adapter.ListPayoutDetailBorrowAdapter;
import com.phc.cssd.adapter.List_Payout_Borrow_Adapter;
import com.phc.cssd.model.ModelPayout;
import com.phc.cssd.model.ModelPayoutDetail;
import com.phc.cssd.url.Url;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CssdTakeBack extends AppCompatActivity {

    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();
    private String userid = null;
    private String B_ID = "";
    private int Qty1;
    String DocNo_Pay;
    String Remark_Pay;
    String IsStatus_Type = "1";
    private EditText edit_search;
    private TextView txt_doc_date;
    private TextView txt_doc_no;
    private TextView txt_qr;
    private CheckBox take_cssd;
    private TextView txt_qr1;
    private TextView txt_day;
    private TextView txt_dep;
    private TextView txt_1;
    private TextView txt_2;
    private TextView txt_3;
    SearchableSpinner searchable_spinner_department;
    private Button btn_calendar;
    private Button btn_search;
    private Button btn_qr;
    private ListView list_doc_no;
    private ListView list_detail;
    private ImageView imv_back;
    private ImageView imv_save;
    private ImageView imv_cancel;
    private Switch bt_switch;
    ArrayList<String> department_ar_text = new ArrayList<>();
    ArrayList<String> department_ar_value = new ArrayList<String>();
    ArrayList<String> payout_detail_itemstock = new ArrayList<>(Arrays.asList(""));
    private List<ModelPayout> ModelPayout;
    private List<ModelPayoutDetail> ModelPayoutDetail;
    static final int DATE_PICKER_ID = 1111;
    private int year;
    private int month;
    private int day;
    private String DocNo = null;
    boolean IsMoreSearch = false;

    public void onBackPressed() {
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cssd_take_back);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getSupportActionBar().hide();

        byIntent();

        byWidget();

        byEvent();

        byProperties();

        searchable_spinner_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                displayPayout();
                clearForm();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        displayPayout();
    }

    private void byIntent(){
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        B_ID = intent.getStringExtra("B_ID");
    }

    private void byWidget(){
        bt_switch = (Switch) findViewById(R.id.bt_switch);
        edit_search = (EditText) findViewById(R.id.edit_search);
        edit_search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            displayPayout();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        take_cssd = (CheckBox) findViewById(R.id.take_cssd);
        take_cssd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (take_cssd.isChecked()) {
                    txt_qr.setVisibility(View.GONE);
                    txt_qr1.setVisibility(View.VISIBLE);
                    txt_qr1.requestFocus();
                }else {
                    txt_qr.setVisibility(View.VISIBLE);
                    txt_qr1.setVisibility(View.GONE);
                    txt_qr.requestFocus();
                }
            }
        });
        txt_qr = (TextView) findViewById(R.id.txt_qr);
        txt_qr1 = (TextView) findViewById(R.id.txt_qr1);
        txt_doc_date = (TextView) findViewById(R.id.txt_doc_date);
        txt_doc_no = (TextView) findViewById(R.id.txt_doc_no);
        //spinner_department = (Spinner) findViewById(R.id.spinner_department);
        searchable_spinner_department = ( SearchableSpinner ) findViewById(R.id.searchable_spinner_department);
        btn_calendar = (Button) findViewById(R.id.btn_calendar);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_qr = (Button) findViewById(R.id.btn_qr);
        list_doc_no = (ListView) findViewById(R.id.list_doc_no);
        list_detail = (ListView) findViewById(R.id.list_detail);
        imv_back = (ImageView) findViewById(R.id.imv_back);
        imv_save = (ImageView) findViewById(R.id.imv_save);
        imv_cancel = (ImageView) findViewById(R.id.imv_cancel);
        txt_day = (TextView) findViewById(R.id.txt_day);
        txt_dep = (TextView) findViewById(R.id.txt_dep);
        txt_1 = (TextView) findViewById(R.id.txt_1);
        txt_2 = (TextView) findViewById(R.id.txt_2);
        txt_3 = (TextView) findViewById(R.id.txt_3);
    }

    private void byEvent(){
        bt_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edit_search.setEnabled(!bt_switch.isChecked());
                txt_doc_date.setEnabled(!bt_switch.isChecked());
                btn_calendar.setEnabled(!bt_switch.isChecked());
                txt_doc_date.setText(bt_switch.isChecked() ? "" : (year + "-" + TwoZero.get((month + 1) + "") + "-" + TwoZero.get(day + "") ) );
                edit_search.setText("");
                displayPayout();
                if (isChecked) {
                    txt_dep.setTextColor(Color.RED);
                    txt_day.setTextColor(Color.BLACK);
                    txt_day.setVisibility(View.GONE);
                    txt_dep.setVisibility(View.VISIBLE);
                }else {
                    txt_day.setTextColor(Color.RED);
                    txt_dep.setTextColor(Color.BLACK);
                    txt_dep.setVisibility(View.GONE);
                    txt_day.setVisibility(View.VISIBLE);
                }
            }
        });

        txt_doc_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_ID);
            }
        });

        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_ID);
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                displayPayout();
                hideKeyboard(CssdTakeBack.this);
            }
        });

        list_doc_no.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DocNo = ((TextView)((RelativeLayout)((RelativeLayout)((RelativeLayout) view).getChildAt(0)).getChildAt(0)).getChildAt(0) ).getText().toString();
                String Date = ((TextView)((RelativeLayout)((RelativeLayout)((RelativeLayout) view).getChildAt(0)).getChildAt(0)).getChildAt(1) ).getText().toString();
                onSelectItem(DocNo, Date);
                Object o = list_doc_no.getItemAtPosition(position);
                ModelPayout newsData = ( ModelPayout ) o;
                DocNo_Pay = newsData.getDocNo();
                Remark_Pay = newsData.getPayout_Status();
                if (Remark_Pay.equals("คืนอุปกรณ์เข้าจ่ายกลาง")){
                    IsStatus_Type = "2";
                }else if (Remark_Pay.equals("คืนอุปกรณ์เข้าจ่ายกลางแล้ว")){
                    IsStatus_Type = "2";
                }else {
                    IsStatus_Type = "1";
                }
                if (Remark_Pay.equals("คืนอุปกรณ์เข้าจ่ายกลาง")){
                    txt_1.setVisibility(View.GONE);
                    txt_2.setVisibility(View.GONE);
                    txt_3.setVisibility(View.GONE);
                    onDisplayPayoutDetail(DocNo_Pay,"2",IsStatus_Type);
                }else if (Remark_Pay.equals("คืนอุปกรณ์เข้าจ่ายกลางแล้ว")){
                    txt_1.setVisibility(View.GONE);
                    txt_2.setVisibility(View.GONE);
                    txt_3.setVisibility(View.GONE);
                    onDisplayPayoutDetail(DocNo_Pay,"2",IsStatus_Type);
                }else {
                    txt_1.setVisibility(View.VISIBLE);
                    txt_2.setVisibility(View.VISIBLE);
                    txt_3.setVisibility(View.VISIBLE);
                    onDisplayPayoutDetail(DocNo_Pay,"1",IsStatus_Type);
                }
                focus();
            }
        });

        imv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_detail.setAdapter(null);
                ModelPayoutDetail = null;
                clearForm();
                displayPayout();
            }
        });

        imv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateSendSterile();
            }
        });

        btn_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DocNo == null){
                    return;
                }
                findQr();
                final Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        focus();
                    }

                }, 500);
            }
        });

        txt_qr.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(DocNo == null){
                    return false;
                }
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            findQr();

                            final Handler h = new Handler();
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    focus();
                                }
                            }, 500);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        txt_qr1.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            findQr1();

                            final Handler h = new Handler();
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    focus();
                                }
                            }, 500);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }

    private void byProperties(){
        addDropDownDepartment();
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        txt_doc_date.setText(year + "-" + TwoZero.get((month + 1) + "") + "-" + TwoZero.get(day + ""));
        searchable_spinner_department.setTitle("เลือกแผนก");
        searchable_spinner_department.setPositiveButton("");
        searchable_spinner_department.requestFocus();
    }

    private void focus(){
        txt_qr.setText("");
        txt_qr.requestFocus();
        txt_qr1.setText("");
        txt_qr1.requestFocus();
    }

    private void clearForm(){
        DocNo = null;
        txt_doc_no.setText("");
        DocNo_Pay = null;
        list_detail.setAdapter(null);
        ModelPayoutDetail = null;
        payout_detail_itemstock = new ArrayList<>(Arrays.asList(""));
        txt_1.setVisibility(View.VISIBLE);
        txt_2.setVisibility(View.VISIBLE);
        txt_3.setVisibility(View.VISIBLE);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                return new DatePickerDialog(this, pickerListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            txt_doc_date.setText(year + "-" + TwoZero.get((month + 1) + "") + "-" + TwoZero.get(day + ""));
            displayPayout();
            clearForm();
        }
    };


    public void onSelectItem(String DocNo, String Date){
        txt_doc_no.setText("เอกสาร : " + DocNo + "       วันที่สร้าง : " + Date);
    }

    private void onCreateSendSterile(){
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(CssdTakeBack.this);
        quitDialog.setTitle(Cons.TITLE);
        quitDialog.setMessage(Cons.CONFIRM_TAKE_BACK);
        quitDialog.setPositiveButton(Cons.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(DocNo == null){
                    Toast.makeText(CssdTakeBack.this, "กรุณาเลือกเอกสารที่จะบันทึก !!", Toast.LENGTH_SHORT).show();
                    return ;
                }
                String DATA_ENTER = "";
                String DATA_QR = "";
                try {
                    List<ModelPayoutDetail> DATA_MODEL = ModelPayoutDetail;
                    Iterator li = DATA_MODEL.iterator();
                    while(li.hasNext()) {
                        try {
                            ModelPayoutDetail m = (ModelPayoutDetail) li.next();
                            if(!m.getEnter_Qty().trim().equals("") && !m.getEnter_Qty().equals("0")){
                                DATA_ENTER += m.getID() + "@" + m.getEnter_Qty() + "@" + m.getItemcode() + "@";
                            }
                        }catch(Exception e){
                            continue;
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                for(int i=1;i<payout_detail_itemstock.size();i++){
                    DATA_QR +=  payout_detail_itemstock.get(i)+ "@";
                }
                List<ModelPayoutDetail> DATA_MODEL = ModelPayoutDetail;
                Iterator li = DATA_MODEL.iterator();
                ModelPayoutDetail m = (ModelPayoutDetail) li.next();
                Log.d("FKDKDJ",m.getIsBorrowStatus());
                if (m.getIsBorrowStatus().equals("4")){
                    createSendSterile1(DocNo, DATA_ENTER, DATA_QR);
                }else {
                    createSendSterile(DocNo, DATA_ENTER, DATA_QR);
                }

            }
        });

        quitDialog.setNegativeButton(Cons.CANCEL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        quitDialog.show();
    }
    // =============================================================================================
    // Create Send Sterile
    // =============================================================================================
    public void createSendSterile(final String p_docno, final String p_data_enter, final String p_data_qr){
        class Create extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(CssdTakeBack.this);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setTitle(Cons.TITLE);
                this.dialog.setIcon(R.drawable.pose_favicon_2x);
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.setCanceledOnTouchOutside(false);
                this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0xEFFFFFFF));
                this.dialog.setIndeterminate(true);
                this.dialog.show();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for (int i = 0; i < rs.length(); i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (c.getString("result").equals("A")) {
                            clearForm();
                            displayPayout();
                        }
                    }
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("p_docno", p_docno);
                data.put("p_user_id", userid);
                if(!p_data_enter.equals("")) {
                    data.put("p_data_enter", p_data_enter);
                }
                if(!p_data_qr.equals("")) {
                    data.put("p_data_qr", p_data_qr);
                }
                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_create_send_sterile_by_borrow.php", data);
                Log.d("FKDKDJ",data+"");
                Log.d("FKDKDJ",result);
                return result;
            }
        }
        Create ru = new Create();
        ru.execute();
    }

    public void createSendSterile1(final String p_docno, final String p_data_enter, final String p_data_qr){
        class Create extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(CssdTakeBack.this);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setTitle(Cons.TITLE);
                this.dialog.setIcon(R.drawable.pose_favicon_2x);
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.setCanceledOnTouchOutside(false);
                this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0xEFFFFFFF));
                this.dialog.setIndeterminate(true);
                this.dialog.show();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for (int i = 0; i < rs.length(); i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (c.getString("result").equals("A")) {
                            clearForm();
                            displayPayout();
                        }
                    }
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("p_docno", p_docno);
                data.put("p_user_id", userid);
                if(!p_data_enter.equals("")) {
                    data.put("p_data_enter", p_data_enter);
                }
                if(!p_data_qr.equals("")) {
                    data.put("p_data_qr", p_data_qr);
                }
                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_create_send_sterile_by_borrow1.php", data);
                Log.d("FKDKDJ",data+"");
                Log.d("FKDKDJ",result);
                return result;
            }
        }
        Create ru = new Create();
        ru.execute();
    }

    // =============================================================================================
    // Define Department
    // =============================================================================================

    public void addDropDownDepartment(){
        class Add extends AsyncTask<String, Void, String> {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                ArrayList<String> ar_data = new ArrayList();
                ArrayList<String> ar_value = new ArrayList();
                ar_data.add("");
                ar_value.add("");
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for (int i = 0; i < rs.length(); i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (c.getString("result").equals("A")) {
                            ar_data.add(c.getString("DepName2"));
                            ar_value.add(c.getString("ID"));
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CssdTakeBack.this, android.R.layout.simple_spinner_dropdown_item, ar_data);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //spinner_department.setAdapter(adapter);
                            searchable_spinner_department.setAdapter(adapter);
                        }
                    }
                    department_ar_text = ar_data;
                    department_ar_value = ar_value;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_select_departments.php", data);
                return result;
            }
        }
        Add ru = new Add();
        ru.execute();
    }
    // =============================================================================================
    // Display Payout
    // =============================================================================================
    private void displayPayout(){
        final String p_date = txt_doc_date.getText().toString();
        final String p_search = edit_search.getText().toString();
        final String p_department_id = ( searchable_spinner_department.getSelectedItemPosition() <= 0) ? null : department_ar_value.get(searchable_spinner_department.getSelectedItemPosition());
        list_doc_no.setAdapter(null);
        if(bt_switch.isChecked() && p_department_id == null){
            Toast.makeText(CssdTakeBack.this, "ยังไม่ได้เลือกแผนก !!", Toast.LENGTH_SHORT).show();
            return;
        }
        final String p_is_check = bt_switch.isChecked() ? "1" : "0";
        class DisplayPayout extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(CssdTakeBack.this);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setTitle(Cons.TITLE);
                this.dialog.setIcon(R.drawable.pose_favicon_2x);
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.setCanceledOnTouchOutside(false);
                this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0xEFFFFFFF));
                this.dialog.setIndeterminate(true);
                this.dialog.show();
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                List<ModelPayout> list = new ArrayList<>();
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        if(c.getString("result").equals("A")) {
                            list.add(
                                    getPay(
                                            c.getString("Department_ID"),
                                            c.getString("DepName"),
                                            c.getString("DocNo"),
                                            c.getString("CreateDate"),
                                            c.getString("Qty"),
                                            c.getString("BorrowBalanceQty"),
                                            c.getString("Balance"),
                                            c.getString("IsStatus"),
                                            c.getString("Payout_Status"),
                                            i
                                    )
                            );
                        }else{
                            if(IsMoreSearch)
                                Toast.makeText(CssdTakeBack.this, c.getString("Message"), Toast.LENGTH_SHORT).show();
                            IsMoreSearch = true;
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ModelPayout = list;
                ArrayAdapter<ModelPayout> adapter;
                adapter = new List_Payout_Borrow_Adapter(CssdTakeBack.this, ModelPayout);
                list_doc_no.setAdapter(adapter);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("p_date", p_date);
                data.put("p_is_check", p_is_check);
                if(!p_search.equals(""))
                    data.put("p_search", p_search);
                if(p_department_id != null)
                    data.put("p_department_id", p_department_id);
                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_payout.php", data);
                    Log.d("FKDKDJ",data+"");
                    Log.d("FKDKDJ",result);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            private ModelPayout getPay(
                    String department_ID,
                    String depName,
                    String docNo,
                    String createDate,
                    String qty,
                    String borrowBalanceQty,
                    String balance,
                    String isStatus,
                    String payout_Status,
                    int index
            ) {
                return new ModelPayout(
                        department_ID,
                        depName,
                        docNo,
                        createDate,
                        qty,
                        borrowBalanceQty,
                        balance,
                        isStatus,
                        payout_Status,
                        index
                );
            }
            // =========================================================================================
        }
        DisplayPayout obj = new DisplayPayout();
        obj.execute();
    }
    // =============================================================================================
    // Display Payout Detail
    // =============================================================================================
    public void onDisplayPayoutDetail(final String p_docno, final String sel, final String type){
        class DisplayPayoutDetail extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(CssdTakeBack.this);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                list_detail.setAdapter(null);
                this.dialog.setTitle(Cons.TITLE);
                this.dialog.setIcon(R.drawable.pose_favicon_2x);
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.setCanceledOnTouchOutside(false);
                this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0xEFFFFFFF));
                this.dialog.setIndeterminate(true);
                this.dialog.show();
                ModelPayoutDetail = null;
                payout_detail_itemstock = new ArrayList<>(Arrays.asList(""));;
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                List<ModelPayoutDetail> list = new ArrayList<>();
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);

                        if(c.getString("result").equals("A")) {

                            list.add(
                                    new ModelPayoutDetail(
                                            c.getString("ID"),
                                            c.getString("DocNo"),
                                            c.getString("itemcode"),
                                            c.getString("itemname"),
                                            c.getString("ItemStockID"),
                                            c.getString("Qty"),
                                            c.getString("Borrow_Balance_Qty"),
                                            c.getString("Balance_Qty"),
                                            c.getString("QR_Qty"),
                                            "0",
                                            c.getString("IsBorrowStatus"),
                                            i
                                    )
                            );
                        }
                    }

                } catch (JSONException e) {
                    list_detail.setAdapter(null);
                    e.printStackTrace();
                }

                ModelPayoutDetail = list;

                ArrayAdapter<ModelPayoutDetail> adapter;
                adapter = new ListPayoutDetailBorrowAdapter(CssdTakeBack.this, ModelPayoutDetail, true,type);
                list_detail.setAdapter(adapter);

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                if(p_docno != null){
                    data.put("p_docno", p_docno);
                }

                if(sel != null){
                    data.put("sel", sel);
                }

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = null;

                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_payout_detail_borrow.php", data);
                    Log.d("KDHDKF",data+"");
                    Log.d("KDHDKF",result);
                }catch(Exception e){
                    e.printStackTrace();
                }

                return result;
            }


            // =========================================================================================
        }

        DisplayPayoutDetail obj = new DisplayPayoutDetail();
        obj.execute();
    }

    // =============================================================================================
    // Find QR
    // =============================================================================================

    public void findQr(){

        final String p_qr = txt_qr.getText().toString();

        class Find extends AsyncTask<String, Void, String> {

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
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < rs.length(); i++) {
                        JSONObject c = rs.getJSONObject(i);

                        if (c.getString("result").equals("A")) {

                            checkItemCodeInList(
                                    c.getString("RowID"),
                                    c.getString("ItemCode"),
                                    c.getString("UsageCode")
                            );
                        }else{
                            Toast.makeText(CssdTakeBack.this, c.getString("Message"), Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();

                data.put("p_qr", p_qr);
                data.put("p_user_id", userid);
                data.put("DepID", String.valueOf(searchable_spinner_department.getSelectedItemPosition()));

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_find_qr_for_change.php", data);
                Log.d("KDHDKF",data+"");

                return result;
            }
        }

        Find ru = new Find();

        ru.execute();
    }

    public void findQr1(){

        final String p_qr = txt_qr1.getText().toString();

        class Find extends AsyncTask<String, Void, String> {

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
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for (int i = 0; i < rs.length(); i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (c.getString("result").equals("A")) {
                            checkItemCodeInList(
                                    c.getString("RowID"),
                                    c.getString("ItemCode"),
                                    c.getString("UsageCode")
                            );
                            DocNo_Pay = c.getString("DocNo");
                            String CreateDate = c.getString("CreateDate");
                            String IsBorrowStatus = c.getString("IsBorrowStatus");
                            if (IsBorrowStatus.equals("คืนอุปกรณ์เข้าจ่ายกลาง")){
                                IsStatus_Type = "2";
                            }else if (IsBorrowStatus.equals("คืนอุปกรณ์เข้าจ่ายกลางแล้ว")){
                                IsStatus_Type = "2";
                            }else {
                                IsStatus_Type = "1";
                            }
                            txt_1.setVisibility(View.GONE);
                            txt_2.setVisibility(View.GONE);
                            txt_3.setVisibility(View.GONE);
                            displayPayout();
                            onDisplayPayoutDetail(DocNo_Pay,"2","2");
                            txt_doc_no.setText("เอกสาร : " + DocNo_Pay + "       วันที่สร้าง : " + CreateDate);
                        }else if (c.getString("result").equals("B")){
                            Toast.makeText(CssdTakeBack.this, "รายการซ้ำ", Toast.LENGTH_SHORT).show();
                            break;
                        }else if (c.getString("result").equals("C")){
                            Toast.makeText(CssdTakeBack.this, "แผนกหรือสถานะรายการไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();

                data.put("p_qr", p_qr);
                data.put("p_user_id", userid);
                if(DocNo_Pay != null){
                    data.put("p_DocNo", DocNo_Pay);
                }
                data.put("DepID", String.valueOf(searchable_spinner_department.getSelectedItemPosition()));

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_find_qr_for_change1.php", data);
                Log.d("KDHDKF",data+"");
                Log.d("KDHDKF",result+"");
                return result;
            }
        }

        Find ru = new Find();

        ru.execute();
    }

    private void checkItemCodeInList(
            String RowID,
            String ItemCode,
            String UsageCode
    ){
        try {

            // Check ItemCode

            List<ModelPayoutDetail> DATA_MODEL = ModelPayoutDetail;

            Iterator li = DATA_MODEL.iterator();

            while(li.hasNext()) {
                try {
                    ModelPayoutDetail m = (ModelPayoutDetail) li.next();

                    if(m.getItemcode().equals(ItemCode)){

                        // Check For Add UsageCode In List
                        if(checkItemStockInList(RowID, ItemCode)){

                            int enter_qty = m.getEnterQty();
                            Qty1 = m.getEnterQty();
                            int qr_qty = m.getQRQty();
                            int balance_qty = m.getBalanceQty();

                            if((enter_qty + qr_qty) < balance_qty) {
                                m.setQR_Qty(Integer.toString(m.getIncQR_Qty()));

                                // Display
                                ModelPayoutDetail = DATA_MODEL;

                                ArrayAdapter<ModelPayoutDetail> adapter;
                                adapter = new ListPayoutDetailBorrowAdapter(CssdTakeBack.this, ModelPayoutDetail, true, IsStatus_Type);
                                list_detail.setAdapter(adapter);

                                Toast.makeText(CssdTakeBack.this, "บันทึกสำเร็จ !!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(CssdTakeBack.this, "คืนเกินจำนวน !!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        break;
                    }

                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean checkItemStockInList(String RowID, String ItemCode){

        // Check first record
        if(payout_detail_itemstock.size() == 1 ){

            payout_detail_itemstock.add(RowID);

            return true;
        }

        // Check duplicate
        try {

            for(int i=0;i<payout_detail_itemstock.size();i++){

                if(payout_detail_itemstock.get(i).equals(RowID)) {

                    Toast.makeText(CssdTakeBack.this, "มีรายการนี้อยู่ในเอกสารคืนแล้ว !!", Toast.LENGTH_SHORT).show();

                    return false;

                }
            }
            // Add to list
            payout_detail_itemstock.add(RowID);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void DelUsage(final String Usagecode, final String DocNo) {
        class DelUsage extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (c.getString("result").equals("true")){
                            displayPayout();
                            onDisplayPayoutDetail(DocNo_Pay,"2","2");
                            Toast.makeText(CssdTakeBack.this, "ลบรายการสำเร็จ !!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(CssdTakeBack.this, "ลบรายการไม่สำเร็จ !!", Toast.LENGTH_SHORT).show();
                        }

                        if (c.getString("Qty_Sum").equals("0")){
                            clearForm();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",DocNo);
                data.put("B_ID",B_ID);
                data.put("Usagecode",Usagecode);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_delete_usage_tackback.php", data);
                    Log.d("DKDKJDDK",data+"");
                    Log.d("DKDKJDDK",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        DelUsage obj = new DelUsage();
        obj.execute();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
