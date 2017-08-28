package com.muilat.j_developers;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.*;
import com.muilat.j_developers.utilities.Developer;

public class ShowItemActivity extends AppCompatActivity {

    Developer developer = MainActivity.current_developer;

Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);

//        Intent callerActivity = getIntent();
//
//        if(callerIntent.hasExtra(Intent.EXTRA_TEXT)){
//            Developer developer  = callerIntent.getStringExtra(Intent.EXTRA_TEXT);
//        }
        ImageView profile_photo = (ImageView)findViewById(R.id.show_photo);
        TextView username = (TextView) findViewById(R.id.show_username);
        TextView url = (TextView) findViewById(R.id.show_url);

        url.setText(developer.getProfileUrl());
        username.setText(developer.getUsername());
//
//
        Glide.with(this)
                .load(developer.getImageResource())
                .into(profile_photo);
    }

    public void  shareProfileButton(View view){

        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText("Checkout this awesome developer @"+developer.getUsername()+", "+developer.getProfileUrl())
            .setChooserTitle("Share Developer with")
            .setSubject("Lagos Java Developer")
                .setType("text/plain")
                .createChooserIntent()
                ;

        if(shareIntent.resolveActivity(getPackageManager()) != null){
            startActivity(shareIntent);
        }
    }

    public  void launchInBrowser(View view){

        String url = developer.getProfileUrl();

        //parse te url string to uri using Uri.parse
        Uri webpage = Uri.parse(url);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, webpage);
        if(browserIntent.resolveActivity(getPackageManager()) != null){
            startActivity(browserIntent);
        }
    }

}
