package com.opentext.formulaone.dao;

/**
 * Created by gasahu on 30-Jun-17.
 */

import android.provider.BaseColumns;

public final class FormulaOneContract {
    static final String DATABASE_NAME = "FORMULA_ONE";
    static final int DATABASE_VERSION = 1;

    public static abstract class Driver implements BaseColumns {
        public static final String TABLE_NAME = "driver";
        public static final String DRIVER_ID = "driver_id";
        public static final String GIVEN_NAME = "given_name";
        public static final String FAMILY_NAME = "family_name";
        public static final String DATE_OF_BIRTH = "dateOfBirth";
        public static final String NATIONALITY = "nationality";
        public static final String PERMANENT_NUMBER = "permanent_number";
        public static final String CODE = "driver_code";
        public static final String URL = "url";
    }
}
