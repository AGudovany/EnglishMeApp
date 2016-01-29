package com.example.EnglishMeApp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by GudovanyiAlexey on 27.01.2016.
 */
public class PaymentsListActivity extends ListActivity {
    /**
     * Called when the activity is first created.
     */
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;
    private TextView mDateSearch;
    private TextView mSumPayments;
    private Calendar calendar;
    private SimpleAdapter mAdapter;
    private static final String NAME = "name";
    private static final String PAYMENT = "payment";
    private double SUM_PAYMENT = 0;
    private int year, month, day;

    private ArrayList<HashMap<String, Object>> results = new ArrayList<HashMap<String, Object>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.payments_list);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        mDateSearch = (TextView) findViewById(R.id.payDate);
        mSumPayments = (TextView) findViewById(R.id.sum_payments);
        Log.e(getClass().getSimpleName(), "mDateSearch" + mDateSearch);

        mDateSearch.setText(((day < 10) ? "0" : "") + String.valueOf(day) + "." + ((month+1 < 10) ? "0" : "") + String.valueOf(month+1) + "." + String.valueOf(year));

        QueryPaymentsListDatabase();
        displayResultList(results);

        mDateSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(999);
            }
        });
    }

    private void displayResultList(ArrayList<HashMap<String,Object>> clientList) {
        mAdapter = new SimpleAdapter(this, results, R.layout.payment_list_item, new String[]{NAME, PAYMENT}, new int[]{R.id.nameText, R.id.paymentText});
        setListAdapter(mAdapter);
        mSumPayments.setText(String.valueOf(SUM_PAYMENT));
    }

    private ArrayList<HashMap<String,Object>> QueryPaymentsListDatabase() {
        try {
            HashMap<String, Object> hm;
            mDatabaseHelper = new DatabaseHelper(this, "englishme.db", null, 1);
            mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
            String query = "SELECT " + DatabaseHelper.NAME_COLUMN + ", "
                    + DatabaseHelper.PAY_AMOUNT_COLUMN + " FROM " + DatabaseHelper.PAYMENTS_TABLE
                    + ", " + DatabaseHelper.CLIENT_TABLE + " WHERE " + DatabaseHelper.CLIENT_ID_COLUMN
                    + " = clients._id";
            Log.e(getClass().getSimpleName(), query);
            Cursor cursor = mSqLiteDatabase.rawQuery(query, null);
            while (cursor.moveToNext()) {
                hm = new HashMap<>();
                hm.put(NAME, cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME_COLUMN)));
                hm.put(PAYMENT, cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.PAY_AMOUNT_COLUMN)));
                results.add(hm);
                SUM_PAYMENT = SUM_PAYMENT + cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.PAY_AMOUNT_COLUMN));
            }
            cursor.close();
            Log.e(getClass().getSimpleName(), "SUM_PAYMENT" + SUM_PAYMENT);
            return results;
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not Open the database");
        }
        return null;
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            mDateSearch.setText(((arg3< 10) ? "0" : "") + String.valueOf(arg3) + "." + ((arg2 + 1 < 10) ? "0" : "") + String.valueOf(arg2+1) + "." + String.valueOf(arg1));
        }
    };

}