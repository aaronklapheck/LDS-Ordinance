package com.klapheck.priesthoodordinances;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;

public class Ordinance extends Activity {

	private final static String fileName = "MySharedString", stringKey = "SharedInt";
	private static int getIntegerDefault = R.string.error;
	private static SharedPreferences someData;
	TextView foo;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blessings);
		
		//registerForContextMenu(findViewById(R.id.changeSize));

		someData = getSharedPreferences(fileName, 0);

		int tvId = someData.getInt(stringKey, getIntegerDefault);

		foo = (TextView) findViewById(R.id.tvBlessings);
		foo.setMovementMethod (LinkMovementMethod.getInstance());
		foo.setText(Html.fromHtml(getString(tvId)));
		
		
	}
	


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// Start track using Google Analytics
		EasyTracker.getInstance().activityStart(this);
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		// Stop track using Google Analytics
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}
	
	
	/*
	 * Create the main menu action bar in upper portion of the screen.
	 */
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.blessing_menu, menu);
		return true;
	}
	
	
	/*
	 * defines what happens when the user clicks on a menu item set up in {@link #onCreateOptionsMenu}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int text_size = (int) foo.getTextSize();
		switch (item.getItemId()) {
		case R.id.enlargeSize:
			// increase text size
			foo.setTextSize(text_size/2 + 2);
			break;
		case R.id.shrinkSize:
			// decrease text size
			foo.setTextSize(text_size/2 - 2);
			text_size = (int) foo.getTextSize();
			break;
		}
		return false;
	}
	
}

