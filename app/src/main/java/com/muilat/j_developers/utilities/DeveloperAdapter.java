package com.muilat.j_developers.utilities;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import com.bumptech.glide.*;
import com.muilat.j_developers.R;

/**
 * Created by my computer on 14-Aug-17.
 */

public class DeveloperAdapter extends ArrayAdapter<Developer> {

    /**
     * @param context The current context. Used to inflate the layout file.
     * @param developers A List of Word objects to display in a list
     */
    public DeveloperAdapter(Activity context, List<Developer> developers){
        // Here, I initialize the ArrayAdapter's internal storage for the context and the list.

        super(context, 0, developers);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The AdapterView position that is requesting a view
     * @param convertView The recycled view to populate.
     *                    (search online for "android view recycling" to learn more)
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Gets the Word object from the ArrayAdapter at the appropriate position
        Developer developer = getItem(position);

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_developer, parent, false);
        }

        TextView username = (TextView) convertView.findViewById(R.id.username);
        ImageView profile_photo = (ImageView)convertView.findViewById(R.id.profile_photo);

        username.setText(developer.getUsername());


        Glide.with(getContext())
                .load(developer.getImageResource())
                .into(profile_photo);



        return convertView;

    }
}
