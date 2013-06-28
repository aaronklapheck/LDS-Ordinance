package com.klapheck.priesthoodordinances;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;

public class AboutApp extends Activity implements View.OnClickListener {
	
	String emailAdd = "aaronklapheck@gmail.com", 
			subject = "LDS Ordinances Feedback";
	Button sendEmail;
	
	String versionName;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_lds);
		
		initializeVars();
		sendEmail.setOnClickListener(this);
		
		TextView version = (TextView) findViewById(R.id.tvVersion);
		
		// Get the version name located in the Manifest file.
		try {
			versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
			version.setText(getResources().getString(R.string.version) + " " + versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
	
	private void initializeVars() {
		// TODO Auto-generated method stub
		sendEmail = (Button) findViewById(R.id.bSendFeedback);
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String emailaddress[] = { emailAdd };
		String message = 
				  "****** App info ******" + '\n'
				+ getResources().getString(R.string.version) + " " + versionName + '\n'
				+ "****** App info ******" + '\n' + '\n'
				+ getResources().getString(R.string.rate) + '\n' + '\n'
				+ getResources().getString(R.string.message)
				+ '\n';
		
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailaddress);
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
		startActivity(emailIntent);
	}

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
