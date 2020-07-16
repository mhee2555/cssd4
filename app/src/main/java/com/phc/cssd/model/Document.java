package com.phc.cssd.model;

public class Document {
    private String DocNo;
    private String DocDate;
    private String Remark;
    private String IsStatus;

    public String getIsStatus() {
        return IsStatus;
    }

    public void setIsStatus(String IsStatus) {
        this.IsStatus = IsStatus;
    }

    public String getDocNo() {
        return DocNo;
    }

    public void setDocNo(String docNo) {
        DocNo = docNo;
    }

    public String getDocDate() {
        return DocDate;
    }

    public void setDocDate(String docDate) {
        DocDate = docDate;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
