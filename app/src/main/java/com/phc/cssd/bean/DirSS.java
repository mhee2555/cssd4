package com.phc.cssd.bean;

import com.phc.cssd.R;
import com.stargreatsoftware.sgtreeview.LayoutItemType;

/**
 * Created by tlh on 2016/10/1 :)
 */

public class DirSS implements LayoutItemType {
    public String dirName;

    public DirSS(String dirName) {
        this.dirName = dirName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_dir_ss;
    }
}
