package com.phc.cssd.model;

import com.phc.cssd.R;

public class ModelSendSterileDetailMachine {

    String i_id;
    String i_code;
    String i_name;
    String i_alt_name;
    String i_barcode;
    String i_qty;
    String i_program;
    String i_program_id;
    String PackingMat;
    String Shelflife;
    String PackingMatID;

    public boolean IsCheck;

    private int index = -1;

    public ModelSendSterileDetailMachine(String i_id, String i_code, String i_name, String i_alt_name, String i_barcode, String i_qty, String i_program, String i_program_id, boolean isCheck, int index) {
        this.i_id = i_id;
        this.i_code = i_code;
        this.i_name = i_name;
        this.i_alt_name = i_alt_name;
        this.i_barcode = i_barcode;
        this.i_qty = i_qty;
        this.i_program = i_program;
        this.i_program_id = i_program_id;
        IsCheck = isCheck;
        this.index = index;
    }

    public String getPackingMat() {
        return PackingMat;
    }

    public void setPackingMat(String packingMat) {
        PackingMat = packingMat;
    }

    public String getShelflife() {
        return Shelflife;
    }

    public void setShelflife(String shelflife) {
        Shelflife = shelflife;
    }

    public String getPackingMatID() {
        return PackingMatID;
    }

    public void setPackingMatID(String packingMatID) {
        PackingMatID = packingMatID;
    }

    public String getI_id() {
        return i_id;
    }

    public void setI_id(String i_id) {
        this.i_id = i_id;
    }

    public String getI_code() {
        return i_code;
    }

    public void setI_code(String i_code) {
        this.i_code = i_code;
    }

    public String getI_name() {
        return i_name;
    }

    public void setI_name(String i_name) {
        this.i_name = i_name;
    }

    public String getI_alt_name() {
        return i_alt_name;
    }

    public void setI_alt_name(String i_alt_name) {
        this.i_alt_name = i_alt_name;
    }

    public String getI_barcode() {
        return i_barcode;
    }

    public void setI_barcode(String i_barcode) {
        this.i_barcode = i_barcode;
    }

    public String getI_qty() {
        return i_qty;
    }

    public void setI_qty(String i_qty) {
        this.i_qty = i_qty;
    }

    public boolean isCheck() {
        return IsCheck;
    }

    public int getCheck() {
        return IsCheck ? R.drawable.ic_box_check_blue : R.drawable.ic_box_normal_blue;
    }

    public void setCheck(boolean check) {
        IsCheck = check;
    }

    public int getIndex() {
        return index;
    }

    public String getRow() {
        return (index+1)+".";
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getI_program() {
        return i_program;
    }

    public void setI_program(String i_program) {
        this.i_program = i_program;
    }

    public String getI_program_id() {
        return i_program_id;
    }

    public void setI_program_id(String i_program_id) {
        this.i_program_id = i_program_id;
    }
}
