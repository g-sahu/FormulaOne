package com.opentext.formulaone.utilities;

import com.opentext.formulaone.beans.Driver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gasahu on 01-Jul-17.
 */

public class Utilities {
    public static List<String[]> getConvertedDriverList(List<Driver> driverList) {
        List<String[]> list = new ArrayList<>();
        Iterator<Driver> driverIterator = driverList.iterator();
        Driver driver;
        String values[];

        while(driverIterator.hasNext()) {
            //values = new String[5];
            values = new String[4];
            driver = driverIterator.next();

            /*values[0] = driver.getGivenName() + " " + driver.getFamilyName();
            values[1] = String.valueOf(driver.getPermanentNumber());
            values[2] = driver.getNationality();
            values[3] = driver.getDateOfBirth();
            values[4] = driver.getUrl();*/

            values[0] = driver.getGivenName() + " " + driver.getFamilyName();
            values[1] = driver.getNationality();
            values[2] = driver.getDateOfBirth();
            values[3] = driver.getUrl();

            list.add(values);
        }

        return list;
    }
}
