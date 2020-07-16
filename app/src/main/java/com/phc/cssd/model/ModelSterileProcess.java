package com.phc.cssd.model;

public class ModelSterileProcess {

    private String ID, SterileName, IsCancel = "1", ProcessTest = "0";
    private int SterileR = 0;
    private int index = -1;
    private int id = 0;

    public ModelSterileProcess(String ID, String sterileName, String IsCancel, String SterileR, String ProcessTest, int index) {
        this.ID = ID;
        SterileName = sterileName;
        this.index = index;
        this.IsCancel = IsCancel;

        try {
            this.SterileR = Integer.valueOf(SterileR).intValue();
        }catch(Exception e){
            this.SterileR = 0;
        }

        try {
            this.id = Integer.valueOf(ID).intValue();
        }catch(Exception e){
            this.id = 0;
        }

        this.ProcessTest = ProcessTest;

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

    public String getSterileName() {
        return SterileName;
    }

    public void setSterileName(String sterileName) {
        SterileName = sterileName;
    }

    public boolean isCancel() {
        return IsCancel.equals("1");
    }

    public void setIsCancel(String isCancel) {
        IsCancel = isCancel;
    }

    public int getSterileR() {
        return SterileR;
    }

    public void setSterileR(String sterileR) {
        try {
            SterileR = Integer.valueOf(sterileR).intValue();
        }catch(Exception e){
            SterileR = 0;
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getProcessTest() {
        return ProcessTest;
    }

    public void setProcessTest(String processTest) {
        ProcessTest = processTest;
    }

    public boolean isBowiedick() {
        try {
            return ProcessTest.equals("1");
        }catch(Exception e){
            return false;
        }
    }
}
