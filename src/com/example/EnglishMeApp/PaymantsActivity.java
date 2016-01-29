package com.example.EnglishMeApp;

import android.app.*;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by GudovanyiAlexey on 15.01.2016.
 */
public class PaymantsActivity extends Activity{
    private String phone;
    private Calendar calendar;
    private int year, month, day;
    private TextView nameView, phoneView, paymentDate;
    private ImageView smsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_info);
        nameView = (TextView) findViewById(R.id.clientNameTextView);
        phoneView = (TextView) findViewById(R.id.phoneTextView);
        smsView = (ImageView) findViewById(R.id.smsButton);
        paymentDate = (TextView) findViewById(R.id.paymentsDateText);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        phone = getIntent().getStringExtra("phone");
        nameView.setText(getIntent().getStringExtra("name"));
        phoneView.setText(phone);
        paymentDate.setText(((day < 10) ? "0" : "") + String.valueOf(day) + "." + ((month+1 < 10) ? "0" : "") + String.valueOf(month+1) + "." + String.valueOf(year));

        phoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);
            }
        });

        smsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.setData(Uri.parse("smsto:" + phone));
                startActivity(smsIntent);
            }
        });

        paymentDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showDialog(999);
            }
        });
    }

    public void onClick(View view) {
        EditText mPayment = (EditText) findViewById(R.id.paymentsEditText);
        TextView mPaymentDate = (TextView) findViewById(R.id.paymentsDateText);
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
            paymentDate.setText(((arg3< 10) ? "0" : "") + String.valueOf(arg3) + "." + ((arg2 + 1 < 10) ? "0" : "") + String.valueOf(arg2+1) + "." + String.valueOf(arg1));
        }
    };
}
