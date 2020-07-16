package com.phc.cssd;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.cssd.adapter.ApproveStockDetailAdapter;
import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.bean.Dir;
import com.phc.cssd.bean.File;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.viewbinder.ApFileNodeBinder;
import com.phc.cssd.viewbinder.DirectoryNodeBinder;
import com.stargreatsoftware.sgtreeview.TreeNode;
import com.stargreatsoftware.sgtreeview.TreeViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ApproveStockActivity extends Activity {
    ArrayList<Response_Aux> results = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> resultspayout = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> resultapprovestock = new ArrayList<Response_Aux>();
    private RecyclerView rv;
    private TreeViewAdapter adapter;

    private static final String TAG_RESULTS="result";
    JSONArray setRs = null;
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();

    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;

    TextView textView019;
    TextView textView020;
    TextView gDate;
    EditText eSearch;
    Button bDate;
    ImageView b_Save;
    ImageView b_Import;
    ImageView b_Cancel;
    String RefDocNo="";
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_stock);
        setTitle("บันทึกรับเข้าห้องปลอดเชื้อ");
        textView020 = (TextView) findViewById(R.id.textView020);
        textView019 = (TextView) findViewById(R.id.textView019);
        rv = (RecyclerView) findViewById(R.id.rv);

        Bundle bd = getIntent().getExtras();
        if (bd != null){
            RefDocNo = bd.getString("DocNo");
            userid =  bd.getString("userid");
        }

        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        ListData( RefDocNo,userid );

        textView019.setText( year + "-" + TwoZero((month + 1)+"") + "-" + TwoZero(day+"" ) );

        b_Import = (ImageView) findViewById(R.id.b_Import);
        b_Cancel = (ImageView) findViewById(R.id.button_search);
        b_Save = (ImageView) findViewById(R.id.b_Save);

        b_Cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if( !textView020.getText().toString().equals("")) {

                    new AlertDialog.Builder(ApproveStockActivity.this)
                            .setTitle("ยกเลิก...")
                            .setMessage("คุณต้องการที่จะยกเลิก เอกสารนี้ " + textView020.getText().toString() + " ใช่หรือไม่?")
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0, int arg1) {
                                    CancelData(textView020.getText().toString(), RefDocNo);
                                    Intent intent = new Intent(ApproveStockActivity.this, SterileSeachActivity.class);
                                    intent.putExtra("userid", userid);
                                    startActivity(intent);
                                    finish();
                                    //System.exit(0);
                                }
                            }).create().show();
                }
            }
        });

        b_Import.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ApproveStockActivity.this, SterileSeachActivity.class);
                intent.putExtra("userid", userid);
                startActivity(intent);
                finish();
            }
        });

        b_Save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            if(!textView020.getText().equals(""))
                UpdateStock( textView020.getText().toString());
