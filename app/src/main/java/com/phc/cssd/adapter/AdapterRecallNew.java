package com.phc.cssd.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.Itemdetail_Recall_Activity;
import com.phc.cssd.R;
import com.phc.cssd.dialog_search_recall;
import com.phc.cssd.model.Modelrecallnew;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AdapterRecallNew extends ArrayAdapter {

    private final List<Modelrecallnew> DATA_MODEL;
    private final Activity context;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    private String TAG_RESULTS="result";
    private JSONArray rs = null;

    TextView Doctype;
    TextView DepRecall;
    TextView Docrecall;
    TextView typerecall;
    TextView Mac;
    TextView Rou;
    TextView Date;
    TextView IsStatusRecall;
    CheckBox checkDoc;
    RelativeLayout iv_arrow;
    private TextView txtremark;
    ImageView remarkrecall;

    String type = null;
    String remark = "";
    private int position;

    public AdapterRecallNew(Activity context, List<Modelrecallnew> DATA_MODEL, String type) {
        super(context, R.layout.activity_adapter_recall_new, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.type = type;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.activity_adapter_recall_new, parent, false);

        Doctype = (TextView) v.findViewById(R.id.Doctype);
        DepRecall = (TextView) v.findViewById(R.id.DepRecall);
        Docrecall = (TextView) v.findViewById(R.id.Docrecall);
        typerecall = (TextView) v.findViewById(R.id.typerecall);
        Mac = (TextView) v.findViewById(R.id.Mac);
        Rou  = (TextView) v.findViewById(R.id.Rou);
        Date = (TextView) v.findViewById(R.id.Date);
        IsStatusRecall = (TextView) v.findViewById(R.id.IsStatusRecall);
        remarkrecall = (ImageView ) v.findViewById(R.id.remarkrecall);
        txtremark = (TextView) v.findViewById(R.id.txtremark);
        iv_arrow = (RelativeLayout) v.findViewById(R.id.iv_arrow);
        checkDoc = (CheckBox) v.findViewById(R.id.checkDoc);

        if (type.equals("0")){
            Log.d("BANK",type);
            checkDoc.setVisibility(View.VISIBLE);
        }else {
            Log.d("BANK",type);
            checkDoc.setVisibility(View.INVISIBLE);
        }

        if (DATA_MODEL.get(position).getDocTypeNo().equals("1")){
            Doctype.setText("ของหมดอายุ :");
        }else if (DATA_MODEL.get(position).getDocTypeNo().equals("2")){
            Doctype.setText("ความเสี่ยง :");
        }

        checkDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((dialog_search_recall) context).CheckDoc(DATA_MODEL.get(position).getDocNo());
            }
        });

        DepRecall.setText("แผนก : "+DATA_MODEL.get(position).getDepName2());

        Docrecall.setText("[ "+DATA_MODEL.get(position).getDocNo()+" ]");

        if (DATA_MODEL.get(position).getMachineName2().equals("null")){
            Mac.setText("เครื่อง : -");
        }else {
            Mac.setText("เครื่อง : "+DATA_MODEL.get(position).getMachineName2());
        }

        if (DATA_MODEL.get(position).getSterileRoundNumber().equals("null")){
            Rou.setText("รอบ : -");
        }else {
            Rou.setText("รอบ : "+DATA_MODEL.get(position).getSterileRoundNumber());
        }

        if (DATA_MODEL.get(position).getDocDate().equals("null")){
            Date.setText("วันที่ : -");
        }else {
            Date.setText("วันที่ : "+DATA_MODEL.get(position).getDocDate());
        }

        if (DATA_MODEL.get(position).getIsStatus().equals("0")){
            IsStatusRecall.setText("สถานะ                       : "+" ร่างเอกสาร");
        }else if (DATA_MODEL.get(position).getIsStatus().equals("1")){
            IsStatusRecall.setText("สถานะ                       : "+" รอยืนยัน");
        }else if (DATA_MODEL.get(position).getIsStatus().equals("2")){
            IsStatusRecall.setText("สถานะ                       : "+" ยืนยันแล้ว");
        }else if (DATA_MODEL.get(position).getIsStatus().equals("3")){
            IsStatusRecall.setText("สถานะ                       : "+" ยกเลิก");
        }

        if (DATA_MODEL.get(position).getRemark().equals("0")){
            remarkrecall.setBackgroundResource(R.drawable.ic_list_grey);
        }else {
            remarkrecall.setBackgroundResource(R.drawable.ic_list_blue);
        }

        remarkrecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getremark(DATA_MODEL.get(position).getDocNo());
            }
        });

        iv_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Itemdetail_Recall_Activity.class);
                i.putExtra("DocNo",DATA_MODEL.get(position).getDocNo());
                context.startActivity(i);
            }
        });

        return v;
    }

    public String convertdate(String date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        DateFormat newformat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date condate;
        try {
            condate = format.parse(date);
            condate = newformat.parse(condate.toString());
            date = condate.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  date;
    }

    public void remark_recall(String DocNo,String Remark) {
        class remark_recall extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            Toast.makeText(context, "บันทึกหมายเหตุสำเร็จ", Toast.LENGTH_SHORT).show();
                            (( dialog_search_recall )context).onResume();
                        }else{
                            Toast.makeText(context, "บันทึกหมายเหตุล้มเหลว", Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                data.put("Remark",params[1]);
                //data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.remark_recall(),data);
                Log.d("FNGDDF",data+"");
                Log.d("FNGDDF",result);
                return  result;
            }
        }

        remark_recall ru = new remark_recall();
        ru.execute(DocNo,Remark);
    }

    public void getremark(String DocNo) {
        class remark_recall extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        remark = c.getString("Remark");
                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.remark_dialog);
                        dialog.setCancelable(true);
                        dialog.setTitle("หมายเหตุ...");

                        final EditText note1 = (EditText) dialog.findViewById(R.id.note1);
                        if (remark.equals("null")){
                            note1.setText("");
                        }else {
                            note1.setText(remark);
                        }
                        Button button1 = (Button) dialog.findViewById(R.id.button1);
                        button1.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                remark_recall(DATA_MODEL.get(position).getDocNo(),note1.getText().toString());
                                dialog.dismiss();
                            }
                        });

                        Button button5 = (Button) dialog.findViewById(R.id.button5);
                        button5.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                String result = ruc.sendPostRequest(iFt.get_remark_recall(),data);
                Log.d("FNGDDF",data+"");
                Log.d("FNGDDF",result);
                return  result;
            }
        }

        remark_recall ru = new remark_recall();
        ru.execute(DocNo);
    }

}