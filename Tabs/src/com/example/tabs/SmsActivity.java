package com.example.tabs;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SmsActivity extends Activity {
	
	EditText et;
	TextView tv;
	Button s, cl, ws, cs;
	WifiManager wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_sms);
        
        start();
    }
    
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
            context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        return networkInfo == null ? false : networkInfo.getState() == NetworkInfo.State.CONNECTED;
    }
    
    public void start() {
    	et = (EditText) findViewById(R.id.editText1);
    	s = (Button) findViewById(R.id.button1);
    	ws = (Button) findViewById(R.id.button4);
    	cs = (Button) findViewById(R.id.button5);
    	
    	s.setOnClickListener(new OnClickListener() {
    		 
			public void onClick(View arg0) {
			    sendSms();
			}
			
		});
		
    	ws.setOnClickListener(new OnClickListener() {
     		 
			public void onClick(View arg0) {
				airplaneMode(true, 0);
				wifiMode(true);
			    sendSms();
			}
		});
    	
    	cs.setOnClickListener(new OnClickListener() {
     		 
			public void onClick(View arg0) {
				airplaneMode(false, 1);
				wifiMode(false);
				sendSms();
			}
		});
    	
	}
    
    public void sendSms() {
    	try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(et.getText().toString(), null, "test message", null, null);
			Toast.makeText(getApplicationContext(), "SMS sent",	Toast.LENGTH_LONG).show();
			et.setText("");
        } catch (ActivityNotFoundException activityException) {
        }    
    }
    
    public void airplaneMode(boolean state, int value) {
    	
		// toggle airplane mode
		Settings.System.putInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, value);

		// Post an intent to reload
		Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
		intent.putExtra("state", state);
		sendBroadcast(intent); 
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			Log.e("Interrupted Exception", null);
		}
    }
    
    public void wifiMode(boolean state) {

    	// turn on wifi
		wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wifi.setWifiEnabled(state);
		
		if (state) {
			while(!isConnected(SmsActivity.this)) {
		    	try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					Log.e("Interrupted Exception", null);
				}
		    }
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			Log.e("Interrupted Exception", null);
		}
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
