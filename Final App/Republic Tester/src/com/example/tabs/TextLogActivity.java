package com.example.tabs;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TextLogActivity extends ListActivity{
	TextView content;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_textlog);
        
        Bundle extras = getIntent().getExtras();
        String logs = null;
        if (extras != null) {
            logs = extras.getString("sb");
        }
        String[] result = logs.split("\\^");
        
        setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_textlog,result));
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		listView.setBackgroundResource(R.drawable.background);
		
        //content = (TextView)findViewById(R.id.textView1);
        //content.setMovementMethod(new ScrollingMovementMethod());
    	//content.setText(logs);
    }
}
