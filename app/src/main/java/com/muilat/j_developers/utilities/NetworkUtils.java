/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.muilat.j_developers.utilities;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.muilat.j_developers.MainActivity;

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
    /* The number of developers we want our API to return */
    private static final int numDays = 30;

    final static String QUERY_PARAM = "q";


    /**
     * Builds the URL used to talk to the weather server using a location. This location is based
     * on the query capabilities of the weather provider that we are using.
     *
     * @return The URL to use to query the github server.
     */
    public static URL buildUrl(String queryString) {
        Log.v(TAG, "Built Strin " + queryString);
        Github_url_string += queryString;

        Uri builtUri = Uri.parse(Github_url_string).buildUpon()
//                .appendQueryParameter(QUERY_PARAM, queryString)

                .build();

        Github_REQUEST_URL = null;
        try {
            Github_REQUEST_URL = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + Github_REQUEST_URL);
//        Toast.makeText(getContext(), "jg jfg", Toast.LENGTH_SHORT).show();
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