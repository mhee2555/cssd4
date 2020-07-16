package com.phc.cssd;

import android.app.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.string.Cons;
import com.phc.cssd.adapter.AdapterRecallNew;
import com.phc.cssd.model.Modelrecallnew;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.viewbinder.DirectoryNodeBinder_Recall_dialog;
import com.stargreatsoftware.sgtreeview.TreeViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class dialog_search_recall extends Activity {

    ArrayList<String> array_doctypeno = new ArrayList<String>();

    HashMap<String, String> CheckDoc1 = new HashMap<String,String>();
    public ArrayList<String> CheckDoc2 = new ArrayList<String>();
    String CheckDocNo;

    Spinner spinner_doctypeno;
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    ListView Rv_new;
    Calendar myCalendar = Calendar.getInstance();
    HTTPConnect ruc = new HTTPConnect();
    ImageView backbtn;
    ImageView imgprint;
    ImageView imagecalendar;
    Button button_search;
    TextView txtdate;
    Intent intent;
    String B_ID;
    String type = "0";
    String type1 = "1";
    TreeViewAdapter adapter;
    DirectoryNodeBinder_Recall_dialog dirr;

    private List<com.phc.cssd.model.Modelrecallnew> Modelrecallnew = null;
    private String TAG_RESULTS="result";
    private JSONArray rs = null;

    String SelectDocNo;
    String recalltypeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_search_recall);

        byIntent();

        initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (spinner_doctypeno.getSelectedItem().toString().equals("1.เรียกคืนของหมดอายุ")){
            getlistdata("1",txtdate.getText().toString(),"0");
        }else if (spinner_doctypeno.getSelectedItem().toString().equals("2.ความเสี่ยง")){
            getlistdata("2",txtdate.getText().toString(),"0");
        }
    }

    private void byIntent() {
        intent = getIntent();
        B_ID = intent.getStringExtra("B_ID");
    }

    private void initialize() {
        spinner_doctypeno = (Spinner) findViewById(R.id.doctypeno_spn);
        backbtn = (ImageView) findViewById(R.id.imageback);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        backbtn.bringToFront();
        imgprint = (ImageView) findViewById(R.id.imageprint);
        imgprint.setBackgroundResource(R.drawable.ic_print_disable);
        Rv_new = (ListView) findViewById(R.id.Rv_new);
        Rv_new.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                SelectDocNo = Modelrecallnew.get(position).getDocNo();
            }
        });

        imgprint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int cnt=0;
                for (String key : CheckDoc1.keySet()) {
                    if (CheckDoc1.get(key) == "0")
                        cnt++;
                }
                if (cnt != 0) {
                    for (String key : CheckDoc1.keySet()) {
                        if (CheckDoc1.get(key) == "0")
                            CheckDoc2.add(key);
                    }
                    if (!spinner_doctypeno.getSelectedItem().toString().equals("-")) {
                        String choice = spinner_doctypeno.getSelectedItem().toString();
                        String[] arr = choice.split("\\.");
                        GetReport(arr[0], txtdate.getText().toString(), B_ID, String.valueOf(CheckDoc2));
                    } else {
                        GetReport(spinner_doctypeno.getSelectedItem().toString(), txtdate.getText().toString(), B_ID, String.valueOf(CheckDoc2));
                    }
                }else {
                    if (!spinner_doctypeno.getSelectedItem().toString().equals("-")){
                        Toast.makeText(dialog_search_recall.this, "กรุณาเลือกรายการ", Toast.LENGTH_SHORT).show();
                    }else {
                        String choice = spinner_doctypeno.getSelectedItem().toString();
                        String[] arr = choice.split("\\.");
                        GetReport(arr[0], txtdate.getText().toString(), B_ID, String.valueOf(CheckDoc2));
                    }
                }
                CheckDoc1.clear();
                CheckDoc2.clear();
            }
        });

        imagecalendar = (ImageView) findViewById(R.id.imgcalendar);

        button_search = (Button) findViewById(R.id.button_search);

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!spinner_doctypeno.getSelectedItem().toString().equals("-")){
                    String choice = spinner_doctypeno.getSelectedItem().toString();
                    String[] arr = choice.split("\\.");
                    getlistdata(arr[0],txtdate.getText().toString(),type);
                    imgprint.setBackgroundResource(R.drawable.ic_print_1);
                    imgprint.setEnabled(true);
                }else{
                    getlistdata(spinner_doctypeno.getSelectedItem().toString(),txtdate.getText().toString(),type1);
                    imgprint.setBackgroundResource(R.drawable.ic_print_disable);
                    imgprint.setEnabled(false);
                }
                CheckDoc1.clear();
                CheckDoc2.clear();
            }
        });

        dirr = new DirectoryNodeBinder_Recall_dialog(dialog_search_recall.this);
        txtdate = (TextView) findViewById(R.id.txtdate);

        getsdate(txtdate,imagecalendar);
        doctypeno_define();
    }

    public void getlistdata(final String Doctypeno, final String Date, final String type) {
        class getlistdata extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(dialog_search_recall.this);
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    List<Modelrecallnew> list = new ArrayList<>();
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (c.getString("flag").equals("true")) {
                            list.add(
                                    get(
                                            c.getString("DocNo"),
                                            c.getString("DepName2"),
                                            c.getString("MachineName2"),
                                            c.getString("SterileRoundNumber"),
                                            c.getString("DocDate"),
                                            c.getString("IsStatus"),
                                            c.getString("DocTypeNo"),
                                            c.getString("Remark")
                                    )
                            );
                            Modelrecallnew = list;
                            ArrayAdapter<Modelrecallnew> adapter;
                            adapter = new AdapterRecallNew(dialog_search_recall.this, Modelrecallnew,type);
                            Rv_new.setAdapter(adapter);
                        }else {
                            Rv_new.setAdapter(null);
                            Toast.makeText(dialog_search_recall.this, "ไม่พบข้อมูล !!", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("B_ID",B_ID);
                data.put("Doctypeno", Doctypeno);
                data.put("Date", Date);
                String result = null;
                try {
                    result = ruc.sendPostRequest(getUrl.xUrl + "cssd_display_recall_new.php", data);
                    Log.d("BANK",data+"");
                    Log.d("BANK",result);
                }catch(Exception e){
                    e.printStackTrace();
                }

                return result;
            }
            private Modelrecallnew get(String DocNo, String DepName2,String MachineName2,String SterileRoundNumber,String DocDate,String IsStatus,String DocTypeNo, String Remark) {
                return new Modelrecallnew( DocNo,DepName2,MachineName2,SterileRoundNumber,DocDate,IsStatus,DocTypeNo,Remark);
            }
            // =========================================================================================
        }

        getlistdata obj = new getlistdata();
        obj.execute();
    }

    private void GetReport(final String recalltypeid_recall, final String eDate, final String bid, final String docnorecall) {
        String url = "";

        if (recalltypeid_recall.equals("-")){
            recalltypeid = "4";
        }else if (recalltypeid_recall.equals("1")){
            recalltypeid = "1";
        }else if (recalltypeid_recall.equals("2")){
            recalltypeid = "3";
        }else if (recalltypeid_recall.equals("3")){
            recalltypeid = "3";
        }

        url = getUrl.xUrl+"report/tcreport/Report_Recall.php?eDate=" + eDate + "&bid=" + bid + "&docno="+ docnorecall;

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

        String spinner_doctypeno1;
        spinner_doctypeno1 = spinner_doctypeno.getSelectedItem().toString().substring(0,1);

        if (recalltypeid_recall.equals("-")) {
            String choice = spinner_doctypeno.getSelectedItem().toString();
            String[] arr = choice.split("\\.");
            getlistdata(arr[0],txtdate.getText().toString(),"1");
        }else {
            getlistdata(spinner_doctypeno1, txtdate.getText().toString(),"0");
        }
    }

