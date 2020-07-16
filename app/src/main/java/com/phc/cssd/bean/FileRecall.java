package com.phc.cssd.bean;

import com.phc.cssd.R;
import com.stargreatsoftware.sgtreeview.LayoutItemType;

/**
 * Created by tlh on 2016/10/1 :)
 */

public class FileRecall implements LayoutItemType {
    public String fileName;

    public FileRecall(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_file_recall;
    }
}
