package com.example.tabs;

import com.parse.ParseObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SmsActivity extends Activity {
	
	EditText et, etMsg;
	TextView tv;
	ImageButton imgButtonSms, imgButtonWifiSms, imgButtonCellSms;
	Button s, cl, ws, cs;
	WifiManager wifi;
    ParseObject OutgoingSMSs = null;

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
    	etMsg = (EditText) findViewById(R.id.editText2);
    	imgButtonSms = (ImageButton) findViewById(R.id.imgButton1);
    	imgButtonWifiSms = (ImageButton) findViewById(R.id.imgButton2);
    	imgButtonCellSms = (ImageButton) findViewById(R.id.imgButton3);
    	
    	imgButtonSms.setOnClickListener(new OnClickListener() {
    		 
			public void onClick(View arg0) {
			    sendSms("normal");
			}
			
		});
		
    	imgButtonWifiSms.setOnClickListener(new OnClickListener() {
     		 
			public void onClick(View arg0) {
				airplaneMode(true, 0);
				wifiMode(true);
			    sendSms("wifi");
			}
		});
    	
    	imgButtonCellSms.setOnClickListener(new OnClickListener() {
     		 
			public void onClick(View arg0) {
				airplaneMode(false, 1);
				wifiMode(false);
				sendSms("cell");
			}
		});
    	
	}
    
    public void sendSms(String textType) {
    	try {
			SmsManager smsManager = SmsManager.getDefault();
			String msg = etMsg.getText().toString();
			smsManager.sendTextMessage(et.getText().toString(), null, msg, null, null);
			Toast.makeText(getApplicationContext(), "SMS sent",	Toast.LENGTH_LONG).show();
			/* store the sent sms in the sent folder 
	    	ContentValues values = new ContentValues();
	    	values.put("address", et.getText().toString());
	    	values.put("body", msg); 
	    	getContentResolver().insert(Uri.parse("content://sms/sent"), values); */
			
			// Get SMS Details
			TelephonyManager tMgr =(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			String myPhoneNumber = tMgr.getLine1Number();
			Long tsLong = System.currentTimeMillis()/1000;
			String ts = tsLong.toString();
			
			// Upload to Parse
			OutgoingSMSs = new ParseObject("OutgoingSMSs");
            OutgoingSMSs.put("Destination", et.getText().toString());
            OutgoingSMSs.put("Source", myPhoneNumber);
            OutgoingSMSs.put("body", msg);
            OutgoingSMSs.put("date", ts);
            OutgoingSMSs.put("Type", textType);
            OutgoingSMSs.saveInBackground();
            
            // Add entry to Text Log View
            ((BaseApplication) this.getApplication()).addToLogText("Texted Number:--- "+et.getText().toString()+"\nTexted Msg:--- "+msg+"\nTexted Time:--- "+ts+"\nText Type:--- "+textType);
            
			et.setText("");
			etMsg.setText("");
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
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	/**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	Intent myIntent;
         
        switch (item.getItemId())
        {
        case R.id.menu_info:
            myIntent = new Intent(SmsActivity.this, AboutActivity.class);
            SmsActivity.this.startActivity(myIntent);
            return true;
            
        case R.id.menu_server:
        	myIntent = new Intent(SmsActivity.this, ServerActivity.class);
            SmsActivity.this.startActivity(myIntent);
            return true;
 
        case R.id.menu_exit:
            Toast.makeText(SmsActivity.this, "Exiting Application", Toast.LENGTH_SHORT).show();
            System.exit(0);
            return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }    

    
}
