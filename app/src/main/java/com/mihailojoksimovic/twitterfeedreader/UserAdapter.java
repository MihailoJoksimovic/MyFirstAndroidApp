package com.mihailojoksimovic.twitterfeedreader;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mihailojoksimovic on 4/2/17.
 */

public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.name)).setText(user.getName());
        ((TextView) convertView.findViewById(R.id.address)).setText(user.getAddress());

        convertView.setTag(user);

        return convertView;
    }
}
