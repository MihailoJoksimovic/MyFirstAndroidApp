package com.mihailojoksimovic.twitterfeedreader;

import android.app.Application;
import android.app.LoaderManager;
import android.content.ContentProvider;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.UserDictionary;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.provider.ContactsContract;
import android.os.Build;
import android.Manifest;
import android.content.pm.PackageManager;



import android.provider.ContactsContract.Contacts;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnClickListener {

    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    private SimpleCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list_view);

        mCursorAdapter = new SimpleCursorAdapter(MainActivity.this,
                android.R.layout.simple_list_item_2, null,
                new String[] { Contacts.DISPLAY_NAME, Contacts.CONTACT_STATUS },
                new int[] { android.R.id.text1, android.R.id.text2 }, 0);

        listView.setAdapter(mCursorAdapter);

        showContacts();
    }

    /**
     * Show the contacts in the ListView.
     */
    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            getLoaderManager().initLoader(0, null, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] mProjection = {
                Contacts._ID,
                Contacts.CONTACT_STATUS,
                Contacts.DISPLAY_NAME
        };

        Loader<Cursor> loader = new CursorLoader(
                MainActivity.this,
                Contacts.CONTENT_URI,
                mProjection,
                "",
                null,
                null
        );

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0) {
            while (data.moveToNext()) {
                Log.i("Test", "Kurac");
            }
        }

        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View v) {

    }
}
