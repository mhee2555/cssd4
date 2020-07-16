package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.OccuranceAdapter;
import com.phc.cssd.adapter.OccuranceDetailAdapter;
import com.phc.cssd.adapter.Occurance_DocListAdapter;
import com.phc.cssd.adapter.Occurance_DocListDetailAdapter;
import com.phc.cssd.adapter.Occurance_NewDocAdapter;
import com.phc.cssd.properties.Response_Aux_itemstock;
import com.phc.cssd.properties.pCustomer;
import com.phc.cssd.url.getUrl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Occurance_MainActivity extends AppCompatActivity {

    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    Calendar myCalendar = Calendar.getInstance();

    ArrayList<Response_Aux_itemstock> ArrayData = new ArrayList<Response_Aux_itemstock>();

    private String DeptID;

    String TAG_RESULTS="result";
    JSONArray setRs = null;

    ListView list_search;

    TextView Sdate;
    TextView Edate;
    TextView txtimport;

    Button bt_Sdate;
    Button bt_Edate;
    Button b_Reresh;

    EditText txtdocno;
    EditText remark;

    LinearLayout linearLayout_1;
    LinearLayout linearLayout_2;
    LinearLayout mainlayout1;
    LinearLayout mainlayout2;
    LinearLayout usagemain;
    LinearLayout typemain;

    CheckBox chkselected;

    String type_layout;
    String DocNo;
    String DocDate;
    String Dept;
    String B_ID = null;
    String Oc_ID2="";
    String WM_ID="";
    String R_ID="";
    String Oc_ID="";

    private ArrayList<pCustomer> clear = new ArrayList<pCustomer>();
    private ArrayList<String> gettypespinner = new ArrayList<String>();
    private OccuranceAdapter UsageObject2 ;
    private ArrayList<pCustomer> selectedStrings = new ArrayList<pCustomer>();
    private ArrayList<String> selectedArray = new ArrayList<String>();
    private Occurance_DocListAdapter UsageObject ;
    private Spinner ocType_spinner;
    private ArrayList<String> array_oc = new ArrayList<String>();
    private Spinner Machine_spinner;
    private Spinner Machine_spinner3;
    private ArrayList<String> array_wm = new ArrayList<String>();
    private Spinner round_spinner;
    private Spinner round_spinner3;
    private ArrayList<String> array_r = new ArrayList<String>();
    private ArrayList<String> array_r2 = new ArrayList<String>();
    //////////////////////////////////////////////////// page 1 end
    private Spinner ocType_spinner2;
    private Spinner ocType_spinner3;
    private ArrayList<String> array_oc2 = new ArrayList<String>();

    Calendar myCalendar2 = Calendar.getInstance();

    TextView Sdate2;

    String DocRef;
    String DocNo2="";
    String MachineId;
    String Round;
    String sel="3";
    String UserCode="1";
    String isOC;

    EditText octxt_docno;
    EditText octxt_docref;
    EditText octxt_machine;
    EditText octxt_round;

    Button get_list_layout;
    Button get_list_layout2;

    /////////////////////////////////////////////////// page 2 end
    String Oc_ID3="";
    String WM_ID3="";
    String R_ID3="";
    String New_DocNo;
    String toaststatus="0";
    String type_doc="0";

    EditText txt_l2_doc;
    EditText txt_l2_date;
    EditText UsageCodeSearch;
    EditText txt_add_ucode_newoc;

    TextView txtview_oc;
    TextView remark2;

    Button bt_import;

    Spinner spType;

    /////////////////////////////////////////////////// page 3 end
    private ArrayList<String> arrayitemOC = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occurance__main);
        getSupportActionBar().hide();

        byIntent();

        initialize();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void byIntent() {
        // Argument
        Intent intent = getIntent();
        B_ID = intent.getStringExtra("B_ID");
    }

    public void initialize(){

        //page 1 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        Sdate = (TextView) findViewById(R.id.Sdate);
        Edate = (TextView) findViewById(R.id.Edate);
        b_Reresh = (Button ) findViewById(R.id.b_Reresh);
        b_Reresh.setVisibility(View.INVISIBLE);
        bt_Sdate = (Button) findViewById(R.id.bt_Sdate);
        bt_Edate = (Button) findViewById(R.id.bt_Edate);
        txtdocno = (EditText) findViewById(R.id.txtdocno);
        remark = (EditText) findViewById(R.id.remark);
        linearLayout_1=(LinearLayout) findViewById(R.id.list_layout1);
        linearLayout_2=(LinearLayout) findViewById(R.id.list_layout2);
        mainlayout1=(LinearLayout) findViewById(R.id.mainlayout1);
        mainlayout2=(LinearLayout) findViewById(R.id.mainlayout2);
        //txtimport=(TextView) findViewById(R.id.txtimport);
        //chkselected = (CheckBox) findViewById(R.id.)
        //txt_NewDoc = (TextView) findViewById(R.id.txt_l2_doc);
        txt_l2_date = (EditText) findViewById(R.id.txt_l2_date);
        bt_import = (Button) findViewById(R.id.bt_import);
        txtview_oc = (TextView) findViewById(R.id.textView201);
        usagemain = (LinearLayout) findViewById(R.id.usage);
        typemain = (LinearLayout) findViewById(R.id.type);
        spType = (Spinner) findViewById(R.id.spinnertype);

        UsageCodeSearch = (EditText) findViewById(R.id.UsageCodeSearch);
        linearLayout_1.setVisibility(View.VISIBLE);
        linearLayout_2.setVisibility(View.GONE);
        mainlayout1.setVisibility(View.VISIBLE);
        mainlayout2.setVisibility(View.GONE);
        usagemain.setVisibility(View.GONE);
        typemain.setVisibility(View.GONE);
        type_layout="1";
        settypespinner();
        getsdate();
        getedate();
        getWashMachine();
        getRound();
        datenow();
        getnewdoc("x");
        getlistdoc(txtdocno.getText().toString(),Sdate.getText().toString(),Edate.getText().toString(),WM_ID,R_ID,Oc_ID,type_layout,"",type_doc);
        Button bt_back = (Button) findViewById(R.id.back1);
        bt_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });
        Button bt_back2 = (Button) findViewById(R.id.back2);
        bt_back2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });
        bt_import.setVisibility(View.GONE);
        usagemain.setVisibility(View.GONE);
        typemain.setVisibility(View.GONE);
        UsageCodeSearch.setVisibility(View.GONE);
