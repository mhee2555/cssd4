package com.phc.cssd.model;

public class ModelItems {
    private String ID;
    private String itemcode;
    private String itemname;
    private String Alternatename;
    private String Barcode;
    private String IsReuse;
    private String IsNormal;
    private String itemtypeID;
    private String UnitID;
    private String SpecialID;

    private String DepartmentID;
    private String ShelfLifeID;
    private String Picture;
    private String SetCount;
    private String PackingMatID;
    private String CostPrice;
    private String SalePrice;
    private String UsagePrice;
    private String SterileMachineID;
    private String SterileProcessID;

    private String WashMachineID;
    private String WashProcessID;
    private String SupllierID;
    private String FactID;
    private String LabelID;
    private String Minimum;
    private String Maximum;
    private String weight;
    private String DepName;
    private String TyeName;

    private String LabelName;
    private String ManuFact;
    private String PackingMat;
    private String SpecialName;
    private String Machine_Name;
    private String SterileName;
    private String suppliername;
    private String UnitName;

    private String IsSet;
    private String IsSpecial;

    private String OnDepartment;
    private String OnStock;
    private String OnCssd;
    private String All_ItemStock;

    private String Shelflife;
    private String NoWash = "0";
    private String NoWashType = "0";
    private String WashDept = "0";
    private String LimitCount;
    private String PayDep;
    private String IsDenger;

    public String getIsDenger() { return IsDenger; }

    public void setIsDenger(String isDenger) { IsDenger = isDenger; }

    public String getPayDep() {
        return PayDep;
    }

    public void setPayDep(String payDep) {
        PayDep = payDep;
    }

    public String getLimitCount() {
        return LimitCount;
    }

    public void setLimitCount(String limitCount) {
        LimitCount = limitCount;
    }

    public String getWashDept() {
        return WashDept;
    }

    public void setWashDept(String washDept) {
        WashDept = washDept;
    }

    public String getNoWash() {
        return NoWash;
    }

    public void setNoWash(String noWash) {
        NoWash = noWash;
    }

    public String getNoWashType() {
        return NoWashType;
    }

    public void setNoWashType(String noWashType) {
        NoWashType = noWashType;
    }

    public String getOnDepartment() {
        return OnDepartment;
    }

    public void setOnDepartment(String onDepartment) {
        OnDepartment = onDepartment;
    }

    public String getOnStock() {
        return OnStock;
    }

    public void setOnStock(String onStock) {
        OnStock = onStock;
    }

    public String getOnCssd() {
        return OnCssd;
    }

    public void setOnCssd(String onCssd) {
        OnCssd = onCssd;
    }

    public String getAll_ItemStock() {
        return All_ItemStock;
    }

    public void setAll_ItemStock(String all_ItemStock) {
        All_ItemStock = all_ItemStock;
    }

    public boolean isChecked() {
        return IsChecked;
    }

    public void setChecked(boolean checked) {
        IsChecked = checked;
    }

    private String WashingName;
    private String WashingProcess = null;

    private boolean IsChecked = false;
    private int index = 0;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String isSet() {
        return IsSet;
    }

    public void setIsSet(String isSet) {
        IsSet = isSet;
    }

