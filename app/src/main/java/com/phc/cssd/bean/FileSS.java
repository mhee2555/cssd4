package com.phc.cssd.bean;

import com.phc.cssd.R;
import com.stargreatsoftware.sgtreeview.LayoutItemType;

/**
 * Created by tlh on 2016/10/1 :)
 */

public class FileSS implements LayoutItemType {
    public String fileName;

    public FileSS(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_file_ss;
    }
}
