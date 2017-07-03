package com.opentext.formulaone.utilities;

/**
 * Created by gasahu on 30-Jun-17.
 */

import android.content.Context;

import com.opentext.formulaone.beans.Driver;
import com.opentext.formulaone.beans.FormulaOneResponse;
import com.opentext.formulaone.dao.FormulaOneDAO;
import com.opentext.formulaone.services.FormulaOneApiClient;
import com.opentext.formulaone.services.IFormulaOneApi;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class FormulaOneLibraryManager {
    private static String TAG = FormulaOneLibraryManager.class.getSimpleName();
    private static List<Driver> driverList;
    private static int start, end;

    public static void init(Context context) {
        FormulaOneDAO dao = new FormulaOneDAO(context);

        if(driverList == null) {
            //Fetch driver details from database here
            driverList = dao.getDrivers();
        }

        dao.closeConnection();
    }

    public List<Driver> getDriverDetails(int limit, int offset) {
        IFormulaOneApi apiService = FormulaOneApiClient.getClient().create(IFormulaOneApi.class);
        Call<FormulaOneResponse> call = apiService.getDrivers(limit, offset);

        try {
            Response<FormulaOneResponse> response = call.execute();
            driverList = response.body().getMrdata().getDriverTable().getDriverList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return driverList;
    }

    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<Driver> driverList) {
        FormulaOneLibraryManager.driverList = driverList;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        FormulaOneLibraryManager.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        FormulaOneLibraryManager.end = end;
    }
}