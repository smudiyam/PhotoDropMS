package edu.photodropms;

/**
 * Created by Sumanth Mudiyam on 3/30/2015.
 * Functionality : Downloads requested image
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownloadPhotoFromDropbox extends AsyncTask<Void, Void, Bitmap> {

    private Context context;
    private String photoName;

    public DownloadPhotoFromDropbox(Context context, String photoName) {
        this.context = context.getApplicationContext();
        this.photoName = photoName;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        File tempFile;
        Bitmap bp;
        try {

            /*  Downloading the just required image */
            String path = PhotoUtil.getCacheFilename();
            tempFile = new File(path);
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            DropboxAPI.DropboxFileInfo info = MainActivity.mDBApi.getFile("/" + photoName, null, outputStream, null);
            bp = PhotoUtil.loadFromCacheFile();
            return bp;

        } catch (IOException | DropboxException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bp) {
        if (bp != null)
            /*  Displays the photo on the PhotoActivity     */
            PhotoActivity.imgFavorite.setImageBitmap(bp);
        else {
            Toast.makeText(context, "Failed to download photo", Toast.LENGTH_LONG)
                    .show();
        }
    }
}