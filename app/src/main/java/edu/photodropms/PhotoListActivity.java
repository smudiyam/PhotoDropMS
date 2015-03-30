package edu.photodropms;

/**
 * Created by Sumanth Mudiyam on 3/30/2015.
 * Functionality : Serves as a display for photo list
 */

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class PhotoListActivity extends ActionBarActivity {

    public static String PHOTO_NAME = "PhotoName";
    ArrayList<String> list;
    ListView myListView;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        if (getIntent().getExtras() != null) {
            list = (ArrayList<String>) getIntent().getExtras().get(MainActivity.PHOTO_LIST);
        }
        Log.d("PhotoListActivity", list.toString());

        myListView = (ListView) findViewById(R.id.listView1);
        adapter = new ListAdapter(this, list);
        myListView.setAdapter(adapter);

        /*  Listview element onClick Functionality    */
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(PhotoListActivity.this,
                        PhotoActivity.class);
                intent.putExtra(PHOTO_NAME,
                        (String) parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_list, menu);
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
