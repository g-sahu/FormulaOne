package com.opentext.formulaone.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gasahu on 01-Jul-17.
 */

public class DriverTable {
    @SerializedName("Drivers")
    private List<Driver> driverList;

    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }
}
