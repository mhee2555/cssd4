package com.phc.cssd.model;

public class Modelrecallnew {
    String DocNo;
    String DepName2;
    String MachineName2;
    String SterileRoundNumber;
    String DocDate;
    String IsStatus;
    String DocTypeNo;
    String Remark;

    public Modelrecallnew(String DocNo, String DepName2, String MachineName2, String SterileRoundNumber, String DocDate, String IsStatus, String DocTypeNo, String Remark) {
        this.DocNo = DocNo;
        this.DepName2 = DepName2;
        this.MachineName2 = MachineName2;
        this.SterileRoundNumber = SterileRoundNumber;
        this.DocDate = DocDate;
        this.IsStatus = IsStatus;
        this.DocTypeNo = DocTypeNo;
        this.Remark = Remark;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getDocNo(){
        return DocNo;
    }

    public void  setDocNo(String docNo){
        this.DocNo = docNo;
    }

    public String getDepName2(){
        return DepName2;
    }

    public void  setDepName2(String depName2){
        this.DepName2 = depName2;
    }

    public String getMachineName2(){
        return MachineName2;
    }

    public void  setMachineName2(String machineName2){
        this.MachineName2 = machineName2;
    }

    public String getSterileRoundNumber(){
        return SterileRoundNumber;
    }

    public void  setSterileRoundNumber(String sterileRoundNumber){
        this.SterileRoundNumber = sterileRoundNumber;
    }

    public String getDocDate(){
        return DocDate;
    }

    public void  setDocDate(String docDate){
        this.DocDate = docDate;
    }

    public String getIsStatus(){
        return IsStatus;
    }

    public void  setIsStatus(String isStatus){
        this.IsStatus = isStatus;
    }

    public String getDocTypeNo(){
        return DocTypeNo;
    }

    public void  setDocTypeNo(String docTypeNo){
        this.DocTypeNo = docTypeNo;
    }

}