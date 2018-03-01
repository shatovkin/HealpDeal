package com.example.dimitri.helpdeal.azureClasses.filterAdapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dimitri.helpdeal.R;


public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] profession;
    private final Integer[] imgid;
    private final String[] names;
    private final String[] firstnames;
    private final String[] experiences;

    public CustomListAdapter(Activity context, String[] profession, Integer[] imgid, String[] names, String[] firstnames, String[] experiences) {
        super(context, R.layout.list_categoryeins, profession);
        this.context=context;
        this.profession=profession;
        this.imgid=imgid;
        this.names = names;
        this.firstnames = firstnames;
        this.experiences = experiences;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_categoryeins, null,true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.iconIVcategoryEins);
        TextView beruf = (TextView) rowView.findViewById(R.id.Profession);
        TextView name = (TextView) rowView.findViewById(R.id.Name);
        TextView firstname = (TextView) rowView.findViewById(R.id.Firstname);
        TextView expirience = (TextView) rowView.findViewById(R.id.Price);

        beruf.setText("Beruf: "+ profession[position]);
        name.setText("Name: "+ names[position]);
        firstname.setText("Vorname: "+ firstnames[position]);
        expirience.setText("Preis: "+experiences[position] + "€");
        imageView.setImageResource(imgid[position]);

        return rowView;
    };
}