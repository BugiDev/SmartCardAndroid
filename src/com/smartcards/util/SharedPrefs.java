package com.smartcards.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Bogdan
 * Klasa SharedPrefs koja služi za čitanje i upis shared preferences-a 
 */
public class SharedPrefs {
	
	private Activity activity;

	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;

	/**
	 * Konstruktor koji prima podatke
	 * @param activity
	 * 
	 */
	public SharedPrefs(Activity activity) {
		this.setActivity(activity);
		setPreferences(activity.getSharedPreferences("smartCard_auth", Context.MODE_PRIVATE));
		setEditor(getPreferences().edit());
	}

	/**
	 * Standardni getter.
	 * @return preferences
	 */
	public SharedPreferences getPreferences() {
		return preferences;
	}

	/**
	 * Standardni setter.
	 * @param preferences
	 */
	public void setPreferences(SharedPreferences preferences) {
		this.preferences = preferences;
	}

	/**
	 * Standardni getter.
	 * @return editor
	 */
	public SharedPreferences.Editor getEditor() {
		return editor;
	}

	/**
	 * Standardni setter.
	 * @param editor
	 */
	public void setEditor(SharedPreferences.Editor editor) {
		this.editor = editor;
	}

	/**
	 * Standardni getter.
	 * @return activity
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * Standardni setter.
	 * @param activity
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

}
