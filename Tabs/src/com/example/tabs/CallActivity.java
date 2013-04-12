package com.example.tabs;

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
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CallActivity extends Activity {
	
	EditText et;
	TextView tv;
	Button c, cl, wc, cc;
	WifiManager wifi;

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
    	c = (Button) findViewById(R.id.button1);
    	wc = (Button) findViewById(R.id.button4);
    	cc = (Button) findViewById(R.id.button5);
    	
    	c.setOnClickListener(new OnClickListener() {
    		 
			public void onClick(View arg0) {
			    makeCall();
			}
			
		});
		
    	wc.setOnClickListener(new OnClickListener() {
     		 
			public void onClick(View arg0) {
				airplaneMode(true, 0);
				wifiMode(true);
			    makeCall();
			}
		});
    	
    	cc.setOnClickListener(new OnClickListener() {
     		 
			public void onClick(View arg0) {
				airplaneMode(false, 1);
				wifiMode(false);
				makeCall();
			}
		});
    	
	}
    
    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                if (isPhoneCalling) {

                    // restart app
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    isPhoneCalling = false;
                    
                    // turn on wifi if off
    				wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    if (!wifi.isWifiEnabled())
    					wifi.setWifiEnabled(true);
                    
                }

            }
        }
    }
    
    public void makeCall() {
    	PhoneCallListener phoneListener = new PhoneCallListener();
	    TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	    telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
	     
		try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+et.getText().toString()));
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
