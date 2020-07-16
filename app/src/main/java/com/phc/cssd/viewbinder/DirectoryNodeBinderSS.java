package com.phc.cssd.viewbinder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.bean.DirSS;
import com.stargreatsoftware.sgtreeview.TreeNode;
import com.stargreatsoftware.sgtreeview.TreeViewBinder;

/**
 * Created by tlh on 2016/10/1 :)
 */

public class DirectoryNodeBinderSS extends TreeViewBinder<DirectoryNodeBinderSS.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        holder.ivArrow.setRotation(0);
        holder.ivArrow.setImageResource(R.drawable.ic_keyboard_arrow_right_black_18dp);
        int rotateDegree = node.isExpand() ? 90 : 0;
        holder.ivArrow.setRotation(rotateDegree);
        DirSS dirNode = (DirSS) node.getContent();

        String foo = dirNode.dirName;
        String[] split = foo.split(",");
        holder.tvName.setText(split[0]);

        if (node.isLeaf())
            holder.ivArrow.setVisibility(View.INVISIBLE);
        else
            holder.ivArrow.setVisibility(View.VISIBLE);

        //Log.d("positionDIR: ",position+"");

            if(split.length>1){
                if(split[1].equals("0")){
                    holder.icLogo.setImageResource(R.drawable.ic_list_blue);
                    holder.ic_status.setImageResource(R.drawable.ic_radio_normal);
                }else  if(split[1].equals("1")||split[1].equals("2")){
                    holder.icLogo.setImageResource(R.drawable.ic_list_blue);
                    holder.ic_status.setImageResource(R.drawable.ic_radio_check);
                }else  if(split[1].equals("3")){
                    holder.icLogo.setImageResource(R.drawable.ic_folder_light_blue_700_24dp);
                    holder.ic_status.setImageResource(0);
                }
            }


    }

    @Override
    public int getLayoutId() {
        return R.layout.item_dir_ss;
    }

    public static class ViewHolder extends TreeViewBinder.ViewHolder {
        private ImageView ivArrow;
        private ImageView icLogo;
        private TextView tvName;
        private ImageView ic_status;

        public ViewHolder(View rootView) {
            super(rootView);
            this.ivArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
            this.icLogo = (ImageView) rootView.findViewById(R.id.ic_logo);
            this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
            this.ic_status = (ImageView) rootView.findViewById(R.id.ic_status);
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
}
