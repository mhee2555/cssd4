package com.phc.cssd.config;

public class Setting {

    //------------------------------------------------
    // Master Item
    //------------------------------------------------
    boolean IsGenItemCode = false ;

    //------------------------------------------------
    // Send sterile
    //------------------------------------------------
    boolean IsUsedDeleteMultiItems = false;

    //------------------------------------------------
    // Wash && Sterile
    //------------------------------------------------
    boolean IsUsedOccupancyRate = false ;
    boolean IsShowBasket = false ;


    public Setting(boolean isGenItemCode, boolean isUsedDeleteMultiItems, boolean isUsedOccupancyRate, boolean isShowBasket) {
        IsGenItemCode = isGenItemCode;
        IsUsedDeleteMultiItems = isUsedDeleteMultiItems;
        IsUsedOccupancyRate = isUsedOccupancyRate;
        IsShowBasket = isShowBasket;
    }

    public boolean isGenItemCode() {
        return IsGenItemCode;
    }

    public void setGenItemCode(boolean genItemCode) {
        IsGenItemCode = genItemCode;
    }

    public boolean isUsedDeleteMultiItems() {
        return IsUsedDeleteMultiItems;
    }

    public void setUsedDeleteMultiItems(boolean usedDeleteMultiItems) {
        IsUsedDeleteMultiItems = usedDeleteMultiItems;
    }

    public boolean isUsedOccupancyRate() {
        return IsUsedOccupancyRate;
    }

    public void setUsedOccupancyRate(boolean usedOccupancyRate) {
        IsUsedOccupancyRate = usedOccupancyRate;
    }

    public boolean isShowBasket() {
        return IsShowBasket;
    }

    public void setShowBasket(boolean showBasket) {
        IsShowBasket = showBasket;
    }
}
