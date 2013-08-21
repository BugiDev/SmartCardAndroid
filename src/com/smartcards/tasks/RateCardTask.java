package com.smartcards.tasks;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.AsyncTask;

import com.smartcards.util.HTTPHelper;

/**
 * AsyncTask klasa RateCardTask koja POST-om poziva servis za rate-ovanje kartica.
 */
public class RateCardTask extends AsyncTask<Void, Boolean, String> {

    public Activity activity;

    public long cardID;

    public float rating;
    
    /**
     * Konstruktor koji prima podatke
     *
     * @param activity the activity
     * @param cardID the card id
     * @param rating the rating
     */
    public RateCardTask(Activity activity, long cardID, float rating) {
        this.activity = activity;
        this.cardID = cardID;
        this.rating = rating;
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected String doInBackground(Void... params) {
        
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("cardID", Long.toString(cardID)));
        nameValuePairs.add(new BasicNameValuePair("rating", Float.toString(rating)));
        
        HTTPHelper httpHelper = new HTTPHelper("hostURL", "rateCardMethod", nameValuePairs, activity);
        return httpHelper.executePOSTHelper();
    }

}