    public boolean isSpecial() {
        return IsSpecial != null && IsSpecial.equals("1");
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getAlternatename() {
        return Alternatename;
    }

    public void setAlternatename(String alternatename) {
        Alternatename = alternatename;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public boolean getIsReuse() {
        return IsReuse != null && IsReuse.equals("1");
    }

    public void setIsReuse(String isReuse) {
        IsReuse = isReuse;
    }

    public boolean getIsNormal() {
        return IsNormal != null && IsNormal.equals("1");
    }

    public void setIsNormal(String isNormal) {
        IsNormal = isNormal;
    }

    public String getItemtypeID() {
        return itemtypeID;
    }

    public void setItemtypeID(String itemtypeID) {
        this.itemtypeID = itemtypeID;
    }

    public String getUnitID() {
        return UnitID;
    }

    public void setUnitID(String unitID) {
        UnitID = unitID;
    }

    public String getSpecialID() {
        return SpecialID;
    }

    public void setSpecialID(String specialID) {
        SpecialID = specialID;
    }

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String departmentID) {
        DepartmentID = departmentID;
    }

    public String getShelfLifeID() {
        return ShelfLifeID;
    }

    public void setShelfLifeID(String shelfLifeID) {
        ShelfLifeID = shelfLifeID;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public String getSetCount() {
        return SetCount;
    }

    public void setSetCount(String setCount) {
        SetCount = setCount;
    }

    public String getPackingMatID() {
        return PackingMatID;
    }

    public void setPackingMatID(String packingMatID) {
        PackingMatID = packingMatID;
    }

    public String getCostPrice() {
        return CostPrice;
    }

    public void setCostPrice(String costPrice) {
        CostPrice = costPrice;
    }

    public String getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(String salePrice) {
        SalePrice = salePrice;
    }

    public String getUsagePrice() {
        return UsagePrice;
    }

    public void setUsagePrice(String usagePrice) {
        UsagePrice = usagePrice;
    }

    public String getSterileMachineID() {
        return SterileMachineID;
    }

    public void setSterileMachineID(String sterileMachineID) {
        SterileMachineID = sterileMachineID;
    }

    public String getSterileProcessID() {
        return SterileProcessID;
    }

    public void setSterileProcessID(String sterileProcessID) {
        SterileProcessID = sterileProcessID;
    }

    public String getWashMachineID() {
        return WashMachineID;
    }

    public void setWashMachineID(String washMachineID) {
        WashMachineID = washMachineID;
    }

    public String getWashProcessID() {
        return WashProcessID;
    }

    public void setWashProcessID(String washProcessID) {
        WashProcessID = washProcessID;
    }

    public String getSupllierID() {
        return SupllierID;
    }

    public void setSupllierID(String supllierID) {
        SupllierID = supllierID;
    }

    public String getFactID() {
        return FactID;
    }

    public void setFactID(String factID) {
        FactID = factID;
    }

    public String getLabelID() {
        return LabelID;
    }

    public void setLabelID(String labelID) {
        LabelID = labelID;
    }

    public String getMinimum() {
        return Minimum;
    }

    public void setMinimum(String minimum) {
        Minimum = minimum;
    }

    public String getMaximum() {
        return Maximum;
    }

    public void setMaximum(String maximum) {
        Maximum = maximum;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDepName() {
        return ( DepName == null || DepName.equals("-") ) ? "" : DepName;
    }

    public void setDepName(String depName) {
        DepName = depName;
    }

    public String getTyeName() {
        return ( TyeName == null || TyeName.equals("-") ) ? "" : TyeName;
    }

    public void setTyeName(String tyeName) {
        TyeName = tyeName;
    }

    public String getLabelName() {
        return ( LabelName == null || LabelName.equals("-") ) ? "" : LabelName;
    }

    public void setLabelName(String labelName) {
        LabelName = labelName;
    }

    public String getManuFact() {
        return ( ManuFact == null || ManuFact.equals("-") ) ? "" : ManuFact;
    }

    public void setManuFact(String manuFact) {
        ManuFact = manuFact;
    }

    public String getPackingMat() {
        return PackingMat;
    }

    public void setPackingMat(String packingMat) {
        PackingMat = packingMat;
    }

    public String getSpecialName() {
        return ( SpecialName == null || SpecialName.equals("-") ) ? "" : SpecialName;
    }

    public void setSpecialName(String specialName) {
        SpecialName = specialName;
    }

    public String getMachine_Name() {
        return Machine_Name;
    }

    public void setMachine_Name(String machine_Name) {
        Machine_Name = machine_Name;
    }

    public String getSterileName() {
        return ( SterileName == null || SterileName.equals("-") ) ? "" : SterileName;
    }

    public void setSterileName(String sterileName) {
        SterileName = sterileName;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
    }

    public String getUnitName() {
        return ( UnitName == null || UnitName.equals("-") ) ? "" : UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public String getWashingName() {
        return ( WashingName == null || WashingName.equals("-") ) ? "" : WashingName;
    }

    public void setWashingName(String washingName) {
        WashingName = washingName;
    }

    public String getWashingProcess() {
        return WashingProcess;
    }

    public void setWashingProcess(String washingProcess) {
        WashingProcess = washingProcess;
    }

    public String getIsSpecial() {
        return IsSpecial;
    }

    public void setIsSpecial(String isSpecial) {
        IsSpecial = isSpecial;
    }

    public String getShelflife() {
        return Shelflife;
    }

    public void setShelflife(String shelflife) {
        Shelflife = shelflife;
    }

    public ModelItems(int index, String ID, String itemcode, String itemname){
        this.index = index;
        this.ID = ID;
        this.itemcode = itemcode;
        this.itemname = itemname;
    }

    public ModelItems(String ID,
                      String itemcode,
                      String itemname,
                      String alternatename,
                      String barcode,
                      String isReuse,
                      String isNormal,
                      String itemtypeID,
                      String unitID,
                      String specialID,
                      String departmentID,
                      String shelfLifeID,
                      String picture,
                      String setCount,
                      String packingMatID,
                      String costPrice,
                      String salePrice,
                      String usagePrice,
                      String sterileMachineID,
                      String sterileProcessID,
                      String washMachineID,
                      String washProcessID,
                      String supllierID,
                      String factID,
                      String labelID,
                      String minimum,
                      String maximum,
                      String weight,
                      String depName,
                      String tyeName,
                      String labelName,
                      String manuFact,
                      String packingMat,
                      String specialName,
                      String machine_Name,
                      String sterileName,
                      String suppliername,
                      String unitName,
                      String washingName,
                      String washingProcess,
                      String IsSet,
                      String IsSpecial,
                      String OnDepartment,
                      String OnStock,
                      String OnCssd,
                      String All_ItemStock,
                      String Shelflife,
                      String NoWash,
                      String NoWashType,
                      String WashDept,
                      String LimitCount,
                      String PayDep,
                      String IsDenger
    ) {

        this.ID = ID;
        this.itemcode = itemcode;
        this.itemname = itemname;
        Alternatename = alternatename;
        Barcode = barcode;
        IsReuse = isReuse;
        IsNormal = isNormal;
        this.itemtypeID = itemtypeID;
        UnitID = unitID;
        SpecialID = specialID;
        DepartmentID = departmentID;
        ShelfLifeID = shelfLifeID;
        Picture = picture;
        SetCount = setCount;
        PackingMatID = packingMatID;
        CostPrice = costPrice;
        SalePrice = salePrice;
        UsagePrice = usagePrice;
        SterileMachineID = sterileMachineID;
        SterileProcessID = sterileProcessID;
        WashMachineID = washMachineID;
        WashProcessID = washProcessID;
        SupllierID = supllierID;
        FactID = factID;
        LabelID = labelID;
        Minimum = minimum;
        Maximum = maximum;
        this.weight = weight;
        DepName = depName;
        TyeName = tyeName;
        LabelName = labelName;
        ManuFact = manuFact;
        PackingMat = packingMat;
        SpecialName = specialName;
        Machine_Name = machine_Name;
        SterileName = sterileName;
        this.suppliername = suppliername;
        UnitName = unitName;
        WashingName = washingName;
        WashingProcess = washingProcess;
        this.IsSet = IsSet;
        this.IsSpecial = IsSpecial;
        this.OnDepartment = OnDepartment;
        this.OnStock = OnStock;
        this.OnCssd = OnCssd;
        this.All_ItemStock = All_ItemStock;
        this.Shelflife = Shelflife;
        this.NoWash = NoWash;
        this.NoWashType = NoWashType;
        this.WashDept = WashDept;
        this.LimitCount = LimitCount;
        this.PayDep = PayDep;
        this.IsDenger = IsDenger;
    }
}
