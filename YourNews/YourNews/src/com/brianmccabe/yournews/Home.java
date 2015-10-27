package com.brianmccabe.yournews;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity {

	// Declaring our tabs and the corresponding fragments.
	ActionBar.Tab nwTab, eTab, cTab;
	Fragment newsTab = new NewsTab();
	Fragment eFragmentTab = new EventsTab();
	Fragment classifiedsTab = new ClassifiedsTab();
	Context ctx = this;
	ProgressDialog dialog;
	static String town = "";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Firebase.setAndroidContext(this);
		
		setContentView(R.layout.activity_home);
		
	   
		// Asking for the default ActionBar element that our platform supports.
		ActionBar actionBar = getActionBar();
		 
        // Screen handling while hiding ActionBar icon.
        actionBar.setDisplayShowHomeEnabled(true);
 
        // Screen handling while hiding Actionbar title.
        actionBar.setDisplayShowTitleEnabled(true);
        
        View homeIcon = findViewById(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.id.home : R.id.disableHome);
        ((View) homeIcon.getParent()).setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        ((View) homeIcon).setVisibility(View.GONE);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        ImageButton mImageButton = (ImageButton) mCustomView.findViewById(R.id.imageButton1);
        mImageButton.setVisibility(View.INVISIBLE);
		mTitleTextView.setText("Localeyes'd");
		actionBar.setCustomView(mCustomView);
		actionBar.setDisplayShowCustomEnabled(true);
        // Creating ActionBar tabs.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 
        // Setting custom tab text.
        nwTab = actionBar.newTab().setText("News");
        eTab = actionBar.newTab().setText("Events");
        cTab = actionBar.newTab().setText("Classifieds");
        
        // Setting tab listeners.
        nwTab.setTabListener(new TabListener(newsTab));
        eTab.setTabListener(new TabListener(eFragmentTab));
        cTab.setTabListener(new TabListener(classifiedsTab));

       
        // Adding tabs to the ActionBar.
        actionBar.addTab(nwTab);
        actionBar.addTab(eTab);
        actionBar.addTab(cTab);
        
        
        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.addEvent) {
			Intent intent = new Intent(this, AddEvent.class);
			startActivity(intent);
			return true;
		}
		
		if (id == R.id.addClassified) {
			Intent intent = new Intent(this, AddClassified.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}