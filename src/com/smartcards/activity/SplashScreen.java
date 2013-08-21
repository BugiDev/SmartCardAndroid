package com.smartcards.activity;

import java.util.concurrent.ExecutionException;

import com.smartcards.tasks.AuthenticateFromPrefsTask;
import com.smartcards.tasks.CheckInternetTask;
import com.smartcards.util.SharedPrefs;
import com.smartcards.util.SoundAndVibration;

import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Klasa SplashScreen koja prikazuje splash screen activity.
 */
public class SplashScreen extends Activity {

    public Dialog dialog = null;

    private SharedPrefs sharedPrefs;

    private SoundAndVibration soundAndVibra;

    /**
     * Metoda koja kreira activity. Instanciraju se objekti za kontrolu zvuka,
     * vibracije i komponenti za unos i prikaz.
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        setSharedPrefs(new SharedPrefs(this));
        setSoundAndVibra(new SoundAndVibration(getSharedPrefs(), this));

        dialog = new Dialog(this, R.style.custom_dialog);
        prepDialog();

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    /**
     * Metoda koja priprema dijalog za prikazivanje. Postavlja se osnovni layout
     * i tekstovi za potrebne TextView-e
     */
    public void prepDialog() {

        dialog.setContentView(R.layout.custom_dialog_alert);
        dialog.setTitle(R.string.dialog_title_network);

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.dialog_alert_text);
        text.setText(R.string.dialog_text_network);

        ImageView image = (ImageView) dialog.findViewById(R.id.dialog_alert_image);
        image.setImageResource(R.drawable.warning);

        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialog_alert_button_ok);
        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getSoundAndVibra().playSoundAndVibra();
                dialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        });

        Button dialogButtonCancle = (Button) dialog.findViewById(R.id.dialog_alert_button_cancle);
        // if button is clicked, close the custom dialog
        dialogButtonCancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getSoundAndVibra().playSoundAndVibra();
                dialog.dismiss();
                finish();
            }
        });

    }

    /**
     * Metoda koja prikazuje splash screen. 
     * U pozadini se izvršava pokušaj autentikacije u slučaju da su prethodno sačuvani pristupni podaci.
     */
    public void showSplash() {

        CheckInternetTask checkInternet = new CheckInternetTask(this);

        try {
            if (checkInternet.execute((Void) null).get()) {
                AuthenticateFromPrefsTask authFromPrefs = new AuthenticateFromPrefsTask(this, getSharedPrefs().getPreferences().getString("username", null), getSharedPrefs().getPreferences()
                        .getString("password", null));
                authFromPrefs.execute((Void) null);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {

        setSoundAndVibra(new SoundAndVibration(getSharedPrefs(), this));

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        showSplash();
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
     * @return the soundAndVibra
     */
    public SoundAndVibration getSoundAndVibra() {
        return soundAndVibra;
    }

    /**
     * Sets the sound and vibra.
     *
     * @param soundAndVibra the soundAndVibra to set
     */
    public void setSoundAndVibra(SoundAndVibration soundAndVibra) {
        this.soundAndVibra = soundAndVibra;
    }

}