//        ocType_spinner.setVisibility(View.INVISIBLE);
//        txtview_oc.setVisibility(View.INVISIBLE);
        Button bt_list2 = (Button) findViewById(R.id.bt_list2);
        bt_list2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //type_layout="2";
                getlistdoc(txtdocno.getText().toString(),Sdate.getText().toString(),Edate.getText().toString(),WM_ID,R_ID,Oc_ID,type_layout,"",type_doc);
                //loginUser();


            }
        });

        get_list_layout = (Button) findViewById(R.id.get_list_layout);
        get_list_layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                type_layout="1";
                get_list_layout.setBackgroundResource(R.drawable.bt_doc1_blue);
                get_list_layout2.setBackgroundResource(R.drawable.bt_doc_grey_oc);
                linearLayout_1.setVisibility(View.VISIBLE);
                mainlayout1.setVisibility(View.VISIBLE);
                linearLayout_2.setVisibility(View.GONE);
                mainlayout2.setVisibility(View.GONE);
                bt_import.setVisibility(View.GONE);
                usagemain.setVisibility(View.GONE);
                typemain.setVisibility(View.GONE);
                UsageCodeSearch.setVisibility(View.GONE);
                ocType_spinner.setVisibility(View.VISIBLE);
                txtview_oc.setVisibility(View.VISIBLE);
                b_Reresh.setVisibility(View.GONE);
/*                radio.setVisibility(View.INVISIBLE);
                txtimport.setVisibility(View.INVISIBLE);*/
                getlistdoc(txtdocno.getText().toString(),Sdate.getText().toString(),Edate.getText().toString(),WM_ID,R_ID,Oc_ID,type_layout,"",type_doc);
                getnewdoc("x");
                //loginUser();


            }
        });
        get_list_layout2= (Button) findViewById(R.id.get_list_layout2);
        get_list_layout2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                type_layout="2";
                get_list_layout.setBackgroundResource(R.drawable.bt_doc1_grey);
                get_list_layout2.setBackgroundResource(R.drawable.bt_doc_blue_oc);
                linearLayout_2.setVisibility(View.VISIBLE);
                mainlayout2.setVisibility(View.VISIBLE);
                linearLayout_1.setVisibility(View.GONE);
                mainlayout1.setVisibility(View.GONE);
                usagemain.setVisibility(View.VISIBLE);
                typemain.setVisibility(View.VISIBLE);
                b_Reresh.setVisibility(View.VISIBLE);
                b_Reresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clear();
                    }
                });
                //UsageCodeSearch.setVisibility(View.VISIBLE);
                bt_import.setVisibility(View.VISIBLE);
                ocType_spinner.setVisibility(View.GONE);
                txtview_oc.setVisibility(View.GONE);
                if(type_doc.equals("0")){
                    UsageCodeSearch.setVisibility(View.VISIBLE);
                }else{
                    UsageCodeSearch.setVisibility(View.INVISIBLE);
                }

