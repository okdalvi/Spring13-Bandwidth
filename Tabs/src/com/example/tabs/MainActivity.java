package com.example.tabs;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
 
		Resources ressources = getResources(); 
		TabHost tabHost = getTabHost(); 
 
		// Call tab
		Intent intentCall = new Intent().setClass(this, CallActivity.class);
		TabSpec tabSpecCall = tabHost
		  .newTabSpec("Call")
		  .setIndicator("", ressources.getDrawable(R.drawable.call_1_config))
		  .setContent(intentCall);
 
		// SMS tab
		Intent intentSms = new Intent().setClass(this, SmsActivity.class);
		TabSpec tabSpecSms = tabHost
		  .newTabSpec("Sms")
		  .setIndicator("", ressources.getDrawable(R.drawable.sms_1_config))
		  .setContent(intentSms);
		
		// Log tab
		Intent intentLog = new Intent().setClass(this, LogActivity.class);
		TabSpec tabSpecLog = tabHost
		  .newTabSpec("Log")
		  .setIndicator("", ressources.getDrawable(R.drawable.log_1_config))
		  .setContent(intentLog);
		 
		// Settings tab
		Intent intentSettings = new Intent().setClass(this, SettingsActivity.class);
		TabSpec tabSpecSettings = tabHost
		  .newTabSpec("Settings")
		  .setIndicator("", ressources.getDrawable(R.drawable.settings_1_config))
		  .setContent(intentSettings);
 
		// add all tabs 
		tabHost.addTab(tabSpecCall);
		tabHost.addTab(tabSpecSms);
		tabHost.addTab(tabSpecLog);
		tabHost.addTab(tabSpecSettings);
 
		//set default tab(zero based)
		tabHost.setCurrentTab(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
