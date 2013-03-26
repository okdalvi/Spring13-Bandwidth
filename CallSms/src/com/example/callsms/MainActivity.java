package com.example.callsms;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	EditText et;
	Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        start();
    }


    public void start() {
    	b1 = (Button) findViewById(R.id.button1);
    	b2 = (Button) findViewById(R.id.button2);
    	
    	et = (EditText) findViewById(R.id.editText1);
    	
    	b1.setOnClickListener(new OnClickListener() {
    		 
			public void onClick(View arg0) {
				Toast msg = Toast.makeText(getBaseContext(), "calling... ", Toast.LENGTH_LONG);
			    msg.show();
			    
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
			
		});
		
    	b2.setOnClickListener(new OnClickListener() {
   		 
			public void onClick(View arg0) {
				Toast msg = Toast.makeText(getBaseContext(), "sending SMS...", Toast.LENGTH_LONG);
				msg.show();
				
				try {
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(et.getText().toString(), null, "test message", null, null);
					Toast.makeText(getApplicationContext(), "sent",	Toast.LENGTH_LONG).show();
		        } catch (ActivityNotFoundException activityException) {
		        }
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
                }

            }
        }
}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
