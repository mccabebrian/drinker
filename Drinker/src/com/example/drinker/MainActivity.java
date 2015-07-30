package com.example.drinker;


import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	TextView tv;
	String name = "";
	String description = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView1);
        
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        new getBeer().execute();
       
    }

    
    private class getBeer extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			

		}

		@SuppressLint("DefaultLocale") protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			
			String jsonStr = sh.makeServiceCall("http://api.brewerydb.com/v2/beer/random/?key=e5c14921dc17c2a6a8ad27fb9d19edf6", ServiceHandler.GET);
			
			Log.d("Response: ", "> " + jsonStr);
			
				if (jsonStr != null) {
				
				try {
					JSONObject t = new JSONObject(jsonStr);
					
					JSONObject jo = t.getJSONObject("data");
					
					name = jo.getString("name");
					description = jo.getString("description");
					
					Log.d("name", "> " + name);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				 Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Log.d("name", "> " + name);
			tv.setText(name + "\n\n" + description);
		}

		
	}
}
