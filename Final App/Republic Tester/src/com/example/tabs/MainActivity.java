package com.example.tabs;

import java.io.File;

import com.parse.Parse;

import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

public class MainActivity extends TabActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);
		Parse.initialize(this, "pPowuNIyQqkuCJaexMKnhAlbzDbTa1mi2qRqjSsk", "o3d6OdaXbwbHpenQvKUSu7E5NrtsWMZuD6cpkPvc");
		//Parse.initialize(this, "E7ckE2r2fOxmWa0krV8aWA9C0xlgoKdlmzKYNVcD", "pNNpeDuH1fJAYkN7bArWwQjYfpQU4QbyX4thVGyv");
		
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
		
		// add all tabs 
		tabHost.addTab(tabSpecCall);
		tabHost.addTab(tabSpecSms);
		tabHost.addTab(tabSpecLog);
		
		 for(int i=0;i<tabHost.getTabWidget().getChildCount();i++) {
	        tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.background);
	        tabHost.getTabWidget().setRightStripDrawable(R.drawable.strip4); 
            tabHost.getTabWidget().setLeftStripDrawable(R.drawable.strip4);
		 }
 
		//set default tab(zero based)
		tabHost.setCurrentTab(0);
	}
	
}
