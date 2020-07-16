package com.phc.cssd.viewbinder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.bean.File;
import com.stargreatsoftware.sgtreeview.TreeNode;
import com.stargreatsoftware.sgtreeview.TreeViewBinder;


/**
 * Created by tlh on 2016/10/1 :)
 */

public class ApFileNodeBinder extends TreeViewBinder<ApFileNodeBinder.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        File fileNode = (File) node.getContent();
        String Txt = fileNode.fileName;
        try {
            if( Integer.parseInt( Txt.substring(1,2) ) == 0 )
                holder.tvName.setText( "ยังไม่ได้นำเข้าแผนก" );
            else
                holder.tvName.setText( "นำเข้าแผนกเรียบร้อย" );
        }catch ( Exception  e ){
            holder.tvName.setText( Txt.substring(1,Txt.length() ) );
        }


            switch( Integer.parseInt( Txt.substring(0,1) ) ){
                case 1:holder.icLogo.setImageResource(R.drawable.ic_note_blue);break;
                case 2:
                    try {
                        if (Integer.parseInt(Txt.substring(1, 2)) == 0)
                            holder.icLogo.setImageResource(R.drawable.ic_radiobox_fill);
                        else
                            holder.icLogo.setImageResource(R.drawable.ic_radiobox_unfill);
                    }catch ( Exception  e ){
                        holder.icLogo.setImageResource(R.drawable.ic_radiobox_fill);
                    }
                break;
            }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_file;
    }

    public class ViewHolder extends TreeViewBinder.ViewHolder {
        public TextView tvName;
        private ImageView icLogo;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
            this.icLogo = (ImageView) rootView.findViewById(R.id.ic_logo);
        }
    }
}
