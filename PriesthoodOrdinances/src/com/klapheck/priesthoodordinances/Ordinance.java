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

	private final static String fileName = "MySharedString", stringKey = "SharedInt", 
			fileNameSize = "textSize", stringKeyText = "textInt";
	private static int getIntegerDefault = R.string.error, defaultTextSize = 18;
	private static SharedPreferences ordinanceData, textData;
	TextView blessingContent;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blessings);
		
		// Get text size stored in shared preferences and save it.
		int textSize = readTextSizeIntFromPrefs();
		
		// If text size is default then store default size to shared preferences.
		if (textSize == getIntegerDefault){
			storeTextSizeIntToPrefs(defaultTextSize);
		}
			
		// Get ordinance data stored in shared preferences and save its ID.
		ordinanceData = getSharedPreferences(fileName, MODE_PRIVATE);
		int tvId = ordinanceData.getInt(stringKey, getIntegerDefault);

		// Setup text view and place string from shared preferences inside it.
		blessingContent = (TextView) findViewById(R.id.tvBlessings);
		blessingContent.setMovementMethod (LinkMovementMethod.getInstance());
		blessingContent.setText(Html.fromHtml(getString(tvId)));
		
		// Set text size to user defined size.
		blessingContent.setTextSize(textSize);
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
		int newSize = 0;
		int text_size = readTextSizeIntFromPrefs();
		switch (item.getItemId()) {
		case R.id.enlargeSize:
			// increase text size
			newSize = text_size + 2;
			break;
		case R.id.shrinkSize:
			// decrease text size
			newSize = text_size - 2;
			break;
		}
		
		// Set text size to the new smaller or larger one.
		blessingContent.setTextSize(newSize);
		
		// Save new text size to shared preferences so when ordinance reloads
		// the user text size will be shown.
		storeTextSizeIntToPrefs(newSize);
		
		return false;
	}
	
	// Take input text size and store it to shared preferences.
	private void storeTextSizeIntToPrefs(int newSize){
		SharedPreferences.Editor editor = getSharedPreferences(fileNameSize, MODE_PRIVATE).edit();
		editor.putInt(stringKeyText, newSize);
		editor.commit();
	}
	
	
	// Get text size stored in shared preferences and output it.
	private int readTextSizeIntFromPrefs(){
		textData = getSharedPreferences(fileNameSize, MODE_PRIVATE);
		int textSize = textData.getInt(stringKeyText, getIntegerDefault);
		return textSize;
	}
	
	
	
}

