package com.klapheck.priesthoodordinances;

import java.util.Arrays;
import com.google.analytics.tracking.android.EasyTracker;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainMenu extends ListActivity{

	private final static String fileName = "MySharedString", stringKey = "SharedInt", fileNameOrder = "MySharedOrder";
	private static int blessingContentClicked = 0;	
	private static SharedPreferences someData;
	private static SharedPreferences.Editor editor;

	private static final int blessingContent[] = {

		// Should use the string and the correct language will be used.
		// This is storing the text that will be displayed in the blessings layout.
		R.string.anointing_oil,
		R.string.sealing_anointing,
		R.string.consecrating_anointing,
		R.string.conferring_priesthood,
		R.string.setting_appart,
		R.string.father_blessings,
		R.string.blessing_children,
		R.string.baptism,
		R.string.confirmation,
		R.string.sacrament,
		R.string.dedicating_homes,
		R.string.dedicating_graves
	};
	
	// The number of ordinances present.
	private final static int blessingNumber = blessingContent.length;
	
	// variable used to hold the user defined blessing order.
	private final static int blessingContentFinal[] = new int[blessingNumber];


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		someData = getSharedPreferences(fileName, 0);
		
		
		// !!!!Test 
		//testingCode(0);
		
		// !!!!Test 
		//testingCode(1);

		/* Get user order from shared preferences. If not user specified then give it default value. */
		int[] orderGet = new int[blessingNumber];
		orderGet = getFromPrefs();
		
		// Default blessing order {0, 1, 2, ...}
		int[] defaultBlessingOrder = new int[blessingNumber];
		for( int i = 0 ; i < blessingNumber ; i++ ) {
			defaultBlessingOrder[i] = i;
		}
		
		// if orderGet has not been user specified {}
		if (orderGet.length<2) {
			// then give orderGet the default blessing order {0, 1, 2, ...}
			orderGet = defaultBlessingOrder;
		}


		/*
		 * Stores blessing names to array.
		 */
		/* For some reason defining the follow string at this location is required. If this
		 * string is placed with the other variables above it causes an error at 
		 * run time. This is likely due to the language preference not yet being established. 
		 * After moving this string to this location it seams to work reliably. 
		 */
		String blessingName[] = {
				getResources().getString(R.string.AnointingOil), //AnointingOil
				getResources().getString(R.string.SealingAnointing), //SealingAnointing
				getResources().getString(R.string.ConsecratingOil), //ConsecratingOil
				getResources().getString(R.string.ConferringPriesthood), //ConferringPriesthood
				getResources().getString(R.string.SettingApart), //Setting Apart
				getResources().getString(R.string.FatherBlessing), //FatherBlessing
				getResources().getString(R.string.NamingChildren), //NamingChildren
				getResources().getString(R.string.Baptism), //Baptism
				getResources().getString(R.string.Confirmation), //Confirmation
				getResources().getString(R.string.Sacrament), //Sacrament
				getResources().getString(R.string.DedicatingHomes), //DedicatingHomes
				getResources().getString(R.string.DedicatingGraves)  //DedicatingGraves
		};

		String[] blessingNameFinal = new String[blessingNumber];

		// Reorder blessingContent & blessingName to match user order.
		for( int i = 0 ; i < blessingNumber ; i++ ) {
			blessingContentFinal[i] = blessingContent[orderGet[i]];
			blessingNameFinal[i] = blessingName[orderGet[i]];
		}


		// Displays the list of blessing in the view using an adapter.
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_activated_1, blessingNameFinal));
	}
	
	
	
	/**
	 * Input a int array and output a single string
	 * */
	private void testingCode(int codeToTest){
		switch(codeToTest){
		case 0:
			// Test storeIntArray
			int[] orderSet = {11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
			storeIntArray(orderSet);
			break;
		case 1:
			// Test clearIntArray
			clearIntArray();
			break;
		case 2:
			
			
			break;
		}
	}
	
	
	/**
	 * clears the user data from shared preferences.
	 */
	public void clearIntArray(){
	    SharedPreferences.Editor edit = getSharedPreferences(fileNameOrder, MODE_PRIVATE).edit();
	    edit.remove("Count");
	    for (int i = 0; i < blessingNumber; i++){
	        edit.remove("IntValue_" + i);
	    }
	    edit.commit();
	}
	
	
	/**
	 * Takes the user order the blessing will appear in and stores it to shared preferences.
	 */
	public void storeIntArray(int[] array){
	    SharedPreferences.Editor edit = getSharedPreferences(fileNameOrder, MODE_PRIVATE).edit();
	    edit.putInt("Count", array.length);
	    int count = 0;
	    for (int i: array){
	        edit.putInt("IntValue_" + count++, i);
	    }
	    edit.commit();
	}
	
	
	/**
	 * Takes the user order the blessing will appear in (stored in shared preferences) and outputs it.
	 */
	public int[] getFromPrefs(){
	    SharedPreferences prefs = getSharedPreferences(fileNameOrder, MODE_PRIVATE);
	    int count = prefs.getInt("Count", 0);
	    int[] ret = new int[count];
	    for (int i = 0; i < count; i++){
	        ret[i] = prefs.getInt("IntValue_"+ i, 0);
	    }
	    return ret;
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
	 * Takes user to specific blessing when they click on it.
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);


		blessingContentClicked = blessingContentFinal[position];
		editor = someData.edit();
		editor.putInt(stringKey, blessingContentClicked);
		editor.commit();
		startActivity(new Intent("com.klapheck.priesthoodordinances.ORDINANCE"));

	}


	/**
	 * Create the main menu action bar in upper portion of the screen.
	 */
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.main_menu, menu);
		return true;
	}

	/**
	 * defines what happens when the user clicks on a menu item set up in {@link #onCreateOptionsMenu}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.aboutUs:
			Intent i = new Intent("com.klapheck.priesthoodordinances.AboutApp");
			startActivity(i);
			break;
		case R.id.share:
			share();
			break;
		case R.id.blessings_in_general:
			Intent bless = new Intent("com.klapheck.priesthoodordinances.BlessingsInGeneral");
			startActivity(bless);
			break;
		case R.id.openSource:
			Intent pref = new Intent("com.klapheck.priesthoodordinances.OpenSource");
			startActivity(pref);
			break;
		case R.id.exit:
			finish();
			break;
		}
		return false;
	}


	// Generic Share. Allows users to Share using any applicable application they see fit.
	private void share() {
		// TODO Auto-generated method stub
		Intent shareIntent=new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT,"http://play.google.com/store/apps/details?id=com.klapheck.priesthoodordinances");
		shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.discovered));
		startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share)));

	}


}
