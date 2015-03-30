package edu.photodropms;

/**
 * Created by Sumanth Mudiyam on 3/30/2015.
 * Functionality : Acts as login page and gives access to features upon successful authentication
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    final static private String APP_KEY = "bkd9becgwi3l800";
    final static private String APP_SECRET = "zq7dibk5ez1n2t9";
    public static String PHOTO_LIST = "PhotoList";
    Boolean loggedIn = false;
    public static DropboxAPI<AndroidAuthSession> mDBApi;
    Button logIn, takePhoto, listPhotos;

    ArrayList<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*    Button instances    */
        logIn = (Button) findViewById(R.id.login);
        logIn.setOnClickListener(this);
        takePhoto = (Button) findViewById(R.id.take_photo);
        takePhoto.setOnClickListener(this);
        listPhotos = (Button) findViewById(R.id.list_photos);
        listPhotos.setOnClickListener(this);

        /*  Session Connection to Dropbox   */
        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);

    }

    /*    On return to Application from Dropbox Session Authentication  */
    protected void onResume() {
        super.onResume();

        if (mDBApi.getSession().authenticationSuccessful()) {
            try {
                mDBApi.getSession().finishAuthentication();
                logIn.setText(R.string.logout);
                loggedIn = true;
            } catch (IllegalStateException e) {
                Log.i("DbAuthLog", "Error authenticating", e);
            }
        }
    }

    /*    Invoking inbuilt camera by using Android DropboxAPI   */
    public void startCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }


    /*    On return to MainActivity from Camera Activity
     *    Uploads the photo dropbox folder     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (data != null && requestCode == 0 && resultCode == Activity.RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            PhotoUtil.saveToCacheFile(bp);
            bp = PhotoUtil.loadFromCacheFile();
            if (bp != null) {
                UploadPhotoToDropbox upload = new UploadPhotoToDropbox(this);
                upload.execute();
            }
        }
    }


    /*  Navigating to PhotoListActivity once list of photos is retrieved   */
    public void photosList() {
        if (list.size() > 0) {
            Intent intent = new Intent(MainActivity.this,
                    PhotoListActivity.class);
            intent.putExtra(PHOTO_LIST, list);
            startActivity(intent);
        } else
            Toast.makeText(this, "No Photos to display",
                    Toast.LENGTH_SHORT).show();
    }


    /*  Buttons onClick functionality     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (!loggedIn)
                    mDBApi.getSession().startOAuth2Authentication(MainActivity.this);
                else {
                    mDBApi.getSession().unlink();
                    logIn.setText(R.string.login);
                    loggedIn = false;
                    Toast.makeText(this, "Logged Out",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.take_photo:
                if (!loggedIn)
                    Toast.makeText(this, "Please login into DropBox Account first",
                            Toast.LENGTH_SHORT).show();
                else
                    startCamera();

                break;
            case R.id.list_photos:
                if (!loggedIn)
                    Toast.makeText(this, "Please login into DropBox Account first",
                            Toast.LENGTH_SHORT).show();
                else {
                    /*  Retrieving Photo list using AsyncTask   */
                    list = new ArrayList<String>();
                    ListPhotos listPhotos = new ListPhotos(MainActivity.this);
                    listPhotos.execute();
                }
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
