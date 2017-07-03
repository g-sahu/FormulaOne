package com.opentext.formulaone.beans;

import com.google.gson.annotations.SerializedName;

public class FormulaOneResponse {
    @SerializedName("MRData")
    private MRData mrdata;

    public MRData getMrdata() {
        return mrdata;
    }

    public void setMrdata(MRData mrdata) {
        this.mrdata = mrdata;
    }
}
