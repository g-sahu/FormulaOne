package com.opentext.formulaone.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gasahu on 01-Jul-17.
 */

public class MRData {
    @SerializedName("xmlns")
    private String xmlns;

    @SerializedName("series")
    private String series;

    @SerializedName("url")
    private String url;

    @SerializedName("limit")
    private int limit;

    @SerializedName("offset")
    private int offset;

    @SerializedName("total")
    private int total;

    @SerializedName("DriverTable")
    private DriverTable driverTable;

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public DriverTable getDriverTable() {
        return driverTable;
    }

    public void setDriverTable(DriverTable driverTable) {
        this.driverTable = driverTable;
    }
}
