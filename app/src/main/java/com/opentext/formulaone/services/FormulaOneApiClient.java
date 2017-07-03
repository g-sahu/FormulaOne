package com.opentext.formulaone.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormulaOneApiClient {
    private static final String BASE_URL = "http://ergast.com/api/f1/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
