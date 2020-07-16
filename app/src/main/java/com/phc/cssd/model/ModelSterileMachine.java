package com.phc.cssd.model;

public class ModelSterileMachine {

    String ID;
    String MachineName;
    String IsActive;
    String DocNo;
    String StartTime;
    String FinishTime;
    String PauseTime;
    String IsPause;
    String CountTime = "";

    private int index = -1;

    public ModelSterileMachine(String ID, String machineName, String isActive, String docNo, String startTime, String finishTime, String pauseTime, String isPause, int index) {
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

    public ModelSterileMachine(String ID, String machineName, String isActive, String docNo, String startTime, String finishTime, String pauseTime, String isPause, String countTime, int index) {
        this.ID = ID;
        MachineName = machineName;
        IsActive = isActive;
        DocNo = docNo;
        StartTime = startTime;
        FinishTime = finishTime;
        PauseTime = pauseTime;
        IsPause = isPause;
        CountTime = countTime;
        this.index = index;
    }

    public String getCountTime() {
        return CountTime == null || CountTime.equals("-") || CountTime.equals("") ? "" : CountTime ;
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
