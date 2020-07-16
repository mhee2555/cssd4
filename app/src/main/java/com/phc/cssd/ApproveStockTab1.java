package com.phc.cssd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;

import static com.phc.cssd.function.KeyboardUtils.hideKeyboard;

public class ApproveStockTab1 extends Fragment {
    AppCompatActivity context;
    ArrayList<Response_Aux> results = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> resultssterileprocess = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> resultssterilemachine = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> resultssterileround = new ArrayList<Response_Aux>();

    final String TAG_RESULTS="result";
    JSONArray setRs = null;
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();

    int year;
    int month;
    int day;
    final int DATE_PICKER_ID = 1111;

    Spinner Spinner01;
    Spinner Spinner02;
    Spinner Spinner03;

    TextView gDate;
    EditText eSearch;
    Button bDate;
    Button bSearch;
    Button bImport;
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_approve_stock_1, container, false);
        Spinner01 = (Spinner) v.findViewById(R.id.spinner01);
        Spinner02 = (Spinner) v.findViewById(R.id.spinner02);
        Spinner03 = (Spinner) v.findViewById(R.id.spinner03);

        gDate = (TextView) v.findViewById(R.id.gDate);
        eSearch = (EditText) v.findViewById(R.id.item_search);
        bDate = (Button) v.findViewById(R.id.bDate);
        bSearch = (Button) v.findViewById(R.id.button_search);
        bImport = (Button) v.findViewById(R.id.b_Import);


        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        bDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // On button click show datepicker dialog
                //showDialog(DATE_PICKER_ID);
            }
        });

        bSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Hide Keyboard
                hideKeyboard(context);
                eSearch.setFocusable(false);
            }
        });
        return v;
    }

}
