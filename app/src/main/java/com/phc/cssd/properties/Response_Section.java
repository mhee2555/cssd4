package com.phc.cssd.properties;

/**
 * Created by Tanadech on 12/16/2016.
 */

public class Response_Section {
    private Boolean error;
    private  String xCode;
    private  String xName;
    private  String xIcon;
    private  String xBranch_Code;

    public Boolean getError() {
        return error;
    }
    public void setError(Boolean error) {
        this.error = error;
    }

    public String getCode() { return xCode; }
    public void setCode(String xCode) {
        this.xCode = xCode;
    }

    public String getxName() { return xName; }
    public void setxName(String xName) {
        this.xName = xName;
    }

    public String getIcon() { return xIcon; }
    public void setIcon(String xIcon) {
        this.xIcon = xIcon;
    }

    public String getBranch_Code() { return xBranch_Code; }
    public void setBranch_Code(String xBranch_Code) {
        this.xBranch_Code = xBranch_Code;
    }
}
