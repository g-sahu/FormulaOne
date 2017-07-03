package com.opentext.formulaone.utilities;

/**
 * Created by gasahu on 30-Jun-17.
 */

import com.opentext.formulaone.dao.FormulaOneContract;

public final class SQLConstants {
    private static final String PRIMARY_KEY = " PRIMARY KEY ";
    private static final String NOT_NULL = " NOT NULL ";
    private static final String TEXT = " TEXT ";
    private static final String INTEGER = " INTEGER ";
    public static final String COMMA_SEP = ", ";
    public static final String DOUBLE_QUOTE = "\"";

    // Create tables
    public static final String SQL_CREATE_DRIVER =
            "CREATE TABLE " + FormulaOneContract.Driver.TABLE_NAME + " (" +
                    FormulaOneContract.Driver.DRIVER_ID + TEXT + NOT_NULL + PRIMARY_KEY + COMMA_SEP +
                    FormulaOneContract.Driver.GIVEN_NAME + TEXT + COMMA_SEP +
                    FormulaOneContract.Driver.FAMILY_NAME + TEXT + NOT_NULL + COMMA_SEP +
                    FormulaOneContract.Driver.DATE_OF_BIRTH + TEXT + COMMA_SEP +
                    FormulaOneContract.Driver.NATIONALITY + TEXT + COMMA_SEP +
                    FormulaOneContract.Driver.PERMANENT_NUMBER + INTEGER + COMMA_SEP +
                    FormulaOneContract.Driver.CODE +	TEXT + COMMA_SEP +
                    FormulaOneContract.Driver.URL + TEXT +
                    " )";

    //Insert queries
    public static final String SQL_INSERT_DRIVERS =
            "INSERT INTO " + FormulaOneContract.Driver.TABLE_NAME + "(" +
                    FormulaOneContract.Driver.DRIVER_ID + COMMA_SEP +
                    FormulaOneContract.Driver.GIVEN_NAME + COMMA_SEP +
                    FormulaOneContract.Driver.FAMILY_NAME + COMMA_SEP +
                    FormulaOneContract.Driver.DATE_OF_BIRTH + COMMA_SEP +
                    FormulaOneContract.Driver.NATIONALITY + COMMA_SEP +
                    FormulaOneContract.Driver.PERMANENT_NUMBER + COMMA_SEP +
                    FormulaOneContract.Driver.CODE + COMMA_SEP +
                    FormulaOneContract.Driver.URL +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    //Select queries
    public static final String SQL_SELECT_DRIVERS =
            "SELECT * FROM " + FormulaOneContract.Driver.TABLE_NAME;

    //Delete queries
    public static final String SQL_DELETE_TRACKS =
            "DELETE FROM " + FormulaOneContract.Driver.TABLE_NAME +
                    " WHERE " + FormulaOneContract.Driver.DRIVER_ID + " IN (";
}