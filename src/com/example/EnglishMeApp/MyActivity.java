package com.example.EnglishMeApp;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyActivity extends ListActivity {
    /**
     * Called when the activity is first created.
     */
    private TextView mHelloTextView;
    private ListView mListView;
    private EditText mNameEditText;
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;
    final String[] catNamesArray = new String[] { "Рыжик", "Барсик", "Мурзик",
            "Мурка", "Васька", "Томасина", "Бобик", "Кристина", "Пушок",
            "Дымка", "Кузя", "Китти", "Барбос", "Масяня", "Симба" };
    //final String[] clientNamesArray = new String[] {};
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> results = new ArrayList<String>();
    /*private ArrayList<String> results = new ArrayList<String>();
    private String tableName = "clients";
    private SQLiteDatabase newDB;*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*openAndQueryDatabase();

        displayResultList();*/
        setContentView(R.layout.main);

        mDatabaseHelper = new DatabaseHelper(this, "englishme.db", null, 1);

        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        // Задайте значения для каждого столбца
        newValues.put(DatabaseHelper.NAME_COLUMN, "Рыжик");
        newValues.put(DatabaseHelper.PHONE_COLUMN, "4954553443");
        // Вставляем данные в таблицу
        mSqLiteDatabase.insert("clients", null, newValues);

        /*Cursor cursor = mSqLiteDatabase.query("clients", new String[]{DatabaseHelper.NAME_COLUMN,
                        DatabaseHelper.PHONE_COLUMN, DatabaseHelper.DATE_COLUMN},
                null, null,
                null, null, null) ;
        Log.w(getClass().getSimpleName(), "Move to First: " + cursor.moveToFirst());
        while (cursor.moveToNext()) {
            String clientName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME_COLUMN));
            results.add("name: " +clientName);
        }

        mAdapter = new ArrayAdapter<String>(this,
                R.layout.activity_custom, R.id.textView, results);
        setListAdapter(mAdapter);

        // не забываем закрывать курсор
        cursor.close();*/
    }

    public void onClick(View view) {
        Cursor cursor = mSqLiteDatabase.query("clients", new String[]{DatabaseHelper.NAME_COLUMN,
                        DatabaseHelper.PHONE_COLUMN, DatabaseHelper.DATE_COLUMN},
                null, null,
                null, null, null) ;
        Log.w(getClass().getSimpleName(), "Move to First: " + cursor.moveToFirst());
        while (cursor.moveToNext()) {
            String clientName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME_COLUMN));
            results.add(clientName);
        }

        Log.w(getClass().getSimpleName(), "results size: " + results.size());
        mAdapter = new ArrayAdapter<String>(this,
               R.layout.activity_custom, R.id.textView, results);
        setListAdapter(mAdapter);

        // не забываем закрывать курсор
        cursor.close();
    }

  /*  private void displayResultList() {
        *//*TextView tView = new TextView(this);
        tView.setText(results.get(5));
        getListView().addHeaderView(tView);*//*

        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, results));
        //getListView().setTextFilterEnabled(true);

    }*/

  /*  private void openAndQueryDatabase() {
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(this.getApplicationContext());
            newDB = dbHelper.getWritableDatabase();
            Cursor c = newDB.rawQuery("SELECT name FROM " +
                    tableName, null);
            Log.w(getClass().getSimpleName(), "Move to First: " + c.moveToFirst());
            if (c != null ) {
                if  (c.moveToFirst()) {
                    do {
                        String firstName = c.getString(c.getColumnIndex("name"));
                        results.add("name: " + firstName);
                    }while (c.moveToNext());
                }
            }
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } *//*finally {
            if (newDB != null)
                newDB.execSQL("DELETE FROM " + tableName);
            newDB.close();
        }*//*

    }*/
}
