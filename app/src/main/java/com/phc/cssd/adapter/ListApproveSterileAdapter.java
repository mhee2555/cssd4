package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.model.ModelApprove;

import java.util.List;

public class ListApproveSterileAdapter extends ArrayAdapter<ModelApprove> {


    private final List<ModelApprove> DATA_MODEL;
    private final Activity context;

    public ListApproveSterileAdapter(Activity context, List<ModelApprove> DATA_MODEL) {
        super(context, R.layout.list_approve_adapter, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.list_approve_adapter, null);
            final ListApproveSterileAdapter.ViewHolder viewHolder = new ListApproveSterileAdapter.ViewHolder();
            viewHolder.index = ( TextView ) view.findViewById(R.id.index);
            viewHolder.usagecount = (TextView) view.findViewById(R.id.usagecount);
            viewHolder.DepID = (TextView) view.findViewById(R.id.DepID);
            viewHolder.itemname_approve = (TextView) view.findViewById(R.id.itemname_approve);
            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ListApproveSterileAdapter.ViewHolder holder = ( ListApproveSterileAdapter.ViewHolder ) view.getTag();

        holder.index.setText(DATA_MODEL.get(position).getIndex()+"");
        holder.itemname_approve.setText(DATA_MODEL.get(position).getItemName());
        holder.usagecount.setText(DATA_MODEL.get(position).getUsageCode());
        holder.DepID.setText("[ แผนก : "+DATA_MODEL.get(position).getDepID()+" ]");

        // =========================================================================================
        return view;
    }

    static class ViewHolder {
        TextView index;
        TextView usagecount;
        TextView DepID;
        TextView itemname_approve;
    }

}
