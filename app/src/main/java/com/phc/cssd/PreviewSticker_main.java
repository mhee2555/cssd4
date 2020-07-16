package com.phc.cssd;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.string.TextAsBitmap;
import com.phc.cssd.data.PreviewSticker;
import com.phc.cssd.master_data.LabelActivity;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.usb.TSCUSBActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PreviewSticker_main extends AppCompatActivity {
    ArrayList<String> state = new ArrayList<String>();
    String[] line=new String[2];
    CheckBox PItemName;
    CheckBox PQrCode;
    CheckBox PPackDate;
    CheckBox PMachine;
    CheckBox UsageCode;
    CheckBox PPrice;
    CheckBox PExpDate;
    CheckBox PRound;
    CheckBox PDept;
    CheckBox PEmp1;
    CheckBox PEmp2;
    CheckBox PEmp3;
    CheckBox PCostbar;
    String xMachine="0";
    String xPrice="0";
    String xCostbar="0";
    String xRound="0";
    String xDept="0";
    String xEmp1="0";
    String xEmp2="0";
    String xEmp3="0";
    String xSpeed="0";
    String xOption="0";
    String xDensity="0";
    String xFlip="0";
   /* ImageView imgCostBar;
    ImageView imgPQrCode;
    ImageView imgPPackDate;
    ImageView imgPMachine;
    ImageView imgUsageCode;
    ImageView imgPExpDate;
    ImageView imgPRound;
    ImageView imgPDept;
    ImageView imgPEmp1;
    ImageView imgPEmp2;
    ImageView imgPEmp3;*/
    //TextView concattxt;
    //TextView imgPPrice;
    TextView Label_act;
    Button savestate;
    Spinner spinnerSP;
    Spinner spinnerOp;
    Spinner spinnerDensity;
    Spinner spinnerTSC;
    Spinner spinnerxFlip;


    Button bt_setxy;
    //addnew***************
    Button sk1_1;
    Button sk1_2;
    Button sk2_1;
    Button sk2_2;
    LinearLayout ls1_1;
    RelativeLayout ls1_1_1;
    RelativeLayout ls1_2;
    RelativeLayout ls2_1;
    RelativeLayout ls2_2;

    // Sticker 1-1 +++++++++++++
    TextView dept1_1;
    TextView price1_1;
    TextView barcode1_1;
    TextView machine1_1;
    TextView round1_1;
    TextView emp1_1_1;
    TextView emp2_1_1;
    TextView emp3_1_1;


    // Sticker 1-2 +++++++++++++
    TextView dept1_2;
    TextView price1_2;
    TextView barcode1_2;
    TextView machine1_2;
    TextView round1_2;
    TextView emp1_1_2;
    TextView emp2_1_2;
    TextView emp3_1_2;
    TextView _dept1_2;

    // Sticker 2-1 +++++++++++++
    TextView dept2_1;
    TextView price2_1;
    TextView barcode2_1;
    TextView machine2_1;
    TextView round2_1;
    TextView emp1_2_1;
    TextView emp2_2_1;
    TextView emp3_2_1;

    // Sticker 2-2 +++++++++++++
    TextView dept2_2;
    TextView price2_2;
    TextView barcode2_2;
    TextView machine2_2;
    TextView round2_2;
    TextView emp1_2_2;
    TextView emp2_2_2;
    TextView emp3_2_2;
    TextView _dept2_2;

    int sticker_status=0;
    Button print_reveiw;





    private ArrayList<String> spinner_SP = new ArrayList<String>();
    private ArrayList<String> spinner_Option = new ArrayList<String>();
    private ArrayList<String> spinner_Density = new ArrayList<String>();
    private ArrayList<UsbDevice> arrayTsc = new ArrayList<UsbDevice>();
    private ArrayList<String> arrayprinter = new ArrayList<String>();
    private ArrayList<String> arrayxFlip = new ArrayList<String>();
    private ArrayList<String> arrayxFlipname = new ArrayList<String>();
    String PSpeed;
    String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();

    ImageView ImageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_sticker_main);

        getSupportActionBar().hide();
        initialize();
        byWidget();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);
        updateDeviceList();

    }

    private void byWidget() {
        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
    }

        private void initialize() {
        PItemName = (CheckBox) findViewById(R.id.cb_PItemName);
        PQrCode = (CheckBox) findViewById(R.id.cb_PQrCode);
        PPackDate = (CheckBox) findViewById(R.id.cb_PPackDate);
        PMachine = (CheckBox) findViewById(R.id.cb_PMachine);
        UsageCode = (CheckBox) findViewById(R.id.cb_UsageCode);
        PPrice = (CheckBox) findViewById(R.id.cb_PPrice);
        PExpDate = (CheckBox) findViewById(R.id.cb_PExpDate);
        PRound = (CheckBox) findViewById(R.id.cb_PRound);
        PDept = (CheckBox) findViewById(R.id.cb_PDept);
        PEmp1 = (CheckBox) findViewById(R.id.cb_PEmp1);
        PEmp2 = (CheckBox) findViewById(R.id.cb_PEmp2);
        PEmp3 = (CheckBox) findViewById(R.id.cb_PEmp3);
        PCostbar = (CheckBox) findViewById(R.id.cb_Costbar);
       /* imgCostBar = (ImageView) findViewById(R.id.imgCostBar);
        imgPQrCode = (ImageView) findViewById(R.id.qrcode);
        imgPPackDate = (ImageView) findViewById(R.id.ppackdate);
        imgPMachine = (ImageView) findViewById(R.id.pmachine);
        imgUsageCode = (ImageView) findViewById(R.id.usagecode);
        imgPExpDate = (ImageView) findViewById(R.id.pexpiredate);
        imgPRound = (ImageView) findViewById(R.id.pround);
        imgPDept = (ImageView) findViewById(R.id.pdept);
        imgPEmp1 = (ImageView) findViewById(R.id.pemp1);
        imgPEmp2 = (ImageView) findViewById(R.id.pemp2);
        imgPEmp3 = (ImageView) findViewById(R.id.pemp3);
        concattxt = (TextView) findViewById(R.id.concattxt);
        imgPPrice = (TextView) findViewById(R.id.imgPPrice);*/
        savestate = (Button) findViewById(R.id.button_save);
        spinnerSP = (Spinner) findViewById(R.id.spinnerSP);
        spinnerOp = (Spinner) findViewById(R.id.spinnerOption);
        spinnerDensity = (Spinner) findViewById(R.id.spinnerDensity);
        spinnerxFlip = (Spinner) findViewById(R.id.spn_xflip);
        ImageBack = (ImageView) findViewById(R.id.imageBack);
        Label_act = (TextView) findViewById(R.id.Label_activity);


        bt_setxy = (Button) findViewById(R.id.bt_setxy);

        ///Sticker+++++++++++++++++++++++++++++++++
        sk1_1 = (Button) findViewById(R.id.sk1_1);
        sk1_2 = (Button) findViewById(R.id.sk1_2);
        sk2_1 = (Button) findViewById(R.id.sk2_1);
        sk2_2 = (Button) findViewById(R.id.sk2_2);
        //ls1_1 = (LinearLayout) findViewById(R.id.ls1_1);
            ls1_1_1 = (RelativeLayout) findViewById(R.id.ls1_1_1);
        ls1_2 = (RelativeLayout) findViewById(R.id.ls1_2);
        ls2_1 = (RelativeLayout) findViewById(R.id.ls2_1);
        ls2_2 = (RelativeLayout) findViewById(R.id.ls2_2);

        // Sticker 1-2 +++++++++++++
            dept1_1= (TextView) findViewById(R.id.dept1_1);
            price1_1= (TextView) findViewById(R.id.price1_1);
            barcode1_1= (TextView) findViewById(R.id.barcode1_1);
            machine1_1= (TextView) findViewById(R.id.machine1_1);
            round1_1= (TextView) findViewById(R.id.round1_1);
            emp1_1_1= (TextView) findViewById(R.id.emp1_1_1);
            emp2_1_1= (TextView) findViewById(R.id.emp2_1_1);
            emp3_1_1= (TextView) findViewById(R.id.emp3_1_1);


        // Sticker 1-2 +++++++++++++
        dept1_2= (TextView) findViewById(R.id.dept1_2);
        price1_2= (TextView) findViewById(R.id.price1_2);
        barcode1_2= (TextView) findViewById(R.id.barcode1_2);
        machine1_2= (TextView) findViewById(R.id.machine1_2);
        round1_2= (TextView) findViewById(R.id.round1_2);
        emp1_1_2= (TextView) findViewById(R.id.emp1_1_2);
        emp2_1_2= (TextView) findViewById(R.id.emp2_1_2);
        emp3_1_2= (TextView) findViewById(R.id.emp3_1_2);
        _dept1_2= (TextView) findViewById(R.id._dept1_2);

        // Sticker 2-1 +++++++++++++
          dept2_1= (TextView) findViewById(R.id.dept2_1);
          price2_1= (TextView) findViewById(R.id.price2_1);
          barcode2_1= (TextView) findViewById(R.id.barcode2_1);
          machine2_1= (TextView) findViewById(R.id.machine2_1);
          round2_1= (TextView) findViewById(R.id.round2_1);
          emp1_2_1= (TextView) findViewById(R.id.emp1_2_1);
          emp2_2_1= (TextView) findViewById(R.id.emp2_2_1);
          emp3_2_1= (TextView) findViewById(R.id.emp3_2_1);

        // Sticker 2-2 +++++++++++++
          dept2_2 = (TextView) findViewById(R.id.dept2_2);
          price2_2 = (TextView) findViewById(R.id.price2_2);
          barcode2_2 = (TextView) findViewById(R.id.barcode2_2);
          machine2_2 = (TextView) findViewById(R.id.machine2_2);
          round2_2 = (TextView) findViewById(R.id.round2_2);
          emp1_2_2 = (TextView) findViewById(R.id.emp1_2_2);
          emp2_2_2 = (TextView) findViewById(R.id.emp2_2_2);
          emp3_2_2 = (TextView) findViewById(R.id.emp3_2_2);
          _dept2_2 = (TextView) findViewById(R.id._dept2_2);

          spinnerTSC = (Spinner) findViewById(R.id.spn_printer);

          setspinnerprinter();
          setxFlip();
        sk1_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_steam_green));
        sk1_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_steamdb_blue));
        sk2_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_nornmal));
        sk2_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_nornmaldb_blue));
        ls1_1_1.setVisibility(View.VISIBLE);
        ls1_2.setVisibility(View.GONE);
        ls2_1.setVisibility(View.GONE);
        ls2_2.setVisibility(View.GONE);
        sticker_status=1;
        print_reveiw=(Button) findViewById(R.id.print_reveiw);

        print_reveiw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    xPrint(sticker_status);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        sk1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sk1_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_steam_green));
                sk1_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_steamdb_blue));
                sk2_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_nornmal));
                sk2_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_nornmaldb_blue));
                ls1_1_1.setVisibility(View.VISIBLE);
                ls1_2.setVisibility(View.GONE);
                ls2_1.setVisibility(View.GONE);
                ls2_2.setVisibility(View.GONE);
                sticker_status=1;
            }
        });
        sk1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sk1_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_steam_blue));
                sk1_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_steamdb_green));
                sk2_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_nornmal));
                sk2_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_nornmaldb_blue));
                ls1_1_1.setVisibility(View.GONE);
                ls1_2.setVisibility(View.VISIBLE);
                ls2_1.setVisibility(View.GONE);
                ls2_2.setVisibility(View.GONE);
                sticker_status=2;
            }
        });
        sk2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sk1_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_steam_blue));
                sk1_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_steamdb_blue));
                sk2_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_normal_green));
                sk2_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_nornmaldb_blue));
                ls1_1_1.setVisibility(View.GONE);
                ls1_2.setVisibility(View.GONE);
                ls2_1.setVisibility(View.VISIBLE);
                ls2_2.setVisibility(View.GONE);
                sticker_status=3;
            }
        });
        sk2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sk1_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_steam_blue));
                sk1_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_steamdb_blue));
                sk2_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_nornmal));
                sk2_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_nornmaldb_green));
                ls1_1_1.setVisibility(View.GONE);
                ls1_2.setVisibility(View.GONE);
                ls2_1.setVisibility(View.GONE);
                ls2_2.setVisibility(View.VISIBLE);
                sticker_status=4;
            }
        });





        ImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(PreviewSticker_main.this, SettingActivity.class);
                //intent.putExtra("page","top");
                //startActivity(intent);
                finish();
            }
        });
        savestate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String longstring ="";
                longstring = ischeckedBox(PMachine.isChecked())+","+
                        ischeckedBox(PPrice.isChecked())+","+
                        ischeckedBox(PRound.isChecked())+","+
                        ischeckedBox(PDept.isChecked())+","+
                        ischeckedBox(PEmp1.isChecked())+","+
                        ischeckedBox(PEmp2.isChecked())+","+
                        ischeckedBox(PEmp3.isChecked())+","+
                        spinnerSP.getSelectedItem().toString()+","+
                        spinnerOp.getSelectedItem().toString()+","+
                        spinnerDensity.getSelectedItem().toString()+","+
                        ischeckedBox(PCostbar.isChecked());
                Log.d("lognstring:", longstring);
                updatestatus(longstring);

            }
        });


        //OnChangelistener
        PMachine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(PMachine.isChecked()){
                    machine1_1.setVisibility(View.VISIBLE);
                    machine1_2.setVisibility(View.VISIBLE);
                    machine2_1.setVisibility(View.VISIBLE);
                    machine2_2.setVisibility(View.VISIBLE);

                }else{
                    machine1_1.setVisibility(View.INVISIBLE);
                    machine1_2.setVisibility(View.INVISIBLE);
                    machine2_1.setVisibility(View.INVISIBLE);
                    machine2_2.setVisibility(View.INVISIBLE);
                }
            }
        });

        PRound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(PRound.isChecked()){
                    round1_1.setVisibility(View.VISIBLE);
                    round1_2.setVisibility(View.VISIBLE);
                    round2_1.setVisibility(View.VISIBLE);
                    round2_2.setVisibility(View.VISIBLE);
                }else{
                    round1_1.setVisibility(View.INVISIBLE);
                    round1_2.setVisibility(View.INVISIBLE);
                    round2_1.setVisibility(View.INVISIBLE);
                    round2_2.setVisibility(View.INVISIBLE);
                }
            }
        });

        PPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(PPrice.isChecked()){
                    price1_1.setVisibility(View.VISIBLE);
                    price1_2.setVisibility(View.VISIBLE);
                    price2_1.setVisibility(View.VISIBLE);
                    price2_2.setVisibility(View.VISIBLE);
                }else{
                    price1_1.setVisibility(View.INVISIBLE);
                    price1_2.setVisibility(View.INVISIBLE);
                    price2_1.setVisibility(View.INVISIBLE);
                    price2_2.setVisibility(View.INVISIBLE);
                }
            }
        });

        PDept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(PDept.isChecked()){
                    dept1_1.setVisibility(View.VISIBLE);
                    dept1_2.setVisibility(View.VISIBLE);
                    dept2_1.setVisibility(View.VISIBLE);
                    dept2_2.setVisibility(View.VISIBLE);
                }else{
                    dept1_1.setVisibility(View.INVISIBLE);
                    dept1_2.setVisibility(View.INVISIBLE);
                    dept2_1.setVisibility(View.INVISIBLE);
                    dept2_2.setVisibility(View.INVISIBLE);
                }
            }
        });

        PCostbar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(PCostbar.isChecked()){
                    barcode1_1.setVisibility(View.VISIBLE);
                    barcode1_2.setVisibility(View.VISIBLE);
                    barcode2_1.setVisibility(View.VISIBLE);
                    barcode2_2.setVisibility(View.VISIBLE);
                }else{
                    barcode1_1.setVisibility(View.INVISIBLE);
                    barcode1_2.setVisibility(View.INVISIBLE);
                    barcode2_1.setVisibility(View.INVISIBLE);
                    barcode2_2.setVisibility(View.INVISIBLE);
                }
            }
        });

        PEmp1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(PEmp1.isChecked()){
                    emp1_1_1.setVisibility(View.VISIBLE);
                    if(PEmp3.isChecked()){
                        //concattxt.setVisibility(View.VISIBLE);
                        emp1_1_2.setVisibility(View.VISIBLE);
                        emp1_2_1.setVisibility(View.VISIBLE);
                        emp1_2_2.setVisibility(View.VISIBLE);
                    }
                }else{
                    emp1_1_1.setVisibility(View.INVISIBLE);
                    //concattxt.setVisibility(View.INVISIBLE);
                    emp1_1_2.setVisibility(View.INVISIBLE);
                    emp1_2_1.setVisibility(View.INVISIBLE);
                    emp1_2_2.setVisibility(View.INVISIBLE);
                }
            }
        });

        PEmp2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(PEmp2.isChecked()){
                    emp2_1_1.setVisibility(View.VISIBLE);
                    emp2_1_2.setVisibility(View.VISIBLE);
                    emp2_2_1.setVisibility(View.VISIBLE);
                    emp2_2_2.setVisibility(View.VISIBLE);
                }else{
                    emp2_1_1.setVisibility(View.INVISIBLE);
                    emp2_1_2.setVisibility(View.INVISIBLE);
                    emp2_2_1.setVisibility(View.INVISIBLE);
                    emp2_2_2.setVisibility(View.INVISIBLE);
                }
            }
        });

        PEmp3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(PEmp3.isChecked()){
                    emp3_1_1.setVisibility(View.VISIBLE);
                    if(PEmp1.isChecked()){
                        //concattxt.setVisibility(View.VISIBLE);
                        emp3_1_2.setVisibility(View.VISIBLE);
                        emp3_2_1.setVisibility(View.VISIBLE);
                        emp3_2_2.setVisibility(View.VISIBLE);
                    }
                }else{
                    emp3_1_1.setVisibility(View.INVISIBLE);
                    //concattxt.setVisibility(View.INVISIBLE);
                    emp3_1_2.setVisibility(View.INVISIBLE);
                    emp3_2_1.setVisibility(View.INVISIBLE);
                    emp3_2_2.setVisibility(View.INVISIBLE);
                }
            }
        });
        Label_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviewSticker_main.this,LabelActivity.class);
                startActivity(intent);
            }
        });

        //default
        PItemName.setChecked(true);
        PQrCode.setChecked(true);
        PPackDate.setChecked(true);
        PExpDate.setChecked(true);
        UsageCode.setChecked(true);
        PItemName.setEnabled(false);
        PQrCode.setEnabled(false);
        PPackDate.setEnabled(false);
        PExpDate.setEnabled(false);
        UsageCode.setEnabled(false);


            dept1_1.setVisibility(View.VISIBLE);
             price1_1.setVisibility(View.VISIBLE);
             barcode1_1.setVisibility(View.VISIBLE);
             machine1_1.setVisibility(View.VISIBLE);
             round1_1.setVisibility(View.VISIBLE);
             emp1_1_1.setVisibility(View.VISIBLE);
             emp2_1_1.setVisibility(View.VISIBLE);
             emp3_1_1.setVisibility(View.VISIBLE);
       /* imgPQrCode.setVisibility(View.VISIBLE);
        imgPPackDate.setVisibility(View.VISIBLE);
        imgPExpDate.setVisibility(View.VISIBLE);
        imgUsageCode.setVisibility(View.VISIBLE);

        imgCostBar.setVisibility(View.INVISIBLE);
        imgPPrice.setVisibility(View.INVISIBLE);
        imgPMachine.setVisibility(View.INVISIBLE);
        imgPRound.setVisibility(View.INVISIBLE);
        imgPDept.setVisibility(View.INVISIBLE);
        imgPEmp1.setVisibility(View.INVISIBLE);
        imgPEmp2.setVisibility(View.INVISIBLE);
        imgPEmp3.setVisibility(View.INVISIBLE);
        concattxt.setVisibility(View.INVISIBLE);*/

        //from db
        get_statussticker("");
        addspeed();
        addOption();
        addDensity();

        bt_setxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviewSticker_main.this, SetPositionStickerActivity.class);
                startActivity(intent);
            }
        });

    }

    public String ischeckedBox(Boolean temp){
        if(temp){
            return "1";
        }else{
            return "0";
        }
    }

    public void get_statussticker(String pass) {
        class get_statussticker extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                     xMachine="0";
                     xPrice="0";
                     xCostbar="0";
                     xRound="0";
                     xDept="0";
                     xEmp1="0";
                     xEmp2="0";
                     xEmp3="0";
                     xSpeed="0";
                     xOption="0";
                     xDensity="0";

                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        Log.d("AAA", "i'm here"+c);
                        if(c.getString("bool").equals("true")){
                            if(c.getString("PMachine").equals("1")){
                                PMachine.setChecked(true);
                                xMachine="1";
                                machine1_1.setVisibility(View.VISIBLE);
                                machine1_2.setVisibility(View.VISIBLE);
                                machine2_1.setVisibility(View.VISIBLE);
                                machine2_2.setVisibility(View.VISIBLE);
                            }
                            if(c.getString("PPrice").equals("1")){
                                PPrice.setChecked(true);
                                xPrice="1";
                                price1_1.setVisibility(View.VISIBLE);
                                price1_2.setVisibility(View.VISIBLE);
                                price2_1.setVisibility(View.VISIBLE);
                                price2_2.setVisibility(View.VISIBLE);
                            }
                            if(c.getString("PRound").equals("1")){
                                PRound.setChecked(true);
                                xRound="1";
                                round1_1.setVisibility(View.VISIBLE);
                                round1_2.setVisibility(View.VISIBLE);
                                round2_1.setVisibility(View.VISIBLE);
                                round2_2.setVisibility(View.VISIBLE);
                            }
                            if(c.getString("PDept").equals("1")){
                                PDept.setChecked(true);
                                xDept="1";
                                dept1_1.setVisibility(View.VISIBLE);
                                dept1_2.setVisibility(View.VISIBLE);
                                dept2_1.setVisibility(View.VISIBLE);
                                dept2_2.setVisibility(View.VISIBLE);
                            }
                            if(c.getString("PEmp1").equals("1")){
                                PEmp1.setChecked(true);
                                xEmp1="1";
                                emp1_1_1.setVisibility(View.VISIBLE);
                                emp1_1_2.setVisibility(View.VISIBLE);
                                emp1_2_1.setVisibility(View.VISIBLE);
                                emp1_2_2.setVisibility(View.VISIBLE);
                            }
                            if(c.getString("PEmp2").equals("1")){
                                PEmp2.setChecked(true);
                                xEmp2="1";
                                emp2_1_1.setVisibility(View.VISIBLE);
                                emp2_1_2.setVisibility(View.VISIBLE);
                                emp2_2_1.setVisibility(View.VISIBLE);
                                emp2_2_2.setVisibility(View.VISIBLE);
                            }
                            if(c.getString("PEmp3").equals("1")){
                                PEmp3.setChecked(true);
                                xEmp3="1";
                                emp3_1_1.setVisibility(View.VISIBLE);
                                emp3_1_2.setVisibility(View.VISIBLE);
                                emp3_2_1.setVisibility(View.VISIBLE);
                                emp3_2_2.setVisibility(View.VISIBLE);
                            }
                            if(c.getString("PCostBar").equals("1")){
                                PCostbar.setChecked(true);
                                xCostbar="1";
                                barcode1_1.setVisibility(View.VISIBLE);
                                barcode1_2.setVisibility(View.VISIBLE);
                                barcode2_1.setVisibility(View.VISIBLE);
                                barcode2_2.setVisibility(View.VISIBLE);
                            }
                           /* if(c.getString("PEmp1").equals("1") && c.getString("PEmp3").equals("1")){
                                concattxt.setVisibility(View.VISIBLE);
                            }*/

                            if(!c.getString("PSpeed").equals("")){
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PreviewSticker_main.this,android.R.layout.simple_spinner_dropdown_item,spinner_SP);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerSP.setAdapter(adapter);
                                Log.d("SPeedd: ", c.getString("PSpeed"));
                                int spinnerPosition = adapter.getPosition(c.getString("PSpeed"));
                                Log.d("AADSDSD", ""+spinnerPosition);
                                Log.d("AADSDSD", ""+c);
                                spinnerSP.setSelection(spinnerPosition);
                                xSpeed=c.getString("PSpeed");
                            }
                            if(!c.getString("POption").equals("")){
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PreviewSticker_main.this,android.R.layout.simple_spinner_dropdown_item,spinner_Option);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerOp.setAdapter(adapter);
                                if(c.getString("POption").equals("1")){
                                    int spinnerPosition = adapter.getPosition("1.รหัสใช้งาน");
                                    spinnerOp.setSelection(spinnerPosition);
                                    xOption="1";
                                }else if(c.getString("POption").equals("2")){
                                    int spinnerPosition = adapter.getPosition("2.รหัสเครื่องมือ");
                                    spinnerOp.setSelection(spinnerPosition);
                                    xOption="2";
                                }
                            }
                            if(!c.getString("PDensity").equals("")){
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PreviewSticker_main.this,android.R.layout.simple_spinner_dropdown_item,spinner_Density);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerDensity.setAdapter(adapter);
                                Log.d("PDensity: ", c.getString("PDensity"));
                                int spinnerPosition = adapter.getPosition(c.getString("PDensity"));
                                Log.d("AADSDSD", ""+spinnerPosition);
                                Log.d("AADSDSD", ""+c);
                                spinnerDensity.setSelection(spinnerPosition);
                                xDensity=c.getString("PDensity");
                            }
                        }else{
                            Toast.makeText(PreviewSticker_main.this,"ดึงข้อมูลล้มเหลว ", Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("pass",params[0]);
                String result = ruc.sendPostRequest(iFt.Get_PreviewSticker(),data);
                return  result;
            }
        }


        get_statussticker ru = new get_statussticker();
        ru.execute(pass);
    }

    public void updatestatus(String tempchecked) {
        class updatestatus extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")){
                            get_statussticker("");
                            Toast.makeText(PreviewSticker_main.this,"บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(PreviewSticker_main.this,"บันทึกล้มเหลว", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("tempchecked",params[0]);
                String result = ruc.sendPostRequest(iFt.UpdatePreviewSticker(),data);
                return  result;
            }
        }

        updatestatus ru = new updatestatus();
        ru.execute( tempchecked );
    }
    private void addspeed() {

        spinner_SP.add("0");
        spinner_SP.add("1");
        spinner_SP.add("2");
        spinner_SP.add("3");
        spinner_SP.add("4");
        spinner_SP.add("5");
        spinner_SP.add("6");
        spinner_SP.add("7");


    }

    private void addOption() {

        spinner_Option.add("1.รหัสใช้งาน");
        spinner_Option.add("2.รหัสเครื่องมือ");


    }

    private void addprinter() {

        arrayTsc.add(TscDevice_1);
        arrayTsc.add(TscDevice_2);
        arrayTsc.add(TscDevice_3);

    }
    private  void nameprinter(){
        arrayprinter.add("เครื่อง 1 ");
        arrayprinter.add("เครื่อง 2 ");
        arrayprinter.add("เครื่อง 3 ");
    }

    private void addDensity() {

        spinner_Density.add("0");
        spinner_Density.add("1");
        spinner_Density.add("2");
        spinner_Density.add("3");
        spinner_Density.add("4");
        spinner_Density.add("5");
        spinner_Density.add("6");
        spinner_Density.add("7");
        spinner_Density.add("8");
        spinner_Density.add("9");
        spinner_Density.add("10");
        spinner_Density.add("11");
        spinner_Density.add("12");
        spinner_Density.add("13");
        spinner_Density.add("14");
        spinner_Density.add("15");

    }


    ArrayList<Integer> PRINTER = new ArrayList<>();
    PreviewSticker p = new PreviewSticker("");


    TSCUSBActivity TscUSB = new TSCUSBActivity();

    private static final String ACTION_USB_PERMISSION = "com.android.recipes.USB_PERMISSION";

    final int VENDOR_ID = 4611;
    final int DELAY_TIME = 0;
    private static final String TAG = "UsbHost";
    // ---------------------------------------------------------------------------------------------

    private static UsbManager mUsbManager;

    private static UsbDevice TscDevice_1;
    private static UsbDevice TscDevice_2;
    private static UsbDevice TscDevice_3;
    private static UsbDevice TscDevice_4;
    private static UsbDevice TscDevice_5;
    private static UsbDevice SunmiDevice;
    private static UsbDevice OrtherDevice;
    private static UsbDevice TscReveiw;

    PendingIntent mPermissionIntent;


    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false) && device != null) {
                    //Query the device's descriptor
                    //getDeviceStatus(device);
                } else {
                    Log.d(TAG, "permission denied for device " + device);
                }
            }
        }
    };


    private void updateDeviceList() {
        HashMap<String, UsbDevice> connectedDevices = mUsbManager.getDeviceList();

        int i = 1;

        if (connectedDevices.isEmpty()) {
            Log.d(TAG, "No Devices Currently Connected");
        } else {
            for (UsbDevice device : connectedDevices.values()) {
                if(device.getVendorId() == 4611) {

                    //System.out.println("STERILE : " + device.getDeviceName());

                    switch(i){
                        case 1 : TscDevice_1 = device; break;
                        case 2 : TscDevice_2 = device; break;
                        case 3 : TscDevice_3 = device; break;
                        case 4 : TscDevice_4 = device; break;
                        case 5 : TscDevice_5 = device; break;
                    }
                    i++;
                }else if(device.getVendorId() == 2965){
                    SunmiDevice = device;
                }else {


                    UsbInterface intf = device.getInterface(0);

                    //System.out.println(device.getDeviceClass() + " == " + UsbConstants.USB_CLASS_PRINTER);

                    if( intf.getInterfaceClass() == UsbConstants.USB_CLASS_PRINTER ){
                        OrtherDevice = device;
                    }
                }
            }

        }
    }
