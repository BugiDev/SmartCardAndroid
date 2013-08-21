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
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.smartcards.adapters.CustomListAdapter;
import com.smartcards.entities.Subject;
import com.smartcards.tasks.GetSubjectsTask;
import com.smartcards.util.SharedPrefs;
import com.smartcards.util.SoundAndVibration;

/**
 * Klasa AllSubjectsActivity koja prikazuje listu svih kategorija.
 */
public class AllSubjectsActivity extends ListActivity {

    CustomListAdapter adapter;

    private String jsonSubjects;

    private SharedPrefs sharedPrefs;

    private SoundAndVibration soundAndVibra;

    List<Subject> subjectList = new ArrayList<Subject>();

    List<String> allSubjects = new ArrayList<String>();
    
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

        GetSubjectsTask getSubjectTask = new GetSubjectsTask(this);
        try {
            jsonSubjects = getSubjectTask.execute((Void) null).get();
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
        subjectList.clear();
        allSubjects.clear();
        drawableLeft.clear();

        try {

            jArray = new JSONArray(jsonSubjects);

            for (int i = 0; i < jArray.length(); i++) {

                JSONObject json_data;

                json_data = jArray.getJSONObject(i);
                Subject subject_single = new Subject(json_data.getLong("subjectID"), json_data.getString("subjectName"), json_data.getBoolean("subjectDeleted"));

                subjectList.add(subject_single);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Metoda koja inicijalizuje listu svih kategorija.
     */
    public void initializeList() {

        for (Subject subject : subjectList) {
            allSubjects.add(subject.getSubjectName());
            drawableLeft.add(R.drawable.logo);
        }

        adapter = new CustomListAdapter(this, allSubjects, drawableLeft);
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
        GetSubjectsTask getSubjectTask = new GetSubjectsTask(this);
        try {
            jsonSubjects = getSubjectTask.execute((Void) null).get();
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
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
            case R.id.action_settings: {
                Intent intent1 = new Intent(this, SettingsActivity.class);
                startActivity(intent1);
                return true;
            }
            case R.id.action_about: {
                Intent intent2 = new Intent(this, InfoActivity.class);
                startActivity(intent2);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /* (non-Javadoc)
     * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        soundAndVibra.playSoundAndVibra();

        Subject subject = subjectList.get(position);

        Intent intent = new Intent(this, CardListActivity.class);
        intent.putExtra("subjectID", subject.getSubjectID());
        intent.putExtra("subjectName", subject.getSubjectName());
        startActivity(intent);

        adapter.notifyDataSetChanged();

    }

}
