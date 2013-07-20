package com.klapheck.priesthoodordinances;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;



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

		someData = getSharedPreferences(fileName, MODE_PRIVATE);

		/* Get user order from shared preferences. If not user specified then give it default value. */
		int[] orderGet = new int[blessingNumber];
		orderGet = getFromPrefs();
		
		// Default blessing order {0, 1, 2, ...}.
		int[] defaultBlessingOrder = new int[blessingNumber];
		for(int i = 0 ; i < blessingNumber ; i++) {
			defaultBlessingOrder[i] = i;
		}
		
		// if orderGet has not been user specified {}
		if (orderGet.length<2) {
			// then store default blessing order {0, 1, 2, ...} and read it.
			storeIntArrayToPrefs(defaultBlessingOrder);
			orderGet = getFromPrefs();
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

		
		// allow user to long-click items in list and get displayed a CAB.
        ListView lv = getListView();
	    lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
	    lv.setMultiChoiceModeListener(new ModeCallback());
		
		// Displays the list of blessing in the view using an adapter.
		setListAdapter(new ArrayAdapter<String>(this,
				R.layout.list_view, blessingNameFinal));
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
	 * Resets the order of the ordinances to the default and stores this to the preferences.
	 */
	public void defaultIntArray(){
	    int[] defaultOrder = new int[blessingNumber];
	    for (int i = 0; i < blessingNumber; i++){
	    	defaultOrder[i] = i;
	    }
	    storeIntArrayToPrefs(defaultOrder);
	}
	
	
	/**
	 * Takes the user order the blessing will appear in and stores it to shared preferences.
	 */
	public void storeIntArrayToPrefs(int[] array){
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
	
	
	/**
     * Allow user to long-click items in list and get displayed a CAB.
     * Source: http://www.java2s.com/Code/Android/UI/ThisdemoillustratestheuseofCHOICEMODEMULTIPLEMODAL.htm
     * @author Aaron Klapheck
     *
     */
    private class ModeCallback implements ListView.MultiChoiceModeListener {
    	
    	// variable to track items selected in CAB.
    	private int[] moveArray = new int[blessingNumber];

    	/**
    	 * Populates the CAB using blessings_action_menu.xml document
    	 */
    	public boolean onCreateActionMode(ActionMode mode, android.view.Menu menu) {
    		MenuInflater inflater = getMenuInflater();
    		inflater.inflate(R.menu.blessings_action_menu, menu);
    		mode.setTitle(getResources().getString(R.string.select_items));
    		return true;
    	}

    	public boolean onPrepareActionMode(ActionMode mode,
    			android.view.Menu menu) {
    		// TODO Auto-generated method stub
    		return false;
    	}

    	int checkedCount;

    	/**
    	 * Defines the text in the subtitle when the user selects more or less items. 
    	 */
    	public void onItemCheckedStateChanged(ActionMode mode,
    			int position, long id, boolean checked) {
    		checkedCount = getListView().getCheckedItemCount();

    		// Use moveArray to track selected items.
    		if(checked){
    			moveArray[position] = 1;
    		}
    		if(!checked){
    			moveArray[position] = 0;
    		}	        

    		// Show subtext depending on the number of Ordinances selected.
    		switch (checkedCount) {
    		case 0:
    			mode.setSubtitle(null);
    			break;
    		case 1:
    			mode.setSubtitle(getResources().getString(R.string.one_selected));
    			break;
    		default:
    			mode.setSubtitle("" + checkedCount + " " + getResources().getString(R.string.items_selected));
    			break;
    		}
    	}


    	/**
    	 * Defines the outcomes when a user clicks a item in the CAB
    	 */
    	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

    		/* Get ordinance order from shared preferences. */
    		int[] orderGet = new int[blessingNumber];
    		orderGet = getFromPrefs();

    		int temporary;
    		boolean movedUp = false;
    		boolean movedDown = false;

    		switch (item.getItemId()) {
    		case R.id.moveUp:
    			for (int i = 0; i < blessingNumber; i++){
    				if(moveArray[i] == 1){
    					if(i == 0){
    						Toast.makeText(MainMenu.this, getResources().getString(R.string.cant_move_up), Toast.LENGTH_SHORT).show();
    						break;
    					}
    					else{
    						movedUp = true;
    						temporary = orderGet[i];
    						orderGet[i] = orderGet[i-1];
    						orderGet[i-1] = temporary;
    					}
    				}
    			} 
    			
    			// Show toast depending on the number of ordinances the user is rearranging.
    			if(movedUp){
    				switch (checkedCount) {
    	    		case 1:
    	    			Toast.makeText(MainMenu.this, getResources().getString(R.string.moved_up), Toast.LENGTH_SHORT).show();
    	    			break;
    	    		default:
    	    			Toast.makeText(MainMenu.this, "" + checkedCount + " " + getResources().getString(R.string.moved_up_plural), Toast.LENGTH_SHORT).show();
    					break;
    				}
    			}

    			// Store the moved ordinances into the user preferences.
    			storeIntArrayToPrefs(orderGet);

    			mode.finish();
    			break; //case R.id.moveUp:
    		case R.id.moveDown:
    			for (int i = (blessingNumber-1); i >= 0; i--){
    				if(moveArray[i] == 1){
    					if(i == (blessingNumber-1)){
    						Toast.makeText(MainMenu.this, getResources().getString(R.string.cant_move_down), Toast.LENGTH_SHORT).show();
    						break;
    					}
    					else{
    						movedDown = true;
    						temporary = orderGet[i];
    						orderGet[i] = orderGet[i+1];
    						orderGet[i+1] = temporary;
    					}
    				}
    			} 

    			// Show toast depending on the number of ordinances the user is rearranging.
    			if(movedDown){
    				switch (checkedCount) {
    	    		case 1:
    	    			Toast.makeText(MainMenu.this, getResources().getString(R.string.moved_down), Toast.LENGTH_SHORT).show();
    	    			break;
    	    		default:
    	    			Toast.makeText(MainMenu.this, "" + checkedCount + " " + getResources().getString(R.string.moved_down_plural), Toast.LENGTH_SHORT).show();
    					break;
    				}
    			}

    			// Store the moved ordinances into the user preferences.
    			storeIntArrayToPrefs(orderGet);

    			mode.finish();
    			break; //case R.id.moveDown:

    		case R.id.defaultOrder:
    			defaultIntArray();
    			mode.finish();
    			break; //case R.id.defaultOrder:

    		} //switch (item.getItemId())

    		// Clear moveArray for next use.
    		for (int i = 0; i < blessingNumber; i++){
    			moveArray[i] = 0;
    		}

    		onCreate(null);
    		return true;
    	}

    	public void onDestroyActionMode(ActionMode mode) {
    	}

    }

}