//    private UsbDevice getPrinter(int Label){
//
//        //System.out.println("PRINTER.size() = " + PRINTER.size());
//
//        if(PRINTER == null || PRINTER.size() == 0) {
//            return null;
//        }
//
//        int printer = PRINTER.get(Label-1);
//
//        //System.out.println("printer = " + printer);
//
//        if(printer == 1){
//            return TscDevice_1;
//        }else if(printer == 2){
//            return TscDevice_2;
//        }else if(printer == 3){
//            return TscDevice_3;
//        }else if(printer == 4){
//            return TscDevice_4;
//        }else if(printer == 5){
//            return TscDevice_5;
//        }else{
//            return null;
//        }
//    }

    //ปริ้นสติ๊กเกอร์
    void xPrint(int label) throws Exception {

        String emp1="กัลญาณี-เตรียม";
        String emp2="พลอย-ตรวจ";
        String emp3="โสภิต-ฆ่าเชื้อ";
        String Price=""+"20";
        String Machine="เครื่อง: 1";
        String Round="รอบ: 2";
        String Dept="CSSD-XXXXXXXX";
        // Default โชว์ตลอด
        String Packdate="01-01-2018";
        String Expiredate="01-01-2018";
        String ItemName="PILLAR IMMOBILIZATION";
        String Itemcode="PXXXXX";
        String Usagecode="PXXXXX-XXXXX";
        String Time="15:42";
        String QRCode="http://www.poseintelligence.com/service";
        boolean PMachine = xMachine.equals("1");
        boolean PPrice = xPrice.equals("1");
        boolean PRound = xRound.equals("1");
        boolean PDept = xDept.equals("1");
        boolean PEmp1 = xEmp1.equals("1");
        boolean PEmp2 = xEmp2.equals("1");
        boolean PEmp3 = xEmp3.equals("1");
        boolean PBarCode = xCostbar.equals("1");
        int PSpeed = Integer.parseInt(xSpeed);
        int PDensity = Integer.parseInt(xDensity);
        String POption = xOption;
       // System.out.println("label = " + label);

        if (mUsbManager.hasPermission(TscDevice_1)) {

            String DATA="";

            switch(label){

                // Sticker Big Hot Air Steam
                case 2:
                    Toast.makeText(this, "label = " + TscReveiw, Toast.LENGTH_SHORT).show();
                    System.out.println("label = " + 1111);
                    System.out.println("label = " + TscReveiw);
                    TscUSB.openport(mUsbManager, TscReveiw);
                    TscUSB.setup(60,76,PSpeed, PDensity,0,2,2);

                        try {


                            TscUSB.sendcommand("DIRECTION "+xFlip+",0\r\n");
                            TscUSB.clearbuffer();

                            //ส่วนบนสติ๊กเกอร์
                            //ชื่ออุปกรณ์
                            line=make2line(ItemName);
                            TscUSB.sendpicture(40,5, TextAsBitmap.getTextBitmap(line[0],22));
                            TscUSB.sendpicture(40,35,TextAsBitmap.getTextBitmap(line[1],22));

                            //TscUSB.sendpicture(50,35,TextAsBitmap.getTextBitmap(m.getItemname(),22));
                            //ราคา
                            if(PBarCode){TscUSB.barcode(280,100,"128",20,2,0,1,1,"Price:"+Price+".-");}
                            if(PPrice){ TscUSB.printerfont(300,120,"1",0,1,1,"Price: "+ Price); }
                            //แผนก
                            if(PDept){TscUSB.sendpicture(50,85,TextAsBitmap.getTextBitmap(Price,20)); }
                            //ชื่อเซ็ท
                            TscUSB.sendpicture(100,120,TextAsBitmap.getTextBitmap(xOption.equals("1") ? Itemcode : Usagecode,20));
                            //QR_Code
                            TscUSB.qrcode(40,160,"H","4","A","0","M2","S1", QRCode);
                            //เครื่อง
                            if(PMachine){TscUSB.sendpicture(210,155,TextAsBitmap.getTextBitmap(Machine,18));}
                            //รอบ
                            if(PRound){TscUSB.sendpicture(310,155,TextAsBitmap.getTextBitmap(Round,18));}
                            //วันแพค
                            TscUSB.printerfont(210,210,"0",0,9,9, Packdate);
                            //วันหมดอายุ
                            TscUSB.printerfont(210,250,"0",0,12,12,Expiredate);
                            //คนเครียม ตรวจ
                            if (PEmp1||PEmp2){TscUSB.sendpicture(200,280,TextAsBitmap.getTextBitmap(emp1 + "/" +emp2,18));}
                            //ฆ่าเชื้อ
                            if (PEmp3){TscUSB.sendpicture(220,308,TextAsBitmap.getTextBitmap(emp3,18));}

                            //ส่วนล่างสติ๊กเกอร์
                            //ชื่ออุปกรณ์
                            TscUSB.sendpicture(50,445,TextAsBitmap.getTextBitmap(ItemName,18));
                            //แผนก
                            TscUSB.sendpicture(50,470,TextAsBitmap.getTextBitmap(Dept,18));
                            //ชื่อเซ็ท
                            TscUSB.sendpicture(85,500,TextAsBitmap.getTextBitmap(Usagecode,18));
                            //QR_Code
                            TscUSB.qrcode(340,490,"H","3","A","0","M2","S1", QRCode);
                            //วันหมดอายุ
                            TscUSB.printerfont(40,560,"0",0,9,9, Packdate);
                            //วันหมดอายุ
                            TscUSB.printerfont(180,560,"0",0,12,12, Expiredate);
                            //TscUSB.sendcommand("PRINT 1\r\n");
                            TscUSB.sendcommand("PRINT 1 \r\n");

                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    TscUSB.closeport(3000);

                    break;

                // Sticker Small Hot Air Steam
                case 1:
                    TscUSB.openport(mUsbManager, TscReveiw);
                    //TscUSB.setup(60,75,2,0,0,2,2);
                    TscUSB.setup(60,55,PSpeed,PDensity,0,2,1);


                        try {

                            TscUSB.sendcommand("DIRECTION "+xFlip+",0\r\n");
                            TscUSB.clearbuffer();

                            //ส่วนบนสติ๊กเกอร์
                            //ชื่ออุปกรณ์
                            line=make2line(ItemName);
                            TscUSB.sendpicture(40,5,TextAsBitmap.getTextBitmap(line[0],22));
                            TscUSB.sendpicture(40,35,TextAsBitmap.getTextBitmap(line[1],22));
                            //TscUSB.sendpicture(50,35,TextAsBitmap.getTextBitmap(m.getItemname(),22));
                            //ราคา
                            if(PBarCode){TscUSB.barcode(280,100,"128",20,0,0,1,1,"Price:"+Price+".-");}
                            if(PPrice){ TscUSB.printerfont(300,80,"1",0,1,1,"Price:"+Price+".-"); }
                            //แผนก
                            if(PDept){TscUSB.sendpicture(40,75,TextAsBitmap.getTextBitmap(Dept,22)); }
                            //ชื่อเซ็ท
                            TscUSB.sendpicture(80,110,TextAsBitmap.getTextBitmap(POption.equals("1") ? Itemcode: Usagecode,20));
                            //QR_Code
                            TscUSB.qrcode(40,160,"H","4","A","0","M1","S1",QRCode);
                            //เครื่อง
                            if(PMachine){TscUSB.sendpicture(210,145,TextAsBitmap.getTextBitmap(Machine,18));}
                            //รอบ
                            if(PRound){TscUSB.sendpicture(310,145,TextAsBitmap.getTextBitmap(Round,18));}
                            //วันแพค
                            TscUSB.printerfont(210,195,"0",0,9,9,Packdate );
                            //วันหมดอายุ
                            TscUSB.printerfont(210,235,"0",0,12,12, Expiredate);
                            //คนเครียม ตรวจ
                            TscUSB.sendpicture(210,265,TextAsBitmap.getTextBitmap((PEmp1 ? emp1 : "" )+"/"+(PEmp2 ? emp2 : "" ),18));
                            //ฆ่าเชื้อ
                            if (PEmp3){TscUSB.sendpicture(210,293,TextAsBitmap.getTextBitmap(emp3,18));}

                            //TscUSB.sendcommand("PRINT 1\r\n");
                            TscUSB.sendcommand("PRINT 1\r\n");

                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    TscUSB.closeport(3000);

                    break;

                // Sticker Big Gas Steam
                case 4:
                    TscUSB.openport(mUsbManager, TscReveiw);
                    TscUSB.setup(55,56, PSpeed, PDensity,0,2,1);


                        try {

                            TscUSB.sendcommand("DIRECTION "+xFlip+",0\r\n");
                            //TscUSB.sendcommand("DIRECTION 1,0\r\n");
                            //TscUSB.sendcommand("DIRECTION 0,0\r\n");

                            TscUSB.clearbuffer();
                            line=make2line(ItemName);
                            TscUSB.sendpicture(40,5,TextAsBitmap.getTextBitmap(line[0],24));
                            TscUSB.sendpicture(40,35,TextAsBitmap.getTextBitmap(line[1],24));
                            //TscUSB.sendpicture(30,20,TextAsBitmap.getTextBitmap(m.getItemname(),22));
                            if(PBarCode){TscUSB.barcode(280,105,"128",20,0,0,1,1,"Price:"+Price+".-");}
                            if(PPrice){ TscUSB.printerfont(300,85,"1",0,1,1,"Price:"+Price+".-"); }
                            if(PDept){TscUSB.sendpicture(30,65,TextAsBitmap.getTextBitmap(Dept,26));}
                            TscUSB.sendpicture(80,98,TextAsBitmap.getTextBitmap(POption.equals("1") ? Itemcode : Usagecode,20));
                            if(PMachine){ TscUSB.sendpicture(180,125,TextAsBitmap.getTextBitmap(Machine,20));}
                            if(PRound){TscUSB.sendpicture(280,125,TextAsBitmap.getTextBitmap(Round,20));}
                            //TscUSB.sendpicture(350,130,TextAsBitmap.getTextBitmap( m.getTime(),20));
                            TscUSB.qrcode(30,125,"H","4","A","0","M1","S1", QRCode);
                            TscUSB.printerfont(200,170,"0",0,9,9, Packdate);
                            TscUSB.printerfont(200,220,"0",0,9,9, Expiredate);
                            TscUSB.sendpicture(180,245,TextAsBitmap.getTextBitmap((PEmp1 ? emp1 : "" )+"/"+(PEmp2 ? emp2 : "" )+"/"+(PEmp3 ? emp3 : "" ),15));

                            //ส่วนล่างสติ๊กเกอร์
                            //ชื่ออุปกรณ์
                            TscUSB.sendpicture(30,300,TextAsBitmap.getTextBitmap(ItemName,18));
                            //แผนก
                            TscUSB.sendpicture(30,325, TextAsBitmap.getTextBitmap(Dept,18));
                            //ชื่อเซ็ท
                            TscUSB.sendpicture(65,355,TextAsBitmap.getTextBitmap(Usagecode,18));
                            //QR_Code
                            TscUSB.qrcode(310,315,"H","3","A","0","M1","S1", QRCode);
                            //วันหมดอายุ
                            TscUSB.printerfont(25,405,"0",0,9,9,Packdate);
                            //วันหมดอายุ
                            TscUSB.printerfont(150,400,"0",0,12,12,Expiredate);

                            TscUSB.sendcommand("PRINT 1\r\n");

                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    TscUSB.closeport(3000);

                    break;

                // Sticker Small Gas Steam
                case 3:

                    TscUSB.openport(mUsbManager, TscReveiw);
                    TscUSB.setup(55,35, PSpeed, PDensity,0,2,1);

                        try {
                            TscUSB.sendcommand("DIRECTION "+xFlip+",0\r\n");
                            TscUSB.clearbuffer();
                            line=make2line(ItemName);
                            TscUSB.sendpicture(30,5,TextAsBitmap.getTextBitmap(line[0],24));
                            TscUSB.sendpicture(30,35,TextAsBitmap.getTextBitmap(line[1],24));
                            //TscUSB.sendpicture(60,0, TextAsBitmap.getTextBitmap(m.getItemname(),22));
                            if(PPrice){ TscUSB.printerfont(280,110,"1",0,1,1,"Price:"+Price+".-"); }
                            if(PBarCode){TscUSB.barcode(260,80,"128",20,0,0,1,1,"Price:"+Price+".-");}
                            if(PDept){ TscUSB.sendpicture(30,90,TextAsBitmap.getTextBitmap(Dept,26));}
                            TscUSB.sendpicture(80,133,TextAsBitmap.getTextBitmap(POption.equals("1") ? Itemcode : Usagecode,20));
                            if(PMachine){ TscUSB.sendpicture(160,160,TextAsBitmap.getTextBitmap(Machine,20));}
                            if(PRound){TscUSB.sendpicture(260,160,TextAsBitmap.getTextBitmap(Round,20));}
                            //TscUSB.sendpicture(350,130,TextAsBitmap.getTextBitmap( m.getTime(),20));
                            TscUSB.qrcode(20,165,"H","3","A","0","M1","S1",QRCode);
                            TscUSB.printerfont(140,210,"0",0,9,9,Packdate);
                            TscUSB.printerfont(270,210,"0",0,9,9,Expiredate);
                            TscUSB.sendpicture(140,250,TextAsBitmap.getTextBitmap((PEmp1 ? emp1 : "" )+"/"+(PEmp2 ? emp2 : "" )+"/"+(PEmp3 ? emp3 : "" ),15));

                            //TscUSB.sendcommand("PRINT 1,1\r\n");
                            TscUSB.sendcommand("PRINT 1\r\n");
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    TscUSB.closeport(3000);

                    break;
            }

        }
    }
    public String[] make2line(String str){
        String[] line = new String[2];
        line[0]="";
        line[1]="";
        if(str.length()>22){
            char[]arr=str.toCharArray();
            for(int i=17;i<22;i++) {
                if (Character.isWhitespace(arr[i])) {
                    line[0] =str.substring(0,i);
                    line[1] = str.substring(i, str.length());
                }else{
                    //ตัดstringหลังตัวที่20ไปอีกบรรทัดใหม่
                    line[0] =str.substring(0,22);
                    line[1] = str.substring(22, str.length());
                }
            }
        }else{
            line[0]=str;
        }
        return line;
    }
    public void setspinnerprinter(){
        addprinter();
        nameprinter();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PreviewSticker_main.this,android.R.layout.simple_spinner_dropdown_item,arrayprinter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTSC.setAdapter(adapter);
        spinnerTSC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(PreviewSticker_main.this, position+"", Toast.LENGTH_SHORT).show();
                switch(position){
                    case 0 :
                        TscReveiw = TscDevice_1;
                        break;
                    case 1 :
                        TscReveiw = TscDevice_2;
                        break;
                    case 2 :
                        TscReveiw = TscDevice_3;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setxFlip(){
        addxFlip();
        addxFlipname();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PreviewSticker_main.this,android.R.layout.simple_spinner_dropdown_item,arrayxFlipname);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerxFlip.setAdapter(adapter);
        spinnerxFlip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(PreviewSticker_main.this, position+"", Toast.LENGTH_SHORT).show();
                xFlip= arrayxFlip.get(position);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void addxFlipname() {

        arrayxFlipname.add("แบบกลับหัว");
        arrayxFlipname.add("แบบปกติแนวตั้ง");

    }
    private void addxFlip() {

        arrayxFlip.add("0");
        arrayxFlip.add("1");

    }

}
