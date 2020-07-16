package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.model.ModelMasterData;

import java.util.List;

public class MasterData3Adapter extends ArrayAdapter<ModelMasterData> {
    private final List<ModelMasterData> list;
    private final Activity context;

    public MasterData3Adapter(Activity context, List<ModelMasterData> list) {
        super(context, R.layout.activity_list_master_3_data, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_master_3_data, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.code = (TextView) view.findViewById(R.id.code);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.value_1 = (TextView) view.findViewById(R.id.value_1);

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final MasterData3Adapter.ViewHolder holder = (MasterData3Adapter.ViewHolder) view.getTag();
        holder.name.setText(list.get(position).getName());
        holder.code.setText(list.get(position).getCode());
        holder.value_1.setText(list.get(position).getValue1());
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        TextView code;
        TextView name;
        TextView value_1;

    }
}