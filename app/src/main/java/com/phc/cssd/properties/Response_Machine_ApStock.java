package com.phc.cssd.properties;

/**
 * Created by Tanadech on 12/18/2016.
 */

public class Response_Machine_ApStock {
    private  Boolean error;
    private  String DocNo;
    private  String MachineName;
    private  String SterileName;
    private  String FinishTime;
    private  int MachineCount;
    private  int McID;
    private  int IsStatus;
    private  int SterileRoundNumber;

    public int getSterileRoundNumber() {
        return SterileRoundNumber;
    }

    public void setSterileRoundNumber(int sterileRoundNumber) {
        SterileRoundNumber = sterileRoundNumber;
    }

    public Boolean getError() {
        return error;
    }
    public void setError(Boolean error) {
        this.error = error;
    }

    public String getDocNo() { return DocNo; }
    public void setDocNo(String DocNo) {
        this.DocNo = DocNo;
    }

    public String getMachineName() { return MachineName; }
    public void setMachineName(String MachineName) {
        this.MachineName = MachineName;
    }

    public String getSterileName() { return SterileName; }
    public void setSterileName(String SterileName) {
        this.SterileName = SterileName;
    }

    public String getFinishTime() { return FinishTime; }
    public void setFinishTime(String FinishTime) {
        this.FinishTime = FinishTime;
    }

    public int getMachineCount() { return MachineCount; }
    public void setMachineCount(int MachineCount) {
        this.MachineCount = MachineCount;
    }

    public int getMcID() { return McID; }
    public void setMcID(int McID) {
        this.McID = McID;
    }

    public int getIsStatus() { return IsStatus; }
    public void setIsStatus(int IsStatus) {
        this.IsStatus = IsStatus;
    }
}