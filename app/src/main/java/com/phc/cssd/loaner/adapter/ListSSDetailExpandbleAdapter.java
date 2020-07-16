package com.phc.cssd.loaner.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.loaner.LoanerMainActivity;
import com.phc.cssd.model.Item;

import java.util.ArrayList;
import java.util.Map;

public class ListSSDetailExpandbleAdapter extends BaseExpandableListAdapter {

    private String DocNo;
    private LoanerMainActivity context;
    private ArrayList<CheckBox> arrCheckBox = new ArrayList<>();
    private ArrayList<Item> expandableListTitle;
    private Map<String, ArrayList<String>> expandableListDetail;

    public ListSSDetailExpandbleAdapter(LoanerMainActivity context, ArrayList<Item> expandableListTitle, Map<String, ArrayList<String>> expandableListDetail,String DocNo) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        arrCheckBox.clear();
        this.DocNo = DocNo;
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition).getItemCode()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.adapter_list_ipd_opd_item, parent, false);
        final int g=groupPosition;
        LinearLayout f = (LinearLayout) v.findViewById(R.id.frame);
        TextView no = (TextView) v.findViewById(R.id.no);
        TextView tFields1 = (TextView) v.findViewById(R.id.tFields1);
        final TextView tFields2 = (TextView) v.findViewById(R.id.tFields2);
        TextView tFields3 = (TextView) v.findViewById(R.id.tFields3);
        Button bt_delete = (Button) v.findViewById(R.id.bt_delete);
        final String itemcode = expandableListTitle.get(groupPosition).getItemCode();
        no.setText(groupPosition+1 + ".");
        tFields1.setText( expandableListTitle.get(groupPosition).getName());
        tFields2.setText(expandableListDetail.get(itemcode).size()+"");
        tFields3.setText("");
        if (tFields2.getText().toString().equals("1")){
            bt_delete.setVisibility(View.VISIBLE);
            tFields3.setText("[ "+expandableListDetail.get(itemcode).get(0)+" ]");
        }else {
            bt_delete.setVisibility(View.GONE);
            tFields3.setText("");
        }
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder quitDialog = new AlertDialog.Builder(context);
                quitDialog.setTitle("ยืนยัน");
                quitDialog.setIcon(R.drawable.pose_favicon_2x);
                quitDialog.setMessage("ต้องการลบรายการนี้หรือไม่ ?");
                quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (tFields2.getText().toString().equals("1")){
                            context.delete_detail(DocNo,expandableListTitle.get(groupPosition).getItemCode());
                        }else {
                            context.delete_detail(DocNo, expandableListDetail.get(itemcode).get(groupPosition));
                        }
                    }
                });

                quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener(
                ) {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                quitDialog.show();
            }
        });

        tFields1.setTextSize(16);
        tFields2.setTextSize(16);

        if(isExpanded){
            f.setBackground(context.getResources().getDrawable(R.drawable.rectangle_frame01));
        }else{
            f.setBackground(context.getResources().getDrawable(R.drawable.rectangle_frame00));
        }

        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.adapter_list_ipd_opd_item, parent, false);

        final int g=groupPosition;
        final int p=childPosition;

        LinearLayout f = (LinearLayout) v.findViewById(R.id.frame);
        TextView no = (TextView) v.findViewById(R.id.no);
        TextView tFields1 = (TextView) v.findViewById(R.id.tFields1);
        TextView tFields2 = (TextView) v.findViewById(R.id.tFields2);
        Button bt_delete = (Button) v.findViewById(R.id.bt_delete);
        final String itemcode = expandableListTitle.get(groupPosition).getItemCode();
        no.setText(groupPosition+1 + "."+(childPosition+1));
        f.setPadding(20,0,25,0);
        tFields1.setText( expandableListDetail.get(itemcode).get(childPosition));
        tFields2.setText("");

        int x = expandableListDetail.get(itemcode).size()-1;
        if(childPosition==x){
            f.setBackground(context.getResources().getDrawable(R.drawable.rectangle_frame03));
        }else{
            f.setBackground(context.getResources().getDrawable(R.drawable.rectangle_frame02));
        }

        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder quitDialog = new AlertDialog.Builder(context);
                quitDialog.setTitle("ยืนยัน");
                quitDialog.setIcon(R.drawable.pose_favicon_2x);
                quitDialog.setMessage("ต้องการลบรายการนี้หรือไม่ ?");
                quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.delete_detail(DocNo,expandableListDetail.get(itemcode).get(p));
                    }
                });

                quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener(
                ) {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                quitDialog.show();
            }
        });

        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
