package com.phc.cssd.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.model.Modelreport;

import java.util.List;

public class AdapterReport extends ArrayAdapter<Modelreport> {

    private final List<Modelreport> DATA_MODEL;
    private final Activity context;

    public AdapterReport(Activity context, List<Modelreport> DATA_MODEL) {
        super(context, R.layout.activity_adapter_report, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_adapter_report, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.txt_report = ( TextView ) view.findViewById(R.id.txt_report);
            viewHolder.ln_1 = ( LinearLayout ) view.findViewById(R.id.ln_1);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        // =========================================================================================
        final ViewHolder holder = (ViewHolder) view.getTag();
        Log.d("BANKFH",DATA_MODEL.get(position).getReport_name());
        holder.txt_report.setText(DATA_MODEL.get(position).getReport_name());
        return view;
        // =========================================================================================
    }
    static class ViewHolder {
        LinearLayout ln_1;
        TextView txt_report;
    }
}
