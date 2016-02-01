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


public class ClientListActivity extends ListActivity {
    /**
     * Called when the activity is first created.
     */
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;
    private EditText mInputSearch;
    private ListView mMainListView;
    private SimpleAdapter mAdapter;
    private static final String NAME = "name";
    private static final String CLIENT_ID = "id";
    private static final String PHONE = "phone";

    private ArrayList<HashMap<String, Object>> results = new ArrayList<HashMap<String, Object>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clients_list);
        //mMainListView = (ListView) findViewById(R.id.mainListView);

        /*mClientList = */
        openAndQueryClientsListDatabase();
        displayResultList(results);


        mInputSearch = (EditText) findViewById(R.id.searchText);
        mInputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ClientListActivity.this.mAdapter.getFilter().filter(charSequence);
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
        Intent intent = new Intent(ClientListActivity.this, PaymantsActivity.class);
        HashMap<String, Object> itemHashMap = (HashMap<String, Object>) l.getItemAtPosition(position);
        String nameItem = itemHashMap.get(NAME).toString();
        String phoneItem = itemHashMap.get(PHONE).toString();
        long idItem = (long) itemHashMap.get(CLIENT_ID);
        intent.putExtra("name", nameItem);
        intent.putExtra("id", idItem);
        intent.putExtra("phone", phoneItem);
        startActivity(intent);
    }

    private void displayResultList(ArrayList<HashMap<String,Object>> clientList) {
        /*mAdapter = new ArrayAdapter<String>(this,
                R.layout.list_item, R.id.textView, clientList);*/
        mAdapter = new SimpleAdapter(this, results, R.layout.list_item, new String[]{NAME}, new int[]{R.id.textView});
        //mMainListView.setAdapter(mAdapter);
        setListAdapter(mAdapter);
    }

    private ArrayList<HashMap<String,Object>> openAndQueryClientsListDatabase() {
        try {
            HashMap<String, Object> hm;
            mDatabaseHelper = new DatabaseHelper(this, "englishme.db", null, 1);
            mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
            Cursor cursor = mSqLiteDatabase.query("clients", new String[]{DatabaseHelper.KEY_ROWID, DatabaseHelper.NAME_COLUMN,
                            DatabaseHelper.PHONE_COLUMN},
                            null, null, null, null, DatabaseHelper.NAME_COLUMN) ;
            while (cursor.moveToNext()) {
                hm = new HashMap<>();
                hm.put(NAME, cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME_COLUMN)));
                hm.put(CLIENT_ID, cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_ROWID)));
                hm.put(PHONE, cursor.getString(cursor.getColumnIndex(DatabaseHelper.PHONE_COLUMN)));
                results.add(hm);
            }

            // не забываем закрывать курсор
            cursor.close();
            return results;
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
        return null;
    }

}
