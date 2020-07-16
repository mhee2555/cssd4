package com.phc.cssd.model;

public class ModelPayout {
    String Department_ID;
    String DepName;
    String DocNo;
    String CreateDate;
    String Qty;
    String BorrowBalanceQty;
    String Balance;
    String IsStatus;
    String Payout_Status;

    int index = 0;

    public ModelPayout(String department_ID, String depName, String docNo, String createDate, String qty, String borrowBalanceQty, String balance, String isStatus, String payout_Status, int index) {
        Department_ID = department_ID;
        DepName = depName;
        DocNo = docNo;
        CreateDate = createDate;
        Qty = qty;
        BorrowBalanceQty = borrowBalanceQty;
        Balance = balance;
        IsStatus = isStatus;
        Payout_Status = payout_Status;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDepartment_ID() {
        return Department_ID;
    }

    public void setDepartment_ID(String department_ID) {
        Department_ID = department_ID;
    }

    public String getDepName() {
        return DepName;
    }

    public void setDepName(String depName) {
        DepName = depName;
    }

    public String getDocNo() {
        return DocNo;
    }

    public void setDocNo(String docNo) {
        DocNo = docNo;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getBorrowBalanceQty() {
        return BorrowBalanceQty;
    }

    public void setBorrowBalanceQty(String borrowBalanceQty) {
        BorrowBalanceQty = borrowBalanceQty;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getIsStatus() {
        return IsStatus;
    }

    public void setIsStatus(String isStatus) {
        IsStatus = isStatus;
    }

    public String getPayout_Status() {
        return Payout_Status;
    }

    public void setPayout_Status(String payout_Status) {
        Payout_Status = payout_Status;
    }
}
