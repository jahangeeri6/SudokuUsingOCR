package com.example.sudukousingcamapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class PrintActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		System.out.println("PrintActivity says: "+ message);
		 TextView textView = new TextView(this);
		    textView.setTextSize(40);
		    textView.setText(message);

		    // Set the text view as the activity layout
		    setContentView(textView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.print, menu);
		return true;
	}

}
