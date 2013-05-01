package com.example.tabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.Parse;
import com.parse.ParseObject;

public class LogActivity extends Activity {
	public String myPhoneNumber= null;
    ParseObject testObject = null;
    ParseObject SMSObject = null;
    ParseObject OutgoingCalls = null;
    ParseObject OutgoingSMSs = null;
    TextView content;
    StringBuffer sb;
	ImageButton imgButtonLogs, imgButtonResults;
	
	public LogActivity(){
        
        sb = new StringBuffer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        content = (TextView) findViewById(R.id.textView1);
        content.setMovementMethod(new ScrollingMovementMethod());
        logActivity();
    }
    
    public void logActivity() {
    	
    	imgButtonLogs = (ImageButton) findViewById(R.id.imageButton2);
    	imgButtonResults = (ImageButton) findViewById(R.id.imageButton3);
    	
    	imgButtonLogs.setOnClickListener(new OnClickListener() {
   		 
			public void onClick(View arg0) {
	            // Displaying Logs on Screen in  a new Activity
	            Intent i = new Intent(LogActivity.this, TextLogActivity.class);
	            i.putExtra("sb", getLogs());
	            startActivity(i);
			}
			
		});
    	
    	imgButtonResults.setOnClickListener(new OnClickListener() {
      		 
			public void onClick(View arg0) {
	            Intent i = new Intent(LogActivity.this, WebActivity.class);
	            startActivity(i);
			}
			
		});
    }
    
    public String getLogs() {
		String logs = ((BaseApplication) this.getApplication()).getLogText().toString();
		return logs;
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	Intent myIntent;
         
        switch (item.getItemId())
        {
        case R.id.menu_info:
            myIntent = new Intent(LogActivity.this, AboutActivity.class);
            LogActivity.this.startActivity(myIntent);
            return true;
            
        case R.id.menu_server:
        	myIntent = new Intent(LogActivity.this, ServerActivity.class);
            LogActivity.this.startActivity(myIntent);
            return true;
 
        case R.id.menu_exit:
            Toast.makeText(LogActivity.this, "Exiting Application", Toast.LENGTH_SHORT).show();
            System.exit(0);
            return true;
 
        default:
    
        	return super.onOptionsItemSelected(item);
        }
    }    
        
}


