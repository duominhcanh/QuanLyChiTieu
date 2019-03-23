package com.hutechlab.model;

public class StringWithTag {
    public String string;
    public int tag;

    public StringWithTag(String string, int tag) {
        this.string = string;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return string;
    }
}
