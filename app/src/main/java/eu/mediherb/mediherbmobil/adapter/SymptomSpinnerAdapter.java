package eu.mediherb.mediherbmobil.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import eu.mediherb.mediherbmobil.classes.gson.Symptom;

public class SymptomSpinnerAdapter extends ArrayAdapter<Symptom> {
    private Context context;
    private Symptom[] symptoms;

    public SymptomSpinnerAdapter(@NonNull Context context, int resource, Symptom[] symptoms) {
        super(context, resource, symptoms);
        this.context = context;
        this.symptoms = symptoms;
    }

    @Override
    public int getCount(){
        return symptoms.length;
    }

    @Override
    public Symptom getItem(int position){
        return symptoms[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(symptoms[position].getSymptom());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(symptoms[position].getSymptom());

        return label;
    }
}

