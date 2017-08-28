package com.muilat.j_developers.utilities;

/**
 * Created by muilat on 14-Aug-17.
 */

public class Developer {

    //username of te developer
    String mUsername ;

    //developer image
    String mImageResource;

    //url tat leads to te developer profile on webrowser
    String mProfileUrl;

    Developer(String username, String imageResource, String profileUrl){
        mUsername = username;
        mImageResource = imageResource;
        mProfileUrl = profileUrl;
    }

    //return username
    public String getUsername(){
        return mUsername;
    }

    public String getImageResource(){
        return mImageResource;
    }

    public String getProfileUrl(){
        return mProfileUrl;
    }
}
