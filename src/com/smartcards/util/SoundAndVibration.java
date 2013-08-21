package com.smartcards.util;

import com.smartcards.activity.R;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Vibrator;

/**
 * @author Bogdan
 * Klasa SoundAndVibration koja je zadužena za zvuk i vibraciju unutar aplikacije.
 */
public class SoundAndVibration {

	private Activity activity;

	private SoundPool soundPool;
	private int soundID;
	private boolean loaded = false;
	private Vibrator vibe;

	private SharedPrefs sharedPrefs;

	private boolean soundSettings;
	private boolean vibraSettings;

	/**
	 * Konstruktor koji prima podatke. 
	 * Instancira zvuk i vibraciju i uzima podešavanja.
	 * @param sharedPrefs
	 * @param activity
	 */
	public SoundAndVibration(SharedPrefs sharedPrefs, Activity activity) {
		this.setSharedPrefs(sharedPrefs);
		this.activity = activity;
		
		soundSettings = sharedPrefs.getPreferences().getBoolean("sound", true);
		vibraSettings = sharedPrefs.getPreferences().getBoolean("vibra", true);
		
		vibe = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				loaded = true;
			}
		});

		soundID = soundPool.load(activity, R.raw.button_click, 1);
		
	}
	
	/**
	 * Metoda koja pusta zvuk i izaziva vibraciju.
	 */
	public void playSoundAndVibra(){
		if (soundSettings) {
			fireSound();
		}

		if (vibraSettings) {
			vibe.vibrate(80);
		}
	}

	
	/**
	 * Metoda koja pusta zvuk
	 */
	public void fireSound() {
		// Getting the user sound settings
		AudioManager audioManager = (AudioManager) activity
				.getSystemService(Context.AUDIO_SERVICE);
		float actualVolume = (float) audioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = (float) audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = actualVolume / maxVolume;
		// Is the sound loaded already?
		if (loaded) {
			soundPool.play(soundID, volume, volume, 1, 0, 1f);
		}
	}

	
	/**
	 * Standardni getter.
	 * @return sharedPrefs
	 */
	public SharedPrefs getSharedPrefs() {
		return sharedPrefs;
	}

	
	/**
	 * Standardni setter.
	 * @param sharedPrefs
	 */
	public void setSharedPrefs(SharedPrefs sharedPrefs) {
		this.sharedPrefs = sharedPrefs;
	}

}
