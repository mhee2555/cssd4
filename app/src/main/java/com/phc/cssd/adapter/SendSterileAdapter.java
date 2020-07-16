package com.phc.cssd.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phc.cssd.url.Url;
import com.phc.core.data.AsonData;
import com.phc.cssd.R;
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

public class SendSterileAdapter extends ArrayAdapter<ModelSendSterile> {

    private final List<ModelSendSterile> DATA_MODEL;
    private final Activity context;

    //------------------------------------------------
    // Background Worker Process Variable
    private boolean Success = false;
    private ArrayList<String> data = null;
    private int size = 0;
    //------------------------------------------------

    public SendSterileAdapter(Activity context, List<ModelSendSterile> DATA_MODEL) {
        super(context, R.layout.activity_list_send_sterile, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_send_sterile, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.txt_id = (TextView) view.findViewById(R.id.txt_id);
            viewHolder.txt_doc_no = (TextView) view.findViewById(R.id.txt_doc_no);
            viewHolder.txt_doc_date = (TextView) view.findViewById(R.id.txt_doc_date);
            viewHolder.txt_department = (TextView) view.findViewById(R.id.txt_department);
            viewHolder.txt_list_count = (TextView) view.findViewById(R.id.txt_list_count);
            viewHolder.txt_total = (TextView) view.findViewById(R.id.txt_total);
            viewHolder.btn_import = (Button) view.findViewById(R.id.btn_import);
            viewHolder.row = (LinearLayout) view.findViewById(R.id.row);

            viewHolder.btn_import.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Display
                    SendSterileAdapter.BackgroundWorker backgroundWorker = new SendSterileAdapter.BackgroundWorker(context);
                    backgroundWorker.execute("update_status", viewHolder.txt_doc_no.getText().toString());

                    try {
                        DATA_MODEL.get(viewHolder.index).setIsStatus(1);
                        viewHolder.row.setVisibility(View.GONE);
                    }catch(Exception e){
                        e.printStackTrace();
                    }



                }
            });

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final SendSterileAdapter.ViewHolder holder = (SendSterileAdapter.ViewHolder) view.getTag();
        holder.txt_id.setText(DATA_MODEL.get(position).getID());
        holder.txt_doc_no.setText(DATA_MODEL.get(position).getDocNo());
        holder.txt_doc_date.setText(DATA_MODEL.get(position).getDocDate());
        holder.txt_department.setText(DATA_MODEL.get(position).getDepName2());
        holder.txt_list_count.setText(DATA_MODEL.get(position).getList_count());
        holder.txt_total.setText(DATA_MODEL.get(position).getTotal());
        holder.index = (DATA_MODEL.get(position).getIndex());
        holder.row.setVisibility(DATA_MODEL.get(position).getIsStatus() == 0 ? View.VISIBLE : View.GONE);
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        int index;
        TextView txt_id;
        TextView txt_doc_no;
        TextView txt_doc_date;
        TextView txt_department;
        TextView txt_list_count;
        TextView txt_total;
        Button btn_import;
        LinearLayout row;
    }

    // =============================================================================================

    public class BackgroundWorker extends AsyncTask<String,Void,String> {
        Context context;

        BackgroundWorker(Context ctx) {
            context = ctx;
        }

        String TYPE;

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];

            String post_data = "";
            String url_ = null;

            TYPE = type;

            System.out.println("TYPE = " + TYPE);

            if (type.equals("update_status")) {
                url_ = Url.URL + "cssd_update_wash_sendsterile_detail.php";
            } else {
                return null;
            }

            try {

                URL url = new URL(url_);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                if (type.equals("update_status")) {
                    String p_docno = params[1];
                    post_data += "p_docno=" + p_docno;
                }

                System.out.println("URL = " + url_ + post_data);

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // ===========================================================================================

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                String result = "";
                String line = "";

                if ((line = bufferedReader.readLine()) != null) {
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

        }

        @Override
        protected void onPostExecute(String result) {
            AsonData ason = new AsonData(result);

            Success = ason.isSuccess();
            size = ason.getSize();
            data = ason.getASONData();

            if (Success && data != null) {

                if (TYPE.equals("update_status")) {
                    System.out.println("OK!!");
                }
            }
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        // =========================================================================================

    }
 }