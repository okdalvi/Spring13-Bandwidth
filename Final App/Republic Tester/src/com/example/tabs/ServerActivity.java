package com.example.tabs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ServerActivity extends Activity{
	TextView content;
	StringBuffer sb;
	
	public ServerActivity() {
    	sb = new StringBuffer();
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        content = (TextView)findViewById(R.id.content);
        sb.append("\nServer Database in Use:\n");
        sb.append("Parse\n\n");
        sb.append("Server Info:\n");
        sb.append("www.parse.com\n\n");
        sb.append("Version: \n\n");
        sb.append("Server ID:\n");
        sb.append("\n");
        sb.append("Server Address:\n");
        sb.append("\n");
    	content.setText(sb);
    }
}
