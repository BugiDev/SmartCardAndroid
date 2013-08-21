package com.smartcards.tasks;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.smartcards.activity.SplashScreen;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

/**
 * AsyncTask klasa CheckInternetTask koja proverava da li uređaj ima pristup internetu.
 */
public class CheckInternetTask extends AsyncTask<Void, Void, Boolean> {

    /** The activity. */
    public SplashScreen activity;

    /**
     * Konstruktor koji prima podatke
     * 
     * @param activity
     *            the activity
     */
    public CheckInternetTask(SplashScreen activity) {
        this.activity = activity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected Boolean doInBackground(Void... params) {

        if (hasActiveInternetConnection()) {
            return true;
        } else {
            return false;// false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(Boolean params) {

        if (!params) {
            activity.dialog.show();
        }
    }

    /**
     * Pošto postoji mogućnost da uređaj nema internet iako je prikačen na
     * mrežu, pingom na google.com moguće je utvrditi da li stvarno ima pristup
     * internetu.
     * 
     * @return true, if successful
     */
    public boolean hasActiveInternetConnection() {
        if (isNetworkAvailable()) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
            }
        } else {
            return false;// false;
        }
        return false;// false;
    }

    /**
     * Provera da li je uređaj prikačen na mrežu.
     * 
     * @return true, if is network available
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
