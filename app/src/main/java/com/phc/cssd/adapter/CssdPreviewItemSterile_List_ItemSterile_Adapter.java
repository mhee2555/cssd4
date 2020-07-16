package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.model.ModelPreviewItemSterile;

import java.util.List;

public class CssdPreviewItemSterile_List_ItemSterile_Adapter extends ArrayAdapter<ModelPreviewItemSterile> {

    private final List<ModelPreviewItemSterile> DATA_MODEL;
    private final Activity context;

    public CssdPreviewItemSterile_List_ItemSterile_Adapter(Activity context, List<ModelPreviewItemSterile> DATA_MODEL) {
        super(context, R.layout.activity_list_preview_item_sterile, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_preview_item_sterile, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.txt_item_code = (TextView) view.findViewById(R.id.txt_item_code);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.txt_set_count = (TextView) view.findViewById(R.id.txt_set_count);
            viewHolder.txt_set_qty = (TextView) view.findViewById(R.id.txt_set_qty);
            viewHolder.txt_pic_1 = (TextView) view.findViewById(R.id.txt_pic_1);
            viewHolder.txt_pic_2 = (TextView) view.findViewById(R.id.txt_pic_2);

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final CssdPreviewItemSterile_List_ItemSterile_Adapter.ViewHolder holder = (CssdPreviewItemSterile_List_ItemSterile_Adapter.ViewHolder) view.getTag();
        holder.txt_item_name.setText(DATA_MODEL.get(position).getItemname());
        holder.txt_item_code.setText(DATA_MODEL.get(position).getItemcode());
        holder.txt_set_count.setText(DATA_MODEL.get(position).getSet_count());
        holder.txt_set_qty.setText(DATA_MODEL.get(position).getSet_qty());
        holder.txt_pic_1.setText(DATA_MODEL.get(position).getPicture_1());
        holder.txt_pic_2.setText(DATA_MODEL.get(position).getPicture_2());
        //holder.index = (DATA_MODEL.get(position).getIndex());
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        TextView txt_item_code;
        TextView txt_item_name;
        TextView txt_set_count;
        TextView txt_set_qty;
        TextView txt_pic_1;
        TextView txt_pic_2;
        //int index;
    }


}