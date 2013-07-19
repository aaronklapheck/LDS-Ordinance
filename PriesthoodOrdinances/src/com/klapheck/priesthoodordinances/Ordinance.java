package com.klapheck.priesthoodordinances;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;

public class Ordinance extends Activity {

	private final static String fileName = "MySharedString", stringKey = "SharedInt";
	private static int getIntegerDefault = R.string.error;
	private static SharedPreferences someData;
	private static View lv;
	private static TextView foo;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		lv = this.findViewById(android.R.id.content);
		setContentView(lv);
		
		registerForContextMenu(findViewById(R.id.changeSize));

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
	
	
	/**
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
	
	
	/**
	 * defines what happens when the user clicks on a menu item set up in {@link #onCreateOptionsMenu}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.changeSize:
			// 
			openContextMenu(Ordinance.lv);
			break;
		}
		return false;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.blessings_text_size_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    //AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId()) {
	        case R.id.small:
	            //small text
	        	Toast.makeText(getApplicationContext(), "small", Toast.LENGTH_SHORT);
	            return true;
	        case R.id.medium:
	            //small text
	        	Toast.makeText(getApplicationContext(), "medium", Toast.LENGTH_SHORT);
	            return true;
	        case R.id.large:
	            //small text
	        	Toast.makeText(getApplicationContext(), "large", Toast.LENGTH_SHORT);
	            return true;
	        case R.id.default_1:
	            //small text
	        	Toast.makeText(getApplicationContext(), "default", Toast.LENGTH_SHORT);
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	
	
	
	
}
