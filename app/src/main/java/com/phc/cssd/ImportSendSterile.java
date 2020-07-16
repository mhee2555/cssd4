package com.phc.cssd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.phc.cssd.url.Url;
import com.phc.core.data.AsonData;
import com.phc.core.string.Cons;
import com.phc.cssd.adapter.SendSterileAdapter;
import com.phc.cssd.data.Master;
import com.phc.cssd.model.ModelSendSterile;

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

public class ImportSendSterile extends AppCompatActivity {
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
    private ListView list_send_sterile;
    private ImageView imageBack;
    //------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_send_sterile);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getSupportActionBar().hide();

        byWidget();

        // Display
        ImportSendSterile.BackgroundWorker backgroundWorker = new ImportSendSterile.BackgroundWorker(ImportSendSterile.this);
        backgroundWorker.execute("display", null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Display
        ImportSendSterile.BackgroundWorker backgroundWorker = new ImportSendSterile.BackgroundWorker(ImportSendSterile.this);
        backgroundWorker.execute("display", null);
    }

    private void byWidget(){
        list_send_sterile = (ListView)findViewById(R.id.list_send_sterile);

        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(Master.open_send_sterile, intent);
                finish();
            }
        });
        imageBack.bringToFront();
    }

    // =============================================================================================

    public class BackgroundWorker extends AsyncTask<String,Void,String> {
        Context context;
        BackgroundWorker (Context ctx) {
            context = ctx;
        }
        String TYPE;

        private ProgressDialog dialog = new ProgressDialog(ImportSendSterile.this);

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];

            String post_data = "";
            String url_ = null;

            TYPE = type;

            System.out.println("TYPE = " + TYPE);

            if(type.equals("display")) {
                url_ = Url.URL + "cssd_display_sendsterile.php";
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

                if(type.equals("display")) {
                   // post_data += "&p_docdate=" + ITEM_ID;
                }

                //System.out.println("URL = " + url_ + post_data);

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
                        ArrayAdapter<ModelSendSterile> adapter = new SendSterileAdapter(ImportSendSterile.this, getModel());
                        list_send_sterile.setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

        // =========================================================================================

        private List<ModelSendSterile> getModel() throws Exception{

            List<ModelSendSterile> list = new ArrayList<>();

            try {
                int index = 0;

                //System.out.println("data.size() = " + data.size());
                //System.out.println("size = " + size);

                for(int i=0;i<data.size();i+=size){

                    list.add(
                            get(

                                    data.get(i),
                                    data.get(i + 1),
                                    data.get(i + 2),
                                    data.get(i + 3),
                                    data.get(i + 4),
                                    data.get(i + 5),
                                    data.get(i + 6),
                                    data.get(i + 7),
                                    data.get(i + 8),
                                    data.get(i + 9),
                                    data.get(i + 10),
                                    data.get(i + 11),
                                    data.get(i + 12),
                                    index
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

        private ModelSendSterile get(
                String ID,
                String DocNo,
                String DocDate,
                String ModifyDate,
                String DeptID,
                String UserCode,
                String Qty,
                String Remark,
                String DepName,
                String DepName2,
                String UserName,
                String list_count,
                String total,
                int index
        ){
            return new ModelSendSterile(
                    ID,
                    DocNo,
                    DocDate,
                    ModifyDate,
                    DeptID,
                    UserCode,
                    Qty,
                    Remark,
                    DepName,
                    DepName2,
                    UserName,
                    list_count,
                    total,
                    index
                );
        }
        // =========================================================================================

    }

}

