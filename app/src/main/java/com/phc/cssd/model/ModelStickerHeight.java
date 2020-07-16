package com.phc.cssd.model;

public class ModelStickerHeight {
    String LabelName;
    String Height;


    public ModelStickerHeight(String DocNo, String Itemcode) {
        this.LabelName = LabelName;
        this.Height = Height;
    }

    public String getLabelName() {
        return LabelName;
    }

    public void setLabelName(String labelName) {
        LabelName = labelName;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }
}
