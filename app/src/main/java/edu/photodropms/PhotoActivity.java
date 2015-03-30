package edu.photodropms;

/**
 * Created by Sumanth Mudiyam on 3/30/2015.
 * Functionality : Displays downloaded image
 */

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class PhotoActivity extends ActionBarActivity {

    String photoName;
    public static ImageView imgFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        if (getIntent().getExtras() != null) {
            photoName = (String) getIntent().getExtras().get(PhotoListActivity.PHOTO_NAME);
        }
        Log.d("PhotoActivity", photoName);

        imgFavorite = (ImageView) findViewById(R.id.imageView);
        /*  Initiates download process  */
        DownloadPhotoFromDropbox download = new DownloadPhotoFromDropbox(this, photoName);
        download.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
