package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.model.ModelUsageEms;

import java.util.List;

public class ListUsageEms extends ArrayAdapter<ModelUsageEms> {

    private final List<ModelUsageEms> DATA_MODEL;
    private final Activity context;

    public ListUsageEms(Activity context, List<ModelUsageEms> DATA_MODEL) {
        super(context, R.layout.list_usage_ems, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.list_usage_ems, null);
            final ListUsageEms.ViewHolder viewHolder = new ListUsageEms.ViewHolder();
            viewHolder.index = ( TextView ) view.findViewById(R.id.index);
            viewHolder.usagecount = (TextView) view.findViewById(R.id.usagecount);
            viewHolder.item_name_count = (TextView) view.findViewById(R.id.item_name_count);
            viewHolder.ems_day = (TextView) view.findViewById(R.id.ems_day);
            viewHolder.remark_ems = (TextView) view.findViewById(R.id.remark_ems);
            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ListUsageEms.ViewHolder holder = ( ListUsageEms.ViewHolder ) view.getTag();

        holder.index.setText(DATA_MODEL.get(position).getIndex()+""+".");
        holder.usagecount.setText(DATA_MODEL.get(position).getUsageCode());
        holder.item_name_count.setText(DATA_MODEL.get(position).getItemName());
        holder.ems_day.setText("( "+DATA_MODEL.get(position).getDay()+" วัน )");
        holder.remark_ems.setText("หมายเหตุ : "+DATA_MODEL.get(position).getRemark());

        // =========================================================================================
        return view;
    }

    static class ViewHolder {
        TextView index;
        TextView usagecount;
        TextView item_name_count;
        TextView ems_day;
        TextView remark_ems;
    }

}