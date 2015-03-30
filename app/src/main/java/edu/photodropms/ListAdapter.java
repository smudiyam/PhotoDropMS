package edu.photodropms;

/**
 * Created by Sumanth Mudiyam on 3/30/2015.
 * Functionality : Creating custom view for listview elements
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> list;

    public ListAdapter(Context context, ArrayList<String> list) {
        super(context, R.layout.item_row_mainlayout, list);
        this.context = context;
        this.list = list;
    }

    /*  Creating custom view for listview elements  */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemRowView = inflater.inflate(R.layout.item_row_mainlayout,
                parent, false);

        TextView photoDisplay = (TextView) itemRowView
                .findViewById(R.id.textView1);
        photoDisplay.setText(list.get(position));

        return itemRowView;
    }
}