package com.phc.cssd.model;

public class ModelSendSterile {

    String ID;
    String DocNo;
    String DocDate;
    String ModifyDate;
    String DeptID;
    String UserCode;
    String Qty;
    String Remark;
    String DepName;
    String DepName2;
    String UserName;
    String list_count;
    String total;
    private int index = -1;

    int IsStatus = 0;

    public ModelSendSterile(String ID, String docNo, String docDate, String modifyDate, String deptID, String userCode, String qty, String remark, String depName, String depName2, String userName, String list_count, String total, int index) {
        this.ID = ID;
        DocNo = docNo;
        DocDate = docDate;
        ModifyDate = modifyDate;
        DeptID = deptID;
        UserCode = userCode;
        Qty = qty;
        Remark = remark;
        DepName = depName;
        DepName2 = depName2;
        UserName = userName;
        this.list_count = list_count;
        this.total = total;
        this.index = index;
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

    public String getDocDate() {
        return DocDate;
    }

    public void setDocDate(String docDate) {
        DocDate = docDate;
    }

    public String getModifyDate() {
        return ModifyDate;
    }

    public void setModifyDate(String modifyDate) {
        ModifyDate = modifyDate;
    }

    public String getDeptID() {
        return DeptID;
    }

    public void setDeptID(String deptID) {
        DeptID = deptID;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getDepName() {
        return DepName;
    }

    public void setDepName(String depName) {
        DepName = depName;
    }

    public String getDepName2() {
        return DepName2;
    }

    public void setDepName2(String depName2) {
        DepName2 = depName2;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getList_count() {
        return list_count;
    }

    public void setList_count(String list_count) {
        this.list_count = list_count;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIsStatus() {
        return IsStatus;
    }

    public void setIsStatus(int isStatus) {
        IsStatus = isStatus;
    }
}
