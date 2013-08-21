package com.smartcards.tasks;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.AsyncTask;

import com.smartcards.util.HTTPHelper;

/**
 * AsyncTask klasa GetCardsTask koja POST-om poziva servis za dobijanje liste svih kartica za određenu kategoriju.
 */
public class GetCardsTask extends AsyncTask<Void, Boolean, String> {

    public Activity activity;
    
    public long subjectID;
    
    /**
     * Konstruktor koji prima podatke.
     *
     * @param activity the activity
     * @param subjectID the subject id
     */
    public GetCardsTask(Activity activity, long subjectID) {
        this.activity = activity;
        this.subjectID = subjectID;
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected String doInBackground(Void... params) {
        
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("subjectID", Long.toString(subjectID)));
        
        HTTPHelper httpHelper = new HTTPHelper("hostURL", "getCardsMethod", nameValuePairs, activity);
        return httpHelper.executePOSTHelper();
    }

}
