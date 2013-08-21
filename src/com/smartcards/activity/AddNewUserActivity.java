package com.smartcards.activity;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartcards.entities.User;
import com.smartcards.tasks.CreateNewUserTask;
import com.smartcards.util.SharedPrefs;
import com.smartcards.util.SoundAndVibration;

/**
 * Klasa AddNewUserActivity koja dodaje novog korisnika.
 */
public class AddNewUserActivity extends Activity {

    private EditText firstname;
    
    private EditText lastname;
    
    private EditText username;
    
    private EditText password;
    
    private EditText passwordAgain;
    
    private EditText email;
    
    private DatePicker birthday;
    
    private Button create;
    
    public Dialog dialog = null;

    private SharedPrefs sharedPrefs;
    
    private SoundAndVibration soundAndVibra;

    private CreateNewUserTask createNewUserTask;
    
    private User user = new User();

    /**
     * Metoda koja kreira activity.
     * Instanciraju se objekti za kontrolu zvuka, vibracije i komponenti za unos i prikaz.
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_add_new_user);
	// Show the Up button in the action bar.
	setupActionBar();

	sharedPrefs = new SharedPrefs(this);
	soundAndVibra = new SoundAndVibration(sharedPrefs, this);

	dialog = new Dialog(this, R.style.custom_dialog);
	prepDialog();

	firstname = (EditText) findViewById(R.id.new_user_firstname);
	lastname = (EditText) findViewById(R.id.new_user_lastname);
	username = (EditText) findViewById(R.id.new_user_username);
	password = (EditText) findViewById(R.id.new_user_password);
	passwordAgain = (EditText) findViewById(R.id.new_user_repeat_password);
	email = (EditText) findViewById(R.id.new_user_email);
	birthday = (DatePicker) findViewById(R.id.new_user_birthday);
	create = (Button) findViewById(R.id.new_user_create);
	
	create.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {

		if (!checkForErrors()) {

		    user.setFirstname(firstname.getText().toString());
		    user.setLastname(lastname.getText().toString());
		    user.setUsername(username.getText().toString());
		    user.setPassword(password.getText().toString());
		    user.setEmail(email.getText().toString());
		    user.setRoleType(1);
		    user.setBirthday(getDateFromDatePicket(birthday));

		    try {
			createNewUserTask = new CreateNewUserTask(AddNewUserActivity.this, user);

			if (createNewUserTask.execute((Void) null).get().equals("true")) {
			    dialog.show();
			}

		    } catch (InterruptedException e) {
			e.printStackTrace();
		    } catch (ExecutionException e) {
			e.printStackTrace();
		    }
		}

	    }
	});

	firstname.setOnFocusChangeListener(new OnFocusChangeListener() {
	    @Override
	    public void onFocusChange(View v, boolean hasFocus) {
		firstname.setError(null);
	    }
	});

	lastname.setOnFocusChangeListener(new OnFocusChangeListener() {
	    @Override
	    public void onFocusChange(View v, boolean hasFocus) {
		lastname.setError(null);
	    }
	});

	username.setOnFocusChangeListener(new OnFocusChangeListener() {
	    @Override
	    public void onFocusChange(View v, boolean hasFocus) {
		username.setError(null);
	    }
	});

	password.setOnFocusChangeListener(new OnFocusChangeListener() {
	    @Override
	    public void onFocusChange(View v, boolean hasFocus) {
		password.setError(null);
	    }
	});

	passwordAgain.setOnFocusChangeListener(new OnFocusChangeListener() {
	    @Override
	    public void onFocusChange(View v, boolean hasFocus) {
		passwordAgain.setError(null);
	    }
	});

	email.setOnFocusChangeListener(new OnFocusChangeListener() {
	    @Override
	    public void onFocusChange(View v, boolean hasFocus) {
		email.setError(null);
	    }
	});

    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {

	getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.add_new_user, menu);
	return true;
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	    case android.R.id.home:
		// This ID represents the Home or Up button. In the case of this
		// activity, the Up button is shown. Use NavUtils to allow users
		// to navigate up one level in the application structure. For
		// more details, see the Navigation pattern on Android Design:
		//
		// http://developer.android.com/design/patterns/navigation.html#up-vs-back
		//
		NavUtils.navigateUpFromSameTask(this);
		return true;
	}
	return super.onOptionsItemSelected(item);
    }

    /**
     * Metoda koja radi validaciju.
     *
     * @return true, if successful
     */
    private boolean checkForErrors() {

	boolean error = false;

	if (username.getText().length() < 6) {
	    username.setError(getResources().getString(R.string.new_user_username_error));
	    error = true;
	}

	if (password.getText().length() < 6) {
	    password.setError(getResources().getString(R.string.new_user_username_error));
	    error = true;
	}

	if (!passwordAgain.getText().toString().equals(password.getText().toString())) {
	    passwordAgain.setError(getResources().getString(R.string.new_user_again_password_error));
	    error = true;
	}

	if (!isEmailValid(email.getText().toString())) {
	    email.setError(getResources().getString(R.string.new_user_email_error));
	    error = true;
	}

	if (firstname.getText().length() < 1) {
	    firstname.setError(getResources().getString(R.string.new_user_firstname_error));
	    error = true;
	}

	if (lastname.getText().length() < 1) {
	    lastname.setError(getResources().getString(R.string.new_user_lastname_error));
	    error = true;
	}

	return error;
    }

    /**
     * Metoda koja validira email.
     *
     * @param email the email
     * @return true, if is email valid
     */
    public boolean isEmailValid(String email) {
	boolean isValid = false;

	String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	CharSequence inputStr = email;

	Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	Matcher matcher = pattern.matcher(inputStr);
	if (matcher.matches()) {
	    isValid = true;
	}
	return isValid;
    }

    /**
     * Metoda koja pretvara vrednost Date picker-a u pravi Date objekat.
     *
     * @param datePicker the date picker
     * @return the date from date picket
     */
    public Date getDateFromDatePicket(DatePicker datePicker) {
	int day = datePicker.getDayOfMonth();
	int month = datePicker.getMonth();
	int year = datePicker.getYear();

	Calendar calendar = Calendar.getInstance();
	calendar.set(year, month, day);

	return calendar.getTime();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {

	soundAndVibra = new SoundAndVibration(sharedPrefs, this);

	if (dialog.isShowing()) {
	    dialog.dismiss();
	}

	super.onResume();
    }

    /**
     * Metoda koja priprema dijalog za prikazivanje. Postavlja se osnovni layout
     * i tekstovi za potrebne TextView-e
     */
    public void prepDialog() {

	dialog.setContentView(R.layout.custom_dialog_create);
	dialog.setTitle(R.string.dialog_create_title);

	// set the custom dialog components - text, image and button
	TextView text = (TextView) dialog.findViewById(R.id.dialog_create_text);
	text.setText(R.string.dialog_create_text);

	ImageView image = (ImageView) dialog.findViewById(R.id.dialog_create_image);
	image.setImageResource(R.drawable.warning);

	Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialog_create_button_ok);
	// if button is clicked, close the custom dialog
	dialogButtonOK.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		soundAndVibra.playSoundAndVibra();
		dialog.dismiss();
		finish();
	    }
	});

    }

}
