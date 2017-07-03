package com.opentext.formulaone.dao;

/**
 * Created by gasahu on 30-Jun-17.
 */

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.opentext.formulaone.beans.Driver;
import com.opentext.formulaone.utilities.FormulaOneConstants;
import com.opentext.formulaone.utilities.FormulaOneLibraryManager;
import com.opentext.formulaone.utilities.SQLConstants;

import java.util.Iterator;
import java.util.List;

class FormulaOneDBHelper extends SQLiteOpenHelper {
    FormulaOneDBHelper(Context context) {
        super(context, FormulaOneContract.DATABASE_NAME, null, FormulaOneContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        SQLiteStatement insertStmt = null;
        List<Driver> driverList;
        Iterator<Driver> driverIterator;
        Driver driver;
        int c, rowsInserted = 0;

        try {
            //Creating table 'Driver'
            Log.d(FormulaOneConstants.LOG_TAG_SQL, SQLConstants.SQL_CREATE_DRIVER);
            db.execSQL(SQLConstants.SQL_CREATE_DRIVER);

            //Getting list of drivers from Ergast API
            FormulaOneLibraryManager manager = new FormulaOneLibraryManager();
            driverList = manager.getDriverDetails(100, 0);

            //Inserting drivers in table 'Driver'
            insertStmt = db.compileStatement(SQLConstants.SQL_INSERT_DRIVERS);

            if(driverList != null && !driverList.isEmpty()) {
                driverIterator = driverList.iterator();

                while (driverIterator.hasNext()) {
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
                        ++rowsInserted;
                        insertStmt.clearBindings();
                    } catch (SQLException sqle) {
                        Log.e(FormulaOneConstants.LOG_TAG_EXCEPTION, sqle.getMessage());
                    }
                }
            }

            Log.d("Drivers added to db", String.valueOf(rowsInserted));
        } catch(Exception e) {
            Log.e(FormulaOneConstants.LOG_TAG_EXCEPTION, e.getMessage());
        } finally {
            if(insertStmt != null) {
                insertStmt.close();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}