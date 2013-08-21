package com.smartcards.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.smartcards.adapters.CustomListAdapter;
import com.smartcards.entities.Card;
import com.smartcards.tasks.GetCardsTask;
import com.smartcards.util.SharedPrefs;
import com.smartcards.util.SoundAndVibration;

/**
 * Klasa InfoActivity koja prikazuje info activity.
 */
public class CardListActivity extends ListActivity {

    CustomListAdapter adapter;

    private String jsonCards;
    
    private long subjectID;
    
    private String subjectName;

    private SharedPrefs sharedPrefs;
    
    private SoundAndVibration soundAndVibra;

    List<Card> cardsList = new ArrayList<Card>();

    List<String> cardQuestions = new ArrayList<String>();
    
    List<Integer> drawableLeft = new ArrayList<Integer>();

    /**
     * Metoda koja kreira activity.
     * Instanciraju se objekti za kontrolu zvuka, vibracije i komponenti za unos i prikaz.
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_subjects);
        
        subjectID = getIntent().getLongExtra("subjectID", -1L);
        subjectName = getIntent().getStringExtra("subjectName");    
        setTitle(subjectName);

        GetCardsTask getCardsTask = new GetCardsTask(this, subjectID);
        try {
            jsonCards = getCardsTask.execute((Void) null).get();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        getSubjectsFromJSON();
        initializeList();
        sharedPrefs = new SharedPrefs(this);
        soundAndVibra = new SoundAndVibration(sharedPrefs, this);
    }

    /**
     * Metoda koja parsira JSON reprezentacije u objekte.
     *
     * @return the subjects from json
     */
    private void getSubjectsFromJSON() {

        JSONArray jArray = null;
        cardsList.clear();
        cardQuestions.clear();
        drawableLeft.clear();

        try {

            jArray = new JSONArray(jsonCards);

            for (int i = 0; i < jArray.length(); i++) {

                JSONObject json_data;
                
                json_data = jArray.getJSONObject(i);
                Card card_single = new Card(json_data.getLong("cardID"), json_data.getString("cardQuestion"), json_data.getString("cardAnswer"), json_data.getInt("cardRatingTotal"),
                        json_data.getInt("cardNumRaters"), json_data.getInt("cardStatus"));

                cardsList.add(card_single);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Metoda koja inicijalizuje listu svih kratica.
     */
    public void initializeList() {

        for (Card card : cardsList) {
            cardQuestions.add(card.getCardQuestion());
            drawableLeft.add(R.drawable.logo);
        }

        adapter = new CustomListAdapter(this, cardQuestions, drawableLeft);
        setListAdapter(adapter);

        ListView list = getListView();
        ColorDrawable blue = new ColorDrawable(this.getResources().getColor(R.color.red));
        list.setDivider(blue);
        list.setDividerHeight(1);
        list.setBackgroundColor(getResources().getColor(R.color.sand));

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        GetCardsTask getCardsTask = new GetCardsTask(this, subjectID);
        Log.d("SUBJECT ID", Long.toString(subjectID));
        try {
            jsonCards = getCardsTask.execute((Void) null).get();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        getSubjectsFromJSON();
        initializeList();
        soundAndVibra = new SoundAndVibration(sharedPrefs, this);
        super.onResume();
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                soundAndVibra.playSoundAndVibra();
                onBackPressed();
        }
        return true;
    }

    /* (non-Javadoc)
     * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        soundAndVibra.playSoundAndVibra();

        Card card = cardsList.get(position);

         Intent intent = new Intent(this, CardFlipActivity.class);
         intent.putExtra("card", card);
         startActivity(intent);
        
        adapter.notifyDataSetChanged();

    }

}
