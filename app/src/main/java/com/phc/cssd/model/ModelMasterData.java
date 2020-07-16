package com.phc.cssd.model;

public class ModelMasterData {

    private String code, name;
    private boolean isCheck = false;
    private int index = -1;
    private String value1;

    public ModelMasterData(int index, String code, String name ) {
        this.code = code;
        this.name = name;
        this.index = index;
    }

    public ModelMasterData(int index, String code, String name, String value1 ) {
        this.code = code;
        this.name = name;
        this.index = index;
        this.value1 = value1;
    }

    public ModelMasterData(String code, String name, boolean isCheck, int index) {
        this.code = code;
        this.name = name;
        this.isCheck = isCheck;
        this.index = index;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }
}
