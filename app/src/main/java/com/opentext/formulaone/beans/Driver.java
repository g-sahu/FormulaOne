package com.opentext.formulaone.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gasahu on 30-Jun-17.
 */

public class Driver {
    @SerializedName("driverId")
    private String driverId;

    @SerializedName("givenName")
    private String givenName;

    @SerializedName("familyName")
    private String familyName;

    @SerializedName("dateOfBirth")
    private String dateOfBirth;

    @SerializedName("nationality")
    private String nationality;

    @SerializedName("permanentNumber")
    private int permanentNumber;

    @SerializedName("code")
    private String code;

    @SerializedName("url")
    private String url;

    public Driver(String driverId, String givenName, String familyName, String dateOfBirth,
                  String nationality, int permanentNumber, String code, String url) {
        this.driverId = driverId;
        this.givenName = givenName;
        this.familyName = familyName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.permanentNumber = permanentNumber;
        this.code = code;
        this.url = url;
    }

    public Driver() {}

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getPermanentNumber() {
        return permanentNumber;
    }

    public void setPermanentNumber(int permanentNumber) {
        this.permanentNumber = permanentNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Driver) {
            Driver driver = (Driver) obj;
            String oldDriverCode = driver.getCode() == null ? "" : driver.getCode();
            String newDriverCode = this.getCode() == null ? "" : this.getCode();

            return (driver.getDriverId().equalsIgnoreCase(this.getDriverId())
                    && driver.getGivenName().equalsIgnoreCase(this.getGivenName())
                    && driver.getFamilyName().equalsIgnoreCase(this.getFamilyName())
                    && driver.getDateOfBirth().equalsIgnoreCase(this.getDateOfBirth())
                    && driver.getNationality().equalsIgnoreCase(this.getNationality())
                    && driver.getPermanentNumber() == this.getPermanentNumber()
                    && oldDriverCode.equalsIgnoreCase(newDriverCode)
                    && driver.getUrl().equalsIgnoreCase(this.getUrl()));
        } else {
            return false;
        }
    }
}