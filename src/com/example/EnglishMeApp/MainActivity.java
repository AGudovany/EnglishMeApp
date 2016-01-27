package com.example.EnglishMeApp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * Created by GudovanyiAlexey on 27.01.2016.
 */
public class MainActivity extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_intent);
        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("tab1");
        tabSpec.setIndicator("Список клієнтів");
        tabSpec.setContent(new Intent(this, ClientListActivity.class));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tab2");
        tabSpec.setIndicator("Оплата");
        tabSpec.setContent(new Intent(this, PaymantsActivity.class));
        tabHost.addTab(tabSpec);
    }
}