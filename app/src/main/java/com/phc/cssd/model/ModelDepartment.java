package com.phc.cssd.model;

public class ModelDepartment {
    private String dept_Name;
    private String dept_ID;

    public ModelDepartment(String dept_ID, String dept_Name) {
        this.dept_ID = dept_ID;
        this.dept_Name = dept_Name;
    }

    public String getDept_Name() {
        return dept_Name;
    }

    public void setDept_Name(String dept_Name) {
        this.dept_Name = dept_Name;
    }

    public String getDept_ID() {
        return dept_ID;
    }

    public void setDept_ID(String dept_ID) {
        this.dept_ID = dept_ID;
    }
}