//                Intent intent = new Intent(ApproveStockActivity.this, SterileSeachActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
        //ListSterileDocDetail(DocNo);
        ListApDocument("");
    }

    public void ListApDocument(String xDate) {
        class ListApDocument extends AsyncTask<String, Void, String> {
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
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultspayout.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields1(c.getString("xDocNo"));
                        newsData.setFields2("1"+c.getString("xRemark"));
                        newsData.setFields3("2"+c.getString("xIsStatus"));
                        if (!c.getString("xDocNo").equals("")) resultspayout.add( newsData );
                    }

                    if(setRs.length()>0) initData(resultspayout);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDate",params[0]);
                String result = ruc.sendPostRequest(iFt.getApdocument(),data);
                return  result;
            }
        }
        ListApDocument ru = new ListApDocument();
        ru.execute( xDate );
    }

    private void initData(ArrayList<Response_Aux> getData) {
        List<TreeNode> nodes = new ArrayList<>();
        TreeNode<Dir> app = new TreeNode<>(new Dir("เอกสารทั้งหมด [ " + getData.size()+" ]"));
        nodes.add(app);
        for (int i= 0;i<getData.size();i++) {
            app.addChild(
                    new TreeNode<>(new Dir(getData.get(i).getFields1()))
                            .addChild(new TreeNode<>(new File(getData.get(i).getFields2())))
                            .addChild(new TreeNode<>(new File(getData.get(i).getFields3())))
            );
        }

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TreeViewAdapter(nodes, Arrays.asList(new ApFileNodeBinder(), new DirectoryNodeBinder()));
        adapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
            @Override
            public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
                if (!node.isLeaf()) {
                    //Update and toggle the node.
                    onToggle(!node.isExpand(), holder);
                    if (!node.isExpand()) {
                        adapter.collapseBrotherNode(node);
                        int xPosition = holder.getAdapterPosition();
                        if(xPosition>0) {
                            String DocNo = resultspayout.get(xPosition - 1).getFields1();
                            textView020.setText( DocNo );
                            ListSterileDocDetail( DocNo );
                        }
                    }
                }
                return false;
            }

            @Override
            public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {
                DirectoryNodeBinder.ViewHolder dirViewHolder = (DirectoryNodeBinder.ViewHolder) holder;
                final ImageView ivArrow = dirViewHolder.getIvArrow();
                int rotateDegree = isExpand ? 90 : -90;
                ivArrow.animate().rotationBy(rotateDegree)
                        .start();
            }
        });
        rv.setAdapter(adapter);
    }

    public void ListData(String RefDocNo,String UserId) {
        class ListData extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String xDocNo="";
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    results.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        xDocNo = c.getString("DocNo");
                        if(!xDocNo.equals("")) {
                            textView020.setText(xDocNo);
                        }else{
                            Intent intent = new Intent(ApproveStockActivity.this, SterileSeachActivity.class);
                            intent.putExtra("userid", userid);
                            startActivity(intent);
                            finish();
                        }
                    }
                    ListSterileDocDetail( xDocNo );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("RefDocNo",params[0]);
                data.put("userid",params[1]);
                String result = ruc.sendPostRequest(iFt.createapprovestockdocno(),data);
                return  result;
            }
        }
        ListData ru = new ListData();
        ru.execute( RefDocNo,UserId );
    }

    public void UpdateStock(String xDocNo) {
        class UpdateStock extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String xDocNo="";
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    results.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        textView020.setText( c.getString("Finish") );
                    }
                    ListSterileDocDetail( xDocNo );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                data.put("xDptID",params[1]);
                String result = ruc.sendPostRequest(iFt.updatestockforapprovestock(),data);
                return  result;
            }
        }
        UpdateStock ru = new UpdateStock();
        ru.execute( xDocNo );
    }

    public void ListSterileDocDetail(String xDocNo) {
        class ListSterileDocDetail extends AsyncTask<String, Void, String> {
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
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    results.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields1(c.getString("xItemName"));
                        newsData.setFields2(c.getString("xUsageCode"));
                        newsData.setFields3(c.getString("xExDay"));
                        newsData.setFields4(c.getString("xQty"));
                        newsData.setFields5(c.getString("xSterileDate"));
                        newsData.setFields6(c.getString("xExpireDate"));
                        results.add( newsData );
                    }
                    final GridView lv1 = (GridView) findViewById(R.id.ListView03);
                    lv1.setAdapter(new ApproveStockDetailAdapter( ApproveStockActivity.this, results));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                String result = ruc.sendPostRequest(iFt.getapprovestockdetail(),data);
                return  result;
            }
        }
        ListSterileDocDetail ru = new ListSterileDocDetail();
        ru.execute(xDocNo);
    }

    public void CancelData( String DocNo,String RefDocNo ) {
        class ListData extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String xDocNo="";
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    results.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        textView020.setText( c.getString("Finish") );
                    }

                    ListSterileDocDetail( xDocNo );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                data.put("RefDocNo",params[1]);
                String result = ruc.sendPostRequest(iFt.cancelapprovestock(),data);
                return  result;
            }
        }
        ListData ru = new ListData();
        ru.execute( DocNo,RefDocNo );
    }


    private String TwoZero(String data){
        String dd = data;
        if(data.length() < 2 ) dd = "0"+data;
        return dd;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SterileSeachActivity.class);
        startActivity(intent);
        this.finish();
    }
}