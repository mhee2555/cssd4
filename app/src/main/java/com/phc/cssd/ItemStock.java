package com.phc.cssd;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.adapter.AuxAdapter;
import com.phc.cssd.function.KeyboardUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemStock extends AppCompatActivity {

    ArrayList<Response_Aux> ArrayData = new ArrayList<Response_Aux>();
    ArrayList<String> SpinnerArr = new ArrayList<String>();
    EditText item_name;
    EditText item_code;
    EditText usage_code;
    Spinner dept;
    EditText qty;
    Button button_add;
    Button button_change_dept;
    ListView list_data;

    String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    private String Item_Code;
    private String userid;
    private int index_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_stock);
        setTitle("Item Stock");
        byIntent();
        initialize();
        CreateItem(Item_Code);

    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        Item_Code = intent.getStringExtra("Item_Code");
        userid = intent.getStringExtra("userid");
    }

    private void initialize(){
        //Item_Code = "P85001";





        item_code = (EditText) findViewById(R.id.item_code);
        item_code.setFocusable(false);
        item_code.setFocusableInTouchMode(false);
        item_name = (EditText) findViewById(R.id.item_name);
        item_name.setFocusable(false);
        item_name.setFocusableInTouchMode(false);
        usage_code = (EditText) findViewById(R.id.usage_code);
        usage_code.setFocusable(false);
        usage_code.setFocusableInTouchMode(false);
        dept = (Spinner) findViewById(R.id.dept);
        qty = (EditText) findViewById(R.id.qty);
        button_add = (Button) findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!qty.getText().toString().equals("") && !item_name.getText().toString().equals("") && !item_code.getText().toString().equals("") && !usage_code.getText().toString().equals("")) {
                    Add_SetData(item_code.getText().toString(), qty.getText().toString(), dept.getSelectedItem().toString());
                    ListData(Item_Code);
                }else{
                    Toast.makeText(ItemStock.this,"กรุณากรอกข้อมูลให้ถูกต้อง",Toast.LENGTH_LONG).show();
                }
            }
        });
        button_change_dept = (Button) findViewById(R.id.button_change_dept);
        button_change_dept.setClickable(false);
        button_change_dept.setEnabled(false);
        button_change_dept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getCountselected().equals("0")) {
                    Change_dept(getSelectedcheckbox(), getCountselected(), dept.getSelectedItem().toString());
                    ListData(Item_Code);
                    toggleButtonAll(false);
                }else{
                    Toast.makeText(ItemStock.this,"กรุณาเลือกข้อมูลอย่างน้อย 1 รายการ",Toast.LENGTH_LONG).show();
                }
            }
        });

        list_data = (ListView) findViewById(R.id.list_data);
        KeyboardUtils.hideKeyboard(ItemStock.this);

    }

    public void CreateItem(String xItem_Code) {
        class CreateItem extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    ArrayData.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")){
                            ListData(Item_Code);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Item_Code",params[0]);
                String result = ruc.sendPostRequest(iFt.CreatItemstock(),data);
                return  result;
            }
        }

        CreateItem ru = new CreateItem();
        ru.execute( xItem_Code );
    }

    public void ListData(String Item_Code) {
        class ListData extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    ArrayData.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
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
                        newsData.setFields10(c.getString("xItem_Code"));
                        ArrayData.add( newsData );
                    }
                    list_data.setAdapter(new AuxAdapter( ItemStock.this, ArrayData));

                    list_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Object o = list_data.getItemAtPosition(position);
                            Response_Aux newsData = (Response_Aux) o;
                            item_name.setText(newsData.getFields9());
                            item_code.setText(newsData.getFields2());
                            usage_code.setText(newsData.getFields8());
                            SpinnerData(newsData.getFields1(),"true");
                        }
                    });

                    list_data.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                        public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                                       int index, long arg3) {
                            registerForContextMenu(list_data);
                            index_data = index;
                            return false;
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Item_Code",params[0]);
                String result = ruc.sendPostRequest(iFt.getItemstock(),data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Item_Code );
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add("ลบข้อมูล");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getTitle().equals("ลบข้อมูล")){
            AlertDialog.Builder builder = new AlertDialog.Builder(ItemStock.this);
            builder.setCancelable(true);
            builder.setTitle("Confirm");
            builder.setMessage("ต้องการลบข้อมูลใช่หรือไม่");
            builder.setPositiveButton("ตกลง",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            OnDeleteSelected(index_data);
                        }
                    });
            builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }

        return super.onContextItemSelected(item);
    }

    public void OnDeleteSelected(int index){
        Object o = list_data.getItemAtPosition(index);
        Response_Aux newsData = (Response_Aux) o;

        ChangeStatus(newsData.getFields1(),newsData.getFields10(),newsData.getFields8());
        ListData(Item_Code);
    }

    public void ChangeStatus(String Item_Codestock,String Item_Code,String Usage_id) {
        class ChangeStatus extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")){
                            Toast.makeText(ItemStock.this,"เปลี่ยนสถานะสำเร็จ",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ItemStock.this,"เปลี่ยนสถานะล้มเหลว",Toast.LENGTH_LONG).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Rowid_itemstock",params[0]);
                data.put("Item_Codestock",params[1]);
                data.put("Usage_id",params[2]);
                String result = ruc.sendPostRequest(iFt.Changestatus_itemstock(),data);
                return  result;
            }
        }

        ChangeStatus ru = new ChangeStatus();
        ru.execute( Item_Codestock,Item_Code,Usage_id );
    }

    public void SpinnerData(String Item_Code, final String boolclick) {
        class SpinnerData extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    SpinnerArr.clear();
                    SpinnerArr.add("-");
                    String Depsel = "";
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        SpinnerArr.add(c.getString("xDepName2"));
                        if(i==0 && (boolclick!=null && boolclick.equals("true"))){
                            Depsel = c.getString("xDepSel");
                        }else if(i==0 && (boolclick!=null && boolclick.equals("false"))){
                            Depsel = "-";
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ItemStock.this,android.R.layout.simple_spinner_dropdown_item,SpinnerArr);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dept.setAdapter(adapter);
                    int spinnerPosition = adapter.getPosition(Depsel);
                    dept.setSelection(spinnerPosition);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Item_Code",params[0]);
                String result = ruc.sendPostRequest(iFt.getDepartment_spinner(),data);
                return  result;
            }
        }

        SpinnerData ru = new SpinnerData();
        ru.execute( Item_Code,boolclick );
    }

    public void Add_SetData(String Item_Code,String Qty,String Dept) {
        class Add_SetData extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")){
                            item_name.setText("");
                            item_code.setText("");
                            usage_code.setText("");
                            qty.setText("");
                            dept.setSelection(0);
                            Toast.makeText(ItemStock.this, "เพิ่มข้อมูลสำเร็จ", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ItemStock.this, "เพิ่มข้อมูลล้มเหลว", Toast.LENGTH_LONG).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Item_Code",params[0]);
                data.put("Qty",params[1]);
                data.put("Dept",params[2]);
                String result = ruc.sendPostRequest(iFt.AddSetData(),data);
                return  result;
            }
        }

        Add_SetData ru = new Add_SetData();
        ru.execute(Item_Code,Qty,Dept);
    }

    public String getSelectedcheckbox(){
        String Arraysel = "";
        for (int i=0;i<ArrayData.size();i++){
            if(i<ArrayData.size()) {
                if(ArrayData.get(i).isIs_Check()) {
                    Arraysel += ArrayData.get(i).getFields1() + ",";
                }
            }
        }
        Arraysel = Arraysel.substring(0, Arraysel.length() - 1);
        Log.d("test", Arraysel);
        return Arraysel;
    }

    public String getCountselected(){
        int count = 0;
        for (int i=0;i<ArrayData.size();i++){
            if(ArrayData.get(i).isIs_Check()) {
                count++;
            }
        }
        return String.valueOf(count);
    }

    public void Change_dept(String Item_Code,String Count,String Dept) {
        class Change_dept extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")){
                            item_name.setText("");
                            item_code.setText("");
                            usage_code.setText("");
                            qty.setText("");
                            dept.setSelection(0);
                            Toast.makeText(ItemStock.this, "เปลี่ยนแผนกสำเร็จ", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ItemStock.this, "เปลี่ยนแผนกล้มเหลว", Toast.LENGTH_LONG).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Rowid_itemstock",params[0]);
                data.put("Count",params[1]);
                data.put("Dept",params[2]);
                String result = ruc.sendPostRequest(iFt.Change_dept(),data);
                return  result;
            }
        }

        Change_dept ru = new Change_dept();
        ru.execute(Item_Code,Count,Dept);
    }

    public void toggleButtonAll(boolean bool){
        item_name.setText("");
        item_code.setText("");
        usage_code.setText("");
        qty.setText("");
        dept.setSelection(0);

        if(bool==true) {
            button_add.setClickable(false);
            button_add.setEnabled(false);
            button_change_dept.setClickable(true);
            button_change_dept.setEnabled(true);
        }else{
            button_add.setClickable(true);
            button_add.setEnabled(true);
            button_change_dept.setClickable(false);
            button_change_dept.setEnabled(false);
        }
    }


}