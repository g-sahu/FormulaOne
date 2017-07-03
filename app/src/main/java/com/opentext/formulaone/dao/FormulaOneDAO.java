package com.opentext.formulaone.dao;

/**
 * Created by gasahu on 30-Jun-17.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.opentext.formulaone.beans.Driver;
import com.opentext.formulaone.utilities.FormulaOneConstants;
import com.opentext.formulaone.utilities.SQLConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FormulaOneDAO {
    private SQLiteDatabase db;
    private FormulaOneDBHelper mDbHelper;

    public FormulaOneDAO(Context context) {
        mDbHelper = new FormulaOneDBHelper(context);
        db = mDbHelper.getWritableDatabase();
    }

    //Closes SQLite database connection
    public void closeConnection() {
        if(mDbHelper != null) {
            mDbHelper.close();
        }

        if(db != null) {
            db.close();
        }
    }

    public List<Driver> getDrivers() {
        List<Driver> driverList = null;
        Driver driver;
        int c;

        Log.d(FormulaOneConstants.LOG_TAG_SQL, SQLConstants.SQL_SELECT_DRIVERS);
        Cursor cursor = db.rawQuery(SQLConstants.SQL_SELECT_DRIVERS, null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0) {
            driverList = new ArrayList<Driver>();

            while(!cursor.isAfterLast()) {
                driver = new Driver();
                c = FormulaOneConstants.ZERO;

                driver.setDriverId(cursor.getString(c++));
                driver.setGivenName(cursor.getString(c++));
                driver.setFamilyName(cursor.getString(c++));
                driver.setDateOfBirth(cursor.getString(c++));
                driver.setNationality(cursor.getString(c++));
                driver.setPermanentNumber(cursor.getInt(c++));
                driver.setCode(cursor.getString(c++));
                driver.setUrl(cursor.getString(c));

                driverList.add(driver);
                cursor.moveToNext();
            }
        }

        cursor.close();
        return driverList;
    }

    public void addDrivers(List<Driver> addedDriversList) {
        Driver driver;
        long driversAdded = 0;
        int c;

        SQLiteStatement insertStmt = db.compileStatement(SQLConstants.SQL_INSERT_DRIVERS);
        Iterator<Driver> driverIterator = addedDriversList.iterator();
        String code;

        //Inserting drivers in table 'Driver'
        while(driverIterator.hasNext()) {
            driver = driverIterator.next();
            c = FormulaOneConstants.ONE;

            insertStmt.bindString(c++, driver.getDriverId());
            insertStmt.bindString(c++, driver.getGivenName());
            insertStmt.bindString(c++, driver.getFamilyName());
            insertStmt.bindString(c++, driver.getDateOfBirth());
            insertStmt.bindString(c++, driver.getNationality());
            insertStmt.bindLong(c++, driver.getPermanentNumber());

            if(driver.getCode() == null) {
                insertStmt.bindNull(c++);
            } else {
                insertStmt.bindString(c++, driver.getCode());
            }

            insertStmt.bindString(c, driver.getUrl());

            Log.d(FormulaOneConstants.LOG_TAG_SQL, insertStmt.toString());

            try {
                insertStmt.executeInsert();
                insertStmt.clearBindings();
                ++driversAdded;
            } catch(SQLException sqle) {
                Log.e(FormulaOneConstants.LOG_TAG_EXCEPTION, sqle.getMessage());
            }
        }

        Log.d("Drivers added to db", String.valueOf(driversAdded));
    }

    public void removeDrivers(List<Driver> removedDriversList) {
        Iterator<Driver> removedDriversIterator = removedDriversList.iterator();
        StringBuilder driverIds = new StringBuilder();
        int driversDeleted;

        while(removedDriversIterator.hasNext()) {
            driverIds.append(SQLConstants.DOUBLE_QUOTE).append(removedDriversIterator.next().getDriverId()).append(SQLConstants.DOUBLE_QUOTE);

            if(removedDriversIterator.hasNext()) {
                driverIds.append(SQLConstants.COMMA_SEP);
            }
        }

        SQLiteStatement deleteStmt = db.compileStatement(SQLConstants.SQL_DELETE_TRACKS + driverIds + ")");
        driversDeleted = deleteStmt.executeUpdateDelete();

        Log.d("Drivers deleted from db", String.valueOf(driversDeleted));
    }
}