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
import com.smartcards.util.HTTPHelper;

/**
 * AsyncTask klasa AuthenticateTask koja POST-om izvršava login za korisnika.
 * U slučaju da ne uspe login, vraća poruku o grešci.
 */ 
public class AuthenticateTask extends AsyncTask<Void, Boolean, Boolean> {

    public LoginActivity activity;

    private String username = "";
    
    private String pass = "";

    /**
     * Konstruktor koji prima podatke
     *
     * @param activity the activity
     * @param username the username
     * @param password the password
     */
    public AuthenticateTask(LoginActivity activity, String username, String password) {
	this.activity = activity;
	this.username = username;
	this.pass = password;
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected Boolean doInBackground(Void... params) {

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

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(final Boolean success) {

	activity.showProgress(false);
	activity.mAuthTask = null;

	if (success) {
		
		Intent intent = new Intent(activity, AllSubjectsActivity.class);
	        activity.startActivity(intent);

	    if (activity.saveCheck.isChecked()) {
		activity.getSharedPrefs().getEditor().putString("username", activity.mUsernameView.getText().toString());
		activity.getSharedPrefs().getEditor().putString("password", activity.mPasswordView.getText().toString());
		activity.getSharedPrefs().getEditor().commit();
	    }
	} else {
	    activity.mUsernameView.setError(activity.getString(R.string.error_username_or_password));
	    activity.mUsernameView.requestFocus();
	}
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onCancelled()
     */
    @Override
    protected void onCancelled() {
	activity.mAuthTask = null;
	activity.showProgress(false);
    }

}
