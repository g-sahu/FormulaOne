package com.opentext.formulaone.services;

/**
 * Created by gasahu on 29-Jun-17.
 */

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.opentext.formulaone.utilities.FormulaOneLibraryManager;
import com.opentext.formulaone.utilities.FormulaOneConstants;

public class DriverDetailsService extends IntentService {

    public DriverDetailsService() {
        super("DriverDetailsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("DriverDetailsService", "Inside DriverDetailsService");

        //Initialising/updating Formula One library
        FormulaOneLibraryManager.init(this);

        //Sending broadcast indicating that DriverDetailsService has finished initiliasing/updating the library
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(FormulaOneConstants.ACTION_SERVICE_BROADCAST);
        sendBroadcast(broadcastIntent);
    }
}