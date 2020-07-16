package com.phc.cssd.viewbinder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.R;
import com.phc.cssd.Recall;
import com.phc.cssd.bean.DirRecall;
import com.phc.cssd.dialog_search_recall;
import com.phc.cssd.url.getUrl;
import com.stargreatsoftware.sgtreeview.TreeNode;
import com.stargreatsoftware.sgtreeview.TreeViewBinder;

import org.json.JSONArray;

/**
 * Created by tlh on 2016/10/1 :)
 */

public class DirectoryNodeBinder_Recall_dialog extends TreeViewBinder<DirectoryNodeBinder_Recall_dialog.ViewHolder> {

    private String[] split;
    String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    Context context;
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }


    public DirectoryNodeBinder_Recall_dialog(dialog_search_recall activity){
        this.context=activity;
    }

    @Override
    public void bindView(final ViewHolder holder, final int position, TreeNode node) {
        holder.ivArrow.setRotation(0);
        holder.ivArrow.setImageResource(R.drawable.ic_keyboard_arrow_right_black_18dp);
        int rotateDegree = node.isExpand() ? 90 : 0;
        holder.ivArrow.setRotation(rotateDegree);
        DirRecall dirNode = (DirRecall) node.getContent();

        String foo = dirNode.dirName;
        Log.d("aaaa2", foo);
        split = foo.split("@@");
        holder.tvName.setText(split[0]);
        if (node.isLeaf())
            holder.ivArrow.setVisibility(View.INVISIBLE);
        else
            holder.ivArrow.setVisibility(View.VISIBLE);

        //Log.d("positionDIR: ",position+"");

        if(split.length>1){
            if(split[1].equals("0")){
                holder.ic_remark.setVisibility(View.INVISIBLE);
                holder.ic_delete.setVisibility(View.INVISIBLE);
                holder.bar.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }else {
                if(split.length>2) {
                    if(split[2].equals("0")) {
                        holder.ic_remark.setVisibility(View.VISIBLE);
                        holder.ic_delete.setVisibility(View.VISIBLE);
                        holder.txtstatus.setText("1");
                    }else{
//                        holder.ic_remark.setVisibility(View.GONE);
                        holder.txtstatus.setText("1");
                        holder.ic_delete.setVisibility(View.GONE);
                    }
                    holder.bar.setBackground(ContextCompat.getDrawable(context, R.drawable.bar_blue_1));
                }
                if(split.length>3){
                    holder.txtremark.setText(split[3]);
                    if(!split[3].equals("%")) {
                        holder.ic_remark.setBackgroundColor(0);
                        holder.ic_remark.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_list_blue));
                    }
                }
                if(split.length>4){
                    holder.txtdocno.setText(split[4]);
                }
            }
        }

        holder.ic_remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.remark_dialog);
                dialog.setCancelable(true);
                dialog.setTitle("หมายเหตุ...");

                final EditText note1 = (EditText) dialog.findViewById(R.id.note1);
                if(!holder.txtremark.getText().toString().equals("%")) {
                    note1.setText(holder.txtremark.getText().toString());
                }else{
                    note1.setText("");
                }
                Button button1 = (Button) dialog.findViewById(R.id.button1);
                if(!holder.txtstatus.getText().toString().equals("0")){
                    note1.setEnabled(false);
                    button1.setVisibility(View.GONE);
                }
                button1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        ((Recall)context).remark_recall(holder.txtdocno.getText().toString(),note1.getText().toString());
//                        remark_recall(holder.txtdocno.getText().toString(),holder.txtremark.getText().toString());
                        dialog.dismiss();
                    }
                });

                Button button5 = (Button) dialog.findViewById(R.id.button5);
                button5.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        holder.ic_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder((Recall)v.getContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
                } else {
                    builder = new AlertDialog.Builder((Recall)v.getContext());
                }
                builder.setTitle("ยืนยันการยกเลิกคำขอเรียกคืนอุปกรณ์")
                        .setMessage("คุณต้องการยกเลิกคำขอเรียกคืนอุปกรณ์หรือไม่?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ((Recall)context).deleterecall(holder.txtdocno.getText().toString());
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });


    }

    @Override
    public int getLayoutId() {
        return R.layout.item_dir_recall;
    }

    public static class ViewHolder extends TreeViewBinder.ViewHolder {
        private ImageView ivArrow;
        private ImageView icLogo;
        private TextView tvName;
        private ImageView ic_status;
        private ImageView ic_remark;
        private ImageView ic_delete;
        private TextView txtdocno;
        private TextView txtremark;
        private TextView txtstatus;
        private LinearLayout bar;

        public ViewHolder(View rootView) {
            super(rootView);
            this.ivArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
            this.icLogo = (ImageView) rootView.findViewById(R.id.ic_logo);
            this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
            this.ic_status = (ImageView) rootView.findViewById(R.id.ic_status);
            this.ic_remark = (ImageView) rootView.findViewById(R.id.ic_remark);
            this.ic_delete = (ImageView) rootView.findViewById(R.id.ic_delete);
            this.txtdocno = (TextView) rootView.findViewById(R.id.txtdocno);
            this.txtremark = (TextView) rootView.findViewById(R.id.txtremark);
            this.txtstatus = (TextView) rootView.findViewById(R.id.txtstatus);
            this.bar = (LinearLayout) rootView.findViewById(R.id.Bar);
        }

        public ImageView getIvArrow() {
            return ivArrow;
        }

        public ImageView getIcLogo() {
            return icLogo;
        }

        public TextView getTvName() {
            return tvName;
        }
    }

//    public void remark_recall(String DocNo,String Remark) {
//        class remark_recall extends AsyncTask<String, Void, String> {
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                try {
//                    JSONObject jsonObj = new JSONObject(s);
//                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
//                    for(int i=0;i<setRs.length();i++){
//                        JSONObject c = setRs.getJSONObject(i);
//                        if(c.getString("bool").equals("true")) {
//                            Toast.makeText((Recall)s., "บันทึกหมายเหตุสำเร็จ", Toast.LENGTH_SHORT).show();
//                            get_datatree_recall();
//                        }else{
//                            Toast.makeText(Recall.this, "บันทึกหมายเหตุล้มเหลว", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//                HashMap<String, String> data = new HashMap<String,String>();
//                data.put("DocNo",params[0]);
//                data.put("Remark",params[1]);
//                String result = ruc.sendPostRequest(iFt.remark_recall(),data);
//                Log.d("test", data+"");
//                Log.d("test", result+"");
//                return  result;
//            }
//        }
//
//        remark_recall ru = new remark_recall();
//        ru.execute(DocNo,Remark);
//    }
}
