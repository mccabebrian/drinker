package com.brianmccabe.yournews;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

public class EntryScreen extends Activity {
    
    static String town = "";
	String test = "";
	private getNextTown gnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_entry_screen);
        
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    LocationListener ll = new Mylocationlistener();
	    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);

    }
    
    private class Mylocationlistener implements LocationListener {
	    @Override
	    public void onLocationChanged(Location location) {
	           
	            getMyLocationAddress(new LatLng(location.getLatitude(),location.getLongitude()));
	            
	       	    }

	    @Override
	    public void onProviderDisabled(String provider) {
	    }

	    @Override
	    public void onProviderEnabled(String provider) {
	    }

	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras) {
	    }
	}
	
	public void getMyLocationAddress(LatLng loc) {
	    
	    Geocoder geocoder= new Geocoder(this, Locale.ENGLISH);
	     
	    try {
	           
	          List<Address> addresses = geocoder.getFromLocation(loc.latitude,loc.longitude, 1);
	          
	          if(!addresses.isEmpty()) {
	           
	              Address fetchedAddress = addresses.get(0);
	              
	              for(int i=0; i<fetchedAddress.getMaxAddressLineIndex(); i++) {
	                    //strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
	                    test = test + fetchedAddress.getAddressLine(i);
	              }
	             
	              gnt = new getNextTown();
	              gnt.execute();
	            
	          }
	          else
	        	  Toast.makeText(getApplicationContext(),"Could not get address..!", Toast.LENGTH_LONG).show();
	             
	      
	    } 
	    catch (IOException e) {
	             Toast.makeText(getApplicationContext(),"Could not get address..!", Toast.LENGTH_LONG).show();
	    }
	  }
	
	public class getNextTown extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@SuppressLint("DefaultLocale") protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			
				try {
					
						String jsonStr2 = sh.makeServiceCall("http://maps.googleapis.com/maps/api/geocode/json?address=" + test.replaceAll("\\s+", "") + "&sensor=false", ServiceHandler.GET);
						
						JSONObject t2 = new JSONObject(jsonStr2);
						JSONArray rows2 = t2.getJSONArray("results");
						JSONObject obj = new JSONObject(rows2.get(0).toString());
						JSONArray rows3 = obj.getJSONArray("address_components");
						JSONObject obj2;
						if(rows3.length()>2){
							obj2 = new JSONObject(rows3.get(rows3.length()-2).toString());
						}else{
							obj2 = new JSONObject(rows3.get(rows3.length()).toString());
						}
						String loc2 = obj2.get("long_name").toString();
						if(town.equals("")){
							  Intent i = new Intent(EntryScreen.this, Home.class);
				                startActivity(i);
				                finish();
						}
						town = loc2;
						Log.d("town:", town);
				} catch (JSONException e) {
					System.out.println("Check internet!!");
					e.printStackTrace();
					
				}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			
			if(town.equals("")){
				town = "dublin";
			}
			gnt.cancel(true);
				
		}
		
		
	}

}