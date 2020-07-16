package com.phc.cssd.model;

public class ModelRecallDetail {

    String itemname;
    String UsageCode;
    String ExpireDate;

    public ModelRecallDetail(String itemname, String UsageCode,String ExpireDate) {
        this.itemname = itemname;
        this.UsageCode = UsageCode;
        this.ExpireDate = ExpireDate;
    }

    public String getItemname(){
        return itemname;
    }

    public void  setItemname(String itemname){
        this.itemname = itemname;
    }

    public String getUsageCode(){
        return UsageCode;
    }

    public void  setUsageCode(String usageCode){
        this.UsageCode = usageCode;
    }

    public String getExpireDate(){
        return ExpireDate;
    }

    public void  setExpireDate(String expireDate){
        this.ExpireDate = expireDate;
    }

}