//    public void get_datatree_recall(String Doctypeno,String Date) {
//        class get_datatree_recall extends AsyncTask<String, Void, String> {
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                try {
//                    Response_Aux_Recall newsData;
//                    JSONObject jsonObj = new JSONObject(s);
//                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
//                    array_recall.clear();
//                    String Docstr1 = "";
//                    String Docstr2 = "";
//                    String Docstr3 = "";
//                    String Docstr4 = "";
//                    String Docstr5 = "";
//                    String Docstr6 = "";
//                    String Docstr7 = "";
//                    String Docstr8 = "";
//                    Boolean flag = false;
//                    List<List<String>> listOfLists = new ArrayList<List<String>>();
//                    for(int i=0;i<setRs.length();i++){
//                        JSONObject c = setRs.getJSONObject(i);
//                        if(c.getString("bool").equals("true")){
//                            Docstr1 = c.getString("DocNo");
//                            Docstr2 = c.getString("Dept");
//                            Docstr3 = c.getString("Remark");
//                            Docstr4 = c.getString("IsStatus");
//                            Docstr5 = c.getString("RefDocNo");
//                            Docstr6 = c.getString("Detail");
//                            Docstr7 = c.getString("DocTypeNo");
//                            Docstr8 = c.getString("DocTypeNo");
//                            flag = true;
//                        }
//                    }
//
//                    if(flag==true) {
//                        try {
//                            String[] Doctypeno = Docstr7.split(",");
//                            String[] Doctemp = Docstr1.split("//");
//                            String[] Depttemp = Docstr2.split("//");
//                            String[] Remarktemp = Docstr3.split("//");
//                            String[] Reftemp = Docstr5.split("//");
//                            String[] Statustemp = Docstr4.split("//");
//                            String[] Itemtemp = Docstr6.split("//");
//                            if(Doctypeno.length==1){
//                                Itemtemp[0] = Itemtemp[0].substring(0, Itemtemp[0].length() - 1);
//                            }else if(Doctypeno.length==2){
//                                Itemtemp[0] = Itemtemp[0].substring(0, Itemtemp[0].length() - 1);
//                                Itemtemp[1] = Itemtemp[1].substring(0, Itemtemp[1].length() - 1);
//                            }else if(Doctypeno.length==3){
//                                Itemtemp[0] = Itemtemp[0].substring(0, Itemtemp[0].length() - 1);
//                                Itemtemp[1] = Itemtemp[1].substring(0, Itemtemp[1].length() - 1);
//                                Itemtemp[2] = Itemtemp[2].substring(0, Itemtemp[2].length() - 1);
//                            }
//
//                            String Docstr10 ="";
//                            if(Doctypeno.length==1){
//                                Docstr10 = Itemtemp[0];
//                            }else if(Doctypeno.length==2){
//                                Docstr10 = Itemtemp[0] + "//" + Itemtemp[1];
//                            }else if(Doctypeno.length==3){
//                                Docstr10 = Itemtemp[0] + "//" + Itemtemp[1] + "//" + Itemtemp[2];
//                            }
//
//                            Log.d("popup", Docstr1+"");
//                            if(!Docstr1.toString().equals("null")){
//                                Rv.setVisibility(View.VISIBLE);
//                                initData(Docstr7,Docstr1, Docstr10,Docstr5,Docstr3,Docstr4,Docstr2,Docstr8);
//                            }else{
//
//                                Rv.setVisibility(View.INVISIBLE);
//                                Toast.makeText(dialog_search_recall.this, "ไม่พบข้อมูล", Toast.LENGTH_SHORT).show();
//                            }
//                        }catch (ArrayIndexOutOfBoundsException e){
//                            e.printStackTrace();
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//                HashMap<String, String> data = new HashMap<String,String>();
//                data.put("Doctypeno",params[0]);
//                data.put("Date",params[1]);
//                data.put("B_ID",B_ID);
//                String result="";
//                result = ruc.sendPostRequest(iFt.get_datatree_dialog_recall(),data);
//                Log.d("BANKM3",data+"");
//                Log.d("BANKM3",result);
//                return  result;
//            }
//        }
//
//        get_datatree_recall ru = new get_datatree_recall();
//        ru.execute(Doctypeno,Date);
//    }
//
//
//    private void initData(String Data1,String Data2,String Data3,String Data4,String Data5,String Data6,String Data7,String Data8) {
//
//        List getData1 = new ArrayList<String>(Arrays.asList(Data1.split(",")));
//        List getData2 = new ArrayList<String>(Arrays.asList(Data2.split("//")));
//        List getData3 = new ArrayList<String>(Arrays.asList(Data3.split("//")));
//        List getData4 = new ArrayList<String>(Arrays.asList(Data4.split("//")));
//        List getData5 = new ArrayList<String>(Arrays.asList(Data5.split("//")));
//        List getData6 = new ArrayList<String>(Arrays.asList(Data6.split("//")));
//        List getData7 = new ArrayList<String>(Arrays.asList(Data7.split("//")));
//        List getData8 = new ArrayList<String>(Arrays.asList(Data8.split("//")));
//
//        List<TreeNode> nodes = new ArrayList<>();
////        TreeNode<DirRecall> app = new TreeNode<>(new DirRecall("เอกสารเรียกคืนทั้งหมด@@0"));
////        app.expand();
//
//        if(Data1.equals("")){
//            nodes.clear();
//            TreeNode treeNode = new TreeNode<>(new DirRecall( "" ));
//            return;
//        }else{
////            nodes.add(app);
//        }
//
//        for (int i= 0;i<getData1.size();i++) {
//
//            System.out.println("xxx = " + getData1.get(i).toString());
//
//            List ListData = new ArrayList<String>(Arrays.asList(getData2.get(i).toString().split(",")));
//            List ListData2 = new ArrayList<String>(Arrays.asList(getData3.get(i).toString().split("#")));
//            List ListData4 = new ArrayList<String>(Arrays.asList(getData4.get(i).toString().split(",")));
//            List ListData5 = new ArrayList<String>(Arrays.asList(getData5.get(i).toString().split(",")));
//            List ListData6 = new ArrayList<String>(Arrays.asList(getData6.get(i).toString().split(",")));
//            List ListData7 = new ArrayList<String>(Arrays.asList(getData7.get(i).toString().split(",")));
//            List ListData8 = new ArrayList<String>(Arrays.asList(getData8.get(i).toString().split(",")));
//
//
//            for (int k = 0; k < ListData.size(); k++) {
//                List ListData3 = new ArrayList<String>(Arrays.asList(ListData2.get(k).toString().split(",")));
//                String State ="";
//                if(ListData6.get(k).toString().equals("0")){
//                    State = "ร่างเอกสาร";
//                }else if(ListData6.get(k).toString().equals("1")){
//                    State = "รอยืนยัน";
//                }else if(ListData6.get(k).toString().equals("2")){
//                    State = "ยืนยันแล้ว";
//                }else if(ListData6.get(k).toString().equals("3")){
//                    State = "ยกเลิก";
//                }
//
//                String ref = "";
//                if(ListData4.get(k).toString().equals("%")){
//                    ref = "";
//                }else{
//                    ref = "[REF:"+ListData4.get(k).toString()+"]";
//                }
//
//                String Type = "";
//
//                if(ListData8.equals("2")){
//                    Type = "ของใกล้หมดอายุ";
//                }else if(ListData8.equals("3")){
//                    Type = "ของหมดอายุ";
//                }
//
//                Log.d("BANKM3",ListData8+"");
//
//                TreeNode treeNode1 = new TreeNode(new DirRecall(Type + "  | " +" แผนก : "+ListData7.get(k).toString()+"    ["+ListData.get(k).toString()+"]    "+ref+"\nสถานะ : "+State+"@@1@@"+ListData6.get(k).toString()+"@@"+ListData5.get(k).toString()+"@@"+ListData.get(k).toString() ));
//
//                //treeNode.addChild(treeNode1);
//                nodes.add( treeNode1 );
//
//                for (int j=0;j<ListData3.size();j++){
//                    TreeNode treeNode2 = new TreeNode(new FileRecall( ListData3.get(j).toString() ));
//                    treeNode1.addChild(treeNode2);
//
//                }
//            }
//
//
//        }
//
//        Rv.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new TreeViewAdapter(nodes, Arrays.asList(new FileNodeBinder_Recall(), new DirectoryNodeBinder_Recall_dialog(dialog_search_recall.this)));
//
//        adapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
//            @Override
//            public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
//                if (!node.isLeaf()) {
//                    //Update and toggle the node.
//                    onToggle(!node.isExpand(), holder);
//                    if (!node.isExpand()) {
//                        adapter.collapseBrotherNode(node);
//                        int xPosition = holder.getAdapterPosition();
//                    }
//                }
//                return false;
//            }
//
//            @Override
//            public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {
//                DirectoryNodeBinder_Recall_dialog.ViewHolder dirViewHolder = (DirectoryNodeBinder_Recall_dialog.ViewHolder) holder;
//                final ImageView ivArrow = dirViewHolder.getIvArrow();
//                int rotateDegree = isExpand ? 90 : -90;
//                ivArrow.animate().rotationBy(rotateDegree)
//                        .start();
//            }
//        });
//        Rv.setAdapter(adapter);
//    }

    public void getsdate(final TextView txtdate,final ImageView imagecalendar){
        presentdate(txtdate);
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

        imagecalendar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(dialog_search_recall.this,R.style.AppTheme1, sdate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void presentdate(final TextView txtdate) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtdate.setText(sdf.format(myCalendar.getTime()));
        myFormat = "yyyy-MM-dd"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat, Locale.US);
    }

    public void doctypeno_define() {
        array_doctypeno.add("-");
        //array_doctypeno.add("1.เรียกคืนของใกล้หมดอายุ");
        array_doctypeno.add("1.เรียกคืนของหมดอายุ");
        array_doctypeno.add("2.ความเสี่ยง");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(dialog_search_recall.this,android.R.layout.simple_spinner_dropdown_item,array_doctypeno);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_doctypeno.setAdapter(adapter);
    }

    public void CheckDoc (String CheckDocno){

        String x="0";
        if(CheckDoc1.get(CheckDocno)=="0"){
            x="1";
        }
        CheckDoc1.put(CheckDocno, x);
    }

}
