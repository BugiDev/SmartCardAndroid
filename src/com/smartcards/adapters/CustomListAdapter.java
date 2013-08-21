package com.smartcards.adapters;

import java.util.List;

import com.smartcards.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Klasa CustomListAdapter koja se koristi pri poluaciji liste.
 */
public class CustomListAdapter extends ArrayAdapter<String> {

    private final Context context;

    private final List<String> values;

    private List<Integer> drawableIntLeft;

    /**
     * Konstruktor koji prima podatke
     * 
     * @param context
     *            the context
     * @param values
     *            the values
     * @param drawableIntLeft
     *            the drawable int left
     */
    public CustomListAdapter(Context context, List<String> values, List<Integer> drawableIntLeft) {
        super(context, R.layout.custom_list_row, values);
        this.context = context;
        this.values = values;
        this.setDrawableIntLeft(drawableIntLeft);
    }

    /**
     * 
     * Metoda koja za svaki red u listi kreira novi View i u njega stavlja sliku i tekst.
     * 
     * @see android.widget.ArrayAdapter#getView(int, android.view.View,
     *      android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        parent.setBackgroundResource(R.drawable.back);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_list_row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.list_subject_text);
        ImageView imageViewLeft = (ImageView) rowView.findViewById(R.id.list_subject_icon_left);

        textView.setText(getValues().get(position));
        imageViewLeft.setImageResource(getDrawableIntLeft().get(position));

        return rowView;
    }

    /**
     * Gets the drawable int left.
     * 
     * @return the drawable int left
     */
    public List<Integer> getDrawableIntLeft() {
        return drawableIntLeft;
    }

    /**
     * Sets the drawable int left.
     * 
     * @param drawableIntLeft
     *            the new drawable int left
     */
    public void setDrawableIntLeft(List<Integer> drawableIntLeft) {
        this.drawableIntLeft = drawableIntLeft;
    }

    /**
     * Gets the values.
     * 
     * @return the values
     */
    public List<String> getValues() {
        return values;
    }

}
