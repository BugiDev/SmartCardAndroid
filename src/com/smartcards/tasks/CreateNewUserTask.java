package com.smartcards.tasks;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.AsyncTask;

import com.smartcards.entities.User;
import com.smartcards.util.HTTPHelper;

/**
 * AsyncTask klasa CreateNewUserTask koja POST-om poziva servis za novog korisnika.
 */
public class CreateNewUserTask extends AsyncTask<Void, Boolean, String> {

    public Activity activity;
    
    public User user;
    
    /**
     * Konstruktor koji prima podatke
     *
     * @param activity the activity
     * @param user the user
     */
    public CreateNewUserTask(Activity activity, User user) {
        this.activity = activity;
        this.user = user;
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected String doInBackground(Void... params) {
        
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username", user.getUsername()));
        nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));
        nameValuePairs.add(new BasicNameValuePair("email", user.getEmail()));
        nameValuePairs.add(new BasicNameValuePair("firstname", user.getFirstname()));
        nameValuePairs.add(new BasicNameValuePair("lastname", user.getLastname()));
        nameValuePairs.add(new BasicNameValuePair("birthday", Long.toString(user.getBirthday().getTime())));
        nameValuePairs.add(new BasicNameValuePair("roletype", Integer.toString(user.getRoleType())));
        
        HTTPHelper httpHelper = new HTTPHelper("hostURL", "createNewUserMethod", nameValuePairs, activity);
        return httpHelper.executePOSTHelper();
    }

}
