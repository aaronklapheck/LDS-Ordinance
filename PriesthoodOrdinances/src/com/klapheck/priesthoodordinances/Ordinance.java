package com.klapheck.priesthoodordinances;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;

public class Ordinance extends Activity {

	private final static String fileName = "MySharedString", stringKey = "SharedInt";
	private static int getIntegerDefault = R.string.error;
	private static SharedPreferences someData;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blessings);

		someData = getSharedPreferences(fileName, 0);

		int tvId = someData.getInt(stringKey, getIntegerDefault);

		TextView foo = (TextView) findViewById(R.id.tvBlessings);
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
	
	
	
}