/*                radio.setVisibility(View.VISIBLE);
                txtimport.setVisibility(View.VISIBLE);*/
                getlistdoc(txtdocno.getText().toString(),Sdate.getText().toString(),Edate.getText().toString(),WM_ID,R_ID,Oc_ID,type_layout,"",type_doc);
                //loginUser();


            }
        });

        bt_import.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(octxt_docno.getText().toString().equals("")&&!DocNo2.equals("")) {
                    // TODO Auto-generated method stub
                    AlertDialog.Builder builder = new AlertDialog.Builder(Occurance_MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("ยืนยัน");
                    builder.setMessage("ต้องการสร้างเอกสารความเสี่ยงหรือไม่");
                    builder.setPositiveButton("ใช่ ",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!isOC.equals("1")) {
                                        //DocRef = DocNo2;
                                        getcreate_listdoc(DocNo2, sel, UserCode,type_doc);
                                        getlistdoc(txtdocno.getText().toString(), Sdate.getText().toString(), Edate.getText().toString(), WM_ID, R_ID, Oc_ID, type_layout, "", type_doc);
                                        //getlistdocdetail("", "2");
                                        //UsageObject2.clearobject();
                                        if(type_doc.equals("0")){
                                            Toast.makeText(Occurance_MainActivity.this, "นำเข้าเอกสารฆ่าเชื้อ", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(Occurance_MainActivity.this, "นำเข้าเอกสารล้าง", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        if(type_doc.equals("0")){
                                            Toast.makeText(Occurance_MainActivity.this, "เอกสารฆ่าเชื้อใบนี้ได้บันทึกความเสี่ยงเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(Occurance_MainActivity.this, "เอกสารล้างใบนี้ได้บันทึกความเสี่ยงเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                            });
                    builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Occurance_MainActivity.this, "ยกเลิกเอกสาร", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else if(DocNo2.equals("")){
                    Toast.makeText(Occurance_MainActivity.this, "ยังไม่ได้เลือกเอกสารนำเข้า", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Occurance_MainActivity.this, "มีเอกสารนำเข้าอยู่แล้ว", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //end page 1 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //page 2 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        octxt_docno = (EditText) findViewById(R.id.octxt_docno);
        octxt_docref = (EditText) findViewById(R.id.octxt_docref);
        octxt_machine = (EditText) findViewById(R.id.octxt_machine);
        octxt_round = (EditText) findViewById(R.id.octxt_round);
        Sdate2 = (EditText) findViewById(R.id.txt_date);

        getsdate2();

        Sdate2.setEnabled(false);
        octxt_docno.setEnabled(false);
        octxt_docref.setEnabled(false);
        octxt_machine.setEnabled(false);
        octxt_round.setEnabled(false);

            octxt_docno.setText(DocNo2);
            octxt_docref.setText(DocRef);
            octxt_machine.setText(MachineId);
            octxt_round.setText(Round);

        Button saveoccurance= (Button) findViewById(R.id.saveoccurance);
        saveoccurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (!octxt_docno.getText().toString().equals("") && type_doc.equals("0")) {

                        // TODO Auto-generated method stub
                        AlertDialog.Builder builder = new AlertDialog.Builder(Occurance_MainActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle("ยืนยัน");
                        builder.setMessage("ต้องการยืนยันเอกสารหรือไม่");
                        builder.setPositiveButton("ตกลง",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (!Oc_ID2.equals("")) {
                                            UpOcType(DocNo2, Oc_ID2, UserCode, remark2.getText().toString(), "1", type_doc);
                                            getsdate2();
                                            getOcirranceType2();
                                            final GridView lv = ( GridView ) findViewById(R.id.oclist_detail);
                                            lv.setAdapter(new OccuranceDetailAdapter(Occurance_MainActivity.this, clear));
                                            octxt_docno.setText("");
                                            octxt_docref.setText("");
                                            octxt_machine.setText("");
                                            getnewdoc("x");
                                            octxt_round.setText("");
                                            remark2.setText("");
                                            getlistdoc(txtdocno.getText().toString(), Sdate.getText().toString(), Edate.getText().toString(), WM_ID, R_ID, Oc_ID, type_layout, "", type_doc);
                                            //getlistdocdetail("","2");

                                        } else {
                                            Toast.makeText(Occurance_MainActivity.this, "กรุณาเลือกประเภทความเสี่ยง", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
//                        builder.setNegativeButton("สร้างแต่ความเสี่ยง", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (!Oc_ID2.equals("")) {
//                                    UpOcType(DocNo2, Oc_ID2, UserCode, remark2.getText().toString(), "0", type_doc);
//                                    getsdate2();
//                                    getOcirranceType2();
//                                    final GridView lv = ( GridView ) findViewById(R.id.oclist_detail);
//                                    lv.setAdapter(new OccuranceDetailAdapter(Occurance_MainActivity.this, clear));
//                                    octxt_docno.setText("");
//                                    octxt_docref.setText("");
//                                    getnewdoc("x");
//                                    octxt_machine.setText("");
//                                    octxt_round.setText("");
//                                    remark2.setText("");
//                                    getlistdoc(txtdocno.getText().toString(), Sdate.getText().toString(), Edate.getText().toString(), WM_ID, R_ID, Oc_ID, type_layout, "", type_doc);
//                                    Toast.makeText(Occurance_MainActivity.this, "บันทึกรายการเฉพาะความเสี่ยง", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(Occurance_MainActivity.this, "กรุณาเลือกประเภทความเสี่ยง", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
                        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Occurance_MainActivity.this, "ยกเลิก", Toast.LENGTH_SHORT).show();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else if (!octxt_docno.getText().toString().equals("") && type_doc.equals("1")) {
                        // TODO Auto-generated method stub
                        AlertDialog.Builder builder = new AlertDialog.Builder(Occurance_MainActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle("Confirm");
                        builder.setMessage("ยืนยันสร้างเอกสาร");
                        builder.setPositiveButton("ตกลง",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (!Oc_ID2.equals("")) {
                                            UpOcType(DocNo2, Oc_ID2, UserCode, remark2.getText().toString(), "0", type_doc);
                                            getsdate2();
                                            getOcirranceType2();
                                            final GridView lv = ( GridView ) findViewById(R.id.oclist_detail);
                                            lv.setAdapter(new OccuranceDetailAdapter(Occurance_MainActivity.this, clear));
                                            octxt_docno.setText("");
                                            octxt_docref.setText("");
                                            octxt_machine.setText("");
                                            octxt_round.setText("");
                                            remark2.setText("");
                                            getnewdoc("x");
                                            getlistdoc(txtdocno.getText().toString(), Sdate.getText().toString(), Edate.getText().toString(), WM_ID, R_ID, Oc_ID, type_layout, "", type_doc);
                                            Toast.makeText(Occurance_MainActivity.this, "บันทึกรายการสำเร็จ", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(Occurance_MainActivity.this, "กรุณาเลือกประเภทความเสี่ยง", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Occurance_MainActivity.this, "กรุณาเลือกประเภทความเสี่ยง", Toast.LENGTH_SHORT).show();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    } else {
                        Toast.makeText(Occurance_MainActivity.this, "ยังไม่มีเอกสารนำเข้า", Toast.LENGTH_SHORT).show();
                    }
            }

        });

        //end page 2 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        getRound3();
        getWashMachine3();
        txt_l2_doc = (EditText) findViewById(R.id.txt_l2_doc);
        txt_l2_date = (EditText) findViewById(R.id.txt_l2_date);
        txt_l2_doc.setEnabled(false);
        txt_l2_date.setEnabled(false);
        remark2 = (TextView) findViewById(R.id.remark2);
        Button save_new= (Button) findViewById(R.id.save_new);
        save_new.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((!WM_ID3.equals("0")&&!R_ID3.equals("0"))) {

                    // TODO Auto-generated method stub
                    AlertDialog.Builder builder = new AlertDialog.Builder(Occurance_MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("ยืนยัน");
                    builder.setMessage("ต้องการสร้างเอกสารหรือไม่");
                    builder.setPositiveButton("ใช่",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!Oc_ID3.equals("")) {
                                        savenewdoc(txt_l2_doc.getText().toString(), txt_l2_date.getText().toString(), WM_ID3, R_ID3, Oc_ID3, remark.getText().toString(), xInsertToDetail(), UserCode,type_doc);
                                        arrayitemOC.clear();
                                        ArrayData.clear();
                                        GridView gv = (GridView) findViewById(R.id.GV_OC);
                                        gv.setAdapter(new Occurance_NewDocAdapter(Occurance_MainActivity.this, ArrayData));
                                        getOcType("");
                                        getRoundxx("");
                                        getMachine();
                                        remark.setText("");
                                        getlistdoc(txtdocno.getText().toString(), Sdate.getText().toString(), Edate.getText().toString(), WM_ID, R_ID, Oc_ID, type_layout, "", type_doc);
                                        getnewdoc("x");
                                        Toast.makeText(Occurance_MainActivity.this, "บันทึกรายการสำเร็จ", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Occurance_MainActivity.this, "กรุณาเลือกประเภทความเสี่ยง", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    Toast.makeText(Occurance_MainActivity.this, "กรุณาเลือกเครื่องและรอบ", Toast.LENGTH_SHORT).show();
                }

            }
        });


        txtdocno.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            getlistdoc(txtdocno.getText().toString(),Sdate.getText().toString(),Edate.getText().toString(),WM_ID,R_ID,Oc_ID,type_layout,UsageCodeSearch.getText().toString(),type_doc);
                            txtdocno.requestFocus();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        UsageCodeSearch = (EditText) findViewById(R.id.UsageCodeSearch);
        UsageCodeSearch.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            getlistdoc(txtdocno.getText().toString(),Sdate.getText().toString(),Edate.getText().toString(),WM_ID,R_ID,Oc_ID,"3",UsageCodeSearch.getText().toString(),type_doc);
                            UsageCodeSearch.requestFocus();
                            UsageCodeSearch.setText("");
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        txt_add_ucode_newoc= (EditText) findViewById(R.id.txt_add_ucode_newoc);
        txt_add_ucode_newoc.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            finddata(txt_add_ucode_newoc.getText().toString());
                            txt_add_ucode_newoc.requestFocus();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Occurance_MainActivity.super.onBackPressed();

    }

        private void checkduplicated(String ucode){
            int count = 0;
            if (arrayitemOC.size() != 0) {
                //arrayitemOC.clear();
                    for(int j =0;j<arrayitemOC.size();j++){
                        if(ucode.toUpperCase().equals(arrayitemOC.get(j))){
                            count++;
                        }
                    }
                if(count==0){
                    arrayitemOC.add(ucode);
                    ListData(xInsertToDetail());
                    toaststatus="0";
                    Log.d("benz", arrayitemOC+"");
                    //Toast.makeText(this, "เพิ่มรายการสำเร็จ", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "รายการซ้ำ", Toast.LENGTH_SHORT).show();
                    txt_add_ucode_newoc.setText("");
                    txt_add_ucode_newoc.requestFocus();
                }

            }else{
                Log.d("asd", "hey ไม่มีรายการมาก่อน");
                toaststatus="0";
                arrayitemOC.add(ucode);
                ListData(xInsertToDetail());

                //Toast.makeText(this, "เพิ่มรายการสำเร็จ", Toast.LENGTH_SHORT).show();
            }

        }
      public String xInsertToDetail() {
            String RID = "";
            for (int i=0;i<arrayitemOC.size();i++) {
                //Log.d(arrayitemOC.get(i)+"arrdub :",arrayitemOC.get(i) );
                if(i!=arrayitemOC.size()-1) {
                    RID = RID + arrayitemOC.get(i) + ",";
                }else{
                    RID = RID + arrayitemOC.get(i);
                }
            }
            return RID;
        }
    public void xDeleteToDetail(String del_code) {
        String RID = "";
        String RowId = xInsertToDetail();
        if(!RowId.equals(null)) {
            Log.d("RowID",  RowId);
            String[] split =  RowId.split(",");
            if (arrayitemOC.size() != 0) {
                arrayitemOC.clear();
                for(int i=0;i<split.length;i++){
                    int count = 0;
                        if(split[i].toUpperCase().equals(del_code)){
                            count++;
                        }
                    if(count==0){
                        arrayitemOC.add(split[i]);
                    }
                }
            }else {
                Log.d("asd", "hey fuvfds");
                for(int i =0;i<split.length;i++){
                    arrayitemOC.add(split[i]);
                }
            }
            Log.d("benz", arrayitemOC+"");
        }
        for (int i=0;i<arrayitemOC.size();i++) {
            //Log.d(arrayitemOC.get(i)+"arrdub :",arrayitemOC.get(i) );
            if(i!=arrayitemOC.size()-1) {
                RID = RID + arrayitemOC.get(i) + ",";
            }else{
                RID = RID + arrayitemOC.get(i);
            }
        }
        if(arrayitemOC.size()!=0){
            toaststatus="1";
            ListData(RID);
        }else{
            ArrayData.clear();
            GridView gv = (GridView) findViewById(R.id.GV_OC);
            gv.setAdapter(new Occurance_NewDocAdapter( Occurance_MainActivity.this, ArrayData));}

    }

    public  void bt_import() {
        if(octxt_docno.getText().equals("")){
            // TODO Auto-generated method stub
            AlertDialog.Builder builder = new AlertDialog.Builder(Occurance_MainActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Confirm");
            builder.setMessage("ต้องการสร้างเอกสารหรือไม่");
            builder.setPositiveButton("ตกลง",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //DocRef =DocNo;
                            Log.d("DocRef: ", DocNo);
                            getcreate_listdoc(DocNo,sel,UserCode,type_doc);
                            getlistdoc(txtdocno.getText().toString(),Sdate.getText().toString(),Edate.getText().toString(),WM_ID,R_ID,Oc_ID,type_layout,"",type_doc);
                            //getlistdocdetail("","2");
                            //UsageObject2.clearobject();
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            Toast.makeText(Occurance_MainActivity.this, "มีรายการเอกสารยังไม่ได้บันทึก", Toast.LENGTH_SHORT).show();
        }


    }

    // page 1 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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

        bt_Sdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Occurance_MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateSadte() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Sdate.setText(sdf.format(myCalendar.getTime()));

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

        bt_Edate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Occurance_MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateEdate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Edate.setText(sdf.format(myCalendar.getTime()));

    }
    private void datenow() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txt_l2_date.setText(sdf.format(myCalendar.getTime()));

    }

    public void getRound3(){
        round_spinner3 = (Spinner) findViewById(R.id.txt_l2_round);
        round_spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                R_ID3 = position+"";
                if (round_spinner3.getSelectedItem().equals("-")){
                    ocType_spinner3.setEnabled(false);
                    getOcType("x");
                }else {
                    ocType_spinner3.setEnabled(true);
                    getOcType("x");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getWashMachine(){
        getMachine();
        Machine_spinner = (Spinner) findViewById(R.id.machine_spinner);
        Machine_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                WM_ID = array_wm.get(position);
                getRoundx(WM_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getMachine() {
        class getMachine extends AsyncTask<String, Void, String> {
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
                    array_wm.clear();
                    array_wm.add("");
                    ArrayList<String> list_sp = new ArrayList<String>();
                    list_sp.add("-");
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xCus = new pCustomer();
                        list_sp.add(c.getString("MachineName"));
                        array_wm.add(c.getString("ID"));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Occurance_MainActivity.this,android.R.layout.simple_spinner_dropdown_item,list_sp);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Machine_spinner.setAdapter(adapter);
                    Machine_spinner3.setAdapter(adapter);
                }catch (JSONException e){
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("x",type_doc);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.getMachine(),data);
                Log.d("BANK",result);
                return result;
            }
        }
        getMachine ru = new getMachine();
        ru.execute();
    }
    public void getRound(){
        round_spinner = (Spinner) findViewById(R.id.round_spinner);
        round_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                R_ID = array_r.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getWashMachine3(){
        Machine_spinner3 = (Spinner) findViewById(R.id.txt_l2_mc);
        Machine_spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                WM_ID3 = array_wm.get(position);
                if (Machine_spinner3.getSelectedItem().equals("-")){
                    round_spinner3.setEnabled(false);
                    getRoundxx(WM_ID3);
                }else {
                    round_spinner3.setEnabled(true);
                    getRoundxx(WM_ID3);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getOcirranceType(){
        getOcType("x");
        ocType_spinner = (Spinner) findViewById(R.id.octype_spinner);
        ocType_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Oc_ID = array_oc.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getOcirranceType2(){
        getOcType("x");
        ocType_spinner2 = (Spinner) findViewById(R.id.octype_spinner2);
        ocType_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Oc_ID2 = array_oc.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void getOcirranceType3(){
        getOcType("x");
        ocType_spinner3 = (Spinner) findViewById(R.id.txt_l2_oc);
        ocType_spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Oc_ID3 = array_oc.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void getRoundx(String x) {
        class getRoundx extends AsyncTask<String, Void, String> {
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
                    array_r.clear();
                    array_r.add("-");
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xCus = new pCustomer();
                        array_r.add(c.getString("roundx"));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Occurance_MainActivity.this,android.R.layout.simple_spinner_dropdown_item,array_r);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    round_spinner.setAdapter(adapter);
                    //round_spinner3.setAdapter(adapter);
                }catch (JSONException e){
                }


            }

            //class connect php RegisterUserClass important !!!!!!!
            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("type",type_doc);
                data.put("x", params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.getround(),data);
                Log.d("BANK",data+"");
                Log.d("BANK",result);
                return result;
            }
        }
        getRoundx ru = new getRoundx();
        ru.execute(x);
    }

    public void getOcType(String x) {
        class getOcType extends AsyncTask<String, Void, String> {
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
                    Log.d("XXX", "Hello" );
                    array_oc.clear();
                    array_oc.add("");
                    ArrayList<String> list_sp = new ArrayList<String>();
                    list_sp.add("-");
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xCus = new pCustomer();
                        list_sp.add(c.getString("xOccuranceName"));
                        array_oc.add(c.getString("ID"));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Occurance_MainActivity.this,android.R.layout.simple_spinner_dropdown_item,list_sp);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ocType_spinner.setAdapter(adapter);
                    ocType_spinner2.setAdapter(adapter);
                    ocType_spinner3.setAdapter(adapter);
                }catch (JSONException e){
                }
            }
            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("x",params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.getocurancetype(),data);
                return result;
            }
        }
        getOcType ru = new getOcType();
        ru.execute(x);
    }

    public void getlistdoc(String docno,String sdate,String edate,String machine,String round,String oc,String typelayout,String UsageCode,String doctype) {
        class getlistdoc extends AsyncTask<String, Void, String> {
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
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    String boo = "";
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xCus = new pCustomer();
                        if(!c.getString("DocNo").equals("")) {
                            xCus.setDocno(c.getString("DocNo"));
                            xCus.setDocdate(c.getString("DocDate"));
                            xCus.setMachine(c.getString("MachineID"));
                            xCus.setRound(c.getString("Round"));
                            xCus.setOccuranceID(c.getString("OccuranceID"));
                            xCus.setCnt(type_doc);
                            xCus.setType(type_layout);
                            pCus.add(xCus);
                            Log.d("BANK423",c.getString("DocNo") + "---"+c.getString("MachineID")+"---"+c.getString("Round"));
                        }
                        boo = c.getString("boo");
                    }
                    if(type_layout=="1"){
                        final ListView lv1 = (ListView) findViewById(R.id.list_docno);
                        lv1.setAdapter(new Occurance_DocListAdapter(Occurance_MainActivity.this, pCus));
                        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Object o = lv1.getItemAtPosition(position);
                                pCustomer newsData = (pCustomer) o;
                                DocNo =newsData.getDocno();
                                DocDate =newsData.getDocdate();
                                Log.d("onItemClick: DocNo", DocNo);
                                Log.d("onItemClick: DocDate", DocDate);
                            }
                        });
                    }else{
                        final ListView lv2 = (ListView) findViewById(R.id.list_docno2);
                        lv2.setAdapter(new OccuranceAdapter(Occurance_MainActivity.this, pCus));
                        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Object o = lv2.getItemAtPosition(position);
                                pCustomer newsData = (pCustomer) o;
                                DocNo2 =newsData.getDocno();
                                DocDate =newsData.getDocdate();
                                isOC=newsData.getOccuranceID();
                                Log.d("onItemClick: DocNo", DocNo2);
                                Log.d("onItemClick: DocDate", DocDate);
                            }
                        });
                    }
                    if(boo.equals("true")){
                        Toast.makeText(Occurance_MainActivity.this, "พบเอกสารจากรหัสใช้งาน", Toast.LENGTH_SHORT).show();
                    }else if(boo.equals("false")){
                        Toast.makeText(Occurance_MainActivity.this, "ไม่พบเอกสารจากรหัสใช้งาน", Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                }


            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                data.put("Sdate",params[1]);
                data.put("Edate",params[2]);
                data.put("MachineID",params[3]);
                data.put("Round",params[4]);
                data.put("OccuranceID",params[5]);
                data.put("typelayout",params[6]);
                data.put("UsageCode",params[7]);
                data.put("doctype",params[8]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.getdoc(),data);
                Log.d("DJDJDRI",data+"");
                Log.d("DJDJDRI",result+"");
                return result;
            }
        }
        getlistdoc ru = new getlistdoc();
        ru.execute(docno,sdate,edate,machine,round,oc,typelayout,UsageCode,doctype);
    }

    public void getlistdocdetail(final String docno, String typelayout,String typedoc) {
        class getlistdocdetail extends AsyncTask<String, Void, String> {
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
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    String refdoc="";
                    String remark="";
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xCus = new pCustomer();
                        xCus.setDocno(c.getString("DocNo"));
                        xCus.setItemID(c.getString("ItemStockID"));
                        xCus.setItemname(c.getString("itemname"));
                        refdoc=c.getString("RefDocNo");
                        remark=c.getString("Remark");
                        xCus.setQty(c.getString("Qty"));
                        xCus.setCnt(c.getString("cnt"));
                        xCus.setUsageCode(c.getString("UsageCode"));
                        pCus.add(xCus);
                    }
                    if(type_layout=="1") {
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Occurance_MainActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        final View convertView = (View) inflater.inflate(R.layout.list_dialog_occurance, null);
                        alertDialog.setView(convertView);
                        final AlertDialog p = alertDialog.show();
                        TextView text_refdoc = (TextView) convertView.findViewById(R.id.text_refdoc);
                        TextView txt_remark = (TextView) convertView.findViewById(R.id.txt_remark);
                        TextView txt_headdoc = (TextView) convertView.findViewById(R.id.txt_headdoc);
                        txt_headdoc.setText("เลขที่เอกสาร :"+docno);
                        text_refdoc.setText("เอกสารอ้างอิง : "+refdoc);
                        txt_remark.setText("หมายเหตุ : "+(remark.equals("null")?"":remark));
                        ListView lv = (ListView) convertView.findViewById(R.id.lv);
                        lv.setAdapter(new Occurance_DocListDetailAdapter(Occurance_MainActivity.this, pCus));
                        TextView txt_back = (TextView) convertView.findViewById(R.id.txt_back);
                        txt_back.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                p.dismiss();
                            }
                        });
                    }else{
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Occurance_MainActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        final View convertView = (View) inflater.inflate(R.layout.list_dialog_occurance, null);
                        alertDialog.setView(convertView);
                        final AlertDialog p = alertDialog.show();
                        TextView text_refdoc = (TextView) convertView.findViewById(R.id.text_refdoc);
                        TextView txt_remark = (TextView) convertView.findViewById(R.id.txt_remark);
                        TextView txt_headdoc = (TextView) convertView.findViewById(R.id.txt_headdoc);
                        txt_headdoc.setText("เลขที่เอกสาร :"+docno);
                        text_refdoc.setText("เอกสารอ้างอิง : "+refdoc);
                        txt_remark.setText("หมายเหตุ : "+(remark.equals("null")?"":remark));
                        ListView lv = (ListView) convertView.findViewById(R.id.lv);
                        lv.setAdapter(new Occurance_DocListDetailAdapter(Occurance_MainActivity.this, pCus));
                        TextView txt_back = (TextView) convertView.findViewById(R.id.txt_back);
                        txt_back.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                p.dismiss();
                            }
                        });
                        }
                }catch (JSONException e){
                    Log.d("XXX", "Error on get listdocdetail" );
                }


            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                data.put("typelayout",params[1]);
                data.put("typedoc",params[2]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.getdocdetail(),data);
                Log.d("BANK",data+"");
                Log.d("BANK",result);
                return result;
            }
        }
        getlistdocdetail ru = new getlistdocdetail();
        ru.execute(docno,typelayout,typedoc);
    }
            //end page 1 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // page 2 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void getRoundxx(String x) {
        class getRoundxx extends AsyncTask<String, Void, String> {
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
                    array_r2.clear();
                    array_r2.add("");
                    ArrayList<String> list_sp =new  ArrayList<String>();
                    list_sp.add("-");
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xCus = new pCustomer();
                        array_r2.add(c.getString("roundx"));
                        list_sp.add(c.getString("roundx"));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Occurance_MainActivity.this,android.R.layout.simple_spinner_dropdown_item,list_sp);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    round_spinner3.setAdapter(adapter);
                    getOcirranceType();
                    getOcirranceType2();
                    getOcirranceType3();
                }catch (JSONException e){
                }
            }
            //class connect php RegisterUserClass important !!!!!!!
            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("type",type_doc);
                data.put("x",params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.getround(),data);
                return result;
            }
        }
        getRoundxx ru = new getRoundxx();
        ru.execute(x);
    }

    public void getsdate2(){

        updateSadte2();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateSadte2();
            }

        };

        /*Sdate2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Occurance_MainActivity.this, date, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*/
    }
    private void updateSadte2() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Sdate2.setText(sdf.format(myCalendar2.getTime()));

    }


    public void getcreate_listdoc(String docref,String sel,String usercode,String doctype) {
        class getlistdoc extends AsyncTask<String, Void, String> {
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
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    DocNo2="";
                    DocRef="";
                    MachineId="";
                    Round="";
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xCus = new pCustomer();
                        DocNo2= c.getString("DocNo");
                        DocRef=c.getString("RefDocNo");
                        MachineId=c.getString("MachineID");
                        Round=c.getString("Round");
                        if(!c.getString("itemname").equals("")){
                            xCus.setItemname(c.getString("itemname"));
                            xCus.setUsageCode(c.getString("UsageCode"));
                            pCus.add(xCus);
                        }
                    }
                    octxt_docno.setText(DocNo2);
                    octxt_docref.setText(DocRef);
                    octxt_machine.setText(MachineId);
                    octxt_round.setText(Round);
                    final GridView lv = (GridView) findViewById(R.id.oclist_detail);
                    lv.setAdapter(new OccuranceDetailAdapter(Occurance_MainActivity.this, pCus));

                }catch (JSONException e){
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xRefDocNo",params[0]);
                data.put("xSel",params[1]);
                data.put("xUserID",params[2]);
                data.put("doctype",params[3]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.createoccurance(),data);
                Log.d("FJLDJD",data+"");
                Log.d("FJLDJD",result+"");
                return result;
            }
        }
        getlistdoc ru = new getlistdoc();
        ru.execute(docref,sel,usercode,doctype);
    }


    public void UpOcType(String docNo,String type,String UserID,String remark,String IsRecall,String typedoc) {
        class UpOcType extends AsyncTask<String, Void, String> {
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
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    String bo = "";
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        bo=c.getString("flag");

                    }
                   /* if(bo.equals("true")){
                        Toast.makeText(Occurance_MainActivity.this, "บันทึกรายการสำเร็จ", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Occurance_MainActivity.this, "บันทึกรายการไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                    }*/

                }catch (JSONException e){
                }


            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                data.put("OcType",params[1]);
                data.put("UserID",params[2]);
                data.put("remark",params[3]);
                data.put("IsRecall",params[4]);
                data.put("typedoc",params[5]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.updateoccurancecreaterecall(),data);
                return result;
            }
        }
        UpOcType ru = new UpOcType();
        ru.execute(docNo,type,UserID,remark,IsRecall,typedoc);
    }

    public void clear(){
        getsdate2();
        getOcirranceType2();
        final GridView lv = ( GridView ) findViewById(R.id.oclist_detail);
        lv.setAdapter(new OccuranceDetailAdapter(Occurance_MainActivity.this, clear));
        octxt_docno.setText("");
        octxt_docref.setText("");
        octxt_machine.setText("");
        getnewdoc("x");
        octxt_round.setText("");
        remark2.setText("");
        getlistdoc(txtdocno.getText().toString(), Sdate.getText().toString(), Edate.getText().toString(), WM_ID, R_ID, Oc_ID, type_layout, "", type_doc);
    }

    public void getnewdoc(String x) {
        class getnewdoc extends AsyncTask<String, Void, String> {
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
                    ArrayList<String> list_sp = new ArrayList<String>();
                    list_sp.add("-");
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        New_DocNo = c.getString("DocNo");
                        txt_l2_doc.setText(c.getString("DocNo"));
                    }
                }catch (JSONException e){
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("x",params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.getnewdoc_occurance(),data);
                return result;
            }
        }
        getnewdoc ru = new getnewdoc();
        ru.execute(x);
    }



    public void ListData(final String Usage_code) {
        class ListData extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Aux_itemstock newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    ArrayData.clear();
                    String bool="";
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux_itemstock();
                        JSONObject c = setRs.getJSONObject(i);
                            newsData.setFields1(c.getString("xID"));
                            newsData.setFields2(c.getString("xItem_Code"));
                            newsData.setFields3(c.getString("xPackDate"));
                            newsData.setFields4(c.getString("xExpDate"));
                            newsData.setFields5(c.getString("xDept"));
                            newsData.setFields6(c.getString("xQty"));
                            newsData.setFields7(c.getString("xStatus"));
                            newsData.setFields8(c.getString("xUsageID"));
                            newsData.setFields9(c.getString("xItem_Name"));
                            bool = c.getString("bool");
                            newsData.setIs_Check(true);
                            ArrayData.add( newsData );
                    }
                    if(toaststatus.equals("0")){
                        GridView gv = (GridView) findViewById(R.id.GV_OC);
                        gv.setAdapter(new Occurance_NewDocAdapter( Occurance_MainActivity.this, ArrayData));
                        Toast.makeText(Occurance_MainActivity.this, "เพิ่มรายการสำเร็จ", Toast.LENGTH_SHORT).show();
                        txt_add_ucode_newoc.setText("");
                        txt_add_ucode_newoc.requestFocus();
                    }else{
                        GridView gv = (GridView) findViewById(R.id.GV_OC);
                        gv.setAdapter(new Occurance_NewDocAdapter( Occurance_MainActivity.this, ArrayData));
                        Toast.makeText(Occurance_MainActivity.this, "ลบข้อมูล", Toast.LENGTH_SHORT).show();
                        txt_add_ucode_newoc.setText("");
                        txt_add_ucode_newoc.requestFocus();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Usage_code",params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.oc_array_usagecode(),data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Usage_code);
    }
    public void savenewdoc(String txtdoc,String txtdate,String wmid,String rid,String ocid,String remark2,String arrayitemoc,String userCode,String type_doc) {
        class savenewdoc extends AsyncTask<String, Void, String> {
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
                    ArrayList<String> list_sp = new ArrayList<String>();
                    list_sp.add("-");
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        New_DocNo = c.getString("DocNo");
                        txt_l2_doc.setText(c.getString("DocNo"));
                    }
                }catch (JSONException e){
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("txtdoc",params[0]);
                data.put("txtdate",params[1]);
                data.put("wmid",params[2]);
                data.put("rid",params[3]);
                data.put("ocid",params[4]);
                data.put("remark2",params[5]);
                data.put("arrayitemoc",params[6]);
                data.put("userCode",params[7]);
                data.put("doctype",params[8]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.savenewdoc_occurance(),data);
                Log.d("FJLDJD",data+"");
                Log.d("FJLDJD",result+"");
                return result;
            }
        }
        savenewdoc ru = new savenewdoc();
        ru.execute(txtdoc,txtdate,wmid,rid,ocid,remark2,arrayitemoc,userCode);
    }

    public void finddata(final String Usage_code) {
        class finddata extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            checkduplicated(c.getString("ucode"));
                            if(i==0 && !Usage_code.equals("")){
                                txt_add_ucode_newoc.setText("");
                                txt_add_ucode_newoc.requestFocus();
                            }
                        }else{
                            Toast.makeText(Occurance_MainActivity.this, "ไม่พบรายการ", Toast.LENGTH_SHORT).show();
                            txt_add_ucode_newoc.setText("");
                            txt_add_ucode_newoc.requestFocus();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Usage_code",params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.Check_usagecode2(),data);
                return  result;
            }
        }

        finddata ru = new finddata();
        ru.execute( Usage_code);
    }

            public void settypespinner(){
                gettypespinner.add("ฆ่าเชื้อ(Sterile)");
                gettypespinner.add("ล้าง(Wash)");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Occurance_MainActivity.this,android.R.layout.simple_spinner_dropdown_item,gettypespinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spType.setAdapter(adapter);
                spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        type_doc = position+"";

                        if(type_doc.equals("0")){
                            UsageCodeSearch.setVisibility(View.VISIBLE);
                            getlistdoc(txtdocno.getText().toString(),Sdate.getText().toString(),Edate.getText().toString(),WM_ID,R_ID,Oc_ID,type_layout,"",type_doc);
                            txtdocno.setText("");
                            getMachine();
                        }else{
                            UsageCodeSearch.setVisibility(View.INVISIBLE);
                            getlistdoc(txtdocno.getText().toString(),Sdate.getText().toString(),Edate.getText().toString(),WM_ID,R_ID,Oc_ID,type_layout,"",type_doc);
                            txtdocno.setText("");
                            getMachine();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
}
