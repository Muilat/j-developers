package com.muilat.j_developers.utilities;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the github (api) servers.
 */
public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    static String Github_url_string = "https://api.github.com/search/users?q=";


    private static URL Github_REQUEST_URL;



    /* The location we want our API to return */
    private static final String location = "lagos";
    /* The language we want our API to return */
    private static final String language = "java";


    /**
     * Builds the URL used to talk to the github
     *
     * @return The URL to use to query the github server.
     */
    public static URL buildUrl(String queryString) {
        Log.v(TAG, "Built Query String " + queryString);
        String Github_url_string_with_query = Github_url_string + queryString;

        Uri builtUri = Uri.parse(Github_url_string_with_query).buildUpon()
//                .appendQueryParameter(QUERY_PARAM, queryString)

                .build();

        Github_REQUEST_URL = null;
        try {
            Github_REQUEST_URL = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + Github_REQUEST_URL);
        return Github_REQUEST_URL;
    }



    /**
     * This method returns the entire result from the HTTP response.
     *
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    @Nullable
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}