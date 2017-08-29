package com.muilat.j_developers;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.muilat.j_developers.utilities.Developer;
import com.muilat.j_developers.utilities.DeveloperAdapter;
import com.muilat.j_developers.utilities.DeveloperLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Developer>>{

//    String Github_url_string = "https://api.github.com/search/users?q=location:lagos+language:java";
//    static URL Github_REQUEST_URL;

    public static  Developer current_developer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isAvailable() &&  networkInfo.isConnected()){
            //network is available go get the data
            getSupportLoaderManager().initLoader(1, null, MainActivity.this);

        }
        else {

            //if there is already fetched_developers
            if (DeveloperLoader.fetched_developers == null) {
                //hide progress bar

                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.GONE);
                //internet is not available; make it known
                TextView empty_view = (TextView) findViewById(R.id.empty_view);
                //make it visible
                empty_view.setVisibility(View.VISIBLE);
                empty_view.setText("No internet connection");
                ListView listView = (ListView) findViewById(R.id.list_quake);
                listView.setEmptyView(findViewById(R.id.empty_view));

            }
        }
    }

    @Override
    public Loader<ArrayList<Developer>> onCreateLoader(int id, Bundle args) {

        return new DeveloperLoader(this);

    }

    /**

     * Update the screen with the given earthquake (which was the result of the
     * {@link DeveloperLoader}).
     */

    @Override
    public void onLoadFinished(Loader<ArrayList<Developer>> loader, ArrayList<Developer> data) {
        //hide the progressbar
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        updateUi(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Developer>> loader) {

        updateUi(new ArrayList<Developer>());
    }








    //update the ui after
    private void updateUi(final ArrayList<Developer> developers) {



        ListView listView = (ListView) findViewById(R.id.list_quake);

        DeveloperAdapter developerAdapter = new DeveloperAdapter(this,developers);
        listView.setAdapter(developerAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //use the position of the item clicked on the listview to
                // get the corresponding url from the developer list
                Developer developer = developers.get(position);

                current_developer = developer;

                Intent developerIntent = new Intent(MainActivity.this, ShowDeveloperActivity.class);

                startActivity(developerIntent);



            }
        });

        //show empty if result is empty
        if(developerAdapter.isEmpty() || developerAdapter==null){
            TextView empty_view = (TextView)findViewById(R.id.empty_view);
            //make it visible
            empty_view.setVisibility(View.VISIBLE);
            listView.setEmptyView(findViewById(R.id.empty_view));
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings){

            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
