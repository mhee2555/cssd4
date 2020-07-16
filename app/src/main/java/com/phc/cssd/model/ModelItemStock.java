package com.phc.cssd.model;

public class ModelItemStock {
    String ID;
    String DocNo;
    String itemname;
    String itemcode;
    String UsageCode;
    String RowID;

    int i = 0;

    public ModelItemStock(String ID, String docNo, String itemname, String itemcode, String usageCode, String rowID,int i) {
        this.ID = ID;
        DocNo = docNo;
        this.itemname = itemname;
        this.itemcode = itemcode;
        UsageCode = usageCode;
        RowID = rowID;
        this.i=i;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDocNo() {
        return DocNo;
    }

    public void setDocNo(String docNo) {
        DocNo = docNo;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getUsageCode() {
        return UsageCode;
    }

    public void setUsageCode(String usageCode) {
        UsageCode = usageCode;
    }

    public String getRowID() {
        return RowID;
    }

    public void setRowID(String rowID) {
        RowID = rowID;
    }
}
