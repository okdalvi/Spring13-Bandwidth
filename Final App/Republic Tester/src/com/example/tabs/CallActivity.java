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
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.telephony.PhoneStateListener;
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

public class CallActivity extends Activity {
	
	EditText et;
	TextView tv;
	Button c, cl, wc, cc;
	ImageButton imgButtonCall, imgButtonWifiCall, imgButtonCellCall;
	WifiManager wifi;
	ParseObject OutgoingCalls = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
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
    	imgButtonCall = (ImageButton) findViewById(R.id.imageButton1);
    	imgButtonWifiCall = (ImageButton) findViewById(R.id.imageButton2);
    	imgButtonCellCall = (ImageButton) findViewById(R.id.imageButton3);
    	
    	imgButtonCall.setOnClickListener(new OnClickListener() {
    		 
			public void onClick(View arg0) {
			    makeCall("normal");
			}
			
		});
		
    	imgButtonWifiCall.setOnClickListener(new OnClickListener() {
     		 
			public void onClick(View arg0) {
				airplaneMode(true, 0);
				wifiMode(true);
			    makeCall("wifi");
			}
		});
    	
    	imgButtonCellCall.setOnClickListener(new OnClickListener() {
     		 
			public void onClick(View arg0) {
				airplaneMode(false, 1);
				wifiMode(false);
				makeCall("cell");
			}
		});
    	
	}
    
   
    public void makeCall(String callType) {
		try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+et.getText().toString()));
			
			// Get Call Details
			TelephonyManager tMgr =(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			String myPhoneNumber = tMgr.getLine1Number();
			Long tsLong = System.currentTimeMillis()/1000;
			String ts = tsLong.toString();
			
			// Upload to Parse
			OutgoingCalls = new ParseObject("OutgoingCalls");
			OutgoingCalls.put("Source", myPhoneNumber);
            OutgoingCalls.put("Destination", et.getText().toString());
            OutgoingCalls.put("dir", "OUTGOING");
            OutgoingCalls.put("date_str", ts);
            OutgoingCalls.put("Type", callType);
            OutgoingCalls.saveInBackground();
            
            // Add entry to Text Log View
            ((BaseApplication) this.getApplication()).addToLogText("Called Number:--- "+et.getText().toString()+"\nCalled Time:--- "+ts+"\nCall Type:--- "+callType);
			
			et.setText("");
            startActivity(callIntent);
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
			while(!isConnected(CallActivity.this)) {
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
    	Intent myIntent = new Intent();
         
        switch (item.getItemId())
        {
        case R.id.menu_info:
            myIntent = new Intent(CallActivity.this, AboutActivity.class);
            CallActivity.this.startActivity(myIntent);
            return true;
            
        case R.id.menu_server:
        	myIntent = new Intent(CallActivity.this, ServerActivity.class);
            CallActivity.this.startActivity(myIntent);
            return true;
 
        case R.id.menu_exit:
            Toast.makeText(CallActivity.this, "Exiting Application", Toast.LENGTH_SHORT).show();
            System.exit(0);
            return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }    

    
}
