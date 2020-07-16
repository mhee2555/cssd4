package com.phc.cssd.model;

public class ModelWashMachine {

    String ID;
    String MachineName;
    String IsActive;
    String DocNo;
    String StartTime;
    String FinishTime;
    String PauseTime;
    String IsPause;
    String CountTime = "";
    String TestProgramID;
    String WashTypeID;
    String TestProgramName;
    String WashTypeName;

    public ModelWashMachine(String ID, String machineName, String isActive, String docNo) {
        this.ID = ID;
        MachineName = machineName;
        IsActive = isActive;
        DocNo = docNo;
    }


    private int index = -1;

    /*
    public ModelWashMachine(String ID, String machineName, String isActive, String docNo, String startTime, String finishTime, String pauseTime, String isPause, int index) {
        this.ID = ID;
        MachineName = machineName;
        IsActive = isActive;
        DocNo = docNo;
        StartTime = startTime;
        FinishTime = finishTime;
        PauseTime = pauseTime;
        IsPause = isPause;
        this.index = index;
    }
    */

    public ModelWashMachine(
            String ID,
            String machineName,
            String isActive,
            String docNo,
            String startTime,
            String finishTime,
            String pauseTime,
            String isPause,
            String countTime,
            String TestProgramID,
            String WashTypeID,
            String TestProgramName,
            String WashTypeName,
            int index
    ) {
        this.ID = ID;
        MachineName = machineName;
        IsActive = isActive;
        DocNo = docNo;
        StartTime = startTime;
        FinishTime = finishTime;
        PauseTime = pauseTime;
        IsPause = isPause;
        CountTime = countTime;
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

    public String getCountTime() {
        return CountTime;
    }

    public void setCountTime(String countTime) {
        CountTime = countTime;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMachineName() {
        return MachineName;
    }

    public void setMachineName(String machineName) {
        MachineName = machineName;
    }

    public String getIsActive() {
        return IsActive;
    }

    public boolean isActive() {
        return IsActive.equals("1");
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    public String getDocNo() {
        return DocNo;
    }

    public void setDocNo(String docNo) {
        DocNo = docNo;
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

    public void setFinishTime(String FinishTime) {
        FinishTime = FinishTime;
    }

    public int getIndex() {
        return index;
    }

    public int getMachineNumber() {
        return index;
    }

    public String getMachineNo() {
        return Integer.toString(index);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPauseTime() {
        return PauseTime;
    }

    public void setPauseTime(String pauseTime) {
        PauseTime = pauseTime;
    }

    public String getIsPause() {
        return IsPause;
    }

    public void setIsPause(String isPause) {
        IsPause = isPause;
    }
}
