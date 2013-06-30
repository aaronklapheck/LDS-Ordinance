package com.klapheck.priesthoodordinances;

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

	private final static String fileName = "MySharedString", stringKey = "SharedInt";
	private static int intData = 0;	
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
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        someData = getSharedPreferences(fileName, 0);
        
        
        /*
    	 * For some reason defining the "classes" string at this location is required. If this
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
        
        
        
        
        
        setListAdapter(new ArrayAdapter<String>(this,
	            android.R.layout.simple_list_item_activated_1, blessingName));
        
        // allow user to long-click items in list and get displayed a CAB
        // see AndroidTutorial class: ActionBar for details.
        /*ListView lv = getListView();
	    lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
	    lv.setMultiChoiceModeListener(new ModeCallback());
	    setListAdapter(new ArrayAdapter<String>(this,
	            android.R.layout.simple_list_item_activated_1, classes));
        */
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
		

		intData = blessingContent[position];
		editor = someData.edit();
		editor.putInt(stringKey, intData);
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
		case R.id.preferences:
			Intent prefrences = new Intent("com.klapheck.priesthoodordinances.Prefs");
			startActivity(prefrences);
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
     * Could not get this to work properly.
     * @author Aaron
     *
     */
	// see AndroidTutorial class: ActionBar for details.
   /* private class ModeCallback implements ListView.MultiChoiceModeListener {

    	*//**
    	 * Populates the CAB using a menu.xml document
    	 *//*
	    public boolean onCreateActionMode(ActionMode mode, android.view.Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.blessings_action_menu, menu);
	        mode.setTitle("Select Items");
	        return true;
	    }

	    *//**
	     * Defines the outcomes when a user clicks a item in the CAB
	     *//*
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	        switch (item.getItemId()) {
	        case R.id.share:
	            Toast.makeText(MainMenu.this, "Shared " + getListView().
	            getCheckedItemCount() +
	                    " items", Toast.LENGTH_SHORT).show();
	            mode.finish();
	            break;
	        default:
	            Toast.makeText(MainMenu.this, "Clicked " + item.getTitle(),
	                    Toast.LENGTH_SHORT).show();
	            break;
	        }
	        return true;
	    }
	    
	    public void onDestroyActionMode(ActionMode mode) {
	    }
	    
	    *//**
	     * Defines the text in the subtitle when the user selects more or less items. 
	     *//*
	    public void onItemCheckedStateChanged(ActionMode mode,
	            int position, long id, boolean checked) {
	        final int checkedCount = getListView().getCheckedItemCount();
	        switch (checkedCount) {
	            case 0:
	                mode.setSubtitle(null);
	                break;
	            case 1:
	                mode.setSubtitle("One item selected");
	                break;
	            default:
	                mode.setSubtitle("" + checkedCount + " items selected");
	                break;
	        }
	    }

		public boolean onPrepareActionMode(ActionMode mode,
				android.view.Menu menu) {
			// TODO Auto-generated method stub
			return false;
		}

	}*/
    
}
