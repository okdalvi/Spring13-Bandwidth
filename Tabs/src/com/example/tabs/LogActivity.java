package com.example.tabs;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;

public class LogActivity extends Activity {

    public String myPhoneNumber= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        Parse.initialize(this, "pPowuNIyQqkuCJaexMKnhAlbzDbTa1mi2qRqjSsk", "o3d6OdaXbwbHpenQvKUSu7E5NrtsWMZuD6cpkPvc");
        getCallDetails();
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    @SuppressLint("SimpleDateFormat")
    private void getCallDetails() {
        
        String phNumber=null,callType=null,callDate=null,date_str=null,callDuration=null;
        
        String dir = null;

        //To get the caller's phone number
        final Context context = getApplicationContext();
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        myPhoneNumber = tMgr.getLine1Number();
           
           
        TextView tv = (TextView)findViewById(R.id.textView1);
        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = managedQuery( CallLog.Calls.CONTENT_URI,null, null,null, null);
        
        /*
         * To get details from Call log
         * */
        int number = managedCursor.getColumnIndex( CallLog.Calls.NUMBER ); 
        int type = managedCursor.getColumnIndex( CallLog.Calls.TYPE );
        int date = managedCursor.getColumnIndex( CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex( CallLog.Calls.DURATION);
        
        sb.append( "Call Details :");
        
        while ( managedCursor.moveToNext() ) {
        
        phNumber = managedCursor.getString( number );
        callType = managedCursor.getString( type );
        callDate = managedCursor.getString( date );
        
        Date callDayTime = new Date(Long.valueOf(callDate));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date_str = sdf.format(callDayTime);
        
        callDuration = managedCursor.getString( duration );
        
        
        int dircode = Integer.parseInt( callType );
        
        switch( dircode ) {
        
        case CallLog.Calls.OUTGOING_TYPE:{
                                            dir = "OUTGOING";
                                            break;
        }

        case CallLog.Calls.INCOMING_TYPE:{
                                            dir = "INCOMING";
                                            break;
        }
        case CallLog.Calls.MISSED_TYPE:{
                                        dir = "MISSED";
                                        break;
        }
        
        }
        
        /*
         * To display the call details on the phone
         * */
        sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir+" \nCall Date:--- "+date_str+" \nCall duration in sec :--- "+callDuration );
        sb.append("\n----------------------------------");
        
        /*
         * To upload the call details to Parse
         * */
        
        ParseObject testObject = new ParseObject("TestObject");
        
        
        if(testObject.getString("Source")!=null && 
                testObject.getString("Destination")!=null && 
                testObject.getString("dir")!=null &&
                testObject.getString("date_str")!=null &&
                testObject.getString("callDuration")!=null){
        
                        if(!(testObject.get("Source").equals(myPhoneNumber)) && 
                                !(testObject.get("Destination").equals(phNumber)) &&
                                !(testObject.get("dir").equals(dir)) &&
                                !(testObject.get("date_str").equals(date_str)) &&
                                !(testObject.get("callDuration").equals(callDuration)))
                        {
                            testObject.put("Source", myPhoneNumber);
                            testObject.put("Destination", phNumber);
                            testObject.put("dir", dir);
                            testObject.put("date_str", date_str);
                            testObject.put("callDuration", callDuration);
                            testObject.saveInBackground();
                        }
        
        }
        
        else{
            
            testObject.put("Source", myPhoneNumber);
            testObject.put("Destination", phNumber);
            testObject.put("dir", dir);
            testObject.put("date_str", date_str);
            testObject.put("callDuration", callDuration);
            testObject.saveInBackground();
            
        }
        
        }
        
        managedCursor.close();
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(sb);
        
        }
}


