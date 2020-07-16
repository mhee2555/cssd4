package com.phc.cssd;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.phc.core.data.AsonData;
import com.phc.core.string.Cons;
import com.phc.cssd.adapter.MasterDataAdapter;
import com.phc.cssd.model.ModelMasterData;
import com.phc.cssd.url.Url;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MasterData_2Field extends Activity {

    //------------------------------------------------
    // Background Worker Process Variable
    private boolean Success = false;
    private ArrayList<String> data = null;
    private int size = 0;
    //------------------------------------------------

    //------------------------------------------------
    // Session Variable
    private String UserName;
    private String EmpCode;
    private String ID;
    //------------------------------------------------

    //------------------------------------------------
    // Widget Variable
    private EditText edt_code;
    private EditText edt_name;

    private TextView txt_name;
    private TextView txt_column_name;

    private EditText txt_search;
    private Button btn_search;

    private Button btn_save;
    private Button btn_delete;
    private Button btn_clear;

    private ImageView imageBack;

    private ListView ListData;
    //------------------------------------------------

    //------------------------------------------------
    // Local Variable
    private boolean IsNew = true;
    private String Table = null;
    String ColumnName = "";
    //------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_data_2_field);

        bySession();

        byWidget();

        onDisplay();

    }

    private void bySession(){
        // Argument
        Intent intent = getIntent();
        UserName = intent.getStringExtra("UserName");
        EmpCode = intent.getStringExtra("EmpCode");
        ID = intent.getStringExtra("ID");
        Table = intent.getStringExtra("data");
    }

    private void byWidget(){

        imageBack = (ImageView) findViewById(R.id.imageBack);

        edt_code = (EditText) findViewById(R.id.edt_code);
        edt_name = (EditText) findViewById(R.id.edt_name);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_clear = (Button) findViewById(R.id.btn_clear);

        txt_search = (EditText) findViewById(R.id.txt_search);
        btn_search = (Button) findViewById(R.id.btn_search);

        ListData = (ListView) findViewById(R.id.ListData);

        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_column_name = (TextView) findViewById(R.id.txt_column_name);

        // Master Label
        if(Table.equals(Cons.ITEM_TYPE)){
            ColumnName = Cons.ITEM_TYPE_NAME;
        }else if(Table.equals(Cons.UNITS)){
            ColumnName = Cons.UNITS_TYPE_NAME;
        }else if(Table.equals(Cons.PACKING)){
            ColumnName = Cons.PACKING_TYPE_NAME;
        }else if(Table.equals(Cons.ManuFact)){
            ColumnName = Cons.ManuFact_TYPE_NAME;
        }else if(Table.equals(Cons.SUPPLIER)){
            ColumnName = Cons.SUPPLIER_TYPE_NAME;
        }

        txt_name.setText(ColumnName);
        txt_column_name.setText(ColumnName);

        // ------------------- Event --------------------

        txt_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                onDisplay();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundWorker backgroundWorker = new BackgroundWorker(MasterData_2Field.this);
                backgroundWorker.execute("save", ID, (IsNew ? "1" : "0" ), edt_code.getText().toString(), edt_name.getText().toString());
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder adb = new AlertDialog.Builder(MasterData_2Field.this);
                adb.setTitle(Cons.TITLE);
                adb.setMessage(Cons.CONFIRM_DELETE);

                adb.setNegativeButton("ยกเลิก", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        // Cancel Event
                    }
                });

                adb.setPositiveButton("ตกลง", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        BackgroundWorker backgroundWorker = new BackgroundWorker(MasterData_2Field.this);
                        backgroundWorker.execute("delete", ID, edt_code.getText().toString());
                    }
                });
                adb.show();
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClearForm();
            }
        });

        // listening to single list item on click
        ListData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IsNew = false;
                btn_save.setText("บันทึก(U)");

                // selected item
                String value = ((TextView)((LinearLayout) view).getChildAt(0)).getText().toString();
                String data = ((TextView)((LinearLayout) view).getChildAt(1)).getText().toString();

                edt_code.setText(value);
                edt_name.setText(data);

                //System.out.println("Data = " + data);

            }
        });

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void onDisplay(){
        // Display
        BackgroundWorker backgroundWorker = new BackgroundWorker(MasterData_2Field.this);
        backgroundWorker.execute("display", Table);
    }

    private void onClearForm(){
        // Form
        IsNew = true;

        btn_save.setText("บันทึก(N)");

        txt_search.setText("");
        edt_code.setText("");
        edt_name.setText("");


        onDisplay();
    }


    // =============================================================================================

    public class BackgroundWorker extends AsyncTask<String,Void,String> {
        Context context;
        BackgroundWorker (Context ctx) {
            context = ctx;
        }
        String TYPE;

        private ProgressDialog dialog = new ProgressDialog(MasterData_2Field.this);

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];

            String post_data = "";
            String url_ = null;

            TYPE = type;

            System.out.println("TYPE = " + TYPE);

            if(type.equals("delete")) {
                url_ = Url.URL + "cssd_delete_master.php";
            }else if(type.equals("save")) {
                url_ = Url.URL + "cssd_save_master.php";
            }else if(type.equals("display")) {
                url_ = Url.URL + "cssd_display_master.php";
            }else{
                return null;
            }

            try {

                URL url = new URL(url_);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                post_data = "p_table=" + Table;

                if(type.equals("display")) {
                    post_data += "&p_filter=" + txt_search.getText().toString();
                }else if(type.equals("save")) {
                    String ID = params[1];
                    String IsNew = params[2];
                    String Code = params[3];
                    String Name = params[4];

                    post_data += "&p_is_new=" + IsNew;
                    post_data += "&p_code=" + Code;
                    post_data += "&p_name=" + Name;
                    post_data += "&p_ID=" + ID;

                }else if(type.equals("delete")) {
                    String id = params[2];
                    post_data += "&p_id=" + id;
                }

                System.out.println("URL = " + url_ + post_data);

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // ===========================================================================================

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

                String result="";
                String line="";

                if((line = bufferedReader.readLine())!= null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            AsonData ason = new AsonData(result);

            Success = ason.isSuccess();
            size = ason.getSize();
            data = ason.getASONData();

            if(Success && data != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

                if(TYPE.equals("display")){
                    try {
                        ArrayAdapter<ModelMasterData> adapter = new MasterDataAdapter(MasterData_2Field.this, getModel());
                        ListData.setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(TYPE.equals("delete")){
                    onClearForm();
                }else if(TYPE.equals("save")){
                    onClearForm();
                }
            }else{
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        private List<ModelMasterData> getModel() throws Exception{

            List<ModelMasterData> list = new ArrayList<>();

            try {
                int index = 0;

                //System.out.println("size() = " + size);
                //System.out.println("data.size() = " + data.size());

                for(int i=0;i<data.size();i+=size){

                    list.add(
                            get(
                                    index,
                                    data.get(i),
                                    data.get(i + 1)
                            )
                    );

                    index++;
                }

               // System.out.println("list = " + list.size());

            }catch(Exception e){
                e.printStackTrace();
            }

            return list;
        }

        private ModelMasterData get(int index, String code, String name) {
            return new ModelMasterData(
                    index,
                    code,
                    name
            );
        }

    }

}
