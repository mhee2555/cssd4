package com.phc.cssd.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.data.Master;
import com.phc.cssd.R;
import com.phc.cssd.model.ModelMasterData;

import java.util.List;

public class DropDownAdapter extends ArrayAdapter<ModelMasterData> {
    private final List<ModelMasterData> list;
    private final Activity context;
    String data_;

    public DropDownAdapter(Activity context, List<ModelMasterData> list, String data_) {
        super(context, R.layout.activity_list_master_data, list);
        this.context = context;
        this.list = list;
        this.data_ = data_;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_master_data, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.code = (TextView) view.findViewById(R.id.code);
            viewHolder.name = (TextView) view.findViewById(R.id.name);

            viewHolder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("RETURN_DATA", viewHolder.name.getText().toString());
                    intent.putExtra("RETURN_VALUE", viewHolder.code.getText().toString());
                    context.setResult(Master.getResult(data_), intent);
                    context.finish();
                }
            });

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final DropDownAdapter.ViewHolder holder = (DropDownAdapter.ViewHolder) view.getTag();
        holder.name.setText(list.get(position).getName());
        holder.code.setText(list.get(position).getCode());
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        TextView code;
        TextView name;

    }
}