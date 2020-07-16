package com.phc.cssd.viewbinder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.bean.Dir;
import com.stargreatsoftware.sgtreeview.TreeNode;
import com.stargreatsoftware.sgtreeview.TreeViewBinder;

/**
 * Created by tlh on 2016/10/1 :)
 */

public class DirectoryNode_Payout extends TreeViewBinder<DirectoryNode_Payout.ViewHolder> {
    @Override
    public DirectoryNode_Payout.ViewHolder provideViewHolder(View itemView) {
        return new DirectoryNode_Payout.ViewHolder(itemView);
    }

    @Override
    public void bindView(DirectoryNode_Payout.ViewHolder holder, int position, TreeNode node) {
        //holder.LL1.setBackgroundResource(R.color.colorDirGreen);
        holder.ivArrow.setRotation(0);
        holder.ivArrow.setImageResource(R.drawable.small_right);
        int rotateDegree = node.isExpand() ? 90 : 0;
        holder.ivArrow.setRotation(rotateDegree);
        Dir dirNode = (Dir) node.getContent();
        holder.tvName.setText(dirNode.dirName);
        if (node.isLeaf())
            holder.ivArrow.setVisibility(View.INVISIBLE);
        else
            holder.ivArrow.setVisibility(View.VISIBLE);

        if(position > 0){
            //holder.LL1.setBackgroundResource(R.drawable.item_pressed_blue);
            holder.icLogo.setImageResource(R.drawable.ic_list_blue);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_dir_payout;
    }

    public static class ViewHolder extends TreeViewBinder.ViewHolder {
        private LinearLayout LL1;
        private ImageView ivArrow;
        private ImageView icLogo;
        private TextView tvName;

        public ViewHolder(View rootView) {
            super(rootView);
            this.LL1 = (LinearLayout) rootView.findViewById(R.id.LL1);
            this.ivArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
            this.icLogo = (ImageView) rootView.findViewById(R.id.ic_logo);
            this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
        }

        public LinearLayout getLL1() {
            return LL1;
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
