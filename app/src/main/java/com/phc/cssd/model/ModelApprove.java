package com.phc.cssd.model;

public class ModelApprove {

    String UsageCode;
    String DepID;
    String ItemName;
    int index;

    public ModelApprove(String UsageCode, String DepID, String ItemName, int index) {
        this.UsageCode = UsageCode;
        this.DepID = DepID;
        this.ItemName = ItemName;
        this.index = index;
    }

    public String getItemName() { return ItemName; }

    public void setItemName(String itemName) { ItemName = itemName; }

    public String getUsageCode() {
        return UsageCode;
    }

    public void setUsageCode(String usageCode) { UsageCode = usageCode; }

    public String getDepID() {
        return DepID;
    }

    public void setDepID(String depID) {
        DepID = depID;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
