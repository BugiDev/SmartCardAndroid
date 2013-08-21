package com.smartcards.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.smartcards.util.SharedPrefs;
import com.smartcards.util.SoundAndVibration;

/**
 * Klasa InfoActivity koja prikazuje info activity.
 */
public class InfoActivity extends Activity {

    TextView infoText;
    
    private SharedPrefs sharedPrefs;
    
    private SoundAndVibration soundAndVibra;

    /**
     * Metoda koja kreira activity.
     * Instanciraju se objekti za kontrolu zvuka, vibracije i komponenti za unos i prikaz.
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        // Show the Up button in the action bar.
        setupActionBar();
        
        sharedPrefs = new SharedPrefs(this);
        soundAndVibra = new SoundAndVibration(sharedPrefs, this);

        infoText = (TextView) findViewById(R.id.info_text);

        infoText.setMovementMethod(new ScrollingMovementMethod());

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        soundAndVibra = new SoundAndVibration(sharedPrefs, this);
        super.onResume();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /** 
     * Metoda koja na pritisak home dugmeta vraća korisnika na prethodni ekran.
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
                soundAndVibra.playSoundAndVibra();
                NavUtils.navigateUpFromSameTask(this);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
