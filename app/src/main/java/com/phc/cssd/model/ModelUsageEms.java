package com.phc.cssd.model;

public class ModelUsageEms {

    String UsageCode;
    String ItemName;
    String day;
    String remark;
    int index;

    public ModelUsageEms(String UsageCode, String ItemName, String day, String remark, int index) {
        this.UsageCode = UsageCode;
        this.ItemName = ItemName;
        this.day = day;
        this.remark = remark;
        this.index = index;
    }

    public String getDay() { return day; }

    public void setDay(String day) { this.day = day; }

    public String getRemark() { return remark; }

    public void setRemark(String remark) { this.remark = remark; }

    public String getItemName() { return ItemName; }

    public void setItemName(String itemName) { ItemName = itemName; }

    public String getUsageCode() {
        return UsageCode;
    }

    public void setUsageCode(String usageCode) { UsageCode = usageCode; }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
