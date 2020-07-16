package com.phc.core.string;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UpdateUserQR {

    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();
    boolean boo;

        public boolean updateQr(String qr_code,String docno,String xsel) {
            class Checkuser extends AsyncTask<String, Void, String> {
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
                        Intent intent = new Intent();
                        JSONObject jsonObj = new JSONObject(s);
                        rs = jsonObj.getJSONArray(TAG_RESULTS);
                        for(int i=0;i<rs.length();i++) {
                            JSONObject c = rs.getJSONObject(i);
                            if (c.getString("check").equals("true")) {
                                boo = true;

                            } else {
                                boo = false;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                protected String doInBackground(String... params) {
                    HashMap<String, String> data = new HashMap<String,String>();
                    data.put("qr_code",params[0]);
                    data.put("docno",params[1]);
                    data.put("xsel",params[2]);
                    Log.d("xDocNo: ", data+"");
                    String result = httpConnect.sendPostRequest(getUrl.xUrl+"chk_qr/check_qr.php",data);
                    Log.d("result fully: ", result);
                    return  result;
                }
            }
            Checkuser ru = new Checkuser();
            ru.execute( qr_code,docno,xsel );

            return boo;
        }


}
