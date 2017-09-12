package com.muilat.j_developers.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.muilat.j_developers.MainActivity;
import com.muilat.j_developers.data.JDeveloperPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by my computer on 14-Aug-17.
 */

public class DeveloperLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<Developer>> {

    Toast toast;

//    URL USGS_REQUEST_URL = MainActivity.Github_REQUEST_URL;

    //to hold the list of already fetched items
    // so there wouldnt be the need to query anotertime
     public static ArrayList<Developer> fetched_developers = null;



    public DeveloperLoader(Context context) {
        super(context);

    }

    @Override
    public ArrayList<Developer> loadInBackground() {

        if(fetched_developers == null) {

            String location = JDeveloperPreferences
                    .getPreferredDeveloperLocation(getContext());
            String language = JDeveloperPreferences
                    .getPreferredDeveloperLanguage(getContext());
            String requestString = "location:"+location+"+language:"+language;
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

            return developers;
        }

        else {
            return fetched_developers;
        }
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
//parsing a json response
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
//                JSONObject properties = currentDeveloper.getJSONObject("properties");

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

        fetched_developers = developers;

        return developers;
    }


}


