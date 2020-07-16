package com.phc.cssd.viewbinder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.bean.Dir;
import com.stargreatsoftware.sgtreeview.TreeNode;
import com.stargreatsoftware.sgtreeview.TreeViewBinder;

/**
 * Created by tlh on 2016/10/1 :)
 */

public class DirectoryNodeBinder_1 extends TreeViewBinder<DirectoryNodeBinder_1.ViewHolder> {
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
        Dir dirNode = (Dir) node.getContent();
        holder.tvName.setText(dirNode.dirName);
        if (node.isLeaf())
            holder.ivArrow.setVisibility(View.INVISIBLE);
        else
            holder.ivArrow.setVisibility(View.VISIBLE);

        if(position > 0) holder.icLogo.setImageResource(R.drawable.ic_list_blue);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_dir;
    }

    public static class ViewHolder extends TreeViewBinder.ViewHolder {
        private ImageView ivArrow;
        private ImageView icLogo;
        private TextView tvName;

        public ViewHolder(View rootView) {
            super(rootView);
            this.ivArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
            this.icLogo = (ImageView) rootView.findViewById(R.id.ic_logo);
            this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
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
