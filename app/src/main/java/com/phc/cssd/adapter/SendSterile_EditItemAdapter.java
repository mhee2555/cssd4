package com.phc.cssd.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.properties.pCustomer;

import java.util.ArrayList;


/**
 * Created by User on 20/7/2560.
 */


public class SendSterile_EditItemAdapter extends ArrayAdapter {

    private ArrayList<pCustomer> listData;
    private AppCompatActivity aActivity;
    public ArrayList<String> selectedStrings = new ArrayList<String>();

    public SendSterile_EditItemAdapter(AppCompatActivity aActivity, ArrayList<pCustomer> listData, ArrayList<String> selectedStrings) {
        super(aActivity, 0, listData);
        this.aActivity= aActivity;
        this.listData = listData;
        this.selectedStrings = selectedStrings;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) aActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.list_edititem_sendsterile, parent, false);
        final pCustomer pCus = listData.get(position);
        final int xps = position;

        TextView txtUcode = (TextView)v.findViewById(R.id.e_itemcode);
        TextView txtitemname= (TextView) v.findViewById(R.id.e_itemname);
        TextView txtxqty_detail = (TextView) v.findViewById(R.id.e_qty);
        CheckBox checkbox =(CheckBox) v.findViewById(R.id.ckItemStock);
        txtUcode.setText(listData.get(position).getUsageCode());
        txtitemname.setText(listData.get(position).getItemname());
        txtxqty_detail.setText(listData.get(position).getXqty());


        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();
                if (isChecked) {
                    listData.get(getPosition).setIscheck(buttonView.isChecked());
                    selectedStrings.add(listData.get(position).getUsageCode());
                }else{

                    listData.get(getPosition).setIscheck(buttonView.isChecked());
                    selectedStrings.remove(listData.get(position).getUsageCode());
                }
            }
        });


        checkbox.setTag(position);
        checkbox.setChecked(listData.get(position).isIscheck());


        return v;
    }


  public ArrayList<String> getSelectedString(){
        return selectedStrings;
    }
    public  void clearobject(){
       selectedStrings.clear();
    }

}