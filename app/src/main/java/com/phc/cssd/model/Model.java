package com.phc.cssd.model;

public class Model {
    String id, code, name;

    int index = 0;

    public Model(int index, String id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}

