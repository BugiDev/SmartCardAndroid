package com.smartcards.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.smartcards.util.HTTPHelper;

// TODO: Auto-generated Javadoc
/**
 *  AsyncTask klasa GetSubjectsTask koja GET-om poziva servis za dobijanje liste svih kategorija.
 */
public class GetSubjectsTask extends AsyncTask<Void, Boolean, String> {

    public Activity activity;
    
    /**
     * Konstruktor koji prima podatke.
     *
     * @param activity the activity
     */
    public GetSubjectsTask(Activity activity) {
        this.activity = activity;
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected String doInBackground(Void... params) {
        
        HTTPHelper httpHelper = new HTTPHelper("hostURL", "getSubjectsMethod", activity);
        String jsonString = httpHelper.executeGETHelper();
            return jsonString;
    }

}
