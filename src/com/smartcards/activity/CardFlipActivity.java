/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.smartcards.activity;

import java.util.concurrent.ExecutionException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.smartcards.entities.Card;
import com.smartcards.tasks.RateCardTask;
import com.smartcards.util.SharedPrefs;
import com.smartcards.util.SoundAndVibration;

/**
 * Klasa CardFlipActivity koja prikazuje animaciju kartica.
 */
public class CardFlipActivity extends Activity implements FragmentManager.OnBackStackChangedListener {

    private Handler mHandler = new Handler();

    private static final int SWIPE_THRESHOLD = 100;
    
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private SharedPrefs sharedPrefs;
    
    private SoundAndVibration soundAndVibra;

    private boolean lastSwipeDown = false;

    static Card card;

    public Dialog dialog = null;

    private Menu menu;

    private float totalRating;
    
    private float ratingValue;

    private RateCardTask rateCardTask;

    private boolean mShowingBack = false;

    /**
     * Metoda koja kreira activity.
     * Instanciraju se objekti za kontrolu zvuka, vibracije i komponenti za unos i prikaz.
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_flip);

        sharedPrefs = new SharedPrefs(this);
        soundAndVibra = new SoundAndVibration(sharedPrefs, this);

        dialog = new Dialog(this, R.style.custom_dialog);
        prepDialog();

        card = getIntent().getParcelableExtra("card");

        if (card.getCardNumRaters() == 0) {
            totalRating = 0.0f;
        } else {
            totalRating = card.getCardRatingTotal() / card.getCardNumRaters();
        }

        if (savedInstanceState == null) {
            // If there is no saved instance state, add a fragment representing
            // the
            // front of the card to this activity. If there is saved instance
            // state,
            // this fragment will have already been added to the activity.
            getFragmentManager().beginTransaction().add(R.id.container, new CardFrontFragment()).commit();
        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }

        // Monitor back stack changes to ensure the action bar shows the
        // appropriate
        // button (either "photo" or "info").
        getFragmentManager().addOnBackStackChangedListener(this);

    }

    /** The my simple gesture listener. */
    SimpleOnGestureListener mySimpleGestureListener = new SimpleOnGestureListener() {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) < Math.abs(diffY)) {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            if (!lastSwipeDown) {
                                flipCard();
                                lastSwipeDown = true;
                                setTitle(R.string.title_card_answer);

                            }
                        } else {
                            if (lastSwipeDown) {
                                flipCard();
                                lastSwipeDown = false;
                                setTitle(R.string.title_card_question);
                            }
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    };

    /** The my gesture detector. */
    @SuppressWarnings("deprecation")
    GestureDetector myGestureDetector = new GestureDetector(mySimpleGestureListener);

    /* (non-Javadoc)
     * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (myGestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.card_flip, menu);
        this.menu = menu;
        MenuItem rateItem = menu.findItem(R.id.action_rate);
        rateItem.setTitle(getResources().getString(R.string.action_rate) + ": " + Float.toString(totalRating) + "/5");
        return true;
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setTitle(R.string.title_card_question);
                soundAndVibra.playSoundAndVibra();
                onBackPressed();
                return true;
            case R.id.action_rate:
                dialog.show();
                return true;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
        setTitle(R.string.title_card_question);
        soundAndVibra.playSoundAndVibra();
        lastSwipeDown = false;
        super.onBackPressed();
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
     * Metoda kojom se okreću kartice.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }

        // Flip to the back.

        mShowingBack = true;

        // Create and commit a new fragment transaction that adds the fragment
        // for the back of
        // the card, uses custom animations, and is part of the fragment
        // manager's back stack.

        getFragmentManager().beginTransaction()

        // Replace the default fragment animations with animator resources
        // representing
        // rotations when switching to the back of the card, as well as animator
        // resources representing rotations when flipping back to the front
        // (e.g. when
        // the system Back button is pressed).
                .setCustomAnimations(R.anim.card_flip_down_in, R.anim.card_flip_down_out, R.anim.card_flip_up_in, R.anim.card_flip_up_out)

                // Replace any fragments currently in the container view with a
                // fragment
                // representing the next page (indicated by the just-incremented
                // currentPage
                // variable).
                .replace(R.id.container, new CardBackFragment())

                // Add this transaction to the back stack, allowing users to
                // press Back
                // to get to the front of the card.
                .addToBackStack(null)

                // Commit the transaction.
                .commit();

        // Defer an invalidation of the options menu (on modern devices, the
        // action bar). This
        // can't be done immediately because the transaction may not yet be
        // committed. Commits
        // are asynchronous in that they are posted to the main thread's message
        // loop.
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                invalidateOptionsMenu();
            }
        });
    }

    /* (non-Javadoc)
     * @see android.app.FragmentManager.OnBackStackChangedListener#onBackStackChanged()
     */
    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);

        // When the back stack changes, invalidate the options menu (action
        // bar).
        invalidateOptionsMenu();
    }

    /**
     * A fragment representing the front of the card.
     */
    public static class CardFrontFragment extends Fragment {
        
        /**
         * Instantiates a new card front fragment.
         */
        public CardFrontFragment() {
        }

        /* (non-Javadoc)
         * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_card_front, container, false);
            TextView cardQuestion = (TextView) view.findViewById(R.id.card_question);
            cardQuestion.setText(card.getCardQuestion());
            return view;

        }
    }

    /**
     * A fragment representing the back of the card.
     */
    public static class CardBackFragment extends Fragment {
        
        /**
         * Instantiates a new card back fragment.
         */
        public CardBackFragment() {
        }

        /* (non-Javadoc)
         * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_card_back, container, false);
            TextView cardAnswer = (TextView) view.findViewById(R.id.card_answer);
            cardAnswer.setText(card.getCardAnswer());
            return view;
        }
    }

    /**
     * Metoda koja priprema dijalog za prikazivanje. Postavlja se osnovni layout
     * i tekstovi za potrebne TextView-e
     */
    public void prepDialog() {

        dialog.setContentView(R.layout.custom_dialog_rating);
        dialog.setTitle(R.string.dialog_rate_title);

        RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.dialog_rating_bar);

        ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                card.setCardRatingTotal(card.getCardRatingTotal() + ratingBar.getRating());
                card.setCardNumRaters(card.getCardNumRaters() + 1);
                if (card.getCardNumRaters() == 0) {
                    totalRating = 0.0f;
                } else {
                    totalRating = card.getCardRatingTotal() / card.getCardNumRaters();
                }
                ratingValue = ratingBar.getRating();
            }
        });

        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialog_rating_button_ok);
        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                soundAndVibra.playSoundAndVibra();
                dialog.dismiss();

                MenuItem rateItem = menu.findItem(R.id.action_rate);
                rateItem.setTitle(getResources().getString(R.string.action_rate) + ": " + Float.toString(totalRating) + "/5");

                rateCardTask = new RateCardTask(CardFlipActivity.this, card.getCardID(), ratingValue);
                try {
                    rateCardTask.execute((Void) null).get();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        Button dialogButtonCancle = (Button) dialog.findViewById(R.id.dialog_rating_button_cancle);
        // if button is clicked, close the custom dialog
        dialogButtonCancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                soundAndVibra.playSoundAndVibra();
                dialog.dismiss();
            }
        });

    }

}
