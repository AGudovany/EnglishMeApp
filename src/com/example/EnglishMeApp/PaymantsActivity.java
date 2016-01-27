package com.example.EnglishMeApp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by GudovanyiAlexey on 15.01.2016.
 */
public class PaymantsActivity extends Activity{
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_info);
        TextView nameView = (TextView) findViewById(R.id.clientNameTextView);
        TextView phoneView = (TextView) findViewById(R.id.phoneTextView);
        ImageView smsView = (ImageView) findViewById(R.id.smsButton);

        phone = getIntent().getStringExtra("phone");
        nameView.setText(getIntent().getStringExtra("name"));
        phoneView.setText(phone);
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
    }

    public void onClick(View view) {
        EditText mPayment = (EditText) findViewById(R.id.paymentsEditText);
        EditText mPaymentDate = (EditText) findViewById(R.id.paymentsDateEditText);
    }

}
