package edu.photodropms;

/**
 * Created by Sumanth Mudiyam on 3/30/2015.
 * Functionality : Retrieves the photo list from dropbox folder
 */


import android.os.AsyncTask;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import java.util.ArrayList;

public class ListPhotos extends AsyncTask<Void, Void, ArrayList<String>> {

    MainActivity activity;

    public ListPhotos(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<String> doInBackground(Void... params) {
        try {
            /*  Functionality : Retrieves the photo list from dropbox folder    */
            DropboxAPI.Entry directory = MainActivity.mDBApi.metadata("/", 1000, null, true, null);
            for (DropboxAPI.Entry entry : directory.contents) {
                activity.list.add(entry.fileName());
            }
        } catch (DropboxException e) {
            e.printStackTrace();
        }

        return activity.list;
    }

    @Override
    protected void onPostExecute(ArrayList<String> list) {
        activity.photosList();
    }
}