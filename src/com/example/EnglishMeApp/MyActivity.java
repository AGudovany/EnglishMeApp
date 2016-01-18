package com.example.EnglishMeApp;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;


public class MyActivity extends ListActivity {
    /**
     * Called when the activity is first created.
     */
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;
    private EditText mInputSearch;
    private ListView mMainListView;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> results = new ArrayList<String>();
    private ArrayList<String> mClientList = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //mMainListView = (ListView) findViewById(R.id.mainListView);

        mClientList = openAndQueryClientsListDatabase();
        displayResultList(mClientList);
        mInputSearch = (EditText) findViewById(R.id.searchText);
        mInputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MyActivity.this.mAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        TextView textView = (TextView) findViewById(R.id.textView);
        Log.v("textview ", (String) l.getItemAtPosition(position));
        Intent intent = new Intent(MyActivity.this, PaymantsActivity.class);
        intent.putExtra("name", (String) l.getItemAtPosition(position));
        startActivity(intent);
    }

    private void displayResultList(ArrayList<String> clientList) {
        mAdapter = new ArrayAdapter<String>(this,
                R.layout.list_item, R.id.textView, clientList);
        //mMainListView.setAdapter(mAdapter);
        setListAdapter(mAdapter);
    }

    private ArrayList<String> openAndQueryClientsListDatabase() {
        try {
            mDatabaseHelper = new DatabaseHelper(this, "englishme.db", null, 1);

            mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
            Cursor cursor = mSqLiteDatabase.query("clients", new String[]{DatabaseHelper.NAME_COLUMN,
                            DatabaseHelper.PHONE_COLUMN, DatabaseHelper.DATE_COLUMN},
                            null, null, null, null, DatabaseHelper.NAME_COLUMN) ;
            while (cursor.moveToNext()) {
                String clientName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME_COLUMN));
                results.add(clientName);
            }

            Log.w(getClass().getSimpleName(), "results size: " + results.size());
            // не забываем закрывать курсор
            cursor.close();
            return results;
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
        return null;
    }

}
