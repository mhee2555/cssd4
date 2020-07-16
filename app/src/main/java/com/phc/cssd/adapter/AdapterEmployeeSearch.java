package com.phc.cssd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.master_data.employee_search;
import com.phc.cssd.properties.Response_Employee_Search;

import java.util.ArrayList;

public class AdapterEmployeeSearch extends ArrayAdapter{
    private ArrayList<Response_Employee_Search> listData ;
    private Context context;
    private int temppos;

    public AdapterEmployeeSearch(employee_search aActivity, ArrayList<Response_Employee_Search> listData) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.list_search_employee, parent, false);

        temppos = position;

        TextView tFields1 = (TextView) v.findViewById(R.id.EmpCode);
        TextView tFields2 = (TextView) v.findViewById(R.id.Name);
        TextView tFields3 = (TextView) v.findViewById(R.id.Dept);
        Button importbtn = (Button) v.findViewById(R.id.button_import);

        tFields1.setText( listData.get(position).getEmpCode() );
        tFields2.setText( listData.get(position).getName() );
        tFields3.setText( listData.get(position).getDept() );

        importbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = listData.get(position).getEmpCode()+"/"+listData.get(position).getName()+"/"+listData.get(position).getDept();
                ((employee_search)context).GetDataEmp(temp);
            }
        });

        return v;
    }


}
