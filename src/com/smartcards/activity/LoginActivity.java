package com.smartcards.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.smartcards.tasks.AuthenticateTask;
import com.smartcards.tasks.GetSubjectsTask;
import com.smartcards.util.SharedPrefs;
import com.smartcards.util.SoundAndVibration;

/**
 * Klasa LoginActivity koja prikazuje login activity.
 */
public class LoginActivity extends Activity {

    private SharedPrefs sharedPrefs;

    private SoundAndVibration soundAndVibra;

    public String mUsername;

    public String mPassword;

    public AuthenticateTask mAuthTask = null;

    public GetSubjectsTask getSubjectTask = null;

    public EditText mUsernameView;

    public EditText mPasswordView;

    public View mLoginFormView;

    public View mLoginStatusView;

    public TextView mLoginStatusMessageView;

    public CheckBox saveCheck;

    /**
     * Metoda koja kreira activity.
     * Instanciraju se objekti za kontrolu zvuka, vibracije i komponenti za unos i prikaz.
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_login);

	setSharedPrefs(new SharedPrefs(this));
	setSoundAndVibra(new SoundAndVibration(getSharedPrefs(), this));

	// Set up the login form.
	mUsernameView = (EditText) findViewById(R.id.username);
	mUsernameView.setText(mUsername);

	saveCheck = (CheckBox) findViewById(R.id.login_save_check);

	mPasswordView = (EditText) findViewById(R.id.password);
	mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	    @Override
	    public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
		if (id == R.id.login || id == EditorInfo.IME_NULL) {
		    attemptLogin();
		    return true;
		}
		return false;
	    }
	});

	mLoginFormView = findViewById(R.id.login_form);
	mLoginStatusView = findViewById(R.id.login_status);
	mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

	findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View view) {
		getSoundAndVibra().playSoundAndVibra();
		attemptLogin();
	    }
	});
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	super.onCreateOptionsMenu(menu);
	getMenuInflater().inflate(R.menu.login, menu);
	getActionBar().setDisplayHomeAsUpEnabled(true);
	return true;
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	    case android.R.id.home:
		getSoundAndVibra().playSoundAndVibra();
		finish();
		return true;
	    case R.id.action_sign_up:
		getSoundAndVibra().playSoundAndVibra();
		Intent intent2 = new Intent(this, AddNewUserActivity.class);
                startActivity(intent2);
		return true;
	    default:
		return super.onOptionsItemSelected(item);
	}
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
	if (mAuthTask != null) {
	    return;
	}

	// Reset errors.
	mUsernameView.setError(null);
	mPasswordView.setError(null);

	// Store values at the time of the login attempt.
	mUsername = mUsernameView.getText().toString();
	mPassword = mPasswordView.getText().toString();

	boolean cancel = false;
	View focusView = null;

	// Check for a valid password.
	if (TextUtils.isEmpty(mPassword)) {
	    mPasswordView.setError(getString(R.string.error_field_required));
	    focusView = mPasswordView;
	    cancel = true;
	} else if (mPassword.length() < 4) {
	    mPasswordView.setError(getString(R.string.error_invalid_password));
	    focusView = mPasswordView;
	    cancel = true;
	}

	// Check for a valid email address.
	if (TextUtils.isEmpty(mUsername)) {
	    mUsernameView.setError(getString(R.string.error_field_required));
	    focusView = mUsernameView;
	    cancel = true;
	} else if (mUsername.length() < 4) {
	    mUsernameView.setError(getString(R.string.error_invalid_username));
	    focusView = mUsernameView;
	    cancel = true;
	}

	if (cancel) {
	    // There was an error; don't attempt login and focus the first
	    // form field with an error.
	    focusView.requestFocus();
	} else {
	    // Show a progress spinner, and kick off a background task to
	    // perform the user login attempt.
	    mLoginStatusMessageView.setText(R.string.login_progress_logging_in);
	    showProgress(true);
	    mAuthTask = new AuthenticateTask(this, mUsernameView.getText().toString(), mPasswordView.getText().toString());
	    mAuthTask.execute((Void) null);
	}
    }

    /**
     * Shows the progress UI and hides the login form.
     *
     * @param show the show
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
	// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
	// for very easy animations. If available, use these APIs to fade-in
	// the progress spinner.
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
	    int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

	    mLoginStatusView.setVisibility(View.VISIBLE);
	    mLoginStatusView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
		@Override
		public void onAnimationEnd(Animator animation) {
		    mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
		}
	    });

	    mLoginFormView.setVisibility(View.VISIBLE);
	    mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
		@Override
		public void onAnimationEnd(Animator animation) {
		    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	    });
	} else {
	    // The ViewPropertyAnimator APIs are not available, so simply show
	    // and hide the relevant UI components.
	    mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
	    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
	}
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
	super.onBackPressed();
	finish();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
	setSoundAndVibra(new SoundAndVibration(getSharedPrefs(), this));
	super.onResume();
    }

    /**
     * Gets the shared prefs.
     *
     * @return the shared prefs
     */
    public SharedPrefs getSharedPrefs() {
	return sharedPrefs;
    }

    /**
     * Sets the shared prefs.
     *
     * @param sharedPrefs the new shared prefs
     */
    public void setSharedPrefs(SharedPrefs sharedPrefs) {
	this.sharedPrefs = sharedPrefs;
    }

    /**
     * Gets the sound and vibra.
     *
     * @return the sound and vibra
     */
    public SoundAndVibration getSoundAndVibra() {
	return soundAndVibra;
    }

    /**
     * Sets the sound and vibra.
     *
     * @param soundAndVibra the new sound and vibra
     */
    public void setSoundAndVibra(SoundAndVibration soundAndVibra) {
	this.soundAndVibra = soundAndVibra;
    }
}
