package com.example.receivecallsms;

import com.parse.Parse;
import com.parse.ParseObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Parse.initialize(this, "pPowuNIyQqkuCJaexMKnhAlbzDbTa1mi2qRqjSsk", "o3d6OdaXbwbHpenQvKUSu7E5NrtsWMZuD6cpkPvc");
		//Parse.initialize(this, "E7ckE2r2fOxmWa0krV8aWA9C0xlgoKdlmzKYNVcD", "pNNpeDuH1fJAYkN7bArWwQjYfpQU4QbyX4thVGyv");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}


