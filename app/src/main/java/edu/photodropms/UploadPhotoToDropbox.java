package edu.photodropms;

/**
 * Created by Sumanth Mudiyam on 3/30/2015.
 * Functionality : Uploads the photo taken
 */

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dropbox.client2.exception.DropboxException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadPhotoToDropbox extends AsyncTask<Void, Void, Boolean> {

    private Context context;

    public UploadPhotoToDropbox(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        File tempFile;
        try {

            /*  Naming Convention for photos taken    */
            String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
            String imageFileName = "img_" + timeStamp;
            String path = PhotoUtil.getCacheFilename();
            tempFile = new File(path);
            FileInputStream fileInputStream = new FileInputStream(tempFile);
            /*  Uploading the photo into dropbox folder */
            MainActivity.mDBApi.putFile("/" + imageFileName + ".jpg", fileInputStream,
                    tempFile.length(), null, null);
            tempFile.delete();
            return true;

        } catch (IOException | DropboxException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Toast.makeText(context, "Photo Uploaded Successfully!",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Failed to upload photo", Toast.LENGTH_LONG)
                    .show();
        }
    }
}