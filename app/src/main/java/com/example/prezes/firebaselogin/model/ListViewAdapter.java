package com.example.prezes.firebaselogin.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.prezes.firebaselogin.R;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by prezes on 2017-05-22.
 */

public class ListViewAdapter extends BaseAdapter {

    Activity activity;
    List<User> lstUsers;
    LayoutInflater inflater;


    public ListViewAdapter(Activity activity, List<User> lstUsers) {
        this.activity = activity;
        this.lstUsers = lstUsers;
    }


    @Override
    public int getCount() {
        return lstUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return lstUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.activity_account, null);

        return itemView;
    }
}
