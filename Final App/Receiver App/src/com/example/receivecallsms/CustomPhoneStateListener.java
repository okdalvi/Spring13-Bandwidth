package com.example.receivecallsms;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;
import com.parse.ParseObject;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class CustomPhoneStateListener extends PhoneStateListener {

    Context context; //Context to make Toast if required 
    private ITelephony telephonyService;
    ParseObject IncomingCalls = null;
    
    public CustomPhoneStateListener(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);

        switch (state) {
        case TelephonyManager.CALL_STATE_IDLE:
            //when Idle i.e no call
            Toast.makeText(context, "Phone state Idle", Toast.LENGTH_LONG).show();
            break;
        case TelephonyManager.CALL_STATE_OFFHOOK:
            //when Off hook i.e in call
            //Make intent and start your service here
            Toast.makeText(context, "Phone state Off hook", Toast.LENGTH_LONG).show();
            break;
        case TelephonyManager.CALL_STATE_RINGING:
            //when Ringing
            Toast.makeText(context, "Phone state Ringing", Toast.LENGTH_LONG).show();
            TelephonyManager telephony = (TelephonyManager) 
            context.getSystemService(Context.TELEPHONY_SERVICE);  
            try {
            	Class c = Class.forName(telephony.getClass().getName());
            	Method m = c.getDeclaredMethod("getITelephony");
            	m.setAccessible(true);
            	Log.v("Call Details", telephony.getLine1Number());
            	telephonyService = (ITelephony) m.invoke(telephony);
            	
            	/* Uncomment to hangup incoming calls
             	Thread.sleep(4000);
 				telephonyService.endCall(); 
 				*/
            	
            	// Get Call Details
    			String myPhoneNumber = telephony.getLine1Number();
    			Long tsLong = System.currentTimeMillis()/1000;
    			String ts = tsLong.toString();
    			
    			// Display state
                String recvdCall = "Call Received From:--- "+incomingNumber+"\nReceived Time:--- "+ts;
                Toast.makeText(context, recvdCall, Toast.LENGTH_LONG).show();
                
    			// Upload to Parse
    			IncomingCalls = new ParseObject("IncomingCalls");
    			IncomingCalls.put("Source", incomingNumber);
                IncomingCalls.put("Destination", myPhoneNumber);
                IncomingCalls.put("dir", "INCOMING");
                IncomingCalls.put("date_str", ts);
                IncomingCalls.saveInBackground();
                
            } catch (Exception e) {
            }            
            break;
        default:
            break;
        }
    }
}