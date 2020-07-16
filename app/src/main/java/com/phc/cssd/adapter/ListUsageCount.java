package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.model.ModelUsageCount;

import java.util.List;

public class ListUsageCount extends ArrayAdapter<ModelUsageCount> {
    
    private final List<ModelUsageCount> DATA_MODEL;
    private final Activity context;
    private String loop;

    public ListUsageCount(Activity context, List<ModelUsageCount> DATA_MODEL, int loop) {
        super(context, R.layout.list_usage_count, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.loop = String.valueOf(loop);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.list_usage_count, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.index = ( TextView ) view.findViewById(R.id.index);
            viewHolder.usagecount = (TextView) view.findViewById(R.id.usagecount);
            viewHolder.item_name_count = (TextView) view.findViewById(R.id.item_name_count);
            viewHolder.ems_day = (TextView) view.findViewById(R.id.ems_day);
            viewHolder.remark_ems = (TextView) view.findViewById(R.id.remark_ems);
            viewHolder.usagecount_qty = (TextView) view.findViewById(R.id.usagecount_qty);
            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ViewHolder holder = (ViewHolder) view.getTag();

        holder.index.setText(DATA_MODEL.get(position).getIndex()+""+".");
        holder.usagecount.setText(DATA_MODEL.get(position).getUsageCode());
        //holder.item_name_count.setText(DATA_MODEL.get(position).getItemName());
        //holder.usagecount_qty.setText("[ "+DATA_MODEL.get(position).getUsageCount()+" รอบ"+" ]");

        // =========================================================================================
        return view;
    }

    static class ViewHolder {
        TextView index;
        TextView usagecount;
        TextView item_name_count;
        TextView ems_day;
        TextView remark_ems;
        TextView usagecount_qty;
    }

}