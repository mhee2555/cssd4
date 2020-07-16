package com.phc.cssd.model;

public class ModelSterileProgram {

    private String ID, SterileName;
    private int index = -1;

    public ModelSterileProgram(String ID, String sterileName, int index) {
        this.ID = ID;
        SterileName = sterileName;
        this.index = index;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
