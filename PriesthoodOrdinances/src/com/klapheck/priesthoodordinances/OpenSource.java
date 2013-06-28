package com.klapheck.priesthoodordinances;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class OpenSource extends Activity {

	TextView tvOpen, ldsOrg, appIcon, androidApp, iPhoneApp, javaSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.open_source);
		
		setText();
		
	}
	
	
    private void setText() {
    	
    	tvOpen = (TextView) findViewById(R.id.tvOpenSource);
		ldsOrg = (TextView) findViewById(R.id.tvLdsOrg);
		appIcon = (TextView) findViewById(R.id.tvAppIcon);
		androidApp = (TextView) findViewById(R.id.tvAndroidApp);
		iPhoneApp = (TextView) findViewById(R.id.tviPhoneApp);
		javaSource = (TextView) findViewById(R.id.tvJavaCode);
		
		tvOpen.setText(Html.fromHtml("<a href=http://opensource.org> " + getResources().getString(R.string.open_source_logo_discription)));
	    tvOpen.setMovementMethod(LinkMovementMethod.getInstance());
	    
	    javaSource.setText(Html.fromHtml("<a href=https://docs.google.com/folder/d/0B2fbY78DLah1R0h2LWk5UzdjbDQ/edit> " + getResources().getString(R.string.java_source)));
	    javaSource.setMovementMethod(LinkMovementMethod.getInstance());
	    
	    ldsOrg.setText(Html.fromHtml("<a href=http://www.lds.org/handbook/handbook-2-administering-the-church/priesthood-ordinances-and-blessings/20.1> " + getResources().getString(R.string.lds_org)));
	    ldsOrg.setMovementMethod(LinkMovementMethod.getInstance());
	    
	    appIcon.setText(Html.fromHtml("<a href=http://www.clker.com/clipart-herald-angel.html> " + getResources().getString(R.string.app_icon)));
	    appIcon.setMovementMethod(LinkMovementMethod.getInstance());
	    
	    androidApp.setText(Html.fromHtml("<a href=https://play.google.com/store/apps/details?id=com.torosys.ldsinfo&hl=en> " + getResources().getString(R.string.lds_info)));
	    androidApp.setMovementMethod(LinkMovementMethod.getInstance());
	    
	    iPhoneApp.setText(Html.fromHtml("<a href=http://itunes.apple.com/us/app/lds-priesthood-ordinances/id350019084?mt=8> " + getResources().getString(R.string.lds_priesthood_ord)));
	    iPhoneApp.setMovementMethod(LinkMovementMethod.getInstance());
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
