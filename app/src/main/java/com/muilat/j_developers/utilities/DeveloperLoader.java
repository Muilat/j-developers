/**
 * Created by my computer on 14-Aug-17.
 */
package com.muilat.j_developers.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.muilat.j_developers.MainActivity;
import com.muilat.j_developers.data.JDeveloperPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class DeveloperLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<Developer>> {


    String mSearchUsername = MainActivity.mSearchUsername;


    //default constructor for the loader
    public DeveloperLoader(Context context) {
        super(context);

    }


    /**
     * This is the method of the AsyncTaskLoader that will load and parse the JSON data
     * from github in the background.
     *
     * @return  developer data from githubApi as an array of Developers.
     *         null if an error occurs
     */
    @Override
    public ArrayList<Developer> loadInBackground() {

        String requestString = "";

            String location = JDeveloperPreferences
                    .getPreferredDeveloperLocation(getContext());
            String language = JDeveloperPreferences
                    .getPreferredDeveloperLanguage(getContext());

            requestString += "location:"+location+"+language:"+language;

            if(mSearchUsername != "") {
                requestString += "+" + mSearchUsername + "%20in:login";
            }

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                URL githubRequestUrl = NetworkUtils.buildUrl(requestString);
                jsonResponse = NetworkUtils.getResponseFromHttpUrl(githubRequestUrl);
            } catch (IOException e) {
                // TODO Handle the IOException
                Log.v("loadInBackground", "Unable to load in background");
            }


            // Extract relevant fields from the JSON response and create an {@link Event} object
            ArrayList<Developer> developers = extractDeveloper(jsonResponse);

            MainActivity.mSearchUsername = "";

            return developers;
//
    }

    /* this is requured to tell the loader to start doing the background load

     */
    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        forceLoad();
    }


    /*
    //return a list of{@link Developer} object that has been built up from
    * @param jsonResponse The json string parse.

    */
    public  ArrayList<Developer> extractDeveloper(String jsonResponse){

        //create an empty arraylist that we can start adding earthquake to
        ArrayList<Developer> developers = new ArrayList<>();

        /* try to parse the sample response, if there is an erroir
           //in the way it is array exception should be trhrown
         */
        try{
            //build up array of developers with the corresponding data

            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray developerArray = baseJsonResponse.getJSONArray("items");

            Log.v(developerArray.toString(),"Lets c");

            for(int i = 0; i < developerArray.length(); i++){
                JSONObject currentDeveloper = developerArray.getJSONObject(i);

                String username = currentDeveloper.getString("login");
                String photo = currentDeveloper.getString("avatar_url");
                String profile_url = currentDeveloper.getString("html_url");

                Developer developer = new Developer(username,photo,profile_url);

                developers.add(developer);

            }

        } catch (JSONException e){
            /* if any erroe is thrown when executing the try;
            //catch the excdeption so the app doesnt crash
             */
            Log.v("DeveloperLoader","problem parsing the developer JSON results", e);

        }


        return developers;
    }


}


