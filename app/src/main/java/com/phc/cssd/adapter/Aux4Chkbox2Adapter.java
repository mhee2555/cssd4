package com.phc.cssd.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.properties.Response_Aux;

import java.util.ArrayList;

public class Aux4Chkbox2Adapter extends ArrayAdapter {
    private ArrayList<Response_Aux> listData ;
    private Context context;

    public Aux4Chkbox2Adapter(AppCompatActivity aActivity, ArrayList<Response_Aux> listData) {
        super(aActivity, 0, listData);
        this.context = aActivity;
        this.listData = listData;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.list_aux4chk2, parent, false);

        TextView tFields1 = (TextView) v.findViewById(R.id.tFields1);
        TextView tFields2 = (TextView) v.findViewById(R.id.tFields2);
        TextView tFields3 = (TextView) v.findViewById(R.id.tFields3);
        TextView tFields4 = (TextView) v.findViewById(R.id.tFields4);
        ImageView imageView1 = (ImageView) v.findViewById(R.id.imageView1);
        ImageView imageView2 = (ImageView) v.findViewById(R.id.imageView2);

        tFields1.setText( listData.get(position).getFields1() );
        tFields2.setText( listData.get(position).getFields2() );
        tFields3.setText( listData.get(position).getFields3() );
        tFields4.setText( listData.get(position).getFields4() );

        if( listData.get(position).getFields5().equals("2") ){
            imageView1.setImageResource(R.drawable.check01_32);
        }else{
            imageView1.setImageResource(R.drawable.uncheck01_32);
        }

        if( listData.get(position).getFields6().equals("1") ){
            imageView2.setImageResource(R.drawable.check01_32);
        }else{
            imageView2.setImageResource(R.drawable.uncheck01_32);
        }

//        if(position %2 == 1)
//        {
//            // Set a background color for ListView regular row/item
//            v.setBackgroundColor(Color.parseColor("#FFFFFF"));
//        }
//        else
//        {
//            // Set the background color for alternate row/item
//            v.setBackgroundColor(Color.parseColor("#e6f7ff"));
//        }

        return v;
    }

}
