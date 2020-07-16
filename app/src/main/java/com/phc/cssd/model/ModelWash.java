package com.phc.cssd.model;

public class ModelWash {
    String ID;
    String DocNo;
    String DocDate;
    String ModifyDate;
    String UserCode;
    String DeptID;
    String Qty;
    String IsStatus;
    String WashProgramID;
    String WashMachineID;
    String WashRoundNumber;
    String StartTime;
    String FinishTime;
    String Remark;
    String WashingProcess;
    String MachineName;
    String TestProgramID;
    String WashTypeID;
    String TestProgramName;
    String WashTypeName;

    int index = 0;

    public ModelWash(
            String ID,
            String docNo,
            String docDate,
            String modifyDate,
            String userCode,
            String deptID,
            String qty,
            String isStatus,
            String washProgramID,
            String washMachineID,
            String washRoundNumber,
            String startTime,
            String finishTime,
            String remark,
            String washingProcess,
            String machineName,
            String TestProgramID,
            String WashTypeID,
            String TestProgramName,
            String WashTypeName,
            int index
    ) {
        this.ID = ID;
        DocNo = docNo;
        DocDate = docDate;
        ModifyDate = modifyDate;
        UserCode = userCode;
        DeptID = deptID;
        Qty = qty;
        IsStatus = isStatus;
        WashProgramID = washProgramID;
        WashMachineID = washMachineID;
        WashRoundNumber = washRoundNumber;
        StartTime = startTime;
        FinishTime = finishTime;
        Remark = remark;
        WashingProcess = washingProcess;
        MachineName = machineName;
        this.TestProgramID = TestProgramID;
        this.WashTypeID = WashTypeID;
        this.TestProgramName = TestProgramName;
        this.WashTypeName = WashTypeName;
        this.index = index;
    }

    public String getTestProgramID() {
        return TestProgramID;
    }

    public void setTestProgramID(String testProgramID) {
        TestProgramID = testProgramID;
    }

    public String getWashTypeID() {
        return WashTypeID;
    }

    public void setWashTypeID(String washTypeID) {
        WashTypeID = washTypeID;
    }

    public String getTestProgramName() {
        return TestProgramName;
    }

    public void setTestProgramName(String testProgramName) {
        TestProgramName = testProgramName;
    }

    public String getWashTypeName() {
        return WashTypeName;
    }

    public void setWashTypeName(String washTypeName) {
        WashTypeName = washTypeName;
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

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getDeptID() {
        return DeptID;
    }

    public void setDeptID(String deptID) {
        DeptID = deptID;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getIsStatus() {
        return IsStatus;
    }

    public void setIsStatus(String isStatus) {
        IsStatus = isStatus;
    }

    public String getWashProgramID() {
        return WashProgramID;
    }

    public void setWashProgramID(String washProgramID) {
        WashProgramID = washProgramID;
    }

    public String getWashMachineID() {
        return WashMachineID;
    }

    public void setWashMachineID(String washMachineID) {
        WashMachineID = washMachineID;
    }

    public String getWashRoundNumber() {
        return WashRoundNumber;
    }

    public void setWashRoundNumber(String washRoundNumber) {
        WashRoundNumber = washRoundNumber;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getFinishTime() {
        return FinishTime;
    }

    public void setFinishTime(String finishTime) {
        FinishTime = finishTime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getWashingProcess() {
        return WashingProcess;
    }

    public void setWashingProcess(String washingProcess) {
        WashingProcess = washingProcess;
    }

    public String getMachineName() {
        return MachineName;
    }

    public void setMachineName(String machineName) {
        MachineName = machineName;
    }
}
