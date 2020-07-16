package com.phc.cssd.properties;

/**
 * Created by Tanadech on 12/16/2016.
 */

public class Response_Order {
    private Boolean error;
    private  String RowId;
    private  String DocNo;
    private  String DocDate;
    private  String xCode;
    private  String xName;
    private  String BreakGroup;
    private  String Detail;
    private  String DueDate;
    private  String IsSendPD;
    private  String IsConFirm;
    private  String Qty;
    private  String Qty1;
    private  String Qty2;
    private  String Transporter;

    public Boolean getError() {
        return error;
    }
    public void setError(Boolean error) {
        this.error = error;
    }

    public String getId() { return RowId; }
    public void setId(String RowId) {
        this.RowId = RowId;
    }

    public String getDocNo() { return DocNo; }
    public void setDocNo(String DocNo) {
        this.DocNo = DocNo;
    }

    public String getDocDate() { return DocDate; }
    public void setDocDate(String DocDate) {
        this.DocDate = DocDate;
    }

    public String getxCode() { return xCode; }
    public void setxCode(String xCode) {
        this.xCode = xCode;
    }

    public String getxName() { return xName; }
    public void setxName(String xName) {
        this.xName = xName;
    }

    public String getBreakGroup() { return BreakGroup; }
    public void setBreakGroup(String BreakGroup) {
        this.BreakGroup = BreakGroup;
    }

    public String getDetail() { return Detail; }
    public void setDetail(String Detail) {
        this.Detail = Detail;
    }

    public String getDueDate() { return DueDate; }
    public void setDueDate(String DueDate) {
        this.DueDate = DueDate;
    }

    public String getIsSendPD() { return IsSendPD; }
    public void setIsSendPD(String IsSendPD) {
        this.IsSendPD = IsSendPD;
    }

    public String getIsConFirm() { return IsConFirm; }
    public void setIsConFirm(String IsConFirm) {
        this.IsConFirm = IsConFirm;
    }

    public String getQty() { return Qty; }
    public void setQty(String Qty) {
        this.Qty = Qty;
    }

    public String getQty1() { return Qty1; }
    public void setQty1(String Qty1) {
        this.Qty1 = Qty1;
    }

    public String getQty2() { return Qty2; }
    public void setQty2(String Qty2) {
        this.Qty2 = Qty2;
    }

    public String getTransporter() { return Transporter; }
    public void setTransporter(String Transporter) {
        this.Transporter = Transporter;
    }

}
