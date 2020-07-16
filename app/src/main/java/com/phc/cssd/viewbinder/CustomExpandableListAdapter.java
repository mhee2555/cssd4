package com.phc.cssd.viewbinder;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phc.cssd.R;

import java.util.List;
import java.util.Map;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private Map<String, List<String>> expandableListDetail;
    private ImageView Iv1;

    public ImageView getArrow(){
        return this.Iv1;
    }

    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       Map<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {

        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_payout, null);
        }
        ImageView ivPrint = (ImageView) convertView.findViewById(R.id.imageView1);
        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.expandedListItem);
        TextView ischeck = (TextView) convertView.findViewById(R.id.ischeck);
        String ardate ="";
        ardate = expandedListText.split(",")[6];
        //ardate = expandedListText.split(",")[6].split("-")[2]+"-"+expandedListText.split(",")[6].split("-")[1]+"-"+expandedListText.split(",")[6].split("-")[0];
//        ardate = expandedListText.split(",")[6].split("-")[1];
//        ardate = expandedListText.split(",")[6].split("-")[2];
        if(Integer.parseInt( expandedListText.split(",")[5] ) == 1){
            expandedListTextView.setText(expandedListText.split(",")[0]+" (S) ("+ardate+")");
        }else{
            expandedListTextView.setText(expandedListText.split(",")[0]+"     ("+ardate+")");
        }

        if( Integer.parseInt( expandedListText.split(",")[2] ) == 0 ){
            //ivPrint.setImageResource(R.drawable.small_print_grey);
            ivPrint.setVisibility(View.INVISIBLE);
        }else{
            ivPrint.setVisibility(View.VISIBLE);
            ivPrint.setImageResource(R.drawable.small_print_blue);
        }

        if( Integer.parseInt( expandedListText.split(",")[3] ) == 0 ){
            ischeck.setVisibility(View.INVISIBLE);
        }else{
            ischeck.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                              View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        final View convertView1 = convertView;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group_payout, null);
        }
        LinearLayout LL = (LinearLayout) convertView.findViewById(R.id.LL1);

        ImageView ivArrow = (ImageView) convertView.findViewById(R.id.imageView1);
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle.substring(1,listTitle.length()));

        if(!isExpanded){
            //ivArrow.setImageResource(R.drawable.small_right);
            ivArrow.setRotation( 0 );
        }else{
            //ivArrow.setImageResource(R.drawable.small_down);
            ivArrow.setRotation( 90 );
        }

        switch(Integer.parseInt(listTitle.substring(0,1))){
            case 1:LL.setBackgroundResource(R.drawable.item_pressed_blue);break;
            case 2:LL.setBackgroundResource(R.drawable.item_pressed_red);break;
            case 3:LL.setBackgroundResource(R.drawable.item_pressed_green);break;
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
