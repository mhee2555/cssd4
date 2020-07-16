package com.phc.cssd.loaner.model;

import com.phc.cssd.model.Document;

public class LoanerDocument extends Document {
    String supplierID;
    String supplierName;
    String sendUser;
    String sendDate;
    String sendTime;
    String appointmentDate;

    String appointmentTime;
    int IsUsed;

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sandDate) {
        this.sendDate = sandDate;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getIsUsed() {
        return IsUsed;
    }

    public void setIsUsed(int isUsed) {
        IsUsed = isUsed;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

}
