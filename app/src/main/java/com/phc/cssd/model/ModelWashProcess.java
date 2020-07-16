package com.phc.cssd.model;

public class ModelWashProcess {

    private String ID, WashingProcess, IsCancel = "1", IsProgramTest = "0";
    private int ProgramR = 0;
    private int index = -1;
    private int id = 0;

    public ModelWashProcess(String ID, String sterileName, String IsCancel, String ProgramR, String IsProgramTest, int index) {
        this.ID = ID;
        WashingProcess = sterileName;
        this.index = index;
        this.IsCancel = IsCancel;

        try {
            this.ProgramR = Integer.valueOf(ProgramR).intValue();
        }catch(Exception e){
            this.ProgramR = 0;
        }

        try {
            this.id = Integer.valueOf(ID).intValue();
        }catch(Exception e){
            this.id = 0;
        }

        this.IsProgramTest = IsProgramTest;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getWashingProcess() {
        return WashingProcess;
    }

    public void setWashingProcess(String sterileName) {
        WashingProcess = sterileName;
    }

    public boolean isCancel() {
        return IsCancel.equals("1");
    }

    public void setIsCancel(String isCancel) {
        IsCancel = isCancel;
    }

    public int getProgramR() {
        return ProgramR;
    }

    public void setProgramR(String sterileR) {
        try {
            ProgramR = Integer.valueOf(sterileR).intValue();
        }catch(Exception e){
            ProgramR = 0;
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getIsProgramTest() {
        return IsProgramTest;
    }

    public void setIsProgramTest(String processTest) {
        IsProgramTest = processTest;
    }

    public boolean isBowiedick() {
        try {
            return IsProgramTest.equals("1");
        }catch(Exception e){
            return false;
        }
    }
}
