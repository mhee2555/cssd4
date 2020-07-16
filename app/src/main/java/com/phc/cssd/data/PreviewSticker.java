package com.phc.cssd.data;

import android.os.AsyncTask;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PreviewSticker {

    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();

    String TAG_RESULTS="result";
    JSONArray setRs = null;

     boolean PMachine=false;
     boolean PPrice=false;
     boolean PRound=false;
     boolean PDept=false;
     boolean PEmp1=false;
     boolean PEmp2=false;
     boolean PEmp3=false;
     boolean PBarCode = false;
     String PSpeed = "6";
     String PDensity = "13";
     String POption = "2";

    public boolean isPBarCode() {
        return PBarCode;
    }

    public void setPBarCode(boolean PBarCode) {
        this.PBarCode = PBarCode;
    }

    public boolean isPMachine() {
        return PMachine;
    }

    public void setPMachine(boolean PMachine) {
        this.PMachine = PMachine;
    }

    public boolean isPPrice() {
        return PPrice;
    }

    public void setPPrice(boolean PPrice) {
        this.PPrice = PPrice;
    }

    public boolean isPRound() {
        return PRound;
    }

    public void setPRound(boolean PRound) {
        this.PRound = PRound;
    }

    public boolean isPDept() {
        return PDept;
    }

    public void setPDept(boolean PDept) {
        this.PDept = PDept;
    }

    public boolean isPEmp1() {
        return PEmp1;
    }

    public void setPEmp1(boolean PEmp1) {
        this.PEmp1 = PEmp1;
    }

    public boolean isPEmp2() {
        return PEmp2;
    }

    public void setPEmp2(boolean PEmp2) {
        this.PEmp2 = PEmp2;
    }

    public boolean isPEmp3() {
        return PEmp3;
    }

    public void setPEmp3(boolean PEmp3) {
        this.PEmp3 = PEmp3;
    }

    public int getPSpeed() {
        try {
            return Integer.valueOf(PSpeed).intValue();
        }catch(Exception e){
            return 6;
        }
    }

    public int getPDensity() {
        try {
            return Integer.valueOf(PDensity).intValue();
        }catch(Exception e){
            return 13;
        }
    }

    public void setPDensity(String PDensity) {
        this.PDensity = PDensity;
    }

    public void setPSpeed(String PSpeed) {
        this.PSpeed = PSpeed;
    }

    public String getPOption() {
        return POption;
    }

    public void setPOption(String POption) {
        this.POption = POption;
    }

    public PreviewSticker(String pass) {


        class getPreviewSticker extends AsyncTask<String, Void, String> {



            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        if (c.getString("bool").equals("true")) {
                            PMachine = c.getString("PMachine").equals("1");
                            PPrice = c.getString("PPrice").equals("1");
                            PRound = c.getString("PRound").equals("1");
                            PDept = c.getString("PDept").equals("1");
                            PEmp1 = c.getString("PEmp1").equals("1");
                            PEmp2 = c.getString("PEmp2").equals("1");
                            PEmp3 = c.getString("PEmp3").equals("1");
                            PSpeed = c.getString("PSpeed");
                            PDensity = c.getString("PDensity");
                            POption = c.getString("POption");
                            //PBarCode =  c.getString("PBarCode").equals("1");

                            /*
                            StringBuilder sb = new StringBuilder();
                            sb.append(" M    : " + 1 + "\n");
                            sb.append(" PPrice    : " + PPrice + "\n");
                            sb.append(" PDept    : " + PDept + "\n");
                            sb.append(" PMachine    : " + PMachine + "\n");
                            sb.append(" PRound    : " + PRound + "\n");
                            sb.append(" PEmp1    : " + PEmp1 + "\n");
                            sb.append(" PEmp2    : " + PEmp2 + "\n");
                            sb.append(" PEmp3    : " + PEmp3 + "\n");
                            sb.append(" PPrice    : " + PPrice + "\n");
                            sb.append(" PSpeed    : " + PSpeed + "\n");
                            sb.append(" POption    : " + POption + "\n");

                            System.out.println(sb);
                            */
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("pass", params[0]);
                String result = ruc.sendPostRequest(Url.URL + "PreviewSticker/getpreviewsticker.php" ,data);

                //System.out.println(Url.URL + "PreviewSticker/getpreviewsticker.php");
                return  result;
            }
        }

        getPreviewSticker ru = new getPreviewSticker();
        ru.execute(pass);
    }

}
