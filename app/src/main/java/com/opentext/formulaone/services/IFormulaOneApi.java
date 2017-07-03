package com.opentext.formulaone.services;

import com.opentext.formulaone.beans.FormulaOneResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IFormulaOneApi {
    @GET("drivers.json")
    Call<FormulaOneResponse> getDrivers(@Query("limit") int limit, @Query("offset") int offset);
}
