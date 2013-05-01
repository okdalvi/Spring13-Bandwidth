package com.example.tabs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends Activity{
	TextView content;
	StringBuffer sb;
	
	public AboutActivity() {
    	sb = new StringBuffer();
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        content = (TextView)findViewById(R.id.content);
        sb.append("\nA Republic Wireless Application:\n");
        sb.append("Automated Call & SMS Test Framework\n\n");
        sb.append("Version 1.0\n\n");
        sb.append("NC State University\n");
        sb.append("Dept Of Computer Science\n");
    	content.setText(sb);
    }
}
