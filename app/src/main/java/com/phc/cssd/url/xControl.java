package com.phc.cssd.url;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.SpinnerListAdapter;
import com.phc.cssd.properties.Response_Aux;

public class xControl {
    ArrayList<Response_Aux> results = new ArrayList<Response_Aux>();
    String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    ArrayList<Response_Aux> xData = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> xDataDepartment = new ArrayList<Response_Aux>();

    public  ArrayList<Response_Aux> getListOccuranceType(){
        return xData;
    }

    public void ListOccuranceType(final Spinner spn,final Activity context) {
        class ListOccuranceType extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                List<String> list = new ArrayList<String>();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    xData.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields1(c.getString("xId"));
                        newsData.setFields2(c.getString("xName"));
                        xData.add( newsData );
                        list.add(c.getString("xName"));
                    }

                    ArrayAdapter<String> SpinnerList = new ArrayAdapter<String>(context,
                            android.R.layout.simple_dropdown_item_1line, list);
                    spn.setAdapter( SpinnerList );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("ProcessOccID","2");
                String result = ruc.sendPostRequest(iFt.getoccurancetype(),data);
                return  result;
            }
        }
        ListOccuranceType ru = new ListOccuranceType();
        ru.execute();
    }

    public  ArrayList<Response_Aux> getListDepartment(){
        return xDataDepartment;
    }

    public void ListDepartment(final Spinner spn,final String date,final Activity context) {
        class ListDepartment extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                List<String> list = new ArrayList<String>();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    xDataDepartment.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields1(c.getString("xId"));
                        newsData.setFields2(c.getString("xName"));
                        newsData.setFields16(c.getString("DepName"));
                        xDataDepartment.add( newsData );
                        list.add(c.getString("xNameNew"));
                    }

                    ArrayAdapter<String> SpinnerList = new ArrayAdapter<String>(context,
                            android.R.layout.simple_dropdown_item_1line, list);
                    spn.setAdapter( SpinnerList );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("date",date);
                String result = ruc.sendPostRequest(iFt.getdepartment(),data);
                Log.d("KFJDDJDL",data+"");
                Log.d("KFJDDJDL",result+"");
                return  result;
            }
        }
        ListDepartment ru = new ListDepartment();
        ru.execute();
    }

    public void ListNumber(final Spinner spn,final  int xNum,final Activity context) {
        class ListNumber extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                ArrayList<String> list = new ArrayList<String>();
                for(int i=0;i<xNum;i++){
                    list.add( (i+1)+"" );
                }
                SpinnerListAdapter SpinnerList = new SpinnerListAdapter(context, list);
                spn.setAdapter( SpinnerList );
            }

            @Override
            protected String doInBackground(String... params) {
                return  null;
            }
        }
        ListNumber ru = new ListNumber();
        ru.execute();
    }


    public  ArrayList<Response_Aux> getListResterileType(){
        return xData;
    }

    public void ListResterileType(final Spinner spn,final Activity context) {
        class ListOccuranceType extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                List<String> list = new ArrayList<String>();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    xData.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields1(c.getString("xId"));
                        newsData.setFields2(c.getString("xName"));
                        xData.add( newsData );
                        list.add(c.getString("xName"));
                    }

                    ArrayAdapter<String> SpinnerList = new ArrayAdapter<String>(context,
                            android.R.layout.simple_dropdown_item_1line, list);
                    spn.setAdapter( SpinnerList );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Status","1");
                String result = ruc.sendPostRequest(iFt.getresteriletype(),data);
                return  result;
            }
        }
        ListOccuranceType ru = new ListOccuranceType();
        ru.execute();
    }

}
