package com.phc.cssd.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.cssd.ItemSet;
import com.phc.cssd.R;
import com.phc.cssd.model.ModelItemDetail;
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
import java.util.List;

public class ItemDetailEnterQtyAdapter extends ArrayAdapter<ModelItemDetail> {

    private final List<ModelItemDetail> DATA_MODEL;
    private final Activity context;

    public ItemDetailEnterQtyAdapter(Activity context, List<ModelItemDetail> DATA_MODEL) {
        super(context, R.layout.activity_list_item_set_edit_data, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_item_set_edit_data, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.txt_code = (TextView) view.findViewById(R.id.txt_code);
            viewHolder.txt_name = (TextView) view.findViewById(R.id.txt_name);
            viewHolder.txt_qty = (EditText) view.findViewById(R.id.txt_qty);
            viewHolder.txt_unit = (TextView) view.findViewById(R.id.txt_unit);
            viewHolder.imv_remove = (ImageView) view.findViewById(R.id.imv_remove);

            viewHolder.imv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ItemSet)context).onDeleteSet( viewHolder.txt_code.getText().toString()  );
                }
            });


            viewHolder.txt_qty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(final View v, boolean hasFocus) {
                    Handler handler = new Handler();
                    Runnable delayedAction = null;

                    if (!hasFocus) {

                            if (delayedAction != null) {
                                handler.removeCallbacks(delayedAction);
                            }

                            delayedAction = new Runnable() {
                                @Override
                                public void run() {
                                    TextView text_qty = (TextView) v;

                                    try {

                                        onUpdateQty(viewHolder.txt_code.getText().toString(), text_qty.getText().toString());

                                        DATA_MODEL.get(viewHolder.index).setQty(text_qty.getText().toString());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            //delay this new search by one second
                            handler.postDelayed(delayedAction, 100);

                        }
                    }

            });

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ItemDetailEnterQtyAdapter.ViewHolder holder = (ItemDetailEnterQtyAdapter.ViewHolder) view.getTag();
        holder.txt_name.setText(DATA_MODEL.get(position).getItemname());
        holder.txt_code.setText(DATA_MODEL.get(position).getID_set());
        holder.txt_qty.setText(DATA_MODEL.get(position).getQty());
        holder.txt_unit.setText(DATA_MODEL.get(position).getUnitName());
        holder.index = (DATA_MODEL.get(position).getIndex());
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        int index;
        TextView txt_code;
        TextView txt_name;
        EditText txt_qty;
        TextView txt_unit;

        ImageView imv_remove;
    }

    private boolean onUpdateQty(String ID, String Qty){
        BackgroundWorker backgroundWorker = new BackgroundWorker(context);
        backgroundWorker.execute(ID, Qty);
        return true;
    }

    // =============================================================================================

    public class BackgroundWorker extends AsyncTask<String,Void,String> {
        Context context;
        BackgroundWorker (Context ctx) {
            context = ctx;
        }
        @Override
        protected String doInBackground(String... params) {

            String login_url = Url.URL + "cssd_update_item_detail.php";

            try {
                    String ID = params[0];
                    String Qty = params[1];

                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = "p_qty=" + Qty + "&p_id=" + ID;

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    // ===========================================================================================

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                    String result="";
                    String line="";

                    while((line = bufferedReader.readLine())!= null) {
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

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


    }
}
