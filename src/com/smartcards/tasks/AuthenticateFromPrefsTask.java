package com.smartcards.tasks;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.AsyncTask;

import com.smartcards.activity.AllSubjectsActivity;
import com.smartcards.activity.LoginActivity;
import com.smartcards.activity.R;
import com.smartcards.activity.SplashScreen;
import com.smartcards.util.HTTPHelper;

/**
 * AsyncTask klasa AuthenticateFromPrefsTask koja POST-om izvršava login za
 * unapred sačuvanje podatke login-a. Ukoliko ima grešaka i loginu, odbacuju se
 * i korisnik se vodi na login stranu. Ukoliko je uspešan login, korisnik se
 * vodi na stranu sa svim kategorijama.
 */
public class AuthenticateFromPrefsTask extends AsyncTask<Void, Boolean, Boolean> {

    public SplashScreen activity;

    private String username = "";

    private String pass = "";

    /**
     * Konstruktor koji prima podatke
     * 
     * @param activity
     *            the activity
     * @param username
     *            the username
     * @param password
     *            the password
     */
    public AuthenticateFromPrefsTask(SplashScreen activity, String username, String password) {
        this.activity = activity;
        this.username = username;
        this.pass = password;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected Boolean doInBackground(Void... params) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("password", pass));

        HTTPHelper httpHelper = new HTTPHelper("hostURL", "authenticateMethod", nameValuePairs, activity);

        if (!httpHelper.executePOSTHelper().equalsIgnoreCase("-1")) {
            activity.getSharedPrefs().getEditor().putString("userID", httpHelper.executePOSTHelper());
            return true;
        } else {
            return false;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(final Boolean success) {

        activity.finish();

        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        if (success) {
            Intent intent = new Intent(activity, AllSubjectsActivity.class);
            activity.startActivity(intent);

        } else {
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.os.AsyncTask#onCancelled()
     */
    @Override
    protected void onCancelled() {
        activity.finish();
    }

}
